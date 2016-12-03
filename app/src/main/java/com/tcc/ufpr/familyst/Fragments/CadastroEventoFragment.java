package com.tcc.ufpr.familyst.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tcc.ufpr.familyst.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroEventoFragment extends Fragment {

    private Spinner spnTipoEvento;
    private EditText nomeEvento;
    private EditText localEvento;
    private EditText descricaoEvento;
    private Button btnCadastrarEvento;

    public CadastroEventoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cadastro_evento, container, false);

        nomeEvento = (EditText) rootView.findViewById(R.id.txt_nome_evento_cadastro);
        localEvento = (EditText) rootView.findViewById(R.id.txt_local_evento_cadastro);
        descricaoEvento = (EditText) rootView.findViewById(R.id.txt_descricao_evento_cadastro);
        spnTipoEvento = (Spinner) rootView.findViewById(R.id.spn_tipo_evento);
        btnCadastrarEvento = (Button) rootView.findViewById(R.id.btn_cadastrar_evento);

        btnCadastrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verifica se há itens a serem cadastrados
                //Persiste objeto
                //sucesso
            }
        });

        return rootView;
    }

}
