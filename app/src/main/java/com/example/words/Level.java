package com.example.words;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int id;
    private List<String> words = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
