package br.eliel.main;

import java.sql.SQLException;

import br.eliel.enums.EstadoCivil;

public class Main extends ImpDao {

	public Main() throws SQLException {
		//inserindo cliente
		Cliente cliente1 = new Cliente(1, "Luke Skywalker", "7777-7777", "Polis Massa", EstadoCivil.SOLTEIRO);
		//chamar métodos
		apagarTabela(cliente1);
		criarTabela(cliente1);
		salvar(cliente1);

		Cliente cliente2 = new Cliente(2, "Chewbacca", "8888-8888", "Kashyyyk", EstadoCivil.SOLTEIRO);
		salvar(cliente2);

		Cliente cliente3 = new Cliente(3, "Han Solo", "9999-9999", "Corellia", EstadoCivil.GAMEOVER);
		salvar(cliente3);

		listarTodos();

		buscar(cliente2);

		cliente2.setNome("NEW NOME 1");
		cliente2.setTelefone("NEW TELEFONE 1");
		cliente2.setEndereco("NEW ENDERECO 1");
		cliente2.setEstadoCivil(EstadoCivil.VIUVO);
		atualizar(cliente2);

		excluir(cliente2);

		listarTodos();

	}

	public static void main(String[] args) throws SQLException {
		new Main();
	}

}
