package br.fib.biblioteca;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import br.fib.biblioteca.Adapters.AutorAdapter;
import br.fib.biblioteca.Controllers.AutorController;
import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.R;
import br.fib.biblioteca.Util.Util;

public class AutorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AutorController autorController;
    private final static Integer AUTOR_GRAVADO = 1;
    private ListView listaAutores;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor);

        autorController = new AutorController(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_autor);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_autor);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        util = new Util();

        carregarAutores();
    }

    public void carregarAutores() {
        List<Autor> autorList = autorController.findAutores(null);
        AutorAdapter autorAdapter = new AutorAdapter(getBaseContext(), autorList, this);

        listaAutores = findViewById(R.id.lista_autores);
        listaAutores.setAdapter(autorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_padrao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.incluir) {
            Intent intent = new Intent(getBaseContext(), AutorModalActivity.class);
            intent.putExtra("idAutor", 0);
            startActivityForResult(intent, AUTOR_GRAVADO);
        }

        if (id == R.id.apagar) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exclusão de Autores")
                    .setMessage("Deseja EXCLUIR os autores selecionados?")
                    .setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listaAutores != null) {
                                        if (!listaAutores.getAdapter().isEmpty()) {
                                            AutorAdapter idSelecionados = (AutorAdapter) listaAutores.getAdapter();

                                            if (!idSelecionados.isEmpty()) {
                                                for (Long idAutores : idSelecionados.getIdSelecionados()) {
                                                    autorController.apagarAutor(idAutores);
                                                }

                                                util.enviarMensagemTela(getBaseContext(), "Autor(es) excluído(s)");
                                                carregarAutores();
                                            }
                                        }
                                    }
                                }
                            })
                    .setNegativeButton("Nao", null)
                    .show();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == AUTOR_GRAVADO) {
            carregarAutores();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_autor);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        startActivity(util.abrirMenu(this, item));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_autor);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
