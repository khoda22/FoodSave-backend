package pe.edu.upc.foodsave.dtos;

import pe.edu.upc.foodsave.entities.Producto;
import pe.edu.upc.foodsave.entities.Usuario;

import java.time.LocalDate;

public class InventarioDTO {
    private Integer idInventario;
    private Integer cantidadInventario;
    private Integer diasduracionInventario;
    private String estadoInventario;
    private LocalDate fechavencimientoInventario;
    private LocalDate fechacreacionInventario;
    private Usuario usuario;
    private Producto producto;

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getCantidadInventario() {
        return cantidadInventario;
    }

    public void setCantidadInventario(Integer cantidadInventario) {
        this.cantidadInventario = cantidadInventario;
    }

    public int getDiasduracionInventario() {
        return diasduracionInventario;
    }

    public void setDiasduracionInventario(int diasduracionInventario) {
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
