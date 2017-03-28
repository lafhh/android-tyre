package com.laf.imageloader.decode;

import android.graphics.BitmapFactory;

/**
 * Created by apple on 2017/3/26.
 */

public class ImageDecodingInfo {

    private String imgUri;

    private int targetWidth;

    private int targetHeight;

    private int srcWidth;

    private int srcHeight;

    /**
     * 以下三个属性对应{@link BitmapFactory.Options}的同名属性
     */
    private int inDensity;

    private int targetDensity;

    private boolean inScaled;




















    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public int getSrcWidth() {
        return srcWidth;
    }

    public void setSrcWidth(int srcWidth) {
        this.srcWidth = srcWidth;
    }

    public int getSrcHeight() {
        return srcHeight;
    }

    public void setSrcHeight(int srcHeight) {
        this.srcHeight = srcHeight;
    }

    public int getInDensity() {
        return inDensity;
    }

    public void setInDensity(int inDensity) {
        this.inDensity = inDensity;
    }

    public int getTargetDensity() {
        return targetDensity;
    }

    public void setTargetDensity(int targetDensity) {
        this.targetDensity = targetDensity;
    }

    public boolean isInScaled() {
        return inScaled;
    }

    public void setInScaled(boolean inScaled) {
        this.inScaled = inScaled;
    }
}
