package com.translator.generator;

import com.translator.model.Language;
import com.translator.model.Word;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 14675742 on 15.06.2018.
 */
public class DefaultPhoneticGeneratorTest {


    @Test
    public void updatePhonemeFinland() {
        HashMap<String, String> fiWords = new HashMap<>();
        fiWords.put("Lakimies", "lɑkiˌmies");
        fiWords.put("päivää", "pæiʋæː");
        updater("fi", fiWords);

    }


    @Test
    public void updatePhonemeItalian() {
        HashMap<String, String> italianWords = new HashMap<>();
        italianWords.put("bambino", "bamˈbiːno");
        italianWords.put("bambinosss", null);
        italianWords.put("suocera", null);
        updater("it", italianWords);
    }


    private void updater(String lang, HashMap<String, String> italianWords) {
        Language language = new Language(lang, lang);
        for (Map.Entry<String, String> entry : italianWords.entrySet()) {
            Word word = new Word(entry.getKey(), language);
            String result = entry.getValue();
            System.out.println("test: " + word);

            DefaultPhoneticGenerator defaultWordGenerator = new DefaultPhoneticGenerator(word);
            defaultWordGenerator.getPhonetic();
            Assert.assertEquals(word.getPhoneme(), result);
        }
    }
}