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

import br.fib.biblioteca.Adapters.LivroAdapter;
import br.fib.biblioteca.Controllers.LivroController;
import br.fib.biblioteca.Models.Livro;
import br.fib.biblioteca.Util.Util;

public class LivroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private LivroController livroController;
    private final static Integer LIVRO_GRAVADO = 1;
    private ListView listaLivros;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);

        livroController = new LivroController(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_livro);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_livro);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        util = new Util();

        carregarLivros();
    }

    public void carregarLivros() {
        List<Livro> livroList = livroController.findLivros(null);
        LivroAdapter livroAdapter = new LivroAdapter(getBaseContext(), livroList, this);

        listaLivros = findViewById(R.id.lista_livros);
        listaLivros.setAdapter(livroAdapter);
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
            Intent intent = new Intent(getBaseContext(), LivroModalActivity.class);
            intent.putExtra("idLivro", 0);
            startActivityForResult(intent, LIVRO_GRAVADO);
        }

        if (id == R.id.apagar) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exclusão de Livros")
                    .setMessage("Deseja EXCLUIR os livros selecionados?")
                    .setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listaLivros != null) {
                                        if (!listaLivros.getAdapter().isEmpty()) {
                                            LivroAdapter idSelecionados = (LivroAdapter) listaLivros.getAdapter();

                                            if (!idSelecionados.isEmpty()) {
                                                for (Long idLivros : idSelecionados.getIdSelecionados()) {
                                                    livroController.apagarLivro(idLivros);
                                                }

                                                util.enviarMensagemTela(getBaseContext(), "Livro(s) excluído(s)");
                                                carregarLivros();
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

        if (resultCode == RESULT_OK && requestCode == LIVRO_GRAVADO) {
            carregarLivros();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_livro);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_livro);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
