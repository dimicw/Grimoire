package com.example.grimuare;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class BrowseSpellsFragment extends Fragment implements RecyclerViewInterface{

    ArrayList<ChosenSpell> chosenSpells;
    int[] classImages;

    RecyclerView recyclerView;

    public static BrowseSpellsFragment newInstance(ArrayList<ChosenSpell> chosenSpells,
                                                   int[] classImages) {
        BrowseSpellsFragment fragment = new BrowseSpellsFragment();
        Bundle args = new Bundle();
        args.putSerializable("chosenSpells", chosenSpells);
        args.putIntArray("classImages", classImages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_spells, container, false);

        assert getArguments() != null;
        chosenSpells = (ArrayList<ChosenSpell>) getArguments().getSerializable("chosenSpells");
        classImages = getArguments().getIntArray("classImages");

        recyclerView = view.findViewById(R.id.recyclerView);

        Spell_RecyclerViewAdapter adapter = new Spell_RecyclerViewAdapter(
                getContext(), chosenSpells, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), SpellCardActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("SPELL", chosenSpells.get(position));
        intent.putExtras(bundle);

        startActivity(intent);
    }
}