package com.zsls.test;
import com.zsls.test.MultiMemberGZIPInputStream;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

public class UncompressFileTAR {
    
    public static final String EXT = ".tar.gz";
    
    /**
     * destcname 为需要输出的位置
	 * fis 为 输入的文件流
     * */
    public static String decompress(InputStream fis,String destcname) {


        //begin decompress
        String fileName =destcname;
//        FileInputStream fis ;
        ArchiveInputStream in = null;
		MultiMemberGZIPInputStream is = null;
        BufferedInputStream bufferedInputStream = null ;
        BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
        BufferedWriter bufferedWriter = null;
		OutputStreamWriter outputStreamWriter = null;
		FileOutputStream fileOutputStream = null;
        try {
//            fis = new FileInputStream(filed);
			bufferedInputStream = new BufferedInputStream(fis);
			is = new MultiMemberGZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is,"UTF-8");
            
//            String outFileName = getFileName(pathname);
			inputStreamReader=new InputStreamReader(in,"UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
            TarArchiveEntry entry = (TarArchiveEntry)in.getNextEntry();
            while(entry != null) {
                String name = entry.getName();
                String[] names = name.split("/");
//                fileName = outFileName;
                for(int i = 0; i < names.length; i++) {
                    String str = names[i];
                    fileName = fileName + File.separator + str;
                  
                }
                if(name.endsWith("/")) {
                    mkFolder(fileName);
                } else {
                    File file = mkFile(fileName);
                    System.out.println(fileName);
					fileOutputStream=new FileOutputStream(file);
					outputStreamWriter=new OutputStreamWriter(fileOutputStream,"UTF-8");
					bufferedWriter = new BufferedWriter(outputStreamWriter);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
//						bufferedWriter.write(line);
						bufferedWriter.write(line+"/r/n");
					}
					bufferedWriter.flush();
					bufferedWriter.close();
					outputStreamWriter.close();
					fileOutputStream.close();

                }
                entry = (TarArchiveEntry)in.getNextEntry();
            }
          
            System.out.println("解压后的文件名称："+ fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArchiveException e) {
            e.printStackTrace();
        } finally {
			IOUtils.closeQuietly(bufferedWriter);
			IOUtils.closeQuietly(outputStreamWriter);
			IOUtils.closeQuietly(fileOutputStream);
			IOUtils.closeQuietly(bufferedReader);
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(bufferedInputStream);
        }
        return fileName;
    }
    
    private static void mkFolder(String fileName) {
        File f = new File(fileName);
        if(!f.exists()) {
            f.mkdirs();
        }
    }
    
    private static File mkFile(String fileName) {
        File f = new File(fileName);
        try {
        f.getParentFile().mkdirs();
            f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    
    public static String getFileName(String f) {    
        String fname = "";    
        int i = f.lastIndexOf('.');    
  
        if (i > 0 &&  i < f.length() - 1) {    
            fname = f.substring(0,i-4);    
        }         
        return fname;    
    }    


}