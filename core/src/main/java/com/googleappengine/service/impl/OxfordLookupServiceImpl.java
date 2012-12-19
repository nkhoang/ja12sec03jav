package com.googleappengine.service.impl;

import com.googleappengine.model.json.Meaning;
import com.googleappengine.model.json.Phrase;
import com.googleappengine.model.json.Sense;
import com.googleappengine.model.json.Word;
import com.googleappengine.service.LookupService;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

/**
 * Oxford Lookup Service.
 */
@Service(value = "oxfordLookupService")
public class OxfordLookupServiceImpl implements LookupService {
    private static final Logger LOG = LoggerFactory
            .getLogger(OxfordLookupServiceImpl.class.getCanonicalName());
    private static final String SERVICE_NAME = "oxford";
    private static final String OXFORD_URL_LINK = "http://oxforddictionaries.com/definition/english/";
    // 10 seconds is just enough, do not need to wait more.
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final String HTML_ATTR_CLASS = "class";

    /**
     * {@inheritDoc}
     */
    public String getServiceName() {
        return SERVICE_NAME;
    }

    private String getPronunciation(Source source) {
        List<Element> prounEles = source.getAllElementsByClass("entryPronunciation");
        if (CollectionUtils.isNotEmpty(prounEles)) {
            // under <a> tag.
            List<Element> childEles = prounEles.get(0).getAllElements("a");
            // only one <a> tag.
            return childEles.get(0).getTextExtractor().toString();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Word lookup(String word) {
        Word w = null;
        try {
            LOG.debug(String.format("Connecting to ... [%s] ", OXFORD_URL_LINK + word));
            URL url = new URL(OXFORD_URL_LINK + word);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(CONNECTION_TIMEOUT);
            connection.setRequestMethod("GET");
            // get inputStream
            InputStream is = connection.getInputStream();
            // create source HTML
            Source source = new Source(is);
            if (source != null) {
                w = new Word();
                w.setTimeStamp(System.currentTimeMillis());
                w.setSourceName(SERVICE_NAME);
                w.setDescription(word.toLowerCase());
                // get pronunciation.
                String pron = getPronunciation(source);
                if (StringUtils.isNotEmpty(pron.trim())) {
                    w.setPron(pron.replaceAll("Pronunciation: ", ""));
                }

                // start the body content.
                List<Element> definitionBodyList = source.getAllElementsByClass("senseGroup");
                // there is only one element like that.
                for (Element senseGroup : definitionBodyList) {

                    List<Element> definitionChildEles = senseGroup.getChildElements();
                    String kind = null;
                    String languageGroup = null;
                    String grammarGroup = null;
                    for (Element child : definitionChildEles) {
                        // collect kind.
                        if (checkElementName(child, "h3") &&
                                checkElementProperty(child, HTML_ATTR_CLASS, "partOfSpeech")) {
                            List<Element> partOfSpeechList = child.getChildElements();
                            try {
                                kind = partOfSpeechList.get(0).getTextExtractor().toString();
                            } catch (RuntimeException re) {
                                // ignore it.
                            }
                        } else if (checkElementName(child, "span") &&
                                checkElementProperty(child, HTML_ATTR_CLASS, "grammarGroup")) {
                            String s = child.getTextExtractor().toString();
                            grammarGroup = StringUtils.isNotBlank(s) ? s : null;
                        } else if (checkElementName(child, "em") &&
                                checkElementProperty(child, HTML_ATTR_CLASS, "languageGroup")) {
                            String s = child.getTextExtractor().toString();
                            languageGroup = StringUtils.isNotBlank(s) ? s : null;
                            // collect sense to start a group of meanings.
                        } else if (checkElementName(child, "ul") &&
                                checkElementProperty(child, HTML_ATTR_CLASS, "sense-entry")) {
                            List<Element> senseEntries = child.getChildElements();
                            if (CollectionUtils.isNotEmpty(senseEntries)) {
                                Sense sense = null;
                                for (Element entry : senseEntries) {
                                    if (checkElementProperty(entry, HTML_ATTR_CLASS, "sense")) {
                                        if (sense != null) {
                                            w.addMeaning(kind, sense);
                                        }
                                        sense = processSense(entry);
                                        if (sense != null) {
                                            sense.setKind(kind);
                                            if (StringUtils.isNotEmpty(grammarGroup)) {
                                                sense.setGrammarGroup(grammarGroup);
                                                grammarGroup = null;
                                            }
                                            if (StringUtils.isNotEmpty(languageGroup)) {
                                                sense.setLanguageGroup(languageGroup);
                                                languageGroup = null;
                                            }
                                        }
                                    } else if (checkElementProperty(entry, HTML_ATTR_CLASS, "subSense")) {
                                        if (sense != null) {
                                            Meaning m = processSubSense(entry);
                                            if (m != null)
                                                sense.getSubSenses().add(m);
                                        }
                                    }
                                }
                                if (sense != null) {
                                    w.addMeaning(kind, sense);
                                }
                            }
                        }
                    }
                }
                // process phrases.
                List<Element> phraseEntryList = source.getAllElementsByClass("subEntryBlock");
                if (CollectionUtils.isNotEmpty(phraseEntryList)) {
                    for (Element phraseEle : phraseEntryList) {
                        processPhrase(phraseEle, w);
                    }
                }

            }
        } catch (SocketTimeoutException sktoe) {
            LOG.info("Time out while fetching : " + OXFORD_URL_LINK + word);
        } catch (Exception e) {
            LOG.error("Error fetching word using URL: " + OXFORD_URL_LINK + word, e);
        }
        return w;
    }

    private Meaning processSubSense(Element e) {
        Meaning m = new Meaning();
        List<Element> eles = e.getAllElements();
        if (CollectionUtils.isNotEmpty(eles)) {
            for (Element ele : eles) {
                if (checkElementProperty(ele, HTML_ATTR_CLASS, "grammarGroup")) {
                    try {
                        String s = ele.getAllElements("em").get(0).getTextExtractor().toString();
                        if (StringUtils.isNotEmpty(s)) {
                            m.setGrammarGroup(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                } else if (checkElementProperty(ele, HTML_ATTR_CLASS, "languageGroup")) {
                    try {
                        String s = ele.getAllElements("em").get(0).getTextExtractor().toString();
                        if (StringUtils.isNotBlank(s)) {
                            m.setLanguageGroup(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                } else if (checkElementProperty(ele, HTML_ATTR_CLASS, "wordForm")) {
                    try {
                        String s = ele.getTextExtractor().toString();
                        if (StringUtils.isNotBlank(s)) {
                            m.setWordForm(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                } else if (checkElementProperty(ele, HTML_ATTR_CLASS, "definition")) {
                    try {
                        String s = ele.getTextExtractor().toString();
                        if (StringUtils.isNotEmpty(s)) {
                            m.setContent(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                } else if (checkElementProperty(ele, HTML_ATTR_CLASS, "exampleGroup")) {
                    try {
                        String s = ele.getTextExtractor().toString();
                        if (StringUtils.isNotBlank(s)) {
                            m.getExamples().add(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                }
            }
        } else {
            m = null;
        }
        return m;
    }

    private void processPhrase(Element e, Word w) {
        Phrase phrase = null;

        List<Element> dls = e.getAllElements("dl");
        if (CollectionUtils.isNotEmpty(dls)) {
            phrase = null;
            String phraseKind = null;
            // get phrase title
            List<Element> titleList = e.getAllElements("h2");
            if (CollectionUtils.isNotEmpty(titleList)) {
                phraseKind = titleList.get(0).getTextExtractor().toString();
            }
            // get meanings.
            List<Element> phraseEntryList = dls.get(0).getChildElements();
            for (Element child : phraseEntryList) {
                if (checkElementName(child, "dt")) {
                    if (phrase != null && StringUtils.isNotEmpty(phrase.getDescription())) {
                        w.addPhrase(phraseKind, phrase);
                    }
                    phrase = new Phrase();
                    String s = child.getTextExtractor().toString();
                    if (StringUtils.isNotBlank(s)) {
                        phrase.setDescription(s);
                    }
                } else if (checkElementName(child, "dd")) {
                    List<Element> senseEntries = child.getChildElements();
                    if (CollectionUtils.isNotEmpty(senseEntries)) {
                        boolean isDIVCase = false;
                        Sense sense = null;
                        for (Element senseEntry : senseEntries) {
                            // there are 2 cases: one is div and ul > subsense, or just ul > sense.
                            if (checkElementProperty(senseEntry, HTML_ATTR_CLASS, "sense") && checkElementName(senseEntry, "div")) {
                                isDIVCase = true;
                                sense = processSense(senseEntry);
                                if (sense != null) {
                                    phrase.getSenseList().add(sense);
                                }
                            } else if (checkElementName(senseEntry, "ul") && isDIVCase) {
                                List<Element> subSenses = senseEntry.getChildElements();
                                if (CollectionUtils.isNotEmpty(subSenses)) {
                                    for (Element subSense : subSenses) {
                                        Meaning m = processSubSense(subSense);
                                        if (m != null) {
                                            sense.getSubSenses().add(m);
                                        }
                                    }
                                }
                            } else if (checkElementName(senseEntry, "ul") && !isDIVCase) {
                                List<Element> senseList = senseEntry.getChildElements();
                                if (CollectionUtils.isNotEmpty(senseList)) {
                                    for (Element senseEle : senseList) {
                                        if (checkElementProperty(senseEle, HTML_ATTR_CLASS, "sense")) {
                                            sense = null;
                                            sense = processSense(senseEle);
                                            if (sense != null) {
                                                phrase.getSenseList().add(sense);
                                            }
                                        } else if (checkElementProperty(senseEle, HTML_ATTR_CLASS, "subSense")) {
                                            if (sense != null) {
                                                Meaning m = processSubSense(senseEle);
                                                if (m != null)
                                                    sense.getSubSenses().add(m);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * The element <i>e</i> is the element contains the sense definition.
     *
     * @param e the element that contains the sense definition.
     * @return a constructed {@link Sense} object.
     */

    private Sense processSense(Element e) {
        Sense sense = new Sense();
        List<Element> eles = e.getAllElements();
        if (CollectionUtils.isNotEmpty(eles)) {
            for (Element ele : eles) {
                if (checkElementProperty(ele, HTML_ATTR_CLASS, "grammarGroup")) {
                    try {
                        String s = ele.getAllElements("em").get(0).getTextExtractor().toString();
                        if (StringUtils.isNotBlank(s)) {
                            sense.setGrammarGroup(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                } else if (checkElementProperty(ele, HTML_ATTR_CLASS, "languageGroup")) {
                    try {
                        String s = ele.getAllElements("em").get(0).getTextExtractor().toString();
                        if (StringUtils.isNotBlank(s)) {
                            sense.setLanguageGroup(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                } else if (checkElementProperty(ele, HTML_ATTR_CLASS, "definition")) {
                    try {
                        String s = ele.getTextExtractor().toString();
                        if (StringUtils.isNotBlank(s)) {
                            sense.setDefinition(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                } else if (checkElementProperty(ele, HTML_ATTR_CLASS, "exampleGroup")) {
                    try {
                        String s = ele.getTextExtractor().toString();
                        if (StringUtils.isNotBlank(s)) {
                            sense.getExamples().add(s);
                        }
                    } catch (RuntimeException re) {
                        // ignore it.
                    }
                }
            }
        } else {
            sense = null;
        }
        if (StringUtils.isEmpty(sense.getDefinition())) {
            return null;
        }
        return sense;
    }

    private boolean checkElementName(Element e, String type) {
        return StringUtils.equals(type, e.getName());
    }

    private boolean checkElementProperty(Element e, String propertyType, String propertyValue) {
        return e.getAttributeValue(propertyType) != null && e.getAttributeValue(propertyType).contains(propertyValue);
    }
}
