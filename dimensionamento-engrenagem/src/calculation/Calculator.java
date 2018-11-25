package calculation;

public class Calculator {

//	public static void main(String[] args) {
	private void sample() {
		double potencia = 11000d;
		double n = 1140;
		int durezaHB = 6000;
		int duracao = 10000;
		double fatorServico = 1;
		double relacaoLarguraDiametro = 0.25;
		int anguloPressao = 20;
		int Z1 = 29;
		int Z2 = 110;
		double moduloEngrenamentoNormalizado = 0;
		double fatorForma = 3.0835;
		
		Calculator calc = new Calculator();
		calc.calcula(potencia, n, durezaHB, duracao, fatorServico, relacaoLarguraDiametro, Z1, Z2);
//		calc.normaliza(moduloEngrenamentoNormalizado, Z1, volumeMinimo, Mt, fatorForma, fatorServico);
//		calc.redimensionaPelaTensao(tensaoAdmissivel, forcaTangencial, fatorForma, fatorServico, moduloEngrenamentoNormalizado, diametroPrimitivoNormalizado);
//		calc.redimensionaPeloModulo(moduloRedimensionado, Z1, Mt, forcaTangencial, fatorForma, fatorServico, larguraEngrenagem);
	}
	
	public void calcula(double potencia, double n, int durezaHB, int duracao, double fatorServico, double relacaoLarguraDiametro,
			int Z1, int Z2) {
		
		
		Calculator myTest = new Calculator();
		
		double Mt = myTest.calculaTorquePinhao(potencia, n);
		System.out.println("Torque no pinhão (Mt): \t\t\t\t" + myTest.round2Decimal(Mt) +" Nmm");
		
		double i = myTest.calculaRelacaoTransmissao(Z1, Z2);
		System.out.println("Relação de transmissao (i): \t\t\t" + myTest.round2Decimal(i));
		
		double W = myTest.calculaFatorDurabilidade(n, duracao);
		System.out.println("Fator de durabilidade (W): \t\t\t" + myTest.round2Decimal(W));
		
		double pressaoAdmissivel = myTest.calculaPressaoAdmissivel(durezaHB, W);
		System.out.println("Pressao Admissivel (Padm): \t\t\t" + myTest.round2Decimal(pressaoAdmissivel) + " N/mm2");
		
		double volumeMinimo = myTest.calculaVolumeMinimoPositivo(Mt, pressaoAdmissivel, i, fatorServico);
		System.out.println("Volume Mínimo (b1d2): \t\t\t\t" + myTest.round2Decimal(volumeMinimo) + " mm3");
		
		double diametroPrimitivo = myTest.calculaDiametroPrimitivo(volumeMinimo, relacaoLarguraDiametro);
		System.out.println("Diâmetro Primitivo: \t\t\t\t" + myTest.round2Decimal(diametroPrimitivo) + " mm");
		
		double moduloEngrenamento = myTest.calculaModuloEngrenamento(diametroPrimitivo, Z1);
		System.out.println("Modulo de Engrenamento (m): \t\t\t" + myTest.round2Decimal(moduloEngrenamento) + " mm");
		
//		moduloEngrenamentoNormalizado = 2.25;
//		System.out.println("Módulo Engrenamento Normalizado: \t\t" + moduloEngrenamentoNormalizado + " mm");
//		
//		double diametroPrimitivoNormalizado = myTest.calculaDiametroPrimitivo(moduloEngrenamentoNormalizado, Z1);
//		System.out.println("Diâmetro Primitivo Normalizado: \t\t" + myTest.round2Decimal(diametroPrimitivoNormalizado) +" mm");
//		
//		double larguraEngrenagem = myTest.calculaLarguraEngrenagem(volumeMinimo, diametroPrimitivoNormalizado);
//		System.out.println("Largura do Pinhão: \t\t\t\t" + myTest.round2Decimal(larguraEngrenagem) + " mm");
//		
//		double forcaTangencial = myTest.calculaForcaTangencial(Mt, diametroPrimitivoNormalizado);
//		System.out.println("Força Tangencial (Ft): \t\t\t\t" + myTest.round2Decimal(forcaTangencial) + " N");
//		
//		double tensaoMaxima = myTest.calculaTensaoMaxima(forcaTangencial, fatorForma, fatorServico, larguraEngrenagem, moduloEngrenamentoNormalizado);
//		System.out.println("Tensão Máxima: \t\t\t\t\t" + myTest.round2Decimal(tensaoMaxima) + " MPa");
		
//		Redimensionamento 1ª hipótese - pela Tensao Admissível
//		double tensaoAdmissivel = 170;
//		double larguraEngrenagemRed = myTest.calculaLarguraEngrenagem(forcaTangencial, fatorForma, fatorServico, moduloEngrenamentoNormalizado, tensaoAdmissivel);
//		System.out.println("Largura do Pinhão Redimensionado: \t\t" + myTest.round2Decimal(larguraEngrenagemRed) + " mm");
//		
//		double novaRazaoLarguraDiametro = myTest.calculaRazaoLarguraDiametroPrimitivo(larguraEngrenagemRed, diametroPrimitivoNormalizado);
//		System.out.println("Nova Razão entre Largura e Diâmetro Primitivo: \t" + myTest.round2Decimal(novaRazaoLarguraDiametro));
//		
//		Redimensionamento 2ª hipótese - alterando módulo
//		double moduloRedimensionado = 2.75;
//		System.out.println("Módulo Redimensionado: \t\t\t\t" + moduloRedimensionado + " mm");
//		
//		double diametroPrimitivoRed = myTest.calculaDiametroPrimitivo(moduloRedimensionado, Z1);
//		System.out.println("Diâmetro Primitivo Redimensionado: \t\t" + myTest.round2Decimal(diametroPrimitivoRed) +" mm");
//		
//		double forcaTangencialRed = myTest.calculaForcaTangencial(Mt, diametroPrimitivoRed);
//		System.out.println("Força Tangencial Redimensionada (Ft): \t\t" + myTest.round2Decimal(forcaTangencialRed) + " N");
//		
//		double tensaoMaximaRed = myTest.calculaTensaoMaxima(forcaTangencial, fatorForma, fatorServico, larguraEngrenagem, moduloRedimensionado);
//		System.out.println("Tensão Máxima Redimensionada: \t\t\t" + myTest.round2Decimal(tensaoMaximaRed) + " MPa");
//		
		
	}
	
	public void normaliza(double moduloEngrenamentoNormalizado, int Z1, double volumeMinimo, double Mt, double fatorForma, double fatorServico) {
		moduloEngrenamentoNormalizado = 2.50;
		System.out.println("Módulo Engrenamento Normalizado: \t\t" + moduloEngrenamentoNormalizado + " mm");
		
		double diametroPrimitivoNormalizado = calculaDiametroPrimitivo(moduloEngrenamentoNormalizado, Z1);
		System.out.println("Diâmetro Primitivo Normalizado: \t\t" + round2Decimal(diametroPrimitivoNormalizado) +" mm");
		
		double larguraEngrenagem = calculaLarguraEngrenagem(volumeMinimo, diametroPrimitivoNormalizado);
		System.out.println("Largura do Pinhão: \t\t\t\t" + round2Decimal(larguraEngrenagem) + " mm");
		
		double forcaTangencial = calculaForcaTangencial(Mt, diametroPrimitivoNormalizado);
		System.out.println("Força Tangencial (Ft): \t\t\t\t" + round2Decimal(forcaTangencial) + " N");
		
		double tensaoMaxima = calculaTensaoMaxima(forcaTangencial, fatorForma, fatorServico, larguraEngrenagem, moduloEngrenamentoNormalizado);
		System.out.println("Tensão Máxima: \t\t\t\t\t" + round2Decimal(tensaoMaxima) + " MPa");
	}
	
	public void redimensionaPelaTensao(double tensaoAdmissivel, double forcaTangencial, double fatorForma, double fatorServico, double moduloEngrenamentoNormalizado, double diametroPrimitivoNormalizado) {
		tensaoAdmissivel = 170;
		double larguraEngrenagemRed = calculaLarguraEngrenagem(forcaTangencial, fatorForma, fatorServico, moduloEngrenamentoNormalizado, tensaoAdmissivel);
		System.out.println("Largura do Pinhão Redimensionado: \t\t" + round2Decimal(larguraEngrenagemRed) + " mm");
		
		double novaRazaoLarguraDiametro = calculaRazaoLarguraDiametroPrimitivo(larguraEngrenagemRed, diametroPrimitivoNormalizado);
		System.out.println("Nova Razão entre Largura e Diâmetro Primitivo: \t" + round2Decimal(novaRazaoLarguraDiametro));
		
	}
	
	public void redimensionaPeloModulo(double moduloRedimensionado, int Z1, double Mt, double forcaTangencial, double fatorForma, double fatorServico, double larguraEngrenagem) {
		moduloRedimensionado = 2.75;
		System.out.println("Módulo Redimensionado: \t\t\t\t" + moduloRedimensionado + " mm");
		
		double diametroPrimitivoRed = calculaDiametroPrimitivo(moduloRedimensionado, Z1);
		System.out.println("Diâmetro Primitivo Redimensionado: \t\t" + round2Decimal(diametroPrimitivoRed) +" mm");
		
		double forcaTangencialRed = calculaForcaTangencial(Mt, diametroPrimitivoRed);
		System.out.println("Força Tangencial Redimensionada (Ft): \t\t" + round2Decimal(forcaTangencialRed) + " N");
		
		double tensaoMaximaRed = calculaTensaoMaxima(forcaTangencial, fatorForma, fatorServico, larguraEngrenagem, moduloRedimensionado);
		System.out.println("Tensão Máxima Redimensionada: \t\t\t" + round2Decimal(tensaoMaximaRed) + " MPa");
		
	}
	
	public double calculaTorquePinhao(double potencia, double n) {
		return 30000 * potencia / (Math.PI * n);
	}
	
	public double calculaRelacaoTransmissao(int Z1, int Z2) {
		return new Double(Z2) / new Double(Z1);
	}
	
	public double calculaFatorDurabilidade(double n, int h) {
		return 60 * n * h / 1000000;
	}
	
	public double calculaFatorDurabilidadeRaiz6(double n, int h) {
		return Math.pow((60 * n * h / 1000000), (1.0/6.0));
	}
	
	public double calculaPressaoAdmissivel(int durezaHB, double W) {
		return 0.487 * durezaHB / W;
	}
	
	public double calculaVolumeMinimoPositivo(double Mt, double pressaoAdmissivel, double i, double fatorServico) {
		return (5.72 * 100000 * (Mt / (Math.pow(pressaoAdmissivel, 2))) * (i + 1.0)/(i + 0.14) * fatorServico) ;
	}
	
	public double calculaDiametroPrimitivo(double volumeMinimo, double relacaoLarguraDiametro) {
		return Math.pow(volumeMinimo / relacaoLarguraDiametro, (1.0/3.0));
	}
	
	public double calculaModuloEngrenamento(double diametroPrimitivo, int Z1) {
		return diametroPrimitivo / Z1;
	}
	
	public double calculaDiametroPrimitivo(double modulo, int dentes) {
		return modulo * dentes;
	}
	
	public double calculaLarguraEngrenagem(double volumeMinimo, double diametroPrimitivo) {
		return volumeMinimo / Math.pow(diametroPrimitivo, 2);
	}
	
	public double calculaForcaTangencial(double torque, double diametroPrimitivo) {
		return 2 * torque / diametroPrimitivo;
	}
	
	public double calculaTensaoMaxima(double forcaTangencial, double fatorForma, double fatorServico, double larguraEngrenagem, double modulo) {
		return forcaTangencial * fatorForma * fatorServico / (larguraEngrenagem * modulo);
	}
	
	public double calculaLarguraEngrenagem(double forcaTangencial, double fatorForma, double fatorServico, double modulo, double tensaoAdmissivel) {
		return forcaTangencial * fatorForma * fatorServico / (modulo * tensaoAdmissivel);
	}
	
	public double calculaRazaoLarguraDiametroPrimitivo(double largura, double diametroPrimitivo) {
		return largura / diametroPrimitivo;
	}
	
	
	private double round2Decimal(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
