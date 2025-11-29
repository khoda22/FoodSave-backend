package pe.edu.upc.foodsave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.foodsave.dtos.RecetaDTO;
import pe.edu.upc.foodsave.entities.Receta;
import pe.edu.upc.foodsave.servicesinterfaces.IRecetaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recetas")
public class RecetaController {
    @Autowired
    private IRecetaService service;

    @PostMapping("/nuevos")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public void insertar(@RequestBody RecetaDTO dto) {
        ModelMapper m = new ModelMapper();
        Receta r = m.map(dto, Receta.class);
        service.insert(r);
    }

    @GetMapping("/listas")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public List<RecetaDTO> listar() {
        return service.list().stream().map(r -> {
            RecetaDTO dto = new RecetaDTO();
            dto.setIdReceta(r.getIdReceta());
            dto.setTitulo(r.getTitulo());
            dto.setInstrucciones(r.getInstrucciones());
            dto.setDificultad(r.getDificultad());
            dto.setTiempoPreparacion(r.getTiempoPreparacion());
            dto.setCreadoPor(r.getCreadoPor());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Receta r = service.listId(id);
        if (r == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe receta " + id);
        ModelMapper m = new ModelMapper();
        RecetaDTO dto = m.map(r, RecetaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/editar")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> editar(@RequestBody RecetaDTO dto) {
        ModelMapper m = new ModelMapper();
        Receta r = m.map(dto, Receta.class);
        Receta existente = service.listId(r.getIdReceta());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe receta " + r.getIdReceta());
        }
        service.edit(r);
        return ResponseEntity.ok("Receta " + r.getIdReceta() + " actualizada.");
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Receta r = service.listId(id);
        if (r == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        service.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }
}
