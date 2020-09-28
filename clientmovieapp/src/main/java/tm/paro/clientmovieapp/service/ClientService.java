package tm.paro.clientmovieapp.service;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import tm.paro.clientmovieapp.auth.ClientUserService;
import tm.paro.clientmovieapp.model.AccessTokenResponse;
import tm.paro.clientmovieapp.model.Resource.Address;
import tm.paro.clientmovieapp.model.Resource.Director;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    private final ClientUserService clientUserService;

    private final RestTemplate restTemplate;
    @Value("${authserver.accessTokenUri}")
    private String tokenEndpoint;
    @Value("${authserver.clientId}")
    private String clientId;
    @Value("${authserver.clientSecret}")
    private String clientSecret;
    @Value("${authserver.username}")
    private String username;
    @Value("${authserver.password}")
    private String password;
    //private final String password= "10SqfAr4mDd13aKms";
    @Value("${authserver.grantType}")
    private String grantType;
    final Gson gson = new Gson();

    private AccessTokenResponse tokenResponse = null;
    private HttpClient client = HttpClientBuilder.create().build();
    @Autowired
    public ClientService(ClientUserService clientUserService, RestTemplate restTemplate) {
        this.clientUserService = clientUserService;
        this.restTemplate = restTemplate;
    }

    public AccessTokenResponse httpapache() {


        final HttpClient client = HttpClientBuilder.create().build();

        try{
            HttpPost post=new HttpPost(tokenEndpoint);
            post.addHeader("content-type", "application/x-www-form-urlencoded");
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", grantType));
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", password));
            urlParameters.add(new BasicNameValuePair("client_id", clientId));
            urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(urlParameters);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
            tokenResponse = gson.fromJson(streamReader, AccessTokenResponse.class);

            LOGGER.info("callbackHandler -  Response received. Token :\n" +
                            "Access token: {} \n" +
                            "Token type: {} \n" +
                            "Expires in: {} \n" +
                            "Refresh token: {} \n" +
                            "Scope: {} \n"
                    , tokenResponse.getAccess_token(), tokenResponse.getToken_type(), tokenResponse.getExpires_in(), tokenResponse.getRefresh_token(), tokenResponse.getScope());


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokenResponse;

    }

    public AccessTokenResponse httpSpring() {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("client_id", clientId);
        requestParams.add("username", username);
        requestParams.add("password", password);
        requestParams.add("grant_type", "password");
        requestParams.add("client_secret", clientSecret);
        requestParams.add("scope", "openid");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestParams, headers);
        tokenResponse = getAccessTokenResponse(request, tokenEndpoint);

        return tokenResponse;

    }
    private AccessTokenResponse getAccessTokenResponse(HttpEntity<MultiValueMap<String, String>> request, String url) {
        try {
            ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(url, request, AccessTokenResponse.class);
            return response.getBody();
        } catch (ResourceAccessException e) {

            try {
                ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(url, request, AccessTokenResponse.class);
                return response.getBody();
            } catch (Exception ex) {
                throw ex;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void displayJSON(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            result.append(line);
        }
    }

    public List<Director> getAllDirectors() {
        List<Director> directorList=new ArrayList<>();
        String allDirectorsAPI="http://localhost:8085/movieapp/director";
        /*client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(allDirectorsAPI);
        get.setHeader("accept", "application/json");
        get.setHeader("authorization", "Bearer " + tokenResponse.getAccess_token());

        //ResponseEntity<String> response = restTemplate.exchange(allDirectorsAPI, HttpMethod.GET, entity, String.class);
        try {
            HttpResponse response = client.execute(get);
            InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
            directorList= gson.fromJson(streamReader, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+tokenResponse.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

        ResponseEntity<List<Director>> response = restTemplate.exchange(allDirectorsAPI, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Director>>() {});
        String result = restTemplate.postForObject(url, entity, String.class);*/

        Director director1=new Director(1, "John", "John",1999, new Address(34000, "Fatih", "Istanbul", "Turkey"), null);
        Director director2=new Director(2, "Anna", "Twist",1995, new Address(34001, "Osman", "Ankara", "Turkey"), null);
        directorList.add(director1);
        directorList.add(director2);
        return directorList;
    }

    public Director getDirectorById(int id) {
        Director director = null;
        String movie="http://localhost:8085/movieapp/director/"+id;
        client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(movie);
        get.setHeader("accept", "application/json");
        get.setHeader("authorization", "Bearer " + tokenResponse.getAccess_token());

        //ResponseEntity<String> response = restTemplate.exchange(allDirectorsAPI, HttpMethod.GET, entity, String.class);
        try {
            HttpResponse response = client.execute(get);
            InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
            director = gson.fromJson(streamReader, Director.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return director;
    }

    public void add(Director director) {
        String movie="http://localhost:8085/movieapp/director/";
        client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(movie);
        post.setHeader("accept", "application/json");
        post.setHeader("authorization", "Bearer " + tokenResponse.getAccess_token());
        String json=gson.toJson(director);
        StringEntity entity = null;
        try{
            entity = new StringEntity(json);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Director added with id={}", director.getDirectorId());
    }

    public void put(Director director) {
        String movie="http://localhost:8085/movieapp/director/";
        client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(movie);
        put.setHeader("accept", "application/json");
        put.setHeader("authorization", "Bearer " + tokenResponse.getAccess_token());
        String json=gson.toJson(director);
        StringEntity entity = null;
        try{
            entity = new StringEntity(json);
            put.setEntity(entity);
            HttpResponse response = client.execute(put);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Director put with id={}", director.getDirectorId());
    }

    public void deleteById(Long directorId) {
        String deleteEndpoint="http://localhost:8085/movieapp/director/"+directorId;
        client = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(deleteEndpoint);
        delete.setHeader("accept", "application/json");
        delete.setHeader("authorization", "Bearer " + tokenResponse.getAccess_token());
        try{
            HttpResponse response = client.execute(delete);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Director deleted with id={}", directorId);
    }
}
