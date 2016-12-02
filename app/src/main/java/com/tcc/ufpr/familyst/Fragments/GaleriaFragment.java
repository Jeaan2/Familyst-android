package com.tcc.ufpr.familyst.Fragments;


import android.content.Intent;
import android.net.Uri;
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

import com.tcc.ufpr.familyst.Activities.CadastroVideoActivity;
import com.tcc.ufpr.familyst.Adapters.VideoAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Noticia;
import com.tcc.ufpr.familyst.Model.Video;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

import java.sql.Date;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GaleriaFragment extends Fragment {

    ListView listViewVideos;

    public GaleriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_galeria, container, false);
        listViewVideos = (ListView) rootView.findViewById(R.id.listview_galeria_videos);

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

        CarregarListaVideos();

        return rootView;

    }

    @Override
    public void onStart(){
        super.onStart();

        //TODO chamar progressdialog
        RestService.getInstance(getActivity()).CarregarVideosFamiliasAsync(new RestCallback(){
            @Override
            public void onRestResult(boolean success) {
                if (success){
                    Toast.makeText(getActivity(),getResources().getText(R.string.sucesso_atualizar_videos), Toast.LENGTH_SHORT).show();
                    CarregarListaVideos();
                }
                else
                {
                    Toast.makeText(getActivity(),getResources().getText(R.string.falha_atualizar_videos), Toast.LENGTH_SHORT).show();
                }
                //TODO dismiss progressdialog
            }
        });
    }

    private void CarregarListaVideos() {
        VideoAdapter adapter = new VideoAdapter(getContext(),
                R.layout.item_lista_video, carregarVideos());
        listViewVideos.setAdapter(adapter);
        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO bindar link do video para abrir no youtube
                Video video = (Video) parent.getItemAtPosition(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video.getLink())));
            }
        });
    }

    private ArrayList<Video> carregarVideos() {
        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());
        return familystApplication.getFamiliaAtual().getVideos();
    }

}
