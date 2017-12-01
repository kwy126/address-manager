package com.circle.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    public static void writeToFile(String content,String pathname) {
        File file = new File(pathname);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file,true);
            byte[] bs = content.getBytes();
            outputStream.write(content.getBytes());
            outputStream.write("\r\n".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
