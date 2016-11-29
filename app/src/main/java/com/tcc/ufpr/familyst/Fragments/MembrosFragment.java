package com.tcc.ufpr.familyst.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());

        UsuarioAdapter adapter = new UsuarioAdapter(getContext(),
                R.layout.item_lista_membros, familystApplication.get_usuarioLogado().getFamilias().get(familystApplication.getIdFamiliaSelecionada()).getUsuarios());

        final ListView listViewMembros = (ListView) rootView.findViewById(R.id.listview_membros);

        listViewMembros.setAdapter(adapter);

        listViewMembros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Abrir tela de profile com os dados do membro
            }
        });

        return rootView;
    }

}
