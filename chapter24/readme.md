1.服务器创建目录 /var/lib/testweb
2.项目打包 mvn package 
3.通过ftp工具把打好的包传递到 /var/lib/testweb
4.在jar包的同一目录 及/var/lib/testweb目录下创建文件 touch Dockerfile
vim Dockerfile
FROM java:8
EXPOSE 8080
VOLUME /tmp
ADD chapter24-0.0.1-SNAPSHOT.jar mytestapp.jar
RUN sh -c 'touch /mytestapp.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mytestapp.jar" ]

##############Dockerfile################
FROM 基础镜像必要，代表你的项目将构建在这个基础上面
EXPOSE 允许指定端口转发
VOLUME 创建一个可以从本地主机或其他容器挂载的挂载点，一般用来存放数据库和需要保持的数据等。
       指定了临时文件目录为/tmp。其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp。改步骤是可选的，如果涉及到文件系统的应用就很有必要了。/tmp目录用来持久化到 Docker 数据文件夹，因为 Spring Boot 使用的内嵌 Tomcat 容器默认使用/tmp作为工作目录 
       项目的 jar 文件作为 “mytestapp.jar” 添加到容器的
ADD 将文件从路径
ENV 可以用于为docker容器设置环境变量
ENTRYPOINT 指定 Docker image 运行成 instance (也就是 Docker container) 时，要执行的命令或者文件
            执行项目 mytestapp.jar。为了缩短 Tomcat 启动时间，添加一个系统属性指向 “/dev/urandom” 作为 Entropy Source
##############Dockerfile################

5.docker build -t mytestapp .
  -t boot-docker 代表你要构建的名字 **看清楚后面有一个点的**
结果如下：
[root@zsls testweb]# docker build -t mytestapp .
Sending build context to Docker daemon  16.79MB
Step 1/7 : FROM java:8
8: Pulling from library/java
5040bd298390: Pull complete 
fce5728aad85: Pull complete 
76610ec20bf5: Pull complete 
60170fec2151: Pull complete 
e98f73de8f0d: Pull complete 
11f7af24ed9c: Pull complete 
49e2d6393f32: Pull complete 
bb9cdec9c7f3: Pull complete 
Digest: sha256:c1ff613e8ba25833d2e1940da0940c3824f03f802c449f3d1815a66b7f8c0e9d
Status: Downloaded newer image for java:8
 ---> d23bdf5b1b1b
Step 2/7 : EXPOSE 8080
 ---> Running in c5118f4623c2
Removing intermediate container c5118f4623c2
 ---> 74762f6ae4cb
Step 3/7 : VOLUME /tmp
 ---> Running in 3122b2bb486d
Removing intermediate container 3122b2bb486d
 ---> e0c8ecda2d7a
Step 4/7 : ADD chapter24-0.0.1-SNAPSHOT.jar mytestapp.jar
 ---> 7137e31f5ce0
Step 5/7 : RUN sh -c 'touch /mytestapp.jar'
 ---> Running in 5243748a9cbb
Removing intermediate container 5243748a9cbb
 ---> 1dac1b59adc3
Step 6/7 : ENV JAVA_OPTS=""
 ---> Running in 162c3e7e1428
Removing intermediate container 162c3e7e1428
 ---> 09d01198e4bb
Step 7/7 : ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mytestapp.jar" ]<code class="hljs dockerfile"></code>
 ---> Running in 8ae3363e1b45
Removing intermediate container 8ae3363e1b45
 ---> 8c6f1130c359
Successfully built 8c6f1130c359
Successfully tagged mytestapp:latest
6.
其中Successfully built 8c6f1130c359 中 8c6f1130c359为构建的镜像IMAGE
输入run命令来启动
[root@zsls testweb]# docker run -d -p 8080:8080 8c6f1130c359
9f7f5c4aeed259b0a1b4db9f8592c30351c8dfffaa9cd50b8de655fdf47ef8b1

-d 表示后台运行
-p映射端口

可以访问 http://192.168.217.201:8080/index