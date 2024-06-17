package listacompras.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import listacompras.app.main.BDHelpers.ItemDBHelper;
import listacompras.app.main.BDHelpers.ListaDbHelper;
import listacompras.app.main.BDHelpers.ListaItemDBHelper;
import listacompras.app.main.Entities.ListaCompras;
import listacompras.app.main.Entities.ListaComprasAdapter;
import listacompras.app.main.Entities.ListaItem;

public class CreateListaComprasActivity extends AppCompatActivity {
    EditText input_nome;
    TextView txt_soma;
    Button btn_incluir;
    Button btn_salvarLista;
    private List<ListaItem> listasItens;
    private ListaComprasAdapter adapter;

    private ItemDBHelper itemDB;
    private ListaDbHelper listaDB;
    private ListaItemDBHelper listaItemDBHelper;

    private List<ListaCompras> dummyData() {
        List<ListaCompras> listasCompras = new ArrayList<>();
        listasCompras.add(1, "Lista 1", "13/06/2024");
        listasCompras.add(new ListaCompras(2, "Lista 2", "10/06/2024"));
        listasCompras.add(new ListaCompras(3, "Lista 3", "09/06/2024"));
        return listasCompras;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_lista_compras);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        input_nome = findViewById(R.id.input_nome);

        findViewById(R.id.btn_incluir).setOnClickListener((v) -> {
            String nome = input_nome.getText().toString();
            //Lista de itens

            // Intent intentSucesso = new Intent(CadastroActivity.this, LoginActivity.class);
            // showSuccessDialog(this, intentSucesso);
        });

        findViewById(R.id.btn_salvar).setOnClickListener((v) -> {
            showFormDialog();
        });


        ListView listViewClientes = findViewById(R.id.listViewListasCompra);
        List<ListaCompras> listasCompras = dummyData();
        adapter = new ListaComprasAdapter(this, listasCompras);
        listViewClientes.setAdapter(adapter);

        listViewClientes.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected cliente
            ListaCompras selectedListaCompras = (ListaCompras) parent.getItemAtPosition(position);

            // Create an Intent to start CartaoActivity
            Intent intent = new Intent(CreateListaComprasActivity.this, CreateListaComprasActivity.class);
            intent.putExtra("listaCompras", selectedListaCompras);

            // Start the new activity
            startActivity(intent);
        });
    }

    private void showFormDialog() {
        // Inflate the custom layout/view
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_cadastro, null);

        // Initialize the EditTexts
        Spinner spinnerItens = dialogView.findViewById(R.id.spinner_itens);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_itens, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Incluir item")
                .setPositiveButton("Incluir", (dialog, which) -> {
                    String name = editTextName.getText().toString();
                    String phone = editTextPhone.getText().toString();

                    Cliente newCliente = new Cliente(clientes.size() + 1, name, phone);
                    clientes.add(newCliente);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}