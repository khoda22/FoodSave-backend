package pe.edu.upc.foodsave.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.foodsave.entities.Producto;
import pe.edu.upc.foodsave.repositories.IProductoRepository;
import pe.edu.upc.foodsave.servicesinterfaces.IProductoService;

import java.util.List;

@Service
public class ProductoServiceImplements implements IProductoService {
    @Autowired
    private IProductoRepository repository;

    @Override
    public List<Producto> list() {
        return repository.findAll();
    }

    @Override
    public void insert(Producto p) {
        repository.save(p);
    }

    @Override
    public Producto listId(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public void edit(Producto p) {
        repository.save(p);
    }

}
