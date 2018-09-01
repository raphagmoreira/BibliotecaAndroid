package br.fib.biblioteca.Models;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class Livro implements Serializable {
    private Long id;
    private String titulo;
    private String imagem;
    private Autor autor;

    public Livro() {

    }

    public Livro(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Bitmap getCapa(AssetManager assetManager) throws Exception {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            InputStream in = assetManager.open("Capas/" + this.getImagem());

            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            return BitmapFactory.decodeByteArray(buffer.toByteArray(), 0, buffer.toByteArray().length);
        } catch (final IOException e) {
            throw new Exception();
        }
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
