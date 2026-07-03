package model;

public class Barbeiro {
	private int id;
	private String nome;
	private String especialidade;
	
	public Barbeiro(String nome, String especialidade) {
		this.nome = nome;
		this.especialidade = especialidade;
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
	
	public String getEspecialidade() {
		return especialidade;
	}
	
	public void setNome(String nome) {
		if(nome == null || nome.isEmpty()) {
			System.out.println("Esse funcionario não existe!");
		} else {
			this.nome = nome;
		}
	}
	
	public void setEspecialidade(String especialidade) {
		if( especialidade == null || especialidade.isEmpty()) {
			System.out.println("Essa especialidade não existe!");
		} else {
			this.especialidade = especialidade;
		}
	}
	
	public void exibirDados() {
		System.out.println("O barbeiro: " + nome + " é especialista em: " + especialidade);
	}
}
