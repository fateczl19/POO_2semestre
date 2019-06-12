package controller;

import java.util.List;

import model.dao.CursoDao;
import model.vo.Curso;
import view.ListaCursoView;

public class CursoController {
	private ListaCursoView lista;
	private CursoDao dao;

	public CursoController() {
		dao = new CursoDao();
	}
	public void listaCurso() {
		lista = new ListaCursoView();
		lista.setVisible(true);
	}
	
	public List<Curso> getCursos(){
		List<Curso> cursos = dao.getCursos();
		
		return cursos;
	}
	
	public Curso getCursoById(long idCurso) {
		Curso curso = dao.getCursobyId(idCurso);
		return curso;
	}
}
