package org.wso2.sample.is.doctorappointment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;


/**
 * Created by kavitha on 11/14/17.
 */
public class DoctorAppointment extends HttpServlet {

        private static final long serialVersionUID = 1L;

        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";

        public static final String APPOINTMENTS = "appointments";
        public static final String APPOINTMENTNO = "appointNo";
        public static final String TITLE = "title";
        public static final String PATIENT = "patient";
        public static final String DATE = "date";
        public static final String DESCRIPTION = "description";


        public DoctorAppointment() {}

        private static Map<String, Appointment> appointments = new HashMap();
        private static List<Integer> appointNos = new ArrayList();

    public void init() {

        appointments.put("101", new Appointment(101, "Doctor Appointment1", "20-Nov-2017", "Patient 1", "Looking for orthopedic solution") );
        appointments.put("102", new Appointment(102, "Doctor Appointment2", "21-Nov-2017", "Patient 2", "Looking for fever solution"));

        appointNos.add(new Integer(101));
        appointNos.add(new Integer(102));

        System.out.println(getAllAppointments());
    }


    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            PrintWriter out = null;
            response.setContentType("application/json");
            out = response.getWriter();

            if ((request.getPathInfo() == null) || (request.getPathInfo().trim().length() == 0) || (request.getPathInfo().equalsIgnoreCase("/")))
            {
                out.println(getAllAppointments());
            } else {
                String appointNo = request.getPathInfo().substring(1);
                if (appointments.containsKey(appointNo)) {
                    out.println(getAppointmentsJSON((Appointment)appointments.get(appointNo)));
                }
            }

            out.close();

        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            JSONObject json = null;
            try {
                json = new JSONObject(getBody(request));

                Integer appointmentno = Integer.valueOf(((Integer)appointNos.get(appointNos.size() - 1)).intValue() + 1);
                appointments.put(appointmentno.toString(), new Appointment(appointmentno.intValue(),(String)json.get("title"), (String)json.get("date"), (String)json.get("patient"), (String)json.get("description")));

                appointNos.add(appointmentno);

                String message = "{\"Appointment No\":\"" + appointmentno.toString() + "\"" + "," + "\"location\"" + " :" + "\"http://localhost:8080/appointment/" + appointmentno.toString() + "\"" + ",}";
//
                PrintWriter out = null;
                response.setContentType("application/json");
                out = response.getWriter();
                out.println(message);
                out.close();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if (request.getMethod().equalsIgnoreCase("PUT")) {
            JSONObject json = null;
            try {
                json = new JSONObject(getBody(request));
                String appointmentString = request.getPathInfo().substring(1);
                Integer appointmentno = new Integer(100);


                if (appointmentString != null) {
                    appointmentno = new Integer(appointmentString);
                }
                appointments.put(appointmentno.toString(), new Appointment(appointmentno.intValue(),(String)json.get("title"), (String)json.get("date"), (String)json.get("patient"), (String)json.get("description")));

                appointNos.add(appointmentno);

                String message = "{\"Appointment No\":\"" + appointmentno.toString() + "\"" + "," + "\"location\"" + " :" + "\"http://localhost:8080/appointment/" + appointmentno.toString() + "\"" + ",}";

                PrintWriter out = null;
                response.setContentType("application/json");
                out = response.getWriter();
                out.println(message);
                out.close();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if ((request.getMethod().equalsIgnoreCase("DELETE")) &&
                (request.getPathInfo() != null) && (request.getPathInfo().trim().length() != 0) && (!request.getPathInfo().equalsIgnoreCase("/")))
        {

            String appointmentNo = request.getPathInfo().substring(1);
            appointments.remove(appointmentNo);

        }
    }



    private static String getAllAppointments(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("\"Appointments: [");

        Iterator<Entry<String, Appointment>> it = appointments.entrySet().iterator();
        int counter = 0;
        while (it.hasNext()) {
            counter++;
            Entry<String, Appointment> appointment = (Entry)it.next();
            buffer.append(((Appointment)appointment.getValue()).toJSONString());
            if (counter < appointments.size()) {
                buffer.append(",");
            }
        }
        buffer.append("\"] }");
        return buffer.toString();
    }


    private static String getAppointmentsJSON(Appointment appointment){
        StringBuffer buffer = new StringBuffer();
        buffer.append("\"Appointments:[");

        buffer.append(appointment.toJSONString());

        buffer.append("\"] }");
        return buffer.toString();
    }


    public static String getBody(HttpServletRequest request)
            throws IOException
    {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try
        {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[''];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }

            body = stringBuilder.toString();
        }
        catch (IOException ex)
        {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        return body;
    }
}
