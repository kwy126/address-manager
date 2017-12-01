package com.circle.util.image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by keweiyang on 2017/6/18.
 */
public class CutImageHandler extends ImageHandler {

    /**
     * 可能按照比例裁剪。只能是0-1的单浮点数
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
     * 构造函数，使用比例进行图片的裁剪
     * @param nextHandler 可能存在的下一个处理器
     * @param ratio 指定的图片裁剪比例
     */
    public CutImageHandler(ImageHandler nextHandler , float ratio) {
        super(nextHandler);
        this.ratio = ratio;
    }

    /**
     * 构造函数，使用指定的宽和高进行图片的裁剪
     * @param nextHandler 可能存在的下一个处理器
     * @param destWith 指定的宽像素
     * @param destHeight 指定的高像素
     */
    CutImageHandler(ImageHandler nextHandler , int destWith , int destHeight) {
        super(nextHandler);
        this.destWith = destWith;
        this.destHeight = destHeight;
    }


    public BufferedImage dispose(BufferedImage srcImage) {
        int sourceWith = srcImage.getWidth();
        int sourceHeight = srcImage.getHeight();

        // 计算裁剪大小和裁剪
        int localDestWith,localDestHeight;
        // 如果条件成立，说明是按照比例裁剪
        // 如果发现输入的目标高宽大于图片的原始高宽，则按照ratio==1处理
        if(sourceWith <= this.destWith || sourceHeight <= this.destHeight) {
            localDestHeight = sourceHeight;
            localDestWith = sourceWith;
        }
        // 如果有比例，则按照比例进行计算
        else if(ratio != -1) {
            localDestWith = Math.round((sourceWith * ratio));
            localDestHeight = Math.round((sourceHeight * ratio));
        }
        // 否则是按照输入的宽、高进行裁剪
        else {
            localDestWith = destWith;
            localDestHeight = destHeight;
        }
        BufferedImage cutImage = srcImage.getSubimage((sourceWith - localDestWith) / 2, (sourceHeight - localDestHeight) / 2, localDestWith, localDestHeight);

        // 这是目标图片
        BufferedImage outputImage = new BufferedImage(localDestWith, localDestHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = outputImage.getGraphics();
        graphics.drawImage(cutImage, 0, 0, null);
        // 开始处理
        graphics.dispose();

        // 继续进行下一个处理
        BufferedImage nextResults = this.doNextHandler(outputImage);
        if(nextResults == null) {
            return outputImage;
        }
        return nextResults;
    }

}
