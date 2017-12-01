package com.circle.util.image;


import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by keweiyang on 2017/6/18.
 */
public abstract class ImageHandlerBuilder {

    /**
     * 图片处理规则的创建器
     * @author yinwenjie
     */
    public static class Builder {

        private List<ImageHandlerDescribe> handlerDescribes = new LinkedList<ImageHandlerDescribe>();

        /**
         * 设定一个需要创建的图片裁剪处理器，并设置裁剪比例。
         * @param ratio 裁剪比例是一个0-1的浮点数，数值大于0小于1
         */
        public Builder createCutHandler(float ratio) {
            ImageHandlerDescribe describe = new ImageHandlerDescribe();
            describe.setRatio(ratio);
            describe.setImageHandlerClass("yinwenjie.sample.image.service.CutImageHandler");
            handlerDescribes.add(describe);
            return this;
        }

        /**
         * 设定一个需要创建的图片裁剪处理器，并设置裁剪高宽像素值
         * @param width 宽度像素值
         * @param height 高度像素值
         */
        public Builder createCutHandler(int width , int height) {
            ImageHandlerDescribe describe = new ImageHandlerDescribe();
            describe.setWidth(width);
            describe.setHeight(height);
            describe.setImageHandlerClass("yinwenjie.sample.image.service.CutImageHandler");
            handlerDescribes.add(describe);
            return this;
        }

        /**
         * 设定一个需要创建的字符形式的图片水印处理器
         * @param markValue 水印字符串
         */
        public Builder createMarkHandler(String markValue) {
            ImageHandlerDescribe describe = new ImageHandlerDescribe();
            describe.setMarkValue(markValue);
            describe.setImageHandlerClass("yinwenjie.sample.image.service.MarkStringImageHandler");
            handlerDescribes.add(describe);
            return this;
        }

        /**
         * 设定一个需要创建的图片缩放处理器，按照设定的比例进行缩放
         * @param ratio 缩放比例是一个0-1的浮点数，数值大于0小于1
         */
        public Builder createZoomHandler(float ratio) {
            ImageHandlerDescribe describe = new ImageHandlerDescribe();
            describe.setRatio(ratio);
            describe.setImageHandlerClass("yinwenjie.sample.image.service.ZoomImageHandler");
            handlerDescribes.add(describe);
            return this;
        }

        /**
         * 该方法用于标记一个事实：需要创建一个图片缩放处理器
         * @param width 指定缩放的宽度
         * @param height 指定缩放的高度
         */
        public Builder createZoomHandler(int width , int height) {
            ImageHandlerDescribe describe = new ImageHandlerDescribe();
            describe.setWidth(width);
            describe.setHeight(height);
            describe.setImageHandlerClass("yinwenjie.sample.image.service.ZoomImageHandler");
            handlerDescribes.add(describe);
            return this;
        }

        public ImageHandler build() {
            return this.nextImageHandler(0);
        }

        /**
         * 递归创建处理器
         * @param index 当前正在创建的处理器在handlerDescribes中的位置
         */
        private ImageHandler nextImageHandler(int index) {
            if(index + 1 > handlerDescribes.size()) {
                return null;
            }
            ImageHandlerDescribe describe = handlerDescribes.get(index);

            if(StringUtils.equals(describe.getImageHandlerClass(), "yinwenjie.sample.image.service.CutImageHandler")) {
                if(describe.getRatio() == null) {
                    return new CutImageHandler(this.nextImageHandler(++index), describe.getWidth() , describe.getHeight());
                } else {
                    return new CutImageHandler(this.nextImageHandler(++index), describe.getRatio());
                }
            } else if(StringUtils.equals(describe.getImageHandlerClass(), "yinwenjie.sample.image.service.MarkStringImageHandler")) {
                return new MarkStringImageHandler(this.nextImageHandler(++index), describe.getMarkValue());
            } else if(StringUtils.equals(describe.getImageHandlerClass(), "yinwenjie.sample.image.service.ZoomImageHandler")) {
                if(describe.getRatio() == null) {
                    return new ZoomImageHandler(this.nextImageHandler(++index), describe.getWidth() , describe.getHeight());
                } else {
                    return new ZoomImageHandler(this.nextImageHandler(++index), describe.getRatio());
                }
            }

            // 执行到这里，实际上是dead code
            return null;
        }
    }

    /**
     * 将要实例化的对象
     */
    private static class ImageHandlerDescribe {
        /**
         * 处理的比例描述
         */
        private Float ratio;
        /**
         * 处理的宽度描述
         */
        private Integer width;
        /**
         * 处理的高度描述
         */
        private Integer height;
        /**
         * 可能的水印信息
         */
        private String markValue;
        /**
         * 需要实例化的具体图片处理器
         */
        private String imageHandlerClass;
        Float getRatio() {
            return ratio;
        }
        void setRatio(Float ratio) {
            this.ratio = ratio;
        }
        Integer getWidth() {
            return width;
        }
        void setWidth(Integer width) {
            this.width = width;
        }
        Integer getHeight() {
            return height;
        }
        void setHeight(Integer height) {
            this.height = height;
        }
        String getImageHandlerClass() {
            return imageHandlerClass;
        }
        String getMarkValue() {
            return markValue;
        }
        void setMarkValue(String markValue) {
            this.markValue = markValue;
        }
        void setImageHandlerClass(String imageHandlerClass) {
            this.imageHandlerClass = imageHandlerClass;
        }
    }

}
