package listacompras.app.main.BDHelpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import listacompras.app.main.Entities.Item;

public class ListaItemDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lista_compras.db";

    private static final int DATABASE_VERSION = 1;

    // Constructor
    public ListaItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE LISTAITEM (" +
                "  ID_LISTA INTEGER NOT NULL," +
                "  ID_ITEM INTEGER NOT NULL," +
                "  FOREIGN KEY(ID_LISTA) REFERENCES LISTACOMPRAS(id)," +
                "  FOREIGN KEY(ID_ITEM) REFERENCES ITEM(id)" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LISTAITEM" );
        onCreate(sqLiteDatabase);
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
                item.setPreco(cursor.getFloat(cursor.getColumnIndex("preco")));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }
}
