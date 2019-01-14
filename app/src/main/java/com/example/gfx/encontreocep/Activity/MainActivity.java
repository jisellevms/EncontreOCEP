package com.example.gfx.encontreocep.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gfx.encontreocep.R;
import com.example.gfx.encontreocep.Response.ViaCepResponse;
import com.example.gfx.encontreocep.Service.Service;

public class MainActivity extends AppCompatActivity {
    private EditText pesquisa;
    private TextView cep,rua,comp,bairro,local,uf,unidade,ibge,gia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pesquisa = findViewById(R.id.etSearch);
        cep = findViewById(R.id.tvCep);
        rua = findViewById(R.id.tvRua);
        comp = findViewById(R.id.tvComplemento);
        bairro = findViewById(R.id.tvBairro);
        local = findViewById(R.id.tvLocalidade);
        uf = findViewById(R.id.tvUf);
        unidade = findViewById(R.id.tvUnidade);
        ibge = findViewById(R.id.tvIbge);
        gia = findViewById(R.id.tvGia);

        pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                new Service(s.toString(), new Service.OnReturnServicePrimary() {
                    @Override
                    public void onCompletion(Object response) {
                        ViaCepResponse retorno = (ViaCepResponse) response;
                        cep.setText("CEP: "+retorno.getCep());
                        rua.setText("Logradouro: "+retorno.getLogradouro());
                        comp.setText("Complemento: "+retorno.getComplemento());
                        bairro.setText("Bairro: "+retorno.getBairro());
                        local.setText("Localidade: "+retorno.getLocalidade());
                        uf.setText("UF: "+retorno.getUf());
                        unidade.setText("Unidade: "+retorno.getUnidade());
                        ibge.setText("IBGE: "+retorno.getIbge());
                        gia.setText("GIA: "+retorno.getGia());
                    }

                    @Override
                    public void onError(String error) {

                    }
                }).execute();
            }
        });

    }
}
