package br.eliel.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.eliel.abstrata.Dao;
import br.eliel.enums.EstadoCivil;

public class ImpDao implements Dao<Cliente, Integer> {
	
	ExecuteSqlGen ex = new ExecuteSqlGen();
	private Connection con = null;
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	} 

	@Override
	public void salvar(Cliente t) {
		try {
			PreparedStatement ps = ex.getSqlInsert(con, t);
			ps.setInt(1, t.getId());
			ps.setString(2, t.getNome());
			ps.setString(3, t.getEndereco());
			ps.setString(4, t.getTelefone());
			ps.setInt(5, t.getEstadoCivil().ordinal());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public Cliente buscar(Integer k) {
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
				c.EstadoCivil(EstadoCivil.values()[rs.getInt("UsEstadoCivil")]);
			}			
			
			ps.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		return c;
	}

	@Override
	public void atualizar(Cliente t) {
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
	public void excluir(Integer pk) {
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
	public List<Cliente> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
}
