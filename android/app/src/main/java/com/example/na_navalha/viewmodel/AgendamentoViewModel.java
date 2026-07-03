package com.example.na_navalha.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.na_navalha.model.Agendamento;
import com.example.na_navalha.repository.AgendamentoRepository;
import java.util.List;

public class AgendamentoViewModel extends ViewModel {

    private AgendamentoRepository repository;
    public MutableLiveData<List<Agendamento>> agendamentos = new MutableLiveData<>();
    public MutableLiveData<Boolean> operacaoSucesso = new MutableLiveData<>();

    public AgendamentoViewModel() {
        repository = new AgendamentoRepository();
    }

    public void carregarAgendamentos(int clienteId) {
        repository.listar(clienteId, agendamentos);
    }

    public void inserir(int clienteId, int barbeiroId, int servicoId, String dataHora) {
        repository.inserir(clienteId, barbeiroId, servicoId, dataHora, operacaoSucesso);
    }

    public void deletar(int id) {
        repository.deletar(id, operacaoSucesso);
    }
}