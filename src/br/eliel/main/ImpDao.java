package br.eliel.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.eliel.abstrata.Dao;
import br.eliel.enums.EstadoCivil;

public class ImpDao implements Dao<Cliente, Integer> {

	private ExecuteSqlGen imp;

	private ExecuteSqlGen getImp() {
		if (imp == null) {
			try {
				setImp(new ExecuteSqlGen());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return imp;
	}

	private void setImp(ExecuteSqlGen imp) {
		this.imp = imp;
	}

	public void apagarTabela(Cliente t) throws SQLException {
		//seta conexão
		String exsql = getImp().getDropTable(getImp().getCon(), t);
		System.out.println("COMANDO DROP");
		System.out.println("________________________________________________________");
		System.out.println(exsql);
		try (PreparedStatement ps = getImp().getCon().prepareStatement(exsql)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Tabela nao encontrada!");
			// e.printStackTrace();
		}
	}

	public void criarTabela(Cliente t) throws SQLException {
		String exsql = getImp().getCreateTable(getImp().getCon(), t);
		System.out.println("\nCOMANDO CREATE");
		System.out.println("________________________________________________________");
		System.out.println(exsql);
		try (PreparedStatement ps = getImp().getCon().prepareStatement(exsql)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void salvar(Cliente t) throws SQLException {
		PreparedStatement add = getImp().getSqlInsert(getImp().getCon(), t);

		Cliente c = (Cliente) t;
		System.out.println("\nCOMANDO INSERT");
		System.out.println("________________________________________________________");
		try {
			add.setInt(1, c.getId());
			add.setString(2, c.getNome());
			add.setString(3, c.getTelefone());
			add.setString(4, c.getEndereco());
			add.setInt(5, c.getEstadoCivil().ordinal());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(add);
		System.out.println("Codigo ID...: " + c.getId());
		System.out.println("Nome Cliente: " + c.getNome());
		System.out.println("Num.Telefone: " + c.getTelefone());
		System.out.println("Endereco....: " + c.getEndereco());
		System.out.println("Estado.Civil: " + c.getEstadoCivil());

		try {
			add.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Cliente buscar(Cliente id) throws SQLException {
		PreparedStatement exbusca = imp.getSqlSelectById(imp.getCon(), id);

		ResultSet exresult;
		Cliente cli = (Cliente) id;
		exbusca.setInt(1, cli.getId());
		System.out.println("\nCOMANDO SELECT BY ID");
		System.out.println("________________________________________________________");
		try {
			exresult = exbusca.executeQuery();
			while (exresult.next()) {
				System.out.println(exbusca);
				System.out.println("Codigo ID...: " + exresult.getInt("UsID"));
				System.out.println("Nome Cliente: " + exresult.getString("UsNome"));
				System.out.println("Num.Telefone: " + exresult.getString("UsTelefone"));
				System.out.println("Endereco....: " + exresult.getString("UsEndereco"));
				System.out.println("Estado.Civil: " + EstadoCivil.values()[exresult.getInt("UsEstadoCivil")]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void atualizar(Cliente t) throws SQLException {
		PreparedStatement exalt = imp.getSqlUpdateById(imp.getCon(), t);

		Cliente c = (Cliente) t;

		int exibir = 0;

		try {
			exalt.setInt(5, c.getId());
			exalt.setString(1, c.getNome());
			exalt.setString(3, c.getTelefone());
			exalt.setString(2, c.getEndereco());
			exalt.setInt(4, c.getEstadoCivil().ordinal());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			exibir = exalt.executeUpdate();
			System.out.println("ID..........: " + c.getId());
			System.out.println("Telefone....: " + c.getTelefone());
			System.out.println("Endereço....: " + c.getEndereco());
			System.out.println("Estado.Civil: " + c.getEstadoCivil());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void excluir(Cliente id) throws SQLException {
		PreparedStatement psexcluir = imp.getSqlDeleteById(imp.getCon(), id);

		Cliente c = (Cliente) id;

		int buscar = 0;
		System.out.println("\nCOMANDO DELETE");
		System.out.println("_________________________________________________________");

		try {
			psexcluir.setInt(1, c.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			buscar = psexcluir.executeUpdate();
			System.out.println(psexcluir);
			System.out.println("ID...........: " + c.getId());
			System.out.println("Nome........: " + c.getNome());
			System.out.println("Telefone....: " + c.getTelefone());
			System.out.println("Endereço....: " + c.getEndereco());
			System.out.println("Estado.Civil: " + c.getEstadoCivil());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Cliente> listarTodos() throws SQLException {
		PreparedStatement ps = imp.getSqlSelectAll(imp.getCon(), new Cliente());

		List<Cliente> c = new ArrayList<Cliente>();

		ResultSet rs = null;
		System.out.println("\nCOMANDO SELECT ALL");
		System.out.println("_________________________________________________________");

		try {
			rs = ps.executeQuery();
			System.out.println(ps);
			while (rs.next()) {
				Cliente cli = new Cliente();
				cli.setId(rs.getInt("UsID"));
				cli.setNome(rs.getString("UsNome"));
				cli.setTelefone(rs.getString("UsTelefone"));
				cli.setEndereco(rs.getString("UsEndereco"));
				cli.setEstadoCivil(EstadoCivil.values()[rs.getInt("UsEstadoCivil")]);
				c.add(cli);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		for (Cliente cliente : c) {
			System.out.println("Codigo ID...: " + cliente.getId());
			System.out.println("Nome Cliente: " + cliente.getNome());
			System.out.println("Num.Telefone: " + cliente.getTelefone());
			System.out.println("Endereco....: " + cliente.getEndereco());
			System.out.println("Estado.Civil: " + cliente.getEstadoCivil());
		}

		return c;
	}
}
