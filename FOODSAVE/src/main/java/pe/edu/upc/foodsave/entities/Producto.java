package pe.edu.upc.foodsave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    @Column(name = "vida_util_dias", nullable = false)
    private Integer vidaUtilDias;

    @Column(name = "estado", nullable = false, length = 40)
    private String estado;

    @Column(name = "codigo_barra")
    private String codigoBarra;

    @Column(name = "peso_unitario", nullable = false)
    private Double pesoUnitario;

    public Producto() {}

    public Producto(Integer idProducto, String nombre, String categoria, Integer vidaUtilDias, String estado, String codigoBarra, Double pesoUnitario) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.vidaUtilDias = vidaUtilDias;
        this.estado = estado;
        this.codigoBarra = codigoBarra;
        this.pesoUnitario = pesoUnitario;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getVidaUtilDias() {
        return vidaUtilDias;
    }

    public void setVidaUtilDias(Integer vidaUtilDias) {
        this.vidaUtilDias = vidaUtilDias;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public Double getPesoUnitario() {
        return pesoUnitario;
    }

    public void setPesoUnitario(Double pesoUnitario) {
        this.pesoUnitario = pesoUnitario;
    }
}
