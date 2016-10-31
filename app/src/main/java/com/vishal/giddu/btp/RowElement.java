package com.vishal.giddu.btp;

/**
 * Created by giddu on 8/9/16.
 */

public class RowElement {

    private String uniqueID;
    private String siteID;
    private String siteLocation;
    private String parameter;


    public RowElement() {
    }


    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

    public String getParamter() {
        return parameter;
    }

    public void setParamter(String parameter) {
        this.parameter = parameter;
    }
}
