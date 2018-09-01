package br.fib.biblioteca.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.fib.biblioteca.Controllers.UsuarioController;
import br.fib.biblioteca.Models.Usuario;
import br.fib.biblioteca.R;
import br.fib.biblioteca.UsuarioActivity;
import br.fib.biblioteca.UsuarioModalActivity;

public class UsuarioAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> usuarioList;
    private final List<Long> idSelecionados = new ArrayList<>();
    private Activity activity;
    private final static Integer USUARIO_GRAVADO = 1;

    public UsuarioAdapter(Context context, List<Usuario> usuarioList, Activity activity) {
        this.context = context;
        this.usuarioList = usuarioList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return usuarioList.size();
    }

    @Override
    public Object getItem(int i) {
        return usuarioList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return usuarioList.get(i).getId();
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
        Usuario usuario = usuarioList.get(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRetorno = inflater.inflate(R.layout.usuario_cell_list, null);

        TextView txtNomeUsuario = viewRetorno.findViewById(R.id.txtNomeUsuario);
        txtNomeUsuario.setText(usuario.getNome());
        txtNomeUsuario.setTag(usuario.getId());

        txtNomeUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UsuarioModalActivity.class);
                intent.putExtra("idUsuario", String.valueOf(view.findViewById(R.id.txtNomeUsuario).getTag()));
                getActivity().startActivityForResult(intent, USUARIO_GRAVADO);
            }
        });

        CheckBox checkBox = viewRetorno.findViewById(R.id.checkbox_usuario);
        checkBox.setTag(usuario.getId());
        checkBox.setChecked(Boolean.FALSE);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkUsuario = (CheckBox) view;
                Long idSelecionado = (Long) view.getTag();

                if (checkUsuario.isChecked()) {
                    idSelecionados.add(idSelecionado);
                } else {
                    if (idSelecionados.contains(idSelecionado)) {
                        idSelecionados.remove(idSelecionado);
                    }
                }
            }
        });

        //Botão Remover
        /*ImageView remover = viewRetorno.findViewById(R.id.excluir_usuario);
        remover.setTag(usuario.getId());

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView botaoRemover = (ImageView) view;
                final Long idUsuario = (Long) botaoRemover.getTag();
                final UsuarioController usuarioController = new UsuarioController(getContext());

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exclusão de Usuários")
                        .setMessage("Deseja EXCLUIR o usuário selecionado?")
                        .setPositiveButton("Sim",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        usuarioController.apagarUsuario(idUsuario);
                                    }
                                })
                        .setNegativeButton("Nao", null)
                        .show();
            }
        });*/

        return viewRetorno;
    }
}
