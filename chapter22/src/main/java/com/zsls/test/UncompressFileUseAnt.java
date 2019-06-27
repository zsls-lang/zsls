package com.zsls.test;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

import java.io.*;

/**
 *@ClassName UncompressFileUseAnt
 *@Description TODO
 *@Version 1.0
 */
public class UncompressFileUseAnt {
	/**
	 * 构建目录
	 * @param outputDir

	 * @param subDir

	 */

	public static void createDirectory(String outputDir, String subDir) {
		File file = new File(outputDir);
		if (!(subDir == null || subDir.trim().equals(""))) {//子目录不为空
			file = new File(outputDir + "/" + subDir);
		}
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	/**
	 * 解压tar.gz 文件
	 * @param file 要解压的tar.gz文件对象

	 * @param outputDir 要解压到某个指定的目录下

	 * @throws IOException

	 */

	public static void unTarGz(File file, String outputDir) throws IOException {
		TarInputStream tarIn = null;
		try {
			tarIn = new TarInputStream(
					new MultiMemberGZIPInputStream(new BufferedInputStream(new FileInputStream(file))), 1024 * 2);

			createDirectory(outputDir, null);//创建输出目录
			TarEntry entry = null;
			while ((entry = tarIn.getNextEntry()) != null) {
				if (entry.isDirectory()) {//是目录
					createDirectory(outputDir, entry.getName());//创建空目录
				} else {//是文件
					File tmpFile = new File(outputDir + "/" + entry.getName());
					createDirectory(tmpFile.getParent() + "/", null);//创建输出目录
					OutputStream out = null;
					try {
						out = new FileOutputStream(tmpFile);
						int length = 0;
						byte[] b = new byte[2014];
						while ((length = tarIn.read(b)) != -1) {
							out.write(b, 0, length);
						}
					} catch (IOException ex) {
						throw ex;
					} finally {
						if (out != null) {
							out.close();
						}
					}
				}
			}
		} catch (IOException ex) {
			throw new IOException("解压归档文件出现异常", ex);
		} finally {
			try {
				if (tarIn != null) {
					tarIn.close();
				}
			} catch (IOException ex) {
				throw new IOException("关闭tarFile出现异常", ex);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		File file = new File("E:\\三盟文档\\西安理工\\172.10.255.18_online_detail-1561432214-5797146007712746.tmp.tar.gz");
		unTarGz(file,"E:\\三盟文档\\西安理工\\test");
	}
}