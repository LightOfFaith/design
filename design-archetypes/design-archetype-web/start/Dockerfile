FROM openjdk:8u111-jdk-alpine

LABEL maintainer="devops@docker.com"

# 工作目录
WORKDIR /app

# 环境变量
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Xms512m -Xmx512m -XX:PermSize=128m -XX:MaxPermSize=256m -Dfile.encoding=UTF-8"

ENV PARAMS=""

# 设置时区
ENV TZ=Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD start.jar /app/design-web.jar

ENTRYPOINT ["sh", "-c", "java -jar $JAVA_OPTS /app/design-web.jar $PARAMS"]