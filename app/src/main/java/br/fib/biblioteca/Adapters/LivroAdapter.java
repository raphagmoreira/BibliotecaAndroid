package br.fib.biblioteca.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.fib.biblioteca.LivroModalActivity;
import br.fib.biblioteca.Models.Livro;
import br.fib.biblioteca.R;

public class LivroAdapter extends BaseAdapter {
    private Context context;
    private List<Livro> livroList;
    private final List<Long> idSelecionados = new ArrayList<>();
    private Activity activity;
    private final static Integer LIVRO_GRAVADO = 1;
    private AssetManager assetManager;

    public LivroAdapter(Context context, List<Livro> livroList, Activity activity) {
        this.context = context;
        this.livroList = livroList;
        this.activity = activity;
        this.assetManager = context.getAssets();
    }

    @Override
    public int getCount() {
        return livroList.size();
    }

    @Override
    public Object getItem(int i) {
        return livroList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return livroList.get(i).getId();
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
        Livro livro = livroList.get(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRetorno = inflater.inflate(R.layout.livro_cell_list, null);

        try {
            ((ImageView) viewRetorno.findViewById(R.id.capa_livro)).setImageBitmap(livro.getCapa(this.assetManager));
        } catch (Exception e) {
            ((ImageView) viewRetorno.findViewById(R.id.capa_livro)).setImageResource(R.drawable.ic_livro);
        }

        TextView txtTitulo = viewRetorno.findViewById(R.id.txtTitulo);
        txtTitulo.setText(livro.getTitulo());
        txtTitulo.setTag(livro.getId());

        txtTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LivroModalActivity.class);
                intent.putExtra("idLivro", String.valueOf(view.findViewById(R.id.txtTitulo).getTag()));
                getActivity().startActivityForResult(intent, LIVRO_GRAVADO);
            }
        });

        ImageView capaLivro = viewRetorno.findViewById(R.id.capa_livro);
        capaLivro.setTag(livro.getId());

        capaLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LivroModalActivity.class);
                intent.putExtra("idLivro", String.valueOf(view.findViewById(R.id.capa_livro).getTag()));
                getActivity().startActivityForResult(intent, LIVRO_GRAVADO);
            }
        });

        CheckBox checkBox = viewRetorno.findViewById(R.id.checkbox_livro);
        checkBox.setTag(livro.getId());
        checkBox.setChecked(Boolean.FALSE);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkLivro = (CheckBox) view;
                Long idSelecionado = (Long) view.getTag();

                if (checkLivro.isChecked()) {
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
