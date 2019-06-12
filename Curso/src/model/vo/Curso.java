package model.vo;

import java.util.Date;

public class Curso {
	long id;
	String nome;
	String descricao;
	int ativo;
	Date horarioInicio;
	Date horarioTermino;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	public Date getHorarioInicio() {
		return horarioInicio;
	}
	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}
	public Date getHorarioTermino() {
		return horarioTermino;
	}
	public void setHorarioTermino(Date horarioTermino) {
		this.horarioTermino = horarioTermino;
	}
	
	
}
