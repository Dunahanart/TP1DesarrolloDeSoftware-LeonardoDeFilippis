import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        System.out.println("--- SISTEMA DE GESTIÓN DE ENVÍOS Y DISTRIBUCIÓN ---\n");

        // ── Parte 1, 2, 3: crear paquetes con distintos constructores ──
        Paquete p1 = new Paquete("PKG-001", 2.5);                          // mínimo
        Paquete p2 = new Paquete("PKG-002", "Libros de texto", 1.8);       // completo
        Paquete p3 = new Paquete("PKG-003", "Electrónica frágil", 0.9);
        Paquete p4 = new Paquete("PKG-004", "Ropa de invierno", 3.2);
        Paquete p5 = new Paquete("PKG-005", "Documentos legales", 0.3);
        Paquete p6 = new Paquete("PKG-006", "Electrónica", 1.5);
        

        System.out.println("-- Paquetes creados --");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p5);
        System.out.println(p6);

        // ── Parte 3 y 7: crear envíos de distintos tipos con constructores ──
        EnvioEstandar envio1 = new EnvioEstandar("Neuquén", p1);
        envio1.agregarPaquete(p2);

        EnvioExpress envio2 = new EnvioExpress("Buenos Aires");
        envio2.agregarPaquete(p3);

        EnvioInternacional envio3 = new EnvioInternacional("Santiago", "Chile", p4);
        envio3.agregarPaquete(p5);

        EnvioInternacional envio4 = new EnvioInternacional("Montevideo", "Uruguay");
        envio4.agregarPaquete(p6);

        // ── Parte 6: lista de envíos para la jornada ──
        List<Envio> jornada = new ArrayList<>();
        jornada.add(envio1);
        jornada.add(envio2);
        jornada.add(envio3);
        jornada.add(envio4);

        // ── Parte 4: iniciar envíos y observar cómo cambian los paquetes ──
        System.out.println("\n-- Iniciando envíos --");
        for (Envio e : jornada) {
            e.iniciar();
        }

        System.out.println("\n-- Estado de paquetes tras iniciar envíos --");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p6);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);

        // ── Parte 4: finalizar envíos ──
        System.out.println("\n-- Finalizando envíos --");
        for (Envio e : jornada) {
            e.finalizar();
        }

        System.out.println("\n-- Estado final de paquetes --");
        System.out.println(p1);
        System.out.println(p6); 
        System.out.println(p3);
        System.out.println(p4);

        // ── Parte 6: resumen de la jornada ──
        System.out.println("\n-- Resumen de la jornada --");
        for (Envio e : jornada) {
            e.mostrarResumen();
        }

        // ── Demostración de transición inválida ──
        System.out.println("\n-- Prueba de transición inválida --");
        Paquete pTest = new Paquete("PKG-TEST", 1.0);
        try {
            pTest.marcarEntregado(); // salta RECIBIDO -> ENTREGADO, debería fallar
        } catch (IllegalStateException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        // ── Demostración de límite de paquetes por envío ──
        System.out.println("\n-- Prueba de límite de paquetes --");
        EnvioEstandar envioLleno = new EnvioEstandar("Bariloche");
        envioLleno.agregarPaquete(new Paquete("A", 1.0));
        envioLleno.agregarPaquete(new Paquete("B", 1.0));
        envioLleno.agregarPaquete(new Paquete("C", 1.0));
        try {
            envioLleno.agregarPaquete(new Paquete("D", 1.0)); // debe fallar
        } catch (IllegalStateException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}
