package br.eliel.abstrata;

import java.sql.SQLException;
import java.util.List;

import br.eliel.main.Cliente;

public interface Dao<Cliente, id> {

	public void salvar(Cliente t) throws SQLException;

	public Cliente buscar(Cliente k) throws SQLException;

	public void atualizar(Cliente t) throws SQLException;

	public void excluir(Cliente k) throws SQLException;

	public List<Cliente> listarTodos() throws SQLException;

}