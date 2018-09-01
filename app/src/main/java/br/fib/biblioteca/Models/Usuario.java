package br.fib.biblioteca.Models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Long id;
    private String nome;
    private String username;
    private String password;

    public Usuario() {

    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    public Usuario(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
