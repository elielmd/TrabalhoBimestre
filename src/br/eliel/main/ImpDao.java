package br.eliel.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.eliel.abstrata.Dao;

public class ImpDao implements Dao<Cliente, Integer> {
	
	private Connection con = null;
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	} 
	
	private ExecuteSqlGen ex = new ExecuteSqlGen();
    private List<Cliente> list = null;
	
	@Override
	public void salvar(Cliente c) {
		try {
			 ps = ex.getSqlInsert(con,c);
	         ps.executeUpdate();
	         ps.close();
	         System.out.println("TESTE");
			/*PreparedStatement ps = ex.getSqlInsert(con, c);
			ps.setInt(1, c.getId());
			ps.setString(2, c.getNome());
			ps.setString(3, c.getEndereco());
			ps.setString(4, c.getTelefone());
			ps.setInt(5, c.getEstadoCivil().ordinal());
			ps.executeUpdate();
			ps.close();*/

		} catch (SQLException e) {
			e.printStackTrace();
		}		
		System.out.println(ex);
	}

	@Override
	public Cliente buscar(Integer k) {
		// TODO Auto-generated method stub
		return null;
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
