package com.circle.vo;


import java.sql.Timestamp;

/**
 * Created by keweiyang on 2017/6/8.
 */
public class ModuleModel {
    private int moduleId;
    private String moduleName;
    private String moduleCode;
    private String moduleSuperCode;
    private String modulePage;
    private int moduleLevel;
    private Timestamp timestamp;

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleSuperCode() {
        return moduleSuperCode;
    }

    public void setModuleSuperCode(String moduleSuperCode) {
        this.moduleSuperCode = moduleSuperCode;
    }

    public String getModulePage() {
        return modulePage;
    }

    public void setModulePage(String modulePage) {
        this.modulePage = modulePage;
    }

    public int getModuleLevel() {
        return moduleLevel;
    }

    public void setModuleLevel(int moduleLevel) {
        this.moduleLevel = moduleLevel;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ModuleModel{" +
                "moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", moduleSuperCode='" + moduleSuperCode + '\'' +
                ", modulePage='" + modulePage + '\'' +
                ", moduleLevel=" + moduleLevel +
                ", timestamp=" + timestamp +
                '}';
    }
}
