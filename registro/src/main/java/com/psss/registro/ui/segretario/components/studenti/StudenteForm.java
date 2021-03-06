package com.psss.registro.ui.segretario.components.studenti;


import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.ServiceFacade;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.time.Year;

class StudenteForm extends FormLayout {

    private final TextField nome = new TextField("Nome");
    private final TextField cognome= new TextField("Cognome");
    private final TextField codiceFiscale = new TextField("Codice Fiscale");
    private final EmailField username = new EmailField("Email");
    private final DatePicker data = new DatePicker("Data di nascita");
    private final ComboBox<Character> sesso = new ComboBox<>("Sesso");
    private final TextField telefono = new TextField("Telefono");
    private final ComboBox<Classe> classe = new ComboBox<>("Classe");

    private final Binder<Studente> binder = new BeanValidationBinder<>(Studente.class);

    private ServiceFacade serviceFacade;

    public StudenteForm(ServiceFacade serviceFacade) {

        this.serviceFacade = serviceFacade;

        nome.setClearButtonVisible(true);

        cognome.setClearButtonVisible(true);

        codiceFiscale.setClearButtonVisible(true);
        codiceFiscale.addValueChangeListener(e-> {
            codiceFiscale.setValue(codiceFiscale.getValue().toUpperCase());
        });

        sesso.setItems('M','F');

        username.setClearButtonVisible(true);

        classe.setItems(serviceFacade.findClasseByAnnoScolastico(Year.now().getValue()));

        add(nome, cognome, codiceFiscale, username, data, sesso, telefono, classe);
        binder.bindInstanceFields(this);
    }


    public Binder<Studente> getBinder() {
        return binder;
    }

    public TextField getNome() {
        return nome;
    }
}
