package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.repositories.ClasseRepository;
import com.psss.registro.backend.repositories.DocenteRepository;
import com.psss.registro.backend.repositories.MateriaRepository;
import com.psss.registro.security.UserAuthority;
import com.psss.registro.security.UserAuthorityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class DocenteService implements CrudService<Docente> {

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ClasseRepository classeRepository;

//    public List<Docente> findAll() {
//        return docenteRepository.findAll();
//    }
//
//    public Optional<Docente> findById(Long id) {
//        return docenteRepository.findById(id);
//    }
//
//    public void deleteById(Long id) {
//        docenteRepository.deleteById(id);
//    }


    @Override
    public JpaRepository<Docente, Long> getRepository() {
        return docenteRepository;
    }

    public Docente create(Docente docente) {

        UserAuthority authority = userAuthorityRepository.findByAuthority("DOCENTE").get();
        authority.addUser(docente);
        userAuthorityRepository.saveAndFlush(authority);

        docente.setUserAuthority(authority);

        // TODO: il docente va aggiunto alla materia qua o nel model?
        for (Materia materia : docente.getMaterie()) {
            materia.addDocente(docente);
            materiaRepository.saveAndFlush(materia);
            // TODO per guido: è necessario fare il save and flush della classe? (fatto)
        }

//        for (Classe classe : classi) {
//            classe.addDocente(docente);
//            classeRepository.saveAndFlush(classe);
//        }

        return getRepository().saveAndFlush(docente);
    }

    public Docente update(Docente docenteOld, Docente docenteNew) {

        docenteOld.setUsername(docenteNew.getUsername());
        docenteOld.setNome(docenteNew.getNome());
        docenteOld.setCognome(docenteNew.getCognome());
        docenteOld.setCodiceFiscale(docenteNew.getCodiceFiscale());
        docenteOld.setSesso(docenteNew.getSesso());
        docenteOld.setData(docenteNew.getData());
        docenteOld.setTelefono(docenteNew.getTelefono());

        // Aggiunge le nuove materie al docente ed aggiunge il docente alla nuove materie
        for (Materia materia : docenteNew.getMaterie()) {
            if(!docenteOld.getMaterie().contains(materia)) {
                docenteOld.addMateria(materia);
                materia.addDocente(docenteOld);
                materiaRepository.saveAndFlush(materia);
            }
        }

        // TODO: risolvere un bug dovuto ad un null pointer
        // Rimuove le vecchie materia al docente e rimuove il docente dalle vecchie materie
        Set<Materia> materiaSet = new HashSet<>(docenteOld.getMaterie());
        for (Materia materia : materiaSet) {
            if(!docenteNew.getMaterie().contains(materia)) {
                docenteOld.removeMateria(materia);
                materia.removeDocente(docenteOld);
                materiaRepository.saveAndFlush(materia);
            }
        }

        // Aggiunge le nuove classi al docente ed aggiunge il docente alla nuove classi
//        for (Classe classe : classi) {
//            if(!docente.getClassi().contains(classe)) {
//                docente.addClasse(classe);
//                classe.addDocente(docente);
//                classeRepository.saveAndFlush(classe);
//            }
//        }
//
//        // Rimuove le vecchie classi al docente e rimuove il docente dalle vecchie classi
//        Set<Classe> classeSet = new HashSet<>(docente.getClassi());
//        for (Classe classe : classeSet) {
//            if(!classi.contains(classe)) {
//                docente.removeClasse(classe);
//                classe.removeDocente(docente);
//                classeRepository.saveAndFlush(classe);
//            }
//        }

        return getRepository().saveAndFlush(docenteOld);
    }

}