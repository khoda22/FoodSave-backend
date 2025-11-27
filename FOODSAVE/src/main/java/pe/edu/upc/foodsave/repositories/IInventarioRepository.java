package pe.edu.upc.foodsave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.foodsave.entities.Inventario;

@Repository
public interface IInventarioRepository extends JpaRepository<Inventario,Integer> {
}
