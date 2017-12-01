package com.circle.util.image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by keweiyang on 2017/6/18.
 */
public class MarkStringImageHandler extends ImageHandler {
    /**
     * 文字形式的水印
     */
    private String markValue;

    /**
     * 构造函数
     * @param nextHandler 可能存在的下一个处理器
     * @param markValue 水印的文字信息
     */
    public MarkStringImageHandler(ImageHandler nextHandler , String markValue) {
        super(nextHandler);
        this.markValue = markValue;
    }

    public BufferedImage dispose(BufferedImage srcImage) {
        int sourceHeight = srcImage.getHeight();

        // 这是画笔，由于不需要进行图片大小、宽高的改变。这里直接在原来的BufferedImage上绘画就行了
        Graphics graphics = srcImage.getGraphics();
        // 得到画笔，准备在原图片上添加水印
        // 处理得相对简单一些，正常生产环境下，还可以设置透明图，位置等等
        graphics.setFont(new Font("宋体", Font.BOLD, 10));
        graphics.drawString(markValue, 0, sourceHeight / 2);
        // 开始处理
        graphics.dispose();

        // 继续进行下一个处理
        BufferedImage nextResults = this.doNextHandler(srcImage);
        if(nextResults == null) {
            return srcImage;
        }
        return nextResults;
    }
}
