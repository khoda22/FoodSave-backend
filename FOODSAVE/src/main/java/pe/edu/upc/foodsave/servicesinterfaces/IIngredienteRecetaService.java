package pe.edu.upc.foodsave.servicesinterfaces;

import pe.edu.upc.foodsave.entities.IngredienteReceta;

import java.util.List;

public interface IIngredienteRecetaService {
    public void insert(IngredienteReceta ir);
    public List<IngredienteReceta> list();
    public IngredienteReceta listId(int id);
    public void delete(int id);
}
