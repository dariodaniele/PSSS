package com.psss.registro.ui.segretario.components.studenti;

import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.ServiceFacade;

import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


class StudenteGrid extends Div implements AbstractGrid {

    private final Grid<Studente> grid = new Grid<>(Studente.class);

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");

    private StudenteDialog dialog;

    private StudenteEditor editor;

    private ServiceFacade serviceFacade;

    private List<Studente> studenti;

    public StudenteGrid(ServiceFacade serviceFacade) {
        setId("grid-wrapper");
        setWidthFull();

        this.serviceFacade = serviceFacade;

        studenti = this.serviceFacade.findAllStudenti();

        grid.setColumns("nome", "cognome","codiceFiscale","username","data", "sesso" ,"telefono","classe");
        grid.getColumnByKey("username").setHeader("E-mail");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(studenti);
        grid.asSingleSelect().addValueChangeListener(event -> {
            Studente studente = event.getValue();

            editor.getForm().getBinder().readBean(studente);
            editor.setVisible(!event.getHasValue().isEmpty());
        });

        add(createToolbarLayout(), grid);
    }

    private HorizontalLayout createToolbarLayout() {
        HorizontalLayout toolBarLayout = new HorizontalLayout();
        toolBarLayout.setId("button-layout");
        toolBarLayout.setWidthFull();
        toolBarLayout.setSpacing(false);
        toolBarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        filtro.setPlaceholder("Cerca...");
        filtro.setClearButtonVisible(true);
        filtro.setValueChangeMode(ValueChangeMode.LAZY);
        filtro.addValueChangeListener(event -> {
            Set<Studente> foundStudenti = studenti.stream()
                    .filter(studente -> studente.getNome().toLowerCase()
                            .startsWith(event.getValue().toLowerCase()) ||
                            studente.getCognome().toLowerCase()
                                    .startsWith(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            grid.setItems(foundStudenti);
        });

        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiungi.addClickListener(event -> {
            dialog = new StudenteDialog(this.serviceFacade);
            dialog.setGrid(this);
            dialog.open();
        });

        toolBarLayout.add(filtro, aggiungi);

        return toolBarLayout;
    }

    public Grid<Studente> getGrid() {
        return grid;
    }

    public List<Studente> getStudenti() {
        return studenti;
    }

    public void setEditor(AbstractEditor editor) {
        this.editor = (StudenteEditor) editor;
    }
}

