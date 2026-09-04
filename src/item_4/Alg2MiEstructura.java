package item_4;

import resources.Par;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Alg2MiEstructura {
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
     * Mismo funcionamiento del Alg2, pero reemplazado la estructura de datos de C, de un arraylist
     * a una LinkedList. Al eliminar un elemento de C, este debe de reordenar cada elemento en el.
     * Al utilizar una LinkedList nos ahorramos este costo innecesario para hacerlo en 0(1).
     * @param S
     * @return
     */
    public Par[] Pareto2(ArrayList<Par> S) {
        // Utilizamos LinkedList en lugar de un arreglo primitivo
        LinkedList<Par> C = new LinkedList<>();

        for (Par p : S) {
            boolean pEsDominado = false;

            // Usamos un ListIterator para recorrer y eliminar de forma segura y en O(1)
            ListIterator<Par> iter = C.listIterator();

            while (iter.hasNext()) {
                Par q = iter.next();

                if (domina(q, p)) {
                    // a) Si q domina a p, descartamos p
                    pEsDominado = true;
                    break;
                } else if (domina(p, q)) {
                    // b) Si p domina a q, eliminamos q.
                    // LinkedList hace esto en O(1) ¡Sin desplazar elementos!
                    iter.remove();
                }
            }

            if (!pEsDominado) {
                // c) p se agrega a C
                C.add(p);
            }
        }
        // Convertimos la lista resultante a un arreglo para retornarlo
        return C.toArray(new Par[0]);
    }

    public static void main(String[] args) {
        Alg2MiEstructura alg2 = new Alg2MiEstructura();
        alg2.buildS();
    }
}
