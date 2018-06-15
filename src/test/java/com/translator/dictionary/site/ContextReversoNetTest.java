package com.translator.dictionary.site;

import com.translator.dictionary.UsagesConfig;
import com.translator.dictionary.site.ContextReversoNet;
import com.translator.exception.DictionaryConfigException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Komyshenets on 12/12/2017.
 */
public class ContextReversoNetTest {

    @Test
    public void getUsages() throws IOException, DictionaryConfigException {
        assertUsages(15, getConfig("suocera", "it", "en"));
        assertUsages(0, getConfig("suocera", "---", "en"));
    }

    private void assertUsages(int size, UsagesConfig contextReversoNet) throws IOException, DictionaryConfigException {
        Map<String, String> usages = contextReversoNet.getUsages();
        Assert.assertEquals(size, usages.size());
    }

    private ContextReversoNet getConfig(String wordText, String langFrom, String langTo) throws DictionaryConfigException {
        ContextReversoNet reversoNet = new ContextReversoNet();
        reversoNet.setWord(wordText);
        reversoNet.setLangFrom(langFrom);
        reversoNet.setLangTo(langTo);
        return reversoNet;
    }
}