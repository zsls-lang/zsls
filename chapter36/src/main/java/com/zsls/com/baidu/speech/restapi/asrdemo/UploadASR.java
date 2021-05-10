/**
 * Company
 * Copyright (C) 2004-2021 All Rights Reserved.
 */
package com.zsls.com.baidu.speech.restapi.asrdemo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zsls
 * @version $Id UploadASR.java, v 0.1 2021-04-06 16:40  Exp $$
 */
@Controller
public class UploadASR {

    private static final String WAV_PATH= File.separatorChar+"app"+File.separatorChar+"yuyin"+File.separatorChar;
    public static final String TEST_WAV = "test.wav";

    @RequestMapping("/upload")
    public String speechRecognize(HttpServletRequest request) throws Exception{
        request.setCharacterEncoding("UTF-8");

        String blob_base64 = request.getParameter("upfileWavB64");
        byte[] bytes_wav = ApiUtils.Convert_Base64ToBytes(blob_base64);

        // 文件上传 查看是否又文件
        String outFileName = WAV_PATH+File.separatorChar+ TEST_WAV;
        File dir = new File(WAV_PATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(outFileName);
        if(file.exists()){
            if(!file.delete()){
                throw new Exception(outFileName+"删除文件失败");
            }
        }
        Path writePath = Paths.get(outFileName);
        //上传语音到服务器
        Files.write(writePath, bytes_wav);

        //解析语音
        String token = "qwertasd";
        final String result = ApiUtils.SendToASRTServer(token);
        System.out.println("result:::::"+result);

        return result;
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/index11")
    public String index11(){
        return "index11";
    }


}
