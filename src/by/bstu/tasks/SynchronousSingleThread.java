package by.bstu.tasks;

import by.bstu.model.Patient;

import java.util.List;

public class SynchronousSingleThread implements Task {

    @Override
    public void start(List<Patient> patientsList) {
        long start = System.nanoTime();
        System.out.println("\n\nSynchronousSingleThread implementation started.\n");

       this.distributePatientsByTherapists(patientsList);

        while (!GP_01_QUEUE.isEmpty()) {
            processPatient(GP_01_QUEUE);
        }
        System.out.println("First GP queue processed successfully.");

        while (!GP_02_QUEUE.isEmpty()) {
            processPatient(GP_02_QUEUE);
        }
        System.out.println("Second GP queue processed successfully.");

        while (!SURGEON_QUEUE.isEmpty()) {
            processPatient(SURGEON_QUEUE);
        }
        System.out.println("Surgeon queue processed successfully.");

        while (!ENDOCRINOLOGIST_QUEUE.isEmpty()) {
            processPatient(ENDOCRINOLOGIST_QUEUE);
        }
        System.out.println("Endocrinologist queue processed successfully.");

        while (!NEUROLOGIST_QUEUE.isEmpty()) {
            processPatient(NEUROLOGIST_QUEUE);
        }
        System.out.println("Neurologist queue processed successfully.");

        clean();

        System.out.println("\n\nSynchronousSingleThread implementation finished.");
        System.out.println("Time spent: " + (System.nanoTime() - start) + " nanoseconds.\n\n");
    }

}
