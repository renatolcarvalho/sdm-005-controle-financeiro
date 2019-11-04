package br.edu.ifsp.controlefinanceiro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.controlefinanceiro.R;
import br.edu.ifsp.controlefinanceiro.model.Conta;

public class ContaAdapter extends ArrayAdapter<Conta> {

    private LayoutInflater layoutInflater;

    public ContaAdapter(@NonNull Context context, List<Conta> contatoList) {
        super(context, R.layout.conta_adapter, contatoList);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.conta_adapter, null);
        }

        TextView descricaoContatoTextView =  convertView.findViewById(R.id.descricaoContatoTextView);
        TextView saldoContatoTextView =  convertView.findViewById(R.id.saldoContatoTextView);

        Conta conta = getItem(position);
        descricaoContatoTextView.setText(conta.getDescricao());
        saldoContatoTextView.setText(String.valueOf(conta.getSaldo()));

        return convertView;
    }
}