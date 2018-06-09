package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.translator.dictionary.PhonemeConfig;
import com.translator.dictionary.TranslateConfig;
import com.translator.utils.SiteConnector;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Komyshenets on 11/17/2017.
 */
public class VocabolAudioCom implements PhonemeConfig, TranslateConfig {
    private String word = null;
    private String translateLang = null;
    //    http://ru.vocabolaudio.com/audio-it/sinistro.mp3

    @Override
    public String getAddress() {
        return String.format("http://%s.vocabolaudio.com/it/%s", translateLang, word);
    }

    @Override
    public void setWord(String word) {
        this.word = word.toLowerCase();
    }

    @Override
    public void setLangTo(String langTo) {
        translateLang = langTo.toLowerCase();
    }

    @Override
    public void setLangFrom(String langFrom) {
        //only italian words
    }

    @Override
    public String getPhoneme() {
        setLangTo("en");
        HtmlPage page = new SiteConnector().connectAndGetPage(getAddress());
        if (page != null) {
            DomElement elementById = page.getElementById("IPA_" + word);
            if (elementById != null) {
                return elementById.asText().replaceAll("\\A.|.\\z", "");
            }
        }
        return null;
    }

    @Override
    public String getTranslate() {
        HtmlPage page = new SiteConnector().connectAndGetPage(getAddress());
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
