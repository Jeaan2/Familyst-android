package com.tcc.ufpr.familyst.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Activities.BaseActivity;
import com.tcc.ufpr.familyst.Adapters.FamiliaPerfilAdapter;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PerfilActivity extends BaseActivity {

    Button btnEditarPerfil;
    Button btnRemoverPerfil;
    TextView txtEmailPerfil;
    ListView listViewFamiliasPerfil;
    TextView txtNomeUsuarioPerfil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_perfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroFamiliaActivity.class);
                startActivity(intent);
            }
        });

        btnEditarPerfil = (Button) findViewById(R.id.btn_editar_perfil);
        btnRemoverPerfil = (Button) findViewById(R.id.btn_remover_conta);
        txtEmailPerfil = (TextView) findViewById(R.id.txt_email_perfil);
        txtNomeUsuarioPerfil = (TextView) findViewById(R.id.txt_nome_perfil);

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO procedimentos para editar perfil
                //abre tela de cadastro com os dados já carregados
                //Flag de edição de dados na tela de cadastro
            }
        });

        btnRemoverPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO procedimentos para remover perfil
                //AlertDialog?

                new AlertDialog.Builder(PerfilActivity.this)
                        .setTitle("Alerta!")
                        .setMessage("Deseja remover o video selecionado?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog dialogProgresso = ProgressDialog.show(PerfilActivity.this, "Aguarde", "Excluindo item.");
                                dialogProgresso.setCancelable(false);

                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        ArrayList<Familia> familias = new ArrayList<>(); //placeholder

        listViewFamiliasPerfil = (ListView) findViewById(R.id.listview_familias_perfil);

        FamiliaPerfilAdapter adapter = new FamiliaPerfilAdapter(getApplicationContext(),
                R.layout.item_familia_perfil, familias);

        listViewFamiliasPerfil.setAdapter(adapter);

        listViewFamiliasPerfil.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Long click!", Toast.LENGTH_LONG).show();
                //TODO abrir tela de familia com extras: idEvento e bool indicando edicao
                return true;
            }
        });
    }

}
