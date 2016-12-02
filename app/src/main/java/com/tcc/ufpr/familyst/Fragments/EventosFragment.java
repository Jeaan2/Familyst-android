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

import com.tcc.ufpr.familyst.Activities.TabHostEventosActivity;
import com.tcc.ufpr.familyst.Adapters.EventoAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Model.Evento;
import com.tcc.ufpr.familyst.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment {


    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Chama cadastro de Eventos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getContext(), TabHostEventosActivity.class);
                startActivity(intent);
            }
        });

        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());

        EventoAdapter adapter = new EventoAdapter(getContext(),
                R.layout.item_lista_eventos, familystApplication.get_usuarioLogado().getFamilias().get(familystApplication.getIdFamiliaSelecionada()).getEventos());

        final ListView listViewProximosEventos = (ListView) rootView.findViewById(R.id.list_proximos_eventos);

        listViewProximosEventos.setAdapter(adapter);

        listViewProximosEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO abre fragment de Evento, passando o objeto selecionado na lista

                Evento evento = (Evento)parent.getAdapter().getItem(position);

                EventoFragment fragment = new EventoFragment();
                Bundle data = new Bundle();



                data.putSerializable("evento", evento);
                fragment.setArguments(data);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        listViewProximosEventos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "Long click!", Toast.LENGTH_LONG).show();

                return true;
            }
        });

        return rootView;
    }

}
