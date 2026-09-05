package Item_7;

import Item_1.Alg1;
import Item_2.Alg2;
import item_4.Alg2MiEstructura;
import resources.Par;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

/**
 * Arnés de benchmarking para comparar Alg1, Alg2 y Alg2MiEstructura.
 *
 * No modifica las clases originales: usa reflection para invocar los
 * métodos privados Pareto()/Pareto2() de Alg1 y Alg2. El método público
 * de Alg2MiEstructura se llama directamente.
 *
 * Genera 3 configuraciones de datos:
 *  - RANDOM        : puntos uniformes en una región grande.
 *  - CONCENTRADO   : puntos agrupados en una región pequeña.
 *  - PEOR_CASO     : anticadena (todos los puntos pertenecen a Pareto(S)).
 *
 * Resultados: se imprimen por consola y se guardan en resultados.csv
 * para graficarlos después (Excel, Python, etc).
 */
public class Benchmark {

    private static final Random rnd = new Random(42); // semilla fija -> resultados reproducibles

    public enum Config { RANDOM, CONCENTRADO, PEOR_CASO }

    public static void main(String[] args) throws Exception {

        // Los 3 algoritmos son O(n^2)
        int[] tamañosPeorCasoYConcentrado = {1000, 5000, 10000, 50000, 100000};
        int[] tamañosRandom               = {1000, 10000, 100000, 1000000};

        // Métodos por reflection (una sola vez)
        Method mAlg1 = Alg1.class.getDeclaredMethod("Pareto", ArrayList.class);
        mAlg1.setAccessible(true);

        Method mAlg2 = Alg2.class.getDeclaredMethod("Pareto2", ArrayList.class);
        mAlg2.setAccessible(true);

        for (Config config : Config.values()) {
            int[] tamaños = (config == Config.RANDOM) ? tamañosRandom : tamañosPeorCasoYConcentrado;

            System.out.println("\n================ Configuración: " + config + " ================");
            for (int n : tamaños) {
                ArrayList<Par> S = generarConjunto(n, config);

                // --- Alg1 ---
                ejecutarYRegistrar("Alg1", config, n, () -> {
                    try {
                        Alg1 alg1 = new Alg1();
                        ArrayList<Par> r = (ArrayList<Par>) mAlg1.invoke(alg1, S);
                        return r.size();
                    } catch (Exception e) { throw new RuntimeException(e); }
                });

                // --- Alg2 (arreglo) ---
                ejecutarYRegistrar("Alg2", config, n, () -> {
                    try {
                        Alg2 alg2 = new Alg2();
                        Par[] r = (Par[]) mAlg2.invoke(alg2, S);
                        return r.length;
                    } catch (Exception e) { throw new RuntimeException(e); }
                });

                // --- Alg2MiEstructura (LinkedList) ---
                ejecutarYRegistrar("Alg2MiEstructura", config, n, () -> {
                    Alg2MiEstructura alg2b = new Alg2MiEstructura();
                    Par[] r = alg2b.Pareto2(S);
                    return r.length;
                });
            }
        }
        System.out.println("\nListo. Resultados finalizados");
    }

    // ---------- Ejecución cronometrada ----------

    @FunctionalInterface
    interface Tarea { int ejecutar(); }

    private static void ejecutarYRegistrar(String nombreAlg, Config config, int n, Tarea tarea) {
        long inicio = System.nanoTime();
        int tamañoPareto = tarea.ejecutar();
        long fin = System.nanoTime();

        double ms = (fin - inicio) / 1_000_000.0;

        System.out.printf("%-20s n=%-9d tiempo=%10.2f ms   |Pareto(S)|=%d%n", nombreAlg, n, ms, tamañoPareto);
    }

    // ---------- Generadores de conjuntos S ----------

    private static ArrayList<Par> generarConjunto(int n, Config config) {
        switch (config) {
            case RANDOM:      return generarAleatorio(n);
            case CONCENTRADO: return generarConcentrado(n);
            case PEOR_CASO:   return generarPeorCaso(n);
            default: throw new IllegalArgumentException();
        }
    }

    /** Puntos uniformes en una región grande [0, 100000] x [0, 100000]. */
    private static ArrayList<Par> generarAleatorio(int n) {
        ArrayList<Par> S = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            float x = rnd.nextFloat() * 100000f;
            float y = rnd.nextFloat() * 100000f;
            S.add(new Par(x, y));
        }
        return S;
    }

    /** Puntos concentrados en una región pequeña [0, 10] x [0, 10]: muchas dominaciones. */
    private static ArrayList<Par> generarConcentrado(int n) {
        ArrayList<Par> S = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            float x = rnd.nextFloat() * 10f;
            float y = rnd.nextFloat() * 10f;
            S.add(new Par(x, y));
        }
        return S;
    }

    /**
     * Peor caso: anticadena. x estrictamente creciente, y estrictamente decreciente.
     * Ningún punto domina a otro -> Pareto(S) = S completo (tamaño n).
     * Esto maximiza el tamaño de C durante toda la ejecución, forzando el comportamiento
     * cuadrático real de los 3 algoritmos.
     */
    private static ArrayList<Par> generarPeorCaso(int n) {
        ArrayList<Par> S = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            float x = i + 1;                 // 1, 2, 3, ..., n
            float y = (n - i) + 0.5f;        // n+0.5, n-0.5, ..., 1.5 (estrictamente decreciente)
            S.add(new Par(x, y));
        }
        // Desordenamos S para que el orden de llegada no favorezca artificialmente
        // a ningún algoritmo (por ejemplo, para que Alg2 no evite siempre los shifts).
        java.util.Collections.shuffle(S, rnd);
        return S;
    }
}
