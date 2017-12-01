package com.circle.vo;

/**
 * Created by keweiyang on 2017/6/10.
 */
public enum DepartmentState {
    delete(-1,"删除"), normal(0, "正常");

    private int state;
    private String name;

    DepartmentState(int state, String name) {
        this.state = state;
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
