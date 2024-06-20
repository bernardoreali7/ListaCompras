package listacompras.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import listacompras.app.main.BDHelpers.DBHelper;
import listacompras.app.main.Entities.Item;
import listacompras.app.main.Entities.ItemsAdapter;
import listacompras.app.main.Entities.ItemsEditAdapter;
import listacompras.app.main.Entities.ListaCompras;

public class EditarListaComprasActivity extends AppCompatActivity implements ItemsEditAdapter.OnItemSelectedListener{
    private EditText listaNome;
    private Button btnSalvar;
    private ImageButton btnVoltar;
    private DBHelper DBHelper;
    private long listId;
    private RecyclerView recyclerViewItems;
    private ItemsEditAdapter itemsEditAdapter;
    private TextView valorTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista_compras);
        Log.d("editList", "onCreate: btn salvar");
        listaNome = findViewById(R.id.input_nome);
        btnSalvar = findViewById(R.id.btn_editar);
        valorTotal = findViewById(R.id.txt_soma);
        btnVoltar = findViewById(R.id.btn_retornar);
        DBHelper = new DBHelper(this);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);

        Intent intent = getIntent();
        listId = intent.getLongExtra("LIST_ID", -1);

        if (listId != -1) {
            ListaCompras listaCompras = DBHelper.getLista(listId);
            listaNome.setText(listaCompras.getName());
            setupRecyclerView(DBHelper.getAllItems(), DBHelper.getItemIdsForLista(listId));
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("editList", "onClick: btn salvar");
                String name = listaNome.getText().toString();
                ListaCompras newLista = new ListaCompras();
                newLista.setId(listId);
                newLista.setNome(name);

                List<Long> updatedItems = itemsEditAdapter.getSelectedItems();
                Log.d("editList", "updated items: " + updatedItems.toString());
                DBHelper.editarLista(newLista);
                DBHelper.deletarItemsLista(newLista);

                for (Long item : updatedItems) {
                    DBHelper.inserirListaItem(listId, item);
                }

                finish();

                Intent intent_redirect = new Intent(EditarListaComprasActivity.this, MainActivity.class);
                startActivity(intent_redirect);
            }
        });
    }

    private void setupRecyclerView(List<Item> items, List<Long> selectedItemIds ) {
        Log.d("editList", "setupRecyclerView: ");
        itemsEditAdapter = new ItemsEditAdapter(items, selectedItemIds, this, this);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(itemsEditAdapter);
        updateTotalValue(itemsEditAdapter.calculateTotal());
    }

    @Override
    public void onItemSelected(List<Long> selectedItems, float total) {
        updateTotalValue(total);
    }

    private void updateTotalValue(float total) {
        valorTotal.setText(String.format("R$ %.2f", total));
    }

}