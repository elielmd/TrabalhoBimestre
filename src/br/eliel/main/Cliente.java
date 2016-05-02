package br.eliel.main;

import br.eliel.anotacoes.Coluna;
import br.eliel.anotacoes.Tabela;
import br.eliel.enums.EstadoCivil;

@Tabela("CAD_USUARIO")
public class Cliente {

	@Coluna(pk = true, nome = "UsID", tamanho = -1)
	private int id;

	@Coluna(nome = "UsNome", tamanho = 100)
	private String nome;

	@Coluna(nome = "UsEndereco", tamanho = 255)
	private String endereco;

	@Coluna(nome = "UsTelefone", tamanho = 20)
	private String telefone;

	@Coluna(nome = "UsEstadoCivil", tamanho = 100)
	private EstadoCivil estadoCivil;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Cliente() {
		this(0, null, null, null, null);
	}

	public Cliente(int id, String nome, String endereco, String telefone, EstadoCivil estadoCivil) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.estadoCivil = estadoCivil;
	}
}