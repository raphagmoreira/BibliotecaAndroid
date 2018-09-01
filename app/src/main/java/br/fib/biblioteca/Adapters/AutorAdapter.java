package br.fib.biblioteca.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.fib.biblioteca.AutorModalActivity;
import br.fib.biblioteca.Models.Autor;
import br.fib.biblioteca.R;

public class AutorAdapter extends BaseAdapter {
    private Context context;
    private List<Autor> autorList;
    private final List<Long> idSelecionados = new ArrayList<>();
    private Activity activity;
    private final static Integer AUTOR_GRAVADO = 1;

    public AutorAdapter(Context context, List<Autor> autorList, Activity activity) {
        this.context = context;
        this.autorList = autorList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return autorList.size();
    }

    @Override
    public Object getItem(int i) {
        return autorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return autorList.get(i).getId();
    }

    public List<Long> getIdSelecionados() {
        return idSelecionados;
    }

    public Activity getActivity() {
        return activity;
    }

    private Context getContext() {
        return this.context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Autor autor = autorList.get(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRetorno = inflater.inflate(R.layout.autor_cell_list, null);

        TextView txtAutor = viewRetorno.findViewById(R.id.txtAutor);
        txtAutor.setText(autor.getNome());
        txtAutor.setTag(autor.getId());

        txtAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AutorModalActivity.class);
                intent.putExtra("idAutor", String.valueOf(view.findViewById(R.id.txtAutor).getTag()));
                getActivity().startActivityForResult(intent, AUTOR_GRAVADO);
            }
        });

        CheckBox checkBox = viewRetorno.findViewById(R.id.checkbox_autor);
        checkBox.setTag(autor.getId());
        checkBox.setChecked(Boolean.FALSE);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkAutor = (CheckBox) view;
                Long idSelecionado = (Long) view.getTag();

                if (checkAutor.isChecked()) {
                    idSelecionados.add(idSelecionado);
                } else {
                    if (idSelecionados.contains(idSelecionado)) {
                        idSelecionados.remove(idSelecionado);
                    }
                }
            }
        });

        return viewRetorno;
    }
}
