package com.unnamed.b.atv.sample.entity;

/**
 * Created by iss on 13/4/18.
 */

public class Contact extends Entity {

    int mContactId = 0;
    String mContactName = "";
    String mContactTitle = "";
    String mContactSubtitle = "";
    String mContactImageSrcServerPath = "";
    String mContactImageSrcLocalPath = "";

    String mWorkType = "";
    String mRequestType = "";
    String mLocation = "";
    String mDepartment = "";
    String mContact = "";

    public String getmContactName() {
        return mContactName;
    }

    public void setmContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public String getmWorkType() {
        return mWorkType;
    }

    public void setmWorkType(String mWorkType) {
        this.mWorkType = mWorkType;
    }

    public String getmRequestType() {
        return mRequestType;
    }

    public void setmRequestType(String mRequestType) {
        this.mRequestType = mRequestType;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmDepartment() {
        return mDepartment;
    }

    public void setmDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public int getContactId() {
        return mContactId;
    }

    public void setContactId(int mContactId) {
        this.mContactId = mContactId;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public String getmContactTitle() {
        return mContactTitle;
    }

    public void setmContactTitle(String mContactTitle) {
        this.mContactTitle = mContactTitle;
    }

    public String getmContactSubtitle() {
        return mContactSubtitle;
    }

    public void setmContactSubtitle(String mContactSubtitle) {
        this.mContactSubtitle = mContactSubtitle;
    }

    public String getmContactImageSrcServerPath() {
        return mContactImageSrcServerPath;
    }

    public void setmContactImageSrcServerPath(String mContactImageSrcServerPath) {
        this.mContactImageSrcServerPath = mContactImageSrcServerPath;
    }

    public String getmContactImageSrcLocalPath() {
        return mContactImageSrcLocalPath;
    }

    public void setmContactImageSrcLocalPath(String mContactImageSrcLocalPath) {
        this.mContactImageSrcLocalPath = mContactImageSrcLocalPath;
    }

    @Override
    public long getKey() {
        return mContactId;
    }

    @Override
    public String getValue() {
        return mContactName;
    }

    private int colorID;
    private String header1;
    private String header2;
    private String header3;
    private String header4;
    private String header5;
    private String header6;

    public Contact() {
    }

    public Contact(int color, String header1, String header2, String header3, String header4, String header5, String header6) {
        this.colorID = color;
        this.header1 = header1;
        this.header2 = header2;
        this.header3 = header3;
        this.header4 = header4;
        this.header5 = header5;
        this.header6 = header6;
    }

    public int getColorID() {
        return colorID;
    }

    public String getHeader1() {
        return header1;
    }

    public String getHeader2() {
        return header2;
    }

    public String getHeader3() {
        return header3;
    }

    public String getHeader4() {
        return header4;
    }

    public String getHeader5() {
        return header5;
    }

    public String getHeader6() {
        return header6;
    }
}
