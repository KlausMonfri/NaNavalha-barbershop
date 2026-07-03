package com.example.na_navalha;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityDadosPessoaisBinding;

public class DadosPessoaisActivity extends AppCompatActivity {

    private ActivityDadosPessoaisBinding binding;
    private boolean editando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dados_pessoais);

        // Busca os dados do SharedPreferences
        SharedPreferences prefs = getSharedPreferences("NaNavalha", MODE_PRIVATE);
        String email = prefs.getString("emailCliente", "cliente@email.com");
        String nome = prefs.getString("nomeCliente", "Cliente");

        // Preenche os campos
        binding.etEmailDados.setText(email);
        binding.etNomeDados.setText(nome);
        binding.tvAvatarDados.setText(String.valueOf(nome.charAt(0)).toUpperCase());

        binding.btnVoltar.setOnClickListener(v -> finish());

        binding.btnAlterarSenha.setOnClickListener(v ->
                Toast.makeText(this, "Alterar senha - em breve!", Toast.LENGTH_SHORT).show());

        binding.btnEditar.setOnClickListener(v -> {
            if (!editando) {
                // Ativa edição
                editando = true;
                binding.etNomeDados.setEnabled(true);
                binding.etEmailDados.setEnabled(true);
                binding.etTelefoneDados.setEnabled(true);
                binding.etCpfDados.setEnabled(true);
                binding.etNomeDados.setTextColor(getColor(android.R.color.white));
                binding.etEmailDados.setTextColor(getColor(android.R.color.white));
                binding.etTelefoneDados.setTextColor(getColor(android.R.color.white));
                binding.etCpfDados.setTextColor(getColor(android.R.color.white));
                binding.btnEditar.setText("Salvar alteracoes");
            } else {
                // Pega os novos dados
                String novoNome = binding.etNomeDados.getText().toString();
                String novoEmail = binding.etEmailDados.getText().toString();

                if (novoNome.isEmpty() || novoEmail.isEmpty()) {
                    Toast.makeText(this, "Nome e email sao obrigatorios!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Salva no SharedPreferences
                getSharedPreferences("NaNavalha", MODE_PRIVATE)
                        .edit()
                        .putString("nomeCliente", novoNome)
                        .putString("emailCliente", novoEmail)
                        .apply();

                // Atualiza o avatar
                binding.tvAvatarDados.setText(String.valueOf(novoNome.charAt(0)).toUpperCase());

                // Desativa edição
                editando = false;
                binding.etNomeDados.setEnabled(false);
                binding.etEmailDados.setEnabled(false);
                binding.etTelefoneDados.setEnabled(false);
                binding.etCpfDados.setEnabled(false);
                binding.btnEditar.setText("Editar informacoes");

                Toast.makeText(this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}