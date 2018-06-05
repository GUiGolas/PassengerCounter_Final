package com.example.guilherme.passengercounter_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper {

    // Database Constraints
    private static final String NOME_BANCO = "turismo.db";
    private static final int VERSAO = 1;

    // Constraints for table "viagem"
    public static final String TABELA_VIAGEM = "viagem";
    public static final String ID_VIAGEM = "_id_viagem";
    public static final String ONIBUS = "onibus";
    public static final String COR = "cor";
    public static final String ORIGEM = "origem";
    public static final String DESTINO = "destino";


    // Constraints for table "passageiros"
    public static final String TABELA_PASSAGEIROS = "passageiros";
    public static final String ID_PASSAGEIROS = "_id_passageiros";
    public static final String NOME = "nome";
    public static final String BARCODE = "barcode";
    public static final String QUARTO = "quarto";

    // Constraints for table "contagem"
    public static final String TABELA_CONTAGEM = "contagem";
    public static final String ID_CONTAGEM = "_id_contagem";
    public static final String TIMESTAMP = "timestamp";
    public static final String CHECKEDIN = "checkedin";


    public CreateDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table "viagem"
        String sql = "CREATE TABLE " +TABELA_VIAGEM + "("
                + ID_VIAGEM + " integer primary key autoincrement,"
                + ONIBUS + " text,"
                + COR + " text,"
                + ORIGEM + " text,"
                + DESTINO + " text"
                +")";
        db.execSQL(sql);

        // create table "passageiros"
            sql = "CREATE TABLE " +TABELA_PASSAGEIROS + "("
                + ID_PASSAGEIROS + " integer primary key autoincrement,"
                + NOME + " text,"
                + BARCODE + " text,"
                + QUARTO + " text,"
                + ID_VIAGEM + "integer,"
                    + "FOREIGN KEY (" + ID_VIAGEM + ") REFERENCES " + TABELA_VIAGEM + "(" + ID_VIAGEM + ")"
                +")";
        db.execSQL(sql);

        // create table "Contagem"
        sql = "CREATE TABLE " +TABELA_CONTAGEM + "("
                + ID_CONTAGEM + " integer primary key autoincrement,"
                + ID_VIAGEM + " integer,"
                + ID_PASSAGEIROS + " integer,"
                + TIMESTAMP + " datetime,"
                + CHECKEDIN + "boolean,"
                + "FOREIGN KEY (" + ID_VIAGEM + ") REFERENCES " + TABELA_VIAGEM + "(" + ID_VIAGEM + ")"
                +")";
        db.execSQL(sql);






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONTAGEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PASSAGEIROS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_VIAGEM);;
        onCreate(db);

    }
}
