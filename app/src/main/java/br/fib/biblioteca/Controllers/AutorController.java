package br.fib.biblioteca.Controllers;

import android.content.Context;

import java.util.List;

import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.Repository.AutorRepository;

public class AutorController {
    private AutorRepository autorRepository;

    public AutorController(Context context) {
        autorRepository = new AutorRepository(context);
    }

    public List<Autor> findAutores(Autor autor) {
        return autorRepository.findAutores(autor);
    }

    public void gravarAutor(Autor autor) {
        autorRepository.gravarAutor(autor);
    }

    public Autor findById(Long idAutor) {
        return autorRepository.findById(idAutor);
    }

    public void apagarAutor(Long idAutor) { autorRepository.apagarAutor(idAutor); }
}
