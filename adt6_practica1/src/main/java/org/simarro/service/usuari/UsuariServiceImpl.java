package org.simarro.service.usuari;

import jakarta.transaction.Transactional;
import org.simarro.model.Usuari;
import org.simarro.repository.IUsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariServiceImpl implements IUsuariService {

    @Autowired
    private IUsuariRepository repo;

    @Override
    public Usuari registrar(Usuari usuari) {
        return repo.save(usuari);
    }

    @Override
    public Usuari modificar(Usuari usuari) {
        return repo.save(usuari);
    }

    @Override
    public List<Usuari> listar() {
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
        //Opción 1 - EliminarPorNombre
        repo.eliminarPorNombre(nombre);

    }

    @Override
    public boolean existsByNombre(String nombre) {
        // Opción 1 - ExisteNombre
        return repo.existsByNombre(nombre);


    }
}
