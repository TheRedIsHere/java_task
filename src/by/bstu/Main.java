package by.bstu;

import by.bstu.model.Patient;
import by.bstu.tasks.AsynchronousMultiThread;
import by.bstu.tasks.AsynchronousSingleThread;
import by.bstu.tasks.SynchronousMultiThread;
import by.bstu.tasks.SynchronousSingleThread;
import by.bstu.tasks.Task;
import by.bstu.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<Task> TASKS = new ArrayList<Task>(){{
        add(new SynchronousSingleThread());
        add(new SynchronousMultiThread());
        add(new AsynchronousSingleThread());
        add(new AsynchronousMultiThread());
    }};

    public static void main(String[] args) {
        int patientCount = processPatientCountArg(args);

        System.out.println("\nStart creating patients.");
        List<Patient> patientsList = Util.createPatients(patientCount);

        System.out.println("\nRun tasks\n");
        TASKS.forEach((s) -> s.start(patientsList));
    }

    /**
     * processes the arguments to get pationt count
     * @param args - command line arguments
     * @return patientCount
     */
    private static int processPatientCountArg(String[] args) {
        int patientCount = 0;
        try {
            patientCount = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.println("Can not parse patient count.");
            System.exit(-1);
        }

        return patientCount;
    }
}
