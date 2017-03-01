package com.laf.network.http;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.laf.network.util.NameValuePair;
import com.laf.network.util.StringUtil;

/**
 * Created by apple on 17/2/16.
 */
public class Request {

    public enum RequestMethod {
        /**
         * get请求
         */
        GET,

        /**
         * post请求
         */
        POST,

        PUT,

        DELETE
    }

    public enum ContentType {
        XML,

        JSON,

        FILE
    }

    private String url;

    private String body;

    private RequestMethod requestMethod = RequestMethod.GET;

    private ContentType contentType = ContentType.JSON;

    private List<NameValuePair<String, String>> requestProperties;

    private boolean isGzip;

    private String boundary = null;

    private ByteArrayOutputStream file = null;

    public String getUrl() { return url; }

    //znn this.url = StringUtil.fixUrl(url);
    public void setUrl(String url) {this.url = url; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public RequestMethod getRequestMethod() { return requestMethod; }

    public void setRequestMethod(RequestMethod reqMethod) { requestMethod = reqMethod; }

    public ContentType getContentType() { return contentType; }

    public void setContentType(ContentType contentType) { this.contentType = contentType; }

    public List<NameValuePair<String, String>> getRequestProperties() { return requestProperties; }

    public void setRequestProperties(List<NameValuePair<String, String>> list) { this.requestProperties = list; }

    public boolean isGzip() {
        return isGzip;
    }

    public void setGzip(boolean gzip) {
        isGzip = gzip;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public ByteArrayOutputStream getFile() {
        return file;
    }

    public void setFile(ByteArrayOutputStream file) {
        this.file = file;
    }

    public void addRequestProperty(String key, String value) {
        if (requestProperties == null) {
            requestProperties = new ArrayList<>();
        }
        if (StringUtil.isNullOrEmpty(key) &&
                StringUtil.isNullOrEmpty(value)) {
            requestProperties.add(new NameValuePair<>(key, value));
        }
    }
}
