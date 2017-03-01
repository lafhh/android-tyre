package com.laf.network.http;

import com.laf.network.util.Base64;
import com.laf.network.util.NameValuePair;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 17/2/24.
 */
public class UpLoadFileHttpManagerTest extends UploadHttpManagerTest {

    @Test
    public void testLogin() throws Exception {
        this.upload();
        TimeUnit.SECONDS.sleep(5);
    }

    @Override
    protected String getUrl(int action, List<NameValuePair<String, String>> params) {
        String url = "http://120.27.195.220/api/products.json";
        return url;
    }

    @Override
    protected String getBody(int action, List<NameValuePair<String, String>> datas) {
        return null;
    }

    @Override
    protected String getBody(int action, List<NameValuePair<String, String>> datas, String path) {
        String body = getBody(action, datas);
        if (body != null) {
            //body = null;
        } else {
            body = path;
        }
        return body;
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
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(
                    new FileInputStream(path));
            out = new ByteArrayOutputStream();

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
//                if (out!= null) out.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    public void upload() {
        List<NameValuePair<String, String>> list = new ArrayList<>();
        String path = "/Users/apple/Documents/ThinkingInJava/practice/src/io/GZIPcompress.java"; //txt file
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
        send(1, list, path);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

}
