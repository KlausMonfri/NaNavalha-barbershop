package conection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	public static Connection conectar() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/barber";
			String usuario = "root";
			String senha = "Gika";
			
			conn = DriverManager.getConnection(url, usuario, senha);
		} catch (Exception e) {
			
			System.out.println("Erro na conexão");
			e.printStackTrace();
		}
		
		return conn;
	}

}
