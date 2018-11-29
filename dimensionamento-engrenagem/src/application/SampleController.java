package application;

import org.apache.commons.lang3.StringUtils;

import calculation.Calculator;
import calculation.GearMath;
import calculation.GeometricGearCalculation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SampleController {

	Calculator calc = new Calculator();

	@FXML TextField tfPotencia;
	@FXML TextField tfRotacao;
	@FXML TextField tfDureza;
	@FXML TextField tfDuracao;
	@FXML TextField tfAnguloPressao;
	@FXML TextField tfFatorServico;
	@FXML TextField tfRazaoLarguraDiametro;
	@FXML TextField tfZ1;
	@FXML TextField tfZ2;
	@FXML TextField tfFatorForma;

	@FXML Button btCalcular;
	@FXML Button btPanelDureza;
	@FXML Button btPanelFatorServico;

	@FXML Label lbMt;
	@FXML Label lbRelacaoTransmissao;
	@FXML Label lbFatorDurabilidade;
	@FXML Label lbPressaoAdmissivel;
	@FXML Label lbVolumeMinimo;
	@FXML Label lbDiametroPrimitivo;
	@FXML Label lbModuloEngrenamento;
	@FXML GridPane gpResultado;
	@FXML Button btRecalcular;

	@FXML TextField tfModuloNormalizado;
	@FXML Label lbModuloNormalizado;

	@FXML Label lbTensaoAdmissivel;
	@FXML Label lbNovoModulo;
	@FXML TextField tfTensaoAdmissivel;
	@FXML TextField tfNovoModulo;
	@FXML Button btRedimensionar;
	@FXML Button btLimpar;
	@FXML Button btResumo;

	@FXML GridPane gpResumo;

	double potencia;
	double n;
	int durezaHB;
	int duracao;
	double anguloPressao;
	double fatorServico;
	double razaoLarguraDiametro;
	int Z1;
	int Z2;
	double moduloEngrenamentoNormalizado;
	double fatorForma;

	//Resultado do primeiro calculo
	double Mt = 0;
	double moduloEngrenamento;
	double i = 0;
	double W = 0;
	double Wraiz6 = 0;
	double pressaoAdmissivel = 0;
	double volumeMinimo = 0;
	double diametroPrimitivo = 0;

	//Resultado da normalizacao
	double diametroPrimitivoNormalizado = 0;
	double larguraEngrenagem = 0;
	double forcaTangencial = 0;
	double tensaoMaxima = 0;

	private int posicaoNoGrid = 0;

	double tensaoAdmissivel = 0;
	double novoModulo = 0;
	private boolean jaRecalculou = false;

	@FXML
	public void calcula(ActionEvent event) {
		for (int i = 0; i < gpResultado.getChildren().size(); i++) {
			if (gpResultado.getChildren().get(i) instanceof Label) {
				gpResultado.getChildren().remove(i);
				i--;
			}
		}
		posicaoNoGrid = 0;

		StringBuilder alerta = new StringBuilder();
		boolean validate = validaCampos(alerta);


		if (!validate) {
			showAlert("Campos obrigatórios não preenchidos", alerta.toString());
			return;
		}

		Mt = calc.calculaTorquePinhao(potencia, n);
		preencheGridPane("Torque no pinhão (Mt) [Nmm]", Mt);

		i = calc.calculaRelacaoTransmissao(Z1, Z2);
		preencheGridPane("Relação de transmissao (i)", i);

		W = calc.calculaFatorDurabilidade(n, duracao);
		preencheGridPane("Fator de durabilidade (W)", W);

		Wraiz6 = calc.calculaFatorDurabilidadeRaiz6(n, duracao);

		pressaoAdmissivel = calc.calculaPressaoAdmissivel(durezaHB, Wraiz6);
		preencheGridPane("Pressao Admissível (Padm) [N/mm2]", pressaoAdmissivel);

		volumeMinimo = calc.calculaVolumeMinimoPositivo(Mt, pressaoAdmissivel, i, fatorServico);
		preencheGridPane("Volume Mínimo (b1d2) [mm3]", volumeMinimo);

		diametroPrimitivo = calc.calculaDiametroPrimitivo(volumeMinimo, razaoLarguraDiametro);
		preencheGridPane("Diâmetro Primitivo [mm]", diametroPrimitivo);

		moduloEngrenamento = calc.calculaModuloEngrenamento(diametroPrimitivo, Z1);
		preencheGridPane("Módulo de Engrenamento (m) [mm]", moduloEngrenamento);

		showAlertModuloNormalizado(round2Decimal(moduloEngrenamento));
	}

	private void preencheGridPane(String label, double value) {
		Font fonte = new Font(11);
		Label lb1 = new Label(label);
		lb1.setFont(fonte);
		Label lb2 = new Label("" + round2Decimal(value));
		lb2.setFont(fonte);
		gpResultado.addRow(posicaoNoGrid, lb1);
		gpResultado.addRow(posicaoNoGrid, lb2);
		posicaoNoGrid++;
	}

	private void preencheTopicoGridPane(String label) {
		Font fonte = Font.font("Verdana", FontWeight.BOLD, 12);
		Label lb1 = new Label(label);
		lb1.setFont(fonte);
		gpResultado.addRow(posicaoNoGrid, lb1);
		posicaoNoGrid++;
	}

	private void showAlertModuloNormalizado(double value) {
		StringBuilder alerta = new StringBuilder();
		alerta.append("O valor do módulo de engrenamento foi " + value)
			.append("\n\nOs módulos normalizados na faixa de 1,0 a 4,0 mm são: ")
			.append("1.0; 1.25; 1.50; 1.75; 2.00; 2.25; 2.50; 2.75; 3.00; 3.25; 3.50; 3.75; 4.00; ")
			.append("\n\nDesejar alterar o valor do Módulo?");

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.initModality(Modality.APPLICATION_MODAL);
	    alert.setTitle("Módulo de Engrenamento");
	    alert.setContentText(alerta.toString());
	    alert.setHeaderText("");
	    alert.showAndWait();

	    if (alert.getResult() == ButtonType.OK) {
	    	lbModuloNormalizado.setVisible(true);
	    	tfModuloNormalizado.setVisible(true);
	    	btCalcular.setVisible(false);
	    	btRecalcular.setVisible(true);
	    } else {
	    	System.out.println("pressed cancel");
	    }
	}

	private boolean validaCampos(StringBuilder alerta) {
		boolean validate = true;
		if (tfPotencia == null || StringUtils.isBlank(tfPotencia.getText())) {
			alerta.append("Potência [W]: deve ser preenchido");
			validate = false;
		} else {
			try {
				potencia = new Double(tfPotencia.getText());
			} catch (Exception e) {
				alerta.append("O valor da Potência deve ser um número decimal");
				validate = false;
			}
		}
		if (tfRotacao == null || StringUtils.isBlank(tfRotacao.getText())) {
			alerta.append("\nRotação [rmp]: deve ser preenchido");
			validate = false;
		} else {
			try {
				n = new Double(tfRotacao.getText());
			} catch (Exception e) {
				alerta.append("\nO valor da Rotação deve ser um número decimal");
				validate = false;
			}
		}
		if (tfDureza == null || StringUtils.isBlank(tfDureza.getText())) {
			alerta.append("\nDureza [HB]: deve ser preenchido");
			validate = false;
		} else {
			try {
				durezaHB = new Integer(tfDureza.getText());
			} catch (Exception e) {
				alerta.append("\nO valor da Dureza deve ser um número inteiro");
				validate = false;
			}
		}
		if (tfDuracao == null || StringUtils.isBlank(tfDuracao.getText())) {
			alerta.append("\nDuração [h]: deve ser preenchido");
			validate = false;
		} else {
			try {
				duracao = new Integer(tfDuracao.getText());
			} catch (Exception e) {
				alerta.append("\nO valor da Duração deve ser um número inteiro");
				validate = false;
			}
		}
		if (tfAnguloPressao == null || StringUtils.isBlank(tfAnguloPressao.getText())) {
			alerta.append("\nÂngulo Pressão: deve ser preenchido");
			validate = false;
		} else {
			try {
				anguloPressao = new Double(tfAnguloPressao.getText());
			} catch (Exception e) {
				alerta.append("\nO valor do Ângulo de Pressão deve ser um número decimal");
				validate = false;
			}
		}
		if (tfFatorServico == null || StringUtils.isBlank(tfFatorServico.getText())) {
			alerta.append("\nFator de Serviço: deve ser preenchido");
			validate = false;
		} else {
			try {
				fatorServico = new Double(tfFatorServico.getText());
			} catch (Exception e) {
				alerta.append("\nO valor do Fator de Serviço deve ser um número decimal");
				validate = false;
			}
		}
		if (tfRazaoLarguraDiametro == null || StringUtils.isBlank(tfRazaoLarguraDiametro.getText())) {
			alerta.append("\nRelação largura/diâmetro primitivo: deve ser preenchido");
			validate = false;
		} else {
			try {
				razaoLarguraDiametro = new Double(tfRazaoLarguraDiametro.getText());
			} catch (Exception e) {
				alerta.append("\nO valor da Razão entre Largura e Diâmetro deve ser um número decimal");
				validate = false;
			}
		}
		if (tfZ1 == null || StringUtils.isBlank(tfZ1.getText())) {
			alerta.append("\nNº Dentes Pinhão: deve ser preenchido");
			validate = false;
		} else {
			try {
				Z1 = new Integer(tfZ1.getText());
			} catch (Exception e) {
				alerta.append("\nO valor do Nº de Dentes do Pinhão deve ser um número inteiro");
				validate = false;
			}
		}
		if (tfZ2 == null || StringUtils.isBlank(tfZ2.getText())) {
			alerta.append("\nNº Dentes Coroa: deve ser preenchido");
			validate = false;
		} else {
			try {
				Z2 = new Integer(tfZ2.getText());
			} catch (Exception e) {
				alerta.append("\nO valor do Nº de Dentes da Coroa deve ser um número inteiro");
				validate = false;
			}
		}
		if (tfFatorForma == null || StringUtils.isBlank(tfFatorForma.getText())) {
			alerta.append("\nFator de Forma: deve ser preenchido");
			validate = false;
		} else {
			try {
				fatorForma = new Double(tfFatorForma.getText());
			} catch (Exception e) {
				alerta.append("\nO valor do Fator de Forma deve ser um número decimal");
				validate = false;
			}
		}

		return validate;
	}



	private double round2Decimal(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.initModality(Modality.APPLICATION_MODAL);
	    alert.setTitle(title);
	    alert.setContentText(message);
	    alert.setHeaderText("");
	    alert.show();
	}


	@FXML
	public void recalcula(ActionEvent event) {
		if (tfModuloNormalizado == null || StringUtils.isBlank(tfModuloNormalizado.getText())) {
			showAlert("Validação de Campos", "O campo Módulo Normalizado é obrigatório");
			return;
		} else {
			try {
				moduloEngrenamentoNormalizado = new Double(tfModuloNormalizado.getText());
			} catch (Exception e) {
				showAlert("Validação de Campos", "O valor do Módulo Normalizado deve ser um número decimal");
				return;
			}
		}
		if (jaRecalculou) {
			limpaGripPaneNormalizado();
		}
		calculaNormalizado();
		jaRecalculou = true;
		showAlertTensaoMaxima(round2Decimal(tensaoMaxima));
	}

	private void calculaNormalizado() {
		preencheTopicoGridPane("Normalizando");
		preencheGridPane("Módulo Engrenamento Normalizado (mn) [mm]", moduloEngrenamentoNormalizado);

		diametroPrimitivoNormalizado = calc.calculaDiametroPrimitivo(moduloEngrenamentoNormalizado, Z1);
		preencheGridPane("Diâmetro Primitivo Normalizado [mm]", diametroPrimitivoNormalizado);

		larguraEngrenagem = calc.calculaLarguraEngrenagem(volumeMinimo, diametroPrimitivoNormalizado);
		preencheGridPane("Largura do Pinhão [mm]", larguraEngrenagem);

		forcaTangencial = calc.calculaForcaTangencial(Mt, diametroPrimitivoNormalizado);
		preencheGridPane("Força Tangencial (Ft) [N]", forcaTangencial);

		tensaoMaxima = calc.calculaTensaoMaxima(forcaTangencial, fatorForma, fatorServico, larguraEngrenagem, moduloEngrenamentoNormalizado);
		preencheGridPane("Tensão Máxima [MPa]", tensaoMaxima);

	}

	private void limpaGripPaneNormalizado() {
		int size = gpResultado.getChildren().size();
		for (int i = size-1; i >= size - 8; i--) {
			if (gpResultado.getChildren().get(i) instanceof Label) {
				gpResultado.getChildren().remove(i);
//				i;
			}
		}
	}
	private void showAlertTensaoMaxima(double value) {
		StringBuilder alerta = new StringBuilder();
		alerta.append("O valor da Tensão que estará atuando na engrenagem é de " + value + " MPa.")
			.append("\n\nVerifique se essa tensão é inferior à tensão admissível do material da engrenagem. ")
			.append("Caso seja maior, há duas hipóteses para redimensionar a engrenagem. ")
			.append("\n1ª Hipótese: redimensionar pela Tensão admissível.")
			.append("\n2ª Hipótese: alterar o módulo de engrenamento.")
			.append("\n\nDesejar redimensionar a engrenagem?");

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.initModality(Modality.APPLICATION_MODAL);
	    alert.setTitle("Tensão Máxima");
	    alert.setContentText(alerta.toString());
	    alert.setHeaderText("");
	    alert.showAndWait();

	    if (alert.getResult() == ButtonType.OK) {
	    	btCalcular.setVisible(false);
	    	btRecalcular.setVisible(false);

	    	lbTensaoAdmissivel.setVisible(true);
	    	lbNovoModulo.setVisible(true);
	    	tfTensaoAdmissivel.setVisible(true);
	    	tfNovoModulo.setVisible(true);
	    	btRedimensionar.setVisible(true);
	    	btResumo.setVisible(true);
	    } else {
	    	btResumo.setVisible(true);
	    	btRecalcular.setVisible(false);
	    }
	}

	@FXML
	public void limpar(ActionEvent event) {
		tfPotencia.setText(null);
		tfRotacao.setText(null);
		tfDureza.setText(null);
		tfDuracao.setText(null);
		tfAnguloPressao.setText(null);
		tfFatorServico.setText(null);
		tfRazaoLarguraDiametro.setText(null);
		tfZ1.setText(null);
		tfZ2.setText(null);
		tfFatorForma.setText(null);
		tfModuloNormalizado.setText(null);
		tfTensaoAdmissivel.setText(null);
		tfNovoModulo.setText(null);

		for (int i = 0; i < gpResultado.getChildren().size(); i++) {
			if (gpResultado.getChildren().get(i) instanceof Label) {
				gpResultado.getChildren().remove(i);
				i--;
			}
		}

		btCalcular.setVisible(true);
		btRecalcular.setVisible(false);

		lbModuloNormalizado.setVisible(false);
    	tfModuloNormalizado.setVisible(false);
    	lbTensaoAdmissivel.setVisible(false);
    	lbNovoModulo.setVisible(false);
    	tfTensaoAdmissivel.setVisible(false);
    	tfNovoModulo.setVisible(false);
    	btRedimensionar.setVisible(false);
    	moduloEngrenamento = 0;
    	moduloEngrenamentoNormalizado = 0;
    	btResumo.setVisible(false);
    	jaRecalculou = false;
	}

	@FXML
	public void redimensiona(ActionEvent event) {
		StringBuilder alerta = new StringBuilder();
		boolean validate = true;
		if (tfTensaoAdmissivel == null || StringUtils.isBlank(tfTensaoAdmissivel.getText())) {
			alerta.append("O campo Nova Largura é obrigatório");
			validate = false;
		} else {
			try {
				tensaoAdmissivel = new Double(tfTensaoAdmissivel.getText());
			} catch (Exception e) {
				alerta.append("O valor da Nova Largura deve ser um número decimal");
				validate = false;
			}
		}
		if (tfNovoModulo == null || StringUtils.isBlank(tfNovoModulo.getText())) {
			alerta.append("\nO campo Novo Módulo é obrigatório");
			validate = false;
		} else {
			try {
				novoModulo = new Double(tfNovoModulo.getText());
			} catch (Exception e) {
				alerta.append("\nO valor do Novo Módulo deve ser um número decimal");
				validate = false;
			}
		}

		if (!validate) {
			showAlert("Validação de Campos", alerta.toString());
			return;
		}
		redimensionaPelaTensao();
		redimensionaPeloModulo();
		btRedimensionar.setVisible(false);
		btResumo.setVisible(true);
	}

	private void redimensionaPelaTensao() {
		preencheTopicoGridPane("1ª Hipótese - pela Tensão Admissível");
		double larguraEngrenagemRed = calc.calculaLarguraEngrenagem(forcaTangencial, fatorForma, fatorServico, moduloEngrenamentoNormalizado, tensaoAdmissivel);
		preencheGridPane("Largura do Pinhão Redimensionado [mm]", larguraEngrenagemRed);

		double novaRazaoLarguraDiametro = calc.calculaRazaoLarguraDiametroPrimitivo(larguraEngrenagemRed, diametroPrimitivoNormalizado);
		preencheGridPane("Nova Razão entre Largura e Diâmetro Primitivo", novaRazaoLarguraDiametro);
	}

	private void redimensionaPeloModulo() {
		preencheTopicoGridPane("2ª Hipótese - pelo Módulo");
		preencheGridPane("Módulo Redimensionado [mm]", novoModulo);

		double diametroPrimitivoRed = calc.calculaDiametroPrimitivo(novoModulo, Z1);
		preencheGridPane("Diâmetro Primitivo Redimensionado [mm]", diametroPrimitivoRed);

		double forcaTangencialRed = calc.calculaForcaTangencial(Mt, diametroPrimitivoRed);
		preencheGridPane("Força Tangencial Redimensionada (Ft) [N]", forcaTangencialRed);

		double tensaoMaximaRed = calc.calculaTensaoMaxima(forcaTangencialRed, fatorForma, fatorServico, larguraEngrenagem, novoModulo);
		preencheGridPane("Tensão Máxima Redimensionada [MPa]", tensaoMaximaRed);

	}

	private int index = 0;
	@FXML
	public void exibirResumo(ActionEvent event) {
		index = 0;

		GeometricGearCalculation ggc = new GeometricGearCalculation();
		showScene();
		preencheGridPaneResumoCabecalho("Formulário", "Pinhão (mm)", "Coroa (mm)");
		preencheGridPaneResumo("Módulo normalizado DIN 780", "mn = " + moduloEngrenamentoNormalizado, "mn = " + moduloEngrenamentoNormalizado);
		preencheGridPaneResumo("Número de Dentes", "Z1 = " + Z1, "Z2 = " + Z2);
		preencheGridPaneResumo("Razão de Contato", "i = " + i, "i = " + i);
		double passo = round2Decimal(ggc.passo(moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Passo to = mn . PI", "to = " + passo, "to = " + passo);
		double vaoEntreDentes = round2Decimal(ggc.vaoEntreDentes(passo));
		preencheGridPaneResumo("Vão entre os dentes lo", "lo = " + vaoEntreDentes, "lo = " + vaoEntreDentes);
		double alturaCabeca = round2Decimal(ggc.alturaCabeca(moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Altura da cabeça do dente hk", "hk = " + alturaCabeca, "hk = " + alturaCabeca);
		double alturaPe = round2Decimal(ggc.alturaPe(moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Altura do pé do dente hf", "hf = " + alturaPe, "hf = " + alturaPe);
		double alturaComum = round2Decimal(ggc.alturaComum(moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Altura comum do dente h", "h = " + alturaComum, "h = " + alturaComum);
		double alturaTotal = round2Decimal(ggc.alturaTotal(moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Altura total do dente hz", "hz = " + alturaTotal, "hz = " + alturaTotal);
		double espessuraDente = round2Decimal(ggc.espessuraDente(passo));
		preencheGridPaneResumo("Espessura do dente So", "So = " + espessuraDente, "So = " + espessuraDente);
		double folgaCabeca = round2Decimal(ggc.folgaCabeca(moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Folga da cabeça Sk", "Sk = " + folgaCabeca, "Sk = " + folgaCabeca);
		double diametroPrimitivoZ1 = round2Decimal(ggc.diametroPrimitivo(moduloEngrenamentoNormalizado, Z1));
		double diametroPrimitivoZ2 = round2Decimal(ggc.diametroPrimitivo(moduloEngrenamentoNormalizado, Z2));
		preencheGridPaneResumo("Diâmetro primitivo do", "do1 = " + diametroPrimitivoZ1, "do2 = " + diametroPrimitivoZ2);
		double diametroBaseZ1 = round2Decimal(ggc.diametroBase(diametroPrimitivoZ1, anguloPressao));
		double diametroBaseZ2 = round2Decimal(ggc.diametroBase(diametroPrimitivoZ2, anguloPressao));
		preencheGridPaneResumo("Diâmetro de base dg", "dg1 = " + diametroBaseZ1, "dg2 = " + diametroBaseZ2);
		double diametroInternoZ1 = round2Decimal(ggc.diametroInterno(diametroPrimitivoZ2, moduloEngrenamentoNormalizado));
		double diametroInternoZ2 = round2Decimal(ggc.diametroInterno(diametroPrimitivoZ2, moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Diâmetro interno df", "df1 = " + diametroInternoZ1, "df2 = " + diametroInternoZ2);
		double diametroExternoZ1 = round2Decimal(ggc.diametroExterno(diametroPrimitivoZ1, moduloEngrenamentoNormalizado));
		double diametroExternoZ2 = round2Decimal(ggc.diametroExterno(diametroPrimitivoZ2, moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Diâmetro externo dk", "dk1 = " + diametroExternoZ1, "dk2 = " + diametroExternoZ2);

		double distanciaCentros = round2Decimal(ggc.distanciaEntreCentros(diametroPrimitivoZ1, diametroPrimitivoZ2));
		preencheGridPaneResumo("Distância entre centros C", "C = " + distanciaCentros, "C = " + distanciaCentros);

		preencheGridPaneResumo("Largura das engrenagens", "b = " + round2Decimal(larguraEngrenagem), "b = " + round2Decimal(larguraEngrenagem));

		GearMath gm = new GearMath();
		double adendo = round2Decimal(gm.getAddendum(diametroPrimitivoZ1, Z1));
		preencheGridPaneResumo("Adendo", "a = " + adendo, "a = " + adendo);
		double dedendo = round2Decimal(gm.getDedendum(moduloEngrenamentoNormalizado));
		preencheGridPaneResumo("Dedendo", "d = " + dedendo, "d = " + dedendo);

		double comprimentoAcao = round2Decimal(gm.getComprimentoAcao(diametroExternoZ1/2, diametroBaseZ1/2, diametroExternoZ2/2, diametroBaseZ2/2, distanciaCentros, anguloPressao));
		preencheGridPaneResumo("Comprimento de Ação", "Z = " + comprimentoAcao, "Z = " + comprimentoAcao);
		double passoBase = round2Decimal(gm.getPb(diametroBaseZ1/2, Z1));
		preencheGridPaneResumo("Passo Base", "Pb = " + passoBase, "Pb = " + passoBase);
		double razaoContato = round2Decimal(gm.getRazaoContato(comprimentoAcao, passoBase));
		preencheGridPaneResumo("Razão de Contato", "mp = " + razaoContato, "mp = " + razaoContato);

	}

	private void showScene() {
		try {
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Resumo.fxml"));

			Scene scene = new Scene(root,600,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			stage.setMaxWidth(stage.getWidth());
			stage.setMaxHeight(stage.getHeight());
			stage.setTitle("Resumo");
			AnchorPane ap = (AnchorPane) root.getChildren().get(0);
			ScrollPane sp = (ScrollPane) ap.getChildren().get(1);
			AnchorPane ap1 = (AnchorPane) sp.getContent();
			gpResumo = (GridPane) ap1.getChildren().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencheGridPaneResumoCabecalho(String label, String value1, String value2) {
		Font fonte = Font.font("Verdana", FontWeight.BOLD, 12);
		Label lb1 = new Label(label);
		lb1.setFont(fonte);
		Label lb2 = new Label(value1);
		lb2.setFont(fonte);
		Label lb3 = new Label(value2);
		lb3.setFont(fonte);
		gpResumo.addRow(index, lb1);
		gpResumo.addRow(index, lb2);
		gpResumo.addRow(index, lb3);
		index++;
	}

	private void preencheGridPaneResumo(String label, String value1, String value2) {
		Font fonte = new Font(11);
		Label lb1 = new Label(label);
		lb1.setFont(fonte);
		Label lb2 = new Label(value1);
		lb2.setFont(fonte);
		Label lb3 = new Label(value2);
		lb3.setFont(fonte);
		gpResumo.addRow(index, lb1);
		gpResumo.addRow(index, lb2);
		gpResumo.addRow(index, lb3);
		index++;
	}
}
