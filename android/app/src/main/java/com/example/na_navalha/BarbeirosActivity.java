package com.example.na_navalha;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityBarbeirosBinding;

public class BarbeirosActivity extends AppCompatActivity {

    private ActivityBarbeirosBinding binding;
    private int barbeirAberto = -1;

    String[][] barbeiros = {
            {"1", "Rafael Lima", "Fade & Texturizado", "4.9", "312", "2.640", "#C8102E",
                    "10 anos de experiencia. Especialista em degrade e cortes modernos.",
                    "Fade,Texturizado,Degrade,Navalhado",
                    "09:00,10:30,14:00,16:30"},
            {"2", "Lucas Mendes", "Navalha & Barba", "4.8", "245", "1.950", "#003087",
                    "8 anos de experiencia. Especialista em barba e acabamentos.",
                    "Barba,Navalha,Acabamento,Bigode",
                    "09:30,11:00,14:30,17:00"},
            {"3", "Diego Santos", "Corte Classico", "4.7", "189", "1.420", "#2A5C2A",
                    "6 anos de experiencia. Especialista em cortes classicos e tradicionais.",
                    "Classico,Degradê,Social,Navalhado",
                    "10:00,13:00,15:00,16:00"},
            {"4", "Andre Costa", "Pigmentacao & Cor", "4.6", "134", "890", "#5C4A2A",
                    "5 anos de experiencia. Especialista em coloracao e pigmentacao capilar.",
                    "Coloracao,Pigmentacao,Progressiva,Hidratacao",
                    "09:00,11:30,14:00,15:30"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_barbeiros);

        carregarBarbeiros();

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
        binding.btnNavBarbeiros.setOnClickListener(v -> {});
        binding.btnNavPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, PerfilActivity.class)));
    }

    private void carregarBarbeiros() {
        binding.layoutBarbeiros.removeAllViews();

        for (int i = 0; i < barbeiros.length; i++) {
            final int index = i;
            String[] b = barbeiros[i];
            boolean aberto = barbeirAberto == i;

            // Card principal
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setBackgroundColor(Color.parseColor("#1E1E1E"));
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cp.setMargins(0, 0, 0, 8);
            card.setLayoutParams(cp);
            card.setPadding(24, 24, 24, 24);

            // Header do card
            LinearLayout header = new LinearLayout(this);
            header.setOrientation(LinearLayout.HORIZONTAL);
            header.setGravity(Gravity.CENTER_VERTICAL);

            // Avatar
            TextView avatar = new TextView(this);
            avatar.setText(b[1].substring(0, 1));
            avatar.setTextColor(Color.WHITE);
            avatar.setTextSize(18);
            avatar.setGravity(Gravity.CENTER);
            avatar.setBackgroundColor(Color.parseColor(b[6]));
            LinearLayout.LayoutParams ap = new LinearLayout.LayoutParams(90, 90);
            ap.setMargins(0, 0, 20, 0);
            avatar.setLayoutParams(ap);
            header.addView(avatar);

            // Info
            LinearLayout info = new LinearLayout(this);
            info.setOrientation(LinearLayout.VERTICAL);
            info.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            TextView nome = new TextView(this);
            nome.setText(b[1]);
            nome.setTextColor(Color.parseColor("#F5F5F0"));
            nome.setTextSize(15);
            nome.setTypeface(null, Typeface.BOLD);
            info.addView(nome);

            TextView esp = new TextView(this);
            esp.setText(b[2]);
            esp.setTextColor(Color.parseColor("#888888"));
            esp.setTextSize(12);
            esp.setPadding(0, 2, 0, 4);
            info.addView(esp);

            // Rating e cortes
            LinearLayout ratingRow = new LinearLayout(this);
            ratingRow.setOrientation(LinearLayout.HORIZONTAL);
            ratingRow.setGravity(Gravity.CENTER_VERTICAL);

            TextView dot = new TextView(this);
            dot.setText("●");
            dot.setTextColor(Color.parseColor("#5cb85c"));
            dot.setTextSize(8);
            dot.setPadding(0, 0, 6, 0);
            ratingRow.addView(dot);

            TextView rating = new TextView(this);
            rating.setText("★ " + b[3] + " (" + b[4] + ")");
            rating.setTextColor(Color.parseColor("#C8102E"));
            rating.setTextSize(12);
            ratingRow.addView(rating);

            TextView cortes = new TextView(this);
            cortes.setText(" · " + b[5] + " cortes");
            cortes.setTextColor(Color.parseColor("#555555"));
            cortes.setTextSize(12);
            ratingRow.addView(cortes);

            info.addView(ratingRow);
            header.addView(info);

            // Seta
            TextView seta = new TextView(this);
            seta.setText(aberto ? "∧" : "∨");
            seta.setTextColor(Color.parseColor("#888888"));
            seta.setTextSize(16);
            header.addView(seta);

            card.addView(header);

            // Expandido
            if (aberto) {
                // Descrição
                TextView desc = new TextView(this);
                desc.setText(b[7]);
                desc.setTextColor(Color.parseColor("#888888"));
                desc.setTextSize(13);
                desc.setPadding(0, 16, 0, 12);
                card.addView(desc);

                // Tags especialidades
                LinearLayout tags = new LinearLayout(this);
                tags.setOrientation(LinearLayout.HORIZONTAL);
                tags.setPadding(0, 0, 0, 16);
                String[] especialidades = b[8].split(",");
                for (String tag : especialidades) {
                    TextView tvTag = new TextView(this);
                    tvTag.setText(tag);
                    tvTag.setTextColor(Color.parseColor("#C8102E"));
                    tvTag.setTextSize(11);
                    tvTag.setBackgroundColor(Color.parseColor("#2A0A0A"));
                    LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    tp.setMargins(0, 0, 8, 0);
                    tvTag.setLayoutParams(tp);
                    tvTag.setPadding(16, 8, 16, 8);
                    tags.addView(tvTag);
                }
                card.addView(tags);

                // Horários disponíveis
                TextView tvHorarios = new TextView(this);
                tvHorarios.setText("Horarios disponiveis hoje:");
                tvHorarios.setTextColor(Color.parseColor("#F5F5F0"));
                tvHorarios.setTextSize(13);
                tvHorarios.setTypeface(null, Typeface.BOLD);
                tvHorarios.setPadding(0, 0, 0, 8);
                card.addView(tvHorarios);

                LinearLayout horariosRow = new LinearLayout(this);
                horariosRow.setOrientation(LinearLayout.HORIZONTAL);
                horariosRow.setPadding(0, 0, 0, 16);
                String[] horarios = b[9].split(",");
                for (String h : horarios) {
                    TextView tvH = new TextView(this);
                    tvH.setText(h);
                    tvH.setTextColor(Color.parseColor("#F5F5F0"));
                    tvH.setTextSize(12);
                    tvH.setBackgroundColor(Color.parseColor("#2A2A2A"));
                    LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    hp.setMargins(0, 0, 8, 0);
                    tvH.setLayoutParams(hp);
                    tvH.setPadding(20, 12, 20, 12);
                    horariosRow.addView(tvH);
                }
                card.addView(horariosRow);

                // Botão agendar
                Button btnAgendar = new Button(this);
                btnAgendar.setText("Agendar com " + b[1].split(" ")[0]);
                btnAgendar.setTextColor(Color.parseColor("#111111"));
                btnAgendar.setBackgroundColor(Color.parseColor("#C8102E"));
                btnAgendar.setTextColor(Color.WHITE);
                LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                btnAgendar.setLayoutParams(bp);
                btnAgendar.setOnClickListener(v ->
                        startActivity(new Intent(this, AgendamentoFluxoActivity.class)));
                card.addView(btnAgendar);
            }

            card.setOnClickListener(v -> {
                barbeirAberto = (barbeirAberto == index) ? -1 : index;
                carregarBarbeiros();
            });

            binding.layoutBarbeiros.addView(card);
        }
    }
}