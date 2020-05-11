package by.bstu.tasks;

import by.bstu.model.Patient;
import by.bstu.util.EnumPatientStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Task {

    List<Patient> GP_01_QUEUE = new ArrayList<>();
    List<Patient> GP_02_QUEUE = new ArrayList<>();
    List<Patient> SURGEON_QUEUE = new ArrayList<>();
    List<Patient> ENDOCRINOLOGIST_QUEUE = new ArrayList<>();
    List<Patient> NEUROLOGIST_QUEUE = new ArrayList<>();

    void start(List<Patient> patientsList);

    default void processPatient(List<Patient> queue) {
        if (queue.size() > 0) {
            Patient patient = queue.get(0);
            EnumPatientStatus status = patient.getStatus();
            if (status.equals(EnumPatientStatus.QUEUED) || status.equals(EnumPatientStatus.MOVED)) {
                patient.setStatus(EnumPatientStatus.LISTEN);
            } else {
                if (queue == GP_01_QUEUE || queue == GP_02_QUEUE) {
                    moveOrDone(patient, queue);
                } else {
                    done(patient, queue);
                }
            }
        }
    }

    default void moveOrDone(Patient patient, List<Patient> queue) {
        int randomInteger = new Random().nextInt(4);
        switch (randomInteger) {
            case 1:
                patient.setStatus(EnumPatientStatus.MOVED);
                SURGEON_QUEUE.add(patient);
                System.out.println("Patient : " + patient);
                break;
            case 2:
                patient.setStatus(EnumPatientStatus.MOVED);
                ENDOCRINOLOGIST_QUEUE.add(patient);
                System.out.println("Patient : " + patient);
                break;
            case 3:
                patient.setStatus(EnumPatientStatus.MOVED);
                NEUROLOGIST_QUEUE.add(patient);
                break;
            default:
                patient.setStatus(EnumPatientStatus.DONE);
                System.out.println("Patient : " + patient);
                break;
        }
        queue.remove(patient);
    }

    default void done(Patient patient, List<Patient> queue) {
        patient.setStatus(EnumPatientStatus.DONE);
        System.out.println("Patient : " + patient);
        queue.remove(patient);
    }

    default void clean() {
        GP_01_QUEUE.clear();
        GP_02_QUEUE.clear();
        SURGEON_QUEUE.clear();
        ENDOCRINOLOGIST_QUEUE.clear();
        NEUROLOGIST_QUEUE.clear();
    }

    /**
     * Splits the passed list on queue for each therapist (2 therapists)
     * @param patientsList - list of the patients
     */
    default void distributePatientsByTherapists(List<Patient> patientsList) {
        for (int i = 0; i < patientsList.size(); i++) {
            Patient patient = patientsList.get(i);
            patient.setStatus(EnumPatientStatus.QUEUED);
            if ((i % 2) == 0) {
                System.out.println("The " + patient.getName() + " moved to GP_01_QUEUE.");
                GP_01_QUEUE.add(patient);
            } else {
                System.out.println("The " + patient.getName() + " moved to GP_02_QUEUE.");
                GP_02_QUEUE.add(patient);
            }
        }
        System.out.println();
    }
}
