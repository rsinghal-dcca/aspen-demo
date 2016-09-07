package com.dcca.aspen.backend.data;

import java.util.ArrayList;
import java.util.Collection;

import com.dcca.aspen.samples.backend.AspenDataService;
import com.dcca.aspen.samples.backend.DataService;
import com.dcca.aspen.samples.backend.data.Category;
import com.dcca.aspen.samples.backend.mock.MockDataService;

public class AspenDataServiceImpl extends AspenDataService {
	  private static AspenDataServiceImpl INSTANCE;
	  
	  public synchronized static AspenDataService getInstance() {
	        if (INSTANCE == null) {
	            INSTANCE = new AspenDataServiceImpl();
	        }
	        return INSTANCE;
	    }
	@Override
	public Collection<Enrollment> getAllEnrollments() {
		ArrayList<Enrollment> list=new ArrayList<Enrollment>();
		Enrollment e1=new Enrollment();
		e1.setBusName("Name 1");
		e1.setnpi(12345);
		e1.setTrackingid(98675);
		e1.setId(1234);
		
		
		Enrollment e2 =new Enrollment();
		e2.setBusName("Name 2");
		e2.setnpi(12346);
		e2.setTrackingid(98685);
		e2.setId(1224);
		list.add(e1);
		list.add(e2);
		return list;
	}

	@Override
	public Collection<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEnrollment(Enrollment p) {
		if (p.getId() < 0) {
            // New product
            p.setId(123);
          //  products.add(p);
            return;
        }
//        for (int i = 0; i < products.size(); i++) {
//            if (products.get(i).getId() == p.getId()) {
//                products.set(i, p);
//                return;
//            }
//        }

        throw new IllegalArgumentException("No product with id " + p.getId()
                + " found");

	}

	@Override
	public void deleteEnrollment(int EnrollmentId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Enrollment getEnrollmentById(int EnrollmentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
