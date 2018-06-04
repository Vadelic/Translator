package com.translator.dictionary.site.wiktionary;

import com.gargoylesoftware.htmlunit.html.DomElement;

import java.util.List;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public interface ParseChain {
    ParseChain nextChain(ParseChain chain);
    List<DomElement> doParse(Iterable<DomElement> elements);
}
