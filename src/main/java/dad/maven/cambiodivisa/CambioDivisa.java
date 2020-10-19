package dad.maven.cambiodivisa;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {
	
	private Stage primaryStage;
	
	private Button btCambiar;
	private TextField tfMonedaOrigen;
	private TextField tfMonedaCambio;
	private ComboBox<Divisa> cbMonedaOrigen;
	private ComboBox<Divisa> cbMonedaCambio;
	
	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.9);
	private Divisa dolar = new Divisa("Dolar", 1.17);
	private Divisa yen = new Divisa("Yen", 124.17);
	
	private Divisa[] _divisa = {euro, libra, dolar, yen};
	
	private void onCambiarAction(ActionEvent e) {
		try {
			Double origen = Double.parseDouble(tfMonedaOrigen.getText());
			Divisa divisaOrigen = cbMonedaOrigen.getSelectionModel().getSelectedItem();
			Divisa divisaCambio = cbMonedaCambio.getSelectionModel().getSelectedItem();
			
			Double cantidadDestino = divisaCambio.fromEuro(divisaOrigen.toEuro(origen));
			
			tfMonedaCambio.setText(cantidadDestino.toString());
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("Debe introducir un número en la cantidad de origen.");
			alert.setContentText(ex.getMessage());
			
			alert.showAndWait();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		tfMonedaOrigen = new TextField("0");
		tfMonedaOrigen.setPrefColumnCount(4);
		tfMonedaOrigen.setPromptText("Indique el valor origen de la divisa");
		tfMonedaOrigen.setMaxWidth(100.0);
		
		tfMonedaCambio = new TextField();
		tfMonedaCambio.setPrefColumnCount(4);
		tfMonedaCambio.setMaxWidth(100.0);
		tfMonedaCambio.setEditable(false);
		
		cbMonedaOrigen = new ComboBox<Divisa>();
		cbMonedaOrigen.getItems().addAll(_divisa);
		cbMonedaOrigen.getSelectionModel().selectFirst();
		
		cbMonedaCambio = new ComboBox<Divisa>();
		cbMonedaCambio.getItems().addAll(_divisa);
		cbMonedaCambio.getSelectionModel().selectFirst();
		
		btCambiar = new Button();
		btCambiar.setText("Cambiar");
		btCambiar.setDefaultButton(true);
		btCambiar.setOnAction(e -> onCambiarAction(e));
		
		HBox hbOrigen = new HBox();
		hbOrigen.setSpacing(1);
		hbOrigen.setAlignment(Pos.BASELINE_CENTER);
		hbOrigen.getChildren().addAll(tfMonedaOrigen, cbMonedaOrigen);
		
		HBox hbDivisa = new HBox();
		hbDivisa.setSpacing(1);
		hbDivisa.setAlignment(Pos.BASELINE_CENTER);
		hbDivisa.getChildren().addAll(tfMonedaCambio, cbMonedaCambio);
		
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(hbOrigen, hbDivisa, btCambiar);
		
		Scene escena = new Scene(root, 320, 200);
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("Cambio de divisa");
		primaryStage.show();
	}	

	public static void main(String[] args) {
		launch(args);
	}

}
