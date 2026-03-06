package org.simarro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.simarro.model.Incidencia;
import org.simarro.service.Incidencia.IIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidencias")
@Tag(name = "Incidencias", description = "Catálogo de incidencias")
public class IncidenciaController {

    @Autowired
    private IIncidenciaService service;

    @GetMapping
    @Operation(summary = "Obtiene el listado de incidencias")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe el listado, aunque puede que vacio",
                    content = @Content(schema = @Schema(implementation = Incidencia.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede obtener el listado",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<List<Incidencia>> listar() {
        List<Incidencia> lista = service.listar();

        // Código 200 OK para select
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Registra una nueva incidencia")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Se registra la incidencia",
                    content = @Content(schema = @Schema(implementation = Incidencia.class)))
    })
    public ResponseEntity<Incidencia> registrar(@RequestBody String nombre, String tipo, String zona, String descripcion, String fecha, Integer alumnoNIA, String estado) {
        Incidencia inc = new Incidencia(nombre, tipo, zona, descripcion, fecha, alumnoNIA, estado);
        Incidencia obj = service.registrar(inc);

        // Código 201 CREATED para insert
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Modifica una incidencia existente por id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe",
                    content = @Content(schema = @Schema(implementation = Incidencia.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<Incidencia> modificar(@RequestBody Incidencia incidencia) {
        Incidencia obj = service.modificar(incidencia);

        // Código 200 OK para update
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina una incidencia existente por id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe",
                    content = @Content(schema = @Schema(implementation = Incidencia.class))),
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

    @GetMapping("/{nia}")
    @Operation(summary = "Obtiene el listado de incidencias dado un usuario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe el listado, aunque puede que vacio",
                    content = @Content(schema = @Schema(implementation = Incidencia.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede obtener el listado",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<List<Incidencia>> listarNIA(@PathVariable Integer nia) {
        List<Incidencia> lista = service.buscarporNIA(nia);

        // Código 200 OK para select
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /////////////////////////////////////////
    // Métodos específicos de esta entidad //
    /////////////////////////////////////////
    @DeleteMapping("/eliminarIncidencia")
    @Operation(summary = "Elimina una incidencia por nombre")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe",
                    content = @Content(schema = @Schema(implementation = Incidencia.class))),
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

    @GetMapping("/busquedaPorZona/{zona}")
    @Operation(summary = "Busca las incidencias por zona")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa (puede devolver lista vacía)",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay incidencias en esa zona (suerte la vuestra)",
                    content = @Content)
    })
    public ResponseEntity<List<Incidencia>> buscarIncidenciasZona(@PathVariable String zona) {
        List<Incidencia> incidencias = service.listarBusquedaPorZona(zona);

        if (incidencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(incidencias);
    }
}
