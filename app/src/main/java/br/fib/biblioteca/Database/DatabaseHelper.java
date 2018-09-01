package br.fib.biblioteca.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import br.fib.biblioteca.Models.Usuario;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Versão do banco
    private static final int DATABASE_VERSION = 1;

    //Nome do banco
    private static final String DATABASE_NAME = "biblioteca.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void dbScriptVersion1(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS USUARIO;");
        db.execSQL("CREATE TABLE USUARIO (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME VARCHAR(200), USERNAME VARCHAR(30), PASSWORD VARCHAR(10));");
        db.execSQL("INSERT INTO USUARIO (NOME, USERNAME, PASSWORD) VALUES ('ADMINISTRADOR', 'admin', '123');");

        db.execSQL("DROP TABLE IF EXISTS LIVRO;");
        db.execSQL("CREATE TABLE LIVRO (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITULO VARCHAR(300), IMAGEM VARCHAR(300));");
        db.execSQL("INSERT INTO LIVRO (TITULO, IMAGEM) VALUES ('Uma Mente Brilhante', 'uma_mente_brilhante.jpg');");

        db.execSQL("DROP TABLE IF EXISTS AUTOR;");
        db.execSQL("CREATE TABLE AUTOR (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME VARCHAR(300));");
        db.execSQL("INSERT INTO AUTOR (NOME) VALUES ('Raphael Garcia Moreira');");

        db.execSQL("DROP TABLE IF EXISTS EMPRESTIMO;");
        db.execSQL("CREATE TABLE EMPRESTIMO (ID INTEGER PRIMARY KEY AUTOINCREMENT, IDUSUARIO INTEGER, IDLIVRO INTEGER, DATA_EMPRESTIMO VARCHAR(10), DATA_DEVOLUCAO VARCHAR(10), DATA_DEVOLVIDO VARCHAR(10), FOREIGN KEY(IDUSUARIO) REFERENCES USUARIO(ID), FOREIGN KEY(IDLIVRO) REFERENCES LIVRO(ID));");

        db.execSQL("DROP TABLE IF EXISTS LIVRO_AUTOR;");
        db.execSQL("CREATE TABLE LIVRO_AUTOR (IDLIVRO INTEGER, IDAUTOR INTEGER, FOREIGN KEY(IDLIVRO) REFERENCES LIVRO(ID), FOREIGN KEY(IDAUTOR) REFERENCES AUTOR(ID));");
        db.execSQL("INSERT INTO LIVRO_AUTOR (IDLIVRO, IDAUTOR) VALUES (1, 1);");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.dbScriptVersion1(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
