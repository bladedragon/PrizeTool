package com.cqupt.prizetool.utils.access_token;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;




import java.net.URL;
import java.sql.SQLException;

@Component
@Slf4j
public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisTokenHelper redisTokenHelper = new RedisTokenHelper();
    @Value("${WX_APPID}")
    private String WX_APPID;
    @Value("${WX_APPSECRET}")
    private String WX_APPSECRET;
    @Value("${GET_ACCESSTOKEN_URL}")
    private String GET_ACCESSTOKEN_URL;
    /**
     * 定时获取access_token
     *
     * @throws SQLException
     */
    @Scheduled(fixedDelay = 7180000)
    public String getAccessTokenFromURL() {
        logger.info("==============开始获取access_token===============");
        String access_token = null;
        String grant_type = "client_credential";
        String AppId = WX_APPID;
        String secret = WX_APPSECRET;
        String url = GET_ACCESSTOKEN_URL+ grant_type + "&appid=" + AppId + "&secret=" + secret;
        logger.info("getUrl==>" + url);

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            // 必须是get方式请求
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println(demoJson.toString());
            access_token = demoJson.getString("access_token");
            logger.info("get access_token==>" + access_token);
            is.close();
            System.out.println("==============结束获取access_token===============");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("==============开始写入access_token===============");
        redisTokenHelper.save("global_token", access_token);

        System.out.println("==============写入access_token成功===============");
        return access_token;
    }

    public String getAccess_Token() {
        String access_token = redisTokenHelper.getObject("global_token");
        if (access_token.equals("1")) {
            log.info("ZLOG=> no ACCESS_TOKEN cache");
            getAccessTokenFromURL();
            access_token = redisTokenHelper.getObject("global_token");
        }
        return access_token;
    }
}


