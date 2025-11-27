package pe.edu.upc.foodsave.servicesinterfaces;

import pe.edu.upc.foodsave.entities.Inventario;

import java.util.List;

public interface IInventarioService {
    public List<Inventario> list();
    public void insert(Inventario p);
    public Inventario listId(int id);
    public void delete(int id);
    public void edit(Inventario p);
}
