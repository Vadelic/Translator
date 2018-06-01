package com.lexicon.translator.dictionary;

import java.util.Map;

/**
 * Created by Komyshenets on 12/11/2017.
 */
public interface UsagesGenerator {
    Map<String, Map<String, String>> getUsages();
}
