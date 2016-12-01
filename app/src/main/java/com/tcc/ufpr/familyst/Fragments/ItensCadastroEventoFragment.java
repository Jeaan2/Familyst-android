package com.tcc.ufpr.familyst.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tcc.ufpr.familyst.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItensCadastroEventoFragment extends Fragment {


    private Spinner spnTipoItem;
    private EditText txtQuantidadeItem;
    private Button addItem;
    private ListView listItens;

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

        return  rootView;
    }

}
