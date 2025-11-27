package pe.edu.upc.foodsave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.foodsave.dtos.*;
import pe.edu.upc.foodsave.entities.Rol;
import pe.edu.upc.foodsave.entities.Usuario;
import pe.edu.upc.foodsave.repositories.IRolRepository;
import pe.edu.upc.foodsave.servicesinterfaces.IUsuarioService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private IUsuarioService uS;
    @Autowired
    private IRolRepository rolRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
  
    @GetMapping("/lista")
    //@PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public List<UsuarioDTO> Listar() {
        return uS.listar().stream().map( x->{
            ModelMapper m = new ModelMapper();
            return m.map(x,UsuarioDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> Registrar(@RequestBody UsuarioRegistroPublicoDTO dto){

        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto, Usuario.class);

        // encriptar password
        u.setPassword(passwordEncoder.encode(dto.getPassword()));

        // forzar rol CLIENT (ignorar cualquier rol que venga del cliente)
        Rol rolClient = rolRepo.findByNombreRol("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no está configurado"));
        u.setRol(rolClient);

        // otros defaults recomendados
        u.setEstado(true);
        u.setLogin(LocalDateTime.now());
        u.setCreacion(LocalDateTime.now());

        uS.Registrar(u);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario CLIENTE registrado");
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR')")
    public UsuarioDTO Listarporid(@PathVariable("id") int id){
        ModelMapper m = new ModelMapper();
        UsuarioDTO dto = m.map(uS.listarporid(id),UsuarioDTO.class);
        return dto;
    }

    @PutMapping("/actualizar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR')")
    public ResponseEntity<String> modificar(@RequestBody UsuarioDTO dto) {

        Usuario existente = uS.listarporid(dto.getIdUsuario());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un usuario con el ID: " + dto.getIdUsuario());
        }

        // Mapear los datos del DTO al usuario existente
        ModelMapper m = new ModelMapper();
        m.getConfiguration().setSkipNullEnabled(true);
        m.map(dto, existente);

        // Encriptar y actualizar contraseña siempre
        existente.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Actualizar rol si viene en el JSON
        if (dto.getRol() != null && dto.getRol().getIdRol() != null) {
            Rol rol = rolRepo.getReferenceById(dto.getRol().getIdRol());
            existente.setRol(rol);
        }

        // Guardar cambios
        uS.Modificar(existente);

        return ResponseEntity.ok("Usuario con ID " + dto.getIdUsuario() + " modificado correctamente.");
    }



    @DeleteMapping( "/borrar/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR')")
    public void Eliminar(@PathVariable("id") int id){
        uS.Eliminar(id);
    }
}
