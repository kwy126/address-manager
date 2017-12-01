package com.circle.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keweiyang on 2017/6/28.
 */
public class TreeNodeDto implements Serializable {

    private String text;
    private String id;

    private List<TreeNodeDto> nodes = new ArrayList<TreeNodeDto>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TreeNodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNodeDto> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "TreeNodeDto{" +
                "text='" + text + '\'' +
                ", id='" + id + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
