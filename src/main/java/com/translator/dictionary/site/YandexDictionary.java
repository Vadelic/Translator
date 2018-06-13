package com.translator.dictionary.site;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

/**
 * Created by Komyshenets on 28.10.2017.
 */
public class YandexDictionary  {
    private JSONObject dataJsonObj;
    private final String KEY_API = "dict.1.1.20171118T170307Z.c16cbdad19e6aaa3.18872430e9d9cc3234817b91bd79937562d227d0";
    private final String URL_FORMAT = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=%s&text=%s&lang=%s-%s";

    public YandexDictionary(String original, String from, String to) throws IOException {
        dataJsonObj = getJsonObject(original, from, to);
    }


    private JSONObject getJsonObject(String original, String langFrom, String langTo) throws IOException {
        String urlStr = String.format(URL_FORMAT, KEY_API, URLEncoder.encode(original, "UTF-8"), langFrom, langTo);
        URL urlObj = new URL(urlStr);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        return new JSONObject(reader.lines().collect(Collectors.joining()));
    }



    public String getTranslate() {
// TODO: 12/11/2017  
        return dataJsonObj.toString();

    }


}
