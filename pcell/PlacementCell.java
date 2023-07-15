package pcell;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlacementCell {
    private final String instituteName;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/y H:m");

    private LocalDateTime cStart = null;
    private LocalDateTime cEnd = null;
    private LocalDateTime sStart = null;
    private LocalDateTime sEnd = null;

    private HashMap<String, Company> companies = new HashMap<>();
    private HashMap<Integer, Student> students = new HashMap<>();

    public PlacementCell(String instituteName) {
        this.instituteName = instituteName;
    }

    @Override
    public String toString() {
        return (this.instituteName + " placement cell");
    }

    public boolean addStudent(Student s) {
        if (this.students.containsKey(s.rollNumber)) { return false; }
        this.students.put(s.rollNumber, s);
        return true;
    }

    public Student getStudent(String name, int rno) {
        Student s = this.students.get(rno);
        if (s != null && name.equals(s.name)) {
            return s;
        }
        return null;
    }

    // Use within try block
    private LocalDateTime strToTime(String time) {
        return LocalDateTime.parse(time, format);
    }

    protected int registerStudent(Student student, String timeInput) {
        if (!student.registered) {
            LocalDateTime time;
            try {
                time = strToTime(timeInput);
            }
            catch (Exception e) {
                return -2; // invalid time
            }
            if (sStart != null && sEnd != null && time.isAfter(sStart)) {
                if (time.isBefore(sEnd)) {
                    student.registered = true;
                    return 0; // success
                } else {
                    return -4; // missed deadline
                }
            } else {
                return -3; // reg not started
            }
        } else {
            return -1; // already registered
        }
    }

    protected int registerStudentForCompany(Student student, String company) {
        Company c = this.companies.get(company);
        if (c != null && c.registered) {
            if (student.cgpa >= c.cgpa && (student.offers.size() == 0 || c.ctc >= 3 * student.offers.get(0).ctc)) {
                c.registeredStudents.add(student);
                student.companiesAppliedFor.add(c);
                return 0; // success
            } else {
                return -3; // not eligible
            }
        } else {
            return -2; // company not found
        }
    }

    protected ArrayList<Company> returnAvailableCompanies(Student student) {
        ArrayList<Company> eligibleFor = new ArrayList<>();
        for (Company c: this.companies.values()) {
            if (student.cgpa >= c.cgpa && (student.offers.size() == 0 || c.ctc >= 3 * student.offers.get(0).ctc) ) {
                if (c.registered) { eligibleFor.add(c); }
            }
        }
        return eligibleFor;
    }

    protected void updateStudentCGPA(Student student, double cgpa) {
        student.cgpa = cgpa;
    }

    protected void updateCompanyJoining(Student student, Company company) {
        company.joiningStudents.add(student);
    }

    public boolean addCompany(Company c) {
        if (this.companies.containsKey(c.name)) { return false; }
        this.companies.put(c.name, c);
        return true;
    }

    public Company getCompany(String name) {
        return this.companies.get(name);
    }

    public ArrayList<Company> returnAllCompanies() {
        Collection<Company> values = this.companies.values();
        return new ArrayList<>(values);
    }

    public int openStudentRegistrations(String start, String end) {
        try {
            if (strToTime(end).isBefore(strToTime(start))) { return -2; } // end before start
            if (this.cEnd == null || this.cStart == null) { return -3; } // company reg not started
            if (strToTime(start).isBefore(this.cEnd)) { return -4; } // student reg starts before company reg ends
            this.sStart = strToTime(start);
            this.sEnd = strToTime(end);
            return 0; // success
        }
        catch (Exception e) {
            return -1; // invalid input
        }
    }

    public int openCompanyRegistrations(String start, String end) {
        try {
            if (strToTime(end).isBefore(strToTime(start))) { return -2; } // end before start
            this.cStart = strToTime(start);
            this.cEnd = strToTime(end);
            return 0; // success
        }
        catch (Exception e) {
            return -1; // invalid input
        }
    }

    public int getStudentRegistrations() {
        int count = 0;
        for (Student s: this.students.values()) {
            if (s.registered) {count++;}
        }
        return count;
    }

    public int getCompanyRegistrations() {
        int count = 0;
        for (Company c: this.companies.values()) {
            if (c.registered) {count++;}
        }
        return count;
    }

    public int[] getPUBnumbers() {
        int placed = 0, unplaced = 0, blocked = 0;
        for (Student s: this.students.values()) {
            if (s.status == 0) { unplaced++; }
            else if (s.status == 1) { placed++; }
            else if (s.status == -1) { blocked++; }
        }
        return new int[] {placed, unplaced, blocked};
    }

    public ArrayList<Student> getCompanyProcessResults(Company company) {
        if (!company.conductedSelection) { return null; }
        return company.joiningStudents;
    }

    public double getAveragePackage() {
        double total = 0;
        int placed = 0;
        for (Student s: this.students.values()) {
            if (s.status == 1) {
                placed += 1;
                total += s.finalOffer.ctc;
            }
        }
        return (total/placed);
    }

    protected int registerCompany(Company company, String timeInput) {
        if (!company.registered) {
            LocalDateTime time;
            try {
                time = strToTime(timeInput);
            }
            catch (Exception e) {
                return -2; // invalid time
            }
            if (cStart != null && cEnd != null && time.isAfter(cStart)) {
                if (time.isAfter(cStart) && time.isBefore(cEnd)) {
                    company.registered = true;
                    return 0; // success
                } else {
                    return -4; // out of time
                }
            } else {
                return -3; // reg not started
            }
        } else {
            return -1; // already registered
        }
    }

    protected void updateStudentOffers(Company company, Student student) {
        Offer offer = new Offer(company, company.role, company.ctc);
        student.offers.add(offer);
        Collections.sort(student.offers);
    }

    public ArrayList<Company> companiesStudentRegistered(Student s) {
        return s.companiesAppliedFor;
    }

    public ArrayList<Offer> getStudentOffers(Student s) {
        return s.offers;
    }

}
