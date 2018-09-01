package br.fib.biblioteca;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.fib.biblioteca.Controllers.AutorController;
import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.Util.Util;

public class AutorModalActivity extends AppCompatActivity {
    private AutorController autorController;
    private Autor autor;
    private EditText edtAutor;
    private Long idAutor;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_autor_modal);

        autorController = new AutorController(getBaseContext());
        util = new Util();

        try {
            idAutor = Long.valueOf(getIntent().getStringExtra("idAutor"));
        } catch (Exception e) {
            idAutor = 0L;
        }

        autor = autorController.findById(idAutor);
        edtAutor = findViewById(R.id.edtNomeAutor);

        Button btnSalvar = findViewById(R.id.btnSalvarAutor);

        if (autor != null) {
            edtAutor.setText(autor.getNome());
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (autor == null) {
                        if (idAutor > 0) {
                            util.enviarMensagemTela(getBaseContext(), "Autor não encontrado!");
                            return;
                        }

                        autor = new Autor();
                    }

                    autor.setNome(String.valueOf(edtAutor.getText()));
                    autorController.gravarAutor(autor);

                    util.enviarMensagemTela(getBaseContext(), "Autor gravado com sucesso!");

                    setResult(RESULT_OK);
                    finish();
                } catch (Exception e) {
                    util.enviarMensagemTela(getBaseContext(), "Problemas ao salvar!");
                }
            }
        });
    }
}
