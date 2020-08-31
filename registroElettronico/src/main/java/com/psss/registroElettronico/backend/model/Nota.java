package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.GregorianCalendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "note")
public class Nota {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private GregorianCalendar data;
    private String testo;


    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="studente_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Studente studente;
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="docente_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    //@JsonManagedReference
    private Docente docente;


}
