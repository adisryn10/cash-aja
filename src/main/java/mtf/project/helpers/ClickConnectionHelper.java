package mtf.project.helpers;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

public class ClickConnectionHelper {

    final static String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

    public static void addClickCounter(String category) throws IOException {
        String payload = "{\"category\":" + "\"" + category + "\"" + "}";
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(baseUrl + "/click/add");
        request.setEntity(entity);
        httpClient.execute(request);
    }
}
