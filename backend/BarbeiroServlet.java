package model;

public class Agendamento {
	private int id;
	private Cliente cliente;
	private Servico servico;
	private Barbeiro barbeiro;
	private String data;
	private String horario;

	
	public Agendamento(Cliente cliente, Servico servico, Barbeiro barbeiro, String data, String horario) {
		this.cliente = cliente;
		this.servico = servico;
		this.barbeiro = barbeiro;
		this.data = data;
		this.horario = horario;
	}
	
	
	public Agendamento(int id, Cliente cliente, Servico servico, Barbeiro barbeiro, String data, String horario) {
		this.id = id;
		this.cliente = cliente;
		this.servico = servico;
		this.barbeiro = barbeiro;
		this.data = data;
		this.horario = horario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Barbeiro getBarbeiro() {
		return barbeiro;
	}

	public void setBarbeiro(Barbeiro barbeiro) {
		this.barbeiro = barbeiro;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public String getData() {
		return data;
	}

	public String getHorario() {
		return horario;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public void exibirDados() {
		System.out.println("O cliente " + cliente.getNome() + " agendou um " + servico.getNome() + " no dia: " + data + " no horario: " + horario + " com o: " + barbeiro.getNome() + " no Preço: R$ " + servico.getPreco());
	}
}