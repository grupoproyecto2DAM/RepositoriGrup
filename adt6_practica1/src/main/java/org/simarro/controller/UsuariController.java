package org.simarro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.simarro.model.Usuari;
import org.simarro.service.usuari.IUsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuaris")
@Tag(name = "Usuaris", description = "Catálogo de usuarios")
public class UsuariController {

    @Autowired
    private IUsuariService service;

    @GetMapping
    @Operation(summary = "Obtiene el listado de usuarios")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe el listado, aunque puede que vacio",
                    content = @Content(schema = @Schema(implementation = Usuari.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede obtener el listado",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<List<Usuari>> listar() {
        List<Usuari> lista = service.listar();

        // Código 200 OK para select
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/signIn")
    @Operation(summary = "Obtiene el usuario por signin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation = Usuari.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede obtener el usuario",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<Usuari> listarId(@RequestParam(value = "nombre") String nombre,
                                                 @RequestParam(value = "contrasena") String contrasena) {
        List<Usuari> lista = service.listar();
        for(Usuari u :lista){
            if (u.getNombre().equalsIgnoreCase(nombre)&&u.getPassword().equalsIgnoreCase(contrasena)){
                // Código 200 OK para select
                return new ResponseEntity<>(u, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new Usuari(), HttpStatus.NO_CONTENT);

    }

    @GetMapping("/signIn")
    @Operation(summary = "Obtiene el usuario por signin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation = Usuari.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede obtener el usuario",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<Usuari> listarID(@RequestParam(value = "nia") Integer nia,
                                           @RequestParam(value = "contrasena") String contrasena) {
        List<Usuari> lista = service.listar();
        for(Usuari u :lista){
            if (u.getNia()==nia && u.getPassword().equalsIgnoreCase(contrasena)){
                // Código 200 OK para select
                return new ResponseEntity<>(u, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new Usuari(), HttpStatus.NO_CONTENT);

    }



    @PostMapping
    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Se registra el usuario",
                    content = @Content(schema = @Schema(implementation = Usuari.class)))
    })
    public ResponseEntity<Usuari> registrar(
            @RequestParam Integer nia,
            @RequestParam String nombre,
            @RequestParam String password,
            @RequestParam String rol,
            @RequestParam String curso,
            @RequestParam String materia
    ) {
        Usuari usuari = new Usuari(nia, nombre, password, rol, curso, materia);
        Usuari obj = service.registrar(usuari);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un usuario existente por id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe",
                    content = @Content(schema = @Schema(implementation = Usuari.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        service.eliminar(id);

        // Código 204 NOT CONTENT para delete
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /////////////////////////////////////////
    // Métodos específicos de esta entidad //
    /////////////////////////////////////////
    @DeleteMapping("/eliminarUsuario")
    @Operation(summary = "Elimina un usuario por nombre")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe",
                    content = @Content(schema = @Schema(implementation = Usuari.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<Void> eliminarPoNombre(@RequestParam(value = "nombre") String nombre) {

        if (!service.existsByNombre(nombre)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.eliminarPorNombre(nombre);

        // Código 204 NOT CONTENT para delete
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
