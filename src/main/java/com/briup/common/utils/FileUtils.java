package com.briup.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtils {

    private static File file;



    public static void readAndWrite(InputStream in, OutputStream out) throws IOException {
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b);
        }
        in.close();
        out.flush();
        out.close();
    }

    public FileUtils() {
    }

    public static File getFile() {
        if (null == file) {
            String filePath = System.getProperty("user.dir");
            file = new File(filePath + "\\photo");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static String getFilePath() {
        if (null == file) {
            String filePath = System.getProperty("user.dir");
            file = new File(filePath + "\\photo");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file.getPath();
    }

    public static boolean deleteFile(File file){
        if (file.exists()){
            return  file.delete();
        }
        return false;
    }

    public static boolean deleteFile(String fileName){
        File file = new File(getFilePath().concat("\\"+fileName));
        if (file.exists()){
            return  FileUtils.file.delete();
        }
        return false;
    }
}
