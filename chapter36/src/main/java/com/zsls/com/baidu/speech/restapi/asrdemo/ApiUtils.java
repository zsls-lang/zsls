package com.zsls.com.baidu.speech.restapi.asrdemo;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
//import java.util.UUID;

public class ApiUtils {
	private static final String UTF_8 = "utf-8";

	public static byte[] Convert_Base64ToBytes(String str_base64) {
		try {
			byte[] decode = Base64.getDecoder().decode(str_base64.getBytes(UTF_8));
			return decode;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String Convert_BytesToBase64(byte[] BytesData) {
		String basicEncoded = Base64.getEncoder().encodeToString(BytesData);
		//String urlEncoded = Base64.getUrlEncoder().encodeToString(ordinal.getBytes(UTF_8));
		//URL安全的base64编码器
		return basicEncoded;
	}

	public static String SendToASRTServer(String token) throws Exception{
		OkHttpClient client = new OkHttpClient.Builder()
			.connectTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(60, TimeUnit.SECONDS)
			.readTimeout(60, TimeUnit.SECONDS)
			.build();
		RequestBody requestBody = RequestBody.Companion.create("token="+token, MediaType.parse("application/json; "
			+ "charset=utf-8"));

		Request build = new Request.Builder()
			.url("http://yourIP:22222/asrt/v1/")
			.removeHeader("user-agent")
			.addHeader("connection","Keep-Alive")
			.addHeader("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
			.post(requestBody)
			.build();
		String result = "";
		try(Response response = client.newCall(build).execute()){
			if(response.isSuccessful()){
				result=response.body().string();
			}else{
				throw new Exception("Unexpected code " + response);
			}
		}
		return result;
	}

}
