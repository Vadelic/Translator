package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.translator.dictionary.PhonemeConfig;
import com.translator.utils.SiteConnector;

/**
 * Created by Komyshenets on 12/11/2017.
 */
public class OxfordDictionariesCom implements PhonemeConfig {
    private String word = null;

    @Override
    public String getPhoneme() {
        HtmlPage page = new SiteConnector().connectAndGetPage(getAddress());
        if (page != null) {
            Object firstByXPath = page.getFirstByXPath("//span[@class='phoneticspelling']");
            if (firstByXPath instanceof DomNode) {
                return ((DomNode) firstByXPath).asText().replaceAll("\\A/|/\\z", "");
            }
        }
        return null;
    }

    @Override
    public String getAddress() {
        return String.format("https://en.oxforddictionaries.com/definition/%s", word);
    }

    @Override
    public void setWord(String word) {
        this.word = word.toLowerCase();
    }

    @Override
    public void setLangFrom(String langFrom) {

    }

    @Override
    public void setLangTo(String langTo) {

    }

}
