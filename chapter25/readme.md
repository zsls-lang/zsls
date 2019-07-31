1.把整个工程代码拷到centos服务器上
[root@zsls chapter25]# ls
chapter25.iml  HELP.md  mvnw  mvnw.cmd  pom.xml  src  target
2.在/usr/local/gs-spring-boot-docker-master目录下运行命令：mvn package docker:build
3.build success说明该项目的镜像创建成功，查看一下
[root@zsls chapter25]# docker images
4.运行该镜像
docker run --name apptest -d -p8080:8080 springio/chapter25

5.查看日志
docker logs -f apptest
退出日志查看 使用
Ctrl+Z

可以访问 http://192.168.217.201:8080/index

docker ps -a 查看容器的id 和名字（例apptest）
如果想要重新启动spring boot服务，可以使用命令
docker restart apptest
停止服务
docker stop apptest
移除容器
docker rm -f apptest