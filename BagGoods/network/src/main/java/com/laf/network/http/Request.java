package com.laf.network.http;

import java.util.List;
import com.laf.network.util.NameValuePair;
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

        JSON
    }

    private String url;

    private String body;

    private RequestMethod requestMethod = RequestMethod.GET;

    private ContentType contentType = ContentType.JSON;

    private List<NameValuePair<String, String>> requestProperties;

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
}
