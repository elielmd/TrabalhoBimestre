package br.eliel.anotacoesmain;

import br.eliel.anotacoes.Coluna;
import br.eliel.anotacoes.Tabela;

@Tabela("CAD_USUARIO")
public class Cliente {

	@Coluna(pk=true, nome="UsID", tamanho=-1)
	private int id;

	@Coluna(nome="UsNome", tamanho=70)
	private String nome;
	
	@Coluna(endereco="UsEnderecos", tamanho=100)
	private String endereco;
	
	@Coluna(telefone="UsTelefone", tamanho=20)
	private String telefone;

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

	public Cliente() {
		this(0, null);
	}

	public Cliente(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}
}