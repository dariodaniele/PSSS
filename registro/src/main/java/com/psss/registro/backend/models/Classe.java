package com.psss.registro.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = {"studenti", "insegnamenti"}, callSuper = false)
@Entity(name = "classi")
public class Classe extends AbstractEntity{

    @Min(1)
    @Max(5)
    private int anno;

    @NotNull(message = "Selezionare la sezione")
    private Character sezione;

    @NotNull(message = "Inserire l'anno scolastico")
    private int annoScolastico;

    @OneToMany(mappedBy = "classe", fetch = FetchType.LAZY) @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Studente> studenti;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.REMOVE) @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Insegnamento> insegnamenti;

    public Classe(int anno, Character sezione, int annoScolastico) {
        this.anno = anno;
        this.sezione = sezione;
        this.annoScolastico = annoScolastico;
        this.studenti = new HashSet<>();
        this.insegnamenti = new HashSet<>();
    }

    public void addStudente(Studente studente) {
        studenti.add(studente);
    }

    @PreRemove
    public void preRemove(){
        studenti.forEach(studente -> studente.setClasse(null));
    }

    public String toString() {
        return this.anno + this.sezione.toString() + " - " + this.annoScolastico;
    }
}
