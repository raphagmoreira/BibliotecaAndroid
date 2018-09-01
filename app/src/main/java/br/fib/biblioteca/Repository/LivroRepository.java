package br.fib.biblioteca.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fib.biblioteca.Database.DatabaseHelper;
import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.Models.Livro;

public class LivroRepository {
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public LivroRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void gravarLivro(Livro livro) {
        Long idInserido = 0L;
        db = databaseHelper.getWritableDatabase();

        ContentValues contentLivro = new ContentValues();
        contentLivro.put("TITULO", livro.getTitulo());
        contentLivro.put("IMAGEM", livro.getImagem());

        if (livro.getId() == null) {
            idInserido = db.insert("LIVRO", null, contentLivro);
        } else if (livro.getId() > 0) {
            db.update("LIVRO", contentLivro, "ID = " + livro.getId(), null);
        }

        if (idInserido > 0) {
            livro.setId(idInserido);
        }

        gravarLivroAutor(livro);
    }
    private void gravarLivroAutor(Livro livro) {
        //VINCULA O AUTOR AO LIVRO
        this.apagarLivroAutor(livro.getId());

        ContentValues contentLivroAutor = new ContentValues();
        contentLivroAutor.put("IDLIVRO", livro.getId());
        contentLivroAutor.put("IDAUTOR", livro.getAutor().getId());

        db.insert("LIVRO_AUTOR", null, contentLivroAutor);
    }

    private void apagarLivroAutor(Long idLivro) {
        db = databaseHelper.getWritableDatabase();
        db.delete("LIVRO_AUTOR", "IDLIVRO = " + idLivro, null);
    }

    public void apagarLivro(Long idLivro) {
        this.apagarLivroAutor(idLivro);

        db = databaseHelper.getWritableDatabase();
        db.delete("LIVRO", "ID = " + idLivro, null);
    }

    public Livro findById(Long idLivro) {
        String sql = "SELECT LIV.ID, LIV.TITULO, LIV.IMAGEM, LAU.IDAUTOR, AUT.NOME FROM LIVRO LIV" +
                " INNER JOIN LIVRO_AUTOR LAU ON (LAU.IDLIVRO = LIV.ID)" +
                " INNER JOIN AUTOR AUT ON (AUT.ID = LAU.IDAUTOR)" +
                " WHERE LIV.ID = " + String.valueOf(idLivro);

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                Autor autor = new Autor();

                autor.setId(cursor.getLong(cursor.getColumnIndex("IDAUTOR")));
                autor.setNome(cursor.getString(cursor.getColumnIndex("NOME")));

                Livro livro = new Livro();

                livro.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                livro.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                livro.setImagem(cursor.getString(cursor.getColumnIndex("IMAGEM")));
                livro.setAutor(autor);

                return livro;
            } else {
                db.close();
                return null;
            }
        } else {
            db.close();
            return null;
        }
    }

    public List<Livro> findLivros(Livro livro) {
        String sql = "SELECT ID, TITULO, IMAGEM FROM LIVRO ";

        if (livro != null) {
            sql += "WHERE 1=1 ";

            if (livro.getId() != null) {
                sql += "AND ID = " + String.valueOf(livro.getId()) + " ";
            }

            if (livro.getTitulo() != null) {
                sql += "AND TITULO LIKE '%" + String.valueOf(livro.getTitulo()) + "%' ";
            }
        }

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                List<Livro> livroList = new ArrayList<>();

                try {
                    do {
                        Livro livroRetorno = new Livro();

                        livroRetorno.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                        livroRetorno.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                        livroRetorno.setImagem(cursor.getString(cursor.getColumnIndex("IMAGEM")));

                        livroList.add(livroRetorno);
                    } while(cursor.moveToNext());
                } finally {
                    cursor.close();
                }

                return livroList;
            } else {
                db.close();
                return null;
            }
        } else {
            db.close();
            return null;
        }
    }
}
