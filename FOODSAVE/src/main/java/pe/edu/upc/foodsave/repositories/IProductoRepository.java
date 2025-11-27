package pe.edu.upc.foodsave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.foodsave.entities.Producto;

import java.util.Optional;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Integer>{
    Optional<Producto> findByCodigoBarra(String codigoBarra);
}
