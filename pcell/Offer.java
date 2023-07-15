package pcell;

public class Offer implements Comparable<Offer>{
    protected final Company company;
    protected final String role;
    protected final double ctc;

    public Offer(Company company, String role, double ctc) {
        this.company = company;
        this.role = role;
        this.ctc = ctc;
    }

    @Override
    public int compareTo(Offer o) {
        return (int)(o.ctc - this.ctc);
    }

    public Company getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public double getCtc() {
        return ctc;
    }
}


