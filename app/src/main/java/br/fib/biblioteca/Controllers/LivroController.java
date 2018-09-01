package br.fib.biblioteca.Controllers;

import android.content.Context;

import java.util.List;

import br.fib.biblioteca.Models.Livro;
import br.fib.biblioteca.Repository.LivroRepository;

public class LivroController {
    private LivroRepository livroRepository;

    public LivroController(Context context) {
        livroRepository = new LivroRepository(context);
    }

    public List<Livro> findLivros(Livro livro) {
        return livroRepository.findLivros(livro);
    }

    public void gravarLivro(Livro livro) {
        livroRepository.gravarLivro(livro);
    }

    public Livro findById(Long idLivro) {
        return livroRepository.findById(idLivro);
    }

    public void apagarLivro(Long idLivro) { livroRepository.apagarLivro(idLivro); }
}
