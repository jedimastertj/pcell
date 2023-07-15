package pcell;

import java.util.*;

public class Company {
    protected final String name;
    protected String role;
    protected double ctc;
    protected double cgpa;
    protected final PlacementCell pcell;

    protected boolean registered = false;
    protected boolean conductedSelection = false;

    protected ArrayList<Student> registeredStudents = new ArrayList<>();
    protected ArrayList<Student> selectedStudents = new ArrayList<>();
    protected ArrayList<Student> joiningStudents = new ArrayList<>();

    public Company(String name, String role, double ctc, double cgpa, PlacementCell pcell) {
        this.name = name;
        this.role = role;
        this.ctc = ctc;
        this.cgpa = cgpa;
        this.pcell = pcell;
    }

    @Override
    public String toString() {
        return ("Name = " + this.name + "\nRole = " + this.role + "\nCGPA criteria = " + this.cgpa +
                "\nCTC = " + this.ctc + " LPA \n");
    }

    public void updateRole(String role) {
        this.role = role;
    }

    public boolean update(int field, double value) {
        if (field == 0) {
            this.ctc = value;
        } else if (field == 1) {
            if (value <= 10) {
                this.cgpa = value;
            } else {
                return false;
            }
        }
        return true;
    }

    public int registerForPlacements(String time) {
        return this.pcell.registerCompany(this, time);
    }

    public ArrayList<Student> getSelectedStudents() {
        if (!this.registered || this.registeredStudents.size() == 0) {
            return null;
        }
        if (this.conductedSelection) { return this.selectedStudents; }
        Random r = new Random();
        int howMany = 1 + r.nextInt(this.registeredStudents.size());
        for (int i = 0; i < howMany; i++) {
            Student s = this.registeredStudents.get(i);
            this.selectedStudents.add(s);
            this.pcell.updateStudentOffers(this, s);
        }
        this.conductedSelection = true;
        return this.selectedStudents;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public double getCtc() {
        return ctc;
    }

    public double getCgpa() {
        return cgpa;
    }
}
