package com.example.grimuare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Spell_RecyclerViewAdapter extends RecyclerView.Adapter<Spell_RecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<SpellModel> spellModels;

    public Spell_RecyclerViewAdapter (Context context, ArrayList<SpellModel> spellModels,
                                      RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.spellModels = spellModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Spell_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new Spell_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Spell_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(spellModels.get(position).getName());
        holder.tvLevelAndSchool.setText(spellModels.get(position).getLevelAndSchool());
        holder.imageView.setImageResource(spellModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return spellModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, tvLevelAndSchool;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.classSymbol);
            tvName = itemView.findViewById(R.id.spellName);
            tvLevelAndSchool = itemView.findViewById(R.id.levelAndSchool);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION)
                            recyclerViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}