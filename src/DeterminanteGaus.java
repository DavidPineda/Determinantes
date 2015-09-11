package determinantes;

import java.util.ArrayList;
import java.util.Arrays;

public class DeterminanteGaus {

	private ArrayList<Double> arrayDeterminate;
	private int signoGlobal = 1;

	public DeterminanteGaus() {
		this.arrayDeterminate = new ArrayList<Double>();
	}

	public double solucionar(double[][] matriz, int fil) {
		pintarMatriz(matriz);
		if (fil < matriz[0].length - 1) {
			switch (validarValor(matriz[fil][fil])) {
			case 0:
				// permutar la matriz
				solucionar(permutar(matriz, fil), fil + 1);
				break;
			case 1:
				// convertir en ceros
				solucionar(convertirCeros(matriz, fil), fil + 1);
				break;
			default:
				// se debe colocar en 1 la fila
				solucionar(transformarMatriz(matriz, fil), fil);
				break;
			}
		}
		return hallarValorDete(matriz);
	}

	private int validarValor(double valPosicion) {

		if (valPosicion == 0) {
			return 0;
		} else if (valPosicion == 1 || valPosicion == -1) {
			return 1;
		} else {
			return -1;
		}

	}

	private double[][] convertirCeros(double matriz[][], int fil) {

		double nuevaFila[];
		for (int filAux = fil + 1; filAux < matriz.length; filAux++) {
			nuevaFila = sumaLogica(matriz, matriz[filAux][fil], filAux,
					matriz[(fil)][fil], fil);
			matriz[filAux] = nuevaFila;
		}
		return matriz;

	}

	/*
	 * Si ambos son negativos se suman Si ambos son positivos se resta Si
	 * multiplicador es positivo y numeroPrincipal es negativo o viceversa se
	 * restan
	 */

	/**
	 *
	 * @param matriz
	 *            Matriz original sobre la cual se va a operar
	 * @param multiplicador
	 *            Es el numero por el cual se debe multiplicar la fila operador
	 *            para que de cero
	 * @param fil
	 *            Es la fila actual que se esta trabajando para conseguir el
	 *            cero
	 * @param numeroPrincipal
	 *            Es el numero por el cual se debe multiplicar el multiplicador,
	 *            nunca varia hasta que cambie columna
	 * @param FilaOperador
	 *            Es la fila por la que siempre de debe multiplicar el
	 *            "Multiplicador"
	 * @return
	 */
	private double[] sumaLogica(double matriz[][], double multiplicador,
			int fil, double numeroPrincipal, int FilaOperador) {

		if(multiplicador > 0 && numeroPrincipal > 0){
			return multiplicarFila(matriz, fil, multiplicador, FilaOperador, false, false);
		}else if(multiplicador < 0 && numeroPrincipal < 0){
			return multiplicarFila(matriz, fil, multiplicador, FilaOperador, true, false);
		}else if(multiplicador < 0 && numeroPrincipal > 0){
			return multiplicarFila(matriz, fil, multiplicador, FilaOperador, false, true);
		}else{
			return multiplicarFila(matriz, fil, multiplicador, FilaOperador, true, true);
		}		
	}

	private double[] multiplicarFila(double matriz[][], int fil,
			double multiplicador, int FilaOperador, boolean estadoMult, boolean estadoSum) {

		boolean multiplicar = true;

		if (Math.abs(matriz[FilaOperador][FilaOperador]) == Math
				.abs(multiplicador))
			multiplicar = false;
		double nuevaFilOperador[] = matriz[fil];
		if (multiplicador == 0)
			return nuevaFilOperador;
		for (int c = 0; c < nuevaFilOperador.length; c++) {
			if (multiplicar) {
				nuevaFilOperador[c] = estadoMult ? (multiplicador * matriz[FilaOperador][c])
						+ matriz[fil][c]
						: (multiplicador * matriz[FilaOperador][c])
								- matriz[fil][c];
			} else {
				nuevaFilOperador[c] = estadoSum ? matriz[FilaOperador][c]
						+ matriz[fil][c] : matriz[FilaOperador][c]
						- matriz[fil][c];						
			}
		}
		return nuevaFilOperador;
	}

	private void pintarMatriz(double[][] matriz) {
		System.out.println("\n");
		for (int f = 0; f < matriz.length; f++) {
			for (int c = 0; c < matriz[0].length; c++) {
				System.out.print(matriz[f][c] + " ");
			}
			System.out.println("\n");
		}
	}

	private double[][] permutarMatrizXFila(double matriz[][], int fil, int col,
			double buscar) {
		for (int i = (fil + 1); i < matriz.length; i++) {
			if (Math.abs(matriz[i][col]) == buscar) {
				double arrayAuxiliar[] = matriz[fil];
				matriz[fil] = matriz[i];
				matriz[i] = arrayAuxiliar;
				arrayDeterminate.add((double)(signoGlobal * -1));
				break;
			}
		}
		return matriz;
	}

	private double[][] permutaMatrizXColomna(double matriz[][], int fil,
			int col, double buscar) {
		for (int i = (col + 1); i < matriz[0].length; i++) {
			if (Math.abs(matriz[fil][i]) == buscar) {
				double auxNum;
				for (int j = 0; j < matriz[0].length; j++) {
					auxNum = matriz[j][col];
					matriz[j][col] = matriz[j][i];
					matriz[j][i] = auxNum;
				}
				arrayDeterminate.add((double) -1);
				break;
			}
		}
		return matriz;
	}

	/**
	 * Metodo encargado de transformar una fila de la matriz para convertir un
	 * numero en 1 por otro lado aun no se prueba el metodo para la multiplicar
	 * debido que se tiene que dividir la matriz
	 *
	 * @param matriz
	 * @param fil
	 * @param operador
	 * @param multiplicador
	 * @return
	 */
	private double[][] transformarMatriz(double matriz[][], int fil) {

		//double multiplicador = Math.abs(matriz[fil][fil]);
		double multiplicador = matriz[fil][fil];
		for (int c = fil; c < matriz[0].length; c++) {
			matriz[fil][c] = (matriz[fil][c] / multiplicador);
		}
		arrayDeterminate.add(multiplicador);
		return matriz;
	}

	private double[] columnaToArrary(double matriz[][], int col) {

		double[] arrayAux = matriz[0];

		for (int f = 0; f < matriz.length; f++) {
			arrayAux[f] = matriz[f][col];
		}
		return arrayAux;
	}

	private double menorVarlorArray(double arrary[], int fil) {
		Arrays.sort(arrary);
		int index = fil + 1;
		while (index < arrary.length) {
			if (arrary[index] != 0)
				return arrary[index];
			index++;
		}
		return 0;
	}

	private double[][] permutar(double matriz[][], int fil) {

		double valorPermutarXFila = menorVarlorArray(matriz[fil], fil);
		double valorPermutarXCol = menorVarlorArray(
				columnaToArrary(matriz, fil), fil);

		if (valorPermutarXFila == 0 && valorPermutarXCol == 0) {
			System.exit(0);
		}

		if (valorPermutarXFila < valorPermutarXCol)
			return permutarMatrizXFila(matriz, fil, fil, valorPermutarXFila);
		return permutaMatrizXColomna(matriz, fil, fil, valorPermutarXCol);
	}

	private double hallarValorDete(double matriz[][]) {

		double valorDet = 1;
		int i = 0;

		for (int c = 0; c < (matriz[0].length); c++) {
			valorDet *= matriz[c][c];
		}

		if (arrayDeterminate.size() > 0) {
			while (i < arrayDeterminate.size()) {
				valorDet *= arrayDeterminate.get(i);
				i++;
			}
		}
		return valorDet;
	}

}
