package by.bstu.tasks;

import by.bstu.model.Patient;

import java.util.List;

public class AsynchronousMultiThread implements Task {

    @Override
    public void start(List<Patient> patientsList) {
        long start = System.nanoTime();
        System.out.println("\n\nAsynchronousMultiThread implementation started.\n");

        this.distributePatientsByTherapists(patientsList);

        Thread gp_1 = new Thread(() -> {
            System.out.println("Thread gp_1 started.");
            System.out.println("Thread gp_1 is processed.");
            while (!GP_01_QUEUE.isEmpty()) {
                processPatient(GP_01_QUEUE);
            }
            System.out.println("First GP queue processed successfully.");
        });

        Thread gp_2 = new Thread(() -> {
            System.out.println("Thread gp_2 started.");
            System.out.println("Thread gp_2 is processed.");
            while (!GP_02_QUEUE.isEmpty()) {
                processPatient(GP_02_QUEUE);
            }
            System.out.println("Second GP queue processed successfully.");
        });

        Thread surgeon = new Thread(() -> {
            System.out.println("Thread surgeon started.");
            System.out.println("Thread surgeon is processed.");
            while (!SURGEON_QUEUE.isEmpty() || !GP_01_QUEUE.isEmpty() || !GP_02_QUEUE.isEmpty()) {
                processPatient(SURGEON_QUEUE);
            }
            System.out.println("Surgeon queue processed successfully.");
        });

        Thread endocrinologist = new Thread(() -> {
            System.out.println("Thread endocrinologist started.");
            System.out.println("Thread endocrinologist is processed.");
            while (!ENDOCRINOLOGIST_QUEUE.isEmpty() || !GP_01_QUEUE.isEmpty() || !GP_02_QUEUE.isEmpty()) {
                processPatient(ENDOCRINOLOGIST_QUEUE);
            }
            System.out.println("Endocrinologist queue processed successfully.");
        });

        Thread neurologist = new Thread(() -> {
            System.out.println("Thread neurologist started.");
            System.out.println("Thread neurologist is processed.");
            while (!NEUROLOGIST_QUEUE.isEmpty() || !GP_01_QUEUE.isEmpty() || !GP_02_QUEUE.isEmpty()) {
                processPatient(NEUROLOGIST_QUEUE);
            }
            System.out.println("Neurologist queue processed successfully.");
        });

        gp_1.start();
        gp_2.start();
        surgeon.start();
        endocrinologist.start();
        neurologist.start();

        try {
            gp_1.join();
            gp_2.join();
            surgeon.join();
            endocrinologist.join();
            neurologist.join();
        } catch (InterruptedException ignored) {}

        clean();

        System.out.println("\n\nAsynchronousMultiThread implementation finished.");
        System.out.println("Time spent: " + (System.nanoTime() - start) + " nanoseconds.\n\n");
    }

}
