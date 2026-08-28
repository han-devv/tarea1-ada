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
     * Para esta función Pareto(S) se compara cada dato con los demas datos del arreglo, si el dato es estrictamente mayor en x e y se hace verdad la variable 'flag'.
     * Por otro lado, si al menos ambas son variables son menor o igual, se continua con las comparaciones. De otro modo, se descarta y se continua el siguiente dato.
     * El orden de este algoritmo es O(n^2), denotado por los 2 ciclos 'for' anidados.
     * @param S
     */
    private ArrayList<Par> Pareto(ArrayList<Par> S){
        ArrayList<Par> P = new ArrayList<>();
        boolean flag;
        for (int i = 0; i < S.size(); i++) {
            Par p1 = S.get(i);
            flag = false;
            for (int j = 0; j < S.size(); j++) {
                Par p2 = S.get(j);

                if(p1.getX()<p2.getX() && p1.getY()<p2.getY()) flag = true;
                if(!(p1.getX() <= p2.getX() && p1.getY() <= p2.getY())) break;
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
