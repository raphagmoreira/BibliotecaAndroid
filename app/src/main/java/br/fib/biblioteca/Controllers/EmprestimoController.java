package br.fib.biblioteca.Controllers;

import android.content.Context;

import java.util.List;

import br.fib.biblioteca.Models.Emprestimo;
import br.fib.biblioteca.Repository.EmprestimoRepository;

public class EmprestimoController {
    private EmprestimoRepository emprestimoRepository;

    public EmprestimoController(Context context) {
        emprestimoRepository = new EmprestimoRepository(context);
    }

    public List<Emprestimo> findEmprestimos(Emprestimo emprestimo) {
        return emprestimoRepository.findEmprestimos(emprestimo);
    }

    public void gravarEmprestimo(Emprestimo emprestimo) {
        emprestimoRepository.gravarEmprestimo(emprestimo);
    }

    public Emprestimo findById(Long idEmprestimo) {
        return emprestimoRepository.findById(idEmprestimo);
    }

    public void apagarEmprestimo(Long idEmprestimo) { emprestimoRepository.apagarEmprestimo(idEmprestimo); }
}
