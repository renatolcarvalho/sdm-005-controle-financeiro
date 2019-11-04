package br.edu.ifsp.controlefinanceiro.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.controlefinanceiro.R;
import br.edu.ifsp.controlefinanceiro.dao.ContaDAO;
import br.edu.ifsp.controlefinanceiro.model.Conta;
import br.edu.ifsp.controlefinanceiro.util.Constantes;

public class ContaActivity extends AppCompatActivity {

    private EditText saldo;
    private EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        pegarElementosTela();
        getSupportActionBar().setSubtitle("Preencha os dados da nova conta");
    }

    public void salvarConta(View view) {
        if (R.id.botao_salvar_nova_conta == view.getId()) {
            if (this.saldo.getText().toString() == "" || this.descricao.getText().toString() == "") {
                Toast.makeText(this, R.string.campos_obrigatorios, Toast.LENGTH_LONG).show();
                finish();
            }

            if(persistirConta()){
                setResult(RESULT_OK);
            }else{
                setResult(Constantes.RESULT_ERROR);
            }

            finish();
        }
    }

    public void cancelarConta(View view ){
        if (R.id.bota_cancelar_nova_conta == view.getId()) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private Boolean persistirConta(){
        Conta conta = new Conta();
        conta.setDescricao(String.valueOf(this.descricao.getText()));
        conta.setSaldo(Double.valueOf(this.saldo.getText().toString()));

        ContaDAO contaDAO = new ContaDAO(this);
        return contaDAO.salvaConta(conta);
    }

    private void pegarElementosTela() {
        this.saldo = findViewById(R.id.saldo_nova_conta);
        this.descricao = findViewById(R.id.descricao_conta_nova);
    }

}
