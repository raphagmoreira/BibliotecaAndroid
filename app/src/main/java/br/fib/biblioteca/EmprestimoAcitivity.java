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

import br.fib.biblioteca.Adapters.EmprestimoAdapter;
import br.fib.biblioteca.Controllers.EmprestimoController;
import br.fib.biblioteca.Models.Emprestimo;
import br.fib.biblioteca.Util.Util;

public class EmprestimoAcitivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EmprestimoController emprestimoController;
    private final static Integer EMPRESTIMO_GRAVADO = 1;
    private ListView listaEmprestimos;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emprestimo);

        emprestimoController = new EmprestimoController(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_emprestimo);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_emprestimo);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        util = new Util();

        carregarEmprestimos();
    }

    public void carregarEmprestimos() {
        List<Emprestimo> emprestimoList = emprestimoController.findEmprestimos(null);

        if (emprestimoList != null) {
            EmprestimoAdapter emprestimoAdapter = new EmprestimoAdapter(getBaseContext(), emprestimoList, this);

            listaEmprestimos = findViewById(R.id.lista_emprestimos);
            listaEmprestimos.setAdapter(emprestimoAdapter);
        }
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
            Intent intent = new Intent(getBaseContext(), EmprestimoModalActivity.class);
            intent.putExtra("idEmprestimo", 0);
            startActivityForResult(intent, EMPRESTIMO_GRAVADO);
        }

        if (id == R.id.apagar) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exclusão de Empréstimos")
                    .setMessage("Deseja EXCLUIR os empréstimos selecionados?")
                    .setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listaEmprestimos != null) {
                                        if (!listaEmprestimos.getAdapter().isEmpty()) {
                                            EmprestimoAdapter idSelecionados = (EmprestimoAdapter) listaEmprestimos.getAdapter();

                                            if (!idSelecionados.isEmpty()) {
                                                for (Long idEmprestimos : idSelecionados.getIdSelecionados()) {
                                                    emprestimoController.apagarEmprestimo(idEmprestimos);
                                                }

                                                util.enviarMensagemTela(getBaseContext(), "Empréstimo(s) excluído(s)");
                                                carregarEmprestimos();
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

        if (resultCode == RESULT_OK && requestCode == EMPRESTIMO_GRAVADO) {
            carregarEmprestimos();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_emprestimo);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_emprestimo);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
