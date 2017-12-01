package com.circle.vo;


import java.sql.Timestamp;


/**
 * Created by keweiyang on 2017/6/8.
 */
public class DepartmentModel {
    private int deptId;
    private String deptName;
    private Timestamp createTime;
    private String creator;
    private String deptDescription;
    private int deptPrincipal;
    private Timestamp timestamp;

    @Override
    public String toString() {
        return "DepartmentModel{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", deptDescription='" + deptDescription + '\'' +
                ", deptPrincipal=" + deptPrincipal +
                ", timestap=" + timestamp +
                '}';
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDeptDescription() {
        return deptDescription;
    }

    public void setDeptDescription(String deptDescription) {
        this.deptDescription = deptDescription;
    }

    public int getDeptPrincipal() {
        return deptPrincipal;
    }

    public void setDeptPrincipal(int deptPrincipal) {
        this.deptPrincipal = deptPrincipal;
    }

    public Timestamp getTimestap() {
        return timestamp;
    }

    public void setTimestap(Timestamp timestap) {
        this.timestamp = timestap;
    }

    public DepartmentModel(String deptName, Timestamp createTime, String creator, String deptDescription, int deptPrincipal, Timestamp timestamp) {
        this.deptName = deptName;
        this.createTime = createTime;
        this.creator = creator;
        this.deptDescription = deptDescription;
        this.deptPrincipal = deptPrincipal;
        this.timestamp = timestamp;
    }

    public DepartmentModel() {
    }
}
