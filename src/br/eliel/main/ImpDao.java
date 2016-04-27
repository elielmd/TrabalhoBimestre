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
	public void salvar(Cliente cliente) {
		try {
			PreparedStatement ps = ex.getSqlInsert(con, cliente);
			ps.setInt(1, cliente.getId());
			ps.setString(2, cliente.getNome());
			ps.setString(3, cliente.getEndereco());
			ps.setString(4, cliente.getTelefone());
			ps.setInt(5, cliente.getEstadoCivil().ordinal());
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
				c.setEndereco(rs.getString("UsEnderecos"));
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
	public void atualizar(Cliente t) {
		// TODO Auto-generated method stub
	}

	@Override
	public void excluir(Integer k) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Cliente> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
}
