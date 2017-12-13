package com.cvent;

/**
 * Created by a.srivastava on 7/19/17.
 */
public class DaoFactory {
    //Create Thread Scoped Connection Object
    public void beginConnectionScope() {
    }

    public void beginTransaction() {
    }


    public PersonDao createPersonDao() {
        return new PersonDao();
    }

    public VehicleDao createVehicleDao() {
        return new VehicleDao();
    }

    public void endTransaction() {
    }

    public void abortTransaction(Exception e) {
    }

    public void endConnectionScope() {
    }
}
