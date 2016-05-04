package br.eliel.main;

import java.sql.Connection;
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
		ExecuteSqlGen ex = new ExecuteSqlGen();
		String exsql = getImp().getDropTable(getImp().getCon(), t);

		try (PreparedStatement ps = getImp().getCon().prepareStatement(exsql)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Tabela nao encontrada!");
			// e.printStackTrace();

		}

	}

	@Override
	public void salvar(Cliente t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente buscar(Integer k) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizar(Cliente t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Integer k) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cliente> listarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*public void criarTabela(Cliente t) throws SQLException {
		ExecuteSqlGen ex = new ExecuteSqlGen();

		try {
			String csql = ex.getCreateTable(con, t);
			PreparedStatement ps = con.prepareStatement(csql);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	@Override
	public void salvar(Cliente t) throws SQLException {
		ExecuteSqlGen ex = new ExecuteSqlGen();

		try {
			PreparedStatement pst = ex.getSqlInsert(con, t);
			pst.setInt(1, t.getId());
			pst.setString(2, t.getNome());
			pst.setString(3, t.getEndereco());
			pst.setString(4, t.getTelefone());
			pst.setInt(5, t.getEstadoCivil().ordinal());

			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Cliente buscar(Integer k) throws SQLException {
		ExecuteSqlGen ex = new ExecuteSqlGen();
		Cliente c = new Cliente();

		try {
			PreparedStatement ps = ex.getSqlSelectById(con, new Cliente());
			ps.setInt(1, k);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				c.setId(rs.getInt("UsID"));
				c.setNome(rs.getString("UsNome"));
				c.setEndereco(rs.getString("UsEndereco"));
				c.setTelefone(rs.getString("UsTelefone"));
				c.setEstadoCivil(EstadoCivil.getOpcao(rs.getInt("UsEstadoCivil")));
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

	@Override
	public void atualizar(Cliente t) throws SQLException {
		ExecuteSqlGen ex = new ExecuteSqlGen();

		try {
			PreparedStatement ps = ex.getSqlUpdateById(con, t);
			ps.setInt(5, t.getId());
			ps.setString(1, t.getNome());
			ps.setString(2, t.getEndereco());
			ps.setString(3, t.getTelefone());
			ps.setInt(4, t.getEstadoCivil().ordinal());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void excluir(Integer pk) throws SQLException {
		ExecuteSqlGen ex = new ExecuteSqlGen();

		try {
			PreparedStatement ps = ex.getSqlDeleteById(con, new Cliente());
			ps.setInt(1, pk);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> listarTodos() throws SQLException {
		ExecuteSqlGen ex = new ExecuteSqlGen();
		List<Cliente> Cliente = new ArrayList<Cliente>();

		try {
			PreparedStatement ps = ex.getSqlSelectAll(con, new Cliente());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Cliente c = new Cliente();
				c.setId(rs.getInt("UsID"));
				c.setNome(rs.getString("UsNome"));
				c.setEndereco(rs.getString("UsEndereco"));
				c.setTelefone(rs.getString("UsTelefone"));
				c.setEstadoCivil(EstadoCivil.getOpcao(rs.getInt("UsEstadoCivil")));

				Cliente.add(c);
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Cliente;
	}*/
}
