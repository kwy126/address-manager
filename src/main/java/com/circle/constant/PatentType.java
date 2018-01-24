package com.circle.constant;

public enum PatentType {

    /**
     * 申请类型
     */
    Apply(0),
    /**
     * 授权类型
     */
    Authorize(1),
    /**
     * 有效类型
     */
    Effection(2);

    private int type;
    private PatentType(int type) {
            this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
