package com.dcca.aspen.crud;

import java.util.Collection;

import com.dcca.aspen.CMS855IForm;
import com.dcca.aspen.backend.data.AspenDataServiceImpl;
import com.dcca.aspen.backend.data.Enrollment;
import com.dcca.aspen.samples.backend.DataService;
//import com.dcca.aspen.samples.backend.data.Availability;
//import com.dcca.aspen.samples.backend.data.Category;
//import com.dcca.aspen.samples.backend.data.Enrollment;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.*;

/**
 * A form for editing a single Enrollment.
 *
 * Using responsive layouts, the form can be displayed either sliding out on the
 * side of the view or filling the whole screen - see the theme for the related
 * CSS rules.
 */
public class CMS855IFormImpl extends VerticalLayout {

    private MainCrudLogic viewLogic;
    private BeanFieldGroup<Enrollment> fieldGroup;
    Label lblTitle;
    Label lblSection;
	protected Button save;
	protected Button cancel;
	//protected Button delete;
	TextField busName;
    TextField npi;

    FormLayout form;
    HorizontalLayout footer;
    
    public CMS855IFormImpl(MainCrudLogic sampleCrudLogic) {
        super();
        setId("product-form");
        addStyleName("product-form-wrapper");
        addStyleName("product-form");
        viewLogic = sampleCrudLogic;
        
        
        lblTitle = new Label("New Enrollment");
        lblTitle.addStyleName("h1");
        addComponent(lblTitle);
        //price.setConverter(new EuroConverter());

       // for (Availability s : Availability.values()) {
       //     availability.addItem(s);
       // }

        form = new FormLayout();
        form.setMargin(false);
        form.setWidth("900");
        form.addStyleName("light");
   
        
        
        busName=new TextField("Business name");
        busName.setRequired(true);
        form.addComponent(busName);
        
        npi=new TextField("NPI");
        npi.setRequired(true);
        form.addComponent(npi);
        
        save=new Button("Save");
        save.addStyleName("primary");
        
        cancel=new Button("Cancel");
        cancel.addStyleName("primary");
        
        cancel=new Button("Cancel");
        cancel.addStyleName("primary");
        
        fieldGroup = new BeanFieldGroup<Enrollment>(Enrollment.class);
        fieldGroup.bindMemberFields(this);

        // perform validation and enable/disable buttons while editing
        ValueChangeListener valueListener = new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                formHasChanged();
            }
        };
        for (Field f : fieldGroup.getFields()) {
            f.addValueChangeListener(valueListener);
        }

        fieldGroup.addCommitHandler(new CommitHandler() {

            @Override
            public void preCommit(CommitEvent commitEvent)
                    throws CommitException {
            }

            @Override
            public void postCommit(CommitEvent commitEvent)
                    throws CommitException {
                AspenDataServiceImpl.get().updateEnrollment(
                        fieldGroup.getItemDataSource().getBean());
            }
        });

        save.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    fieldGroup.commit();

                    // only if validation succeeds
                    Enrollment Enrollment = fieldGroup.getItemDataSource().getBean();
                    viewLogic.saveEnrollment(Enrollment);
                } catch (CommitException e) {
                    Notification n = new Notification(
                            "Please re-check the fields", Type.ERROR_MESSAGE);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                }
            }
        });

        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                viewLogic.cancelEnrollment();
            }
        });

     /*   delete.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Enrollment Enrollment = fieldGroup.getItemDataSource().getBean();
                viewLogic.deleteEnrollment(Enrollment);
            }
        });*/
        
        //horizontal layout for button
        footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true,false,true,false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
      
        footer.addComponent(save);
        footer.addComponent(cancel);
        
        form.addComponent(footer);
        addComponent(form);
    }

//    public void setCategories(Collection<Category> categories) {
//        category.setOptions(categories);
//    }

    public void editEnrollment(Enrollment Enrollment) {
        if (Enrollment == null) {
            Enrollment = new Enrollment();
        }
        fieldGroup.setItemDataSource(new BeanItem<Enrollment>(Enrollment));

        // before the user makes any changes, disable validation error indicator
        // of the Enrollment name field (which may be empty)
        busName.setValidationVisible(false);

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }

    private void formHasChanged() {
        // show validation errors after the user has changed something
        busName.setValidationVisible(true);

        // only Enrollments that have been saved should be removable
        boolean canRemoveEnrollment = false;
        BeanItem<Enrollment> item = fieldGroup.getItemDataSource();
        if (item != null) {
            Enrollment Enrollment = item.getBean();
            canRemoveEnrollment = Enrollment.getId() != -1;
        }
      //  delete.setEnabled(canRemoveEnrollment);
    }

	
}
