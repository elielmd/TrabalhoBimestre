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