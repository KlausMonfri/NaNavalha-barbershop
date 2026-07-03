package model;

public class Produto {
	private int id;
	private String nome;
	private double valor;
	private String tipo;
	private int quantidade;
	
	public Produto(String nome, double valor, String tipo, int quantidade) {
		this.nome = nome;
		this.valor = valor;
		this.tipo = tipo;
		this.quantidade = quantidade;
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
	
	public double getValor() {
		return valor;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setValor(double valor) {
		if (valor < 0) {
			System.out.println("O valor está inválido");
		} else {
			this.valor = valor;
		}
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean vender(int quantidadeSolicitada) {
	    if (quantidadeSolicitada > quantidade) {
	        System.out.println("Estoque insuficiente!");
	        return false;
	    } else {
	        quantidade -= quantidadeSolicitada;
	        System.out.println("Venda realizada com sucesso!");
	        return true;
	    }
	  }
	
	public void exibirDados() {
		System.out.println("\"Produto: \" + nome +\r\n"
				+ "                       \" | Tipo: \" + tipo +\r\n"
				+ "                       \" | Valor: R$ \" + valor +\r\n"
				+ "                       \" | Quantidade: \" + quantidade");
	}
	
	
	}
	
