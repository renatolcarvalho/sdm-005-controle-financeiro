package br.edu.ifsp.controlefinanceiro.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.controlefinanceiro.R;
import br.edu.ifsp.controlefinanceiro.VO.TransacaoVO;

public class TransacaoAdapter extends ArrayAdapter<TransacaoVO> {

    private TextView descricaoTransacaoTextView;
    private TextView valorTransacaoTextView;
    private TextView centroCustoTransacaoTextView;
    private LayoutInflater layoutInflater;

    public TransacaoAdapter(@NonNull Context context, List<TransacaoVO> transacaoList) {
        super(context, R.layout.transacao_adapter, transacaoList);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.transacao_adapter, null);
        }

        this.descricaoTransacaoTextView =  convertView.findViewById(R.id.descricao_transacao_view);
        this.valorTransacaoTextView =  convertView.findViewById(R.id.valor_transacao_view);
        this.centroCustoTransacaoTextView =  convertView.findViewById(R.id.centro_custo_transacao_view);

        TransacaoVO transacao = getItem(position);
        descricaoTransacaoTextView.setText(transacao.getDescricao());
        valorTransacaoTextView.setText(setarSimbolo(transacao));
        centroCustoTransacaoTextView.setText("(".concat(transacao.getCentroCusto()).concat(")"));

        return convertView;
    }

    private String setarSimbolo(TransacaoVO transacao){
        if(transacao.getNaturezaOperacao().equals("Entrada")){
            return "+ R$ ".concat(String.valueOf(transacao.getValor()));
        }else{
            return "- R$ ".concat(String.valueOf(transacao.getValor()));
        }
    }
}
