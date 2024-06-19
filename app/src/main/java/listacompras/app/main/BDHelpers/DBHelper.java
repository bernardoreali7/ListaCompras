package listacompras.app.main.BDHelpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import listacompras.app.main.Entities.Item;
import listacompras.app.main.Entities.ListaCompras;
import listacompras.app.main.Utils.Util;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lista_compras.db";

    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE_ITEM = "CREATE TABLE ITEM (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "PRECO FLOAT NOT NULL" +
                ");";

        final String CREATE_TABLE_LISTA_COMPRAS = "CREATE TABLE LISTACOMPRAS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  nome TEXT NOT NULL," +
                "  data DATE NOT NULL" +
                ");";

        final String CREATE_TABLE_LISTA_ITEM = "CREATE TABLE LISTAITEM (" +
                "  ID_LISTA INTEGER NOT NULL," +
                "  ID_ITEM INTEGER NOT NULL," +
                "  FOREIGN KEY(ID_LISTA) REFERENCES LISTACOMPRAS(id)," +
                "  FOREIGN KEY(ID_ITEM) REFERENCES ITEM(id)" +
                ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_LISTA_COMPRAS);
        sqLiteDatabase.execSQL(CREATE_TABLE_ITEM);
        sqLiteDatabase.execSQL(CREATE_TABLE_LISTA_ITEM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ITEM" );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS listacompras" );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LISTAITEM" );
        onCreate(sqLiteDatabase);
    }

    public long inserirItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", item.getNome());
        values.put("preco", item.getPreco());

        long id = db.insert("ITEM", null, values);

        db.close();

        return id;
    }

    public int editarItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", item.getNome());
        values.put("preco", item.getPreco());

        return db.update("ITEM", values, "id = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public void deletarItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ITEM", "id = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public Item getItem(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("ITEM",
                new String[]{"id", "nome", "PRECO"},
                "id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex("id")));
        item.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        item.setPreco(cursor.getFloat(cursor.getColumnIndex("PRECO")));

        cursor.close();

        return item;
    }

    @SuppressLint("Range")
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        String selectQuery = "SELECT * FROM ITEM";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                item.setPreco(cursor.getFloat(cursor.getColumnIndex("PRECO")));
                items.add(item);
            } while (cursor.moveToNext());
        }

        db.close();

        return items;
    }

    public long inserirLista(ListaCompras lista) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", lista.getName());
        values.put("data", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(lista.getData()));

        long id = db.insert("listacompras", null, values);

        db.close();

        return id;
    }

    public int editarLista(ListaCompras lista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", lista.getName());
        values.put("data", Util.formatDateToString(new Date()));

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
                lista.setData(Util.strToDateTimeDB(cursor.getString(cursor.getColumnIndex("data"))));
                listas.add(lista);
            } while (cursor.moveToNext());
        }

        db.close();

        return listas;
    }

    public long inserirListaItem(Long id_lista, Long id_item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_lista", id_lista);
        values.put("id_item", id_item);

        long id = db.insert("LISTAITEM", null, values);

        db.close();

        return id;
    }

    @SuppressLint("Range")
    public List<Item> getItemsForLista(long id_lista) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT i.* FROM ITEM i "
                + "INNER JOIN LISTAITEM lci "
                + "ON i.id = lci.id"
                + " WHERE lci.id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id_lista)});

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                item.setPreco(cursor.getFloat(cursor.getColumnIndex("PRECO")));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }
}
