package Item_1;

import java.util.*;
import resources.Par;
/**
 * Algoritmo para obtener el Pareto(S), en un tiempo ordinario [O(n^2)]
 * <p>--Han
 */

public class Alg1 {
    Scanner sc = new Scanner(System.in).useDelimiter(("[\\r|\\n]"));

    /**
     * Un armado simple del conjunto S.
     * Se crea un Arraylist de clase "Par" (Descrita en src/resources) para guardar los pares ordenados del conjunto.
     */
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
        }else {

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
        ArrayList<Par> P = Pareto(S);

            System.out.printf("\n-%s\n", "-- Este era el conjunto 'S'");
            for (Par p : S) {
                System.out.printf("\n%s", p.toString());
            }
            System.out.printf("\n%s\n", "--------------");
            System.out.printf("\n-%s\n", "Este es el resultado de Pareto(S)");
            for (Par p : P) {
                int numeroPunto = S.indexOf(p) + 1;
                System.out.printf("\np%d = %s", numeroPunto ,p.toString());
            }

    }

    /**
     * Para esta función Pareto(S) se compara cada dato con los demas datos del arreglo, si el dato pertenece o no, se monitorea con la variable 'flag'.
     * Si al menos una de las variables de p2 (dato de comparación) son menor o igual que p1 (dato analizado), se considera la variable 'mequal'. Por otro lado si tambíen se encuentra un valor de p2 estrictamente mayor que p1
     * se considera la variable 'mstrict'. Cuando ambas variables son true, se entiende que p2 domina a p1, por lo que este se descarta.
     *
     * <p>El orden de este algoritmo es O(n^2), denotado por los 2 ciclos 'for' anidados.
     * @param S
     */
    private ArrayList<Par> Pareto(ArrayList<Par> S){
        ArrayList<Par> P = new ArrayList<>();
        boolean flag;
        for (int i = 0; i < S.size(); i++) {
            Par p1 = S.get(i);
            flag = true;
            for (int j = 0; j < S.size(); j++) {
                if(i==j) continue;
                Par p2 = S.get(j);

                boolean mequal = false;
                boolean mstrict = false;

                if (p2.getX() <= p1.getX() && p2.getY() <= p1.getY()) mequal = true;
                if(p2.getX() < p1.getX() || p2.getY() < p1.getY()) mstrict = true;

                if (mequal && mstrict){ flag = false; break;}

            }
            if(flag) P.add(S.get(i));
        }
        return P;
    }

    public static void main(String[] args) {
        Alg1 alg1 = new Alg1();
        alg1.buildS();
    }
}
