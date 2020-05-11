package by.bstu.util;

import by.bstu.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * Generates a list of the patients
     * @param count - count of the patients
     * @return list of the patients
     */
    public static List<Patient> createPatients(int count) {
        List<Patient> patientsList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Patient patient = new Patient("Patient_" + i);
            patientsList.add(patient);
            System.out.println("Created: " + patient);
        }
        return patientsList;
    }
}
