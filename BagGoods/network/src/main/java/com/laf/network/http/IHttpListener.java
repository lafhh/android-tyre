package com.laf.network.http;

/**
 * Created by apple on 2017/3/13.
 */

public interface IHttpListener {

    void onResult(int action, Response response);

    void onProgress(boolean isInProgress);
}
