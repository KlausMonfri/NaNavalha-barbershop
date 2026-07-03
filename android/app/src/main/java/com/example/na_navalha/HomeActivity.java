package com.example.na_navalha;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private String emailCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        // Pega os dados do login
        String nomeCliente = getIntent().getStringExtra("nomeCliente");
        emailCliente = getIntent().getStringExtra("emailCliente");

        if (nomeCliente != null && !nomeCliente.isEmpty()) {
            binding.tvNomeCliente.setText(nomeCliente);
        }

        // Botão de notificações
        binding.ivNotificacao.setOnClickListener(v ->
                startActivity(new Intent(this, NotificacoesActivity.class)));

        // Botão agendar agora (banner promoção)
        binding.btnAgendar.setOnClickListener(v ->
                startActivity(new Intent(this, AgendamentoFluxoActivity.class)));

        // Botão agendar horário
        binding.btnAgendarHorario.setOnClickListener(v ->
                startActivity(new Intent(this, AgendamentoFluxoActivity.class)));

        // Ver todos agendamentos
        binding.tvVerTodosAgendamentos.setOnClickListener(v ->
                startActivity(new Intent(this, HorariosActivity.class)));

        // Ver todos serviços
        binding.tvVerTodosServicos.setOnClickListener(v ->
                startActivity(new Intent(this, AgendamentoFluxoActivity.class)));

        // Ver todos barbeiros
        binding.tvVerTodosBarbeiros.setOnClickListener(v ->
                startActivity(new Intent(this, BarbeirosActivity.class)));

        // Bottom Nav - Início
        binding.btnNavInicio.setOnClickListener(v -> {});

        // Bottom Nav - Serviços
        binding.btnNavServicos.setOnClickListener(v ->
                startActivity(new Intent(this, AgendamentoFluxoActivity.class)));

        // Bottom Nav - Horários
        binding.btnNavHorarios.setOnClickListener(v ->
                startActivity(new Intent(this, HorariosActivity.class)));

        // Bottom Nav - Barbeiros
        binding.btnNavBarbeiros.setOnClickListener(v ->
                startActivity(new Intent(this, BarbeirosActivity.class)));

        // Bottom Nav - Perfil (passa o email para o perfil)
        binding.btnNavPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilActivity.class);
            intent.putExtra("emailCliente", emailCliente);
            startActivity(intent);
        });
    }
}