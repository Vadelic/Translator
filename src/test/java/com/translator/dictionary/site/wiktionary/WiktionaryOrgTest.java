package com.translator.dictionary.site.wiktionary;

import com.translator.dictionary.PhonemeConfig;
import com.translator.exception.DictionaryConfigException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public class WiktionaryOrgTest {

    @Test
    public void parsePageEnglish() throws IOException, DictionaryConfigException {
        {
            PhonemeConfig wiktionaryOrgPhonetic = new WiktionaryOrg();
            wiktionaryOrgPhonetic.setWord("auto");
            wiktionaryOrgPhonetic.setLangFrom("en");
            String parseResult = wiktionaryOrgPhonetic.getPhoneme();
            Assert.assertEquals(parseResult, "ɔtoʊ");
        }
        {
            PhonemeConfig wiktionaryOrgPhonetic = new WiktionaryOrg();
            wiktionaryOrgPhonetic.setWord("suspect");
            wiktionaryOrgPhonetic.setLangFrom("en");
            String parseResult = wiktionaryOrgPhonetic.getPhoneme();
            Assert.assertEquals(parseResult, "sʌs.pɛkt");
        }
    }

    @Test
    public void parsePageFinland() throws IOException, DictionaryConfigException {
        {
            PhonemeConfig wiktionaryOrgPhonetic = new WiktionaryOrg();
            wiktionaryOrgPhonetic.setWord("sakki");
            wiktionaryOrgPhonetic.setLangFrom("fi");
            String parseResult = wiktionaryOrgPhonetic.getPhoneme();
            Assert.assertEquals(parseResult, null);
        }
        {
            PhonemeConfig wiktionaryOrgPhonetic = new WiktionaryOrg();
            wiktionaryOrgPhonetic.setWord("Lakimies");
            wiktionaryOrgPhonetic.setLangFrom("fi");
            String parseResult = wiktionaryOrgPhonetic.getPhoneme();
            Assert.assertEquals(parseResult, "lɑkiˌmies");
        }

        {
            PhonemeConfig wiktionaryOrgPhonetic = new WiktionaryOrg();
            wiktionaryOrgPhonetic.setWord("lakimies");
            wiktionaryOrgPhonetic.setLangFrom("fi");
            String parseResult = wiktionaryOrgPhonetic.getPhoneme();
            Assert.assertEquals(parseResult, "lɑkiˌmies");
        }
    }

    @Test
    public void parsePageItalian() throws IOException, DictionaryConfigException {
        {
            PhonemeConfig wiktionaryOrgPhonetic = new WiktionaryOrg();
            wiktionaryOrgPhonetic.setWord("arancione");
            wiktionaryOrgPhonetic.setLangFrom("it");

            String parseResult = wiktionaryOrgPhonetic.getPhoneme();
            Assert.assertEquals(parseResult, "a.ranˈtʃo.ne");
        }
    }
}