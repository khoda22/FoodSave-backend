package pe.edu.upc.foodsave.dtos;

import pe.edu.upc.foodsave.entities.Receta;
import pe.edu.upc.foodsave.entities.Usuario;

public class CalificacionRecetaDTO {
    private Integer idCalificacionReceta;
    private Integer calificacion;
    private Receta receta;
    private Usuario usuario;

    public Integer getIdCalificacionReceta() {
        return idCalificacionReceta;
    }

    public void setIdCalificacionReceta(Integer idCalificacionReceta) {
        this.idCalificacionReceta = idCalificacionReceta;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
