package com.abc.theqrscannerprofree.utils;

public class ForRstUtils {
    private String mContents;
    private OurFormUtils mBarcodeFormat;

    public void setContents(String contents) {
        mContents = contents;
    }

    public void setBarcodeFormat(OurFormUtils format) {
        mBarcodeFormat = format;
    }

    public OurFormUtils getBarcodeFormat() {
        return mBarcodeFormat;
    }

    public String getContents() {
        return mContents;
    }
}