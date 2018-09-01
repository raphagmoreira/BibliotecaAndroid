package br.fib.biblioteca;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import br.fib.biblioteca.Adapters.SpinnerAutorAdapter;
import br.fib.biblioteca.Controllers.AutorController;
import br.fib.biblioteca.Controllers.LivroController;
import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.Models.Livro;
import br.fib.biblioteca.Util.Util;

public class LivroModalActivity extends AppCompatActivity implements View.OnClickListener {
    private LivroController livroController;
    private Livro livro;
    private EditText edtTitulo;
    private EditText edtCapaLivro;
    private Long idLivro;
    private Util util;
    private static Integer FILE_SELECT_CODE = 1;
    private AutorController autorController;
    private List<Autor> autorList;
    private Spinner comboAutores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_livro_modal);

        livroController = new LivroController(getBaseContext());
        autorController = new AutorController(getBaseContext());
        util = new Util();

        try {
            idLivro = Long.valueOf(getIntent().getStringExtra("idLivro"));
        } catch (Exception e) {
            idLivro = 0L;
        }

        livro = livroController.findById(idLivro);

        edtTitulo = findViewById(R.id.edtTituloLivro);

        edtCapaLivro = findViewById(R.id.edtCapaLivro);
        edtCapaLivro.setEnabled(Boolean.FALSE);

        comboAutores = findViewById(R.id.spinner_autor);
        Resources res = getResources();
        autorList = autorController.findAutores(null);

        SpinnerAutorAdapter autorAdapter = new SpinnerAutorAdapter(this, R.layout.spinner_autor_item, autorList, res);
        comboAutores.setAdapter(autorAdapter);

        Button btnSalvar = findViewById(R.id.btnSalvarLivro);

        if (livro != null) {
            edtTitulo.setText(livro.getTitulo());

            if (livro.getAutor() != null) {
                if (livro.getAutor().getId() != null) {
                    comboAutores.setSelection(autorAdapter.getPosition(livro.getAutor()));
                }
            }
        }

        btnSalvar.setOnClickListener(this);

        Button btnUploadCapa = findViewById(R.id.btnUploadCapa);

        btnUploadCapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intent, "Selecione uma capa para o livro"), FILE_SELECT_CODE);
                } catch (Exception e) {
                    util.enviarMensagemTela(getBaseContext(), "Problemas ao realizar o upload da capa!");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == FILE_SELECT_CODE) {
            edtCapaLivro.setText(data.getData().getPath());
            util.enviarMensagemTela(getBaseContext(), "Capa selecionada com sucesso!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvarLivro: {
                try {
                    if (livro == null) {
                        if (idLivro > 0) {
                            util.enviarMensagemTela(getBaseContext(), "Livro não encontrado!");
                            return;
                        }

                        livro = new Livro();
                    }

                    livro.setTitulo(String.valueOf(edtTitulo.getText()));

                    if (comboAutores != null) {
                        if (!comboAutores.getAdapter().isEmpty()) {
                            SpinnerAutorAdapter idSelecionado = (SpinnerAutorAdapter) comboAutores.getAdapter();

                            if (idSelecionado != null) {
                                Autor autor = new Autor(idSelecionado.getIdAutor());
                                livro.setAutor(autor);
                            }
                        }
                    }

                    livroController.gravarLivro(livro);

                    util.enviarMensagemTela(getBaseContext(), "Livro gravado com sucesso!");

                    setResult(RESULT_OK);
                    finish();
                } catch (Exception e) {
                    util.enviarMensagemTela(getBaseContext(), "Problemas ao salvar!");
                }

                break;
            }
        }
    }
}
