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
import br.fib.biblioteca.Models.Emprestimo;
import br.fib.biblioteca.Models.Usuario;
import br.fib.biblioteca.R;

public class SpinnerUsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> usuarioList;
    private LayoutInflater inflater;
    private Resources resLocal;
    private Long idUsuario;

    public SpinnerUsuarioAdapter(Context context, int textViewResourceId, List<Usuario> usuarioList, Resources resLocal) {
        super(context, textViewResourceId, usuarioList);
        this.context = context;
        this.usuarioList = usuarioList;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_usuario_item, parent, false);
        Usuario usuario = usuarioList.get(position);

        TextView txtUsuario = row.findViewById(R.id.spinnerItemUsuario);

        txtUsuario.setText(usuario.getNome());
        txtUsuario.setTag(usuario.getId());
        idUsuario = usuario.getId();

        return row;
    }
}
