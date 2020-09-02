package com.psss.registro.services;

import com.psss.registro.models.Docente;
import com.psss.registro.repositories.DocenteRepository;
import com.psss.registro.security.UserAuthority;
import com.psss.registro.security.UserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public List<Docente> findAll(String filtro) {
        if(filtro == null || filtro.isEmpty()) {
            return docenteRepository.findAll();
        } else {
            return docenteRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(filtro, filtro);
        }
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    public Docente createDocente(Docente d) {

        UserAuthority authority = userAuthorityRepository.findByAuthority("DOCENTE").get();

        d.setUserAuthority(authority);
        authority.addUser(d);

        userAuthorityRepository.saveAndFlush(authority);
        return docenteRepository.saveAndFlush(d);
    }

    public Docente updateDocente(Docente docente, Docente docenteTemp) {
        docente.setNome(docenteTemp.getNome());
        docente.setCognome(docenteTemp.getCognome());
        docente.setCodiceFiscale(docenteTemp.getCodiceFiscale());
        docente.setSesso(docenteTemp.getSesso());
        docente.setData(docenteTemp.getData());
//        docente.setEmail(docenteTemp.getEmail());
        docente.setUsername(docenteTemp.getUsername());
        docente.setTelefono(docenteTemp.getTelefono());

        return docenteRepository.saveAndFlush(docente);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }

    public Long deleteByNomeAndCognome(String nome, String cognome) {
        return docenteRepository.deleteByNomeAndCognome(nome, cognome);
    }

    public Docente getOne(Long id) {
        return docenteRepository.getOne(id);
    }
}
