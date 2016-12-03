package com.tcc.ufpr.familyst.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Activities.CadastroAlbumActivity;
import com.tcc.ufpr.familyst.Adapters.TipoEventoAdapter;
import com.tcc.ufpr.familyst.Adapters.TipoItemAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Item;
import com.tcc.ufpr.familyst.Model.TipoEvento;
import com.tcc.ufpr.familyst.Model.TipoItem;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

import java.util.ArrayList;

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

        ArrayList<TipoEvento> tiposEventos = ((FamilystApplication)getActivity().getApplication()).getTiposEventos();
        TipoEventoAdapter adapter = new TipoEventoAdapter(getActivity(),
                R.layout.item_lista_tipoevento, tiposEventos);
        spnTipoEvento.setAdapter(adapter);

        btnCadastrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verifica se há itens a serem cadastrados
                //Persiste objeto
                //sucesso

                //HACK SINIIIIIIISTRO VEI ahahhahaha
                String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
                ItensCadastroEventoFragment f = (ItensCadastroEventoFragment)getFragmentManager().findFragmentByTag(tag);
                ArrayList<Item> itensAdicionados = f._itensAdicionados;
                String hahahahaha = "aff"; //so pra debug

                RestService.getInstance(getActivity()).EnviarEvento(
                        nomeEvento.getText().toString(),
                        descricaoEvento.getText().toString(),
                        localEvento.getText().toString(),
                        ((TipoEvento)spnTipoEvento.getSelectedItem()).getIdTipoEvento(),
                        itensAdicionados,
                        new RestCallback(){
                    @Override
                    public void onRestResult(boolean success) {
                        if (success){
                            Toast.makeText(getContext(),getResources().getText(R.string.sucesso_cadastro_evento), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                        else
                        {
                            Toast.makeText(getContext(),getResources().getText(R.string.falha_cadastro_evento), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return rootView;
    }

}
