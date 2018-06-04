package com.translator.dictionary.site;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by Komyshenets on 12/7/2017.
 */
public abstract class SiteConnector {

    protected final Logger log = Logger.getLogger(getClass());

    protected HtmlPage connectAndGetPage(String link) {
        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(false);
            return webClient.getPage(link);
        } catch (IOException | FailingHttpStatusCodeException e) {
            log.warn(String.format("URL %s exeption %s", link, e.getClass()));
            return null;
        }
    }
}
