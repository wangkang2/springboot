package com.wk.conf;

import com.wk.conf.properties.HttpPool;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Autowired
    private HttpPool httpPool;

    //首先实例化一个连接池管理器，设置最大连接数、并发连接数
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        httpClientConnectionManager.setMaxTotal(httpPool.getMaxTotal());
        //并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(httpPool.getDefaultMaxPerRoute());
        return httpClientConnectionManager;
    }

    //实例化连接池，设置连接池管理器
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager")PoolingHttpClientConnectionManager httpClientConnectionManager){
        //HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接使用new来实例化一个HttpClientBuilder，可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        return httpClientBuilder;
    }

    //注入连接池，用于获取httpClient
    @Bean
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }

    @Bean(name = "builder")
    public RequestConfig.Builder getBuilder(){
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(httpPool.getConnectTimeout())
                .setConnectionRequestTimeout(httpPool.getConnectionRequestTimeout())
                .setSocketTimeout(httpPool.getSocketTimeout())
                .setStaleConnectionCheckEnabled(httpPool.isStaleConnectionCheckEnabled());
    }

    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder){
        return builder.build();
    }

}
