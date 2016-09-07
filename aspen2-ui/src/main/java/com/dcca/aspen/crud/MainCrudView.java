package com.dcca.aspen.crud;

import java.util.Collection;

import com.dcca.aspen.backend.data.Enrollment;
import com.dcca.aspen.samples.ResetButtonForTextField;
import com.dcca.aspen.samples.backend.DataService;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid.SelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A view for performing create-read-update-delete operations on enrollments.
 *
 * See also {@link MainCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class MainCrudView extends CssLayout implements View {

    public static final String VIEW_NAME = "Enrollments";
    private EnrollmentGrid grid;
    private CMS855IFormImpl form;

    private MainCrudLogic viewLogic = new MainCrudLogic(this);
    private Button newenrollment;

    public MainCrudView() {
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout topLayout = createTopBar();

        grid = new EnrollmentGrid();
        grid.addSelectionListener(new SelectionListener() {

            @Override
            public void select(SelectionEvent event) {
                viewLogic.rowSelected(grid.getSelectedRow());
            }
        });

        form = new CMS855IFormImpl(viewLogic);
       // form.setCategories(DataService.get().getAllCategories());

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.addComponent(topLayout);
        barAndGridLayout.addComponent(grid);
        barAndGridLayout.setMargin(true);
        barAndGridLayout.setSpacing(true);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setExpandRatio(grid, 1);
        barAndGridLayout.setStyleName("crud-main-layout");

        addComponent(barAndGridLayout);
        addComponent(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("Filter");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                grid.setFilter(event.getText());
            }
        });

        newenrollment = new Button("New enrollment");
        newenrollment.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newenrollment.setIcon(FontAwesome.PLUS_CIRCLE);
        newenrollment.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                viewLogic.newEnrollment();
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth("100%");
        topLayout.addComponent(filter);
        topLayout.addComponent(newenrollment);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        viewLogic.enter(event.getParameters());
    }

    public void showError(String msg) {
        Notification.show(msg, Type.ERROR_MESSAGE);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg, Type.TRAY_NOTIFICATION);
    }

    public void setNewenrollmentEnabled(boolean enabled) {
        newenrollment.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().reset();
    }

    public void selectRow(Enrollment row) {
        ((SelectionModel.Single) grid.getSelectionModel()).select(row);
    }

    public Enrollment getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void editenrollment(Enrollment enrollment) {
        if (enrollment != null) {
            form.addStyleName("visible");
            form.setEnabled(true);
        } else {
            form.removeStyleName("visible");
            form.setEnabled(false);
        }
        form.editEnrollment(enrollment);
    }

    public void showenrollments(Collection<Enrollment> enrollments) {
        grid.setEnrollments(enrollments);
    }

    public void refreshenrollment(Enrollment enrollment) {
        grid.refresh(enrollment);
        grid.scrollTo(enrollment);
    }

    public void removeenrollment(Enrollment enrollment) {
        grid.remove(enrollment);
    }

}
