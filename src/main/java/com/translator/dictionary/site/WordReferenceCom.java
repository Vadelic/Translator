package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.translator.dictionary.PhonemeConfig;
import com.translator.dictionary.TranslateConfig;

import java.io.IOException;

/**
 * Created by Komyshenets on 12/3/2017.
 */
public class WordReferenceCom extends SiteConnector implements PhonemeConfig, TranslateConfig {
    private final static String URL = "http://www.wordreference.com/%s%s/%s";
    private String word;
    private String wordLang;
    private String translateLang;

    public WordReferenceCom(String word, String wordLang, String translateLang) {
        this.translateLang = translateLang;
        this.word = word.toLowerCase();
        this.wordLang = wordLang;
    }


    @Override
    public String getTranslate() {
        HtmlPage page = connectAndGetPage(String.format(URL, wordLang, translateLang, word));
        if (page != null) {
            // TODO: 12/12/2017 parce and return
        }
        return null;
    }


    @Override
    public String getPhoneme() throws IOException {
        HtmlPage page = connectAndGetPage(String.format(URL, wordLang, wordLang, word));
        if (page != null) {
            // TODO: 12/12/2017 parce and return
        }
        return null;
    }
}
