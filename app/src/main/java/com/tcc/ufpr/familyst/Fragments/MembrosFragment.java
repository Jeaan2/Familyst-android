package com.tcc.ufpr.familyst.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Activities.CadastroMembroActivity;
import com.tcc.ufpr.familyst.Activities.TabHostEventosActivity;
import com.tcc.ufpr.familyst.Adapters.UsuarioAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Usuario;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MembrosFragment extends Fragment {

    ListView listViewMembros;

    public MembrosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_membros, container, false);
        listViewMembros= (ListView) rootView.findViewById(R.id.listview_membros);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Chama cadastro de Membros", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getContext(), CadastroMembroActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void carregarListaMembros(){
        UsuarioAdapter adapter = new UsuarioAdapter(getContext(),
                R.layout.item_lista_membros, carregarMembros());

        listViewMembros.setAdapter(adapter);
        listViewMembros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Abrir tela de profile com os dados do membro
            }
        });
        listViewMembros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Long click!", Toast.LENGTH_LONG).show();
                return true;

            }
        });
    }

    private ArrayList<Usuario> carregarMembros()
    {
        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());
        return familystApplication.getFamiliaAtual().getUsuarios();
    }


    @Override
    public void onStart(){
        super.onStart();

        //TODO chamar progressdialog
        RestService.getInstance(getActivity()).CarregarUsuariosFamiliasAsync(new RestCallback(){
            @Override
            public void onRestResult(boolean success) {
                if (success){
                    Toast.makeText(getActivity(),getResources().getText(R.string.sucesso_atualizar_membros), Toast.LENGTH_SHORT).show();
                    carregarListaMembros();
                }
                else
                {
                    Toast.makeText(getActivity(),getResources().getText(R.string.falha_atualizar_membros), Toast.LENGTH_SHORT).show();
                }
                //TODO dismiss progressdialog
            }
        });
    }

}
