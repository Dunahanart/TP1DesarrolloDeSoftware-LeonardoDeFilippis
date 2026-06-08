import java.util.ArrayList;
import java.util.List;

// Parte 7: clase abstracta como base para EnvioEstandar, EnvioExpress, EnvioInternacional
public abstract class Envio {

    private static int contadorId = 1;

    private final int id;
    private final String destino;
    private final List<Paquete> paquetes;
    private boolean iniciado;
    private boolean finalizado;

    // Parte 3: constructor básico
    public Envio(String destino) {
        this.id = contadorId++;
        this.destino = destino;
        this.paquetes = new ArrayList<>();
        this.iniciado = false;
        this.finalizado = false;
    }

    // Parte 3: constructor con un paquete inicial
    public Envio(String destino, Paquete paquete) {
        this(destino);
        agregarPaquete(paquete);
    }

    // Agrega un paquete respetando el límite de 3
    public void agregarPaquete(Paquete paquete) {
        if (paquetes.size() >= 3) {
            throw new IllegalStateException("Un envío no puede tener más de 3 paquetes.");
        }
        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no puede ser nulo.");
        }
        paquetes.add(paquete);
    }

    // Parte 4: al iniciar, los paquetes reflejan el cambio
    public void iniciar() {
        if (paquetes.isEmpty()) {
            throw new IllegalStateException("No se puede iniciar un envío sin paquetes.");
        }
        if (iniciado) {
            throw new IllegalStateException("El envío ya fue iniciado.");
        }
        iniciado = true;
        for (Paquete p : paquetes) {
            p.iniciarPreparacion();
            p.salirADistribucion();
        }
        System.out.println("Envío #" + id + " iniciado hacia " + destino);
    }

    // Parte 4: al finalizar, los paquetes actualizan su estado
    public void finalizar() {
        if (!iniciado) {
            throw new IllegalStateException("El envío no ha sido iniciado.");
        }
        if (finalizado) {
            throw new IllegalStateException("El envío ya fue finalizado.");
        }
        finalizado = true;
        for (Paquete p : paquetes) {
            p.marcarEntregado();
        }
        System.out.println("Envío #" + id + " finalizado. Paquetes entregados en " + destino);
    }

    // Parte 5 y 7: cálculo de costo delegado a cada subclase
    public double calcularCosto() {
        double base = calcularCostoBase();
        double recargo = aplicarRecargo(base);
        return base + recargo;
    }

    // Parte 7: método abstracto — cada tipo lo implementa a su manera
    protected abstract double calcularCostoBase();

    // Parte 5: lógica interna de recargo, puede sobreescribirse
    protected double aplicarRecargo(double costoBase) {
        return 0;
    }

    // Parte 5: peso total encapsulado, usado internamente
    protected double calcularPesoTotal() {
        return paquetes.stream().mapToDouble(Paquete::getPesoKg).sum();
    }

    // Getters
    public int getId() { return id; }
    public String getDestino() { return destino; }
    public List<Paquete> getPaquetes() { return List.copyOf(paquetes); }
    public boolean isIniciado() { return iniciado; }
    public boolean isFinalizado() { return finalizado; }

    public void mostrarResumen() {
        System.out.printf("Envío #%d | Tipo: %-15s | Destino: %-15s | Paquetes: %d | Costo: $%.2f%n",
            id, getClass().getSimpleName(), destino, paquetes.size(), calcularCosto());
    }

    @Override
    public String toString() {
        return String.format("Envío #%d hacia %s (%s) - Costo: $%.2f",
            id, destino, getClass().getSimpleName(), calcularCosto());
    }
}
