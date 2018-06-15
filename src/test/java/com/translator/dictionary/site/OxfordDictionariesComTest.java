package com.translator.dictionary.site;

import com.translator.dictionary.PhonemeConfig;
import com.translator.dictionary.site.OxfordDictionariesCom;
import com.translator.exception.DictionaryConfigException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Komyshenets on 12/11/2017.
 */
public class OxfordDictionariesComTest {

    @Test
    public void getPhoneme() throws IOException, DictionaryConfigException {
        HashMap<String, String> testWords = new HashMap<>();
        testWords.put("imagine", "ɪˈmadʒɪn");
        testWords.put("dsdvsdvv", null);
        testWords.put("auto", "ˈɔːtəʊ");

        for (Map.Entry<String, String> entry : testWords.entrySet()) {
            String wordText = entry.getKey();
            String wordPhoneme = entry.getValue();

            PhonemeConfig config = new OxfordDictionariesCom();
            config.setWord(wordText);
            String phonetic = config.getPhoneme();
            System.out.println(phonetic);
            Assert.assertEquals(phonetic, wordPhoneme);
        }
    }


}