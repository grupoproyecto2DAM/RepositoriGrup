package org.simarro.service.Incidencia;

import jakarta.transaction.Transactional;
import org.simarro.model.Incidencia;
import org.simarro.repository.IIncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Incidencia> listar() {
        return repo.findAll();
    }

    @Override
    public List<Incidencia> buscarporNIA(Integer nia) {
        return repo.findByAlumnoNIA(nia);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Métodos específicos de esta entidad

    @Override
    @Transactional
    public void eliminarPorNombre(String nombre) {
        //Opción 1 - EliminarPorNombre
        repo.eliminarPorNombre(nombre);

    }

    @Override
    public boolean existsByNombre(String nombre) {
        // Opción 1 - ExisteNombre
        return repo.existsByNombre(nombre);


    }

    @Override
    public List<Incidencia> listarBusquedaPorZona(String zona) {
        return repo.findAll().stream()
                .filter(i -> i.getZona().equalsIgnoreCase(zona))
                .toList();
    }
}
