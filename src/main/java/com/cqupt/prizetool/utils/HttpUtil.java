package com.cqupt.prizetool.utils;


import com.cqupt.prizetool.exception.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Component
@Slf4j
public class HttpUtil {

//    @Async("getAsyncExecutor")
    public  String httpRequestToString(String path, String method, String body) throws ValidException {
        if (path == null || method == null) {
            return null;
        }

        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;

        try {
            URL url = new URL(path);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);

            if (null != body) {
                OutputStream outputStream = null;
                try {
                    outputStream = conn.getOutputStream();
                    outputStream.write(body.getBytes("UTF-8"));

                }catch (Exception e){
                    log.error(e.getMessage());
//                    throw new ValidException("网络请求失败");
                    return "";
                }finally{
                    outputStream.close();
                }


            }
            // 将返回的输入流转换成字符串
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            response = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException execption) {
                execption.printStackTrace();
            }

        }
        return response;


    }

}
