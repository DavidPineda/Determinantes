package determinantes;

/*

 1 7 6 1 9
 7 0 2 4 4
 0 3 8 7 4
 0 0 4 0 7
 3 1 3 1 4

 1  2  3
 2  2  1
 -1  2  3

 = 8
 
 1  2  3
 1  1 -1
 2  0  5
 
 = -15 -- Errado
 
 2  3  3  6
 2  3  6  7
 21 82  0  3
 2 23  1  1
 
 = -116
 
 3  2  1
 0  2 -5
 -2  1  4

 = 63

 1  0  0  1
 1  2  3  2
 2  3  4  0
 1  2 -1 -2
 
 = -24
 
 1  1  3 -2
 2 -4  7  2
 3 -2  9 -1
 1  3 -1 -1

 = 15 -- Errado

 1  1  1
 1  5 25
 1 20 400

 56.5  1   1
 113  5  25   
 181 20 400

 1  56.5   1
 1   113  25
 1   181 400


 */
import java.util.Scanner;

public class Main {

    private DeterminanteRecursiva d1;
    private DeterminanteGaus d2;
    Scanner scanner;

    public static void main(String args[]) {
        Main m = new Main();
        m.scanner = new Scanner(System.in);
        double matriz[][] = m.cargarMatriz();
        if (matriz != null) {
            m.menu(matriz);
        } else {
            System.out.println("La matriz no pudo ser cargada.");
        }
        m.scanner.close();
    }

    private double[][] cargarMatriz() {
        System.out.println("Digite el tamaño n de la matriz: \n");
        String auxNum = scanner.next();
        if (auxNum.matches("[0-9]*")) {
            int tam = Integer.parseInt(auxNum);
            if (tam > 1) {
                double matriz[][] = new double[tam][tam];
                System.out.println("Digite los datos de la matriz \n");
                try {
                    for (int f = 0; f < tam; f++) {
                        for (int c = 0; c < tam; c++) {
                            //matriz[f][c] = Double.parseDouble(scanner.next("(-)?[0-9]*"));
                            matriz[f][c] = Double.parseDouble(scanner.next());
                        }
                    }
                    return matriz;
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } else {
                System.out.println("El valor digitado no es correcto");
            }
        } else {
            System.out.println("El valor digitado debe ser un número entero");
        }
        return null;
    }

    private void ejecutarMatrizRecursiva(double[][] matriz) {
        d1 = new DeterminanteRecursiva();
        try {
            System.out.println("La determinante por recursividad es: "
                    + d1.calcularDeterminante(matriz) + " Contador "
                    + d1.getOE());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out
                    .println("Ocurrio un error al validar la matriz solicitada");
        }
    }

    private void ejecutarMatrizGaus(double[][] matriz) {
        d2 = new DeterminanteGaus();
        try {
            System.out.println("La determinante por Gauss Jordan es: "
                    + d2.solucionar(matriz, 0));
        } catch (Exception ex) {
            System.out
                    .println("Ocurrio un error al validar la matriz solicitada");
        }
    }

    private void menu(double[][] matriz) {
        System.out
                .println("Seleccione el metodo con el que va a solucionar la matriz : \n");
        System.out.println("1)  Recurusivo");
        System.out.println("2)  Eliminacion Gauss Jordan");
        System.out.println("3)  Ambos");
        String auxNum = scanner.next();
        if (auxNum.matches("[0-9]*")) {
            switch (Integer.parseInt(auxNum)) {
                case 1:
                    ejecutarMatrizRecursiva(matriz);
                    break;
                case 2:
                    ejecutarMatrizGaus(matriz);
                    break;
                case 3:
                    ejecutarMatrizRecursiva(copiarMatriz(matriz));
                    ejecutarMatrizGaus(copiarMatriz(matriz));
                    break;
                default:
                    menu(matriz);
            }
        } else {
            menu(matriz);
        }

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

    private double[][] copiarMatriz(double[][] matriz) {
        double[][] newMatriz = new double[matriz.length][matriz.length];
        for (int f = 0; f < newMatriz[0].length; f++) {
            for (int c = 0; c < newMatriz.length; c++) {
                newMatriz[f][c] = matriz[f][c];
            }
        }
        return newMatriz;
    }

}
