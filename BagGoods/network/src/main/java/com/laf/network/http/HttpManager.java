package com.laf.network.http;

import android.util.Log;
import com.laf.network.http.Request.ContentType;
import com.laf.network.http.Request.RequestMethod;
import com.laf.network.http.Response.ResponseCode;
import com.laf.network.util.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by apple on 17/2/18.
 *
 */
public abstract class HttpManager {
    private static final String TAG = "HttpManager";

    private static final int THREAD_POOL_MAX_SIZE = 5;

    private static Executor sFixedThreadPool = Executors.newFixedThreadPool(THREAD_POOL_MAX_SIZE);

    public interface IHttpListener {
        //应该定义另一个实体，给客户端用而不是response
        void onResult(int action, Response response);
        void onProgress(boolean isInProgress);
    }

    IHttpListener iHttpListener;

    protected void send(final int action,
                     final List<NameValuePair<String, String>> datas) {
        Runnable runnable = new Runnable() {
            public void run() {
                iHttpListener.onProgress(true);

                Request request = buildRequest(action, datas);

                Response response = HttpConnector.connect(request);

                if (response.getResponseCode() == null) {
                    response.setResponseCode(ResponseCode.Failed);
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
                        if (response.getResponseCode() == ResponseCode.Succeed) {
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
        sFixedThreadPool.execute(runnable);
    }

    protected abstract String getUrl(int action, List<NameValuePair<String, String>> params);

    protected abstract String getBody(int action, List<NameValuePair<String, String>> datas);

//    protected abstract String getBody(int action, String datas);

    protected abstract Object handleData(int action,
                                         List<NameValuePair<String, String>> datas,
                                         Response response);

    protected RequestMethod getRequestMethod(int action) {
        return RequestMethod.POST;
    }

    protected ContentType getContentType(int action) {
        return ContentType.JSON;
    }

    protected List<NameValuePair<String, String>> getRequestProperties(int action) {
        return new ArrayList<>();
    }

    protected boolean isGzip(int action) { return false; }



    protected Request buildRequest(int action, List<NameValuePair<String, String>> datas) {
        Request request = new Request();
        request.setUrl(getUrl(action, datas));
        request.setBody(getBody(action, datas));
        request.setRequestMethod(getRequestMethod(action));
        request.setContentType(getContentType(action));
        request.setRequestProperties(getRequestProperties(action));
        request.setGzip(isGzip(action));
        return request;
    }

    protected void parserResult(int action, Response response) {
        String data = response.getData();

        if (data != null) {
            if (getContentType(action) == ContentType.JSON) {
                try {
                    JSONObject rootJsonObj = new JSONObject(data);

                    if (rootJsonObj.has("result")) {
                        JSONObject resultObj = rootJsonObj.getJSONObject("result");
//                        response.setResultCode(resultObj.getInt("resultCode"));
                        int resultCode = resultObj.getInt("resultCode");
                        String resultDesc = resultObj.getString("resultDesc");

                        response.setResultCode(resultCode);
                        response.setResultDesc(resultDesc);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, e.toString());
                }
            }
        }
    }

    public void setListenner(IHttpListener listener) {
        iHttpListener = listener;
    }

    protected Executor getsFixedThreadPool() {
        return sFixedThreadPool;
    }
}



