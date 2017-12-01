package com.circle.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by keweiyang on 2017/6/15.
 */
public class RoleModuleModel implements Serializable {
    private int id;
    private String roleLabel;
    private String moduleCode;
    private String mosuleSuperCode;
    private int finds;
    private int adds;
    private int deletes;
    private int modifys;
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getMosuleSuperCode() {
        return mosuleSuperCode;
    }

    public void setMosuleSuperCode(String mosuleSuperCode) {
        this.mosuleSuperCode = mosuleSuperCode;
    }

    public int getFinds() {
        return finds;
    }

    public void setFinds(int finds) {
        this.finds = finds;
    }

    public int getAdds() {
        return adds;
    }

    public void setAdds(int adds) {
        this.adds = adds;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }

    public int getModifys() {
        return modifys;
    }

    public void setModifys(int modifys) {
        this.modifys = modifys;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "RoleModuleModel{" +
                "id=" + id +
                ", roleLabel='" + roleLabel + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", mosuleSuperCode='" + mosuleSuperCode + '\'' +
                ", finds=" + finds +
                ", adds=" + adds +
                ", deletes=" + deletes +
                ", modifys=" + modifys +
                ", timestamp=" + timestamp +
                '}';
    }
}
