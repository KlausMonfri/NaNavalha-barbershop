package dao;

import model.Agendamento;
import model.Barbeiro;
import model.Cliente;
import model.Servico;
import conection.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public List<Agendamento> listar() throws SQLException {
        return listarPorCliente(0);
    }

    public List<Agendamento> listarPorCliente(int clienteId) throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.data_hora, a.status, " +
                     "c.id AS cli_id, c.nome AS cli_nome, c.cpf, c.email, " +
                     "b.id AS barb_id, b.nome AS barb_nome, b.especialidade, " +
                     "s.id AS serv_id, s.nome AS serv_nome, s.preco " +
                     "FROM agendamento a " +
                     "JOIN cliente c ON a.cliente_id = c.id " +
                     "JOIN barbeiro b ON a.barbeiro_id = b.id " +
                     "JOIN servico s ON a.servico_id = s.id";

        if (clienteId > 0) {
            sql += " WHERE a.cliente_id = " + clienteId;
        }

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getString("cli_nome"), rs.getString("cpf"), rs.getString("email"));
                cliente.setId(rs.getInt("cli_id"));

                Barbeiro barbeiro = new Barbeiro(rs.getString("barb_nome"), rs.getString("especialidade"));
                barbeiro.setId(rs.getInt("barb_id"));

                Servico servico = new Servico(rs.getString("serv_nome"), rs.getDouble("preco"));
                servico.setId(rs.getInt("serv_id"));

                Agendamento ag = new Agendamento(
                    rs.getInt("id"),
                    cliente,
                    servico,
                    barbeiro,
                    rs.getString("data_hora"),
                    rs.getString("status")
                );
                lista.add(ag);
            }
        }
        return lista;
    }

    public void inserir(int clienteId, int barbeiroId, int servicoId, String dataHora) throws SQLException {
        String sql = "INSERT INTO agendamento (cliente_id, barbeiro_id, servico_id, data_hora) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            ps.setInt(2, barbeiroId);
            ps.setInt(3, servicoId);
            ps.setString(4, dataHora);
            ps.executeUpdate();
        }
    }

    public void atualizar(int id, String novaDataHora, String status) throws SQLException {
        String sql = "UPDATE agendamento SET data_hora = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novaDataHora);
            ps.setString(2, status);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM agendamento WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}