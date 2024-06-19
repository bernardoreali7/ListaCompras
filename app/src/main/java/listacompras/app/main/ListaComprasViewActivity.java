package listacompras.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import listacompras.app.main.Entities.ListaCompras;
import listacompras.app.main.Entities.ListaComprasAdapter;
import listacompras.app.main.Utils.Util;

public class ListaComprasViewActivity extends AppCompatActivity {

    private List<ListaCompras> listasCompras;
    private ListaComprasAdapter adapter;

    private List<ListaCompras> dummyData() {
        List<ListaCompras> listasCompras = new ArrayList<>();
        ListaCompras lista1 = new ListaCompras();
        lista1.setId(1);
        lista1.setNome("Lista 1");
        lista1.setData(Util.strToDateTime("13/06/2024"));
        listasCompras.add(lista1);

        ListaCompras lista2 = new ListaCompras();
        lista1.setId(2);
        lista1.setNome("Lista 2");
        lista1.setData(Util.strToDateTime("09/06/2024"));
        listasCompras.add(lista2);
        return listasCompras;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ListView listViewClientes = findViewById(R.id.listViewListasCompra);
        listasCompras = dummyData();
        adapter = new ListaComprasAdapter(this, listasCompras);
        listViewClientes.setAdapter(adapter);

        listViewClientes.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected cliente
            ListaCompras selectedListaCompras = (ListaCompras) parent.getItemAtPosition(position);

            Intent intent = new Intent(ListaComprasViewActivity.this, ListaComprasViewActivity.class);
            intent.putExtra("listaCompras", selectedListaCompras);

            // Start the new activity
            startActivity(intent);
        });
    }
}