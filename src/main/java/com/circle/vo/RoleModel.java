package com.circle.vo;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by keweiyang on 2017/6/12.
 */
public class RoleModel {
    private int roleId;
    private String roleName;
    private String roleDescription;
    private String roleLabel;
    private Date createTime;
    private String creator;
    private Timestamp timestamp;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "RoleModel{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", roleLabel='" + roleLabel + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
