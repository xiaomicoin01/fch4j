package org.freecash.utils;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wanglint
 */
public class HttpClientComponent {
    private static CloseableHttpClient httpclient;

    static {

        ConnectionKeepAliveStrategy myStrategy = (HttpResponse response, HttpContext context)-> {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && "timeout".equalsIgnoreCase(param)) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 60 * 1000;
        };

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(1000);
        connectionManager.setDefaultMaxPerRoute(50);

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
                .setConnectTimeout(timeOut).build();
        return requestConfig;
    }

    /**
     * 发送json数据
     * @param url 地址
     * @param jsonData json字符串
     * @return httpPost
     */
    public static HttpPost getPostForJson(String url, String jsonData){
        HttpPost httpPost = new HttpPost(url);

        StringEntity postEntity = new StringEntity(jsonData, "UTF-8");
        postEntity.setContentType("application/json");
        httpPost.setEntity(postEntity);
        return httpPost;
    }
    /**
     * 发送json数据
     * @param url 地址
     * @param paramMap 参数
     * @return HttpPost
     */
    public static HttpPost getPostForMap(String url, Map<String,Object> paramMap) throws Exception{
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<>();
        paramMap.forEach((k,v)->{
            if(Objects.isNull(v)){
                params.add(new BasicNameValuePair(k,""));
            }else{
                params.add(new BasicNameValuePair(k,v.toString()));
            }
        });

        httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
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
                    connMgr.closeExpiredConnections();
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            shutdown();
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

}
