package dao;

import model.Servico;
import conection.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public List<Servico> listar() throws SQLException {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Servico s = new Servico(rs.getString("nome"), rs.getDouble("preco"));
                s.setId(rs.getInt("id"));
                lista.add(s);
            }
        }
        return lista;
    }

    public void inserir(Servico servico) throws SQLException {
        String sql = "INSERT INTO servico (nome, preco) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, servico.getNome());
            ps.setDouble(2, servico.getPreco());
            ps.executeUpdate();
        }
    }

    public void atualizar(Servico servico) throws SQLException {
        String sql = "UPDATE servico SET nome = ?, preco = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, servico.getNome());
            ps.setDouble(2, servico.getPreco());
            ps.setInt(3, servico.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM servico WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}