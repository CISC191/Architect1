package edu.sdccd.cisc191.common.entities;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;


/**
 * Native Java implementation of the Requests library
 */

public class Requests {
    enum Protocol {
        GET,
        POST,
        DELETE,
        UPDATE,
    }

    public static String request(Protocol method, String urlToRead, Map<String, String> headers, String body) throws MalformedURLException {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // System.out.println(method.toString());
            conn.setRequestMethod(method.toString());

            // if header is not null or empty
            if(!headers.isEmpty()) {
                for(Map.Entry<String,String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // if body is not null or empty
            if(!(body.length() == 0)) {
                conn.setDoOutput(true);
                OutputStream outStream = conn.getOutputStream();
                OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
                outStreamWriter.write(body);
                outStreamWriter.flush();
                outStreamWriter.close();
                outStream.close();
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
        } catch(IOException e) {
            System.err.println(e);
        }
        return result.toString();
    }

    public static String get(String urlToRead) throws MalformedURLException {
        return request(Protocol.GET, urlToRead, Collections.<String, String>emptyMap(), new String());
    }

    public static String post(String urlToRead, Map<String, String> headers, String body) throws MalformedURLException {
        return request(Protocol.POST, urlToRead, headers, body);
    }

    public static String delete(String urlToRead, Map<String, String> headers, String body) throws MalformedURLException {
        return request(Protocol.DELETE, urlToRead, headers, body);
    }
    public static String update(String urlToRead, Map<String, String> headers, String body) throws MalformedURLException {
        return request(Protocol.UPDATE, urlToRead, headers, body);
    }
}
