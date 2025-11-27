package pe.edu.upc.foodsave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.foodsave.entities.Receta;

@Repository
public interface IRecetaRepository extends JpaRepository<Receta, Integer> {
}
