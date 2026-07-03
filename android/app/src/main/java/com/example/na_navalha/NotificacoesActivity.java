package com.example.na_navalha;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityNotificacoesBinding;

public class NotificacoesActivity extends AppCompatActivity {

    private ActivityNotificacoesBinding binding;

    String[][] notificacoes = {
            {"✓", "#5cb85c", "Agendamento confirmado!", "Seu corte + barba com Rafael Lima esta confirmado para amanha as 14:30.", "Agora ha pouco", "true"},
            {"🔔", "#C8102E", "Lembrete de horario", "Seu agendamento com Lucas Mendes e em 2 horas. Nao se atrase!", "2h atras", "true"},
            {"🏷", "#555555", "Oferta exclusiva", "20% de desconto na sua proxima barba. Valido apenas essa semana!", "1 dia atras", "false"},
            {"⏰", "#555555", "Horario alterado", "Seu agendamento do dia 18/05 foi transferido de 10:00 para 11:00 pelo barbeiro.", "2 dias atras", "false"},
            {"🔔", "#555555", "Nao perca sua barba!", "Faz 30 dias desde seu ultimo corte. Que tal agendar um horario?", "3 dias atras", "false"},
            {"🏷", "#555555", "Novo servico disponivel", "Pigmentacao capilar agora disponivel com o barbeiro Andre Costa!", "5 dias atras", "false"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notificacoes);

        binding.btnVoltar.setOnClickListener(v -> finish());
        binding.btnMarcarTodas.setOnClickListener(v -> finish());

        carregarNotificacoes();
    }

    private void carregarNotificacoes() {
        binding.layoutNotificacoes.removeAllViews();

        for (String[] n : notificacoes) {
            boolean naoLida = n[5].equals("true");

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setGravity(Gravity.TOP);
            card.setBackgroundColor(naoLida ?
                    Color.parseColor("#1A1A2A") : Color.parseColor("#1E1E1E"));
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            cp.setMargins(0, 0, 0, 8);
            card.setLayoutParams(cp);
            card.setPadding(24, 24, 24, 24);

            // Ícone
            TextView icone = new TextView(this);
            icone.setText(n[0]);
            icone.setTextSize(20);
            icone.setGravity(Gravity.CENTER);
            icone.setBackgroundColor(Color.parseColor(naoLida ? "#1E1E3A" : "#2A2A2A"));
            LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(72, 72);
            ip.setMargins(0, 0, 20, 0);
            icone.setLayoutParams(ip);
            card.addView(icone);

            // Conteúdo
            LinearLayout content = new LinearLayout(this);
            content.setOrientation(LinearLayout.VERTICAL);
            content.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            TextView titulo = new TextView(this);
            titulo.setText(n[2]);
            titulo.setTextColor(Color.parseColor("#F5F5F0"));
            titulo.setTextSize(14);
            titulo.setTypeface(null, naoLida ? Typeface.BOLD : Typeface.NORMAL);
            titulo.setPadding(0, 0, 0, 4);
            content.addView(titulo);

            TextView msg = new TextView(this);
            msg.setText(n[3]);
            msg.setTextColor(Color.parseColor("#888888"));
            msg.setTextSize(12);
            msg.setPadding(0, 0, 0, 8);
            content.addView(msg);

            TextView tempo = new TextView(this);
            tempo.setText(n[4]);
            tempo.setTextColor(Color.parseColor("#555555"));
            tempo.setTextSize(11);
            content.addView(tempo);

            card.addView(content);

            // Bolinha não lida
            if (naoLida) {
                TextView bolinha = new TextView(this);
                bolinha.setText("●");
                bolinha.setTextColor(Color.parseColor("#C8102E"));
                bolinha.setTextSize(10);
                card.addView(bolinha);
            }

            binding.layoutNotificacoes.addView(card);
        }
    }
}