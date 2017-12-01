package com.circle.vo;

import java.util.Date;

public class AddressModel {
    private int id;
    private String request_number;
    private String request_date;
    private String requester_postcode;
    private String requester_address;
    private String file_entry_date;
    private String patent_type;
    private String requester_type;
    private String province;
    private String city;
    private String region;
    private String function_region;
    private String region_marked;
    private String function_region_marked;
    private String month;
    private String remark;
    private int type;//type=0表示申请，type=1表示授权，type=2表示有效
    private int state;//0表示为解析，1表示已解析
    private String channel;
    private int isRight;
    private int functionIsMatch;
    private String creator;
    private Date createTime;

    public int getFunctionIsMatch() {
        return functionIsMatch;
    }

    public void setFunctionIsMatch(int functionIsMatch) {
        this.functionIsMatch = functionIsMatch;
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

    public String getRequester_postcode() {
        return requester_postcode;
    }

    public void setRequester_postcode(String requester_postcode) {
        this.requester_postcode = requester_postcode;
    }

    public String getRequester_address() {
        return requester_address;
    }

    public void setRequester_address(String requester_address) {
        this.requester_address = requester_address;
    }

    public String getFile_entry_date() {
        return file_entry_date;
    }

    public void setFile_entry_date(String file_entry_date) {
        this.file_entry_date = file_entry_date;
    }

    public String getPatent_type() {
        return patent_type;
    }

    public void setPatent_type(String patent_type) {
        this.patent_type = patent_type;
    }

    public String getRequester_type() {
        return requester_type;
    }

    public void setRequester_type(String requester_type) {
        this.requester_type = requester_type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRegion_marked() {
        return region_marked;
    }

    public void setRegion_marked(String region_marked) {
        this.region_marked = region_marked;
    }

    public String getFunction_region_marked() {
        return function_region_marked;
    }

    public void setFunction_region_marked(String function_region_marked) {
        this.function_region_marked = function_region_marked;
    }

    public int getIsRight() {
        return isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}