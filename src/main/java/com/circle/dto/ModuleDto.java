package com.circle.dto;

import java.io.Serializable;

/**
 * Created by keweiyang on 2017/6/15.
 */
public class ModuleDto implements Serializable{

    private long id;
    private String name;
    private String code;
    private String superCode;
    private String url;
    private int level;
    private boolean find;
    private boolean del;
    private boolean modify;
    private boolean add;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuperCode() {
        return superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isFind() {
        return find;
    }

    public void setFind(boolean find) {
        this.find = find;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    @Override
    public String toString() {
        return "ModuleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", superCode='" + superCode + '\'' +
                ", url='" + url + '\'' +
                ", level=" + level +
                ", find=" + find +
                ", del=" + del +
                ", modify=" + modify +
                ", add=" + add +
                '}';
    }
}
