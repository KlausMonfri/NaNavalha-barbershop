package com.example.na_navalha;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityAjudaBinding;

public class AjudaActivity extends AppCompatActivity {

    private ActivityAjudaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ajuda);

        binding.btnVoltar.setOnClickListener(v -> finish());

        configurarFaq(binding.faq1, binding.resp1, binding.seta1);
        configurarFaq(binding.faq2, binding.resp2, binding.seta2);
        configurarFaq(binding.faq3, binding.resp3, binding.seta3);
        configurarFaq(binding.faq4, binding.resp4, binding.seta4);
        configurarFaq(binding.faq5, binding.resp5, binding.seta5);
    }

    private void configurarFaq(View faq, android.widget.TextView resp,
                               android.widget.TextView seta) {
        faq.setOnClickListener(v -> {
            if (resp.getVisibility() == View.GONE) {
                resp.setVisibility(View.VISIBLE);
                seta.setText("-");
            } else {
                resp.setVisibility(View.GONE);
                seta.setText("+");
            }
        });
    }
}