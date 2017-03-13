package com.laf.network.http;

/**
 * Created by apple on 17/2/21.
 */
public class NetUtil {
    public static final String IP = "120.27.195.220";
    public static final String PORT = ":80";
    public static final String HTTP_PREF = "http://" + IP + PORT;
    public static final String HTTPS_PREF = "https://" + IP + PORT;
    public static final String BASE_URL = HTTP_PREF + "/api/";
    public static final String URL_PRODUCTS = BASE_URL + "products.json";
    public static final String URL_STORE = BASE_URL + "store.json";
    public static final String GET_TYPE = "requestType=";

    /**
     * 请求首页数据
     */
    public static final String GET_HOME = URL_PRODUCTS + "?resolution=%s&" + GET_TYPE + "homex";

    /**
     * 发送验证码接口（测试用，生产环境是post）
     */
    public static final String GET_GETVERIFY = URL_PRODUCTS + "?mobile=%s&" + GET_TYPE + "vcode";


}
