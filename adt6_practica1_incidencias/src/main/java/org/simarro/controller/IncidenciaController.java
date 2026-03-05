package org.simarro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.simarro.model.Incidencia;
import org.simarro.service.IIncidenciaService;
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
    public ResponseEntity<Incidencia> registrar(@RequestBody Incidencia incidencia) {
        Incidencia obj = service.registrar(incidencia);

        // Código 201 CREATED para insert
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Modifica una incidencia existente")
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

    @DeleteMapping("/{id}")
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

    /////////////////////////////////////////
    // Métodos específicos de esta entidad //
    /////////////////////////////////////////
    @DeleteMapping("/eliminarDestino")
    @Operation(summary = "Elimina una incidencia")
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
    public ResponseEntity<Void> eliminarPorDestino(@RequestParam(value = "titulo") String titulo) {

        if (!service.existsByTitulo(titulo)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.eliminarPorTitulo(titulo);

        // Código 204 NOT CONTENT para delete
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Opción 1 - Búsqueda filtrada con métodos específicos
    @GetMapping("/busquedaFiltrada1")
    @Operation(summary = "Busca las incidencias con un filtro")
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
    public ResponseEntity<List<Incidencia>> buscarIncidencias1(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String zona) {

        List<Incidencia> incidencias = service.listarBusquedaFiltrada1(titulo, tipo, zona);

        if(incidencias.isEmpty()) {
            // Código 204 NoData para select
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            // Código 200 OK para select
            return new ResponseEntity<>(incidencias, HttpStatus.OK);
        }
    }

    // Opción 2 - Búsqueda filtrada sin métodos específicos
    @GetMapping("/busquedaFiltrada2")
    @Operation(summary = "Busca las incidencias con un filtro2")
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
    public ResponseEntity<List<Incidencia>> buscarIncidencias2(
            @RequestParam(value = "origen", required = true) String origen,
            @RequestParam(value = "destino", required = true) String destino,
            @RequestParam(value = "escalas", required = true) String escalas) {

        // Obtengo todos las incidencias filtradas
        List<Incidencia> resultados  = service.listarBusquedaFiltrada2(origen, destino, escalas);

        if(resultados.isEmpty()) {
            // Código 204 NoData para select
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            // Código 200 OK para select
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        }

    }


}
