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
                } else if (typeParemetros.equals(int.class)){
                    if (field.getAnnotation(Coluna.class).pk() == true) {
                        typeColumn = "INT NOT NULL";
                    } else {
                        typeColumn = "INT";
                    }
                } else if (typeParemetros.isEnum()) {
                    typeColumn = "INT";
                }

                if (i > 0) sb.append(",");

                sb.append("\n\t").append(nameColumn).append(" ").append(typeColumn);
			}
		}
	}
}
			