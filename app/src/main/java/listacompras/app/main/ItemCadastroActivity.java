package listacompras.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import listacompras.app.main.BDHelpers.DBHelper;
import listacompras.app.main.Entities.Item;

public class ItemCadastroActivity extends AppCompatActivity {

    private EditText nomeEditText;
    private EditText precoEditText;
    private Button botaoSalvar;
    private DBHelper DBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_cadastro);

        DBHelper = new DBHelper(this);

        nomeEditText = findViewById(R.id.input_nome);
        precoEditText = findViewById(R.id.input_preco);
        botaoSalvar = findViewById(R.id.btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeEditText.getText().toString();
                String preco = precoEditText.getText().toString();

                Item newItem = new Item();
                newItem.setNome(nome);
                newItem.setPreco(Float.parseFloat(preco));
                DBHelper.inserirItem(newItem);

                Intent intent = new Intent(ItemCadastroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
