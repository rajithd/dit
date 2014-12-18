package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;

public class HttpConnector {

    private HttpClient client;


    public HttpConnector() {
        try {

            client = HttpClients.custom().build();

        } catch (Exception e) {

        }
    }

    public ObjectMapper mapper = new ObjectMapper();

    public <T> T responseToObject(HttpResponse response, Class<T> valueType) throws Exception {
        String content = IOUtils.toString(response.getEntity().getContent());
        return mapper.readValue(content, valueType);
    }

    public <T> T responseToObject(HttpResponse response, TypeReference<T> typeRef) throws Exception {
        String content = IOUtils.toString(response.getEntity().getContent());
        return mapper.readValue(content, typeRef);
    }

    public <T> StringEntity objectToEntity(T request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, request);
        StringEntity entity = new StringEntity(baos.toString());
        return entity;
    }

    public <T> T doPut(T request, String url, Class<T> valueType) throws Exception {

        HttpPut put = new HttpPut(url);
        StringEntity params = objectToEntity(request);
        put.setEntity(params);
        HttpResponse response = client.execute(put);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("return code is " + response.getStatusLine());
        }

        T responseObject = responseToObject(response, valueType);
        return responseObject;
    }


    public <T> T doPost(T request, String url, Class<T> valueType) throws Exception {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        StringEntity params = objectToEntity(request);
        post.setEntity(params);
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("return code is " + response.getStatusLine());
        }

        T responseObject = responseToObject(response, valueType);
        return responseObject;
    }

    public <T> String doPostJson(T request, String url) throws Exception {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        StringEntity params = objectToEntity(request);
        post.setEntity(params);
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("return code is " + response.getStatusLine());
        }

        return IOUtils.toString(response.getEntity().getContent());
    }

    public <T> T doGet(String url, Class<T> valueType) throws Exception {

        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("return code is " + response.getStatusLine());
        }

        T responseObject = responseToObject(response, valueType);
        return responseObject;
    }

    public String doDeleteJson(String url) throws Exception {
        HttpDelete delete = new HttpDelete(url);
        HttpResponse response = client.execute(delete);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("return code is " + response.getStatusLine());
        }

        return IOUtils.toString(response.getEntity().getContent());
    }

    public String doGetJson(String url) throws Exception {

        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("return code is " + response.getStatusLine());
        }
        return IOUtils.toString(response.getEntity().getContent());
    }

    public <T> String  doPutJson(T request, String url, Class<T> valueType) throws Exception {
        HttpPut put = new HttpPut(url);
        put.addHeader("Content-Type", "application/json");
        HttpResponse response = client.execute(put);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("return code is " + response.getStatusLine());
        }

        return IOUtils.toString(response.getEntity().getContent());
    }


    public <T> T doGet(String url, TypeReference<T> typeRef) throws Exception {
        HttpGet get = new HttpGet(url);

        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(String.format("Response for URL %s is '%s', status code: %s, content: %s",
                    url, response.getStatusLine(), response.getStatusLine().getStatusCode(), IOUtils.toString(response.getEntity().getContent())));
        }

        T responseObject = responseToObject(response, typeRef);
        return responseObject;
    }

}
