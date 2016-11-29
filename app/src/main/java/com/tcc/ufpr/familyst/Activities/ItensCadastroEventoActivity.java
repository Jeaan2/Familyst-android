package com.tcc.ufpr.familyst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tcc.ufpr.familyst.Adapters.ItemEventoAdapter;
import com.tcc.ufpr.familyst.Model.Item;
import com.tcc.ufpr.familyst.R;

public class ItensCadastroEventoActivity extends AppCompatActivity {

    private Spinner spnTipoItem;
    private EditText txtQuantidadeItem;
    private Button addItem;
    private ListView listItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_cadastro_evento);

        spnTipoItem = (Spinner) findViewById(R.id.spn_tipo_item);
        txtQuantidadeItem = (EditText) findViewById(R.id.txt_quantidade_item);
        listItens = (ListView) findViewById(R.id.list_itens_adicionados);
        addItem = (Button) findViewById(R.id.btn_add_item);

        /*Item dados_ItemEvento[] = new Item[]
                {
                        new Item(1, "Refrigerante", 2),
                        new Item(1, "Batata", 6),
                        new Item(1, "Doritos", 4),
                        new Item(1, "Nachos", 1),
                        new Item(1, "Guacamole", 1),
                };

        ItemEventoAdapter adapter = new ItemEventoAdapter(this,
                R.layout.item_lista_itensevento, dados_ItemEvento);*/



        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adiciona Item no listView
                //Atribui na lista do objeto Evento criado na outra tab.
            }
        });


    }
}
