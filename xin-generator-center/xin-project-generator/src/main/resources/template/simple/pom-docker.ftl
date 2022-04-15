<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.xin.commons</groupId>
        <artifactId>xin-spring-boot-starter-parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.${packageName}</groupId>
    <artifactId>${projectName}</artifactId>
    <description>${projectDesc}</description>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <start-class>com.${packageName}.${className}Application</start-class>
        <!--docker config-->
        <push.image>true</push.image>
        <docker.serverId>docker-hub</docker.serverId>
        <docker.repostory>core-harbor.com</docker.repostory>
        <docker.registry.name>repository-xxxx</docker.registry.name>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- spring-boot-starter-actuator 管理工具/web 查看堆栈，动态刷新配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>com.xin.commons</groupId>
            <artifactId>xin-commons-support-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>${r'${project.artifactId}'}-${r'${project.version}'}</finalName>
        <!-- 可以把属性写到文件里,用属性文件里定义的属性做替换 -->
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <!--启用过滤 true:替换   false:不替换-->
                <filtering>true</filtering>
                <excludes>
                    <exclude>mybatis/*.xml</exclude>
                    <exclude>logs/*.xml</exclude>
                </excludes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <!--启用过滤 true:替换   false:不替换-->
                <filtering>false</filtering>
                <includes>
                    <include>mybatis/*.xml</include>
                    <include>logs/*.xml</include>
                </includes>
            </resource>
        </resources>

        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!--如果没有该配置，devtools不会生效-->
                    <fork>true</fork>
                </configuration>
            </plugin>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.2.0</version>

                <executions>
                    <execution>
                        <!--要绑定到的生命周期的阶段-->
                        <id>default</id>
                        <!--要绑定到的生命周期的阶段-->
                        <phase>install</phase>
                        <!--要绑定的插件的目标-->
                        <goals>
                            <goal>build</goal>
                            <goal>push</goal>
                        </goals>
                    </execution>
                </executions>

                <configuration>
                    <!-- 私有仓库配置，需要settings.xml文件配合serverId对应的服务地址 -->
                    <serverId>${r'${docker.serverId}'}</serverId>
                    <!--配置 registryUrl-->
                    <registryUrl>${r'${docker.repostory}'}</registryUrl>
                    <!--这个是用来打docker包的，如果本地没有安装docker，就需要借助环境上的docker来打包-->
                    <!--<dockerHost>${r'${docker.repostory}'}</dockerHost>-->
                    <!--用来指定镜像名称-->
                    <imageName>
                        ${r'${docker.repostory}'}/${r'${docker.registry.name}'}/${r'${project.parent.artifactId}'}:${r'${projects.version}'}
                    </imageName>
                    <!--是否需要push到docker hub上-->
                    <pushImage>false</pushImage>
                    <!--<pushImage>${r'${push.image}'}</pushImage>-->
                    <!--覆盖相同标签镜像-->
                    <forceTags>true</forceTags>
                    <!--镜像tags-->
                    <imageTags>
                        <imageTag>${r'${project.version}'}</imageTag>
                    </imageTags>
                    <!--Dockerfile的位置 -->
                    <dockerDirectory>src/main/docker</dockerDirectory>
                    <!--配置会将dockerDirectory的内容拷贝值${r'${project.build.directory}'}/docker-->
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${r'${project.build.directory}'}</directory>
                            <include>${r'${project.build.finalName}'}.jar</include>
                        </resource>
                    </resources>
                    <!--为了提供给Dockerfile通过 JAR_FILE 使用-->
                    <buildArgs>
                        <JAR_FILE>target/${r'${project.build.finalName}'}.jar</JAR_FILE>
                    </buildArgs>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-deploy-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

