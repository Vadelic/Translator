package com.translator.dictionary.site;

import com.translator.dictionary.UsagesConfig;
import com.translator.dictionary.site.GlosbeCom;
import com.translator.exception.DictionaryConfigException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public class GlosbeComTest {

    @Test
    public void getTranslate() {
        HashMap<String, String> testWords = new HashMap<>();
        testWords.put("päivää", "добрый день, здравствуйте, привет");
        testWords.put("-----", null);
        testWords.put("suocera", null);
        executeTranslate(testWords, "fi", "ru");

        testWords = new HashMap<>();
        testWords.put("päivää", null);
        testWords.put("-----", null);
        testWords.put("suocera", "свекровь, теща, тёща");
        executeTranslate(testWords, "it", "ru");


        testWords = new HashMap<>();
        testWords.put("imagine", "воображать, вообразить, представлять");
        testWords.put("päivääqwqw", null);
        executeTranslate(testWords, "en", "ru");


    }

    private void executeTranslate(HashMap<String, String> testWords, String langFrom, String langTo) {
        for (Map.Entry<String, String> entry : testWords.entrySet()) {
            String wordText = entry.getKey();
            String wordPhoneme = entry.getValue();

            GlosbeCom glosbeCom = getConfig(wordText, langFrom, langTo);
            String phonetic = glosbeCom.getTranslate();
            Assert.assertEquals(phonetic, wordPhoneme);
        }
    }

    private GlosbeCom getConfig(String wordText, String langFrom, String langTo) {
        GlosbeCom glosbeCom = new GlosbeCom();
        glosbeCom.setWord(wordText);
        glosbeCom.setLangFrom(langFrom);
        glosbeCom.setLangTo(langTo);
        return glosbeCom;
    }

    @Test
    public void getUsages() throws IOException, DictionaryConfigException {
        assertUsages(24, getConfig("suocera", "it", "en"));
        assertUsages(0, getConfig("---", "---", "en"));
        assertUsages(25, getConfig("lakimies", "fi", "ru"));
    }

    private void assertUsages(int size, UsagesConfig contextReversoNet) throws IOException, DictionaryConfigException {
        Map<String, String> usages = contextReversoNet.getUsages();
        System.out.println(usages.size());
        Assert.assertEquals(size, usages.size());
    }
}