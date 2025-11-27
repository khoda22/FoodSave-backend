package pe.edu.upc.foodsave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR','CLIENTE')")
    public ResponseEntity<String> insertar(@RequestBody EscaneoDTO dto) {
        // 1) validar origen
        String origen = dto.getOrigen() != null ? dto.getOrigen().toUpperCase() : "";
        if (!origen.equals("QR") && !origen.equals("BARRAS") && !origen.equals("MANUAL")) {
            return ResponseEntity.badRequest().body("Origen inválido. Use: QR, BARRAS o MANUAL.");
        }

        // 2) validar usuario
        if (dto.getUsuario().getIdUsuario() <= 0) {
            return ResponseEntity.badRequest().body("idUsuario es requerido.");
        }
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + dto.getUsuario().getIdUsuario()));

        // 3) resolver producto
        Producto producto = null;

        boolean tieneId = dto.getProducto().getIdProducto() > 0;
        boolean tieneCB = dto.getProducto().getCodigoBarra() != null && !dto.getProducto().getCodigoBarra().isBlank();

        if (!tieneId && !tieneCB) {
            return ResponseEntity.badRequest().body("Debe enviar idProducto o codigoBarra.");
        }

        if (tieneCB) {
            producto = productoRepository.findByCodigoBarra(dto.getProducto().getCodigoBarra().trim())
                    .orElse(null);
            if (producto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado para el código de barras proporcionado. Regístrelo primero en /productos.");
            }
        }

        if (tieneId) {
            Producto porId = productoRepository.findById(dto.getProducto().getIdProducto())
                    .orElse(null);
            if (porId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con idProducto=" + dto.getProducto().getIdProducto());
            }
            // si llegaron ambos, verificar consistencia
            if (producto != null && porId.getIdProducto() != producto.getIdProducto()) {
                return ResponseEntity.badRequest()
                        .body("idProducto y codigoBarra no corresponden al mismo producto.");
            }
            // si venía solo id, usar ese
            if (producto == null) producto = porId;
        }

        // 4) construir y guardar
        Escaneo e = new Escaneo();
        e.setOrigen(origen);
        e.setUsuario(usuario);
        e.setProducto(producto);
        e.setFechaEscaneo(dto.getFechaEscaneo()); // si null, @PrePersist pone hoy

        service.insert(e);
        return ResponseEntity.status(HttpStatus.CREATED).body("Escaneo registrado correctamente.");
    }

    @GetMapping("/listas")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR','PROGRAMADOR')")
    public List<EscaneoDTO>listar(){
        return service.list().stream().map(a->{
            ModelMapper m=new ModelMapper();
            return m.map(a,EscaneoDTO.class);
        }).collect(Collectors.toList());
    }

}
