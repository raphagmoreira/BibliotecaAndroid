package br.fib.biblioteca;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.fib.biblioteca.Controllers.UsuarioController;
import br.fib.biblioteca.Models.Usuario;
import br.fib.biblioteca.Util.Util;

public class UsuarioModalActivity extends AppCompatActivity {

    private UsuarioController usuarioController;
    private Usuario usuario;
    private EditText edtNomeCompleto;
    private EditText edtUsername;
    private EditText edtPassword;
    private Long idUsuario;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_usuario_modal);

        usuarioController = new UsuarioController(getBaseContext());
        util = new Util();

        try {
            idUsuario = Long.valueOf(getIntent().getStringExtra("idUsuario"));
        } catch (Exception e) {
            idUsuario = 0L;
        }

        usuario = usuarioController.findById(idUsuario);

        edtNomeCompleto = findViewById(R.id.edtNome);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        if (usuario != null) {
            edtNomeCompleto.setText(usuario.getNome());
            edtUsername.setText(usuario.getUsername());
            edtPassword.setText(usuario.getPassword());
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (usuario == null) {
                        if (idUsuario > 0) {
                            util.enviarMensagemTela(getBaseContext(), "Usuário não encontrado!");
                            return;
                        }

                        usuario = new Usuario();
                    }

                    usuario.setNome(String.valueOf(edtNomeCompleto.getText()));
                    usuario.setUsername(String.valueOf(edtUsername.getText()));
                    usuario.setPassword(String.valueOf(edtPassword.getText()));
                    usuarioController.gravarUsuario(usuario);

                    util.enviarMensagemTela(getBaseContext(), "Usuário gravado com sucesso!");

                    setResult(RESULT_OK);
                    finish();
                } catch (Exception e) {
                    util.enviarMensagemTela(getBaseContext(), "Problemas ao salvar!");
                }
            }
        });
    }
}
