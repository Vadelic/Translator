package com.translator.dictionary.site;

import com.translator.dictionary.PhonemeConfig;
import com.translator.dictionary.TranslateConfig;
import com.translator.exception.DictionaryConfigException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 14675742 on 15.06.2018.
 */
public class VocabolAudioComTest {

    @Test
    public void getPhoneme() throws IOException, DictionaryConfigException {

        HashMap<String, String> testWords = new HashMap<>();
        testWords.put("bambino", "bamˈbiːno");
        testWords.put("bambinosss", null);
        testWords.put("suocera", null);
        testWords.put("сhiesa", null);
        testWords.put("chiesa", "ˈkjɛːza");// TODO: 12/14/2017 разобраться с кирилицей

        PhonemeConfig vocabolAudioCom = new VocabolAudioCom();
        for (Map.Entry<String, String> entry : testWords.entrySet()) {
            String wordText = entry.getKey();
            String wordPhoneme = entry.getValue();

            vocabolAudioCom.setWord(wordText);
            String phonetic = vocabolAudioCom.getPhoneme();
            System.out.println(phonetic);
            Assert.assertEquals(phonetic, wordPhoneme);
        }
    }

    @Test
    public void getTranslate() throws IOException, DictionaryConfigException {
        HashMap<String, String> testWords = new HashMap<>();
        testWords.put("bambino", "мальчик, ребенок");
        testWords.put("bambinosss", null);
        testWords.put("suocera", null);

        TranslateConfig vocabolAudioCom = new VocabolAudioCom();
        for (Map.Entry<String, String> entry : testWords.entrySet()) {
            String wordText = entry.getKey();
            String wordPhoneme = entry.getValue();

            vocabolAudioCom.setWord(wordText);
            vocabolAudioCom.setLangTo("ru");

            String translate = vocabolAudioCom.getTranslate();
            System.out.println(translate);
            Assert.assertEquals(translate, wordPhoneme);
        }
    }
}