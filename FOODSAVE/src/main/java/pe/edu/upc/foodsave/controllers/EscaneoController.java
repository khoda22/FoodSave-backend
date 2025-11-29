package pe.edu.upc.foodsave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.foodsave.dtos.EscaneoDTO;
import pe.edu.upc.foodsave.entities.Escaneo;
import pe.edu.upc.foodsave.entities.Producto;
import pe.edu.upc.foodsave.entities.Usuario;
import pe.edu.upc.foodsave.repositories.IProductoRepository;
import pe.edu.upc.foodsave.repositories.IUsuarioRepository;
import pe.edu.upc.foodsave.servicesinterfaces.IEscaneoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/escaneos")
public class EscaneoController {
    @Autowired private IEscaneoService service;
    @Autowired private IUsuarioRepository usuarioRepository;
    @Autowired private IProductoRepository productoRepository;

    @PostMapping("/nuevos")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> insertar(@RequestBody EscaneoDTO dto) {
        ModelMapper m=new ModelMapper();
        Escaneo e = m.map(dto, Escaneo.class);

        service.insert(e);
        return ResponseEntity.status(HttpStatus.CREATED).body("Escaneo registrado correctamente.");
    }

    @GetMapping("/listas")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PROGRAMADOR','CLIENTE')")
    public List<EscaneoDTO>listar(){
        return service.list().stream().map(a->{
            ModelMapper m=new ModelMapper();
            return m.map(a,EscaneoDTO.class);
        }).collect(Collectors.toList());
    }

}
