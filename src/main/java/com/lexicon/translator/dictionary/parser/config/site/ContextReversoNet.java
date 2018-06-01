package com.lexicon.translator.dictionary.parser.config.site;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.lexicon.translator.dictionary.parser.config.UsagesConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Komyshenets on 12/12/2017.
 */
public class ContextReversoNet extends SiteConnector implements UsagesConfig {
    private final static String URL = "http://context.reverso.net/translation/%s-%s/%s";
    private String word;
    private String wordLang;
    private String targetLang;
    private static Map<String, String> landMap = new HashMap<>();

    static {
        landMap.put("ru", "russian");
        landMap.put("it", "italian");
        landMap.put("en", "english");
    }

    public ContextReversoNet(String word, String wordLang, String targetLang) {
        this.word = word.toLowerCase();
        this.wordLang = landMap.get(wordLang.toLowerCase());
        this.targetLang = landMap.get(targetLang.toLowerCase());
    }

    @Override
    public Map<String, String> getUsages() {
        Map<String, String> map = new HashMap<>();
        HtmlPage page = connectAndGetPage(String.format(URL, wordLang, targetLang, word));
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
    }

    private Map<String, String> extractUsagePair(HtmlDivision div) {
        Map<String, String> map = new HashMap<>();
        String original = ((HtmlSpan) div.getFirstByXPath("div[@class='src ltr']/span[@class='text']")).asText();
        String translate = ((HtmlSpan) div.getFirstByXPath("div[@class='trg ltr']/span[@class='text']")).asText();
        if (original.length() <= 300 && translate.length() <= 300)
            map.put(original, translate);
        return map;
    }
}
