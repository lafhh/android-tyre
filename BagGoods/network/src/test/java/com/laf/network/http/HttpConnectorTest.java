package com.laf.network.http;

import com.laf.network.http.Request.ContentType;
import com.laf.network.http.Request.RequestMethod;
import com.laf.network.http.Response.ResponseCode;
import com.laf.network.util.NameValuePair;
import com.laf.network.util.StringUtil;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by apple on 17/2/23.
 */
public class HttpConnectorTest {
 private static final String TAG = "HttpConnector";
    /**
     * Connect Timeout: 1min
     */
    private static final int CONNECT_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(60L);

     /**
     * Connect Timeout: 1min
     */
    private static final int READ_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(60L);

        public static Response connect(Request request) {
        Response response = new Response();
        //没有设置对应的请求

        HttpURLConnection conn = null;
        System.setProperty("http.keepAlive", "false");

        if (StringUtil.isNullOrEmpty(request.getUrl())) {
            response.setResponseCode(ResponseCode.ParamError);
        }

        try {
            URL url = new URL(request.getUrl());
            if (!url.getProtocol().toLowerCase().equals("https")) {
                conn = (HttpURLConnection) url.openConnection();
            } else {
                conn = getHttpsConn(url);
            }
System.out.println(conn.toString());
            conn.setDoInput(true);
            conn.setUseCaches(false);
            setRequestMethod(request, conn);
            setRequestProperty(request, conn);

            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);

            if (request.getContentType() == ContentType.FILE) {
                StringBuilder contentBody = new StringBuilder();
                String endBoundary = "\r\n--" + request.getBoundary() + "--\r\n";
                OutputStream out = conn.getOutputStream();

                out.write(("--" + request.getBoundary()).getBytes("UTF-8"));
                contentBody.append("\r\n")
                           .append("Content-Disposition:form-data; name=\"upload1\";")
                           .append("filename=" + "\"" + request.getBody() + "\"")
                           .append("\r\n")
                           .append("Content-Type:application/octet-stream")
                           .append("\r\n\r\n");
                out.write(contentBody.toString().getBytes("UTF-8"));
                byte[] data = request.getFile().toByteArray();

                out.write(data, 0, data.length);
                request.getFile().close();

                out.write(endBoundary.getBytes("UTF-8"));
                out.flush();
System.out.println(out);
                out.close();

            } else if (request.getBody() != null) {
                byte[] data = request.getBody().getBytes("UTF-8");

                OutputStream out = conn.getOutputStream();
                if (request.isGzip()) {
                    GZIPOutputStream gzOut = new GZIPOutputStream(out);
                    gzOut.write(data);
                    gzOut.flush();
                    gzOut.close();
                } else {
                    out.write(data);
                    out.flush();
                }
                out.close();
            }

            int responseCode = initResponseCode(response, conn);
            switch(responseCode) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_FOUND:
                case HttpURLConnection.HTTP_CONFLICT:
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_CREATED:
                    setResponseData(request, response, conn, false);
//                    Log.d(TAG, "url : " + request.getUrl()
//                        + " statuscode : " + responseCode
//                        + " body : " + response.getData());
                    System.out.println("url : " + request.getUrl()
                        + " statuscode : " + responseCode
                        + " body : " + response.getData());
                    break;
                default:
//                    Log.d(TAG, "url : " + request.getUrl()
//                        + " statuscode : " + responseCode);
                   System.out.println("url : " + request.getUrl()
                        + " statuscode : " + responseCode);
                    break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCode.Failed);

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCode.Timeout);

        } catch (ConnectException e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCode.NetworkError);

        } catch (SocketException e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCode.NetworkError);

        } catch (IOException e) {
            if (conn != null) {
                try {
                    int responseCode = initResponseCode(response, conn);
                            switch (responseCode) {
                                case HttpURLConnection.HTTP_BAD_REQUEST:
                                case HttpURLConnection.HTTP_UNAUTHORIZED:
                                case HttpURLConnection.HTTP_FORBIDDEN:
                                case HttpURLConnection.HTTP_NOT_FOUND:
                                case HttpURLConnection.HTTP_CONFLICT:
                                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                                    setResponseData(request, response, conn, true);
                                default:
//                                    Log.d(TAG, "IOException get statuscode: " + responseCode
//                                            + " url : " + request.getUrl());
                                    System.out.println("IOException get statuscode: " + responseCode
                                            + " url : " + request.getUrl());
                                    break;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    response.setResponseCode(ResponseCode.Failed);
                } finally {
                    conn.disconnect();
                }
            } else {
                e.printStackTrace();
                response.setResponseCode(ResponseCode.NetworkError);
            }
        }
        return response;
    }

    public static void setRequestMethod(Request request,
    HttpURLConnection conn) throws ProtocolException {
        RequestMethod method = request.getRequestMethod();
        switch(method) {
            case GET:
                conn.setRequestMethod("GET");
            case POST:
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                break;
            case PUT:
                conn.setRequestMethod("PUT");
                conn.setDoOutput(true);
                break;
            case DELETE:
                conn.setRequestMethod("DELETE");
                break;
            default:

        }
    }

    public static void setRequestProperty(Request request,
    HttpURLConnection conn) {
        ContentType contentType = request.getContentType();
        switch(contentType) {
            case XML:
                if (request.getRequestMethod() == RequestMethod.POST) {
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                } else {
                    conn.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");
                }
                break;
            case JSON:
                if (request.getRequestMethod() == RequestMethod.POST) {
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                } else {
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                }
                break;
            case FILE:
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary" + request.getBoundary());
            default:
        }

        boolean isGzip = request.isGzip();
        if (isGzip) {
            conn.setRequestProperty("Accept-Encoding", "gzip");
            if (request.getBody() != null) {
                conn.setRequestProperty("Content-Encoding", "gzip");
            }
        }

        if (request.getRequestProperties() != null) {
            for (NameValuePair<String, String> nameValuePair : request.getRequestProperties()) {
                conn.setRequestProperty(nameValuePair.key,
                                        nameValuePair.value);
            }
        }
    }

    public static int initResponseCode(Response response,
    HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        switch(responseCode) {
            case HttpURLConnection.HTTP_OK:
            case HttpURLConnection.HTTP_CREATED:
                response.setResponseCode(ResponseCode.Succeed);
                break;
            case HttpURLConnection.HTTP_BAD_REQUEST:
                response.setResponseCode(ResponseCode.BadRequest);
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                response.setResponseCode(ResponseCode.UnAuthorized);
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                response.setResponseCode(ResponseCode.Forbidden);
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                response.setResponseCode(ResponseCode.NotFound);
                break;
            case HttpURLConnection.HTTP_CONFLICT:
                response.setResponseCode(ResponseCode.Conflict);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                response.setResponseCode(ResponseCode.InternalError);
                break;
            default:
                response.setResponseCode(ResponseCode.Failed);
                break;
        }
        return responseCode;
    }

    private static void setResponseData(Request request, Response response,
    HttpURLConnection conn, boolean isError) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        InputStream is = null;

        if (isError)
            is = conn.getErrorStream();
        if (is == null)
            is = conn.getInputStream();

        InputStream bis = null;
        InputStream gzis;
        if ("gzip".equals(conn.getHeaderField("Content-Encoding"))) {
            gzis = new GZIPInputStream(is);

        } else {
            bis = new BufferedInputStream(is);
            bis.mark(2);
            byte[] header = new byte[2];
            int result = bis.read(header);
            bis.reset();

            //这是什么？也要进行解压
            if (result == 2 &&
                    ((header[0] & 0xFF) | ((header[1] & 0xFF) << 8)) == 0x8b1f) {
                gzis = new GZIPInputStream(bis);
                bis = null;
            } else {
                gzis = bis;
                bis = null;
            }
        }
        int length = gzis.read(bytes);
        while (length != -1) {
            os.write(bytes, 0, length);
            length = gzis.read(bytes);
        }
        bytes = os.toByteArray();
        gzis.close();
        if (bis != null)
            bis.close();
        if (is != null)
            is.close();

        response.setData(new String(bytes, "UTF-8"));

    }

    private static HttpURLConnection getHttpsConn(URL url) throws IOException {
        trustAll();
        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
        return https;
    }

    //信任所有证书
    private static void trustAll() {
        TrustManager[] trustAllCertificates = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;// Not relevant.
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing. Just allow them all.
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing. Just allow them all.
                    }
                }
        };

        HostnameVerifier trustAllHostnames = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;// Just allow them all.
            }
        };

        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCertificates, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
