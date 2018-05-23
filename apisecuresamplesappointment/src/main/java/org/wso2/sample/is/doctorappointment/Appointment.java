/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/

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
