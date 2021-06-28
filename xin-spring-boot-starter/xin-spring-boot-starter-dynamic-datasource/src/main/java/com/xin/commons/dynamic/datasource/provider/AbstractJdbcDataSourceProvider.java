package com.xin.commons.dynamic.datasource.provider;

import com.xin.commons.dynamic.datasource.DynamicDataSourceCreator;
import com.xin.commons.dynamic.datasource.autoconfigure.DataSourceProperty;
import com.xin.commons.dynamic.datasource.autoconfigure.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC数据源提供者(抽象)
 */
@Slf4j
public abstract class AbstractJdbcDataSourceProvider implements DynamicDataSourceProvider {

    @Autowired(required = false)
    protected DynamicDataSourceProperties dynamicDataSourceProperties;

    @Autowired
    private DynamicDataSourceCreator dynamicDataSourceCreator;

    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;

    public AbstractJdbcDataSourceProvider(String driverClassName, String url, String username, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(driverClassName);
            log.info("成功加载数据库驱动程序");
            conn = DriverManager.getConnection(url, username, password);
            log.info("成功获取数据库连接");
            stmt = conn.createStatement();
            Map<String, DataSourceProperty> dataSourcePropertiesMap = executeStmt(stmt);
            Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size());
            for (Map.Entry<String, DataSourceProperty> item : dataSourcePropertiesMap.entrySet()) {
                dataSourceMap.put(item.getKey(), dynamicDataSourceCreator.createDataSource(item.getValue()));
            }
            return dataSourceMap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(conn);
            JdbcUtils.closeStatement(stmt);
        }
        return null;
    }

    /**
     * 执行语句获得数据源参数
     *
     * @param statement 语句
     * @return 数据源参数
     * @throws SQLException sql异常
     */
    protected abstract Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException;
}
