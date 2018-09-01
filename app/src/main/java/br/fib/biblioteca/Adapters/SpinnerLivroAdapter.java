package br.fib.biblioteca.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.Models.Livro;
import br.fib.biblioteca.R;

public class SpinnerLivroAdapter extends ArrayAdapter<Livro> {

    private Context context;
    private List<Livro> livroList;
    private LayoutInflater inflater;
    private Resources resLocal;
    private Long idLivro;

    public SpinnerLivroAdapter(Context context, int textViewResourceId, List<Livro> livroList, Resources resLocal) {
        super(context, textViewResourceId, livroList);
        this.context = context;
        this.livroList = livroList;
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

    public Long getIdLivro() {
        return idLivro;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_livro_item, parent, false);
        Livro livro = livroList.get(position);

        TextView txtLivro = row.findViewById(R.id.spinnerItemLivro);

        txtLivro.setText(livro.getTitulo());
        txtLivro.setTag(livro.getId());
        idLivro = livro.getId();

        return row;
    }
}
