package com.example.na_navalha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.na_navalha.databinding.ActivityLoginBinding;
import com.example.na_navalha.repository.LoginRepository;
import com.example.na_navalha.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private boolean modoLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Observa login
        viewModel.loginSucesso.observe(this, sucesso -> {
            if (sucesso != null && sucesso) {
                Toast.makeText(this, "Login realizado!", Toast.LENGTH_SHORT).show();

                // Pega os dados do cliente retornados pela API
                LoginRepository.ClienteResponse cliente = viewModel.clienteData.getValue();
                int clienteId = cliente != null ? cliente.id : 1;
                String nomeCliente = cliente != null && cliente.nome != null ? cliente.nome : "Cliente";
                String emailCliente = binding.etEmail.getText().toString();

                // Salva no SharedPreferences
                getSharedPreferences("NaNavalha", MODE_PRIVATE)
                        .edit()
                        .putString("emailCliente", emailCliente)
                        .putString("nomeCliente", nomeCliente)
                        .putInt("clienteId", clienteId)
                        .apply();

                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        });

        // Observa cadastro
        viewModel.cadastroSucesso.observe(this, sucesso -> {
            if (sucesso != null && sucesso) {
                Toast.makeText(this, "Cadastro realizado! Faca login.", Toast.LENGTH_SHORT).show();
                alternarModo(true);
            }
        });

        // Observa erros
        viewModel.erroMensagem.observe(this, erro -> {
            if (erro != null) {
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
            }
        });

        // Toggle Entrar
        binding.btnToggleEntrar.setOnClickListener(v -> alternarModo(true));

        // Toggle Cadastrar
        binding.btnToggleCadastrar.setOnClickListener(v -> alternarModo(false));

        // Botão principal
        binding.btnEntrar.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String senha = binding.etSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (modoLogin) {
                viewModel.login(email, senha);
            } else {
                String nome = binding.etNome != null ? binding.etNome.getText().toString() : "";
                String telefone = binding.etTelefone != null ? binding.etTelefone.getText().toString() : "";
                viewModel.cadastrar(nome, "", email, senha, telefone);
            }
        });
    }

    private void alternarModo(boolean login) {
        modoLogin = login;
        if (login) {
            binding.btnToggleEntrar.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#2A2A2A")));
            binding.btnToggleEntrar.setTextColor(android.graphics.Color.parseColor("#F5F5F0"));
            binding.btnToggleCadastrar.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT));
            binding.btnToggleCadastrar.setTextColor(android.graphics.Color.parseColor("#888888"));
            binding.btnEntrar.setText("Entrar");
            binding.tvTitulo.setText("Bem-vindo de volta");
            if (binding.layoutCadastro != null)
                binding.layoutCadastro.setVisibility(View.GONE);
        } else {
            binding.btnToggleCadastrar.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#2A2A2A")));
            binding.btnToggleCadastrar.setTextColor(android.graphics.Color.parseColor("#F5F5F0"));
            binding.btnToggleEntrar.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT));
            binding.btnToggleEntrar.setTextColor(android.graphics.Color.parseColor("#888888"));
            binding.btnEntrar.setText("Cadastrar");
            binding.tvTitulo.setText("Criar conta");
            if (binding.layoutCadastro != null)
                binding.layoutCadastro.setVisibility(View.VISIBLE);
        }
    }
}