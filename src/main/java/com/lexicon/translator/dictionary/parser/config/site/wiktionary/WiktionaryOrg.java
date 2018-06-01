package com.lexicon.translator.dictionary.parser.config.site.wiktionary;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lexicon.translator.dictionary.parser.config.PhonemeConfig;
import com.lexicon.translator.dictionary.parser.config.site.SiteConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public class WiktionaryOrg extends SiteConnector implements PhonemeConfig {
    private final String wordLang;
    private final String word;
    private static Map<String, String> landMap = new HashMap<>();

    static {
        landMap.put("ru", "Russian");
        landMap.put("fi", "Finnish");
        landMap.put("it", "Italian");
        landMap.put("en", "English");
    }

    public WiktionaryOrg(String wordOriginal, String wordLang) {
        this.word = wordOriginal.toLowerCase();
        this.wordLang = landMap.get(wordLang.toLowerCase());
    }

    private ParseChain pronunciationChain(String language) {
        return new LanguageBlock(language)
                .nextChain(new PronunciationBlock()
                        .nextChain(new IPABlock()));
    }


    @Override
    public String getPhoneme() {
        HtmlPage page = connectAndGetPage(String.format("https://en.wiktionary.org/wiki/%s", word));
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
    }
}
