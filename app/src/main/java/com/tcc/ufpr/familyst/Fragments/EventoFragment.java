package com.tcc.ufpr.familyst.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tcc.ufpr.familyst.Adapters.ComentarioAdapter;
import com.tcc.ufpr.familyst.Adapters.ItemEventoAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Model.Evento;
import com.tcc.ufpr.familyst.Model.Item;
import com.tcc.ufpr.familyst.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventoFragment extends Fragment {


    public EventoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        Evento evento = (Evento) getArguments().getSerializable("evento");

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();

        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());
        ItemEventoAdapter adapter = new ItemEventoAdapter(getContext(),
                R.layout.item_lista_itensevento, evento.getItensEvento());
        ComentarioAdapter adapter2 = new ComentarioAdapter(getContext(),
                R.layout.item_lista_comentarios, evento.getComentariosEvento());

        final ListView listViewItens = (ListView) rootView.findViewById(R.id.list_itens);
        final ListView listViewComentarios = (ListView) rootView.findViewById(R.id.list_comentarios);
        final TextView textViewCriadorEvento = (TextView) rootView.findViewById(R.id.criadorEvento);
        textViewCriadorEvento.setText(evento.getUsuarioEvento().getNome());
        final TextView textViewDescricaoEvento = (TextView) rootView.findViewById(R.id.descricaoEvento);
        textViewDescricaoEvento.setText(evento.getDescricao());
        final TextView textViewLocalEvento = (TextView) rootView.findViewById(R.id.localEvento);
        textViewLocalEvento.setText(evento.getLocal());

        /*textViewComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComentariosFragment fragment = new ComentariosFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });*/

        listViewItens.setAdapter(adapter);
        listViewItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Implementar surgimento do botão "levar"
            }
        });

        listViewComentarios.setAdapter(adapter2);


        return rootView;
    }

}
