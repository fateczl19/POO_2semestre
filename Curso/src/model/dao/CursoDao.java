package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.connection.Database;
import model.vo.Curso;

public class CursoDao {
	private final String INSERT = "INSERT INTO CURSO (NOME, DESCRICAO, ATIVO, HORARIO_INICIO, HORARIO_TERMINO) VALUES (?,?,?,?,?)";
    private final String UPDATE = "UPDATE CURSO SET NOME=?, DESCRICAO=?, ATIVO=?, HORARIO_INICIO=?, HORARIO_TERMINO=? WHERE ID=?";
    private final String DELETE = "DELETE FROM CURSO WHERE ID =?";
    private final String LIST = "SELECT * FROM CURSO";
    private final String LISTBYID = "SELECT * FROM CURSO WHERE ID=?";
    
    public void inserir(Curso curso) {
    	try(Connection con = Database.getConnection()){
    		try(PreparedStatement stmt = con.prepareStatement(INSERT)){
	    		stmt.setString(1, curso.getNome());
	    		stmt.setString(2, curso.getDescricao());
	    		stmt.setInt(3, curso.getAtivo());
	    		Date inicio = new java.sql.Date(curso.getHorarioInicio().getTime());
	    		stmt.setDate(4, inicio);
	    		Date fim = new java.sql.Date(curso.getHorarioTermino().getTime());
	    		stmt.setDate(5, fim);
	    		stmt.execute();
	    		JOptionPane.showMessageDialog(null, "Curso cadastrado com sucesso.");
    		}
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Erro ao inserir curso no banco de dados: " +e.getMessage());
		}
    }
    
    public void atualizar(Curso curso) {
    	try(Connection con = Database.getConnection()){
    		try(PreparedStatement stmt = con.prepareStatement(UPDATE)){
	    		stmt.setString(1, curso.getNome());
	    		stmt.setString(2, curso.getDescricao());
	    		stmt.setInt(3, curso.getAtivo());
	    		Date inicio = new java.sql.Date(curso.getHorarioInicio().getTime());
	    		stmt.setDate(4, inicio);
	    		Date fim = new java.sql.Date(curso.getHorarioTermino().getTime());
	    		stmt.setDate(5, fim);
	    		stmt.execute();
	    		JOptionPane.showMessageDialog(null, "Curso alterado com sucesso.");
    		}
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Erro ao alterar curso no banco de dados: " +e.getMessage());
		}
    }
    
    public void remover(long idContato) {
    	try(Connection con = Database.getConnection()){
    		try(PreparedStatement stmt = con.prepareStatement(DELETE)){
	    		stmt.setLong(1, idContato);
	    		JOptionPane.showMessageDialog(null, "Curso removido com sucesso.");
    		}
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Erro ao remover curso no banco de dados: " +e.getMessage());
		}
    }
    
    public List<Curso> getCursos(){
    	ArrayList<Curso> cursos = new ArrayList<Curso>();
    	try(Connection con = Database.getConnection()){
    		try(PreparedStatement stmt = con.prepareStatement(LIST)){
    			stmt.execute();
    			ResultSet rs = stmt.getResultSet();
    			
				while(rs.next()) {
					Curso curso = new Curso();
					 
	                curso.setId(rs.getLong("id"));
	                curso.setNome(rs.getString("nome"));
	                curso.setDescricao(rs.getString("descricao"));
	                curso.setAtivo(rs.getInt("ativo"));
	                curso.setHorarioInicio(rs.getDate("horario_inicio"));
	                curso.setHorarioTermino(rs.getDate("horario_termino"));
	                
	                cursos.add(curso);
				}
    		}
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Erro ao listar cursos no banco de dados: " +e.getMessage());
		}
        return cursos;
    }
    
    public Curso getCursobyId(long idCurso) {
    	Curso curso = new Curso();
    	try (Connection con = Database.getConnection()){
			try(PreparedStatement stmt = con.prepareStatement(LISTBYID)){
				stmt.setLong(1, idCurso);
				stmt.execute();
				ResultSet rs = stmt.getResultSet();
				rs.next();
				
				curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setDescricao(rs.getString("descricao"));
                curso.setAtivo(rs.getInt("ativo"));
                curso.setHorarioInicio(rs.getDate("horario_inicio"));
                curso.setHorarioTermino(rs.getDate("horario_termino"));
				
			}
		}catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Erro ao listar cursos no banco de dados: " +e.getMessage());
		}
    	return curso;
    	
    }
}