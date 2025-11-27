package pe.edu.upc.foodsave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upc.foodsave.dtos.CalificacionRecetaDTO;
import pe.edu.upc.foodsave.entities.CalificacionReceta;
import pe.edu.upc.foodsave.entities.Receta;
import pe.edu.upc.foodsave.entities.Usuario;
import pe.edu.upc.foodsave.repositories.ICalificacionRecetaRepository;
import pe.edu.upc.foodsave.repositories.IRecetaRepository;
import pe.edu.upc.foodsave.repositories.IUsuarioRepository;
import pe.edu.upc.foodsave.servicesinterfaces.ICalificacionRecetaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionRecetaController {

    @Autowired
    private ICalificacionRecetaService service;
    @Autowired
    private IRecetaRepository recetaRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private ICalificacionRecetaRepository calificacionRepo;

    @PostMapping("/nuevos")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<?> insertar(@RequestBody CalificacionRecetaDTO dto) {

        if (dto.getCalificacion() == null || dto.getCalificacion() < 1 || dto.getCalificacion() > 5) {
            return ResponseEntity.badRequest().body("La calificación debe estar entre 1 y 5.");
        }
        if (dto.getReceta().getIdReceta() == null || dto.getReceta().getIdReceta() <= 0) {
            return ResponseEntity.badRequest().body("idReceta es obligatorio.");
        }
        if (dto.getUsuario().getIdUsuario() == 0 || dto.getUsuario().getIdUsuario() <= 0) {
            return ResponseEntity.badRequest().body("idUsuario es obligatorio.");
        }

        Receta receta = recetaRepository.findById(dto.getReceta().getIdReceta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La receta no existe"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no existe"));

        // ¿Ya calificó? -> actualiza; si no, crea.
        CalificacionReceta cal = calificacionRepo
                .findByUsuario_IdUsuarioAndReceta_IdReceta(usuario.getIdUsuario(), receta.getIdReceta())
                .orElseGet(CalificacionReceta::new);

        cal.setUsuario(usuario);
        cal.setReceta(receta);
        cal.setCalificacion(dto.getCalificacion());

        service.insert(cal); // save()
        return ResponseEntity.ok("Calificación registrada/actualizada.");
    }


    @GetMapping("/listas")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR','CLIENTE')")
    public List<CalificacionRecetaDTO>listar(){
        return service.list().stream().map(a->{
            ModelMapper m=new ModelMapper();
            return m.map(a,CalificacionRecetaDTO.class);
        }).collect(Collectors.toList());
    }


    // (Opcional) endpoint de agregados por receta
    @GetMapping("/recetas/{idReceta}/rating")
    public ResponseEntity<?> ratingDeReceta(@PathVariable Integer idReceta) {
        Double promedio = calificacionRepo.promedioPorReceta(idReceta);
        Long cantidad = calificacionRepo.cantidadPorReceta(idReceta);
        Map<String,Object> out = new HashMap<>();
        out.put("idReceta", idReceta);
        out.put("promedio", promedio == null ? 0.0 : promedio);
        out.put("cantidad", cantidad == null ? 0L : cantidad);
        return ResponseEntity.ok(out);
    }
}
