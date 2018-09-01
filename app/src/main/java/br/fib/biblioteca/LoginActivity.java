package br.fib.biblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.fib.biblioteca.Controllers.UsuarioController;

public class LoginActivity extends AppCompatActivity {
    private UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuarioController = new UsuarioController(getBaseContext());

        Button login = findViewById(R.id.btnEntrar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.edtUsuario);
                EditText password = findViewById(R.id.edtSenha);

                if (usuarioController.validaUsuario(String.valueOf(username.getText()), String.valueOf(password.getText()))) {
                    Toast.makeText(getBaseContext(), "Login Correto!", Toast.LENGTH_SHORT).show();

                    Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
                    mainIntent.putExtra("usuario", String.valueOf(username.getText()));
                    startActivity(mainIntent);
                } else {
                    Toast.makeText(getBaseContext(), "Login Incorreto!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
