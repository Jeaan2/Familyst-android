package com.tcc.ufpr.familyst.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Adapters.FamiliaAdapter;
import com.tcc.ufpr.familyst.Adapters.TipoItemAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.Model.Item;
import com.tcc.ufpr.familyst.Model.TipoItem;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItensCadastroEventoFragment extends Fragment {


    private Spinner spnTipoItem;
    private EditText txtQuantidadeItem;
    private Button addItem;
    private ListView listItens;
    public ArrayList<Item> _itensAdicionados;

    public ItensCadastroEventoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_itens_cadastro_evento, container, false);

        spnTipoItem = (Spinner) rootView.findViewById(R.id.spn_tipo_item);
        txtQuantidadeItem = (EditText) rootView.findViewById(R.id.txt_quantidade_item);
        listItens = (ListView) rootView.findViewById(R.id.list_itens_adicionados);
        addItem = (Button) rootView.findViewById(R.id.btn_add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int idTipoItem = ((TipoItem)spnTipoItem.getSelectedItem()).getIdTipoItem();
                    int quantidade = Integer.valueOf(txtQuantidadeItem.getText().toString());

                    Item item = new Item(-1, quantidade, idTipoItem);
                    _itensAdicionados.add(item);
            }
        });

        ArrayList<TipoItem> tiposItens = ((FamilystApplication)getActivity().getApplication()).getTiposItens();
        TipoItemAdapter adapter = new TipoItemAdapter(getActivity(),
                R.layout.item_lista_tipoitem, tiposItens);
        spnTipoItem.setAdapter(adapter);

        _itensAdicionados = new ArrayList<>();

        listItens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Long click!", Toast.LENGTH_LONG).show();
                //TODO abrir tela de Cadastro com extras: idEvento e bool indicando edicao
                return true;

            }
        });

        return  rootView;
    }

}
