package com.lexicon.translator.dictionary.parser.config;


import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public interface TranslateConfig  {
    @Nullable
    String getTranslate() throws IOException;

}
