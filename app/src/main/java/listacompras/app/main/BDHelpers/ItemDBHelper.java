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

import listacompras.app.main.Entities.Item;

public class ItemDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lista_compras.db";

    private static final int DATABASE_VERSION = 1;

    // Constructor
    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE ITEM (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "PRECO FLOAT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ITEM" );
        onCreate(sqLiteDatabase);
    }

    public long inserirItem(String nome, Float preco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("preco", preco);

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
                new String[]{"id", "nome", "preco"},
                "id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex("id")));
        item.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        item.setPreco(cursor.getFloat(cursor.getColumnIndex("preco")));

        cursor.close();

        return item;
    }

    @SuppressLint("Range")
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        String selectQuery = "SELECT * FROM ITEMS";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                item.setPreco(cursor.getFloat(cursor.getColumnIndex("preco")));
                items.add(item);
            } while (cursor.moveToNext());
        }

        db.close();

        return items;
    }
}
