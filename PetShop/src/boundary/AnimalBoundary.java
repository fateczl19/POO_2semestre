package boundary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import control.AnimalControl;
import entity.Animal;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnimalBoundary extends Application implements EventHandler<ActionEvent>{
	
	private Label lblId = new Label("Id:");
	private Label lblAnimalNome = new Label("Nome do Animal: ");
	private Label lblDtNasc = new Label("Data de Nascimento: ");
	private Label lblPeso = new Label("Peso: ");
	
	private TextField txtId = new TextField();
	private TextField txtAnimalNome = new TextField();
	private TextField txtDtNasc = new TextField();
	private TextField txtPeso = new TextField();
	
	private Button btnAdd = new Button("Adicionar");
	private Button btnPesq = new Button("Pesquisar");

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private TableView<Animal> tableView = new TableView<>();
	private AnimalControl control = new AnimalControl();
	

	public static void main(String[] args) {
		Application.launch(args);
	}


	@Override
	public void start(Stage stage) throws Exception {
		
		tableView.setStyle(STYLESHEET_MODENA);
		
		BorderPane border = new BorderPane();
		VBox TopLeftbox = new VBox();
		VBox TopRightbox = new VBox();
		Scene scene = new Scene(border, 800, 600);
		
		
		TopLeftbox.getChildren().addAll(lblId,lblAnimalNome, lblDtNasc, lblPeso, btnAdd );
		TopLeftbox.setSpacing(13);
		border.setLeft(TopLeftbox);
		
		TopRightbox.getChildren().addAll(txtId,txtAnimalNome, txtDtNasc, txtPeso, btnPesq );
		TopRightbox.setSpacing(5);
		TopRightbox.setPrefSize(400, 0);
		border.setRight(TopRightbox);
		
		createTableColumns();
		border.setBottom(tableView);
		BorderPane.setMargin(tableView, new Insets(0, 0, 300, 0));
		BorderPane.setAlignment(tableView, Pos.TOP_CENTER);
		
		btnAdd.setPrefSize(400, 20);
		btnPesq.setPrefSize(400, 20);
		btnAdd.addEventFilter(ActionEvent.ACTION, this);
		btnPesq.addEventFilter(ActionEvent.ACTION, this);
		
	    stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.setTitle("Registro de Animais do Petshop");
		stage.show();
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnAdd) { 
			Animal p = boundaryToAnimal();
			control.adicionar(p);
			animalToBoundary(new Animal());
		} else if (event.getTarget() == btnPesq) { 
			control.pesquisar(txtAnimalNome.getText());		
		}
	}
	
	private void animalToBoundary(Animal a) {
		txtId.setText( String.valueOf(a.getId()) );
		txtAnimalNome.setText( a.getNome() );
		txtDtNasc.setText( sdf.format(a.getNascimento()) );
		txtPeso.setText( String.format("%6.2f", a.getPeso()) );
	}

	private Animal boundaryToAnimal() {
		Animal a = new Animal();
		a.setNome( txtAnimalNome.getText() );
		try {
			a.setId( Long.parseLong(txtId.getText()) );
			a.setPeso( Float.parseFloat(txtPeso.getText()) );
			Date d = sdf.parse(txtDtNasc.getText());
			a.setNascimento(d);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	
	private void createTableColumns() { 
		tableView.setItems( control.getDataList() );
		tableView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Animal>() {
			@Override
			public void changed(ObservableValue<? extends Animal> p, Animal p1, Animal p2) {
				if (p2 != null) { 
					animalToBoundary(p2);
				}
			} 
		});
		
		TableColumn<Animal, Number> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(
				item -> new ReadOnlyLongWrapper(item.getValue().getId()));
		
		TableColumn<Animal, String> nomeColumn = new TableColumn<>("Nome");
		nomeColumn.setCellValueFactory(
				item -> new ReadOnlyStringWrapper(item.getValue().getNome()));
		
		TableColumn<Animal, String> dtColumn = new TableColumn<>("Nascimento");
		dtColumn.setCellValueFactory(
				item -> new ReadOnlyStringWrapper(sdf.format(item.getValue().getNascimento()))
				);
		
		TableColumn<Animal, Number> pesoColumn = new TableColumn<>("Peso");
		pesoColumn.setCellValueFactory(
				item -> new ReadOnlyDoubleWrapper(item.getValue().getPeso()));
		

		
		
		tableView.getColumns().addAll(idColumn, nomeColumn, dtColumn, pesoColumn);
	}

}
