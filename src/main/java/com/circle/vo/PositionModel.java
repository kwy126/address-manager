package com.circle.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by keweiyang on 2017/6/27.
 */
public class PositionModel {

    private int poId;
    private String poDepartment;
    private String poName;
    private String poDescription;
    private String creator;
    private Timestamp createTime;
    private Timestamp timestamp;

    private String deptName;

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public String getPoDepartment() {
        return poDepartment;
    }

    public void setPoDepartment(String poDepartment) {
        this.poDepartment = poDepartment;
    }

    public String getPoName() {
        return poName;
    }

    public String getPoDescription() {
        return poDescription;
    }

    public void setPoDescription(String poDescription) {
        this.poDescription = poDescription;
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

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setPoName(String poName) {
        this.poName = poName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "PositionModel{" +
                "poId=" + poId +
                ", poDepartment='" + poDepartment + '\'' +
                ", poName='" + poName + '\'' +
                ", poDescription='" + poDescription + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", timestamp=" + timestamp +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
