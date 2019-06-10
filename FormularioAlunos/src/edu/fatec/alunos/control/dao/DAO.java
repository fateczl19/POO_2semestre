package edu.fatec.alunos.control.dao;

import java.util.List;

import edu.fatec.alunos.entity.Aluno;

public interface DAO {
	public void inserir(Aluno a) throws DAOException;

	public void aualizar(Aluno a) throws DAOException;

	public List<Aluno> pesquisar(String ra) throws DAOException;

	public void remover(Aluno a) throws DAOException;
}
