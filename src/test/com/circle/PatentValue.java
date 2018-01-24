package com.circle;

public class PatentValue {
    private int id;
    private String patent_id;
    private String patent_number;
    private int assignee;
    private int market_attractiveness;
    private int market_coverage;
    private int legal;
    private int technology;
    private int patent_value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatent_id() {
        return patent_id;
    }

    public void setPatent_id(String patent_id) {
        this.patent_id = patent_id;
    }

    public String getPatent_number() {
        return patent_number;
    }

    public void setPatent_number(String patent_number) {
        this.patent_number = patent_number;
    }

    public int getAssignee() {
        return assignee;
    }

    public void setAssignee(int assignee) {
        this.assignee = assignee;
    }


    public int getMarket_coverage() {
        return market_coverage;
    }

    public void setMarket_coverage(int market_coverage) {
        this.market_coverage = market_coverage;
    }



    public int getTechnology() {
        return technology;
    }

    public void setTechnology(int technology) {
        this.technology = technology;
    }

    public int getPatent_value() {
        return patent_value;
    }

    public void setPatent_value(int patent_value) {
        this.patent_value = patent_value;
    }

    public int getMarket_attractiveness() {
        return market_attractiveness;
    }

    public void setMarket_attractiveness(int market_attractiveness) {
        this.market_attractiveness = market_attractiveness;
    }

    public int getLegal() {
        return legal;
    }

    public void setLegal(int legal) {
        this.legal = legal;
    }
}
