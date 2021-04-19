package com.example.words;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {
    private Level level;
    private static final String TAG = "GameFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        level = getArguments().getParcelable("level");
        Log.i(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView charsRV = view.findViewById(R.id.rv_game_characters);
        charsRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        List<Character> uniqueChars = GamePlayUtil.extractUniqueChars(level.getWords());
        List<CharacterPlaceHolder> characterPlaceHolders = new ArrayList<>();

        for (int i = 0; i < uniqueChars.size(); i++) {
            CharacterPlaceHolder characterPlaceHolder = new CharacterPlaceHolder();
            characterPlaceHolder.setVisible(true);
            characterPlaceHolder.setCharacter(uniqueChars.get(i));
            characterPlaceHolders.add(characterPlaceHolder);
        }

        CharacterAdapter characterAdapter = new CharacterAdapter(characterPlaceHolders);
        charsRV.setAdapter(characterAdapter);
    }
}
