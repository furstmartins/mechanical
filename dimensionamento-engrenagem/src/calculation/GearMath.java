package calculation;
/*
   * Copyright (c) 2018, Rodolfo Martins. All rights reserved.
   * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
   *
   * This code is free code; you can redistribute it and/or modify it
   * under the terms of the GNU General Public License version 2 only, as
   * published by the Free Software Foundation.
   *
   * This code is distributed in the hope that it will be useful, but WITHOUT
   * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
   * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
   * version 2 for more details.
   *
   * Please contact me in twitter @furstmartins if you need additional
   * information or have any questions.
   *
   * @version 1.0
   */
public class GearMath {

    /**
     * Returns the Gear's Module
     *
     * @param  diameter  a double value of the diameter of gear
     * @param  teeth the number of teeth of the gear
     * @return  a double value
     */
    public double getModule(double diameter, int teeth) {
        return diameter / teeth;
    }

    /**
     * Returns the Gear's Module
     *
     * @param  drivenTeeth  the number of driven teeth
     * @param  drivingTeeth the number of driving teeth
     * @return  a double value
     */
//    public double getModule(int drivenTeeth, int drivingTeeth) {
//        return drivingTeeth / drivenTeeth;
//    }

    /**
     * Returns the Gear's Module
     *
     * @param  primitiveRadius1 the primitive radius of driven
     * @param  primitiveRadius2 the primitive radius of driving
     * @return  a double value
     */
//    public double getModule(double primitiveRadius1, double primitiveRadius2) {
//        return primitiveRadius2 / primitiveRadius1;
//    }

    /**
     * Returns the Addendum
     *
     * @param diameter the gear's diameter
     * @param teeth the gear's teeth
     * @return a double value
     */
    public double getAddendum(double diameter, int teeth) {
        return getModule(diameter, teeth) * 1;
    }

    /**
     * Returns the Addendum
     *
     * @param  primitiveRadius1 the primitive radius of driven
     * @param  primitiveRadius2 the primitive radius of driving
     * @return a double value
     */
//    public double getAddendum(double primitiveRadius1, double primitiveRadius2) {
//        return getModule(primitiveRadius1, primitiveRadius2) * 1;
//    }

    /**
     * Returns the Dedendum
     *
     * @param module the gear's module
     * @return a double value
     */
    public double getDedendum(double module) {
        return 1.25 * module;
    }

    /**
     * Returns the Dedendum
     *
     * @param drivenTeeth the number of driven teeth
     * @param drivingTeeth the number of driving teeth
     * @return a double value
     */
    public double getDedendum(int drivenTeeth, int drivingTeeth) {
        return 1.25 * getModule(drivenTeeth, drivingTeeth);
    }

    /**
     * Returns the Gear's Diameter
     *
     * @param  module  a double value of the module about gears
     * @param  teeth the number of teeth
     * @return  a double value
     */
    public double getDiameter(double module, int teeth) {
        return module * teeth;
    }

    /**
     * Retorns the Gear's Primitive Radius
     *
     * @param module
     * @param teeth
     *
     * @return a double value
     * */
    public double getPrimitiveRadius(double module, int teeth) {
        return module * teeth / 2;
    }

    public double getRb(double raio, double anguloPressao) {
        return raio * Math.cos(getAnguloEmRadianos(anguloPressao));
    }

    public double getRo(double raio, double adendo) {
        return raio + adendo;
    }

    public double getCentros(double raioPrimitivo1, double raioPrimitivo2) {
        return raioPrimitivo1 + raioPrimitivo2;
    }

    public double getComprimentoAcao(double ro1, double rb1, double ro2, double rb2, double C, double anguloPressao) {
        return Math.sqrt(Math.pow(ro1, 2) - Math.pow(rb1, 2)) + Math.sqrt(Math.pow(ro2, 2) - Math.pow(rb2, 2))
                - C * Math.sin(getAnguloEmRadianos(anguloPressao));
    }

    public double getPb(double Rb, int numeroDentes) {
        return 2 * Math.PI * Rb / numeroDentes;
    }

    public double getRazaoContato(double Z, double Pb) {
        return Z / Pb;
    }

    public boolean getValidaRazaoContato(double razaoContato) {
        return razaoContato > 1.4;
    }

    public String getValidaRazaoContatoString(double razaoContato) {
        if (getValidaRazaoContato(razaoContato)) {
            return "OK";
        } else {
            return "NOK";
        }
    }

    public double razaoTransmissaoPorDentes(int numeroDentesEntrada, int numeroDentesSaida) {
        return numeroDentesSaida / numeroDentesEntrada;
    }

    public double getTransmissionRatioByTeeth(int drivenTeeth, int drivingTeeth) {
        return Double.valueOf(drivingTeeth) / Double.valueOf(drivenTeeth);
    }

    public double razaoTransmissaoPorTorque(double torqueEntrada, double torqueSaida) {
        return torqueSaida / torqueEntrada;
    }

    public double razaoTransmissaoPorDiametro(double diametroEntrada, double diametroSaida) {
        return diametroSaida / diametroEntrada;
    }

    public double getTransmissionByDiameter(double drivenDiameter, double drivingDiameter) {
        return drivingDiameter / drivenDiameter;
    }

    public double razaoTransmissaoPorVelocidaAngular (double velocidadeAngularEntrada, double velocidadeAngularSaida) {
        return velocidadeAngularEntrada / velocidadeAngularSaida;
    }

    public double passo(double modulo) {
        return Math.PI * modulo;
    }

    public double passo(double diametro, int numeroDentes) {
        return Math.PI * getModule(diametro, numeroDentes);
    }

    public double getEspessura (double passo) {
        return passo / 2;
    }

    public double getEspessura (double diametro, int numeroDentes) {
        return passo(diametro, numeroDentes) / 2;
    }

    public double getCentrosFolga(double centros, double folga) {
        return centros + folga;
    }

    public double getCentrosFolga(double raioPrimitivo1, double raioPrimitivo2, double folga) {
        return getCentros(raioPrimitivo1, raioPrimitivo2) + folga;
    }

    public double getAnguloPressaoFolga(double centros, double anguloPressao, double centrosFolga) {
        return Math.acos(centros * Math.cos(getAnguloEmRadianos(anguloPressao)) /
                centrosFolga) * 180 / Math.PI;
    }

    public double getAnguloPressaoFolga(double raioPrimitivo1, double raioPrimitivo2, double folga, double anguloPressao) {
        return Math.acos(getCentros(raioPrimitivo1, raioPrimitivo2) * Math.cos(getAnguloEmRadianos(anguloPressao)) /
                getCentrosFolga(raioPrimitivo1, raioPrimitivo2, folga)) * 180 / Math.PI;
    }

    public double getRaioPrimitivoFolgaPinhao(double numeroDentesPinhao, double numeroDentesCoroa, double centrosFolga) {
        return numeroDentesPinhao * centrosFolga / (numeroDentesPinhao + numeroDentesCoroa);
    }

    public double getRaioPrimitivoFolgaCoroa(double numeroDentesPinhao, double numeroDentesCoroa, double centrosFolga) {
        return numeroDentesCoroa * centrosFolga / (numeroDentesPinhao + numeroDentesCoroa);
    }

    public double getFolga(double centrosFolga, double anguloPressao, double anguloPressaoFolga) {
        return 2 * centrosFolga * ((Math.tan(getAnguloEmRadianos(anguloPressaoFolga)) - getAnguloEmRadianos(anguloPressaoFolga))
        - (Math.tan(getAnguloEmRadianos(anguloPressao)) - getAnguloEmRadianos(anguloPressao)));
    }

    public int getTeeth(double module, double diameter) {
        Double teeth = diameter / module;
        return teeth.intValue();
    }
	
	public double getRadium(double module, int teeth) {
		return module * teeth / 2;
	}

    private double getAnguloEmRadianos(double angulo) {
        return angulo * Math.PI / 180;
    }
}
