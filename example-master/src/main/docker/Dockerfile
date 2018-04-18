# 指定一个基础镜像centos
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD master.jar master.jar
#执行jar文件
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/master.jar"]