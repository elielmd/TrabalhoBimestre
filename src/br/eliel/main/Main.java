package br.eliel.main;

import java.sql.SQLException;

import br.eliel.enums.EstadoCivil;

public class Main extends ImpDao{

	public Main() throws SQLException {
		
		Cliente cliente1 = new Cliente(1, "Luke Skywalker", "Polis Massa", "7777-7777", EstadoCivil.SOLTEIRO);
		apagarTabela(cliente1);
		criarTabela(cliente1);
		salvar(cliente1);
		
		Cliente cliente2 = new Cliente(2,"Chewbacca","8888-8888","Kashyyyk",EstadoCivil.SOLTEIRO);
		salvar(cliente2);
		
		Cliente cliente3 = new Cliente(3,"Han Solo","9999-9999","Corellia",EstadoCivil.GAMEOVER);
		salvar(cliente3);
		
		listarTodos();
		
		buscar(cliente1);

		/*Cliente usu1 = new Cliente();
		usu1.setId(1)
		usu1.setNome("Luke Skywalker");
		usu1.setTelefone("7777-7777");
		usu1.setEndereco("Polis Massa");
		usu1.setEstadoCivil(EstadoCivil.SOLTEIRO);

		Cliente usu2 = new Cliente();
		usu2.setId(2);
		usu2.setNome(Chewbacca);
		usu2.setTelefone("8888-8888");
		usu2.setEndereco("Kashyyyk");
		usu2.setEstadoCivil(EstadoCivil.SOLTEIRO);

		Cliente usu3 = new Cliente();
		usu3.setId(3);
		usu3.setNome("Han Solo");
		usu3.setTelefone("4599563232");
		usu3.setEndereco("Corellia");
		usu3.setEstadoCivil(EstadoCivil.GAMEOVER);

		
		ImpDao ex = new ImpDao();

		try {
			ex.setCon(con.abrirConexao());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("1 -> apagarTabela");
		System.out.println("*********************************");
		ex.apagarTabela(usu1);
		System.out.println("*********************************");

		System.out.println("\n2 -> criarTabela");
		System.out.println("*********************************");
		ex.criarTabela(usu1);
		System.out.println("*********************************");

		ex.setCon(null);
		
		try {
			con.fecharConexao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("\n3 -> inserir objeto 1");
		System.out.println("*********************************");
		ex.salvar(usu1);
		System.out.println("*********************************");

		System.out.println("\n4 - > inserir objeto 2\n");
		System.out.println("*********************************");
		ex.salvar(usu2);
		System.out.println("*********************************");

		System.out.println("\n5 - > inserir objeto 3\n");
		System.out.println("*********************************");
		ex.salvar(usu2);
		System.out.println("*********************************");

		System.out.println("\n6 -> listarTodos");
		System.out.println("*********************************");
		for (Cliente usu : ex.listarTodos()) {
			System.out.println("Id..........: " + usu.getId());
			System.out.println("Nome........: " + usu.getNome());
			System.out.println("Endereco....: " + usu.getEndereco());
			System.out.println("Telefone....: " + usu.getTelefone());
			System.out.println("Estado civil: " + usu.getEstadoCivil());
		}
		System.out.println("*********************************");

		System.out.println("\n7 -> buscar objeto 1");
		Cliente usu4 = new Cliente();
		System.out.println("*********************************");
		usu4 = ex.buscar(usu1.getId());
		System.out.println("Id..........: " + usu4.getId());
		System.out.println("Nome........: " + usu4.getNome());
		System.out.println("Endereco....: " + usu4.getEndereco());
		System.out.println("Telefone....: " + usu4.getTelefone());
		System.out.println("Estado civil: " + usu4.getEstadoCivil());
		System.out.println("*********************************");

		System.out.println("\n8 -> alterar objeto 2\n");
		usu2.setTelefone("6666-6666");
		System.out.println("*********************************");
		ex.atualizar(usu2);
		System.out.println("*********************************");

		System.out.println("\n9 -> excluir objeto 3\n");
		System.out.println("*********************************");
		ex.excluir(usu3.getId());
		System.out.println("*********************************");

		System.out.println("\n10 -> listarTodos");
		System.out.println("*********************************");
		for (Cliente c : ex.listarTodos()) {
			System.out.println("Id..........: " + c.getId());
			System.out.println("Nome........: " + c.getNome());
			System.out.println("Endereco....: " + c.getEndereco());
			System.out.println("Telefone....: " + c.getTelefone());
			System.out.println("Estado civil: " + c.getEstadoCivil());
		}
		System.out.println("*********************************");

		ex.setCon(null);
		try {
			con.fecharConexao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public static void main(String[] args) throws SQLException {
		new Main();
	}

}
