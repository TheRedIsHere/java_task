package by.bstu.tasks;

import by.bstu.model.Patient;

import java.util.List;


public class SynchronousMultiThread implements Task {

    @Override
    public void start(List<Patient> patientsList) {
        long start = System.nanoTime();
        System.out.println("\n\nSynchronousMultiThread implementation started.\n");

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
            try {
                gp_1.join();
                System.out.println("Thread gp_2 is processed.");
                while (!GP_02_QUEUE.isEmpty()) {
                    processPatient(GP_02_QUEUE);
                }
                System.out.println("Second GP queue processed successfully.");
            } catch (InterruptedException ignored) {}
        });

        Thread surgeon = new Thread(() -> {
            System.out.println("Thread surgeon started.");
            try {
                gp_2.join();
                System.out.println("Thread surgeon is processed.");
                while (!SURGEON_QUEUE.isEmpty()) {
                    processPatient(SURGEON_QUEUE);
                }
                System.out.println("Surgeon queue processed successfully.");
            } catch (InterruptedException ignored) {}
        });

        Thread endocrinologist = new Thread(() -> {
            System.out.println("Thread endocrinologist started.");
            try {
                surgeon.join();
                System.out.println("Thread endocrinologist is processed.");
                while (!ENDOCRINOLOGIST_QUEUE.isEmpty()) {
                    processPatient(ENDOCRINOLOGIST_QUEUE);
                }
                System.out.println("Endocrinologist queue processed successfully.");
            } catch (InterruptedException ignored) {}
        });

        Thread neurologist = new Thread(() -> {
            System.out.println("Thread neurologist started.");
            try {
                endocrinologist.join();
                System.out.println("Thread neurologist is processed.");
                while (!NEUROLOGIST_QUEUE.isEmpty()) {
                    processPatient(NEUROLOGIST_QUEUE);
                }
                System.out.println("Neurologist queue processed successfully.");
            } catch (InterruptedException ignored) {}
        });

        gp_1.start();
        gp_2.start();
        surgeon.start();
        endocrinologist.start();
        neurologist.start();
        try {
            neurologist.join();
        } catch (InterruptedException ignored) {}

        clean();

        System.out.println("\n\nSynchronousMultiThread implementation finished.");
        System.out.println("Time spent: " + (System.nanoTime() - start) + " nanoseconds.\n\n");
    }

}
