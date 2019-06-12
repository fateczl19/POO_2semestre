package view;

import controller.CursoController;

public class Pricipal {

	private static CursoController cursos;

	public static void main(String[] args) {
		cursos = new CursoController();
		cursos.listaCurso();
	}

}
