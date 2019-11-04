package br.edu.ifsp.controlefinanceiro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import br.edu.ifsp.controlefinanceiro.R;
import br.edu.ifsp.controlefinanceiro.VO.TransacaoVO;
import br.edu.ifsp.controlefinanceiro.adapter.TransacaoAdapter;
import br.edu.ifsp.controlefinanceiro.dao.ContaDAO;
import br.edu.ifsp.controlefinanceiro.dao.TransacaoDAO;
import br.edu.ifsp.controlefinanceiro.model.Conta;
import br.edu.ifsp.controlefinanceiro.util.Constantes;
import br.edu.ifsp.controlefinanceiro.util.Utils;

public class DetalheContaActivity extends AppCompatActivity {

    private static final int NOVA_TRANSACAO_REQUEST_CODE = 0;
    TextView descricaoView;
    TextView saldoView;
    ListView historicoTransacoesListview;

    Integer contaId;

    List<TransacaoVO> historicoTransacao;

    TransacaoAdapter historicoTransacaoAdapter;

    private ContaDAO contaDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_conta);

        contaId = (Integer) getIntent().getSerializableExtra(Constantes.CONTA_DETALHE);

        Conta contaDetalhe = new ContaDAO(this).buscarContaPorId(contaId);

        contaDAO = new ContaDAO(this);

        historicoTransacao = new TransacaoDAO(this).buscarHistoricoTransacoesPorConta(contaId);

        historicoTransacaoAdapter = new TransacaoAdapter(this, historicoTransacao);
        historicoTransacoesListview = findViewById(R.id.historico_transacao_list_view);
        historicoTransacoesListview.setAdapter(historicoTransacaoAdapter);


        saldoView = findViewById(R.id.saldo_conta_detalhe);
        descricaoView = findViewById(R.id.descricao_conta_detalhe);

        saldoView.setText("R$".concat(Utils.formatDecimal(contaDAO.buscarSaldoContaPorId(contaId))));
        descricaoView.setText(contaDetalhe.getDescricao());

        getSupportActionBar().setSubtitle("Detalhes da conta bancária");
    }



    public void  novaTransacao(View view){
        Intent novaTransacaoIntent = new Intent(this, TransacaoActivity.class);

        novaTransacaoIntent.putExtra(Constantes.CONTA_ID, contaId);
        startActivityForResult(novaTransacaoIntent, NOVA_TRANSACAO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NOVA_TRANSACAO_REQUEST_CODE:
                if(resultCode == RESULT_OK) {

                    this.historicoTransacao.removeAll(historicoTransacao);
                    this.historicoTransacao.addAll(new TransacaoDAO(this).buscarHistoricoTransacoesPorConta(contaId));

                    saldoView.setText("R$ " + contaDAO.buscarSaldoContaPorId(contaId)); //Atualiza o saldo na tela
                    this.historicoTransacaoAdapter.notifyDataSetChanged();
                    Toast.makeText(this, R.string.msg_sucesso, Toast.LENGTH_LONG).show();
                }

                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(this, R.string.msg_cancelamento, Toast.LENGTH_LONG).show();
                }

                if(resultCode == Constantes.RESULT_ERROR){
                    Toast.makeText(this, R.string.msg_erro, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}