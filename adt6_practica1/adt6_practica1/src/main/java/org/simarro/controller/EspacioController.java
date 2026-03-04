package org.simarro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.simarro.model.Espacio;
import org.simarro.service.Incidencia.IEspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/espacios")
@Tag(name = "Espacios", description = "Catálogo de espacios")
public class EspacioController {

    @Autowired
    private IEspacioService service;

    @GetMapping
    @Operation(summary = "Obtiene el listado de incidencias")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe el listado, aunque puede que vacio",
                    content = @Content(schema = @Schema(implementation = Espacio.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede obtener el listado",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<List<Espacio>> listar() {

        List<Espacio> lista = service.listar();
        // Código 200 OK para select
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Registra una nueva incidencia")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Se registra la incidencia",
                    content = @Content(schema = @Schema(implementation = Espacio.class)))
    })
    public ResponseEntity<Espacio> registrar(@RequestBody Espacio espacio) {
        Espacio obj = service.registrar(espacio);

        // Código 201 CREATED para insert
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Modifica una incidencia existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe",
                    content = @Content(schema = @Schema(implementation = Espacio.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<Espacio> modificar(@RequestBody Espacio espacio) {
        Espacio obj = service.modificar(espacio);

        // Código 200 OK para update
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una incidencia existente por id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe",
                    content = @Content(schema = @Schema(implementation = Espacio.class))),
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
                    content = @Content(schema = @Schema(implementation = Espacio.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<Void> eliminarPorNombre(@RequestParam(value = "nombre") String nombre) {

        if (!service.existsByNombre(nombre)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.eliminarPorNombre(nombre);

        // Código 204 NOT CONTENT para delete
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    // Búsqueda filtrada sin métodos específicos
    @GetMapping("/busquedaFiltrada2")
    @Operation(summary = "Busca las incidencias con un filtro2")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Existe el listado, aunque puede que vacio",
                    content = @Content(schema = @Schema(implementation = Espacio.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede obtener el listado",
                    content = @Content(schema = @Schema(implementation=ResponseEntity.class)))
    })
    public ResponseEntity<List<Espacio>> buscarIncidencias2(
            @RequestParam(value = "titulo", required = true) String nombre){

        // Obtengo todos las incidencias filtradas
        List<Espacio> resultados  = service.listarBusquedaFiltrada2(nombre);

        if(resultados.isEmpty()) {
            // Código 204 NoData para select
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            // Código 200 OK para select
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        }

    }


}
