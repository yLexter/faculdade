package general;

import utils.Role;

import java.time.LocalDate;

public class Coordinator extends Employee {

    public Coordinator(String name, String lastName, int age, LocalDate dateOfBirth, Role role, String cpf) {
        super(name, lastName, age, dateOfBirth, role, cpf);
    }
}