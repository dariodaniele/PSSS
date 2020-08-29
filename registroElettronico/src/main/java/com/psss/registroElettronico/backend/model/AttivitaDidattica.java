package com.psss.registroElettronico.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.GregorianCalendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attivitadidattiche")
public class AttivitaDidattica {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private GregorianCalendar data;
    private String testo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Docente docente;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classe classe;

}
