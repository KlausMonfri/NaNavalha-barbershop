package com.example.na_navalha.repository;

import com.example.na_navalha.Constants;
import com.example.na_navalha.model.Agendamento;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LoginRepository {

    private static final String BASE_URL = Constants.BASE_URL;

    // Modelo para receber a resposta do login
    public static class ClienteResponse {
        public int id;
        public String nome;
        public String email;
    }

    public interface LoginApi {
        @FormUrlEncoded
        @POST("login")
        Call<ClienteResponse> login(
                @Field("email") String email,
                @Field("senha") String senha
        );

        @FormUrlEncoded
        @POST("cadastro")
        Call<ClienteResponse> cadastrar(
                @Field("nome") String nome,
                @Field("cpf") String cpf,
                @Field("email") String email,
                @Field("senha") String senha,
                @Field("telefone") String telefone
        );
    }

    private LoginApi api;

    public LoginRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(LoginApi.class);
    }

    public void login(String email, String senha,
                      MutableLiveData<Boolean> sucesso,
                      MutableLiveData<String> erro,
                      MutableLiveData<ClienteResponse> clienteData) {
        api.login(email, senha).enqueue(new Callback<ClienteResponse>() {
            @Override
            public void onResponse(Call<ClienteResponse> call, Response<ClienteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    clienteData.setValue(response.body());
                    sucesso.setValue(true);
                } else {
                    erro.setValue("Email ou senha incorretos! Codigo: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ClienteResponse> call, Throwable t) {
                erro.setValue("Falha: " + t.getMessage());
            }
        });
    }

    public void cadastrar(String nome, String cpf, String email, String senha, String telefone,
                          MutableLiveData<Boolean> sucesso,
                          MutableLiveData<String> erro) {
        api.cadastrar(nome, cpf, email, senha, telefone).enqueue(new Callback<ClienteResponse>() {
            @Override
            public void onResponse(Call<ClienteResponse> call, Response<ClienteResponse> response) {
                if (response.isSuccessful()) {
                    sucesso.setValue(true);
                } else {
                    erro.setValue("Erro ao cadastrar! Codigo: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ClienteResponse> call, Throwable t) {
                erro.setValue("Falha: " + t.getMessage());
            }
        });
    }
}