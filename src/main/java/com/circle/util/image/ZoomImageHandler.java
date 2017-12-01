package com.circle.util.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by keweiyang on 2017/6/18.
 */
public class ZoomImageHandler extends ImageHandler {
    /**
     * 缩放比例。只能是0-1的单浮点数
     */
    private float ratio = -1f;

    /**
     * 可能按照自定高宽裁剪
     */
    private int destWith;

    /**
     * 可能按照自定高宽裁剪
     */
    private int destHeight;

    /**
     * 构造函数，使用指定的比例进行图片缩放
     * @param nextHandler 可能存在的下一个处理器
     * @param ratio 指定的比例信息
     * */
    ZoomImageHandler(ImageHandler nextHandler , float ratio) {
        super(nextHandler);
        this.ratio = ratio;
    }

    /**
     * 构造函数，使用指定的宽和高进行图片缩放
     * @param nextHandler 可能存在的下一个处理器
     * @param destWith 指定的宽度
     * @param destHeight 指定的高度
     */
    ZoomImageHandler(ImageHandler nextHandler, int destWith, int destHeight) {
        super(nextHandler);
        this.destWith = destWith;
        this.destHeight = destHeight;
    }


    public BufferedImage dispose(BufferedImage srcImage) {
		/*
		 * 处理过程为
		 * 1、首先确定外部输入的是按比例缩放还是按照一个高宽数值缩放
		 * 2、如果是按照一个高宽数值缩放，则要首先计算一个缩放比例
		 * 3、构建一个新的画布，并按照指定的比例或者计算出来的比例进行缩放操作
		 * */
        int sourceWith = srcImage.getWidth();
        int sourceHeight = srcImage.getHeight();

        //得到合适的压缩大小，按比例。
        int localDestWith,localDestHeight;
        // 如果条件成立，说明是按照比例缩小
        if(ratio != -1) {
            localDestWith = Math.round((sourceWith * ratio));
            localDestHeight = Math.round((sourceHeight * ratio));
        }
        // 否则是按照输入的宽、高重新计算一个比例，再进行缩小
        else {
            float localRatio;
            // 如果发现输入的目标高宽大于图片的原始高宽，则按照ratio==1处理
            if(sourceWith <= this.destWith || sourceHeight <= this.destHeight) {
                localDestHeight = sourceHeight;
                localDestWith = sourceWith;
            }
            // 按照高计算
            else if(sourceWith > sourceHeight) {
                localRatio = new BigDecimal(this.destHeight).divide(new BigDecimal(sourceHeight), 2, RoundingMode.HALF_UP).floatValue();
                localDestHeight = (int)(sourceHeight * localRatio);
                localDestWith = (int)(sourceWith * localRatio);
            }
            // 否则按照宽计算
            else {
                localRatio = new BigDecimal(this.destWith).divide(new BigDecimal(sourceWith), 2, RoundingMode.HALF_UP).floatValue();
                localDestHeight = (int)(sourceHeight * localRatio);
                localDestWith = (int)(sourceWith * localRatio);
            }
        }

        // 快速缩放算法
        Image destImage = srcImage.getScaledInstance(localDestWith, localDestHeight, Image.SCALE_FAST);
        // RGB位深为24位，适合互联网显示
        BufferedImage outputImage = new BufferedImage(localDestWith, localDestHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = outputImage.getGraphics();
        graphics.drawImage(destImage, 0, 0, null);
        graphics.dispose();

        // 继续进行下一个处理
        BufferedImage nextResults = this.doNextHandler(outputImage);
        if(nextResults == null) {
            return outputImage;
        }
        return nextResults;
    }
}
