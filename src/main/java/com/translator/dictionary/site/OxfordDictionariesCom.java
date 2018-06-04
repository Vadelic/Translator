package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.translator.dictionary.PhonemeConfig;

/**
 * Created by Komyshenets on 12/11/2017.
 */
public class OxfordDictionariesCom extends SiteConnector implements PhonemeConfig {
    private final static String URL = "https://en.oxforddictionaries.com/definition/%s";
    private String word;

    public OxfordDictionariesCom(String wordOriginal) {
        this.word = wordOriginal.toLowerCase();
    }

    @Override
    public String getPhoneme() {
        HtmlPage page = connectAndGetPage(String.format(URL, word));
        if (page != null) {
            Object firstByXPath = page.getFirstByXPath("//span[@class='phoneticspelling']");
            if (firstByXPath instanceof DomNode) {
                return ((DomNode) firstByXPath).asText().replaceAll("\\A/|/\\z", "");
            }
        }
        return null;
    }
}
