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
import com.tcc.ufpr.familyst.Model.Usuario;
import com.tcc.ufpr.familyst.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MembrosFragment extends Fragment {


    public MembrosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_membros, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), CadastroMembroActivity.class);
                startActivity(intent);
            }
        });

        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());

        UsuarioAdapter adapter = new UsuarioAdapter(getContext(),
                R.layout.item_lista_membros, familystApplication.getFamiliaAtual().getUsuarios());

        final ListView listViewMembros = (ListView) rootView.findViewById(R.id.listview_membros);

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

        return rootView;
    }

}
