package com.example.grimuare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ChangeCharacterFragment extends Fragment implements RecyclerViewInterface{

    public interface CharacterClickListener {
        void onCharacterClick(int position);
    }

    private CharacterClickListener characterClickListener;

    private ArrayList<Character> allCharacters;

    private RecyclerView recyclerView;

    public static ChangeCharacterFragment newInstance(ArrayList<Character> allCharacters,
                                                      CharacterClickListener listener) {
        ChangeCharacterFragment fragment = new ChangeCharacterFragment();
        Bundle args = new Bundle();
        args.putSerializable("allCharacters", allCharacters);
        fragment.setArguments(args);
        fragment.characterClickListener = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_change_character, container, false);

        assert getArguments() != null;
        allCharacters = (ArrayList<Character>) getArguments().getSerializable("allCharacters");

        recyclerView = view.findViewById(R.id.recyclerView);

        Character_RecyclerViewAdapter adapter = new Character_RecyclerViewAdapter(
                getContext(), allCharacters, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        if (characterClickListener != null)
            characterClickListener.onCharacterClick(position);
    }
}