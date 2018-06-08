package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.translator.dictionary.PhonemeConfig;
import com.translator.dictionary.TranslateConfig;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Komyshenets on 11/17/2017.
 */
public class VocabolAudioCom extends SiteConnector implements PhonemeConfig, TranslateConfig {
    private final static String URL = "http://%s.vocabolaudio.com/it/%s";
    private String word;
    private String translateLang;


    //    http://ru.vocabolaudio.com/audio-it/sinistro.mp3
    public VocabolAudioCom(String word, String translateLang) {
        this.word = word.toLowerCase();
        this.translateLang = translateLang.toLowerCase();
    }

    public VocabolAudioCom(String wordOriginal) {
        this(wordOriginal, "en");
    }


    @Override
    public String getPhoneme() {
        HtmlPage page = connectAndGetPage(getAddress());
        if (page != null) {
            DomElement elementById = page.getElementById("IPA_" + word);
            if (elementById != null) {
                return elementById.asText().replaceAll("\\A.|.\\z", "");
            }
        }
        return null;
    }

    @Override
    public String getAddress() {
        return String.format(URL, translateLang, word);
    }

    @Override
    public String getTranslate() {
        HtmlPage page = connectAndGetPage(String.format(getAddress()));
        if (page != null) {
            DomElement elementById = page.getElementById("translation_" + word);
            if (elementById != null) {
                DomElement ul = elementById.getFirstElementChild();
                Iterator<DomElement> liIterator = ul.getChildElements().iterator();
                Set<String> translates = new TreeSet<>();
                while (liIterator.hasNext() && translates.size() < 3) {
                    translates.add(liIterator.next().asText().toLowerCase().trim());
                }
                if (!translates.isEmpty())
                    return String.join(", ", translates);
            }
        }
        return null;
    }
}
