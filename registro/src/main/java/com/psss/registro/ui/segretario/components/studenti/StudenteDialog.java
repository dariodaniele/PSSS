package com.psss.registro.ui.segretario.components.studenti;

import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.ServiceFacade;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

class StudenteDialog extends Dialog{

    private final StudenteForm form;

    private final Button conferma = new Button("Conferma");

    private StudenteGrid grid;

    private ServiceFacade serviceFacade;

    public StudenteDialog(ServiceFacade serviceFacade) {
        setId("editor-layout");

        this.serviceFacade = serviceFacade;

        form = new StudenteForm(this.serviceFacade);

        Label titolo = new Label("Nuovo Studente");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        form.getNome().setAutofocus(true);

        add(formDiv, createButtonLayout(formDiv));

        addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
                form.getBinder().readBean(null);
            }
        });
    }

    private HorizontalLayout createButtonLayout(Div formDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(false);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        conferma.addClickShortcut(Key.ENTER).listenOn(formDiv);
        conferma.setEnabled(false);
        conferma.addClickListener(e -> {
                    Studente studente = new Studente();
                    form.getBinder().writeBeanIfValid(studente);
                    Notification notification = new Notification();
                    notification.setDuration(3000);
                    if (serviceFacade.saveStudente(studente)) {
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        notification.setText("Studente inserito con successo!");
                        notification.open();
                        grid.getStudenti().add(studente);
                        grid.getGrid().setItems(grid.getStudenti());
                        close();
                    } else {
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        notification.setText("Attenzione: non è possibile inserie lo studente!");
                        notification.open();
                    }
            });

        form.getBinder().addStatusChangeListener(e -> conferma.setEnabled(form.getBinder().isValid()));

        buttonLayout.add(conferma);

        return buttonLayout;
    }

    public void setGrid(StudenteGrid grid) {
            this.grid = grid;
        }

}
