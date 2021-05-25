<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.xin.commons</groupId>
        <artifactId>${projectName}</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>${projectName}-rest</artifactId>
    <version>${r'${projects.version}'}</version>

    <properties>
        <start-class>com.${packageName}.${className}Application</start-class>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.xin.commons</groupId>
            <artifactId>${projectName}-core</artifactId>
        </dependency>
        <dependency>
            <groupId>com.ctrip.framework.apollo</groupId>
            <artifactId>apollo-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
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
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <profiles>
        <profile>
            <id>local</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <env>local</env>
            </properties>
        </profile>
        <profile>
            <id>dev</id>
            <properties>
                <env>dev</env>
            </properties>
        </profile>
        <profile>
            <id>test</id>
            <properties>
                <env>test</env>
            </properties>
        </profile>
        <profile>
            <id>prod</id>
            <properties>
                <env>prod</env>
            </properties>
        </profile>
    </profiles>

    <build>
        <finalName>${r'${project.parent.artifactId}'}-${r'${projects.version}'}</finalName>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <!--启用过滤 true:替换   false:不替换-->
                <filtering>true</filtering>
                <excludes>
                    <exclude>mybatis/*.xml</exclude>
                    <exclude>logs/*.xml</exclude>
                    <exclude>messages/**</exclude>
                </excludes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <!--启用过滤 true:替换   false:不替换-->
                <filtering>false</filtering>
                <includes>
                    <include>mybatis/*.xml</include>
                    <include>logs/*.xml</include>
                    <include>messages/**</include>
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
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-deploy-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>

        </plugins>
    </build>
</project>

