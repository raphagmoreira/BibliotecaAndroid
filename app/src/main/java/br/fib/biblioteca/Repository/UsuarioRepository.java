package br.fib.biblioteca.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.fib.biblioteca.Database.DatabaseHelper;
import br.fib.biblioteca.Models.Usuario;

public class UsuarioRepository {
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public UsuarioRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void gravarUsuario(Usuario usuario) {
        db = databaseHelper.getWritableDatabase();

        ContentValues contentUsuario = new ContentValues();
        contentUsuario.put("NOME", usuario.getNome());
        contentUsuario.put("USERNAME", usuario.getUsername());
        contentUsuario.put("PASSWORD", usuario.getPassword());

        if (usuario.getId() == null) {
            db.insert("USUARIO", null, contentUsuario);
        } else if (usuario.getId() > 0) {
            db.update("USUARIO", contentUsuario, "ID = " + usuario.getId(), null);
        }
    }

    public void apagarUsuario(Long idUsuario) {
        db = databaseHelper.getWritableDatabase();
        db.delete("USUARIO", "ID = " + idUsuario, null);
    }

    public Usuario findByUserPass(String username, String password) {
        String sql = "SELECT NOME FROM USUARIO " +
                "WHERE UPPER(USERNAME) = '" + username.toUpperCase() + "' AND PASSWORD = '" + password + "'";

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor != null){
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                db.close();

                String nome = cursor.getString(cursor.getColumnIndex("NOME"));
                return new Usuario(nome);
            } else {
                db.close();
                return null;
            }
        } else {
            db.close();
            return null;
        }
    }

    public Usuario findById(Long idUsuario) {
        String sql = "SELECT ID, NOME, USERNAME, PASSWORD FROM USUARIO WHERE ID = " + String.valueOf(idUsuario);

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                Usuario usuarioRetorno = new Usuario();

                usuarioRetorno.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                usuarioRetorno.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                usuarioRetorno.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                usuarioRetorno.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));

                return usuarioRetorno;
            } else {
                db.close();
                return null;
            }
        } else {
            db.close();
            return null;
        }
    }

    public List<Usuario> findUsuarios(Usuario usuario) {
        String sql = "SELECT ID, NOME, USERNAME, PASSWORD FROM USUARIO ";

        if (usuario != null) {
            sql += "WHERE 1=1 ";

            if (usuario.getId() != null) {
                sql += "AND ID = " + String.valueOf(usuario.getId()) + " ";
            }

            if (usuario.getNome() != null) {
                sql += "AND NOME LIKE '%" + String.valueOf(usuario.getNome()) + "%' ";
            }

            if (usuario.getUsername() != null) {
                sql += "AND USERNAME LIKE '%" + String.valueOf(usuario.getUsername()) + "%' ";
            }
        }

        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                db.close();

                List<Usuario> usuarioList = new ArrayList<>();

                try {
                    do {
                        Usuario usuarioRetorno = new Usuario();

                        usuarioRetorno.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                        usuarioRetorno.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                        usuarioRetorno.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                        usuarioRetorno.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));

                        usuarioList.add(usuarioRetorno);
                    } while(cursor.moveToNext());
                } finally {
                    cursor.close();
                }

                return usuarioList;
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
