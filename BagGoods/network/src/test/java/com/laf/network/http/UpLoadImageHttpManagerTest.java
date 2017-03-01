package com.laf.network.http;

import com.laf.network.util.Base64;
import com.laf.network.util.NameValuePair;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 17/2/24.
 */
public class UpLoadImageHttpManagerTest extends UploadHttpManagerTest {

    @Test
    public void testLogin() throws Exception {
//        this.upload();
//        TimeUnit.SECONDS.sleep(5);
    }

    protected String getUrl(int action, List<NameValuePair<String, String>> params) {
        String url = "http://120.27.195.220/api/products.json?requestType=assignment";
        return url;
    }

    @Override
    protected String getBody(int action, List<NameValuePair<String, String>> datas) {
        return null;
    }

    @Override
    protected String getBody(int action, List<NameValuePair<String, String>> datas, String path) {
        getBody(action, datas);
        return null;
    }

    @Override
    protected Object handleData(int action, List<NameValuePair<String, String>> datas, Response response) {
        String body = response.getData();
        System.out.println(body);
        return body;
    }

    @Override
    protected Request.RequestMethod getRequestMethod(int action) {
        return Request.RequestMethod.POST;
    }

    @Override
    protected Request.ContentType getContentType(int action) {
        return Request.ContentType.FILE;
    }

    @Override
    protected String getBoundary(int action) {
        return "AaB03x";
    }

    @Override
    protected ByteArrayOutputStream getFile(int action, String path) {

        return null;
    }

    public void upload() {
        List<NameValuePair<String, String>> list = new ArrayList<>();
        list.add(new NameValuePair("request_type", "vcode"));
        list.add(new NameValuePair("mobile", Base64.encode("113813931721")));
        setListenner(new IHttpListener() {
            @Override
            public void onResult(int action, Response response) {
                String s = (String) response.getObj();
                System.out.println("onresult " + s);
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
