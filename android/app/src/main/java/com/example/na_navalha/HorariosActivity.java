package com.example.na_navalha;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import com.example.na_navalha.databinding.ActivityHorariosBinding;
import com.example.na_navalha.model.Agendamento;
import com.example.na_navalha.repository.AgendamentoRepository;
import java.util.List;

public class HorariosActivity extends AppCompatActivity {

    private ActivityHorariosBinding binding;
    private boolean mostraProximos = true;
    private AgendamentoRepository repository = new AgendamentoRepository();
    private MutableLiveData<List<Agendamento>> agendamentosLive = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_horarios);

        agendamentosLive.observe(this, lista -> {
            if (lista != null) {
                if (mostraProximos) {
                    mostrarProximosDoBanco(lista);
                } else {
                    mostrarHistoricoDoBanco(lista);
                }
            }
        });

        carregarDoBank();

        binding.btnProximos.setOnClickListener(v -> {
            mostraProximos = true;
            binding.btnProximos.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.parseColor("#2A2A2A")));
            binding.btnProximos.setTextColor(Color.parseColor("#F5F5F0"));
            binding.btnHistorico.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.TRANSPARENT));
            binding.btnHistorico.setTextColor(Color.parseColor("#888888"));
            carregarDoBank();
        });

        binding.btnHistorico.setOnClickListener(v -> {
            mostraProximos = false;
            binding.btnHistorico.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.parseColor("#2A2A2A")));
            binding.btnHistorico.setTextColor(Color.parseColor("#F5F5F0"));
            binding.btnProximos.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.TRANSPARENT));
            binding.btnProximos.setTextColor(Color.parseColor("#888888"));
            carregarDoBank();
        });

        // Bottom Nav
        binding.btnNavInicio.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
        binding.btnNavServicos.setOnClickListener(v ->
                startActivity(new Intent(this, AgendamentoFluxoActivity.class)));
        binding.btnNavHorarios.setOnClickListener(v -> {});
        binding.btnNavBarbeiros.setOnClickListener(v ->
                startActivity(new Intent(this, BarbeirosActivity.class)));
        binding.btnNavPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, PerfilActivity.class)));
    }

    private void carregarDoBank() {
        int clienteId = getSharedPreferences("NaNavalha", MODE_PRIVATE)
                .getInt("clienteId", 1);
        repository.listar(clienteId, agendamentosLive);
    }

    private void mostrarProximosDoBanco(List<Agendamento> lista) {
        binding.layoutLista.removeAllViews();
        boolean temProximos = false;

        for (Agendamento a : lista) {
            String status = a.getHorario() != null ? a.getHorario() : "Agendado";
            if (status.equals("Cancelado") || status.equals("Concluido")) continue;

            temProximos = true;
            String statusColor = status.equals("Agendado") ? "#5cb85c" : "#f0ad4e";
            String servico = a.getServico() != null ? a.getServico().getNome() : "-";
            String barbeiro = a.getBarbeiro() != null ? "com " + a.getBarbeiro().getNome() : "-";
            String data = a.getData() != null ? a.getData() : "-";
            String preco = a.getServico() != null ? "R$ " + (int) a.getServico().getPreco() : "-";

            adicionarCardProximo(a.getId(), status, servico, barbeiro, data, preco, statusColor);
        }

        if (!temProximos) {
            TextView tv = new TextView(this);
            tv.setText("Nenhum agendamento proximo encontrado.");
            tv.setTextColor(Color.parseColor("#888888"));
            tv.setTextSize(14);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0, 40, 0, 0);
            binding.layoutLista.addView(tv);
        }
    }

    private void mostrarHistoricoDoBanco(List<Agendamento> lista) {
        binding.layoutLista.removeAllViews();
        boolean temHistorico = false;

        for (Agendamento a : lista) {
            String status = a.getHorario() != null ? a.getHorario() : "Agendado";
            if (!status.equals("Cancelado") && !status.equals("Concluido")) continue;

            temHistorico = true;
            String statusColor = status.equals("Cancelado") ? "#e05555" : "#5cb85c";
            String servico = a.getServico() != null ? a.getServico().getNome() : "-";
            String barbeiro = a.getBarbeiro() != null ? a.getBarbeiro().getNome() : "-";
            String data = a.getData() != null ? a.getData() : "-";
            String preco = a.getServico() != null ? "R$ " + (int) a.getServico().getPreco() : "-";
            String info = barbeiro + " - " + data;

            adicionarCardHistorico(status, servico, info, preco, statusColor, false);
        }

        if (!temHistorico) {
            TextView tv = new TextView(this);
            tv.setText("Nenhum historico encontrado.");
            tv.setTextColor(Color.parseColor("#888888"));
            tv.setTextSize(14);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0, 40, 0, 0);
            binding.layoutLista.addView(tv);
        }
    }

    private void adicionarCardProximo(int id, String status, String servico, String barbeiro,
                                      String data, String preco, String statusColor) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(Color.parseColor("#1E1E1E"));
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cp.setMargins(0, 0, 0, 16);
        card.setLayoutParams(cp);
        card.setPadding(32, 32, 32, 32);

        // Status e preço
        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvStatus = new TextView(this);
        tvStatus.setText(status);
        tvStatus.setTextColor(Color.parseColor(statusColor));
        tvStatus.setTextSize(11);
        tvStatus.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        row1.addView(tvStatus);

        TextView tvPreco = new TextView(this);
        tvPreco.setText(preco);
        tvPreco.setTextColor(Color.parseColor("#C8102E"));
        tvPreco.setTextSize(14);
        row1.addView(tvPreco);
        card.addView(row1);

        // Serviço
        TextView tvServico = new TextView(this);
        tvServico.setText(servico);
        tvServico.setTextColor(Color.parseColor("#F5F5F0"));
        tvServico.setTextSize(16);
        tvServico.setTypeface(null, Typeface.BOLD);
        tvServico.setPadding(0, 8, 0, 4);
        card.addView(tvServico);

        // Barbeiro
        TextView tvBarbeiro = new TextView(this);
        tvBarbeiro.setText(barbeiro);
        tvBarbeiro.setTextColor(Color.parseColor("#888888"));
        tvBarbeiro.setTextSize(13);
        tvBarbeiro.setPadding(0, 0, 0, 12);
        card.addView(tvBarbeiro);

        // Data
        TextView tvData = new TextView(this);
        tvData.setText("Data: " + data);
        tvData.setTextColor(Color.parseColor("#888888"));
        tvData.setTextSize(12);
        tvData.setPadding(0, 0, 0, 16);
        card.addView(tvData);

        // Botões
        LinearLayout rowBtns = new LinearLayout(this);
        rowBtns.setOrientation(LinearLayout.HORIZONTAL);

        Button btnCancelar = new Button(this);
        btnCancelar.setText("Cancelar");
        btnCancelar.setTextColor(Color.parseColor("#F5F5F0"));
        btnCancelar.setBackgroundColor(Color.parseColor("#2A2A2A"));
        LinearLayout.LayoutParams bp1 = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        bp1.setMargins(0, 0, 8, 0);
        btnCancelar.setLayoutParams(bp1);

        btnCancelar.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Cancelar agendamento")
                    .setMessage("Tem certeza que deseja cancelar?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        MutableLiveData<Boolean> sucesso = new MutableLiveData<>();
                        sucesso.observe(this, ok -> {
                            if (ok != null && ok) {
                                Toast.makeText(this, "Agendamento cancelado!", Toast.LENGTH_SHORT).show();
                                carregarDoBank();
                            } else {
                                Toast.makeText(this, "Erro ao cancelar!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        repository.cancelar(id, sucesso);
                    })
                    .setNegativeButton("Nao", null)
                    .show();
        });

        rowBtns.addView(btnCancelar);

        Button btnRemarcar = new Button(this);
        btnRemarcar.setText("Remarcar");
        btnRemarcar.setTextColor(Color.WHITE);
        btnRemarcar.setBackgroundColor(Color.parseColor("#C8102E"));
        btnRemarcar.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        btnRemarcar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Remarcar agendamento");

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(40, 20, 40, 20);

            EditText etNovaData = new EditText(this);
            etNovaData.setHint("Nova data/hora (2024-06-01 10:00:00)");
            layout.addView(etNovaData);
            builder.setView(layout);

            builder.setPositiveButton("Remarcar", (dialog, which) -> {
                String novaData = etNovaData.getText().toString();
                if (novaData.isEmpty()) {
                    Toast.makeText(this, "Informe a nova data!", Toast.LENGTH_SHORT).show();
                    return;
                }
                MutableLiveData<Boolean> sucesso = new MutableLiveData<>();
                sucesso.observe(this, ok -> {
                    if (ok != null && ok) {
                        Toast.makeText(this, "Agendamento remarcado!", Toast.LENGTH_SHORT).show();
                        carregarDoBank();
                    } else {
                        Toast.makeText(this, "Erro ao remarcar!", Toast.LENGTH_SHORT).show();
                    }
                });
                repository.remarcar(id, novaData, sucesso);
            });
            builder.setNegativeButton("Cancelar", null);
            builder.show();
        });

        rowBtns.addView(btnRemarcar);
        card.addView(rowBtns);
        binding.layoutLista.addView(card);
    }

    private void adicionarCardHistorico(String status, String servico, String info,
                                        String preco, String statusColor, boolean avaliado) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(Color.parseColor("#1E1E1E"));
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cp.setMargins(0, 0, 0, 16);
        card.setLayoutParams(cp);
        card.setPadding(32, 32, 32, 32);

        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvStatus = new TextView(this);
        tvStatus.setText(status);
        tvStatus.setTextColor(Color.parseColor(statusColor));
        tvStatus.setTextSize(11);
        tvStatus.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        row1.addView(tvStatus);

        TextView tvPreco = new TextView(this);
        tvPreco.setText(preco);
        tvPreco.setTextColor(Color.parseColor("#C8102E"));
        tvPreco.setTextSize(14);
        row1.addView(tvPreco);
        card.addView(row1);

        TextView tvServico = new TextView(this);
        tvServico.setText(servico);
        tvServico.setTextColor(Color.parseColor("#F5F5F0"));
        tvServico.setTextSize(16);
        tvServico.setPadding(0, 8, 0, 4);
        card.addView(tvServico);

        TextView tvInfo = new TextView(this);
        tvInfo.setText(info);
        tvInfo.setTextColor(Color.parseColor("#888888"));
        tvInfo.setTextSize(12);
        tvInfo.setPadding(0, 0, 0, 12);
        card.addView(tvInfo);

        if (avaliado) {
            TextView tvAvaliado = new TextView(this);
            tvAvaliado.setText("★★★★★  Avaliado");
            tvAvaliado.setTextColor(Color.parseColor("#C8102E"));
            tvAvaliado.setTextSize(13);
            card.addView(tvAvaliado);
        } else if (status.equals("Finalizado")) {
            Button btnAvaliar = new Button(this);
            btnAvaliar.setText("★ Avaliar atendimento");
            btnAvaliar.setTextColor(Color.parseColor("#F5F5F0"));
            btnAvaliar.setBackgroundColor(Color.parseColor("#2A2A2A"));
            btnAvaliar.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            btnAvaliar.setOnClickListener(v ->
                    Toast.makeText(this, "Avaliacao - em breve!", Toast.LENGTH_SHORT).show());
            card.addView(btnAvaliar);
        }

        binding.layoutLista.addView(card);
    }
}