package com.laf.network.http;

import com.laf.network.util.Base64;
import com.laf.network.util.NameValuePair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 17/2/24.
 */
public class DoGetHttpManagerTest extends HttpManagerTest {

    @Test
    public void testLogin() throws Exception {
        this.login();
        TimeUnit.SECONDS.sleep(5);
    }

    protected String getUrl(int action, List<NameValuePair<String, String>> params) {
        String url = "http://120.27.195.220/api/products.json";
        return url;
    }

    protected String getBody(int action, List<NameValuePair<String, String>> datas) {
        return "requestType=assignment";
    }

    @Override
    protected Object handleData(int action, List<NameValuePair<String, String>> datas, Response response) {
        String body = response.getData();
        if (body != null) {
            //处理数据
        }
System.out.println("handleData(): " + body);
        return body;
    }

    protected Request.RequestMethod getRequestMethod(int action) {
        return Request.RequestMethod.GET;
    }

    public void login() {
        List<NameValuePair<String, String>> list = new ArrayList<>();
        list.add(new NameValuePair("request_type", "vcode"));
        list.add(new NameValuePair("mobile", Base64.encode("113813931721")));
        setListenner(new HttpManagerTest.IHttpListener() {
            @Override
            public void onResult(int action, Response response) {
                String s = (String) response.getObj();
                System.out.println("onResult() " + s);
            }

            @Override
            public void onProgress(boolean isInProgress) {

            }
        });
        send(1, list);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }





}
