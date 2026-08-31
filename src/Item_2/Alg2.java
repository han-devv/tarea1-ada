package Item_2;

import java.util.*;
import resources.Par;

public class Alg2 {
    private Scanner sc = new Scanner(System.in).useDelimiter(("[\\r|\\n]"));
    private Par[] ALG2Arreglo; // Arreglo para almacenar el conjunto C de candidatos

    private void buildS(){
        ArrayList<Par> S = new ArrayList<>();

        System.out.print("¿Desea cargar los datos predeterminados? (S/N): ");
        String option = sc.next().trim();

        if (option.isEmpty() || option.equalsIgnoreCase("S") || option.equalsIgnoreCase("si")) {
            S.add(new Par(0.3f, 7.8f)); // p1
            S.add(new Par(0.8f, 4.2f)); // p2
            S.add(new Par(2.0f, 2.2f)); // p3
            S.add(new Par(4.0f, 0.8f)); // p4
            S.add(new Par(2.5f, 6.0f)); // p5
            S.add(new Par(4.0f, 4.5f)); // p6
            S.add(new Par(5.5f, 3.2f)); // p7
            S.add(new Par(7.0f, 2.0f)); // p8
            S.add(new Par(1.5f, 7.0f)); // p9
            S.add(new Par(6.0f, 5.0f)); // p10
            System.out.println("-> Cargar datos de prueba completado.\n");
        } else {
            String input = "";
            System.out.printf("-%s\n", "Ingrese los pares ordenados del conjunto 'S' separados por ','");

            do {
                System.out.printf("\n-%s", "Para finalizar escriba 'S'");
                System.out.printf("\n-%s", "Ingrese el Par: ");
                input = sc.next();
                if (input.equalsIgnoreCase("S")) continue;

                try {
                    String[] tokens = input.split(",");
                    float x = Float.parseFloat(tokens[0].trim());
                    float y = Float.parseFloat(tokens[1].trim());

                    S.add(new Par(x, y));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Ingrese un valor valido!\n");
                }
            } while (!input.equalsIgnoreCase("S"));
        }

        Par[] P = Pareto2(S);

        System.out.printf("\n-%s\n", "-- Este era el conjunto 'S'");
        for (Par p : S) {
            System.out.printf("\n%s", p.toString());
        }
        System.out.printf("\n%s\n", "--------------");
        System.out.printf("\n-%s\n", "Este es el resultado de Pareto(S) con Alg2");
        for (Par p : P) {
            int numeroPunto = S.indexOf(p) + 1;
            System.out.printf("\np%d = %s", numeroPunto ,p.toString());
        }
    }

    /**
     * Determina si el punto pA domina al punto pB de la misma manera que en Alg1.
     */
    private boolean domina(Par pA, Par pB) {
        boolean mequal = pA.getX() <= pB.getX() && pA.getY() <= pB.getY();
        boolean mstrict = pA.getX() < pB.getX() || pA.getY() < pB.getY();
        return mequal && mstrict;
    }

    /**
     * Algoritmo 2 para calcular Pareto(S).
     * En un conjunto C inicialmente vacío almacenado en ALG2Arreglo se mantienen los candidatos a Pareto(S).
     */
    private Par[] Pareto2(ArrayList<Par> S) {
        // En el peor de los casos, la cantidad de elementos en C es el tamaño de S.
        ALG2Arreglo = new Par[S.size()];
        int sizeC = 0;

        for (Par p : S) {
            boolean pEsDominado = false;
            int k = 0;
            while (k < sizeC) {
                Par q = ALG2Arreglo[k];
                if (domina(q, p)) {
                    // a) si el punto q domina al punto p, entonces descarta a p y revisa el siguiente punto de S
                    pEsDominado = true;
                    break;
                } else if (domina(p, q)) {
                    // b) si el punto p domina el punto q, entonces elimina a q de C y se continúa revisando el siguiente punto de C
                    for (int m = k; m < sizeC - 1; m++) {
                        ALG2Arreglo[m] = ALG2Arreglo[m + 1];
                    }
                    ALG2Arreglo[sizeC - 1] = null;
                    sizeC--;
                    // Al eliminar q de C, el siguiente elemento se desplaza al índice k actual, por lo que no incrementamos k.
                } else {
                    k++;
                }
            }
            if (!pEsDominado) {
                // c) si ningún punto q en C domina a p, entonces p se agrega a C; p puede pertenecer a Pareto(S)
                ALG2Arreglo[sizeC] = p;
                sizeC++;
            }
        }

        // Retornamos una copia exacta del arreglo C con los elementos activos
        Par[] resultado = new Par[sizeC];
        System.arraycopy(ALG2Arreglo, 0, resultado, 0, sizeC);
        return resultado;
    }

    public static void main(String[] args) {
        Alg2 alg2 = new Alg2();
        alg2.buildS();
    }
}
