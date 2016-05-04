package br.eliel.abstrata;

import java.sql.SQLException;
import java.util.List;

import br.eliel.main.Cliente;

public interface Dao<T, K> {

	public void salvar(T t) throws SQLException;

	public T buscar(K k) throws SQLException;

	public void atualizar(T t) throws SQLException;

	public void excluir(K k) throws SQLException;

	public List<T> listarTodos() throws SQLException;

}