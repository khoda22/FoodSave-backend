package pe.edu.upc.foodsave.dtos;

import pe.edu.upc.foodsave.entities.Producto;
import pe.edu.upc.foodsave.entities.Usuario;

import java.time.LocalDate;

public class EscaneoDTO {
    private Integer idEscaneo;
    private LocalDate fechaEscaneo;
    private String origen;
    private Usuario usuario;
    private Producto producto;

    public Integer getIdEscaneo() {
        return idEscaneo;
    }

    public void setIdEscaneo(Integer idEscaneo) {
        this.idEscaneo = idEscaneo;
    }

    public LocalDate getFechaEscaneo() {
        return fechaEscaneo;
    }

    public void setFechaEscaneo(LocalDate fechaEscaneo) {
        this.fechaEscaneo = fechaEscaneo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
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
