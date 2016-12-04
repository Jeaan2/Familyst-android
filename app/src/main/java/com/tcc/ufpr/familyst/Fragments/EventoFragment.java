package com.tcc.ufpr.familyst.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Activities.LoginActivity;
import com.tcc.ufpr.familyst.Adapters.ComentarioAdapter;
import com.tcc.ufpr.familyst.Adapters.ItemEventoAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Album;
import com.tcc.ufpr.familyst.Model.Comentario;
import com.tcc.ufpr.familyst.Model.Evento;
import com.tcc.ufpr.familyst.Model.Item;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventoFragment extends Fragment {

    ListView listViewItens;
    ListView listViewComentarios;
    TextView textViewCriadorEvento;
    TextView textViewDescricaoEvento;
    TextView textViewLocalEvento;
    Evento evento;

    public EventoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        listViewItens= (ListView) rootView.findViewById(R.id.list_itens);
        listViewComentarios = (ListView) rootView.findViewById(R.id.list_comentarios);
        textViewCriadorEvento = (TextView) rootView.findViewById(R.id.criadorEvento);
        textViewDescricaoEvento = (TextView) rootView.findViewById(R.id.descricaoEvento);
        textViewLocalEvento = (TextView) rootView.findViewById(R.id.localEvento);

        int idEvento = getArguments().getInt("idEvento");
        evento = carregarEvento(idEvento);

        textViewCriadorEvento.setText(evento.getUsuarioEvento().getNome());
        textViewDescricaoEvento.setText(evento.getDescricao());
        textViewLocalEvento.setText(evento.getLocal());

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();

        CarregarListaComentarios();

        return rootView;
    }

    private Evento carregarEvento(int idEvento) {
        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());
        ArrayList<Evento> eventos = familystApplication.getFamiliaAtual().getEventos();
        for (int i = 0 ; i < eventos.size() ; i++)
        {
            Evento evento = eventos.get(i);
            if (evento.getIdEvento() == idEvento)
                return evento;
        }
        return null;
    }

    private void CarregarListaComentarios() {

        ItemEventoAdapter adapter = new ItemEventoAdapter(getContext(),
                R.layout.item_lista_itensevento, evento.getItensEvento());
        ComentarioAdapter adapter2 = new ComentarioAdapter(getContext(),
                R.layout.item_lista_comentarios, evento.getComentariosEvento());

        listViewItens.setAdapter(adapter);
        listViewComentarios.setAdapter(adapter2);
        listViewItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Implementar surgimento do botão "levar"
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        //TODO chamar progressdialog
        final ProgressDialog dialogProgresso = ProgressDialog.show(getContext(), "Aguarde", "Atualizando comentários.");
        dialogProgresso.setCancelable(false);
        RestService.getInstance(getActivity()).CarregarComentariosEventosFamiliasAsync(new RestCallback(){
            @Override
            public void onRestResult(boolean success) {
                if (success){
                    Toast.makeText(getActivity(),getResources().getText(R.string.sucesso_atualizar_comentarios), Toast.LENGTH_SHORT).show();
                    CarregarListaComentarios();
                }
                else
                {
                    Toast.makeText(getActivity(),getResources().getText(R.string.falha_atualizar_comentarios), Toast.LENGTH_SHORT).show();
                }
                //TODO dismiss progressdialog
                dialogProgresso.dismiss();
            }
        });
    }
}
