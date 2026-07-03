package com.example.na_navalha.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.na_navalha.repository.LoginRepository;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<Boolean> loginSucesso = new MutableLiveData<>();
    public MutableLiveData<Boolean> cadastroSucesso = new MutableLiveData<>();
    public MutableLiveData<String> erroMensagem = new MutableLiveData<>();
    public MutableLiveData<LoginRepository.ClienteResponse> clienteData = new MutableLiveData<>();

    private LoginRepository repository;

    public LoginViewModel() {
        repository = new LoginRepository();
    }

    public void login(String email, String senha) {
        repository.login(email, senha, loginSucesso, erroMensagem, clienteData);
    }

    public void cadastrar(String nome, String cpf, String email, String senha, String telefone) {
        repository.cadastrar(nome, cpf, email, senha, telefone, cadastroSucesso, erroMensagem);
    }
}