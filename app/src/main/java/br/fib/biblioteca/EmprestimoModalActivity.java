package br.fib.biblioteca;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.Spinner;

import br.fib.biblioteca.Adapters.SpinnerAutorAdapter;
import br.fib.biblioteca.Adapters.SpinnerLivroAdapter;
import br.fib.biblioteca.Adapters.SpinnerUsuarioAdapter;
import br.fib.biblioteca.Controllers.EmprestimoController;
import br.fib.biblioteca.Controllers.LivroController;
import br.fib.biblioteca.Controllers.UsuarioController;
import br.fib.biblioteca.Models.Emprestimo;
import br.fib.biblioteca.Models.Livro;
import br.fib.biblioteca.Models.Usuario;
import br.fib.biblioteca.Util.Util;
import java.util.Calendar;
import java.util.List;

public class EmprestimoModalActivity extends AppCompatActivity implements View.OnClickListener {
    private EmprestimoController emprestimoController;
    private Emprestimo emprestimo;
    private EditText edtDataEmprestimo;
    private EditText edtDataDevolucao;
    private EditText edtDataDevolvido;
    private Long idEmprestimo;
    private Util util;
    private Spinner comboUsuarios;
    private Spinner comboLivros;
    private List<Usuario> usuarioList;
    private List<Livro> livroList;
    private UsuarioController usuarioController;
    private LivroController livroController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_emprestimo_modal);

        emprestimoController = new EmprestimoController(getBaseContext());
        usuarioController = new UsuarioController(getBaseContext());
        livroController = new LivroController(getBaseContext());
        util = new Util();
        Resources res;

        try {
            idEmprestimo = Long.valueOf(getIntent().getStringExtra("idEmprestimo"));
        } catch (Exception e) {
            idEmprestimo = 0L;
        }

        emprestimo = emprestimoController.findById(idEmprestimo);

        edtDataEmprestimo = findViewById(R.id.edtDataEmprestimo);
        edtDataDevolucao = findViewById(R.id.edtDataDevolucao);
        edtDataDevolvido = findViewById(R.id.edtDataDevolvido);

        comboUsuarios = findViewById(R.id.spinner_usuario);
        res = getResources();
        usuarioList = usuarioController.findUsuarios(null);

        SpinnerUsuarioAdapter usuarioAdapter = new SpinnerUsuarioAdapter(this, R.layout.spinner_usuario_item, usuarioList, res);
        comboUsuarios.setAdapter(usuarioAdapter);

        comboLivros = findViewById(R.id.spinner_livro);
        res = getResources();
        livroList = livroController.findLivros(null);

        SpinnerLivroAdapter autorAdapter = new SpinnerLivroAdapter(this, R.layout.spinner_livro_item, livroList, res);
        comboLivros.setAdapter(autorAdapter);

        Button btnSalvar = findViewById(R.id.btnSalvarEmprestimo);
        btnSalvar.setOnClickListener(this);

        if (emprestimo != null) {
            edtDataEmprestimo.setText(emprestimo.getDataEmprestimo());
            edtDataDevolucao.setText(emprestimo.getDataDevolucao());
            edtDataDevolvido.setText(emprestimo.getDataDevolvido());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvarEmprestimo: {
                try {
                    if (emprestimo == null) {
                        if (idEmprestimo > 0) {
                            util.enviarMensagemTela(getBaseContext(), "Empréstimo não encontrado!");
                            return;
                        }

                        emprestimo = new Emprestimo();
                    }

                    emprestimo.setDataEmprestimo(String.valueOf(edtDataEmprestimo.getText()));
                    emprestimo.setDataDevolucao(String.valueOf(edtDataDevolucao.getText()));
                    emprestimo.setDataDevolvido(String.valueOf(edtDataDevolvido.getText()));

                    if (comboLivros != null) {
                        if (!comboLivros.getAdapter().isEmpty()) {
                            SpinnerLivroAdapter idSelecionado = (SpinnerLivroAdapter) comboLivros.getAdapter();

                            if (idSelecionado != null) {
                                Livro livro = new Livro(idSelecionado.getIdLivro());
                                emprestimo.setLivro(livro);
                                emprestimo.setIdLivro(livro.getId());
                            }
                        }
                    }

                    if (comboUsuarios != null) {
                        if (!comboUsuarios.getAdapter().isEmpty()) {
                            SpinnerUsuarioAdapter idSelecionado = (SpinnerUsuarioAdapter) comboUsuarios.getAdapter();

                            if (idSelecionado != null) {
                                Usuario usuario = new Usuario(idSelecionado.getIdUsuario());
                                emprestimo.setUsuario(usuario);
                                emprestimo.setIdUsuario(usuario.getId());
                            }
                        }
                    }

                    emprestimoController.gravarEmprestimo(emprestimo);

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
