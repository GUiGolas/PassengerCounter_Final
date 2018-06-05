package com.example.guilherme.passengercounter_final;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ger recyclerview reference
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        List<Viagem> viagens = new ArrayList<Viagem>();

        /* ACRESCENTA VIAGENS NA LISTA */

        Viagem novaViagem = new Viagem("Cometa1","azul","bh","sp",1524);
        viagens.add(novaViagem);

        novaViagem = new Viagem("São Geraldo","verde","op","ub",13248);
        viagens.add(novaViagem);

        novaViagem = new Viagem("Itapemirim","amarelo","bh","vix",98754);
        viagens.add(novaViagem);

        novaViagem = new Viagem("Util","roxo","df","ma",14563);
        viagens.add(novaViagem);

        /* ACRESCENTA VIAGENS NA LISTA */

        recyclerView.setAdapter(new CardViagemAdapter(viagens, this));

        LinearLayoutManager layout = new LinearLayoutManager(context);
        //RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
        //        LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

    }
}
