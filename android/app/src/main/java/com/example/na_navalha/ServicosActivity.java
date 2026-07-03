package com.example.na_navalha;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.na_navalha.databinding.ActivityServicosBinding;

public class ServicosActivity extends AppCompatActivity {

    private ActivityServicosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_servicos);
    }
}