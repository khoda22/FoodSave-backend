package pe.edu.upc.foodsave.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInventario;

    @NotNull
    @Column(name = "cantidadInventario", nullable = false)
    private Integer cantidadInventario;

    @Min(0)
    @Column(name = "diasduracionInventario", nullable = false)
    private Integer diasduracionInventario;

    @Column(name = "estadoInventario", length = 50, nullable = false)
    private String estadoInventario;

    @Column(name = "fechavencimientoInventario", nullable = false)
    private LocalDate fechavencimientoInventario;

    @Column(name = "fechacreacionInventario", nullable = false)
    private LocalDate fechacreacionInventario;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    public Inventario() {}

    // ——— Hooks ———
    @PrePersist
    public void onCreate() {
        if (this.fechacreacionInventario == null) {
            this.fechacreacionInventario = LocalDate.now();
        }
        calcularVencimiento();
        actualizarEstado();
    }

    @PreUpdate
    public void onUpdate() {
        // Si cambian días o fecha de creación, recalculemos siempre
        calcularVencimiento();
        actualizarEstado();
    }

    // ——— Regla de negocio ———
    private void calcularVencimiento() {
        // Protégete ante valores negativos (ya hay @Min, pero por si acaso)
        int dias = Math.max(0, this.diasduracionInventario);
        this.fechavencimientoInventario = this.fechacreacionInventario.plusDays(dias);
    }

    private void actualizarEstado() {
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), this.fechavencimientoInventario);
        if (diasRestantes < 0) {
            this.estadoInventario = "VENCIDO";
        } else if (diasRestantes <= 7) {
            this.estadoInventario = "POR_VENCER";
        } else {
            this.estadoInventario = "OK";
        }
    }

    public void recomputarFechasYEstado() {
        if (this.fechacreacionInventario == null) {
            this.fechacreacionInventario = LocalDate.now();
        }

        int dias = Math.max(0, this.diasduracionInventario);
        this.fechavencimientoInventario = this.fechacreacionInventario.plusDays(dias);

        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), this.fechavencimientoInventario);
        if (diasRestantes < 0) {
            this.estadoInventario = "VENCIDO";
        } else if (diasRestantes <= 7) {
            this.estadoInventario = "POR_VENCER";
        } else {
            this.estadoInventario = "OK";
        }
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public @NotNull Integer getCantidadInventario() {
        return cantidadInventario;
    }

    public void setCantidadInventario(@NotNull Integer cantidadInventario) {
        this.cantidadInventario = cantidadInventario;
    }

    @Min(0)
    public Integer getDiasduracionInventario() {
        return diasduracionInventario;
    }

    public void setDiasduracionInventario(@Min(0) Integer diasduracionInventario) {
        this.diasduracionInventario = diasduracionInventario;
    }

    public String getEstadoInventario() {
        return estadoInventario;
    }

    public void setEstadoInventario(String estadoInventario) {
        this.estadoInventario = estadoInventario;
    }

    public LocalDate getFechavencimientoInventario() {
        return fechavencimientoInventario;
    }

    public void setFechavencimientoInventario(LocalDate fechavencimientoInventario) {
        this.fechavencimientoInventario = fechavencimientoInventario;
    }

    public LocalDate getFechacreacionInventario() {
        return fechacreacionInventario;
    }

    public void setFechacreacionInventario(LocalDate fechacreacionInventario) {
        this.fechacreacionInventario = fechacreacionInventario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
