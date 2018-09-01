package br.fib.biblioteca.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fib.biblioteca.Database.DatabaseHelper;
import br.fib.biblioteca.Models.Autor;

public class AutorRepository {
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public AutorRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void gravarAutor(Autor autor) {
        db = databaseHelper.getWritableDatabase();

        ContentValues contentAutor = new ContentValues();
        contentAutor.put("NOME", autor.getNome());

        if (autor.getId() == null) {
            db.insert("AUTOR", null, contentAutor);
        } else if (autor.getId() > 0) {
            db.update("AUTOR", contentAutor, "ID = " + autor.getId(), null);
        }
    }

    public void apagarAutor(Long idAutor) {
        db = databaseHelper.getWritableDatabase();
        db.delete("AUTOR", "ID = " + idAutor, null);
    }

    public Autor findById(Long idAutor) {

        String sql = "SELECT ID, NOME FROM AUTOR WHERE ID = " + String.valueOf(idAutor);
        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                Autor autor = new Autor();

                autor.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                autor.setNome(cursor.getString(cursor.getColumnIndex("NOME")));

                return autor;
            } else {
                db.close();
                return null;
            }
        } else {
            db.close();
            return null;
        }
    }

    public List<Autor> findAutores(Autor autor) {
        String sql = "SELECT ID, NOME FROM AUTOR ";

        if (autor != null) {
            sql += "WHERE 1=1 ";

            if (autor.getId() != null) {
                sql += "AND ID = " + String.valueOf(autor.getId()) + " ";
            }

            if (autor.getNome() != null) {
                sql += "AND NOME LIKE '%" + String.valueOf(autor.getNome()) + "%' ";
            }
        }

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                List<Autor> autorList = new ArrayList<>();

                try {
                    do {
                        Autor autorRetorno = new Autor();

                        autorRetorno.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                        autorRetorno.setNome(cursor.getString(cursor.getColumnIndex("NOME")));

                        autorList.add(autorRetorno);
                    } while(cursor.moveToNext());
                } finally {
                    cursor.close();
                }

                return autorList;
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
