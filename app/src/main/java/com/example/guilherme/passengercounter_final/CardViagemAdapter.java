package com.example.guilherme.passengercounter_final;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CardViagemAdapter extends RecyclerView.Adapter {

    private List<Viagem> viagens;
    private Context context;

    public CardViagemAdapter(List<Viagem> viagens, Context context) {
        this.viagens = viagens;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_viagem, parent, false);

        ViagemViewHolder holder = new ViagemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        ViagemViewHolder holder = (ViagemViewHolder) viewHolder;
        Viagem viagem  = viagens.get(position) ;
        holder.onibus.setText(viagem.getOnibus());
        holder.cor.setText(viagem.getCor());
        holder.origem.setText(viagem.getOrigem());
        holder.destino.setText(viagem.getDestino());
//TODO criar os cardviews para cada contagem
        //TODO: Acabei de criar a logica de Adicionar passageiros
    }

    @Override
    public int getItemCount() {
        return viagens.size();
    }
}
