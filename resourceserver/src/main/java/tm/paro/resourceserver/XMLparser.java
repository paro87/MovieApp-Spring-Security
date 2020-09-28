package tm.paro.resourceserver;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.XML;
import tm.paro.resourceserver.model.xml.JsonData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class XMLparser {
    public static void main(String[] args) {
        try{
            Gson gson = new Gson();
            HttpClient client=HttpClient.newHttpClient();
            HttpRequest request=HttpRequest.newBuilder()
                    .uri(URI.create("https://mynkey.com/assets/xml/dummy.xml"))
                    .build();
            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = XML.toJSONObject(response.body());
            //String jsonPrettyPrintString = jsonObject.toString(4);

            JsonData jsonData = gson.fromJson(jsonObject.toString(), JsonData.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
