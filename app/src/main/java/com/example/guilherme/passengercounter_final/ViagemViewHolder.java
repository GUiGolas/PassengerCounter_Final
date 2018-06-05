package com.example.guilherme.passengercounter_final;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViagemViewHolder extends RecyclerView.ViewHolder {

    final TextView onibus;
    final TextView cor;
    final TextView origem;
    final TextView destino;
    CardView cv;

    public ViagemViewHolder(View view) {
        super(view);

        cv = (CardView)itemView.findViewById(R.id.cv);
        onibus = (TextView) itemView.findViewById(R.id.item_viagem_onibus);
        cor = (TextView) view.findViewById(R.id.item_viagem_cor);
        origem = (TextView) view.findViewById(R.id.item_viagem_origem);
        destino = (TextView) view.findViewById(R.id.item_viagem_destino);


    }




}
