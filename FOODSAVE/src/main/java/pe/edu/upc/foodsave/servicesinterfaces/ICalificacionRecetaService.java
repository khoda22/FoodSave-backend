package pe.edu.upc.foodsave.servicesinterfaces;

import pe.edu.upc.foodsave.entities.CalificacionReceta;

import java.util.List;

public interface ICalificacionRecetaService {
    public void insert(CalificacionReceta cr);
    public List<CalificacionReceta> list();
}
