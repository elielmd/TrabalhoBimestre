package br.eliel.abstrata;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class MetodosSqlGen {

		public ExecuteMetodos() {
		}

		protected abstract String getCreateTable(Connection con, Object obj) {	
		}
		
		protected abstract String getDropTable(Connection con, Object obj) {
		}

		protected abstract PreparedStatement getSqlInset(Connection con, Object obj) {	
		}
		
		protected abstract PreparedStatement getSelectAll(Connection con, Object obj) {
		}

		public static void main(String[] args) {

			new ExecuteMetodos();

		}
	}