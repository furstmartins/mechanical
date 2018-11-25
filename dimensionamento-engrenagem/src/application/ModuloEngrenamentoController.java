package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloEngrenamentoController {

	@FXML TextField lbValorModuloEngrenamento;
	@FXML TextField tfValorModuloEngrenamento;
	@FXML Button btManterModuloEngrenamento;
	@FXML Button btAlterarModuloEngrenamento;
	
	@FXML 
	public void manterModuloEngrenamento(ActionEvent event) {
//		moduloEngrenamentoNormalizado = round2Decimal(new Double(lbValorModuloEngrenamento.getText()));
		Stage stage = (Stage) btManterModuloEngrenamento.getScene().getWindow();
	    stage.close();

//	    System.out.println(moduloEngrenamento);
//	    preencheGridPane("Módulo de Engrenamento Normalizado (m) [mm]", 2.50, 7);
	}

	@FXML 
	public void alterarModuloEngrenamento(ActionEvent event) {
		
	}

}
