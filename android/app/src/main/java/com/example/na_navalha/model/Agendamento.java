package com.example.na_navalha.model;

public class Agendamento {
    private int id;
    private String data;
    private String horario;
    private Cliente cliente;
    private Barbeiro barbeiro;
    private Servico servico;

    public int getId() { return id; }
    public String getData() { return data; }
    public String getHorario() { return horario; }
    public Cliente getCliente() { return cliente; }
    public Barbeiro getBarbeiro() { return barbeiro; }
    public Servico getServico() { return servico; }

    public static class Cliente {
        private int id;
        private String nome;
        private String email;
        private String cpf;
        private String senha;
        private String telefone;

        public int getId() { return id; }
        public String getNome() { return nome; }
        public String getEmail() { return email; }
        public String getCpf() { return cpf; }
        public String getSenha() { return senha; }
        public String getTelefone() { return telefone; }
    }

    public static class Barbeiro {
        private int id;
        private String nome;
        private String especialidade;

        public int getId() { return id; }
        public String getNome() { return nome; }
        public String getEspecialidade() { return especialidade; }
    }

    public static class Servico {
        private int id;
        private String nome;
        private double preco;

        public int getId() { return id; }
        public String getNome() { return nome; }
        public double getPreco() { return preco; }
    }
}