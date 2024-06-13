package listacompras.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity {
    ImageButton btn_addListaCompras;
    private List<ListaCompras> listasCompras;
    private ListaComprasAdapter adapter;

    private List<ListaCompras> dummyData() {
        List<ListaCompras> listasCompras = new ArrayList<>();
        listasCompras.add(new ListaCompras(1, "Lista 1", "13/06/2024"));
        listasCompras.add(new ListaCompras(2, "Lista 2", "10/06/2024"));
        listasCompras.add(new ListaCompras(3, "Lista 3", "09/06/2024"));
        return listasCompras;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listViewClientes = findViewById(R.id.listViewListasCompra);
        listasCompras = dummyData();
        adapter = new ListaComprasAdapter(this, listasCompras);
        listViewClientes.setAdapter(adapter);

        listViewClientes.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected cliente
            ListaCompras selectedListaCompras = (ListaCompras) parent.getItemAtPosition(position);

            // Create an Intent to start CartaoActivity
            Intent intent = new Intent(MainActivity.this, ListaComprasViewActivity.class);
            intent.putExtra("listaCompras", selectedListaCompras);

            // Start the new activity
            startActivity(intent);
        });

        btn_addListaCompras = findViewById(R.id.btn_addListaCompras);


        btn_addListaCompras.setOnClickListener((v) -> {
            new Intent(MainActivity.this, CreateListaComprasActivity.class);
        });
    }
}