package com.vishal.giddu.btp;

/**
 * Created by giddu on 8/9/16.
 */

public class RowElement {

    private String uniqueID;
    private String siteID;
    private String siteLocation;
    private String colour;
    private String odour;
    private String temp;
    private String ph;
    private String ec;
    private String p_do;
    private String no2no3;
    private String bod;
    private String total_coliforms;
    private String faecal_coliforms;


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


    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getOdour() {
        return odour;
    }

    public void setOdour(String odour) {
        this.odour = odour;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getP_do() {
        return p_do;
    }

    public void setP_do(String p_do) {
        this.p_do = p_do;
    }

    public String getNo2no3() {
        return no2no3;
    }

    public void setNo2no3(String no2no3) {
        this.no2no3 = no2no3;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getTotal_coliforms() {
        return total_coliforms;
    }

    public void setTotal_coliforms(String total_coliforms) {
        this.total_coliforms = total_coliforms;
    }

    public String getFaecal_coliforms() {
        return faecal_coliforms;
    }

    public void setFaecal_coliforms(String faecal_coliforms) {
        this.faecal_coliforms = faecal_coliforms;
    }
}
