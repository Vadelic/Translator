package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.*;
import com.translator.dictionary.TranslateConfig;
import com.translator.dictionary.UsagesConfig;

import java.util.*;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public class GlosbeCom extends SiteConnector implements TranslateConfig, UsagesConfig {
    private final static String URL = "https://glosbe.com/%s/%s/%s";
    private final int MAX_USAGE_LENGTH = 300;
    private String word;
    private String wordLang;
    private String targetLang;

    public GlosbeCom(String word, String wordLang, String translateLang) {
        this.targetLang = translateLang;
        this.word = word.toLowerCase();
        this.wordLang = wordLang;
    }
    @Override
    public String getAddress() {
        return String.format(URL, wordLang, targetLang, word);
    }
    @Override
    public String getTranslate() {
        HtmlPage page = connectAndGetPage(getAddress());
        if (page != null) {
            DomElement elementById = page.getElementById("phraseTranslation");

            if (elementById != null) {
                List<HtmlElement> lines = ((HtmlDivision) elementById).getElementsByAttribute("strong", "class", " phr");
                Iterator<HtmlElement> liIterator = lines.iterator();
                Set<String> translates = new TreeSet<>();
                while (liIterator.hasNext() && translates.size() < 3) {
                    HtmlElement next = liIterator.next();
                    translates.add(next.asText().toLowerCase().trim());
                }
                if (!translates.isEmpty())
                    return String.join(", ", translates);
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getUsages() {
        Map<String, String> map = new HashMap<>();
        HtmlPage page = connectAndGetPage(getAddress());
        if (page != null) {
            List<Object> byXPath = page.getByXPath("//div[@id='tmTable']/div");
            for (Object o : byXPath) {
                if (o instanceof HtmlDivision) {
                    Map<String, String> pair = extractUsagePair((HtmlDivision) o);
                    if (!pair.isEmpty())
                        map.putAll(pair);
                }
            }
        }
        return map;
    }

    private Map<String, String> extractUsagePair(HtmlDivision div) {
        Map<String, String> map = new HashMap<>();
        String original = ((HtmlSpan) div.getFirstByXPath("div[@lang='" + wordLang + "']/span/span")).asText();
        String translate = ((HtmlSpan) div.getFirstByXPath("div[@lang='" + targetLang + "']/span/span")).asText();
        if (original.length() <= MAX_USAGE_LENGTH && translate.length() <= MAX_USAGE_LENGTH)
            map.put(original, translate);
        return map;
    }
}
