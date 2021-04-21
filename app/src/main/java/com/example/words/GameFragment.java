package com.example.words;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment implements View.OnClickListener {
    private Level level;
    private static final String TAG = "GameFragment";
    private View guessActionContainer;
    private View btnAccept, btnCancel;
    private CharacterAdapter guessCharsAdapter;
    private CharacterAdapter wordsAdapter;

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

        guessActionContainer = view.findViewById(R.id.frame_game_guessActions);
        btnAccept = view.findViewById(R.id.btn_game_accept);
        btnAccept.setOnClickListener(this);
        btnCancel = view.findViewById(R.id.btn_game_cancel);
        btnCancel.setOnClickListener(this);


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
        characterAdapter.setOnRvItemClickListener(new OnRvItemClickListener<CharacterPlaceHolder>() {
            @Override
            public void onItemClick(CharacterPlaceHolder item, int position) {
                guessActionContainer.setVisibility(View.VISIBLE);
                guessCharsAdapter.add(item);
            }
        });

        RecyclerView rvGuessChars = view.findViewById(R.id.rv_game_guess);
        rvGuessChars.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        guessCharsAdapter = new CharacterAdapter();
        rvGuessChars.setAdapter(guessCharsAdapter);

        int maxLength = 0;
        for (int i = 0; i < level.getWords().size(); i++) {
            if (level.getWords().get(i).length() > maxLength) {
                maxLength = level.getWords().get(i).length();
            }
        }

        RecyclerView rvWords = view.findViewById(R.id.rv_game_words);
        rvWords.setLayoutManager(new GridLayoutManager(getContext(), maxLength, RecyclerView.VERTICAL, false));

        List<CharacterPlaceHolder> wordsCharsPlaceHolders = new ArrayList<>();
        for (int i = 0; i < level.getWords().size(); i++) {
            for (int j = 0; j < maxLength; j++) {
                CharacterPlaceHolder characterPlaceHolder = new CharacterPlaceHolder();
                if (j < level.getWords().get(i).length()) {
                    characterPlaceHolder.setCharacter(level.getWords().get(i).charAt(j));
                    characterPlaceHolder.setNull(false);
                    characterPlaceHolder.setVisible(false);
                    characterPlaceHolder.setTag(level.getWords().get(i));
                } else {
                    characterPlaceHolder.setNull(true);
                }
                wordsCharsPlaceHolders.add(characterPlaceHolder);
            }
        }
        wordsAdapter = new CharacterAdapter(wordsCharsPlaceHolders);
        rvWords.setAdapter(wordsAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAccept)) {
            String word = guessCharsAdapter.getWord();
            for (int i = 0; i < level.getWords().size(); i++) {
                if (word.equals(level.getWords().get(i))) {
                    Toast.makeText(getContext(), "کلمه درست است: " + word, Toast.LENGTH_LONG).show();
                    btnCancel.performClick();
                    wordsAdapter.makeWordVisible(word);
                    return;
                }
            }
            btnCancel.performClick();
            Toast.makeText(getContext(), "دوباره تلاش کنید.", Toast.LENGTH_LONG).show();
        }

        if (v.equals(btnCancel)) {
            guessActionContainer.setVisibility(View.GONE);
            guessCharsAdapter.clear();
        }

    }
}
