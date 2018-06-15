package com.translator.dictionary.site.wiktionary;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.translator.dictionary.PhonemeConfig;
import com.translator.utils.SiteConnector;
import com.translator.exception.DictionaryConfigException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public class WiktionaryOrg implements PhonemeConfig {
    private final Logger log = Logger.getLogger(getClass());

    private String wordLang = null;
    private String word = null;


    @Override
    public void setWord(String word) {
        this.word = word.toLowerCase();
    }

    @Override
    public void setLangFrom(String langFrom) throws DictionaryConfigException {
        Properties properties = getLangProperties("WiktionaryOrg.properties");
        this.wordLang = properties.getProperty(langFrom.toLowerCase());
    }

    @Override
    public void setLangTo(String langTo) {
    }


    @Override
    public String getAddress() {
        return String.format("https://en.wiktionary.org/wiki/%s", word);
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

    private ParseChain pronunciationChain(String language) {
        return new LanguageBlock(language)
                .nextChain(new PronunciationBlock()
                        .nextChain(new IPABlock()));
    }


    @Override
    public String getPhoneme() throws DictionaryConfigException {
        try {
            HtmlPage page = new SiteConnector().connectAndGetPage(getAddress());
            if (page != null) {
                HtmlElement documentElement = page.getDocumentElement();
                List<HtmlElement> parserOutput = documentElement.getElementsByAttribute("div", "class", "mw-parser-output");
                for (HtmlElement htmlElement : parserOutput) {
                    List<DomElement> domElements = null;
                    try {
                        domElements = pronunciationChain(wordLang).doParse(htmlElement.getChildElements());
                    } catch (NullPointerException ignored) {
                    }
                    if (domElements != null && !domElements.isEmpty()) {
                        return domElements.get(0).asText().replaceAll("\\A ?[\\W]{1,2}|[\\W] ?\\z", "");
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new DictionaryConfigException(String.format("Error while parsing phoneme: %s", getAddress()), e);
        }
    }


}
