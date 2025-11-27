package pe.edu.upc.foodsave.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.foodsave.entities.IngredienteReceta;
import pe.edu.upc.foodsave.repositories.IIngredienteRecetaRepository;
import pe.edu.upc.foodsave.servicesinterfaces.IIngredienteRecetaService;

import java.util.List;

@Service
public class IngredienteRecetaServiceImplements implements IIngredienteRecetaService {
    @Autowired
    private IIngredienteRecetaRepository repository;

    @Override
    public void insert(IngredienteReceta ir) {
        repository.save(ir);
    }

    @Override
    public List<IngredienteReceta> list() {
        return repository.findAll();
    }

    @Override
    public IngredienteReceta listId(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
