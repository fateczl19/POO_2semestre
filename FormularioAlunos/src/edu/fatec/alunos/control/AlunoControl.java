package edu.fatec.alunos.control;

import java.util.ArrayList;
import java.util.List;

import edu.fatec.alunos.control.dao.AlunoDAO;
import edu.fatec.alunos.control.dao.DAOException;
import edu.fatec.alunos.entity.Aluno;

public class AlunoControl {
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private AlunoDAO dao = new AlunoDAO();

	public void inserir(Aluno a) throws ControlException {
		try {
			dao.inserir(a);
			alunos.clear();
			alunos.addAll(dao.pesquisar(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}

	public void aualizar(Aluno a) throws ControlException {
		try {
			dao.aualizar(a);
			alunos.clear();
			alunos.addAll(dao.pesquisar(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}

	public void pesquisar(String ra) throws ControlException {
		try {
			alunos.clear();
			alunos.addAll(dao.pesquisar(ra));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}

	public void remover(Aluno a) throws ControlException {
		try {
			dao.remover(a);
			alunos.clear();
			alunos.addAll(dao.pesquisar(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
}
