package br.fib.biblioteca.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.R;

public class SpinnerAutorAdapter extends ArrayAdapter<Autor> {

    private Context context;
    private List<Autor> autorList;
    private LayoutInflater inflater;
    private Resources resLocal;
    private Long idAutor;

    public SpinnerAutorAdapter(Context context, int textViewResourceId, List<Autor> autorList, Resources resLocal) {
        super(context, textViewResourceId, autorList);
        this.context = context;
        this.autorList = autorList;
        this.resLocal = resLocal;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_autor_item, parent, false);
        Autor autor = autorList.get(position);

        TextView txtAutor = row.findViewById(R.id.spinnerItemAutor);

        txtAutor.setText(autor.getNome());
        txtAutor.setTag(autor.getId());
        idAutor = autor.getId();

        return row;
    }
}
