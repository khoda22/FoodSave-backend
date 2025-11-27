package pe.edu.upc.foodsave.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.foodsave.entities.Rol;
import pe.edu.upc.foodsave.repositories.IRolRepository;
import pe.edu.upc.foodsave.servicesinterfaces.IRolService;

import java.util.List;

@Service
public class RolServiceImplement implements IRolService {

    @Autowired
    private IRolRepository rR;

    @Override
    public List<Rol> listar() {
        return rR.findAll();
    }

    @Override
    public void Registrar(Rol r) {
        rR.save(r);
    }

    @Override
    public Rol listarporid(int id) {
        return rR.findById(id).orElse(new Rol());
    }

    @Override
    public void Modificar(Rol r) {
        rR.save(r);
    }

    @Override
    public void Eliminar(int id) {
        rR.deleteById(id);
    }
}
