package br.edu.ifsp.controlefinanceiro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifsp.controlefinanceiro.R;
import br.edu.ifsp.controlefinanceiro.adapter.ContaAdapter;
import br.edu.ifsp.controlefinanceiro.dao.ContaDAO;
import br.edu.ifsp.controlefinanceiro.dao.TransacaoDAO;
import br.edu.ifsp.controlefinanceiro.model.Conta;
import br.edu.ifsp.controlefinanceiro.util.Constantes;
import br.edu.ifsp.controlefinanceiro.util.Utils;

public class ListaContaActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    private final int NOVA_CONTA_REQUEST_CODE = 0;

    private ListView listaContaView;
    private TextView saldoContasView;
    private TextView entradaTextView;
    private TextView saidaTextView;

    private List<Conta> listaConta;

    ContaAdapter contaAdapter;

    ContaDAO contaDAO;
    TransacaoDAO transacaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_conta);

        contaDAO = new ContaDAO(this);
        transacaoDAO = new TransacaoDAO(this);

        listaConta = contaDAO.buscarContas();


        listaContaView = findViewById(R.id.lista_conta_view);
        listaContaView.setOnItemClickListener(this);

        saldoContasView = findViewById(R.id.totalSaldoContasView);

        saldoContasView.setText("R$".concat(Utils.formatDecimal(contaDAO.buscarSaldo())));
        contaAdapter = new ContaAdapter(this, listaConta);

        //Buscar total de entrada e saída
        entradaTextView = findViewById(R.id.entradaTextView);
        saidaTextView = findViewById(R.id.saidaTextView);
        entradaTextView.setText("R$ ".concat(String.valueOf(transacaoDAO.buscarValorTransacoesCredito())));
        saidaTextView.setText("R$ ".concat(String.valueOf(transacaoDAO.buscarValorTransacoesDebito())));

        listaContaView.setAdapter(contaAdapter);
    }

    @Override
    protected void onRestart() { super.onRestart();
        listaConta = contaDAO.buscarContas();
        saldoContasView.setText("R$ ".concat(Utils.formatDecimal(contaDAO.buscarSaldo())));
        contaAdapter = new ContaAdapter(this, listaConta);
        listaContaView.setAdapter(contaAdapter);

        entradaTextView.setText(String.valueOf(transacaoDAO.buscarValorTransacoesCredito()));
        saidaTextView.setText(String.valueOf(transacaoDAO.buscarValorTransacoesDebito()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        if(item.getItemId() == R.id.novaContaMenuItem){
            intent = new Intent(getApplicationContext(), ContaActivity.class);
            startActivityForResult(intent, NOVA_CONTA_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NOVA_CONTA_REQUEST_CODE:
                if(resultCode == RESULT_OK) {

                    this.listaConta.removeAll(listaConta);
                    this.listaConta.addAll(contaDAO.buscarContas());
                    saldoContasView.setText("R$ ".concat(String.valueOf(contaDAO.buscarSaldo())));
                    this.contaAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Conta conta = listaConta.get(position);

        Intent detalhesContaIntent = new Intent(this, DetalheContaActivity.class);
        detalhesContaIntent.putExtra(Constantes.CONTA_DETALHE, conta.getId());

        startActivity(detalhesContaIntent);
    }
}
