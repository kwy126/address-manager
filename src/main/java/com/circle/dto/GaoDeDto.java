package com.circle.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keweiyang on 2017/12/26.
 */
public class GaoDeDto {


    private String status;
    private String info;
    private String infocode;
    private List<Regeocode> geocodes;

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

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }


    public List<Regeocode> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<Regeocode> geocodes) {
        this.geocodes = geocodes;
    }
}



