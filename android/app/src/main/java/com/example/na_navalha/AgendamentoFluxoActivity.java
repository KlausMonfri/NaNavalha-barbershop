package com.example.na_navalha;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityAgendamentoFluxoBinding;
import com.example.na_navalha.repository.AgendamentoRepository;

public class AgendamentoFluxoActivity extends AppCompatActivity {

    private ActivityAgendamentoFluxoBinding binding;
    private int passoAtual = 1;

    private int servicoId = -1;
    private String servicoNome = "";
    private double servicoPreco = 0;
    private int barbeiroId = -1;
    private String barbeiroNome = "";
    private String dataSelecionada = "";
    private String horarioSelecionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agendamento_fluxo);

        mostrarPasso(1);

        binding.btnVoltar.setOnClickListener(v -> finish());

        binding.btnVoltarPasso.setOnClickListener(v -> {
            if (passoAtual > 1) mostrarPasso(passoAtual - 1);
            else finish();
        });

        binding.btnContinuar.setOnClickListener(v -> {
            if (validarPasso()) {
                if (passoAtual < 5) {
                    mostrarPasso(passoAtual + 1);
                } else {
                    confirmarAgendamento();
                }
            }
        });
    }

    private boolean validarPasso() {
        switch (passoAtual) {
            case 1:
                if (servicoId == -1) { Toast.makeText(this, "Selecione um servico!", Toast.LENGTH_SHORT).show(); return false; }
                break;
            case 2:
                if (barbeiroId == -1) { Toast.makeText(this, "Selecione um barbeiro!", Toast.LENGTH_SHORT).show(); return false; }
                break;
            case 3:
                if (dataSelecionada.isEmpty()) { Toast.makeText(this, "Selecione uma data!", Toast.LENGTH_SHORT).show(); return false; }
                break;
            case 4:
                if (horarioSelecionado.isEmpty()) { Toast.makeText(this, "Selecione um horario!", Toast.LENGTH_SHORT).show(); return false; }
                break;
        }
        return true;
    }

    private void mostrarPasso(int passo) {
        boolean avancar = passo > passoAtual;
        passoAtual = passo;
        binding.tvPasso.setText("Passo " + passo + " de 5");
        atualizarProgresso(passo);
        binding.frameConteudo.removeAllViews();

        switch (passo) {
            case 1: mostrarServicos(); break;
            case 2: mostrarBarbeiros(); break;
            case 3: mostrarDatas(); break;
            case 4: mostrarHorarios(); break;
            case 5: mostrarConfirmacao(); binding.btnContinuar.setText("Confirmar"); break;
        }

        if (passo < 5) binding.btnContinuar.setText("Continuar");

        // Animação
        binding.frameConteudo.startAnimation(
                AnimationUtils.loadAnimation(this,
                        avancar ? R.anim.slide_in_right : R.anim.slide_in_left)
        );
    }

    private void atualizarProgresso(int passo) {
        int ativo = Color.parseColor("#C8102E");
        int inativo = Color.parseColor("#333333");

        binding.tab1.setTextColor(passo >= 1 ? ativo : inativo);
        binding.tab2.setTextColor(passo >= 2 ? ativo : inativo);
        binding.tab3.setTextColor(passo >= 3 ? ativo : inativo);
        binding.tab4.setTextColor(passo >= 4 ? ativo : inativo);
        binding.tab5.setTextColor(passo >= 5 ? ativo : inativo);

        binding.progress1.setBackgroundColor(passo >= 1 ? ativo : inativo);
        binding.progress2.setBackgroundColor(passo >= 2 ? ativo : inativo);
        binding.progress3.setBackgroundColor(passo >= 3 ? ativo : inativo);
        binding.progress4.setBackgroundColor(passo >= 4 ? ativo : inativo);
        binding.progress5.setBackgroundColor(passo >= 5 ? ativo : inativo);
    }

    private void mostrarServicos() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView titulo = new TextView(this);
        titulo.setText("Servicos");
        titulo.setTextColor(Color.parseColor("#F5F5F0"));
        titulo.setTextSize(22);
        titulo.setPadding(0, 0, 0, 8);
        layout.addView(titulo);

        TextView sub = new TextView(this);
        sub.setText("8 servicos disponiveis");
        sub.setTextColor(Color.parseColor("#888888"));
        sub.setTextSize(13);
        sub.setPadding(0, 0, 0, 24);
        layout.addView(sub);

        String[][] servicos = {
                {"1", "Corte Masculino", "R$ 40", "30 min", "POPULAR"},
                {"2", "Barba Completa", "R$ 30", "20 min", "POPULAR"},
                {"3", "Corte + Barba", "R$ 65", "50 min", "POPULAR"},
                {"4", "Sobrancelha", "R$ 20", "15 min", ""},
        };

        for (String[] s : servicos) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setBackgroundColor(Color.parseColor("#1E1E1E"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 16);
            card.setLayoutParams(params);
            card.setPadding(32, 32, 32, 32);

            TextView nome = new TextView(this);
            nome.setText(s[1] + (s[4].isEmpty() ? "" : "  POPULAR"));
            nome.setTextColor(Color.parseColor("#F5F5F0"));
            nome.setTextSize(15);
            card.addView(nome);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 16, 0, 0);

            TextView preco = new TextView(this);
            preco.setText(s[2]);
            preco.setTextColor(Color.parseColor("#C8102E"));
            preco.setTextSize(15);
            LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            preco.setLayoutParams(pp);
            row.addView(preco);

            TextView tempo = new TextView(this);
            tempo.setText(s[3]);
            tempo.setTextColor(Color.parseColor("#888888"));
            tempo.setTextSize(12);
            row.addView(tempo);

            Button btnAgendar = new Button(this);
            btnAgendar.setText("Agendar");
            btnAgendar.setTextColor(Color.parseColor("#F5F5F0"));
            btnAgendar.setBackgroundColor(Color.parseColor("#C8102E"));
            LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            bp.setMargins(16, 0, 0, 0);
            btnAgendar.setLayoutParams(bp);

            final int sid = Integer.parseInt(s[0]);
            final String snome = s[1];
            final double spreco = Double.parseDouble(s[2].replace("R$ ", ""));

            btnAgendar.setOnClickListener(v -> {
                servicoId = sid;
                servicoNome = snome;
                servicoPreco = spreco;
                mostrarPasso(2);
            });

            row.addView(btnAgendar);
            card.addView(row);
            layout.addView(card);
        }

        scroll.addView(layout);
        binding.frameConteudo.addView(scroll);
    }

    private void mostrarBarbeiros() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView titulo = new TextView(this);
        titulo.setText("Escolha o barbeiro");
        titulo.setTextColor(Color.parseColor("#F5F5F0"));
        titulo.setTextSize(20);
        titulo.setPadding(0, 0, 0, 24);
        layout.addView(titulo);

        String[][] barbeiros = {
                {"1", "Rafael Lima", "Fade e Texturizado", "4.9", "#C8102E"},
                {"2", "Lucas Mendes", "Navalha e Barba", "4.8", "#003087"},
                {"3", "Diego Santos", "Corte Classico", "4.7", "#2A5C2A"},
        };

        for (String[] b : barbeiros) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setGravity(Gravity.CENTER_VERTICAL);
            card.setBackgroundColor(Color.parseColor("#1E1E1E"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 160);
            params.setMargins(0, 0, 0, 16);
            card.setLayoutParams(params);
            card.setPadding(32, 0, 32, 0);

            TextView avatar = new TextView(this);
            avatar.setText(b[1].substring(0, 1));
            avatar.setTextColor(Color.WHITE);
            avatar.setTextSize(20);
            avatar.setGravity(Gravity.CENTER);
            avatar.setBackgroundColor(Color.parseColor(b[4]));
            LinearLayout.LayoutParams ap = new LinearLayout.LayoutParams(100, 100);
            ap.setMargins(0, 0, 24, 0);
            avatar.setLayoutParams(ap);
            card.addView(avatar);

            LinearLayout info = new LinearLayout(this);
            info.setOrientation(LinearLayout.VERTICAL);
            info.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            TextView nome = new TextView(this);
            nome.setText(b[1]);
            nome.setTextColor(Color.parseColor("#F5F5F0"));
            nome.setTextSize(15);
            nome.setPadding(0, 0, 0, 4);
            info.addView(nome);

            TextView esp = new TextView(this);
            esp.setText(b[2]);
            esp.setTextColor(Color.parseColor("#888888"));
            esp.setTextSize(12);
            esp.setPadding(0, 0, 0, 4);
            info.addView(esp);

            TextView rating = new TextView(this);
            rating.setText("★ " + b[3]);
            rating.setTextColor(Color.parseColor("#C8102E"));
            rating.setTextSize(12);
            info.addView(rating);

            card.addView(info);

            final int bid = Integer.parseInt(b[0]);
            final String bnome = b[1];

            card.setOnClickListener(v -> {
                barbeiroId = bid;
                barbeiroNome = bnome;
                mostrarPasso(3);
            });

            layout.addView(card);
        }

        scroll.addView(layout);
        binding.frameConteudo.addView(scroll);
    }

    private void mostrarDatas() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView titulo = new TextView(this);
        titulo.setText("Escolha a data");
        titulo.setTextColor(Color.parseColor("#F5F5F0"));
        titulo.setTextSize(20);
        titulo.setPadding(0, 0, 0, 24);
        layout.addView(titulo);

        String[][] datas = {
                {"HOJE", "24", "2025-05-24"},
                {"SEG", "25", "2025-05-25"},
                {"TER", "26", "2025-05-26"},
                {"QUA", "27", "2025-05-27"},
                {"QUI", "28", "2025-05-28"},
                {"SEX", "29", "2025-05-29"},
        };

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(row);

        for (String[] d : datas) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setGravity(Gravity.CENTER);
            card.setBackgroundColor(Color.parseColor("#1E1E1E"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 140, 1);
            params.setMargins(4, 0, 4, 0);
            card.setLayoutParams(params);
            card.setPadding(8, 16, 8, 16);

            TextView dia = new TextView(this);
            dia.setText(d[0]);
            dia.setTextColor(Color.parseColor("#888888"));
            dia.setTextSize(10);
            dia.setGravity(Gravity.CENTER);
            card.addView(dia);

            TextView num = new TextView(this);
            num.setText(d[1]);
            num.setTextColor(Color.parseColor("#F5F5F0"));
            num.setTextSize(20);
            num.setGravity(Gravity.CENTER);
            num.setPadding(0, 4, 0, 0);
            card.addView(num);

            final String data = d[2];

            card.setOnClickListener(v -> {
                dataSelecionada = data;
                mostrarPasso(4);
            });

            row.addView(card);
        }

        scroll.addView(layout);
        binding.frameConteudo.addView(scroll);
    }

    private void mostrarHorarios() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView titulo = new TextView(this);
        titulo.setText("Escolha o horario");
        titulo.setTextColor(Color.parseColor("#F5F5F0"));
        titulo.setTextSize(20);
        titulo.setPadding(0, 0, 0, 24);
        layout.addView(titulo);

        String[] horarios = {"09:00", "09:30", "10:00", "10:30", "11:00", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"};

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(3);

        for (String h : horarios) {
            Button btn = new Button(this);
            btn.setText(h);
            btn.setTextColor(Color.parseColor("#F5F5F0"));
            btn.setBackgroundColor(Color.parseColor("#1E1E1E"));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(8, 8, 8, 8);
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            btn.setLayoutParams(params);

            btn.setOnClickListener(v -> {
                horarioSelecionado = h;
                mostrarPasso(5);
            });

            grid.addView(btn);
        }

        layout.addView(grid);
        scroll.addView(layout);
        binding.frameConteudo.addView(scroll);
    }

    private void mostrarConfirmacao() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView titulo = new TextView(this);
        titulo.setText("Confirmar agendamento");
        titulo.setTextColor(Color.parseColor("#F5F5F0"));
        titulo.setTextSize(20);
        titulo.setPadding(0, 0, 0, 24);
        layout.addView(titulo);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(Color.parseColor("#1E1E1E"));
        card.setPadding(32, 32, 32, 32);
        layout.addView(card);

        adicionarLinhaConfirmacao(card, "Servico", servicoNome);
        adicionarLinhaConfirmacao(card, "Preco", "R$ " + (int)servicoPreco);
        adicionarLinhaConfirmacao(card, "Barbeiro", barbeiroNome);
        adicionarLinhaConfirmacao(card, "Data", dataSelecionada);
        adicionarLinhaConfirmacao(card, "Horario", horarioSelecionado);

        android.view.View divider = new android.view.View(this);
        divider.setBackgroundColor(Color.parseColor("#333333"));
        LinearLayout.LayoutParams dp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        dp.setMargins(0, 16, 0, 16);
        divider.setLayoutParams(dp);
        card.addView(divider);

        LinearLayout totalRow = new LinearLayout(this);
        totalRow.setOrientation(LinearLayout.HORIZONTAL);

        TextView totalLabel = new TextView(this);
        totalLabel.setText("Total");
        totalLabel.setTextColor(Color.parseColor("#F5F5F0"));
        totalLabel.setTextSize(16);
        totalLabel.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        totalRow.addView(totalLabel);

        TextView totalValor = new TextView(this);
        totalValor.setText("R$ " + (int)servicoPreco);
        totalValor.setTextColor(Color.parseColor("#C8102E"));
        totalValor.setTextSize(16);
        totalRow.addView(totalValor);

        card.addView(totalRow);

        scroll.addView(layout);
        binding.frameConteudo.addView(scroll);
    }

    private void adicionarLinhaConfirmacao(LinearLayout parent, String label, String valor) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rp.setMargins(0, 0, 0, 12);
        row.setLayoutParams(rp);

        TextView lbl = new TextView(this);
        lbl.setText(label);
        lbl.setTextColor(Color.parseColor("#888888"));
        lbl.setTextSize(13);
        lbl.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        row.addView(lbl);

        TextView val = new TextView(this);
        val.setText(valor);
        val.setTextColor(Color.parseColor("#F5F5F0"));
        val.setTextSize(13);
        row.addView(val);

        parent.addView(row);
    }

    private void confirmarAgendamento() {
        AgendamentoRepository repo = new AgendamentoRepository();
        String dataHora = dataSelecionada + " " + horarioSelecionado + ":00";
        repo.inserir(1, barbeiroId, servicoId, dataHora, null);
        mostrarPopupSucesso();
    }

    private void mostrarPopupSucesso() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sucesso);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button btnOk = dialog.findViewById(R.id.btnOkSucesso);
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(this, HorariosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        dialog.show();
    }
}