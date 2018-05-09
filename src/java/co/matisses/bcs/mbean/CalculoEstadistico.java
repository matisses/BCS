package co.matisses.bcs.mbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ygil
 */
public class CalculoEstadistico {

    private Float q1;
    private Float q2;
    private Float q3;
    private Float RIC;
    private Float max;
    private Float min;
    private Float bigoteInferior;
    private Float bigoteSuperior;
    private Float promedioInterno;
    private Float promedioLimites;
    private List<Float> valores;
    private List<Float> atipicosInferiores;
    private List<Float> atipicosSuperiores;
    private List<Float> valoresSimetricos;

    public CalculoEstadistico() {
        q1 = 0F;
        q2 = 0F;
        q3 = 0F;
        RIC = 0F;
        max = 0F;
        min = 0F;
        bigoteInferior = 0F;
        bigoteSuperior = 0F;
        promedioInterno = 0F;
        promedioLimites = 0F;
        valores = new ArrayList<>();
        atipicosInferiores = new ArrayList<>();
        atipicosSuperiores = new ArrayList<>();
        valoresSimetricos = new ArrayList<>();
    }

    public CalculoEstadistico(List<Float> valores) {
        q1 = 0F;
        q2 = 0F;
        q3 = 0F;
        RIC = 0F;
        max = 0F;
        min = 0F;
        bigoteInferior = 0F;
        bigoteSuperior = 0F;
        promedioInterno = 0F;
        promedioLimites = 0F;
        this.valores = valores;
        atipicosInferiores = new ArrayList<>();
        atipicosSuperiores = new ArrayList<>();
        valoresSimetricos = new ArrayList<>();
    }

    public Float getQ1() {
        return q1;
    }

    public Float getQ2() {
        return q2;
    }

    public Float getQ3() {
        return q3;
    }

    public Float getRIC() {
        return RIC;
    }

    public Float getMax() {
        return max;
    }

    public Float getMin() {
        return min;
    }

    public Float getBigoteInferior() {
        return bigoteInferior;
    }

    public Float getBigoteSuperior() {
        return bigoteSuperior;
    }

    public Float getPromedioInterno() {
        return promedioInterno;
    }

    public Float getPromedioLimites() {
        return promedioLimites;
    }

    public List<Float> getValores() {
        return valores;
    }

    public List<Float> getAtipicosInferiores() {
        return atipicosInferiores;
    }

    public List<Float> getAtipicosSuperiores() {
        return atipicosSuperiores;
    }

    public List<Float> getValoresSimetricos() {
        return valoresSimetricos;
    }

    public void agregarValor(Float d) {
        valores.add(d);
    }

    private void calcularMediana() {
        if (valores.isEmpty()) {
            return;
        }

        List<Float> duplicados = new ArrayList<>(valores);
        Collections.sort(duplicados);

        //Si es numero de items par, hay que calcular la mediana, si es impar, se selecciona el item de la mitad del arreglo
        if (duplicados.size() % 2 == 0) {
            int pos1 = (duplicados.size() / 2) - 1;
            int pos2 = (duplicados.size() / 2);
            Float val1 = duplicados.get(pos1);
            Float val2 = duplicados.get(pos2);

            q2 = (val1 + val2) / 2;
        } else {
            int pos = ((duplicados.size() - 1) / 2);

            q2 = duplicados.get(pos);
        }
    }

    private void calcularQ1() {
        if (valores.isEmpty()) {
            return;
        }

        //Obtiene un duplicado de la lista de valores, la ordena y extrae la primera mitad
        List<Float> duplicados = new ArrayList<>(valores);
        Collections.sort(duplicados);

        if (valores.size() > 1) {
            duplicados = duplicados.subList(0, (duplicados.size() / 2));
        }

        //Si es numero de items par, hay que calcular la mediana, si es impar, se selecciona el item de la mitad del arreglo
        if (duplicados.size() % 2 == 0) {
            int pos1 = (duplicados.size() / 2) - 1;
            int pos2 = (duplicados.size() / 2);
            Float val1 = duplicados.get(pos1);
            Float val2 = duplicados.get(pos2);

            q1 = (val1 + val2) / 2;
        } else {
            int pos = ((duplicados.size() - 1) / 2);

            q1 = duplicados.get(pos);
        }
    }

    private void calcularQ2() {
        calcularMediana();
    }

    private void calcularQ3() {
        if (valores.isEmpty()) {
            return;
        }

        //Obtiene un duplicado de la lista de valores, lo ordena y extrae la segunda mitad
        List<Float> duplicados = new ArrayList<>(valores);
        Collections.sort(duplicados);

        if (duplicados.size() > 1) {
            if (duplicados.size() % 2 == 0) {
                duplicados = duplicados.subList((duplicados.size() / 2), duplicados.size());
            } else {
                duplicados = duplicados.subList((duplicados.size() / 2) + 1, duplicados.size());
            }
        }

        //Si es numero de items par, hay que calcular la mediana, si es impar, se selecciona el item de la mitad del arreglo
        if (duplicados.size() % 2 == 0) {
            int pos1 = (duplicados.size() / 2) - 1;
            int pos2 = (duplicados.size() / 2);
            Float val1 = duplicados.get(pos1);
            Float val2 = duplicados.get(pos2);

            q3 = (val1 + val2) / 2;
        } else {
            int pos = ((duplicados.size() - 1) / 2);

            q3 = duplicados.get(pos);
        }
    }

    private void calcularRIC() {
        if (valores.isEmpty()) {
            return;
        }

        RIC = q3 - q1;
    }

    private void calcularMin() {
        if (valores.isEmpty()) {
            return;
        }

        min = Collections.min(valores);
    }

    private void calcularMax() {
        if (valores.isEmpty()) {
            return;
        }

        max = Collections.max(valores);
    }

    private void obtenerAtipicosInferiores() {
        if (valores.isEmpty()) {
            return;
        }

        atipicosInferiores = new ArrayList<>();
        List<Float> duplicados = new ArrayList<>(valores);
        Collections.sort(duplicados);

        bigoteInferior = (q1 - (RIC * 1.5F)) > min ? (q1 - (RIC * 1.5F)) : min;

        for (int i = 0; i < duplicados.size(); i++) {
            Float valor = duplicados.get(i);

            if (valor < bigoteInferior) {
                atipicosInferiores.add(valor);
            }
        }
    }

    private void obtenerAtipicosSuperior() {
        if (valores.isEmpty()) {
            return;
        }

        atipicosSuperiores = new ArrayList<>();
        List<Float> duplicados = new ArrayList<>(valores);
        Collections.sort(duplicados);

        bigoteSuperior = (q3 + (RIC * 1.5F)) < max ? (q3 + (RIC * 1.5F)) : max;

        for (int i = 0; i < duplicados.size(); i++) {
            Float valor = duplicados.get(i);

            if (valor > bigoteSuperior) {
                atipicosSuperiores.add(valor);
            }
        }
    }

    private void obtenerValoresSimetricos() {
        if (valores.isEmpty()) {
            return;
        }

        valoresSimetricos = new ArrayList<>();
        List<Float> duplicados = new ArrayList<>(valores);
        Collections.sort(duplicados);

        for (int i = 0; i < duplicados.size(); i++) {
            Float valor = duplicados.get(i);

            if (valor >= q1 && valor <= q3) {
                valoresSimetricos.add(valor);
            }
        }
    }

    private void calcularPromedioInterno() {
        if (valores.isEmpty()) {
            return;
        }

        Float acumulado = 0F;
        int elementos = 0;

        for (Float valor : valoresSimetricos) {
            if (valor >= q1 && valor <= q3) {
                acumulado += valor;
                elementos++;
            }
        }

        if (elementos == 0) {
            promedioInterno = 0F;
        } else {
            promedioInterno = acumulado / elementos;
        }
    }

    private void calcularPromedioLimites() {
        if (valores.isEmpty()) {
            return;
        }

        Float acumulado = 0F;
        int elementos = 0;

        for (Float valor : valoresSimetricos) {
            if (valor >= min && valor <= max) {
                acumulado += valor;
                elementos++;
            }
        }

        if (elementos == 0) {
            promedioLimites = 0F;
        } else {
            promedioLimites = acumulado / elementos;
        }
    }

    public void calcularValoresBoxPlot() {
        calcularQ1();
        calcularQ2();
        calcularQ3();
        calcularRIC();
        calcularMin();
        calcularMax();
        obtenerAtipicosInferiores();
        obtenerAtipicosSuperior();
        obtenerValoresSimetricos();
        calcularPromedioInterno();
        calcularPromedioLimites();
    }
//    public static void main(String[] args) {
//        CalculoEstadistico calculo = new CalculoEstadistico();
//
//        calculo.agregarValor(62.32f);
//        calculo.agregarValor(62.44f);
//        calculo.agregarValor(67.03f);
//        calculo.agregarValor(67.03f);
//        calculo.agregarValor(66.03f);
//        calculo.agregarValor(16.33f);
//
//        calculo.calcularValoresBoxPlot();
//
//        System.out.println("Q1: " + calculo.getQ1());
//        System.out.println("Q2: " + calculo.getQ2());
//        System.out.println("Q3: " + calculo.getQ3());
//        System.out.println("RIC: " + calculo.getRIC());
//        System.out.println("Bigote Inf: " + calculo.getBigoteInferior());
//        System.out.println("Bigote Sup: " + calculo.getBigoteSuperior());
//        System.out.println("Max: " + calculo.getMax());
//        System.out.println("Min: " + calculo.getMin());
//
//        StringBuilder sb = new StringBuilder();
//        for (Float d : calculo.getAtipicosInferiores()) {
//            sb.append(d);
//            sb.append(",");
//        }
//        System.out.println("Atipicos Inf: " + sb.toString());
//
//        sb = new StringBuilder();
//        for (Float d : calculo.getValoresSimetricos()) {
//            sb.append(d);
//            sb.append(",");
//        }
//        System.out.println("Valores simetricos: " + sb.toString());
//
//        sb = new StringBuilder();
//        for (Float d : calculo.getAtipicosSuperiores()) {
//            sb.append(d);
//            sb.append(",");
//        }
//        System.out.println("Atipicos Sup: " + sb.toString());
//        System.out.println("Promedio Interno: " + calculo.getPromedioInterno());
//        System.out.println("Promedio Limites: " + calculo.getPromedioLimites());
//    }
}
