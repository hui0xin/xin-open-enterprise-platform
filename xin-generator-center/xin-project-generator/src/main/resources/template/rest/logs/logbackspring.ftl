<?xml version="1.0" encoding="UTF-8" ?>

<!--
    级别从高到低 OFF 、 FATAL 、 ERROR 、 WARN 、 INFO 、 DEBUG 、 TRACE 、 ALL
    日志输出规则 根据当前ROOT 级别，日志输出时，级别高于root默认的级别时 会输出
    以下 每个配置的 filter 是过滤掉输出文件里面，会出现高级别文件，依然出现低级别的日志信息，通过filter 过滤只记录本级别的日志
    scan 当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。
    scanPeriod 设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。
    debug 当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。
-->
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter"/>
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>

    <springProperty scope="context" name="appName" source="spring.application.name"/>
    <#--    <property name="logHomeDir" value="/data1/logs/"/>-->
    <property name="logHomeDir" value="/Users/hx/logs/"/>
    <!-- 异步缓冲队列的深度,该值会影响性能.默认值为256-->
    <property name="queueSize" value="512"/>
    <property name="maxHistory" value="15"/>

    <!-- 为了本地调试的时候，控制台打出 彩色日志-->
    <property name="consolePattern" value="%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr([${r'${appName:-}'}]){blue} %clr(%-5level){faint} %clr([%thread]){magenta} %clr(%logger{50}){cyan} - %msg%n"/>
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} - [${r'${appName:-}'}] - [%-5level] - [%thread] - [%X{PtxId}] - [%logger] - %msg%n"/>

    <!-- 控制台打印 带有彩色-->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>${r'${consolePattern}'}</pattern>
        </layout>
    </appender>

    <appender name="file_appender_daily" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${r'${logHomeDir}'}/${r'${appName}'}/${r'${appName}'}.log</file>
        <!-- 如果是 true，日志被追加到文件结尾，如果是 false，清空现存文件，默认是true。-->
        <append>true</append>
        <encoder>
            <pattern>${r'${pattern}'}</pattern>
            <charset>utf-8</charset>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${r'${logHomeDir}'}/${r'${appName}'}/logs_bak/${r'${appName}'}.log.%d{yyyy-MM-dd}.gz
            </fileNamePattern>
            <!-- 单个文件最大为20MB -->
            <!--<timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">-->
            <!--<maxFileSize>20MB</maxFileSize>-->
            <!--</timeBasedFileNamingAndTriggeringPolicy>-->
            <!-- 文件的保留个数 -->
            <maxHistory>${r'${maxHistory}'}</maxHistory>
        </rollingPolicy>
    </appender>

    <appender name="ASYNC_LOG" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 不丢失日志.默认的,如果队列的80%已满,则会丢弃TRACT、DEBUG、INFO级别的日志 -->
        <discardingThreshold>0</discardingThreshold>
        <!-- 更改默认的队列的深度,该值会影响性能.默认值为256 -->
        <queueSize>${r'${queueSize}'}</queueSize>
        <appender-ref ref="file_appender_daily"/>
    </appender>

    <springProfile name="!prod">
        <logger name="com.xin" level="DEBUG" additivity="false">
            <springProfile name="!prod">
                <springProfile name="!test">
                    <springProfile name="!dev">
                        <appender-ref ref="CONSOLE"/>
                    </springProfile>
                </springProfile>
            </springProfile>
            <appender-ref ref="ASYNC_LOG"/>
        </logger>
    </springProfile>

    <root level="INFO">
        <appender-ref ref="ASYNC_LOG"/>
        <springProfile name="!prod">
            <springProfile name="!test">
                <springProfile name="!dev">
                    <appender-ref ref="CONSOLE"/>
                </springProfile>
            </springProfile>
        </springProfile>
    </root>
</configuration>