package com.example.words;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharViewHolder> {
    private List<CharacterPlaceHolder> characterPlaceHolders = new ArrayList<>();

    public void setOnRvItemClickListener(OnRvItemClickListener<CharacterPlaceHolder> onRvItemClickListener) {
        this.onRvItemClickListener = onRvItemClickListener;
    }

    private OnRvItemClickListener<CharacterPlaceHolder> onRvItemClickListener;

    public CharacterAdapter() {

    }

    public CharacterAdapter(List<CharacterPlaceHolder> characterPlaceHolders) {
        this.characterPlaceHolders = characterPlaceHolders;
    }

    @NonNull
    @Override
    public CharViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_char, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharViewHolder holder, int position) {
        holder.bind(characterPlaceHolders.get(position));
    }

    @Override
    public int getItemCount() {
        return characterPlaceHolders.size();
    }

    public class CharViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChar;

        public CharViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChar = itemView.findViewById(R.id.tv_char);
        }

        public void bind(CharacterPlaceHolder characterPlaceHolder) {
            if (characterPlaceHolder.isVisible()) {
                tvChar.setText(characterPlaceHolder.getCharacter().toString());
                tvChar.setVisibility(View.VISIBLE);
            } else tvChar.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRvItemClickListener != null) {
                        onRvItemClickListener.onItemClick(characterPlaceHolder, getAdapterPosition());
                    }
                }
            });
        }
    }
}
