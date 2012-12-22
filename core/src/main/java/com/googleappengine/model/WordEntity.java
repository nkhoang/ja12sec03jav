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
    public enum WordType {
        ADJECTIVE, ADVERB, NOUN, PRONOUN, VERB
    }

    public WordEntity() {
    }

    public WordEntity(String description, String type) {
        this.word = description;
        this.wordType = type;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    private String word;
    @Lob
    @Column(name = "DATA")
    private Text wordJSON;
    @Basic
    private Long timeStamp;

    @Basic
    @Column(name = "WORD_TYPE")
    private String wordType;

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

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    @PrePersist
    public void prePersist() {
        this.timeStamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("[id: %s, description: %s, time: %s, type: %s]", getId(), getWord(), getTimeStamp(),
                getWordType());
    }
}
