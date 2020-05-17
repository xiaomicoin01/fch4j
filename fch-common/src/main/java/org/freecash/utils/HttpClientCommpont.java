package org.freecash.utils;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HttpClientCommpont {
    static CloseableHttpClient httpclient;
    static int timeOut = 60000;
    static int maxConnTotal = 1000;

    static {
//        RequestConfig requestConfig = RequestConfig //请求设置
//                .custom()
//                .setConnectionRequestTimeout(60000) //从连接池获取连接超时时间
//                .setConnectTimeout(60000) //连接超时时间
//                .setSocketTimeout(60000).build(); //套接字超时时间

        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 60 * 1000;//如果没有约定，则默认定义时长为60s
            }
        };

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxConnTotal);
        connectionManager.setDefaultMaxPerRoute(50);

//        HttpClientBuilder builder = HttpClients.custom()
//                .setDefaultRequestConfig(requestConfig)
//                .setKeepAliveStrategy(myStrategy)
//                .setMaxConnTotal(maxConnTotal); //设置最大连接数
//        httpclient = builder.build();

        httpclient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(myStrategy)
                .setDefaultRequestConfig(RequestConfig.custom().setStaleConnectionCheckEnabled(true).build())
                .build();

        new IdleConnectionMonitorThread(connectionManager).start();
    }

    public static RequestConfig getRequestConfig(int timeOut) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut).build();//设置请求和传输超时时间
        return requestConfig;
    }

    /**
     * 发送json数据
     * @param url 地址
     * @param jsonData json字符串
     * @return
     */
    public static HttpPost getPostForJson(String url, String jsonData){
        HttpPost httpPost = new HttpPost(url);

        StringEntity postEntity = new StringEntity(jsonData, "UTF-8");
        postEntity.setContentType("application/json");
        httpPost.setEntity(postEntity);
        return httpPost;
    }

    public static String send(HttpPost httpPost) throws Exception {
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            response.setLocale(Locale.CHINESE);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String html = EntityUtils.toString(entity, "UTF-8");
                entity.getContent().close();
                return html;
            } else {
                throw new Exception("发送数据处理报错," + response.getStatusLine().getStatusCode() + "," + response.getStatusLine().getReasonPhrase());
            }
        } catch (IOException e) {
            throw e;
        } finally {
            httpPost.abort();
            httpPost.completed();
            if (response != null) {
                ((CloseableHttpResponse) response).close();
            }
            //httpclient.close();
            //httpclient.getConnectionManager().shutdown();
        }
    }
}

class IdleConnectionMonitorThread extends Thread {

    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

}
