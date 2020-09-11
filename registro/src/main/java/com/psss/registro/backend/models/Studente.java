package com.psss.registro.backend.models;

import com.psss.registro.app.security.User;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "studenti") @ToString(exclude = {"classe"}) @EqualsAndHashCode(exclude = {"classe", "storicoClassi"})
public class Studente extends User {
    @NotBlank(message = "Inserire il nome")
    @Size(min = 1, max = 50, message = "Il nome deve essere compreso fra 1 e 50 caratteri")
    private String nome;
    @NotBlank(message = "Inserire il cognome")
    @Size(min = 1, max = 50, message = "Il cognome deve essere compreso fra 1 e 50 caratteri")
    private String cognome;
    //@Column(unique=true)
    @NotBlank(message = "Inserire il codice Fiscale")
    @Size(min = 1, max = 16, message = "Il codice fiscale deve essere di 16 caratteri")
    @Pattern(regexp = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$", message = "Inserire un codice fiscale valido")
    private String codiceFiscale;
    @Past(message = "La data dev'essere nel passato")
    @NotNull(message = "Inserire la data")
    private LocalDate data;
    @NotNull(message = "Selezionare il sesso")
    private Character sesso;
    @NotBlank(message = "Inserire il numero di telefono")
    @Size(min = 10, max = 10, message = "Il numero di telefono dev'essere di 10 cifre")
    @Digits(message = "Inserire un numero di telefono valido", integer = 10, fraction = 0)
    private String telefono;
    
    @ManyToOne
    private Classe classe;
    @ManyToMany
    private Set<Classe> storicoClassi;
    //TODO: non si riesce a tenere traccia delle classi di uno studente così come lo abbiamo gestito.

    public void setClasse(Classe classe){

        this.classe = classe;
        if (this.classe != null) {
            classe.addStudente(this);
        }
    }

    public String toString() {
        return nome + " " + cognome;
    }
}