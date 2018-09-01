package br.fib.biblioteca.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fib.biblioteca.Database.DatabaseHelper;
import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.Models.Emprestimo;
import br.fib.biblioteca.Models.Livro;
import br.fib.biblioteca.Models.Usuario;

public class EmprestimoRepository {
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public EmprestimoRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void gravarEmprestimo(Emprestimo emprestimo) {
        db = databaseHelper.getWritableDatabase();

        ContentValues contentAutor = new ContentValues();
        contentAutor.put("IDUSUARIO", emprestimo.getIdUsuario());
        contentAutor.put("IDLIVRO", emprestimo.getIdLivro());
        contentAutor.put("DATA_EMPRESTIMO", emprestimo.getDataEmprestimo());
        contentAutor.put("DATA_DEVOLUCAO", emprestimo.getDataDevolucao());
        contentAutor.put("DATA_DEVOLVIDO", emprestimo.getDataDevolvido());

        if (emprestimo.getId() == null) {
            db.insert("EMPRESTIMO", null, contentAutor);
        } else if (emprestimo.getId() > 0) {
            db.update("EMPRESTIMO", contentAutor, "ID = " + emprestimo.getId(), null);
        }
    }

    public void apagarEmprestimo(Long idEmprestimo) {
        db = databaseHelper.getWritableDatabase();
        db.delete("EMPRESTIMO", "ID = " + idEmprestimo, null);
    }

    public Emprestimo findById(Long idEmprestimo) {

        String sql = "SELECT " +
                "EMP.ID, " +
                "EMP.IDLIVRO, " +
                "EMP.IDUSUARIO, " +
                "EMP.DATA_EMPRESTIMO, " +
                "EMP.DATA_DEVOLUCAO, " +
                "EMP.DATA_DEVOLVIDO, " +
                "USU.NOME AS USUARIO, " +
                "AUT.ID AS IDAUTOR, " +
                "AUT.NOME AS AUTOR, " +
                "LIV.TITULO " +
                "FROM EMPRESTIMO EMP " +
                "INNER JOIN USUARIO USU ON (USU.ID = EMP.IDUSUARIO) " +
                "INNER JOIN LIVRO LIV ON (LIV.ID = EMP.IDLIVRO) " +
                "INNER JOIN LIVRO_AUTOR LAU ON (LAU.IDLIVRO = LIV.ID) " +
                "INNER JOIN AUTOR AUT ON (AUT.ID = LAU.IDAUTOR) " +
                "WHERE EMP.ID = " + String.valueOf(idEmprestimo);

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                Autor autor = new Autor();

                autor.setId(cursor.getLong(cursor.getColumnIndex("IDAUTOR")));
                autor.setNome(cursor.getString(cursor.getColumnIndex("AUTOR")));

                Livro livro = new Livro();

                livro.setId(cursor.getLong(cursor.getColumnIndex("IDLIVRO")));
                livro.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                livro.setAutor(autor);

                Usuario usuario = new Usuario();
                usuario.setId(cursor.getLong(cursor.getColumnIndex("IDUSUARIO")));
                usuario.setNome(cursor.getString(cursor.getColumnIndex("USUARIO")));

                Emprestimo emprestimo = new Emprestimo();


                emprestimo.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                emprestimo.setIdLivro(cursor.getLong(cursor.getColumnIndex("IDLIVRO")));
                emprestimo.setIdUsuario(cursor.getLong(cursor.getColumnIndex("IDUSUARIO")));
                emprestimo.setDataEmprestimo(cursor.getString(cursor.getColumnIndex("DATA_EMPRESTIMO")));
                emprestimo.setDataDevolucao(cursor.getString(cursor.getColumnIndex("DATA_DEVOLUCAO")));
                emprestimo.setDataDevolvido(cursor.getString(cursor.getColumnIndex("DATA_DEVOLVIDO")));
                emprestimo.setLivro(livro);
                emprestimo.setUsuario(usuario);

                return emprestimo;
            } else {
                db.close();
                return null;
            }
        } else {
            db.close();
            return null;
        }
    }

    public List<Emprestimo> findEmprestimos(Emprestimo emprestimo) {
        String sql = "SELECT " +
                "EMP.ID, " +
                "EMP.IDLIVRO, " +
                "EMP.IDUSUARIO, " +
                "EMP.DATA_EMPRESTIMO, " +
                "EMP.DATA_DEVOLUCAO, " +
                "EMP.DATA_DEVOLVIDO, " +
                "USU.NOME AS USUARIO, " +
                "AUT.ID AS IDAUTOR, " +
                "AUT.NOME AS AUTOR, " +
                "LIV.TITULO " +
                "FROM EMPRESTIMO EMP " +
                "INNER JOIN USUARIO USU ON (USU.ID = EMP.IDUSUARIO) " +
                "INNER JOIN LIVRO LIV ON (LIV.ID = EMP.IDLIVRO) " +
                "INNER JOIN LIVRO_AUTOR LAU ON (LAU.IDLIVRO = LIV.ID) " +
                "INNER JOIN AUTOR AUT ON (AUT.ID = LAU.IDAUTOR) ";

        if (emprestimo != null) {
            sql += "WHERE 1=1 ";

            if (emprestimo.getId() != null) {
                sql += "AND EMP.ID = " + String.valueOf(emprestimo.getId()) + " ";
            }

            if (emprestimo.getLivro().getTitulo() != null) {
                sql += "AND LIV.TITULO LIKE '%" + String.valueOf(emprestimo.getLivro().getTitulo()) + "%' ";
            }

            if (emprestimo.getUsuario().getNome() != null) {
                sql += "AND USU.NOME LIKE '%" + String.valueOf(emprestimo.getUsuario().getNome()) + "%' ";
            }

            if (emprestimo.getIdLivro() != null) {
                sql += "AND EMP.DATA_EMPRESTIMO = '" + String.valueOf(emprestimo.getIdLivro()) + "' ";
            }

            if (emprestimo.getIdLivro() != null) {
                sql += "AND EMP.DATA_DEVOLUCAO = '" + String.valueOf(emprestimo.getIdLivro()) + "' ";
            }

            if (emprestimo.getIdLivro() != null) {
                sql += "AND EMP.DATA_DEVOLVIDO = '" + String.valueOf(emprestimo.getIdLivro()) + "' ";
            }
        }

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                List<Emprestimo> emprestimoList = new ArrayList<>();

                try {
                    do {
                        Autor autor = new Autor();

                        autor.setId(cursor.getLong(cursor.getColumnIndex("IDAUTOR")));
                        autor.setNome(cursor.getString(cursor.getColumnIndex("AUTOR")));

                        Livro livro = new Livro();

                        livro.setId(cursor.getLong(cursor.getColumnIndex("IDLIVRO")));
                        livro.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                        livro.setAutor(autor);

                        Usuario usuario = new Usuario();
                        usuario.setId(cursor.getLong(cursor.getColumnIndex("IDUSUARIO")));
                        usuario.setNome(cursor.getString(cursor.getColumnIndex("USUARIO")));

                        Emprestimo emprestimoRetorno = new Emprestimo();


                        emprestimoRetorno.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                        emprestimoRetorno.setIdLivro(cursor.getLong(cursor.getColumnIndex("IDLIVRO")));
                        emprestimoRetorno.setIdUsuario(cursor.getLong(cursor.getColumnIndex("IDUSUARIO")));
                        emprestimoRetorno.setDataEmprestimo(cursor.getString(cursor.getColumnIndex("DATA_EMPRESTIMO")));
                        emprestimoRetorno.setDataDevolucao(cursor.getString(cursor.getColumnIndex("DATA_DEVOLUCAO")));
                        emprestimoRetorno.setDataDevolvido(cursor.getString(cursor.getColumnIndex("DATA_DEVOLVIDO")));
                        emprestimoRetorno.setLivro(livro);
                        emprestimoRetorno.setUsuario(usuario);

                        emprestimoList.add(emprestimoRetorno);
                    } while(cursor.moveToNext());
                } finally {
                    cursor.close();
                }

                return emprestimoList;
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
