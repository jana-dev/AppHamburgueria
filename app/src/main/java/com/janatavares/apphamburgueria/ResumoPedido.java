package com.janatavares.apphamburgueria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResumoPedido extends AppCompatActivity {

    public TextView textNomeCliente;
    public TextView textResumoPedido;
    public TextView textValorTotal;
    public TextView textQuantidadeTotal;
    public Button buttonFinalizarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumo_pedido);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textNomeCliente = findViewById(R.id.textNomeCliente);
        textResumoPedido = findViewById(R.id.resumoPedido);
        textValorTotal = findViewById(R.id.valorPedido);
        textQuantidadeTotal = findViewById(R.id.quantidadePedido);
        buttonFinalizarPedido = findViewById(R.id.buttonFinalizarPedido);

        //Obtendo os valores da tela MainActivity
        Intent intent = getIntent();
        String nomeCliente = intent.getStringExtra("nomeCliente");
        float valorTotal = intent.getFloatExtra("valorTotal",20.0f );
        int quantidade = intent.getIntExtra("quantidade", 1);
        boolean addBacon = intent.getBooleanExtra("addBacon", false);
        boolean addQueijo = intent.getBooleanExtra("addQueijo", false);
        boolean addOnionRings = intent.getBooleanExtra("addOnionRings", false);
        boolean addDoritos = intent.getBooleanExtra("addDoritos", false);
        boolean addGeleia = intent.getBooleanExtra("addGeleia", false);

        //Alterando as textViews para os valores obtidos da Main
        textNomeCliente.setText(nomeCliente);
        textQuantidadeTotal.setText(String.format(quantidade + " hamburguer(s) "));
        textValorTotal.setText(String.format("R$ %.2f", valorTotal));
        textResumoPedido.setText("Adicionais:");
        if(addBacon){
            textResumoPedido.setText(textResumoPedido.getText() + "\n Bacon ");
        }
        if(addQueijo){
            textResumoPedido.setText(textResumoPedido.getText() + "\n Queijo Cheddar");
        }
        if(addOnionRings){
            textResumoPedido.setText(textResumoPedido.getText() + "\n Onion Rings ");
        }
        if(addDoritos){
            textResumoPedido.setText(textResumoPedido.getText() + " \n Doritos ");
        }
        if(addGeleia){
            textResumoPedido.setText(textResumoPedido.getText() + "\n Geleia de pimenta ");
        }

        //Ao clicar no botão finalizar pedido, abrirá um email com os dados do pedido
        buttonFinalizarPedido.setOnClickListener(v -> {
            String emailHamburgueria = "pedididosJanaHamburgueria@gmail.com";

            //Cria o titulo do email
            String subject = "Pedido de " + textNomeCliente.getText().toString();

            //Cria o corpo do email
            String body = textQuantidadeTotal.getText().toString() + "\n";
            body += textResumoPedido.getText().toString() + "\n";
            body += textValorTotal.getText().toString();

            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:" + emailHamburgueria + "?subject=" + subject + "&body=" + body));

            startActivity(email);
        });

    }
}