package com.dcca.aspen.crud;

import com.dcca.aspen.MyUI;
import com.dcca.aspen.backend.data.Enrollment;
import com.dcca.aspen.samples.authentication.CurrentUser;
import com.dcca.aspen.samples.backend.BPMService;
import com.dcca.aspen.samples.backend.AspenDataService;

import java.io.Serializable;

import com.vaadin.server.Page;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the enrollment editor form and the data source, including
 * fetching and saving enrollments.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class MainCrudLogic implements Serializable {

    private MainCrudView view;

    public MainCrudLogic(MainCrudView simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editEnrollment(null);
        // Hide and disable if not admin
        if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewenrollmentEnabled(false);
        }

        view.showenrollments(AspenDataService.get().getAllEnrollments());
    }

    public void cancelEnrollment() {
        setFragmentParameter("");
        view.clearSelection();
        view.editenrollment(null);
        BPMService.getInstance().start855Process(CurrentUser.get());
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String enrollmentId) {
        String fragmentParameter;
        if (enrollmentId == null || enrollmentId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = enrollmentId;
        }

        Page page = MyUI.get().getPage();
        page.setUriFragment("!" + MainCrudView.VIEW_NAME + "/"
                + fragmentParameter, false);
    }

    public void enter(String enrollmentId) {
        if (enrollmentId != null && !enrollmentId.isEmpty()) {
            if (enrollmentId.equals("new")) {
                newEnrollment();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(enrollmentId);
                    Enrollment enrollment = findenrollment(pid);
                    view.selectRow(enrollment);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private Enrollment findenrollment(int enrollmentId) {
        return AspenDataService.get().getEnrollmentById(enrollmentId);
    }

    public void saveEnrollment(Enrollment enrollment) {
        view.showSaveNotification(enrollment.getBusName() + " ("
                + enrollment.getId() + ") updated");
        view.clearSelection();
        view.editenrollment(null);
        view.refreshenrollment(enrollment);
        setFragmentParameter("");
    }

    public void deleteEnrollment(Enrollment enrollment) {
        AspenDataService.get().deleteEnrollment(enrollment.getId());
        view.showSaveNotification(enrollment.getBusName() + " ("
                + enrollment.getId() + ") removed");

        view.clearSelection();
        view.editenrollment(null);
        view.removeenrollment(enrollment);
        setFragmentParameter("");
    }

    public void editEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(enrollment.getId() + "");
        }
        view.editenrollment(enrollment);
    }

    public void newEnrollment() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editenrollment(new Enrollment());
    }

    public void rowSelected(Enrollment enrollment) {
     
            view.editenrollment(enrollment);
        }
    }

