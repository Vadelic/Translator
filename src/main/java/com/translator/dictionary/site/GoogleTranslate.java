package com.translator.dictionary.site;

import com.translator.dictionary.TranslateConfig;
import com.translator.exception.DictionaryConfigException;
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
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Komyshenets on 28.10.2017.
 */
public class GoogleTranslate implements TranslateConfig {
    private final Logger log = Logger.getLogger(getClass());

    private String KEY_API;

    private String original;
    private String from;
    private String to;

    public GoogleTranslate() throws DictionaryConfigException {
        Properties properties = getLangProperties("google.properties");
        this.KEY_API = properties.getProperty("key_api");
    }
    private Properties getLangProperties(String propertyName) throws DictionaryConfigException {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream(propertyName));
            return properties;
        } catch (Exception e) {
            log.error("Error during load properties");
            throw new DictionaryConfigException("Error during load properties", e);
        }
    }

    @Override
    public void setWord(String word) {
        this.original = word.toLowerCase();
    }

    @Override
    public void setLangFrom(String langFrom) {
        this.from = langFrom.toLowerCase();
    }

    @Override
    public void setLangTo(String langTo) {
        to = langTo.toLowerCase();
    }


    private JSONObject getJsonObject(String original, String langFrom, String langTo) throws IOException {

        URL urlObj = new URL(getAddress());
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
    public String getTranslate() throws DictionaryConfigException {
        try {
            String googleTranslateResult = parseJson();
            if (googleTranslateResult != null && !Objects.equals(googleTranslateResult.toLowerCase(), original)) {
                return googleTranslateResult;
            }
            log.debug(String.format("cud't translate '%s' %s-%s", original, from, to));
            return null;
        } catch (IOException e) {
            throw new DictionaryConfigException("Google exception while translating ", e);
        }
    }

    @Override
    public String getAddress() {
        return "https://translation.googleapis.com/language/translate/v2";
    }
}
