package com.circle.vo;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by keweiyang on 2017/6/4.
 */
public class StaticsModel {

    private int id;
    private String region;
    private int invention_number;
    private int use_number;
    private int design_number;
    private int sum;
    private Date create_date;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getInvention_number() {
        return invention_number;
    }

    public void setInvention_number(int invention_number) {
        this.invention_number = invention_number;
    }

    public int getUse_number() {
        return use_number;
    }

    public void setUse_number(int use_number) {
        this.use_number = use_number;
    }

    public int getDesign_number() {
        return design_number;
    }

    public void setDesign_number(int design_number) {
        this.design_number = design_number;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
