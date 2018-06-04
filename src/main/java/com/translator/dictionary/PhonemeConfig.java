package com.translator.dictionary;


import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public interface PhonemeConfig  {
    @Nullable
    String getPhoneme() throws IOException;
}