package utils;

/**
 * Created by amanpreet.oberoi on 11/15/2017.
 */

import config.ConfigReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by amanpreet.oberoi on 4/19/2016.
 */

public class APIUtils {
    @SuppressWarnings("rawtypes")
    public static APIResponse get(String url, HashMap<String, String> headers) throws IOException {
        CloseableHttpClient client = getHttpClient();
        try {
            HttpGet request = new HttpGet(url);
            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    request.addHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "application/json");
            HttpResponse response = null;
            try {
                response = client.execute(request);
            } catch (Exception e) {
                response = client.execute(request);
            }
            APIResponse rb = new APIResponse(response);
            return rb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static APIResponse post(String URL, HashMap<String, String> headers, String dataToPost) throws IOException {
        CloseableHttpClient client = getHttpClient();
        try {
            HttpPost request = new HttpPost(URL);
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "application/json");
            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    request.addHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }
            StringEntity params = new StringEntity(dataToPost);
            request.setEntity(params);
            HttpResponse response = null;
            try {
                response = client.execute(request);
            } catch (Exception e) {
                response = client.execute(request);
            }
            APIResponse rb = new APIResponse(response);
            return rb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static APIResponse put(String URL, HashMap<String, String> headers, String dataToPost) throws IOException {
        CloseableHttpClient client = getHttpClient();
        try {
            HttpPut put = new HttpPut(URL);
            put.addHeader("content-type", "application/json");
            put.addHeader("Accept", "application/json");
            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    put.addHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }
            if (!StringUtils.isEmpty(dataToPost)) {
                StringEntity params = new StringEntity(dataToPost);
                put.setEntity(params);
            }

            HttpResponse response = null;
            try {
                response = client.execute(put);
                ;
            } catch (Exception e) {
                response = client.execute(put);
            }
            APIResponse rb = new APIResponse(response);
            return rb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static APIResponse patch(String URL, HashMap<String, String> headers, String dataToPost) throws IOException {
        CloseableHttpClient client = getHttpClient();
        try {
            HttpPatch patch = new HttpPatch(URL);
            patch.addHeader("content-type", "application/json");
            patch.addHeader("Accept", "application/json");
            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    patch.addHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }
            if (StringUtils.isEmpty(dataToPost)) {
                //do nothing
            } else {
                StringEntity params = new StringEntity(dataToPost);
                patch.setEntity(params);
            }
            HttpResponse response = null;
            try {
                response = client.execute(patch);
            } finally {
                response = client.execute(patch);
            }
            APIResponse rb = new APIResponse(response);
            return rb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static APIResponse delete(String url, HashMap<String, String> headers) {
        CloseableHttpClient client = getHttpClient();
        try {
            HttpDelete delete = new HttpDelete(url);
            delete.addHeader("content-type", "application/json");
            delete.addHeader("Accept", "application/json");
            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    delete.addHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }
            HttpResponse response = null;
            try {
                response = client.execute(delete);
            } finally {
                response = client.execute(delete);
            }
            APIResponse rb = new APIResponse(response);
            return rb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Method for Creating Closeable HTTPClient with SSL Connection
    */
    public static CloseableHttpClient getHttpClient() {
        int timeout = 180;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,
                    new TrustManager[]{new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }

                        public void checkClientTrusted(
                                X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(
                                X509Certificate[] certs, String authType) {
                        }
                    }}, new SecureRandom());
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
            return closeableHttpClient;
        } catch (Exception e) {
            System.out.println("Error Creating Default Closeable HttpClient " + e.getClass().getName());
            e.printStackTrace();
            return HttpClientBuilder.create().build();
        }
    }

    public static String readResponse(HttpResponse response) {
        try {
            InputStream ips = response.getEntity().getContent();
            BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String s;
            while (true) {
                s = buf.readLine();
                if (s == null || s.length() == 0)
                    break;
                sb.append(s);
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifyUserExistence(String userName, String emailID, String password) {
        boolean userExists = false;
        try {
            APIResponse response = APIUtils.get(ConfigReader.getInstance().getApplicationBaseUrl() + "user/all/json", null);
            System.out.println(response.message);
            JSONArray array = new JSONArray(response.message);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String userNameResponse = object.get("name").toString();
                String emailIDResponse = object.get("email").toString();
                String passwordResponse = object.get("password").toString();
                if (userNameResponse.equals(userName) && emailID.equals(emailIDResponse) && password.equals(passwordResponse)) {
                    System.out.println("Name : " + object.get("name"));
                    System.out.println("Email ID : " + object.get("email"));
                    System.out.println("Password : " + object.get("password"));
                    userExists = true;
                    break;
                }
            }
            return userExists;
        } catch (Exception e) {
            System.out.println("Error while fetching existing user presence from API " + e.getClass().getName());
            e.printStackTrace();
            return userExists;
        }
    }

    public static class APIResponse {
        public int code;
        public String message;
        public Header[] responseHeaders;

        public APIResponse(HttpResponse response) {
            this.message = readResponse(response);
            this.code = response.getStatusLine().getStatusCode();
            this.responseHeaders = response.getAllHeaders();
        }
    }
}