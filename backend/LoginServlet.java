package model;

public class Servico {
	private int id;
	private String nome;
	private double preco;
	
	public Servico(String nome, double preco) {
		this.nome= nome;
		this.preco = preco;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public double getPreco() {
		return preco;
	}
	
	public void setNome( String nome) {
		if( nome == null || nome.isEmpty()) {
			System.out.println("Nome de serviço invalido");
		} else {
			this.nome = nome;
		}
	}
	
	public void setPreco( double preco ) {
		if (preco < 0) {
			System.out.println("Preço invalido");
	}else {
		this.preco = preco;}
	}
	
	
	public void exibirDados() {
		System.out.println("O serviço "+ nome + "custa: R$"+ preco);
	}
}
