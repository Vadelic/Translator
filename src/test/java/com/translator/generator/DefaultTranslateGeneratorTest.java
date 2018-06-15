package com.translator.generator;

import com.translator.model.Language;
import com.translator.model.Translate;
import com.translator.model.Word;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 14675742 on 15.06.2018.
 */
public class DefaultTranslateGeneratorTest {

    @Test
    public void getTranslate() {


        Language language = new Language("it", "it");
        Word word = new Word("bambino", language);
        System.out.println(word);

        HashMap<String, String> translates = new HashMap<>();
        translates.put("en", "baby, boy, child");
        translates.put("de", "baby, junge, kind");
        translates.put("ru", "мальчик, ребенок");
        translates.put("fi", "lapsi, poika, vauva");


        for (Map.Entry<String, String> entry : translates.entrySet()) {
            Language languageTo = new Language(entry.getKey(), entry.getKey());
            String result = entry.getValue();

            DefaultTranslateGenerator defaultTranslateGenerator = new DefaultTranslateGenerator(word, languageTo);
            Translate translate = defaultTranslateGenerator.getTranslate();


            Assert.assertEquals(translate.getTranslate(), result);
        }

    }
}