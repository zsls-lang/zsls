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
     指定要构建的spring boot服务镜像的基础源镜像是java，版本是8；如果本地没有java:8的镜像，就会从dockerHub下载。一般首次下载，以后就不会下载了。
EXPOSE 允许指定端口转发
VOLUME 创建一个可以从本地主机或其他容器挂载的挂载点，一般用来存放数据库和需要保持的数据等。
       指定了临时文件目录为/tmp。其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp。改步骤是可选的，如果涉及到文件系统的应用就很有必要了。/tmp目录用来持久化到 Docker 数据文件夹，因为 Spring Boot 使用的内嵌 Tomcat 容器默认使用/tmp作为工作目录 
       项目的 jar 文件作为 “mytestapp.jar” 添加到容器的
       指向了容器内的/tmp的目录，由于 Spring Boot 使用内置的Tomcat容器，Tomcat 默认使用/tmp作为工作目录。这个命令的效果是：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
       VOLUME 命令只能指定挂载点，也就是说在此处，只能指定容器内的目录，不能指定宿主机上对应的目录，也就是说是宿主机上自动生成的目录 去挂载了  容器内的指定的/tmp目录！！！
       如果想要指定宿主机的目录去进行挂载，只能通过run命令中的 -v参数进行 宿主机的指定目录 挂载  容器内的指定目录
ADD 将文件从路径
     其实就是 ADD 【rz上传上来的文件】 【要被拷贝到即将要构建的docker容器中的文件】
ENV 可以用于为docker容器设置环境变量
RUN sh -c 'touch /mytestapp.jar'
    实际执行复制操作的命令
ENTRYPOINT 指定 Docker image 运行成 instance (也就是 Docker container) 时，要执行的命令或者文件
            执行项目 mytestapp.jar。为了缩短 Tomcat 启动时间，添加一个系统属性指向 “/dev/urandom” 作为 Entropy Source
           ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/swapping.jar"]
           ENTRYPOINT 配置容器启动后执行的命令，并且不可被 docker run 提供的参数覆盖。
           -Djava.security.egd=file:/dev/./urandom
           添加java.security.egd的系统属性指向/dev/urandom，JVM上的随机数与熵池策略
##############Dockerfile################

5.docker build -t mytestapp .
  -t boot-docker 代表你要构建的名字随便取的(不一定是mytestapp 也可以是其他的) **看清楚后面有一个点的**
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
**Successfully built 8c6f1130c359
Successfully tagged mytestapp:latest**
6.
其中Successfully built 8c6f1130c359 中 8c6f1130c359为构建的镜像IMAGE
输入run命令来启动
[root@zsls testweb]# docker run -d -p 8080:8080 8c6f1130c359
9f7f5c4aeed259b0a1b4db9f8592c30351c8dfffaa9cd50b8de655fdf47ef8b1

其他启动方式：
    docker run --name swapping -itd --net=host  mytestapp 
    需要注意spring boot项目的时区问题的启动命令【关于时区处理问题，需要参考：https://www.cnblogs.com/sxdcgaq8080/p/10057385.html】
    需要注意spring boot项目启动开发配置文件和生产配置文件的配置文件的启动应用【https://www.cnblogs.com/sxdcgaq8080/p/10481974.html】
    docker run --name mytestapp -itd --net=host -v /etc/localtime:/etc/localtime:ro  -v /etc/timezone:/etc/timezone:ro  mytestapp

-d 表示后台运行
-p映射端口

启动成功后：查看容器启动情况
docker logs -f swapping
退出日志查看 使用
Ctrl+Z

可以访问 http://192.168.217.201:8080/index

如果想要重新启动spring boot服务，可以使用命令
docker restart swapping
停止服务
docker stop swapping
移除容器
docker rm -f swapping