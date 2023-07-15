package pcell;

import java.util.*;

public class Student {
    protected String name;
    protected final int rollNumber;
    protected double cgpa;
    protected String branch;
    protected final PlacementCell pcell;
    protected Offer finalOffer = null;

    protected boolean registered = false;
    protected int status = 0; // 0 not placed, 1 placed, -1 blocked

    protected ArrayList<Company> companiesAppliedFor = new ArrayList<>();
    protected ArrayList<Offer> offers = new ArrayList<>(); // highest to lowest

    public Student(String name, int rollNumber, double cgpa, String branch, PlacementCell pcell) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.cgpa = cgpa;
        this.branch = branch;
        this.pcell = pcell;
    }

    public int registerForPlacement(String time) {
        return this.pcell.registerStudent(this, time);
    }

    public int registerForCompany(String company) {
        if (this.registered && this.status == 0) {
            return this.pcell.registerStudentForCompany(this, company);
        } else {
            return -1; // can't continue
        }
    }

    public ArrayList<Company> getAvailableCompanies() {
        if (this.registered && this.status == 0) {
            return this.pcell.returnAvailableCompanies(this);
        }
        return null; // can't continue
    }

    public int getStatus() {
        return this.status;
    }

    public void updateCGPA(double cgpa) {
        this.pcell.updateStudentCGPA(this, cgpa);
    }

    public int answerOffer(int choice) {
        if (this.registered && this.status == 0) {
            if (this.offers.size() > 0) {
                if (choice == 1) {
                    this.finalOffer = this.offers.get(0);
                    this.pcell.updateCompanyJoining(this, this.finalOffer.company);
                    this.status = 1;
                    return 0; // accepted
                } else if (choice == 0) {
                    this.offers.remove(0);
                    if (this.offers.size() == 0) {
                        this.status = -1;
                        return -3; // blocked
                    }
                    return 0; // rejected
                }
            } else {
                return -2; // no offers
            }
        }
        return -1; // can't continue
    }

    public String getName() {
        return this.name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public Offer getLatestOffer() {
        if (this.status == 0 && this.offers.size() > 0) {
            return this.offers.get(0);
        }
        return null;
    }

}
