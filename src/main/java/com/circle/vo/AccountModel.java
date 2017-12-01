package com.circle.vo;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by keweiyang on 2017/6/4.
 */
public class AccountModel {
    private int acctId;
    private String acctName;
    private String acctPassword;
    private int acctState;//帐号状态，-1->删除状态，0->正常状态
    private int acctSuper;//0-超级管理员，1-普通管理员
    private int acctDeleteState;
    private Date createTime;
    private String creator;
    private Timestamp timestamp;//乐观锁
    private String acctNickName;

    public int getAcctId() {
        return acctId;
    }

    public void setAcctId(int acctId) {
        this.acctId = acctId;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAcctPassword() {
        return acctPassword;
    }

    public void setAcctPassword(String acctPassword) {
        this.acctPassword = acctPassword;
    }

    public int getAcctState() {
        return acctState;
    }

    public void setAcctState(int acctState) {
        this.acctState = acctState;
    }

    public int getAcctSuper() {
        return acctSuper;
    }

    public void setAcctSuper(int acctSuper) {
        this.acctSuper = acctSuper;
    }

    public int getAcctDeleteState() {
        return acctDeleteState;
    }

    public void setAcctDeleteState(int acctDeleteState) {
        this.acctDeleteState = acctDeleteState;
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

    public String getAcctNickName() {
        return acctNickName;
    }

    public void setAcctNickName(String acctNickName) {
        this.acctNickName = acctNickName;
    }
}
