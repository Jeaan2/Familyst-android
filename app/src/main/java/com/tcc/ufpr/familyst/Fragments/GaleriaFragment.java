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

import com.tcc.ufpr.familyst.Activities.CadastroVideoActivity;
import com.tcc.ufpr.familyst.Adapters.VideoAdapter;
import com.tcc.ufpr.familyst.Model.Video;
import com.tcc.ufpr.familyst.R;

import java.sql.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GaleriaFragment extends Fragment {


    public GaleriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_galeria, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Chama cadastro de notícias", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //TODO mudar para CadastroVideo
                Intent intent = new Intent(getContext(), CadastroVideoActivity.class);
                startActivity(intent);
            }
        });

        Video dados_video[] = new Video[]
                {
                     new Video(1, "Joãozinho jogando bola", new Date(1, 1, 1), "https://www.youtube.com/watch?v=J---aiyznGQ"),
                     new Video(1, "Moda de Viola no domingo", new Date(1, 1, 1), "https://www.youtube.com/watch?v=J---aiyznGQ"),
                     new Video(1, "Festa Junina tranquila", new Date(1, 1, 1), "https://www.youtube.com/watch?v=J---aiyznGQ"),
                };

        VideoAdapter adapter = new VideoAdapter(getContext(),
                R.layout.item_lista_video, dados_video);

        final ListView listViewVideos = (ListView) rootView.findViewById(R.id.listview_galeria_videos);

        listViewVideos.setAdapter(adapter);

        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO bindar link do video para abrir no youtube
            }
        });

        return rootView;

    }

}
