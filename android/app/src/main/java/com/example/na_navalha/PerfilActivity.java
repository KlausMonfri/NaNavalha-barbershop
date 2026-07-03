package com.example.na_navalha;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityPerfilBinding;

public class PerfilActivity extends AppCompatActivity {

    private ActivityPerfilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_perfil);

        // Busca os dados do SharedPreferences
        SharedPreferences prefs = getSharedPreferences("NaNavalha", MODE_PRIVATE);
        String email = prefs.getString("emailCliente", "cliente@email.com");
        String nome = prefs.getString("nomeCliente", "Cliente");

        // Exibe os dados
        binding.tvEmailPerfil.setText(email);
        binding.tvNomePerfil.setText(nome);
        if (!email.isEmpty()) {
            binding.tvAvatarPerfil.setText(String.valueOf(nome.charAt(0)).toUpperCase());
        }

        // Dados pessoais
        binding.btnDadosPessoais.setOnClickListener(v -> {
            Intent intent = new Intent(this, DadosPessoaisActivity.class);
            intent.putExtra("emailCliente", email);
            intent.putExtra("nomeCliente", nome);
            startActivity(intent);
        });

        // Histórico de atendimentos
        binding.btnHistoricoAtendimentos.setOnClickListener(v ->
                startActivity(new Intent(this, HorariosActivity.class)));

        // Ajuda e FAQ
        binding.btnAjuda.setOnClickListener(v ->
                startActivity(new Intent(this, AjudaActivity.class)));

        // Fale conosco
        binding.btnFaleConosco.setOnClickListener(v ->
                Toast.makeText(this, "Fale conosco - em breve!", Toast.LENGTH_SHORT).show());

        // Sair da conta
        binding.btnSairConta.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Sair da conta")
                    .setMessage("Tem certeza que deseja sair?")
                    .setPositiveButton("Sair", (dialog, which) -> {
                        // Limpa os dados salvos
                        getSharedPreferences("NaNavalha", MODE_PRIVATE)
                                .edit()
                                .clear()
                                .apply();
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        // Bottom Nav
        binding.btnNavInicio.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
        binding.btnNavServicos.setOnClickListener(v ->
                startActivity(new Intent(this, AgendamentoFluxoActivity.class)));
        binding.btnNavHorarios.setOnClickListener(v -> {
            startActivity(new Intent(this, HorariosActivity.class));
            finish();
        });
        binding.btnNavBarbeiros.setOnClickListener(v -> {
            startActivity(new Intent(this, BarbeirosActivity.class));
            finish();
        });
        binding.btnNavPerfil.setOnClickListener(v -> {});
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza os dados quando voltar da tela de dados pessoais
        SharedPreferences prefs = getSharedPreferences("NaNavalha", MODE_PRIVATE);
        String emailAtualizado = prefs.getString("emailCliente", "cliente@email.com");
        String nomeAtualizado = prefs.getString("nomeCliente", "Cliente");

        binding.tvEmailPerfil.setText(emailAtualizado);
        binding.tvNomePerfil.setText(nomeAtualizado);
        binding.tvAvatarPerfil.setText(String.valueOf(nomeAtualizado.charAt(0)).toUpperCase());
    }
}