package listacompras.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import listacompras.app.main.BDHelpers.DBHelper;
import listacompras.app.main.Entities.Item;
import listacompras.app.main.Entities.ItemsAdapter;
import listacompras.app.main.Entities.ListaCompras;

public class CreateListaComprasActivity extends AppCompatActivity implements ItemsAdapter.OnItemSelectedListener{
    EditText input_nome;
    private List<Item> listasItens = new ArrayList<>();
    private List<Item> selectedItems = new ArrayList<>();;
    private ItemsAdapter itemAdapter;
    private DBHelper DBHelper;
    private RecyclerView recyclerViewItems;
    private Button buttonSave;
    private ImageButton btn_retornar;
    private TextView txt_soma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lista_compras);

        DBHelper = new DBHelper(this);

        input_nome = findViewById(R.id.input_nome);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        buttonSave = findViewById(R.id.btn_salvar);
        btn_retornar = findViewById(R.id.btn_retornar);

        listasItens.addAll(DBHelper.getAllItems());

        itemAdapter = new ItemsAdapter(listasItens, this);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(itemAdapter);

        txt_soma = findViewById(R.id.txt_soma);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listName = input_nome.getText().toString();
                if (listName.isEmpty()) {
                    input_nome.setError("Nome da lista é obrigatório");
                    return;
                }

                ListaCompras newLista = new ListaCompras();
                newLista.setNome(listName);
                newLista.setData(new Date());

                long listId = DBHelper.inserirLista(newLista);

                List<Item> listaItemsSelecionados = itemAdapter.getSelectedItems();

                for (Item item : listaItemsSelecionados) {
                    DBHelper.inserirListaItem(listId, item.getId());
                }

                Intent intent = new Intent(CreateListaComprasActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btn_retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRetornar = new Intent(CreateListaComprasActivity.this, MainActivity.class);
                startActivity(intentRetornar);
            }
        });
    }

    @Override
    public void onItemSelected(List<Item> selectedItems) {
        float total = 0.0f;
        for (Item item : selectedItems) {
            total += item.getPreco();
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        txt_soma.setText("Total: " + df.format(total));
    }
}