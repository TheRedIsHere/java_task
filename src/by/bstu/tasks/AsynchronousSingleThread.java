package by.bstu.tasks;

import by.bstu.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class AsynchronousSingleThread implements Task {

    @Override
    public void start(List<Patient> patientsList) {
        long start = System.nanoTime();
        System.out.println("\n\nAsynchronousSingleThread implementation started.\n");

        this.distributePatientsByTherapists(patientsList);

        List<List<Patient>> queues = new ArrayList<List<Patient>>() {{
            add(GP_01_QUEUE);
            add(GP_02_QUEUE);
            add(SURGEON_QUEUE);
            add(ENDOCRINOLOGIST_QUEUE);
            add(NEUROLOGIST_QUEUE);
        }};

        boolean gp_1_removed = false;
        boolean gp_2_removed = false;
        boolean surgeon_removed = false;
        boolean endocrinologist_removed = false;
        boolean neurologist_removed = false;

        while (!GP_01_QUEUE.isEmpty()
                || !GP_02_QUEUE.isEmpty()
                || !SURGEON_QUEUE.isEmpty()
                || !ENDOCRINOLOGIST_QUEUE.isEmpty()
                || !NEUROLOGIST_QUEUE.isEmpty())
        {
            queues.forEach(this::processPatient);
            if (GP_01_QUEUE.isEmpty() && !gp_1_removed) {
                gp_1_removed = true;
                queues.remove(GP_01_QUEUE);
                System.out.println("First GP queue processed successfully.");
            }
            if (GP_02_QUEUE.isEmpty() && !gp_2_removed) {
                gp_2_removed = true;
                queues.remove(GP_02_QUEUE);
                System.out.println("Second GP queue processed successfully.");
            }
            if (GP_01_QUEUE.isEmpty() && GP_02_QUEUE.isEmpty()) {
                if (SURGEON_QUEUE.isEmpty() && !surgeon_removed) {
                    surgeon_removed = true;
                    queues.remove(SURGEON_QUEUE);
                    System.out.println("Surgeon queue processed successfully.");
                }
                if (ENDOCRINOLOGIST_QUEUE.isEmpty() && !endocrinologist_removed) {
                    endocrinologist_removed = true;
                    queues.remove(ENDOCRINOLOGIST_QUEUE);
                    System.out.println("Endocrinologist queue processed successfully.");
                }
                if (NEUROLOGIST_QUEUE.isEmpty() && !neurologist_removed) {
                    neurologist_removed = true;
                    queues.remove(NEUROLOGIST_QUEUE);
                    System.out.println("Neurologist queue processed successfully.");
                }
            }
        }

        clean();

        System.out.println("\n\nAsynchronousSingleThread implementation finished.");
        System.out.println("Time spent: " + (System.nanoTime() - start) + " nanoseconds.\n\n");
    }
}
