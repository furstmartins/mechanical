package calculation;

public class GeometricGearCalculation {

	public double passo(double modulo) {
		return Math.PI * modulo;
	}
	
	public double vaoEntreDentes(double passo) {
		return passo / 2.0;
	}
	
	public double alturaCabeca(double modulo) {
		return modulo;
	}
	
	public double alturaPe(double modulo) {
		return 1.2 * modulo;
	}
	
	public double alturaComum(double modulo) {
		return 2.0 * modulo;
	}
	
	public double alturaTotal(double modulo) {
		return 2.2 * modulo;
	}
	
	public double espessuraDente(double passo) {
		return passo / 2.0;
	}
	
	public double folgaCabeca(double modulo) {
		return 0.2 * modulo;
	}
	
	public double diametroPrimitivo(double modulo, int Z) {
		return modulo * Z;
	}
	
	public double diametroBase(double diametroPrimitivo, double angulo) {
		return diametroPrimitivo * Math.cos(angulo * Math.PI / 180);
	}
	
	public double diametroInterno(double diametroPrimitivo, double modulo) {
		return diametroPrimitivo - 2.4 * modulo;
	}
	
	public double diametroExterno(double diametroPrimitivo, double modulo) {
		return diametroPrimitivo + 2 * modulo;
	}
	
	public double distanciaEntreCentros(double diametroPrimitivoZ1, double diametroPrimitivoZ2) {
		return (diametroPrimitivoZ1 + diametroPrimitivoZ2) / 2;
	}
}
