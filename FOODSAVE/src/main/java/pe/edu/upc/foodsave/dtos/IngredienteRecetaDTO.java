package pe.edu.upc.foodsave.dtos;

import pe.edu.upc.foodsave.entities.Producto;
import pe.edu.upc.foodsave.entities.Receta;

import java.math.BigDecimal;

public class IngredienteRecetaDTO {
    private Integer idIngredienteReceta;
    private BigDecimal cantidadProductos;
    private String unidad;
    private String nota;
    private Receta receta;
    private Producto producto;

    public Integer getIdIngredienteReceta() {
        return idIngredienteReceta;
    }

    public void setIdIngredienteReceta(Integer idIngredienteReceta) {
        this.idIngredienteReceta = idIngredienteReceta;
    }

    public BigDecimal getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(BigDecimal cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
