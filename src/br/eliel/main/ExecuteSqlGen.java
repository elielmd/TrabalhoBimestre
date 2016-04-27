package br.eliel.main;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.eliel.abstrata.SqlGen;
import br.eliel.anotacoes.Coluna;
import br.eliel.anotacoes.Tabela;
import br.eliel.enums.EstadoCivil;

public class ExecuteSqlGen extends SqlGen {
	
	private Connection con = null;
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ExecuteSqlGen() {
		
		Cliente cliente = new Cliente(1, "Eliel", "batata", "33333", EstadoCivil.GAMEOVER);
	    try(PreparedStatement ps = con.prepareStatement(getCreateTable(con, cliente))){System.out.println(ps);ps.executeUpdate();}	
	    //try(PreparedStatement ps = con.prepareStatement(getDropTable(con, cliente))){ps.executeUpdate();};
		//System.out.println(strCreateTable);
		/*String strDropTable = getDropTable(con, cliente);
		System.out.println(strDropTable);
		/*PreparedStatement t = getSqlInsert(con, cliente); 
		t.setInt(1, 5);
		t.setString(2, cliente.getNome()); 
		t.setString(3, cliente.getEndereco()); 
		t.setString(4, cliente.getTelefone());
		t.setInt(5, cliente.getEstadoCivil().ordinal());
		t.executeUpdate();
		System.out.println(t);
		
		PreparedStatement t1 = getSqlSelectAll(con, cliente);
		t1.executeQuery();
		System.out.println(t1);
	    
		PreparedStatement t2 = getSqlSelectById(con, cliente);
		t2.setInt(1, 5);
		t2.executeQuery();
		System.out.println(t2);
		
		PreparedStatement t3 = getSqlUpdateById(con, cliente);
		t3.setInt(1, 5);
		t3.setString(2, cliente.getNome()); 
		t3.setString(3, cliente.getEndereco()); 
		t3.setString(4, cliente.getTelefone());
		t3.setInt(5, cliente.getEstadoCivil().ordinal());
		t3.executeUpdate();
		System.out.println(t3);
		
		PreparedStatement t4 = getSqlDeleteById(con, cliente);
		t4.setInt(1, 5);
		t4.executeUpdate();
		System.out.println(t4);
		
		try {
			abrirConexao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
   
    /*
	private void abrirConexao() throws SQLException {
		String url = "jdbc:h2:D:/banco/trabalhosql";
		String user = "sa";
		String pass = "sa";
		con = DriverManager.getConnection(url, user, pass);
	}

	private void fecharConexao() throws SQLException {
		con.close();
	} */ catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	protected String getCreateTable(Connection con, Object obj) {
		try {
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
			System.out.println(sb.toString());
			return sb.toString();

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getDropTable(Connection con, Object obj) {
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

			// Statement exeT = con.createStatement();
			// exeT.executeUpdate(sb.toString());

			return sb.toString();
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {
		Class<?> cl = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nomeTabela;

        if (cl.isAnnotationPresent(Tabela.class)) {
            Tabela table = cl.getAnnotation(Tabela.class);
            nomeTabela = table.value();
        } else {
        	nomeTabela = cl.getSimpleName().toUpperCase();
        }

        sb.append("INSERT INTO ").append(nomeTabela).append(" (");

        Field[] atributos = cl.getDeclaredFields();

        for (int i = 0; i < atributos.length; i++) {
            Field field = atributos[i];
            String nomeColuna;

            if (field.isAnnotationPresent(Coluna.class)) {
            	Coluna column = field.getAnnotation(Coluna.class);
                if (column.nome().isEmpty()) {
                	nomeColuna = field.getName().toUpperCase();
                } else {
                	nomeColuna = column.nome();
                }
            } else {
            	nomeColuna = field.getName().toUpperCase();
            }

            if (i > 0) {
                sb.append(", ");
            }

            sb.append(nomeColuna);
        }

        sb.append(") VALUES (");

        for (int i = 0; i < atributos.length; i++) {
            if (i > 0) sb.append(", ");

            sb.append("?");
        }
        sb.append(");");
        String add = sb.toString();
        //System.out.println(add);

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(add);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 
        return ps;
	}

	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
		Class<?> cl = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nomeTabela;
        if (cl.isAnnotationPresent(Tabela.class)) {
            nomeTabela = cl.getAnnotation(Tabela.class).value();
        } else {
            nomeTabela = cl.getSimpleName().toUpperCase();
        }
        sb.append("SELECT * FROM ").append(nomeTabela).append(";");
        String selectFrom = sb.toString();
        //System.out.println(selectFrom);
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(selectFrom);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj) {
		PreparedStatement ps = null;
		try{
			StringBuilder sb = new StringBuilder();
			String nomeTabela;
			if (obj.getClass().isAnnotationPresent(Tabela.class)) {
				Tabela anTabela = obj.getClass().getAnnotation(Tabela.class);
				nomeTabela = anTabela.value();
			} else {
				nomeTabela = obj.getClass().getSimpleName().toUpperCase();
			}			
			Field[] atributos = obj.getClass().getDeclaredFields();			
			String pk = "";
			for (int i = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anColuna = field.getAnnotation(Coluna.class);
					
					if(anColuna.pk()){
						if (pk.equalsIgnoreCase("")){
							pk = anColuna.nome();
						}else{
							pk = pk + ", " + anColuna.nome();
						}							
					}
				}
			}
			sb.append("SELECT * FROM ").append(nomeTabela).append(" WHERE ").append(pk).append(" = ?");
			try {				
				ps =  con.prepareStatement(sb.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return ps;	
		}catch(SecurityException e){
			throw new RuntimeException(e);
		}	
	}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj) {
		Class<? extends Object> cl = obj.getClass();
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		String nomeTabela;
		if (cl.isAnnotationPresent(Tabela.class)) {
			Tabela anTabela = cl.getAnnotation(Tabela.class);
			nomeTabela = anTabela.value();
		} else {
			nomeTabela = cl.getSimpleName().toUpperCase();
		}
		sb.append("UPDATE ").append(nomeTabela).append(" SET \n");

		Field[] atributos = cl.getDeclaredFields();
		String pk = "";

		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			String nomeColuna;
			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna anColuna = field.getAnnotation(Coluna.class);
				if (anColuna.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				} else {
					nomeColuna = anColuna.nome();
				}
				if (anColuna.pk()) {
					pk = nomeColuna;
				}
			} else {
				nomeColuna = field.getName().toUpperCase();
			}
			if (nomeColuna != pk) {
				sb.append("  ").append(nomeColuna).append(" = ?");

				if (i < atributos.length - 1) {
					sb.append(", \n");
				}
			}
		}

		sb.append("\n WHERE \n  ").append(pk).append(" = ?");

		try {
			ps = con.prepareStatement(sb.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}

	@Override
	protected PreparedStatement getSqlDeleteById(Connection con, Object obj) {
		PreparedStatement ps = null;
		try{
			StringBuilder sb = new StringBuilder();
			String nomeTabela;
			if (obj.getClass().isAnnotationPresent(Tabela.class)) {
				Tabela anTabela = obj.getClass().getAnnotation(Tabela.class);
				nomeTabela = anTabela.value();
			} else {
				nomeTabela = obj.getClass().getSimpleName().toUpperCase();
			}
			
			Field[] atributos = obj.getClass().getDeclaredFields();			
			String pk = "";
			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];
				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anColuna = field.getAnnotation(Coluna.class);
					if(anColuna.pk()){
						if (pk.equalsIgnoreCase("")){
							pk = anColuna.nome();
						}else{
							pk = pk + ", " + anColuna.nome();
						}							
					}
				}
			}
			sb.append("DELETE FROM ").append(nomeTabela).append(" WHERE ").append(pk).append(" = ?");
			
			try {				
				ps =  con.prepareStatement(sb.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}catch(SecurityException e){
			throw new RuntimeException(e);
		}			
		return ps;	
	}

	public static void main(String[] args) throws SQLException {
		new ExecuteSqlGen();
	}

}