package com.circle.constant;

public enum PostCode {

    /**
     *
     */
    HaiShu(3150),
    JiangBei(3150),
    /**
     *
     */
    BeiLun(3158),
    /**
     *
     */
    YinZhou(3151),
    XianShan(3157),
    NingHai(3156),
    CiXi(3153),
    FengHua(3155),
    YuYao(3154),
    ZhenHai(3152);

    private int postCode;
    private PostCode(int postCode) {
            this.postCode = postCode;
    }

    public int getPostCode() {
        return this.postCode;
    }
}
