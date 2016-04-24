package br.eliel.abstrata;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.eliel.anotacoes.Coluna;
import br.eliel.anotacoes.Tabela;
import br.eliel.anotacoesmain.Cliente;
import br.eliel.enums.*;

public class ExecuteSqlGen extends SqlGen {
	private Connection con;

	public ExecuteSqlGen() {

		Cliente cliente = new Cliente(1, "Eliel", "batata", "33333", EstadoCivil.GAMEOVER);
		String strCreateTable = getCreateTable(cliente);
		System.out.println(strCreateTable);
		String strDropTable = getDropTable(cliente);
		System.out.println(strDropTable);

		/*
		 * try { abrirConexao(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } //fecharConexao();
		 */
	}

	private void abrirConexao() throws SQLException {
		String url = "jdbc:h2:D:/banco/banco";
		String user = "sa";
		String pass = "sa";
		con = DriverManager.getConnection(url, user, pass);
	}

	private void fecharConexao() throws SQLException {
		con.close();
	}

	protected String getCreateTable(/* Connection con, */ Object obj) {
		try {
			String nameTable;
			Class<?> cl = obj.getClass();

			StringBuilder sb = new StringBuilder();
			// Declaração da tabela.
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("CREATE TABLE ").append(nomeTabela).append(" (");

			Field[] atributos = cl.getDeclaredFields();

			// Declaração das colunas

			for (int i = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				String nomeColuna;
				String tipoColuna = null;

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = anotacaoColuna.nome();
					}

				} else {
					nomeColuna = field.getName().toUpperCase();
				}

				Class<?> tipoParametro = field.getType();

				if (tipoParametro.equals(String.class)) {
					tipoColuna = "VARCHAR(100)";

				} else if (tipoParametro.equals(int.class)) {
					tipoColuna = "INT";

				} else if (tipoParametro.equals(int.class)) {
					if (field.getAnnotation(Coluna.class).pk() == true) {
						tipoColuna = "INT NOT NULL";
					} else {
						tipoColuna = "INT";
					}
				} else if (tipoParametro.isEnum()) {
					tipoColuna = "INT";
				}

				if (i > 0)
					sb.append(",");

				sb.append("\n\t").append(nomeColuna).append(" ").append(tipoColuna);
			}

			sb.append(",\n\tPRIMARY KEY( ");
			for (int x = 0, achou = 0; x < atributos.length; x++) {
				Field fields = atributos[x];
				if (fields.isAnnotationPresent(Coluna.class)) {
					Coluna anotacaoColuna = fields.getAnnotation(Coluna.class);
					if (anotacaoColuna.pk()) {
						if (achou > 0) {
							sb.append(", ");
						}
						if (anotacaoColuna.nome().isEmpty()) {
							sb.append(fields.getName().toUpperCase());
						} else {
							sb.append(anotacaoColuna.nome());
						}
						achou++;
					}
				}
				if (x == atributos.length - 1) {
					sb.append(")");
				}	
			}
			sb.append("\n);");

			return sb.toString();

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getDropTable(/*Connection con,*/ Object obj) {
		 try {
	            String nomeTabela;
	            StringBuilder sb = new StringBuilder();

	            Class<?> cl = obj.getClass();

	            if (cl.isAnnotationPresent(Tabela.class)) {
	                Tabela t = cl.getAnnotation(Tabela.class);
	                nomeTabela = t.value();
	            } else {
	                nomeTabela = cl.getSimpleName().toUpperCase();
	            }

	            sb.append("DROP TABLE ").append(nomeTabela).append(";");

	           /*Statement exeT = con.createStatement();
	           exeT.executeUpdate(drop);*/
	            return sb.toString();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	            return null;
	        }
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlDeleteById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		new ExecuteSqlGen();
	}

}