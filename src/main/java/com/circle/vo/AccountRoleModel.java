package com.circle.vo;


import java.sql.Timestamp;

/**
 * Created by keweiyang on 2017/6/13.
 */
public class AccountRoleModel  {

    private int aoId;
    private String acctName;
    private String roleLabel;
    private Timestamp timestamp;

   /* public AccountRoleModel(String acctName, String roleLabel, Timestamp timestamp) {
        this.acctName = acctName;
        this.roleLabel = roleLabel;
        this.timestamp = timestamp;
    }*/

    @Override
    public String toString() {
        return "AccountRoleModel{" +
                "aoId=" + aoId +
                ", acctName='" + acctName + '\'' +
                ", roleLabel='" + roleLabel + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public int getAoId() {
        return aoId;
    }

    public void setAoId(int aoId) {
        this.aoId = aoId;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
