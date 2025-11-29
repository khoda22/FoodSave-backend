package pe.edu.upc.foodsave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.foodsave.dtos.IngredienteRecetaDTO;
import pe.edu.upc.foodsave.entities.IngredienteReceta;
import pe.edu.upc.foodsave.repositories.IProductoRepository;
import pe.edu.upc.foodsave.repositories.IRecetaRepository;
import pe.edu.upc.foodsave.servicesinterfaces.IIngredienteRecetaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteRecetaController {
    @Autowired
    private IIngredienteRecetaService service;
    @Autowired
    private IRecetaRepository recetaRepository;
    @Autowired
    private IProductoRepository productoRepository;

    @PostMapping("/nuevos")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public void insertar(@RequestBody IngredienteRecetaDTO dto) {
        IngredienteReceta ir = new IngredienteReceta();
        ir.setCantidadProductos(dto.getCantidadProductos());
        ir.setUnidad(dto.getUnidad());
        ir.setNota(dto.getNota());
        if (dto.getReceta().getIdReceta() != null && dto.getReceta().getIdReceta() > 0) {
            ir.setReceta(recetaRepository.getReferenceById(dto.getReceta().getIdReceta()));
        }
        if (dto.getProducto().getIdProducto() > 0) {
            ir.setProducto(productoRepository.getReferenceById(dto.getProducto().getIdProducto()));
        }
        service.insert(ir);
    }

    @GetMapping("/listas")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public List<IngredienteRecetaDTO>listar(){
        return service.list().stream().map(a->{
            ModelMapper m=new ModelMapper();
            return m.map(a,IngredienteRecetaDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        IngredienteReceta ir = service.listId(id);
        if (ir == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        service.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }
}
