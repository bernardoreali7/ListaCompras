package listacompras.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import listacompras.app.main.BDHelpers.DBHelper;
import listacompras.app.main.Entities.ListaCompras;
import listacompras.app.main.Entities.ListaComprasAdapter;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_addListaCompras;

    Button btn_addItem;
    private List<ListaCompras> listasCompras = new ArrayList<>();
    private ListaComprasAdapter adapter;

    private DBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper = new DBHelper(this);

        ListView listViewListaCompras = findViewById(R.id.listViewListasCompra);

        listasCompras.addAll(DBHelper.getAllListas());

        adapter = new ListaComprasAdapter(this, listasCompras);
        listViewListaCompras.setAdapter(adapter);

        listViewListaCompras.setOnItemClickListener((parent, view, position, id) -> {
            ListaCompras selectedListaCompras = (ListaCompras) parent.getItemAtPosition(position);

            Intent intent = new Intent(MainActivity.this, ListaComprasViewActivity.class);
            intent.putExtra("listaCompras", selectedListaCompras);

            startActivity(intent);
        });

        btn_addListaCompras = findViewById(R.id.btn_addListaCompras);
        btn_addListaCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreateListIntent = new Intent(MainActivity.this, CreateListaComprasActivity.class);
                startActivity(CreateListIntent);
            }
        });

        btn_addItem = findViewById(R.id.btn_incluir);

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(MainActivity.this, ItemCadastroActivity.class);
                startActivity(addItemIntent);
            }
        });
    }
}