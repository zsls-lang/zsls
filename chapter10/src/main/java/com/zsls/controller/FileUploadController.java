package com.zsls.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@ClassName FileUploadController
 *@Description TODO
 *@Author zsls
 *@Date 2019/2/12 17:59
 *@Version 1.0
 */
@Controller
@RequestMapping("/uploads")
public class FileUploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	@GetMapping
	public String index() {
		return "index";
	}

	/**
	 * @Description
	 * // 报错 template might not exist or might not be accessible by any of the configured
	 * 需要增加@ResponseBody注解解决
	 * @return java.util.Map<java.lang.String , java.lang.String>
	 */
	@RequestMapping(value = "/upload1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload1(@RequestParam("file") MultipartFile file) throws IOException {
		LOGGER.info("contentType : {}", file.getContentType());
		LOGGER.info("originalFilename : {}", file.getOriginalFilename());
		LOGGER.info("size : {}", file.getSize());

		//TODO 将文件写到指定目录（具体开发中有可能是将文件写入到云存储或者指定目录通过 Nginx 进行 gzip 压缩和反向代理 这里测试先写入本地电脑指定目录）
		file.transferTo(new File("D:" + File.separator + "test" + File.separator + "chapter10" + File.separator + file
				.getOriginalFilename()));

		Map<String, String> result = new HashMap<>();
		result.put("contentType", file.getContentType());
		result.put("originalFilename", file.getOriginalFilename());
		result.put("size", file.getSize() + "");
		return result;
	}

	@PostMapping("/upload2")
	@ResponseBody
	public List<Map<String, String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
		if (null == files || files.length == 0) {
			return null;
		}

		List<Map<String, String>> result = new ArrayList<>();
		for (MultipartFile file : files) {
			file.transferTo(new File(
					"D:" + File.separator + "test" + File.separator + "chapter10" + File.separator + file
							.getOriginalFilename()));
			Map<String, String> fileInfoMap = new HashMap<>();
			fileInfoMap.put("contentType", file.getContentType());
			fileInfoMap.put("originalFilename", file.getOriginalFilename());
			fileInfoMap.put("size", file.getSize() + "");
			result.add(fileInfoMap);
		}
		return result;
	}

	@PostMapping("/upload3")
	@ResponseBody
	public void upload3(String base64) throws IOException {
		// TODO BASE64 方式的 格式和名字需要自己控制（如 png 图片编码后前缀就会是 data:image/png;base64,）
		final File tempFile = new File(
				"D:\\test\\chapter10\\test.jpg");
		// TODO 防止有的传了 data:image/png;base64, 有的没传的情况
		String[] d = base64.replace("\r\n","").split("base64,");
		final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
		FileCopyUtils.copy(bytes, tempFile);
	}



}
