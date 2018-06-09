package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.translator.dictionary.PhonemeConfig;
import com.translator.dictionary.TranslateConfig;
import com.translator.exception.DictionaryConfigException;
import com.translator.utils.SiteConnector;

import java.io.IOException;

/**
 * Created by Komyshenets on 12/3/2017.
 */
public class WordReferenceCom implements PhonemeConfig, TranslateConfig {
    private String word = null;
    private String wordLang = null;
    private String translateLang = null;

    @Override
    public String getAddress() {
        return String.format("http://www.wordreference.com/%s%s/%s", wordLang, translateLang, word);
    }

    @Override
    public void setWord(String word) {
        this.word = word.toLowerCase();
    }

    @Override
    public void setLangFrom(String langFrom) {
        this.wordLang = langFrom.toLowerCase();
    }

    @Override
    public void setLangTo(String langTo) {
        translateLang = langTo.toLowerCase();
    }


    @Override
    public String getTranslate() {
        HtmlPage page = new SiteConnector().connectAndGetPage(getAddress());
        if (page != null) {
            // TODO: 12/12/2017 parce and return
        }
        return null;
    }

    @Override
    public String getPhoneme() throws DictionaryConfigException {
        HtmlPage page = new SiteConnector().connectAndGetPage(getAddress());
        if (page != null) {
            // TODO: 12/12/2017 parce and return
        }
        return null;
    }
}
