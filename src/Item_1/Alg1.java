package Item_1;

import java.util.*;
import resources.Par;
/**
 * Algoritmo para obtener el Pareto(S), en un tiempo ordinario [O(n^2)]
 * --Han
 */

public class Alg1 {
    Scanner sc = new Scanner(System.in).useDelimiter(("[\\r|\\n]"));

    /**
     * Un armado simple del conjunto S.
     * Se crea un Arraylist de clase "Par" (Descrita en src/resources) para guardar los pares ordenados del conjunto.
     */
    private void buildS(){
        ArrayList<Par> S = new ArrayList<>();
        String input = "";
        System.out.printf("-%s\n", "Ingrese los pares ordenados del conjunto 'S' separados por ','");

        do{
            System.out.printf("\n-%s","Para finalizar escriba 'S'");
            System.out.printf("\n-%s","Ingrese el Par: ");
            input = sc.next();
            if(input.equalsIgnoreCase("S")) continue;

            try{
                //todo-- Agregar verificación de repetidos.
                String [] tokens = input.split(",");
                float x = Float.parseFloat(tokens[0].trim());
                float y = Float.parseFloat(tokens[1].trim());

                S.add(new Par(x,y));
            }catch(NumberFormatException|ArrayIndexOutOfBoundsException e ){
                System.out.println("Ingrese un valor valido!\n");
            }
        }while(!input.equalsIgnoreCase("S"));
        ArrayList<Par> P = Pareto(S);

        System.out.printf("\n-%s\n", "-- Este era el conjunto 'S'");
        for(Par p : S) {
            System.out.printf("\n%s", p.toString());
        }
        System.out.printf("\n%-s\n","--------------");
        System.out.printf("\n-%s\n", "Este es el resultado de Pareto(S)");
        for(Par p : P) {
            System.out.printf("\n%s", p.toString());
        }
    }

    /**
     * Para esta función Pareto(S) se compara cada dato con los demas datos del arreglo, si el dato pertenece o no, se monitorea con la variable 'flag'.
     * Si al menos una de las variables de p2 (dato de comparación) son menor o igual que p1 (dato analizado), se considera la variable 'mequal'. Por otro lado si tambíen se encuentra un valor de p2 estrictamente mayor que p1
     * se considera la variable 'mstrict'. Cuando ambas variables son true, se entiende que p2 domina a p1, por lo que este se descarta.
     * El orden de este algoritmo es O(n^2), denotado por los 2 ciclos 'for' anidados.
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
