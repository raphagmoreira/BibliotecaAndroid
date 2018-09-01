package br.fib.biblioteca.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import br.fib.biblioteca.AutorActivity;
import br.fib.biblioteca.EmprestimoAcitivity;
import br.fib.biblioteca.LivroActivity;
import br.fib.biblioteca.LoginActivity;
import br.fib.biblioteca.MainActivity;
import br.fib.biblioteca.R;
import br.fib.biblioteca.UsuarioActivity;

public class Util {
    public void enviarMensagemTela(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }

    public Intent abrirMenu(Activity activity, MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.home) {
            intent = new Intent(activity, MainActivity.class);
        } else if (id == R.id.usuarios) {
            intent = new Intent(activity, UsuarioActivity.class);
        } else if (id == R.id.livros) {
            intent = new Intent(activity, LivroActivity.class);
        } else if (id == R.id.autores) {
            intent = new Intent(activity, AutorActivity.class);
        } else if (id == R.id.emprestimos) {
            intent = new Intent(activity, EmprestimoAcitivity.class);
        } else if (id == R.id.logoff) {
            intent = new Intent(activity, LoginActivity.class);
        }

        return intent;
    }
}
