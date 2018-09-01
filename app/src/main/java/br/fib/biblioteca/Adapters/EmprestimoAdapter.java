package br.fib.biblioteca.Adapters;

import android.annotation.SuppressLint;
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

import br.fib.biblioteca.EmprestimoModalActivity;
import br.fib.biblioteca.Models.Emprestimo;
import br.fib.biblioteca.R;

public class EmprestimoAdapter extends BaseAdapter {
    private Context context;
    private List<Emprestimo> emprestimoList;
    private final List<Long> idSelecionados = new ArrayList<>();
    private Activity activity;
    private final static Integer EMPRESTIMO_GRAVADO = 1;

    public EmprestimoAdapter(Context context, List<Emprestimo> emprestimoList, Activity activity) {
        this.context = context;
        this.emprestimoList = emprestimoList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return emprestimoList.size();
    }

    @Override
    public Object getItem(int i) {
        return emprestimoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return emprestimoList.get(i).getId();
    }

    public List<Long> getIdSelecionados() {
        return idSelecionados;
    }

    private Context getContext() {
        return this.context;
    }

    public Activity getActivity() {
        return activity;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Emprestimo emprestimo = emprestimoList.get(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRetorno = inflater.inflate(R.layout.emprestimo_cell_list, null);

        TextView txtEmprestimo = viewRetorno.findViewById(R.id.txtEmprestimo);
        txtEmprestimo.setText(emprestimo.getLivro().getTitulo());
        txtEmprestimo.setTag(emprestimo.getId());

        txtEmprestimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EmprestimoModalActivity.class);
                intent.putExtra("idEmprestimo", String.valueOf(view.findViewById(R.id.txtEmprestimo).getTag()));
                getActivity().startActivityForResult(intent, EMPRESTIMO_GRAVADO);
            }
        });

        CheckBox checkBox = viewRetorno.findViewById(R.id.checkbox_emprestimo);
        checkBox.setTag(emprestimo.getId());
        checkBox.setChecked(Boolean.FALSE);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkEmprestimo = (CheckBox) view;
                Long idSelecionado = (Long) view.getTag();

                if (checkEmprestimo.isChecked()) {
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
