#jdk版本
FROM java:8
# 作者
MAINTAINER wangkang
#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
#设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp
#复制target/spring-boot-web-demo.jar到容器里WORKDIR下
ADD target/springboot-0.0.1.jar  springboot.jar
#暴露端口
EXPOSE 8080
#启动容器时的进程
ENTRYPOINT ["java","-jar","springboot.jar"]