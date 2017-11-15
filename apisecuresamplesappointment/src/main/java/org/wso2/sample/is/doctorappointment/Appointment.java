package org.wso2.sample.is.doctorappointment;

/**
 * Created by kavitha on 11/14/17.
 */
public class Appointment {

        private int appointNo;
        private String title;
        private String date;
        private String patient;
        private String description;


    public Appointment(int appointNo, String title, String date, String patient, String description){
        this.appointNo = appointNo;
        this.date = date;
        this.title = title;
        this.description = description;
        this.patient = patient;

    }


    public String toJSONString()
    {
        return "{\"Appointment No\":\"" + appointNo + "\"," + "\"" + "Appointment Title" + "\":" + "\"" + title + "\"," + "\"" + "Patient Name" + "\":" + "\"" + patient + "\"," + "Appointment Date" + "\":" + "\"" + date + "\"," + "\"" + "Description" + "\":" + "\"" + description + "\"}";
    }
}
