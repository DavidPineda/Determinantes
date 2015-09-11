package determinantes;

/*
 2 -1 1
 3 1 2
 -1 2 5
 
 -1 1 4
 2 -2 0
 1 2 3
 
 5 0 5 2 3
 0 1 0 2 2
 1 0 5 5 9
 8 0 0 6 9
 7 9 5 6 6
 
 7 1 1 1 4
 7 6 3 7 5
 7 2 5 0 5
 3 3 4 6 0
 9 0 6 7 7
 
 -35 -3 1 -19
 -7 3 -2 -3
 -18 1 3 -12
 9 6 7 7
 
 1 0 1 0 1
 0 1 0 -1 0
 0 0 0 1 -1
 -1 1 0 0 1
 1 0 1 0 0
 
 8 2 0 1 2
 9 4 9 4 8
 1 5 0 2 9
 1 5 3 2 3
 0 6 7 9 1
 
 0 7 6 2 9
 1 0 2 4 4
 0 3 8 7 4
 5 0 4 0 7
 7 1 1 1 4
 
 0 2 1 5 7
 3 6 7 0 0
 2 7 8 7 4
 9 7 2 9 3 
 1 7 2 9 6
 
 0 2 1 5 7
 3 6 7 0 0 F1 - 3F4
 2 7 8 7 4 F2 - 2F4
 9 7 2 9 3 F3 - 9F4 
 1 7 2 9 6
 
 
   2   1    5    7
 -15   1  -27  -18 
  -7   4  -11   -8
 -56 -16  -72  -51
 
   2   1    5    7
 -15   1  -27  -18 F1 - F0
  -7   4  -11   -8 F2 - 4F0
 -56 -16  -72  -51 F3 + 16F0
 
   2	1	   5	   7
 -17    0    -32     -25
 -15    0    -31     -36
 -24    0      8      61
 
 -17  -32  -25
 -15  -31  -36
 -24    8   61 	

 -25   -32   -17
 -36   -31   -15
  61     8   -24
  
  3  2  1
  0  2 -5
 -2  1  4
 
  1  2  1
  0  1  3
 -1  1 -2
  
  3 4 2
 -2 3 5 
  4 2 6
  
  2  2  1  7  2
  5  2  0  9  3
 -1  2  3  2  10
  0  1  8  3  9
  1  2  3  8  2
  
 */

public class DeterminanteRecursiva {

	private int adjunto;
	private long OE;

	public long getOE(){
		return this.OE;
	}
	
	public DeterminanteRecursiva(){
		this.adjunto = 1;
		this.OE = 0;
	}
	
	public double calcularDeterminante(double matriz[][]){
		double determinante = 0;
		switch (matriz[0].length) {
		case 2:
			determinante = derterminanteNivel2(matriz);
			break;
		case 3:
			determinante = derterminanteNivel3(matriz);
			break;			
		default:
			determinante = derterminanteNivelSuperior(matriz);
			break;
		}
		return determinante;
	}
	
	public double derterminanteNivel2(double matriz[][]){
		double determinante = 0;
		determinante = (matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]);
		return determinante;
	}
	
	public double derterminanteNivel3(double matriz[][]){
		double determinante = 0;
		double diagonalPrincipal = matriz[0][0] * matriz[1][1] * matriz[2][2];
		double diagonalInversa = matriz[2][0] * matriz[1][1] * matriz[0][2];
		double triangulo1 = matriz[0][1] * matriz[2][0] * matriz[1][2];
		double triangulo2 = matriz[0][2] * matriz[1][0] * matriz[2][1];
		double triangulo3 = matriz[2][1] * matriz[1][2] * matriz[0][0];
		double triangulo4 = matriz[0][1] * matriz[1][0] * matriz[2][2];
		determinante =  ((diagonalPrincipal + triangulo1 + triangulo2) - (diagonalInversa + triangulo3 + triangulo4)) * adjunto;
		return determinante;		
	}

	public double derterminanteNivelSuperior(double matriz[][]){
		if(matriz != null && matriz[0].length > 3)
			return derterminanteNivelSuperior(organizarMatriz(matriz));
		return derterminanteNivel3(matriz);
	}
	
	public double[][] organizarMatriz(double matriz[][]){
		//int columna = -1, fila = -1, contadorColumna = 0;
		int columna = 0, fila = 0, contadorColumna = 0;
		OE += 3;
		// Se busca la columna por la cual iniciar el proceso de acuerdo a la cantidad de 0
		for(int c = 0; c < matriz[0].length; c++){
			OE += 2;
			int contador = 0;
			for(int f = 0; f < matriz[0].length; f++){
				OE += 3;
				if(matriz[f][c] == 0){
					contador += 1;
					OE += 2;
				}
			}
			if(contador > contadorColumna){
				columna = c;
				contadorColumna = contador;
				OE += 3;
			}
		}
		// Verificar si existe almenos un 1, en la columna seleccionada
		if(columna != -1){
			OE += 1;
			for(int f = 0; f < matriz[0].length; f++){
				OE += 2;
				if(Math.abs(matriz[f][columna]) == 1){
					fila = f;
					OE += 1;
					break;
				}
			}
		}else{
			// Buscar si existe un 
			for(int c = 0; c < matriz[0].length; c++){
				OE += 2;
				if(fila == -1 && columna == -1){
					OE += 2;
					for(int f = 0; f < matriz[0].length; f++){
						OE += 2;
						if(matriz[f][c] == 1 || matriz[f][c] == -1){
							fila = f;
							columna = c;
							OE += 2;
							break;
						}
					}
				}else
					break;
			}
		}
		if(fila != -1 && columna != -1){
			OE += 2;
			// Si se encontro un 1 en la columna se eliminan los 0 de la columna
			matriz = ponerCeros(matriz, fila, columna);
			OE += 2;
			if(columna % 2 == 0){
				this.adjunto *= 1;
			}else{
				this.adjunto *= -1;
			}
			OE += 1;
		}
		return recortarMatriz(matriz, fila, columna);
	}
	
	public double[][] recortarMatriz(double matriz[][], int fila, int columna){
		double matrizRecortada[][] = new double[matriz[0].length - 1][matriz[0].length - 1];
		int auxFila = 0, auxColumna = 0, fil = 0, col = 0;
		// Llenamos la nueva matriz
		while(fil < matriz[0].length){
			while(col < matriz[0].length){
				if(fil != fila){
					if(col != columna){
						try{
							matrizRecortada[auxFila][auxColumna] = matriz[fil][col];
							auxColumna += 1;							
						}catch(Exception ex){
							ex.printStackTrace();							
						}
					}
					if(col == matriz[0].length - 1){
						auxFila += 1;
					}
				}
				col += 1;
			}
			col = 0;
			auxColumna = 0;
			fil += 1;
		}
		return matrizRecortada;
	}
	
	public double[][] ponerCeros(double matriz[][], int fila, int columna){
		double valorInicial = matriz[fila][columna];
		OE += 1;
		double filaAlterada[] = new double[matriz[0].length];
		OE += 1;
		for(int f = 0; f < matriz[0].length; f++){
			OE += 2;
			if(f != fila && matriz[f][columna] != 0){
				OE += 2;
				if(matriz[f][columna] < valorInicial){
					// Se realiza suma de columnas
					filaAlterada = sumarOrestar(matriz[f], matriz[fila], matriz[f][columna], true);
				}else{					
					// Se realizan restas de columnas
					filaAlterada = sumarOrestar(matriz[f], matriz[fila], matriz[f][columna], false);
				}
				OE += 1;
				for(int i = 0; i < filaAlterada.length; i++){
					OE += 2;
					matriz[f][i] = filaAlterada[i];
					OE += 1;
				}
			}else{
				continue;
			}
		}
		return matriz;
	}
	
	public double[] sumarOrestar(double filaAlterar[], double filaInicial[], double Valor_linea, boolean sum_res){
		double filaAlterada[] = new double[filaAlterar.length];
		if(sum_res){
			for(int i = 0; i < filaAlterar.length; i++){
				OE += 2;
				filaAlterada[i] = (filaAlterar[i]) + ((filaInicial[i] * Math.abs(Valor_linea)));
			}			
		}else{
			for(int i = 0; i < filaAlterar.length; i++){
				OE += 2;
				filaAlterada[i] = (filaAlterar[i]) - ((filaInicial[i] * Math.abs(Valor_linea)));
			}
		}
		OE += 1;
		return filaAlterada;
	}
	
}
