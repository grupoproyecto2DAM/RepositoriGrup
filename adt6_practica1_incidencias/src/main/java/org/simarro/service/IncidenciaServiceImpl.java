package org.simarro.service;

import jakarta.transaction.Transactional;
import org.simarro.model.Incidencia;
import org.simarro.repository.IIncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidenciaServiceImpl implements IIncidenciaService {

    @Autowired
    private IIncidenciaRepository repo;

    @Override
    public Incidencia registrar(Incidencia incidencia) {
        return repo.save(incidencia);
    }

    @Override
    public Incidencia modificar(Incidencia incidencia) {
        return repo.save(incidencia);
    }

    @Override
    public List<Incidencia> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Métodos específicos de esta entidad

    @Override
    @Transactional
    public void eliminarPorTitulo(String titulo) {
        //Opción 1 - Eliminar
        repo.eliminarPorTitulo(titulo);

        //Opción 2 - Eliminar
        // repo.deleteByTitulo(titulo);
    }

    @Override
    public boolean existsByTitulo(String titulo) {
        // Opción 1 - ExisteDestino
        return repo.existsByTitulo(titulo);

        // Opción 2 - ExisteDestino
        // return repo.existePorFecha(fecha);

    }

    @Override
    public List<Incidencia> listarBusquedaFiltrada1(String titulo, String tipo, String zona) {

        if (titulo != null && tipo != null && zona != null) {
            return repo.findByTituloAndTipoAndZona(titulo, tipo, zona);
        } else if (titulo != null && tipo != null) {
            return repo.findByTituloAndTipo(titulo, tipo);
        } else if (titulo != null && zona != null) {
            return repo.findByTituloAndZona(titulo, zona);
        } else if (tipo != null && zona != null) {
            return repo.findByTipoAndZona(tipo, zona);
        } else if (titulo != null) {
            return repo.findByTitulo(titulo);
        } else if (tipo != null) {
            return repo.findByTipo(tipo);
        } else if (zona != null) {
            return repo.findByZona(zona);
        } else {
            return repo.findAll();
        }
    }

    @Override
    public List<Incidencia> listarBusquedaFiltrada2(String titulo, String tipo, String zona) {

        List<Incidencia> todos = repo.findAll();

        List<Incidencia> resultados = new ArrayList<>();

        // En este ejemplo, en el controlador se pasan obligatoriamente
        // todos los parámetros. Por eso la condición (if) es fácil.
        // Si suponemos que podemos pasar el número de parámetros que
        // queramos, deberíamos hacer más condiciones para cada caso.
        for (Incidencia incidencia : todos) {
            if (incidencia.getTitulo().toLowerCase().equals(titulo) &&
                    incidencia.getTipo().toLowerCase().equals(tipo) &&
                    incidencia.getZona().equalsIgnoreCase(zona)) {

                resultados.add(incidencia);
            }
        }
        return resultados;
    }
}
