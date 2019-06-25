package com.zsls;

import com.zsls.test.MultiMemberGZIPInputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class Chapter22Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter22Application.class, args);
	}

	public void test() throws IOException {
		FileInputStream fis = new FileInputStream("");
		new BufferedReader(new InputStreamReader(new MultiMemberGZIPInputStream(fis),"UTF-8"));
	}

	public static void saveAsUTF8(String inputFileUrl, String outputFileUrl) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(inputFileUrl)));
		BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFileUrl), "UTF-8"));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			bufferedWriter.write(line + "\r\n");
		}
		bufferedWriter.close();
		bufferedReader.close();
	}
}
