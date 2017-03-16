package com.laf.baggoods.http;

import com.laf.baggoods.util.NewContainer;
import com.laf.baggoods.util.SysTools;
import com.laf.network.http.HttpManager;
import com.laf.network.http.IHttpListener;
import com.laf.network.http.NetUtil;
import com.laf.network.http.Request.RequestMethod;
import com.laf.network.http.Response;
import com.laf.network.util.NameValuePair;

import java.util.List;

/**
 * Created by apple on 2017/3/11.
 */

public class ImplHttpManager extends HttpManager {

    /**
     * 登录action
     */
    private static final int HOME = 0x00000001;

    /**
     * 请求首页数据
     */
    public void requestHomeData(IHttpListener listener) {
        send(HOME, NewContainer.<NameValuePair<String,String>>list(), listener);
    }


    @Override
    protected String getUrl(int action, List<NameValuePair<String, String>> params) {
        String url = "";
        switch (action) {
            case HOME:
                String resolution = SysTools.getResolution();
                url = String.format(NetUtil.GET_HOME, resolution);
                break;
        }
        return url;
    }

    @Override
    protected RequestMethod getRequestMethod(int action) {
        RequestMethod requestMethod = RequestMethod.POST;
        switch(action) {
            case HOME:
                requestMethod = RequestMethod.GET;
                break;

            default:
        }
        return requestMethod;
    }

    @Override
    protected boolean isGzip(int action) {
        boolean isGzip = false;
        switch(action) {
            case HOME:
                isGzip = true;
            default:
        }
        return isGzip;
    }

    @Override
    protected String getBody(int action, List<NameValuePair<String, String>> datas) {
        String body = "";
        switch (action) {

        }
        return body;
    }

    @Override
    protected Object handleData(int action, List<NameValuePair<String, String>> datas, Response response) {
        String data = response.getData();
        switch(action) {
            case HOME:

                break;
        }
        return data;
    }
}
