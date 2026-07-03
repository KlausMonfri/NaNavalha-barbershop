package conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConection {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/barber";
		String usuario = "root";
		String senha = "Gika";
		
		try {
			Connection conn = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conexão realizada com sucesso!");
		}
		
		catch (SQLException e) {
			System.out.println("Erro na conexão!");
			e.printStackTrace();
		}
	}
}
