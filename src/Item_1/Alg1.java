package Item_1;

import java.util.*;
import resources.Par;
/**
 * Algoritmo para obtener el Skyline(S), en un tiempo ordinario [O(n^2)]
 * --Han
 */

public class Alg1 {
    ArrayList<Par> S = new ArrayList<>();
    Scanner sc = new Scanner(System.in).useDelimiter(("[\\r|\\n]"));

    public static void main(String[] args) {
        Alg1 alg1 = new Alg1();
        alg1.menu();
    }

    public void menu(){
        String input = "";
        System.out.printf("-%s\n", "Ingrese los pares ordenados del conjunto 'S' separados por ','");

        do{
            System.out.printf("\n-%s","Para finalizar escriba 'S'");
            System.out.printf("\n-%s","Ingrese el Par: ");
            input = sc.next();
            if(input.equalsIgnoreCase("S")) continue;

            try{
                String [] tokens = input.split(",");
                float x = Float.parseFloat(tokens[0]);
                float y = Float.parseFloat(tokens[1]);

                S.add(new Par(x,y));
            }catch(NumberFormatException|ArrayIndexOutOfBoundsException e ){
                System.out.println("Ingrese un valor valido!\n");
            }
        }while(!input.equalsIgnoreCase("S"));
    }
}
