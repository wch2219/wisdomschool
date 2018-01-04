package com.dlwx.baselib.utiles;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class FileOperation {

    /**
     * get file size 通过URL地址获取文件大小
     * @param filePath * url file path
     * @return filesize
     * @throws MalformedURLException
     */
    public static String getFileSize(String filePath) throws MalformedURLException {
        HttpURLConnection urlcon = null;
        String size="";
        //format double
        java.text.DecimalFormat fnum = new java.text.DecimalFormat("#0.000");
        //create url link
        URL url=new URL(filePath);
        try {
            //open url
            urlcon = (HttpURLConnection)url.openConnection();
            //get url properties
            double filesize=urlcon.getContentLength();
            //format output
            size=fnum.format(filesize/1024);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //close connect
            urlcon.disconnect();
        }
        return size;
    }
}
