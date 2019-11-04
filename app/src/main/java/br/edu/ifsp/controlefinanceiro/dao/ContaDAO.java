package br.edu.ifsp.controlefinanceiro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinanceiro.helper.SQLiteHelper;
import br.edu.ifsp.controlefinanceiro.model.Conta;
import br.edu.ifsp.controlefinanceiro.util.DB;

public class ContaDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContaDAO(Context context) {
        this.dbHelper = SQLiteHelper.getInstance(context);
    }

    public Double buscarSaldo(){
        try {
            database = dbHelper.getReadableDatabase();

            StringBuilder sql = new StringBuilder();
            sql.append(DB.BUSCAR_SALDO_CONTAS_QUERY);
            Cursor cursor = database.rawQuery(sql.toString(), null);

            cursor.moveToFirst();

            return cursor.getDouble(0);
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
        }

        return null;
    }

    public Double buscarSaldoContaPorId(Integer id){
        try{
            database = dbHelper.getReadableDatabase();

            Cursor cursor = database.rawQuery(DB.BUSCAR_SALDO_CONTA_POR_ID_QUERY, new String[] {String.valueOf(id)});

            cursor.moveToFirst();

            return cursor.getDouble(0);
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
        }

       return null;
    }

    public Conta buscarContaPorId(Integer id){

        try{
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(DB.BUSCAR_CONTA_POR_ID, new String[] {String.valueOf(id)});

            cursor.moveToFirst();

            Conta contaDB = new Conta();
            contaDB.setId(id);
            contaDB.setDescricao(cursor.getString(0));
            contaDB.setSaldo(cursor.getDouble(1));

            cursor.close();

            database.close();

            return contaDB;
        }catch (Exception e){
            Log.i("ERROR", e.getMessage());
        }

       return null;
    }

    public Boolean salvaConta(Conta conta) {
        try{
            database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("descricao", conta.getDescricao());
            values.put("saldo", conta.getSaldo());

            database.insert(DB.TABELA_CONTA, null, values);


            database.close();
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public List<Conta> buscarContas() {
        try{
            database = dbHelper.getReadableDatabase();

            List<Conta> contasDB = new ArrayList<>();

            String[] cols = new String[]{ "id", "descricao", "saldo"};
            Cursor cursor = database.query(DB.TABELA_CONTA,
                    cols,
                    null, null, null, null, "saldo");

            while (cursor.moveToNext()) {
                Conta conta = new Conta();
                conta.setId(cursor.getInt(0));
                conta.setDescricao(cursor.getString(1));
                conta.setSaldo(Double.valueOf(cursor.getString(2)));

                contasDB.add(conta);
            }

            cursor.close();

            database.close();

            return contasDB;
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
        }
        return null;
    }
}