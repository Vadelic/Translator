package com.translator.dictionary.site.wiktionary;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public class IPABlock implements ParseChain {
    private ParseChain next;

    @Override
    public ParseChain nextChain(ParseChain chain) {
        next = chain;
        return this;
    }

    @Override
    public List<DomElement> doParse(Iterable<DomElement> elements) {
        List<DomElement> result = new ArrayList<>();
        for (DomElement element : elements) {
            if (element instanceof HtmlUnorderedList)
                result.addAll(((HtmlUnorderedList) element).getElementsByAttribute("span", "class", "IPA"));
        }
        if (next != null && !result.isEmpty())
            return next.doParse(result);
        return result;
    }
}
