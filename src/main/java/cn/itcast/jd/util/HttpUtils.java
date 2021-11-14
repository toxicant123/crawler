package cn.itcast.jd.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-14 10:42
 */
@Component
public class HttpUtils {

    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(10);

    }

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return
     */
    public String doGet(String url) {
        //获取HttpClient对象
        //创建HttpGet请求对象，设置url地址
        //使用HttpClient

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断entity是否不为空，如果不为空就可以使用entityutils
                if (response.getEntity() != null) {
                    String s = EntityUtils.toString(response.getEntity());
                    return s;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "";

    }


    /**
     * 下载图片
     *
     * @param url
     * @return
     */
    public String doGetImage(String url) {
        //获取HttpClient对象
        //创建HttpGet请求对象，设置url地址
        //使用HttpClient

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(url);

            httpGet.setConfig(getConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断entity是否不为空，如果不为空就可以使用entityutils
                if (response.getEntity() != null) {
                    //下载图片
                    //获取图片的后缀
                    //创建图片名，重命名图片
                    //下载图片
                    //返回图片名称
                    String extName = url.substring(url.lastIndexOf("."));
                    String picName = UUID.randomUUID().toString() + extName;

                    //声明outputStream
                    OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\13655\\Desktop\\images\\" + picName));
                    response.getEntity().writeTo(outputStream);
                    return picName;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "";
    }

    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000) //创建连接的最长时间
                .setConnectionRequestTimeout(500) //获取连接的最长时间
                .setSocketTimeout(10000)  //数据传输的最长时间
                .build();
        return config;
    }

}
