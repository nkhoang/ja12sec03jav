package com.googleappengine.model;

import com.google.appengine.api.datastore.Text;

import javax.persistence.*;

@Entity
public class WordEntity extends BaseEntity {
    @Basic
    private String word;
    @Lob
    private Text wordJSON;
    @Basic
    private Long timeStamp;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
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
}
