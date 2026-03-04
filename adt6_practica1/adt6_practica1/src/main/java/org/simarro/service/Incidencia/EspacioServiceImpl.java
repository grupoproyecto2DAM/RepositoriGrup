package org.simarro.service.Incidencia;

import jakarta.transaction.Transactional;
import org.simarro.model.Espacio;
import org.simarro.model.enums.CategoriaIncidencia;
import org.simarro.model.enums.TipoEspacio;
import org.simarro.repository.IEspacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EspacioServiceImpl implements IEspacioService {

    @Autowired
    private IEspacioRepository repo;

    @Override
    public Espacio registrar(Espacio espacio) {
        return repo.save(espacio);
    }

    @Override
    public Espacio modificar(Espacio espacio) {
        return repo.save(espacio);
    }

    @Override
    public List<Espacio> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Métodos específicos de esta entidad

    @Override
    @Transactional
    public void eliminarPorNombre(String nombre) {
        //Opción 1 - Eliminar
        repo.eliminarPorNombre(nombre);

        //Opción 2 - Eliminar
        // repo.deleteByTitulo(titulo);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        // Opción 1 - ExisteDestino
        return repo.existsByNombre(nombre);

        // Opción 2 - ExisteDestino
        // return repo.existePorFecha(fecha);

    }


    @Override
    public List<Espacio> listarBusquedaFiltrada2(String nombre) {

        List<Espacio> todos = repo.findAll();

        List<Espacio> resultados = new ArrayList<>();

        // En este ejemplo, en el controlador se pasan obligatoriamente
        // todos los parámetros. Por eso la condición (if) es fácil.
        // Si suponemos que podemos pasar el número de parámetros que
        // queramos, deberíamos hacer más condiciones para cada caso.
        for (Espacio espacio : todos) {
            if (espacio.getNombre().toLowerCase().equals(nombre)) {

                resultados.add(espacio);
            }
        }
        return resultados;
    }
}
