package com.googleappengine.util;

import com.googleappengine.model.json.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.io.Writer;

public class WordUtil {
    public static final Logger LOG = LoggerFactory.getLogger(WordUtil.class.getCanonicalName());

    private WordUtil() {}

    /**
     * Convert a word from {@link Word} obj to json string data.
     *
     * @param word the word to be converted
     * @return the JSON string.
     * @throws JAXBException
     */
    public static String toJson(Word word) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Word.class);

        Writer writer = new StringWriter();
        context.createMarshaller().marshal(word, writer);

        return writer.toString();
    }
}
