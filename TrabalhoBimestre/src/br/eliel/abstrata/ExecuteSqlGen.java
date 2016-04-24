package br.eliel.abstrata;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import br.eliel.anotacoes.Coluna;
import br.eliel.anotacoes.Tabela;

public abstract class ExecuteSqlGen {
	public ExecuteSqlGen() {
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
			sb.append("CREATE TABLE ").append(nameTable).append(" (");

			Field[] attributes = cl.getDeclaredFields();
		}
	}
}