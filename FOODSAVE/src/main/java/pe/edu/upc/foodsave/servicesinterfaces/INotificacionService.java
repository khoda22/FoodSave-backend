package pe.edu.upc.foodsave.servicesinterfaces;

import pe.edu.upc.foodsave.entities.Notificacion;

import java.util.List;
import java.util.Optional;

public interface INotificacionService {
    public void insert(Notificacion n);
    public List<Notificacion> list();
    Optional<Notificacion> findByInventarioIdAndTipo(Integer inventarioId, boolean tipo);
}
