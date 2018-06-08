package com.translator.dictionary;


import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public interface UsagesConfig {
    @Nullable
    Map<String, String> getUsages() throws IOException;

    String getAddress();
}
