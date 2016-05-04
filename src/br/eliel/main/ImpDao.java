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
		exbusca.setInt(1,cli.getId());
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

	/*@Override
	public void excluir(Integer k) throws SQLException {
		PreparedStatement psexcluir = imp.getSqlDeleteById(imp.getCon(), k);

		Cliente c = (Cliente) k;

		int buscar = 0;

		try {
			psexcluir.setInt(1, c.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			buscar = psexcluir.executeUpdate();
			System.out.println("ID...........: " + c.getId());
			System.out.println("Nome........: " + c.getNome());
			System.out.println("Telefone....: " + c.getTelefone());
			System.out.println("Endereço....: " + c.getEndereco());
			System.out.println("Estado.Civil: " + c.getEstadoCivil());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}*/

	@Override
	public List<Cliente> listarTodos() throws SQLException {
		PreparedStatement ps = imp.getSqlSelectAll(imp.getCon(), new Cliente());

		List<Cliente> c = new ArrayList<Cliente>();

		ResultSet rs = null;

		try {
			rs = ps.executeQuery();
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

	@Override
	public void excluir(Cliente id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/*
	 * public void criarTabela(Cliente t) throws SQLException { ExecuteSqlGen ex
	 * = new ExecuteSqlGen();
	 * 
	 * try { String csql = ex.getCreateTable(con, t); PreparedStatement ps =
	 * con.prepareStatement(csql); ps.executeUpdate(); ps.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace();
	 * 
	 * }
	 * 
	 * }
	 * 
	 * @Override public void salvar(Cliente t) throws SQLException {
	 * ExecuteSqlGen ex = new ExecuteSqlGen();
	 * 
	 * try { PreparedStatement pst = ex.getSqlInsert(con, t); pst.setInt(1,
	 * t.getId()); pst.setString(2, t.getNome()); pst.setString(3,
	 * t.getEndereco()); pst.setString(4, t.getTelefone()); pst.setInt(5,
	 * t.getEstadoCivil().ordinal());
	 * 
	 * pst.executeUpdate(); pst.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } }
	 * 
	 * @Override public Cliente buscar(Integer k) throws SQLException {
	 * ExecuteSqlGen ex = new ExecuteSqlGen(); Cliente c = new Cliente();
	 * 
	 * try { PreparedStatement ps = ex.getSqlSelectById(con, new Cliente());
	 * ps.setInt(1, k); ResultSet rs = ps.executeQuery();
	 * 
	 * while (rs.next()) { c.setId(rs.getInt("UsID"));
	 * c.setNome(rs.getString("UsNome"));
	 * c.setEndereco(rs.getString("UsEndereco"));
	 * c.setTelefone(rs.getString("UsTelefone"));
	 * c.setEstadoCivil(EstadoCivil.getOpcao(rs.getInt("UsEstadoCivil"))); }
	 * 
	 * ps.close(); rs.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * return c; }
	 * 
	 * @Override public void atualizar(Cliente t) throws SQLException {
	 * ExecuteSqlGen ex = new ExecuteSqlGen();
	 * 
	 * try { PreparedStatement ps = ex.getSqlUpdateById(con, t); ps.setInt(5,
	 * t.getId()); ps.setString(1, t.getNome()); ps.setString(2,
	 * t.getEndereco()); ps.setString(3, t.getTelefone()); ps.setInt(4,
	 * t.getEstadoCivil().ordinal()); ps.executeUpdate(); ps.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } }
	 * 
	 * @Override public void excluir(Integer pk) throws SQLException {
	 * ExecuteSqlGen ex = new ExecuteSqlGen();
	 * 
	 * try { PreparedStatement ps = ex.getSqlDeleteById(con, new Cliente());
	 * ps.setInt(1, pk); ps.executeUpdate(); ps.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } }
	 * 
	 * @Override public List<Cliente> listarTodos() throws SQLException {
	 * ExecuteSqlGen ex = new ExecuteSqlGen(); List<Cliente> Cliente = new
	 * ArrayList<Cliente>();
	 * 
	 * try { PreparedStatement ps = ex.getSqlSelectAll(con, new Cliente());
	 * ResultSet rs = ps.executeQuery();
	 * 
	 * while (rs.next()) {
	 * 
	 * Cliente c = new Cliente(); c.setId(rs.getInt("UsID"));
	 * c.setNome(rs.getString("UsNome"));
	 * c.setEndereco(rs.getString("UsEndereco"));
	 * c.setTelefone(rs.getString("UsTelefone"));
	 * c.setEstadoCivil(EstadoCivil.getOpcao(rs.getInt("UsEstadoCivil")));
	 * 
	 * Cliente.add(c); }
	 * 
	 * ps.close(); rs.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * return Cliente; }
	 */
}
