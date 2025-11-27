package pe.edu.upc.foodsave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.foodsave.entities.Escaneo;

@Repository
public interface IEscaneoRepository extends JpaRepository<Escaneo, Integer> {
}
