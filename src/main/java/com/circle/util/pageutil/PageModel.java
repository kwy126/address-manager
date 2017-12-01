package com.circle.util.pageutil;

import java.io.Serializable;

/**
 * Created by keweiyang on 2017/6/11.
 */
public class PageModel implements Serializable {
    private int count;//总数
    private int totalPage;//总页数
    private int nowPage;//当前页数
    private int number;//每页数量

    @Override
    public String toString() {
        return "PageModel{" +
                "count=" + count +
                ", totalPage=" + totalPage +
                ", nowPage=" + nowPage +
                ", number=" + number +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
