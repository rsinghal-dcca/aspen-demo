package com.dcca.aspen.samples.backend;

import java.io.Serializable;
import java.util.Collection;

import com.dcca.aspen.backend.data.AspenDataServiceImpl;
import com.dcca.aspen.backend.data.Enrollment;
import com.dcca.aspen.samples.backend.data.Category;
import com.dcca.aspen.samples.backend.mock.MockDataService;

/**
 * Back-end service interface for retrieving and updating Enrollment data.
 */
public abstract class AspenDataService implements Serializable {

    public abstract Collection<Enrollment> getAllEnrollments();

    public abstract Collection<Category> getAllCategories();

    public abstract void updateEnrollment(Enrollment p);

    public abstract void deleteEnrollment(int EnrollmentId);

    public abstract Enrollment getEnrollmentById(int EnrollmentId);

    public static AspenDataService get() {
        return AspenDataServiceImpl.getInstance();
    }

}
