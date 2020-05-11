package by.bstu.model;

import by.bstu.util.EnumPatientStatus;

public class Patient {

    private final String name;
    private EnumPatientStatus status;

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public EnumPatientStatus getStatus() {
        return status;
    }

    public void setStatus(EnumPatientStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Patient name='" + name + " in status=" + status + '.';
    }

}
