package com.circle;

import java.util.List;

/**
 * @author KeWeiYang
 * @date 2018/3/5 9:33
 * TODO
 */
public class CniprPatentDto {
    private String status;
    private String message;
    private int total;
    private int from;
    private int to;
    private List<CniprPatent> list ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<CniprPatent> getList() {
        return list;
    }

    public void setList(List<CniprPatent> list) {
        this.list = list;
    }
}
