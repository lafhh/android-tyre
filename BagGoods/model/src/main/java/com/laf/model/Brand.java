package com.laf.model;

import java.util.ArrayList;

/**
 * Created by apple on 2017/3/13.
 */

public class Brand {

    private int brandId;

    private String name;

    private int imgs;

    private ArrayList<String> imgsUrl;

    private String imgUrl;




















    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgs() {
        return imgs;
    }

    public void setImgs(int imgs) {
        this.imgs = imgs;
    }

    public ArrayList<String> getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(ArrayList<String> imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
