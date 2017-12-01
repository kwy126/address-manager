package com.circle.dto;

import java.io.Serializable;

/**
 * Created by keweiyang on 2017/6/13.
 */
public class RoleDto implements Serializable{
    private long id;
    private String acctName;
    private String roleName;
    private boolean opt;

    public RoleDto() {
        // TODO Auto-generated constructor stub
    }

    public RoleDto(long id, String acctName, String roleName, boolean opt) {
        super();
        this.id = id;
        this.acctName = acctName;
        this.roleName = roleName;
        this.opt = opt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isOpt() {
        return opt;
    }

    public void setOpt(boolean opt) {
        this.opt = opt;
    }

    @Override
    public String toString() {
        return "RoleDto [id:" + id + ", acctName:" + acctName + ", roleName:" + roleName + ", opt:" + opt + "]";
    }
}
