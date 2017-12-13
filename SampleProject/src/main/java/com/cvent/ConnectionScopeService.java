package com.cvent;

/**
 * Created by a.srivastava on 7/19/17.
 */
public class ConnectionScopeService {
    DaoFactory daoFactory = new DaoFactory();
    public void doSomeTask() {
        try{
            daoFactory.beginConnectionScope();
            try{
                daoFactory.beginTransaction();

                PersonDao  personDao  = daoFactory.createPersonDao();
                VehicleDao vehicleDao = daoFactory.createVehicleDao();

                daoFactory.endTransaction();
            } catch(Exception e){
                daoFactory.abortTransaction(e);
            }
        } finally {
            daoFactory.endConnectionScope();
        }
    }
}
