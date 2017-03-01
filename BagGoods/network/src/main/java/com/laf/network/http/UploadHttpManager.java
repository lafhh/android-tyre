package com.laf.network.http;

import com.laf.network.util.NameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by apple on 17/2/27.
 */
public abstract class UploadHttpManager extends HttpManager {

    protected void send(final int action,
                        final List<NameValuePair<String, String>> datas,
                        final String path) {
        Runnable runnable = new Runnable() {
            public void run() {
                iHttpListener.onProgress(true);

                Request request = buildRequest(action, datas, path);

                Response response = HttpConnector.connect(request);

                if (response.getResponseCode() == null) {
                    response.setResponseCode(Response.ResponseCode.Failed);
                }

                switch (response.getResponseCode()) {
                    case Succeed:
                    case BadRequest:
                    case UnAuthorized:
                    case Forbidden:
                    case NotFound:
                    case Conflict:
                    case InternalError:
                        parserResult(action, response);
                        if (response.getResponseCode() == Response.ResponseCode.Succeed) {
                            response.setObj(handleData(action, datas, response));
                        }
                        break;
                    default:
                        response.setResultCode(-1);
                        break;
                }

                iHttpListener.onResult(action, response);

                iHttpListener.onProgress(false);
            }
        };
        getsFixedThreadPool().execute(runnable);
    }

    protected abstract String getBody(int action, List<NameValuePair<String, String>> datas, String path);

    protected abstract String getBoundary(int action);

    protected abstract ByteArrayOutputStream getFile(int action, String path);

    protected Request buildRequest(int action, List<NameValuePair<String, String>> datas, String path) {
        Request request = new Request();
        request.setUrl(getUrl(action, datas));
        request.setBody(getBody(action, datas, path));
        request.setRequestMethod(getRequestMethod(action));
        request.setContentType(getContentType(action));
        request.setRequestProperties(getRequestProperties(action));
        request.setGzip(isGzip(action));
        request.setBoundary(getBoundary(action));
        request.setFile(getFile(action, path));
        return request;
    }
}
