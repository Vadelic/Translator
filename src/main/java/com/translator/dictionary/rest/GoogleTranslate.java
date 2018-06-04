package com.translator.dictionary.rest;

import com.translator.dictionary.TranslateConfig;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Komyshenets on 28.10.2017.
 */
public class GoogleTranslate implements TranslateConfig {
    private final Logger log = Logger.getLogger(getClass());

    private static final String KEY_API = "AIzaSyDQQA7BNS57D1Lw2lHD5jIOu5LryxnLi7E";
    private static final String URL = "https://translation.googleapis.com/language/translate/v2";

    private String original;
    private String from;
    private String to;

    public GoogleTranslate(String original, String from, String to) {
        this.original = original.toLowerCase();
        this.from = from;
        this.to = to;
    }

    private JSONObject getJsonObject(String original, String langFrom, String langTo) throws IOException {

        URL urlObj = new URL(URL);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes("q=" + URLEncoder.encode(original, "UTF-8"));
        dataOutputStream.writeBytes("&");
        dataOutputStream.writeBytes("target=" + URLEncoder.encode(langTo, "UTF-8"));
        dataOutputStream.writeBytes("&");
        dataOutputStream.writeBytes("source=" + URLEncoder.encode(langFrom, "UTF-8"));
        dataOutputStream.writeBytes("&");
        dataOutputStream.writeBytes("key=" + URLEncoder.encode(KEY_API, "UTF-8"));


        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        return new JSONObject(reader.lines().collect(Collectors.joining()));

    }


    private String parseJson() throws IOException {
        JSONObject jsonObject = getJsonObject(original, from, to);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray translations = data.getJSONArray("translations");
        if (translations.length() > 0) {
            JSONObject firstTranslate = translations.getJSONObject(0);
            return firstTranslate.getString("translatedText");
        }

        return null;
    }

    @Override
    public String getTranslate() throws IOException {
            String googleTranslateResult = parseJson();
            if (googleTranslateResult != null && !Objects.equals(googleTranslateResult.toLowerCase(), original)) {
                return googleTranslateResult;
            }
        log.debug(String.format("cud't translate '%s' %s-%s", original, from, to));
        return null;
    }
}
