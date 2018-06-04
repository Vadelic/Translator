package com.translator.dictionary.site.wiktionary;

import com.gargoylesoftware.htmlunit.html.DomElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komyshenets on 12/7/2017.
 */
class LanguageBlock implements ParseChain {
    private ParseChain next = null;
    private String language;

    LanguageBlock(String language) {
        this.language = language;
    }

    @Override
    public ParseChain nextChain(ParseChain chain) {
        next = chain;
        return this;
    }

    @Override
    public List<DomElement> doParse(Iterable<DomElement> elements) {
        boolean flag = false;
        List<DomElement> result = new ArrayList<>();
        for (DomElement element : elements) {
            if (!flag) {
                flag = startPosition(element);
            } else {
                if (!finishPosition(element))
                    result.add(element);
                else break;
            }
        }

        if (next != null && !result.isEmpty())
            return next.doParse(result);
        return result;
    }

    private boolean startPosition(DomElement element) {
        return "h2".equals(element.getTagName()) && language.equals(element.getFirstElementChild().getId());
    }

    private boolean finishPosition(DomElement element) {
        return "h2".equals(element.getTagName());
    }


}
