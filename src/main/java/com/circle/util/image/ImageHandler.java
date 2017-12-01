package com.circle.util.image;

import java.awt.image.BufferedImage;

/**
 * Created by keweiyang on 2017/6/18.
 */
public abstract class ImageHandler {

    /**
     * 下一个处理器
     */
    private ImageHandler nextHandler;

    ImageHandler(ImageHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * 进行下一个处理器的处理
     * @param srcImage 图片文件数据
     * @return 如果没有下一个处理器了，则返回null，其他情况下返回下一个处理器的处理的数据流结果
     */
    BufferedImage doNextHandler(BufferedImage srcImage) {
        if(this.nextHandler != null) {
            return this.nextHandler.dispose(srcImage);
        }

        return null;
    }

    /**
     * 这个dispose方法，就是子类需要主要实现的方式。<br>
     * 如果处理过程中，不需要变更画布的尺寸，则可以在处理后将srcImage代表的画布直接返回<br>
     * 如果在处理过程中，需要变更画布尺寸，则可以在实现的方法中创建一个新的画布，并进行返回
     * @param srcImage 从上一个处理器传来的处理过得图片信息（画布信息）
     * @return 处理完成后一定要返回一个画布。
     */
    public abstract BufferedImage dispose(BufferedImage srcImage);
}
