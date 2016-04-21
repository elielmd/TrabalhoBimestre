package br.eliel.anotacoesmain;

import br.eliel.anotacoes.Coluna;
import br.eliel.anotacoes.Tabela;

@Tabela("CAD_USUARIO")
public class Cliente {

	@Coluna(pk=true)
	private int id;

	@Coluna(nome="USNOME")
	private String nome;
	
	@Coluna(endereco="USENDERECO")
	private String endereco;
	
	@Coluna(telefone="USTELEFONE")
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