package com.googleappengine.model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import javax.persistence.*;

@Entity
@Table(name = "WORD")
public class WordEntity extends BaseEntity {
    /**
     * Word Type.
     */
    public static enum WordType {
        ADJECTIVE, ADVERB, NOUN, PRONOUN, VERB
    }

    public static enum WordDict {
        OXFORD, VDICT
    }

    public WordEntity() {
    }

    public WordEntity(WordDict type, String description, String text) {
        this.dictType = type;
        this.word = description;
        this.wordJSON = new Text(text);
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

    @Override
    public String toString() {
        return String.format("[id: %s, description: %s, time: %s, dictType: %s]", getId(), getWord(), getTimeStamp(),
                getDictType().name());
    }
}
