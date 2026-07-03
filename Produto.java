package dao;

import model.Produto;
import conection.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public List<Produto> listar() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto(
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("tipo"),
                    rs.getInt("quantidade")
                );
                p.setId(rs.getInt("id"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, tipo, quantidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getValor());
            ps.setString(3, produto.getTipo());
            ps.setInt(4, produto.getQuantidade());
            ps.executeUpdate();
        }
    }

    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, preco = ?, tipo = ?, quantidade = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getValor());
            ps.setString(3, produto.getTipo());
            ps.setInt(4, produto.getQuantidade());
            ps.setInt(5, produto.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}