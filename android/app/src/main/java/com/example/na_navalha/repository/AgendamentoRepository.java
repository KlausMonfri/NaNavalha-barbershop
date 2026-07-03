package com.example.na_navalha.repository;

import com.example.na_navalha.Constants;
import androidx.lifecycle.MutableLiveData;
import com.example.na_navalha.model.Agendamento;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public class AgendamentoRepository {

    private static final String BASE_URL = Constants.BASE_URL;

    public interface AgendamentoApi {
        @GET("agendamentos")
        Call<List<Agendamento>> listar(@Query("clienteId") int clienteId);

        @FormUrlEncoded
        @POST("agendamentos")
        Call<Void> inserir(
                @Field("clienteId") int clienteId,
                @Field("barbeiroId") int barbeiroId,
                @Field("servicoId") int servicoId,
                @Field("dataHora") String dataHora
        );

        @FormUrlEncoded
        @PUT("agendamentos")
        Call<Void> atualizar(
                @Field("id") int id,
                @Field("dataHora") String dataHora,
                @Field("status") String status
        );

        @DELETE("agendamentos")
        Call<Void> deletar(@Query("id") int id);
    }

    private AgendamentoApi api;

    public AgendamentoRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(AgendamentoApi.class);
    }

    public void listar(int clienteId, MutableLiveData<List<Agendamento>> liveData) {
        api.listar(clienteId).enqueue(new Callback<List<Agendamento>>() {
            @Override
            public void onResponse(Call<List<Agendamento>> call, Response<List<Agendamento>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Agendamento>> call, Throwable t) {
                liveData.setValue(null);
            }
        });
    }

    public void inserir(int clienteId, int barbeiroId, int servicoId, String dataHora,
                        MutableLiveData<Boolean> sucesso) {
        api.inserir(clienteId, barbeiroId, servicoId, dataHora).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (sucesso != null) sucesso.setValue(response.isSuccessful());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (sucesso != null) sucesso.setValue(false);
            }
        });
    }

    public void cancelar(int id, MutableLiveData<Boolean> sucesso) {
        api.atualizar(id, "2025-01-01 00:00:00", "Cancelado").enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sucesso.setValue(response.isSuccessful());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sucesso.setValue(false);
            }
        });
    }

    public void remarcar(int id, String novaDataHora, MutableLiveData<Boolean> sucesso) {
        api.atualizar(id, novaDataHora, "Agendado").enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sucesso.setValue(response.isSuccessful());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sucesso.setValue(false);
            }
        });
    }

    public void deletar(int id, MutableLiveData<Boolean> sucesso) {
        api.deletar(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sucesso.setValue(response.isSuccessful());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sucesso.setValue(false);
            }
        });
    }
}