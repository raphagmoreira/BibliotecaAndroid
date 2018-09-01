package br.fib.biblioteca;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.fib.biblioteca.Adapters.UsuarioAdapter;
import br.fib.biblioteca.Controllers.UsuarioController;
import br.fib.biblioteca.Models.Usuario;
import br.fib.biblioteca.Util.Util;

public class UsuarioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private UsuarioController usuarioController;
    private final static Integer USUARIO_GRAVADO = 1;
    private ListView listaUsuarios;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        usuarioController = new UsuarioController(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_usuario);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_usuario);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        util = new Util();

        carregarUsuarios();
    }

    public void carregarUsuarios() {
        List<Usuario> usuarioList = usuarioController.findUsuarios(null);
        UsuarioAdapter usuarioAdapter = new UsuarioAdapter(getBaseContext(), usuarioList, this);

        listaUsuarios = findViewById(R.id.lista_usuarios);
        listaUsuarios.setAdapter(usuarioAdapter);

        /*listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), UsuarioModalActivity.class);
                intent.putExtra("idUsuario", String.valueOf(view.findViewById(R.id.txtNomeUsuario).getTag()));
                startActivityForResult(intent, USUARIO_GRAVADO);
            }
        });*/

        /*listaUsuarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {

            }
        });*/
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
            Intent intent = new Intent(getBaseContext(), UsuarioModalActivity.class);
            intent.putExtra("idUsuario", 0);
            startActivityForResult(intent, USUARIO_GRAVADO);
        }

        if (id == R.id.apagar) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exclusão de Usuários")
                    .setMessage("Deseja EXCLUIR os usuários selecionados?")
                    .setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listaUsuarios != null) {
                                        if (!listaUsuarios.getAdapter().isEmpty()) {
                                            UsuarioAdapter idSelecionados = (UsuarioAdapter) listaUsuarios.getAdapter();

                                            if (!idSelecionados.isEmpty()) {
                                                for (Long idUsuario : idSelecionados.getIdSelecionados()) {
                                                    usuarioController.apagarUsuario(idUsuario);
                                                }

                                                util.enviarMensagemTela(getBaseContext(), "Usuário(s) excluído(s)");
                                                carregarUsuarios();
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

        if (resultCode == RESULT_OK && requestCode == USUARIO_GRAVADO) {
            carregarUsuarios();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_usuario);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_usuario);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
