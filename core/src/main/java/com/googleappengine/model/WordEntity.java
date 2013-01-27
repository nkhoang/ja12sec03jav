package com.googleappengine.model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "WORD")
public class WordEntity extends BaseEntity implements Serializable{
    /**
     * Word Type.
     */
    public static enum WordType {
        ADJECTIVE, ADVERB, NOUN, PRONOUN, VERB
    }

    public static enum WordDict {
        OXFORD, VDICT, EN, VN
    }

    public WordEntity() {
    }

    public WordEntity(WordDict dictType, WordType wordType, String description, String text) {
        this.dictType = dictType;
        this.word = description;
        this.wordJSON = new Text(text);
        this.wordType = wordType;
    }

    public WordEntity(WordDict type, String description) {
        this.dictType = type;
        this.word = description;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    private String word;
    @Lob
    @Column(name = "DATA")
    private Text wordJSON;
    @Basic
    private Long timeStamp;
    @Enumerated
    @Column(name = "DICT_TYPE")
    private WordDict dictType;
    @Enumerated
    @Column(name = "WORD_TYPE")
    private WordType wordType;

    @Override
    @Id
    @Column(name = "WORD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key getId() {
        return super.getId();
    }

    @Override
    public void setId(Key id) {
        super.setId(id);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Text getWordJSON() {
        return wordJSON;
    }

    public void setWordJSON(Text wordJSON) {
        this.wordJSON = wordJSON;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @PrePersist
    public void prePersist() {
        this.timeStamp = System.currentTimeMillis();
    }

    public WordDict getDictType() {
        return dictType;
    }

    public void setDictType(WordDict dictType) {
        this.dictType = dictType;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    @Override
    public String toString() {
        return String.format("[id: %s, description: %s, time: %s, dictType: %s]", getId(), getWord(), getTimeStamp(),
                getDictType().name());
    }
}
