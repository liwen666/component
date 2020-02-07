package com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

public class BaseObjectCreate {

    @Test
    public void createCollectObj() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "{\"resourceType\":\"COLLECT_DATA_OBJECT\",\"createTime\":\"2020-02-06 15:51:55\",\"updateTime\":\"2020-02-06 16:22:40\",\"updatePerson\":\"liwen\",\"versionState\":\"INACTIVE\",\"version\":1,\"versionCode\":\"aakcyfkcdktkrkul\",\"resourceId\":886050,\"objectId\":886051,\"objectInfoCode\":null,\"fields\":[],\"dataObjectId\":886051,\"dataSourceCode\":null,\"queryParams\":null,\"keyFieldCode\":\"case_id\",\"viewType\":null,\"delFields\":\"\"}");
        Request request = new Request.Builder()
                .url("http://10.0.14.102:9800/collect/data/version")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Cookie", "token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaXdlbiIsImF1ZCI6IndlYiIsImlzcyI6IjIyMDI4OC0yMjAzMTQiLCIyMjAyODgiOiIxNGI3NjZhMDdkNGE0MjI1YjkzOTk2ZTM5OGQ2NTVkNyIsImV4cCI6MTU4MDk4MDA5NiwiaWF0IjoxNTgwOTc2NDk2fQ.zwjK-C53GJbu1muiVWn9XvxKEd03mKwaMXiSXMDf-GK35RWkTmBbPOETZ2vtsDBRYiP0H-EfH0BjEx-0yBvSNQ; content=W3siaWQiOjIyMDMxNCwibmFtZSI6ImpyeOa1i+ivleacuuaehCJ9LHsiaWQiOjI5Mzg5NiwibmFtZSI6IuaMh+agh+WKoOW3pea1i+ivleacuuaehCJ9XQ==; project={%22projectId%22:885858%2C%22projectName%22:%22%E6%B5%8B%E8%AF%95%E4%BB%AA%E8%A1%A8%E7%9B%98%22%2C%22projectCode%22:%22test_dashboard%22%2C%22project%22:%22icon1.png%22%2C%22createPerson%22:null%2C%22category%22:null%2C%22description%22:%22%22%2C%22useState%22:%22UNUSE%22%2C%22approvalWay%22:%22None%22}; user=eyJjb250ZW50Q29kZSI6IjIyMDMxNCIsImNvbnRlbnROYW1lIjoianJ45rWL6K+V5py65p6EIiwiY3VycmVudFByb2plY3RDb2RlIjo4ODU4NTgsImVuYWJsZWQiOnRydWUsImlkIjo4ODU3OTUsIm5pY2tOYW1lIjoi5p2O5paHXyIsInVzZXJOYW1lIjoibGl3ZW4ifQ==")
                .addHeader("Host", "10.0.14.102:9800")
                .addHeader("Origin", "http://10.0.14.102:9800")
                .addHeader("Pragma", "no-cache")
                .addHeader("Referer", "http://10.0.14.102:9800/index.html")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .addHeader("Postman-Token", "9cae0954-f86f-4ff6-8369-cabdb4c9fd65")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

}
