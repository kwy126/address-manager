package com.circle.vo;

import java.util.ArrayList;
import java.util.List;

public class AddressCodeModel {
    private int id;
    private String name;
    private String region_code;
    private String parent_code;
    private String url;
    private String level;
    private String description;
    private int type;
    private int state;
    private String old_desc;


    private List<AddressCodeModel> childs = new ArrayList<AddressCodeModel>();
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        this.parent_code = parent_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AddressCodeModel> getChilds() {
        return childs;
    }

    public void setChilds(List<AddressCodeModel> childs) {
        this.childs = childs;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOld_desc() {
        return old_desc;
    }

    public void setOld_desc(String old_desc) {
        this.old_desc = old_desc;
    }
}