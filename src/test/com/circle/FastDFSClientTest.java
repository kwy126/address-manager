package com.circle;

import com.circle.utils.fdfs.FastDFSClient;
import com.circle.utils.io.FileUtil;
import com.circle.utils.io.IOUtil;
import com.circle.utils.io.URLResourceUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by keweiyang on 2017/12/28.
 */
public class FastDFSClientTest {

    /**
     * 上传测试.
     * @throws Exception
     */
    @Test
    public  void upload() throws Exception {
        File file = URLResourceUtil.asFile("classpath://logo.png");
        String fileId = FastDFSClient.uploadFile(file,"mobike");
        System.out.println("Upload local file " + "mobike" + " ok, fileid=" + fileId);
        // fileId:	group1/M00/00/00/wKgEfVUYPieAd6a0AAP3btxj__E335.jpg
        // url:	http://192.168.4.125:8888/group1/M00/00/00/wKgEfVUYPieAd6a0AAP3btxj__E335.jpg

        //group1/M00/00/00/rBAADFpE5mqAKRPUAA57EXU0yCY0794429
        //group1/M00/00/00/rBAADFpE2s-ATceXAA57EXU0yCY5022927
    }

    /**
     * 下载测试.
     * @throws Exception
     */
    @Test
    public  void download() throws Exception {
        String fileId = "group1/M00/00/00/rBAADFpE5mqAKRPUAA57EXU0yCY0794429";
        InputStream inputStream = FastDFSClient.downloadFile(fileId);

        boolean isExist = FileUtil.isFileExists("2.png");
        if (!isExist) {
        }

        File destFile = URLResourceUtil.asFile("classpath://2.png");
        FileUtils.copyInputStreamToFile(inputStream, destFile);
    }

    /**
     * 删除测试
     * @throws Exception
     */
    public static void delete() throws Exception {
        String fileId = "group1/M00/00/00/wKgEfVUYPieAd6a0AAP3btxj__E335.jpg";
        int result = FastDFSClient.deleteFile(fileId);
        System.out.println(result == 0 ? "删除成功" : "删除失败:" + result);
    }



 /*   *//**
     * @param args
     * @throws Exception
     *//*
    public static void main(String[] args) throws Exception {
        upload();
        //download();
        //delete();*/

}
