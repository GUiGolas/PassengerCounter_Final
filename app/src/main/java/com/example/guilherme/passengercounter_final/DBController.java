package com.example.guilherme.passengercounter_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBController {

    private SQLiteDatabase db;
    private CreateDB banco;

    public DBController(Context context){
        banco = new CreateDB(context);
    }

    public String InsereDbViagem(String _onibus, String _cor, String _origem, String _destino){

        ContentValues values ;
        long resultado;

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put(CreateDB.ONIBUS,_onibus);
        values.put(CreateDB.COR, _cor);
        values.put(CreateDB.ORIGEM, _origem);
        values.put(CreateDB.DESTINO, _destino);

        resultado = db.insert(CreateDB.TABELA_VIAGEM, null, values);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return " Registro Inserido com sucesso" ;

    }

    public String InsereDbPassageiros(String _nome, String _barcode , String _quarto, String _id_viagem){

        ContentValues values ;
        long resultado;

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put(CreateDB.NOME,_nome);
        values.put(CreateDB.BARCODE, _barcode);
        values.put(CreateDB.QUARTO, _quarto);
        values.put(CreateDB.ID_VIAGEM, _id_viagem);

        resultado = db.insert(CreateDB.TABELA_PASSAGEIROS, null, values);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return " Registro Inserido com sucesso" ;

    }

    public String InsereDbContagem(String _id_viagem, String _id_passageiro , String _checked){

        ContentValues values ;
        long resultado;

        Long tsLong = System.currentTimeMillis()/1000;
        String _timestamp = tsLong.toString();

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put(CreateDB.ID_PASSAGEIROS,_id_passageiro);
        values.put(CreateDB.CHECKEDIN, _checked);
        values.put(CreateDB.TIMESTAMP, _timestamp);
        values.put(CreateDB.ID_VIAGEM, _id_viagem);

        resultado = db.insert(CreateDB.TABELA_CONTAGEM, null, values);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return " Registro Inserido com sucesso" ;

    }


    public Cursor carregaDdViagem(){
        Cursor cursor;
        String[] campos =  {banco.ID_VIAGEM,banco.ONIBUS, banco.COR, banco.ORIGEM, banco.DESTINO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_VIAGEM, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDdPassageiros(String _id_viagem){
        Cursor cursor;
        String[] campos =  {banco.ID_VIAGEM,banco.ID_PASSAGEIROS, banco.NOME, banco.BARCODE, banco.QUARTO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PASSAGEIROS, campos, banco.ID_VIAGEM + " = ?" , new String[] {_id_viagem}, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDdContagem(String _id_viagem){
        Cursor cursor;
        String[] campos =  {banco.ID_CONTAGEM,banco.ID_VIAGEM, banco.ID_PASSAGEIROS, banco.TIMESTAMP, banco.CHECKEDIN};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CONTAGEM, campos, banco.ID_VIAGEM + " = ?" , new String[] {_id_viagem}, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public int contaTotalPassageirosContagem(String _id_viagem, String _id_contagem){
        String selection = banco.ID_CONTAGEM + "= ?  AND " + banco.ID_VIAGEM + "=? ";
        Cursor cursor;
        String[] campos =  {banco.ID_CONTAGEM,banco.ID_VIAGEM, banco.ID_PASSAGEIROS, banco.TIMESTAMP, banco.CHECKEDIN};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CONTAGEM, campos, selection, new String[] {_id_contagem, _id_viagem}, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();

        return  cursor.getCount();
    }

    public int contaTotalContagemCheckedIn(String _id_viagem, String _id_contagem){
        String selection = banco.ID_CONTAGEM + "= ?  AND " + banco.ID_VIAGEM + "=? AND " + banco.CHECKEDIN +"= '1' ";
        Cursor cursor;
        String[] campos =  {banco.ID_CONTAGEM,banco.ID_VIAGEM, banco.ID_PASSAGEIROS, banco.TIMESTAMP, banco.CHECKEDIN};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CONTAGEM, campos, selection, new String[] {_id_contagem, _id_viagem }, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();

        return  cursor.getCount();
    }

    public String selecionaViagemId(String _onibus, String _cor, String _origem, String _destino){
        String selection = banco.ONIBUS + "= ?  AND " + banco.COR + "=? AND " + banco.ORIGEM + "=? AND "+ banco.DESTINO +"= ? ";
        Cursor cursor;
        String[] campos =  {banco.ID_VIAGEM,banco.ONIBUS, banco.COR, banco.ORIGEM, banco.DESTINO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CONTAGEM, campos, selection, new String[] {_onibus,_cor,_origem, _destino }, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();

        if (cursor != null){
            return cursor.getString(cursor.getColumnIndex(banco.ID_VIAGEM));

        }else
            return null;

    }



}
