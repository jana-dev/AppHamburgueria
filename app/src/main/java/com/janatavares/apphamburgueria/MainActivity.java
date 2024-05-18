package com.janatavares.apphamburgueria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public EditText nomeCliente;
    public CheckBox addBacon;
    public CheckBox addQueijo;
    public CheckBox addOnionRings;
    public CheckBox addDoritos;
    public CheckBox addGeleia;
    public Button btMenos;
    public Button btMais;
    public TextView quantidadeTotal;
    public TextView valorTotalPedido;
    public Button confirmarPedido;
    public float valorTotal;
    public int quantidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nomeCliente = findViewById(R.id.editTextNome);
        addBacon = findViewById(R.id.checkBoxBacon);
        addQueijo = findViewById(R.id.checkBoxQueijo);
        addOnionRings = findViewById(R.id.checkBoxOnionRing);
        addDoritos = findViewById(R.id.checkBoxDoritos);
        addGeleia = findViewById(R.id.checkBoxGeleia);
        btMenos = findViewById(R.id.buttonMenos);
        btMais = findViewById(R.id.buttonMais);
        quantidadeTotal = findViewById(R.id.textViewQuantidade);
        confirmarPedido = findViewById(R.id.buttonConfirmarPedido);
        valorTotalPedido = findViewById(R.id.textViewValorTotal);

        quantidade = 1;
        valorTotal = 20.0f;


        // Adiciona ouvintes de evento para os checkBox, caso selecione acrescenta o valor, caso desmarcado, diminui o valor
        addBacon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            valorTotal += isChecked ? 2f : -2f;
            atualizarQuantidadeTotal();
        });
        addQueijo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            valorTotal += isChecked ? 2f : -2f;
            atualizarQuantidadeTotal();
        });
        addOnionRings.setOnCheckedChangeListener((buttonView, isChecked) -> {
            valorTotal += isChecked ? 3f : -3f;
            atualizarQuantidadeTotal();
        });
        addDoritos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            valorTotal += isChecked ? 4f : -4f;
            atualizarQuantidadeTotal();
        });
        addGeleia.setOnCheckedChangeListener((buttonView, isChecked) -> {
            valorTotal += isChecked ? 2f : -2f;
            atualizarQuantidadeTotal();
        });

        // Adiciona ouvintes de evento para os botões de menos e mais
        btMenos.setOnClickListener(v -> {
            if (quantidade > 1) {
                quantidade--;
                atualizarQuantidadeTotal();
            }
        });
        btMais.setOnClickListener(v -> {
            quantidade++;
            atualizarQuantidadeTotal();
        });

        // Adiciona ouvintes de evento para o botão de confirmar pedido
        // Inicia uma nova intent para abrir a outra tela e passar os valores desta tela para a próxima
        confirmarPedido.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResumoPedido.class);
            intent.putExtra("nomeCliente", nomeCliente.getText().toString());
            intent.putExtra("quantidade", quantidade);
            intent.putExtra("addBacon", addBacon.isChecked());
            intent.putExtra("addQueijo", addQueijo.isChecked());
            intent.putExtra("addOnionRings", addOnionRings.isChecked());
            intent.putExtra("addDoritos", addDoritos.isChecked());
            intent.putExtra("addGeleia", addGeleia.isChecked());
            intent.putExtra("valorTotal", valorTotal * quantidade);
            startActivity(intent);
        });
    }

    // Atualiza a quantidade total e o valor total do pedido
    private void atualizarQuantidadeTotal() {
        quantidadeTotal.setText(String.valueOf(quantidade));
        valorTotalPedido.setText(String.format("R$ %.2f", valorTotal * quantidade));

    }

}