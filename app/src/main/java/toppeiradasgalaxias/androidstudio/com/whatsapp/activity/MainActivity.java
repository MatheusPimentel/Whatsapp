package toppeiradasgalaxias.androidstudio.com.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import toppeiradasgalaxias.androidstudio.com.whatsapp.R;
import toppeiradasgalaxias.androidstudio.com.whatsapp.adapter.TabAdapter;
import toppeiradasgalaxias.androidstudio.com.whatsapp.config.ConfiguracaoFirebase;
import toppeiradasgalaxias.androidstudio.com.whatsapp.helper.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private android.support.v7.widget.Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);

        //configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        //configurar adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;

            case R.id.item_adicionar:
                abrirCadastroContato();
                return true;

            case R.id.item_configuracoes:
                return true;

            case R.id.item_pesquisa:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroContato() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        //configurações do dialog
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        //configura botões
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void deslogarUsuario() {
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
