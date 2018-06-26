package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.translator.dictionary.UsagesConfig;
import com.translator.exception.DictionaryConfigException;
import com.translator.utils.SiteConnector;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Komyshenets on 12/12/2017.
 */
public class ContextReversoNet implements UsagesConfig {
    private final Logger log = Logger.getLogger(getClass());
    private final int MAX_USAGE_LENGTH = 40;
    private String word = null;
    private String wordLang = null;
    private String targetLang = null;;

    @Override
    public void setWord(String word) {
        this.word = word.toLowerCase();
    }

    @Override
    public void setLangFrom(String langFrom) throws DictionaryConfigException {
        Properties properties = getLangProperties("contextReversoNet.properties");
        this.wordLang = properties.getProperty(langFrom.toLowerCase());
    }

    @Override
    public void setLangTo(String langTo) throws DictionaryConfigException {
        Properties properties = getLangProperties("contextReversoNet.properties");
        this.targetLang = properties.getProperty(langTo.toLowerCase());
    }


    @Override
    public String getAddress() {
        return String.format("http://context.reverso.net/translation/%s-%s/%s", wordLang, targetLang, word);
    }


    private Properties getLangProperties(String propertyName) throws DictionaryConfigException {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream(propertyName));
            return properties;
        } catch (IOException e) {
            log.error("Error during load properties");
            throw new DictionaryConfigException("Error during load properties", e);
        }
    }

    @Override
    public Map<String, String> getUsages() throws DictionaryConfigException {
        try {
            Map<String, String> map = new HashMap<>();
            HtmlPage page = new SiteConnector().connectAndGetPage(getAddress());
            if (page != null) {
                List<Object> byXPath = page.getByXPath("//section[@id='examples-content']/div[@class='example']");
                for (Object o : byXPath) {
                    if (o instanceof HtmlDivision) {
                        Map<String, String> pair = extractUsagePair((HtmlDivision) o);
                        if (!pair.isEmpty())
                            map.putAll(pair);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            throw new DictionaryConfigException("Error while parsing usages", e);
        }
    }

    private Map<String, String> extractUsagePair(HtmlDivision div) {
        Map<String, String> map = new HashMap<>();
        String original = ((HtmlSpan) div.getFirstByXPath("div[@class='src ltr']/span[@class='text']")).asText();
        String translate = ((HtmlSpan) div.getFirstByXPath("div[@class='trg ltr']/span[@class='text']")).asText();
        if (original.length() <= MAX_USAGE_LENGTH && translate.length() <= MAX_USAGE_LENGTH)
            map.put(original, translate);
        return map;
    }
}
