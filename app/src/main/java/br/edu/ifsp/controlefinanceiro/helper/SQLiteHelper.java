package br.edu.ifsp.controlefinanceiro.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.ifsp.controlefinanceiro.util.DB;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper sInstance;

    private static final String DATABASE_NAME = "controle_financeiro";
    private static final int DATABASE_VERSION = 1;

    public static synchronized SQLiteHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new SQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB.CRIAR_TABELA_CONTA);
        db.execSQL(DB.CRIAR_TABELA_CENTRO_CUSTO);
        db.execSQL(DB.CRIAR_TABELA_TRANSACAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}