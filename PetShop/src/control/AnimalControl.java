package control;

import java.util.ArrayList;
import java.util.List;

import entity.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AnimalControl {
	private List<Animal> lista = new ArrayList<>();
	private ObservableList<Animal> dataList = FXCollections.observableArrayList();
	
	public ObservableList<Animal> getDataList() {
		
		return dataList;
	}
	
	public void setDataList(ObservableList<Animal> dataList) {
		this.dataList = dataList;
	}
	
	public void adicionar(Animal l) {
		lista.add(l);
		System.out.println(
				String.format("Adicionado Animal %s na lista, tamanho: %d ", 
						l, lista.size()));
		dataList.clear();
		dataList.addAll(lista);
	}
	
	public void pesquisar(String nome) { 
		dataList.clear();
		for (Animal a : lista) { 
			if (a.getNome().contains(nome)) {
				dataList.add(a);
			}
		}
	}

}
