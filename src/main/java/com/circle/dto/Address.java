package com.circle.dto;

import com.circle.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Address implements Serializable{
    private String suggestion;
    private String count;
    private String infocode;
    private String status;
    private String info;

    private List<Position> pois = new ArrayList<Position>();

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List getPois() {
        return pois;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setPois(List<Position> pois) {
        this.pois = pois;
    }

    @Override
    public String toString() {
        return "Address{" +
                "suggestion='" + suggestion + '\'' +
                ", count='" + count + '\'' +
                ", infocode='" + infocode + '\'' +
                ", status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", pois=" + pois +
                '}';
    }
}
