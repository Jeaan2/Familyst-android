package com.tcc.ufpr.familyst.Fragments;


import android.app.ProgressDialog;
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

import com.tcc.ufpr.familyst.Activities.CadastroAlbumActivity;
import com.tcc.ufpr.familyst.Activities.CadastroVideoActivity;
import com.tcc.ufpr.familyst.Adapters.VideoAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Album;
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

                //TODO mudar para CadastroVideo
                Intent intent = new Intent(getContext(), CadastroVideoActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        // chamar progressdialog
        final ProgressDialog dialogProgresso = ProgressDialog.show(getContext(), "Aguarde", "Atualizando Vídeos.");
        dialogProgresso.setCancelable(false);
        RestService.getInstance(getActivity()).CarregarVideosFamiliasAsync(new RestCallback(){
            @Override
            public void onRestResult(boolean success) {
                if (success){
                    CarregarListaVideos();
                }
                else
                {
                    Toast.makeText(getActivity(),getResources().getText(R.string.falha_atualizar_videos), Toast.LENGTH_SHORT).show();
                }
                // dismiss progressdialog
                dialogProgresso.dismiss();
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
        listViewVideos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Video video = (Video) parent.getItemAtPosition(position);

                Intent intent = new Intent(getContext(), CadastroVideoActivity.class);
                intent.putExtra("idVideo", video.getIdVideo());
                intent.putExtra("isEdicao", true);
                startActivity(intent);

                return true;

            }
        });
    }

    private ArrayList<Video> carregarVideos() {
        FamilystApplication familystApplication = ((FamilystApplication)getActivity().getApplication());
        return familystApplication.getFamiliaAtual().getVideos();
    }

}
