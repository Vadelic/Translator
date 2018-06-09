package com.translator.dictionary;


import com.translator.exception.DictionaryConfigException;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public interface UsagesConfig extends Config {
    @Nullable
    Map<String, String> getUsages() throws DictionaryConfigException;
}
