package com.circle.vo;

public class AuthorizeAddressModel {
    private int id;
    private String request_number;
    private String request_date;
    private String patenter_postcode;
    private String patenter_address;
    private String authorize_entry_data;
    private String patent_type;
    private String province;
    private String city;
    private String region;
    private String function_region;
    private String month;
    private String remark;
    private int state;

    @Override
    public String toString() {
        return "AuthorizeAddressModel{" +
                "id=" + id +
                ", request_number='" + request_number + '\'' +
                ", request_date='" + request_date + '\'' +
                ", patenter_postcode='" + patenter_postcode + '\'' +
                ", patenter_address='" + patenter_address + '\'' +
                ", authorize_entry_data='" + authorize_entry_data + '\'' +
                ", patent_type='" + patent_type + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", function_region='" + function_region + '\'' +
                ", month='" + month + '\'' +
                ", remark='" + remark + '\'' +
                ", state=" + state +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest_number() {
        return request_number;
    }

    public void setRequest_number(String request_number) {
        this.request_number = request_number;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getPatenter_postcode() {
        return patenter_postcode;
    }

    public void setPatenter_postcode(String patenter_postcode) {
        this.patenter_postcode = patenter_postcode;
    }

    public String getPatenter_address() {
        return patenter_address;
    }

    public void setPatenter_address(String patenter_address) {
        this.patenter_address = patenter_address;
    }

    public String getAuthorize_entry_data() {
        return authorize_entry_data;
    }

    public void setAuthorize_entry_data(String authorize_entry_data) {
        this.authorize_entry_data = authorize_entry_data;
    }

    public String getPatent_type() {
        return patent_type;
    }

    public void setPatent_type(String patent_type) {
        this.patent_type = patent_type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFunction_region() {
        return function_region;
    }

    public void setFunction_region(String function_region) {
        this.function_region = function_region;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}