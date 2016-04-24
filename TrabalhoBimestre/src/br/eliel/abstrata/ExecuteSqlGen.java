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
	
	public ExecuteSqlGen() throws SQLException {
		
		Cliente cliente = new Cliente(1, "Eliel", "batata", "33333", EstadoCivil.GAMEOVER);
		String strCreateTable = getCreateTable(cliente);
		
		/*abrirConexao();
		fecharConexao();*/
	}
		
	private void abrirConexao() throws SQLException {
		String url = "jdbc:h2:D:/banco/banco";
		String user = "sa";
		String pass = "sa";
		con = DriverManager.getConnection(url, user, pass);
		System.err.println("a");
		
	}

	private void fecharConexao() throws SQLException {
		con.close();
	}

	protected String getCreateTable(Connection con, Object obj) {
		try {
			String nameTable;
			Class<?> cl = obj.getClass();

			StringBuilder sb = new StringBuilder();

			if (cl.isAnnotationPresent(Tabela.class)) {
				Tabela annotationTable = cl.getAnnotation(Tabela.class);
				nameTable = annotationTable.value();
			} else {
				nameTable = cl.getSimpleName().toUpperCase();
			}
			System.out.println(nameTable);

			sb.append("CREATE TABLE ").append(nameTable).append(" (");

			Field[] attributes = cl.getDeclaredFields();

			for (int i = 0; i < attributes.length; i++) {
				Field field = attributes[i];

				String nameColumn;
				String typeColumn = null;

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna annotationColumn = field.getAnnotation(Coluna.class);

					if (annotationColumn.nome().isEmpty()) {
						nameColumn = field.getName().toUpperCase();
					} else {
						nameColumn = annotationColumn.nome();
					}
				} else {
					nameColumn = field.getName().toUpperCase();
				}

				Class<?> typeParemetros = field.getType();

				if (typeParemetros.equals(String.class)) {
					if (field.getAnnotation(Coluna.class).tamanho() > -1) {
						typeColumn = "VARCHAR(" + field.getAnnotation(Coluna.class).tamanho() + ")";
					} else {
						typeColumn = "VARCHAR(100)";
					}
				} else if (typeParemetros.equals(int.class)) {
					if (field.getAnnotation(Coluna.class).pk() == true) {
						typeColumn = "INT NOT NULL";
					} else {
						typeColumn = "INT";
					}
				} else if (typeParemetros.isEnum()) {
					typeColumn = "INT";
				}

				if (i > 0)
					sb.append(",");

				sb.append("\n\t").append(nameColumn).append(" ").append(typeColumn);

			}

			sb.append(",\n\tPRIMARY KEY(");
			for (int y = 0; y < attributes.length; y++) {
				int get = 0;
				Field fields = attributes[y];

				if (fields.isAnnotationPresent(Coluna.class)) {
					Coluna annotationColumn = fields.getAnnotation(Coluna.class);

					if (annotationColumn.pk()) {
						if (get > 0)
							sb.append(", ");

						if (annotationColumn.nome().isEmpty()) {
							sb.append(fields.getName().toUpperCase());
						} else {
							sb.append(annotationColumn.nome());
						}
						get++;
					}
				}
				if (y == attributes.length - 1) {
					sb.append(")");
				}
			}
			sb.append("\n);");

			String create = sb.toString();
			Statement execute = con.createStatement();
			execute.executeUpdate(create);

			return create;

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
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
		try {
				new ExecuteSqlGen();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}