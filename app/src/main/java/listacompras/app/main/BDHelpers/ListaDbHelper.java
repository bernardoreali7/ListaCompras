package listacompras.app.main.BDHelpers;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listacompras.Utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import listacompras.app.main.Entities.ListaCompras;

public class ListaDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lista_compras.db";

    private static final int DATABASE_VERSION = 1;

    // Constructor
    public ListaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE LISTACOMPRAS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  nome TEXT NOT NULL," +
                "  data DATE NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS listacompras" );
        onCreate(sqLiteDatabase);
    }

    public long inserirLista(String nome, Date data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("data", data.getTime());

        long id = db.insert("listacompras", null, values);

        db.close();

        return id;
    }

    public int editarLista(ListaCompras lista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", lista.getName());
        values.put("data", lista.getData().getTime());

        return db.update("listacompras", values, "id = ?",
                new String[]{String.valueOf(lista.getId())});
    }

    public void deletarLembrete(ListaCompras lista) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("listacompras", "id = ?",
                new String[]{String.valueOf(lista.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public ListaCompras getLista(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("listacompras",
                new String[]{"id", "nome", "data"},
                "id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ListaCompras lista = new ListaCompras();
        lista.setId(cursor.getInt(cursor.getColumnIndex("id")));
        lista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        lista.setData(Util.strToDateTime(cursor.getString(cursor.getColumnIndex("data"))));

        cursor.close();

        return lista;
    }

    @SuppressLint("Range")
    public List<ListaCompras> getAllListas() {
        List<ListaCompras> listas = new ArrayList<>();

        String selectQuery = "SELECT * FROM listacompras ORDER BY data DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ListaCompras lista = new ListaCompras();
                lista.setId(cursor.getInt(cursor.getColumnIndex("id")));
                lista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                lista.setData(Util.strToDateTime(cursor.getString(cursor.getColumnIndex("data"))));
                listas.add(lista);
            } while (cursor.moveToNext());
        }

        db.close();

        return listas;
    }
}
