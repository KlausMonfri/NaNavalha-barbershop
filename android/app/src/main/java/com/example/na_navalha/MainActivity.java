package com.example.na_navalha;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.na_navalha.databinding.ActivityMainBinding;
import com.example.na_navalha.model.Agendamento;
import com.example.na_navalha.viewmodel.AgendamentoViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AgendamentoViewModel viewModel;
    private ArrayAdapter<String> adapter;
    private List<String> itens = new ArrayList<>();
    private List<Agendamento> agendamentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(AgendamentoViewModel.class);

        // Vincula o ViewModel ao layout via Data Binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Pega o clienteId do SharedPreferences
        int clienteId = getSharedPreferences("NaNavalha", MODE_PRIVATE)
                .getInt("clienteId", 1);

        // Configura a lista
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itens);
        binding.listViewAgendamentos.setAdapter(adapter);

        // Observa a lista de agendamentos
        viewModel.agendamentos.observe(this, lista -> {
            itens.clear();
            agendamentos.clear();
            if (lista != null && !lista.isEmpty()) {
                for (Agendamento ag : lista) {
                    agendamentos.add(ag);
                    itens.add("Agendamento #" + ag.getId() + "\n" +
                            "Data: " + ag.getData());
                }
            } else {
                itens.add("Nenhum agendamento encontrado.");
            }
            adapter.notifyDataSetChanged();
        });

        // Observa o resultado das operações
        viewModel.operacaoSucesso.observe(this, sucesso -> {
            if (sucesso != null && sucesso) {
                Toast.makeText(this, "Operacao realizada com sucesso!", Toast.LENGTH_SHORT).show();
                limparCampos();
                viewModel.carregarAgendamentos(clienteId);
            } else if (sucesso != null) {
                Toast.makeText(this, "Erro na operacao!", Toast.LENGTH_SHORT).show();
            }
        });

        // Botão salvar
        binding.btnSalvar.setOnClickListener(v -> {
            String clienteIdStr = binding.etClienteId.getText().toString();
            String barbeiroIdStr = binding.etBarbeiroId.getText().toString();
            String servicoIdStr = binding.etServicoId.getText().toString();
            String dataHora = binding.etDataHora.getText().toString();

            if (clienteIdStr.isEmpty() || barbeiroIdStr.isEmpty() ||
                    servicoIdStr.isEmpty() || dataHora.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            int cId = Integer.parseInt(clienteIdStr);
            int barbeiroId = Integer.parseInt(barbeiroIdStr);
            int servicoId = Integer.parseInt(servicoIdStr);

            viewModel.inserir(cId, barbeiroId, servicoId, dataHora);
        });

        // Carrega os agendamentos
        viewModel.carregarAgendamentos(clienteId);
    }

    private void limparCampos() {
        binding.etClienteId.setText("");
        binding.etBarbeiroId.setText("");
        binding.etServicoId.setText("");
        binding.etDataHora.setText("");
    }
}