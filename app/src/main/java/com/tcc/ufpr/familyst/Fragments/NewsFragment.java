package com.tcc.ufpr.familyst.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcc.ufpr.familyst.Activities.CadastroNoticiaActivity;
import com.tcc.ufpr.familyst.Adapters.NewsAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Model.Noticia;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private NewsAdapter noticiasAdapter;
    private ArrayList<Noticia> listaNoticiasCardView = new ArrayList<>();

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Chama cadastro de notícias", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getContext(), CadastroNoticiaActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardlist_news);
        recList.setHasFixedSize(true);

        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        noticiasAdapter = new NewsAdapter(getActivity());
        recList.setAdapter(noticiasAdapter);

        listaNoticiasCardView = carregarNoticias();
        noticiasAdapter.setListaNoticia(listaNoticiasCardView);

        return rootView;
    }

    private ArrayList<Noticia> carregarNoticias() {
        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());
        return familystApplication.get_usuarioLogado().getFamilias().get(familystApplication.getIdFamiliaSelecionada()).getNoticias();
    }

}
