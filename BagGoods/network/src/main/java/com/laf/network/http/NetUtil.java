package com.laf.network.http;

/**
 * Created by apple on 17/2/21.
 */
public class NetUtil {
    public static final String IP = "120.27.195.220";
    public static final String PORT = ":80";
    public static final String HTTP_PREF = "http://" + IP + PORT;
    public static final String BASE_URL = HTTP_PREF + "/api/";

    public static final String GET_TYPE = "?requestType=%s";
    public static final String GET_GETVERIFY = GET_TYPE + "&mobile=%s";
    public static final String URL_STORE = BASE_URL + "store.json";
    public static final String URL_PRODUCTS = BASE_URL + "products.json" + GET_GETVERIFY;

    //
}
