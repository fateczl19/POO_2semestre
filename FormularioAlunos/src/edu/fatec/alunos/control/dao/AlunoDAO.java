package edu.fatec.alunos.control.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.fatec.alunos.control.connection.ConnectDB;
import edu.fatec.alunos.entity.Aluno;

public class AlunoDAO implements DAO {

	@Override
	public void inserir(Aluno a) throws DAOException {
		try {
			Connection conn = ConnectDB.getInstance().getConnection();
			String sql = "insert into aluno (ra, nome, idade, nascimento) values (?, ?, ?,  ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, a.getRa());
			stmt.setString(2, a.getNome());
			stmt.setInt(3, a.getIdade());
			Date d = new java.sql.Date(a.getNascimento().getTime());
			stmt.setDate(4, d);
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	@Override
	public void aualizar(Aluno a) throws DAOException {
		try {
			Connection conn = ConnectDB.getInstance().getConnection();
			String sql = "update aluno set nome = ?, idade = ?, nascimento = ? where ra = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, a.getNome());
			stmt.setInt(2, a.getIdade());
			Date d = new java.sql.Date(a.getNascimento().getTime());
			stmt.setDate(3, d);
			stmt.setString(4, a.getRa());
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	@Override
	public List<Aluno> pesquisar(String ra) throws DAOException {
		List<Aluno> alunos = new ArrayList<>();
		try {
			Connection conn = ConnectDB.getInstance().getConnection();
			String sql = "select * from aluno where ra like ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + ra + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Aluno a = new Aluno();
				a.setRa(rs.getString("ra"));
				a.setNome(rs.getString("nome"));
				a.setIdade(rs.getInt("idade"));
				a.setNascimento(rs.getDate("nascimento"));
				alunos.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
		return alunos;
	}

	@Override
	public void remover(Aluno a) throws DAOException {
		try {
			Connection conn = ConnectDB.getInstance().getConnection();
			String sql = "delete from aluno where ra = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, a.getRa());
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}
