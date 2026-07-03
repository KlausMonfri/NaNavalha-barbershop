package dao;

import model.Barbeiro;
import conection.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarbeiroDAO {

    public List<Barbeiro> listar() throws SQLException {
        List<Barbeiro> lista = new ArrayList<>();
        String sql = "SELECT * FROM barbeiro";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Barbeiro b = new Barbeiro(rs.getString("nome"), rs.getString("especialidade"));
                b.setId(rs.getInt("id"));
                lista.add(b);
            }
        }
        return lista;
    }

    public void inserir(Barbeiro barbeiro) throws SQLException {
        String sql = "INSERT INTO barbeiro (nome, especialidade) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barbeiro.getNome());
            ps.setString(2, barbeiro.getEspecialidade());
            ps.executeUpdate();
        }
    }

    public void atualizar(Barbeiro barbeiro) throws SQLException {
        String sql = "UPDATE barbeiro SET nome = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barbeiro.getNome());
            ps.setString(2, barbeiro.getEspecialidade());
            ps.setInt(3, barbeiro.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM barbeiro WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}