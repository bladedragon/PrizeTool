package com.cqupt.prizetool.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;


public class DruidDataSourceBuilder {
    private DruidDataSource dataSource = new DruidDataSource();

    public final static DruidDataSourceBuilder newInstance() {
        return new DruidDataSourceBuilder();
    }

    public DruidDataSourceBuilder appendDriverClassName(String driverClassName) {
        dataSource.setDriverClassName(driverClassName);
        return this;
    }

    public DruidDataSourceBuilder appendUrl(String url) {
        dataSource.setUrl(url);
        return this;
    }

    public DruidDataSourceBuilder appendUsername(String username) {
        dataSource.setUsername(username);
        return this;
    }

    public DruidDataSourceBuilder appendPassword(String password) {
        dataSource.setPassword(password);
        return this;
    }

    public DruidDataSourceBuilder appendInitialSize(int initialSize) {
        dataSource.setInitialSize(initialSize);
        return this;
    }

    public DruidDataSourceBuilder appendMinIdle(int minIdle) {
        dataSource.setMinIdle(minIdle);
        return this;
    }

    public DruidDataSourceBuilder appendMaxActive(int maxActive) {
        dataSource.setMaxActive(maxActive);
        return this;
    }

    public DruidDataSourceBuilder appendMaxWait(long maxWait) {
        dataSource.setMaxWait(maxWait);
        return this;
    }
    public DruidDataSource getDataSource() {
        return dataSource;
    }


}
