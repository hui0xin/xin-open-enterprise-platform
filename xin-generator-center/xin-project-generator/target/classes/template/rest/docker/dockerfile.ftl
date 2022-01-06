# FROM 表示从哪个镜像开始，以此为基础打包，可以配置多个 FROM aaa FROM bbb
FROM azul/zulu-openjdk:8

# MAINTAINER 表示谁来制作的
MAINTAINER huixin hxniit@126.com

# ENV 用于为镜像定义所需的环境变量，并可被Dockerfile文件中位于其后的其它指令（如ENV、ADD、COPY等）
# 所调用调用格式为$variable_name或${r'${variable_name}'}
ENV JAVA_HOME=""
ENV TZ=Asia/Shanghai
ENV APP_HOME=/usr/local/xin-project
ENV JAVA_OPTS "-Xms128M -Xmx128M -Djava.security.egd=file:/dev/./urandom"
ENV JAR_FILE_NAME=${projectName}-1.0-SNAPSHOT.jar
#ENV JAR_FILE_NAME=${r'${JAR_FILE}'}
ENV APP_PORT=7200

# VOLUME 用于在image中创建一个挂载点目录，以挂载Docker host上的卷或其它容器上的卷
VOLUME /tmp

# RUN 在新镜像内部执行的命令，比如安装一些软件、配置一些基础环境，可使用\来换行，
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p $APP_HOME

# EXPOSE 暴露镜像的端口供主机做映射，启动镜像时，使用-P参数来讲镜像端口与宿主机的随机端口做映射。
EXPOSE $APP_PORT

# ADD 将主机的文件复制到镜像中，跟COPY一样
ADD $JAR_FILE_NAME $APP_HOME/$JAR_FILE_NAME

RUN sh -c 'touch ${r'${APP_HOME}'}/$JAR_FILE_NAME'

#指定后续命令的执行目录 相当于linux cd
WORKDIR $APP_HOME

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar $JAR_FILE_NAME" ]

//# docker rmi $(docker images | grep "<none>" | awk "{print $3}")

# docker exec -it 76f021ae6bc1 sh

# Run as:
# docker run -idt -p 7200:7200 -e appPort=7200 xin/xin-demo:latest

# 5. 向镜像中增加文件 向镜像中添加文件有两种命令:COPY 和 ADD。
#COPY 命令可以复制本地文件夹到镜像中:
#COPY website /var/www/html
#ADD 命令支持添加本地的 tar 压缩包到容器中指定目录,压缩包会被自动解压为目录,也
#可以自动下载 URL 并拷贝到镜像,例如:
#ADD html.tar /var/www
#ADD http://www.westos.org/html.tar /var/www
# 6. CMD 与 ENTRYPOINT
#ENTRYPOINT 容器启动后执行的命令,让容器执行表现的像一个可执行程序一样,与
#CMD 的 区 别 是 不 可 以 被 docker run 覆 盖 , 会 把 docker run 后 面 的 参 数 当 作 传 递 给
#ENTRYPOINT 指令的参数。Dockerfile 中只能指定一个 ENTRYPOINT,如果指定了很多,
#只 有 最 后 一 个 有 效 。 docker run 命 令 的 -entrypoint 参 数 可 以 把 指 定 的 参 数 继 续 传 递 给
#ENTRYPOINT。在本实验中两种方式都可以选择。

