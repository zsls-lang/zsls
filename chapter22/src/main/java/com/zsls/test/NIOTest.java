package com.sunmnet.bigdata.web.rcztsj;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test001 {
    public static void readWrite(File file) throws Exception{
        //读取文件
        FileInputStream inputStream = new FileInputStream(file);

        //输出文件
        FileOutputStream outputStream = new FileOutputStream("D:\\\\fuck.7z");

        //简历管道
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
        int read = 0 ;
        while((read = inputStreamChannel.read(buffer))!=-1){
            //传出数据
            buffer.flip();

            //从 Buffer 中读取数据 & 传出数据到通道
            outputStreamChannel.write(buffer);

            //重置缓冲区
            buffer.clear();

        }

    }


    public static void readWriteUtils(File file) throws Exception{
        String outFileName = "D:\\chaijin.exe";

        Path readPath = Paths.get(file.getPath());
        Path writePath = Paths.get(outFileName);
        //copy 文件
//        Files.copy(readPath,writePath);

        //读取文件
        Files.write(writePath,Files.readAllBytes(readPath));
    }



    public static void main(String[] args) throws Exception{

//        readWrite(new File("D:\\BaiduNetdiskDownload\\2.0\\2.0整理.7z"));

        readWriteUtils(new File("D:\\BaiduNetdiskDownload\\VMware-workstation-full-12.5.5-5234757.exe"));
    }
}
