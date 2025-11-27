package pe.edu.upc.foodsave.servicesinterfaces;

import pe.edu.upc.foodsave.entities.Escaneo;

import java.util.List;

public interface IEscaneoService {
    public void insert(Escaneo e);
    public List<Escaneo> list();
}
