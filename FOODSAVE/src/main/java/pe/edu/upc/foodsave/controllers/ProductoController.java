package pe.edu.upc.foodsave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.foodsave.dtos.ProductoDTO;
import pe.edu.upc.foodsave.entities.Producto;
import pe.edu.upc.foodsave.servicesinterfaces.IProductoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private IProductoService service;

    @GetMapping("/listas")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR')")
    public List<ProductoDTO> listar(){
        return service.list().stream().map(a->{
            ModelMapper m=new ModelMapper();
            return m.map(a,ProductoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/nuevos")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> insertar(@RequestBody ProductoDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Producto producto = mapper.map(dto, Producto.class);
        service.insert(producto);
        return ResponseEntity.ok("Producto registrado correctamente");
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Producto prov = service.listId(id);
        if (prov == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        ProductoDTO dto = m.map(prov, ProductoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        Producto p = service.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        service.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }
    @PutMapping("/edit")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> modificar(@RequestBody ProductoDTO dto) {
        ModelMapper m = new ModelMapper();
        Producto p = m.map(dto, Producto.class);
        Producto existente = service.listId(p.getIdProducto());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + p.getIdProducto());
        }
        service.edit(p);
        return ResponseEntity.ok("Registro con ID " + p.getIdProducto() + " modificado correctamente.");
    }
}
