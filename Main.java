import java.util.*;
import pcell.*; 

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        final PlacementCell pcell = new PlacementCell("IIITD");
        int choice, outcome;
        boolean bOutcome;

        while (true) {

            System.out.println("Welcome to Pcell: ");
            System.out.println("1. Enter the application");
            System.out.println("2. Exit the application");

            System.out.print("\nEnter choice: ");
            choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {

                while (true) {

                    System.out.println("Choose the mode you want to enter in: ");
                    System.out.println("1. As student");
                    System.out.println("2. As company");
                    System.out.println("3. As placement cell");
                    System.out.println("4. Return to main application");

                    System.out.print("\nEnter choice: ");
                    choice = sc.nextInt(); sc.nextLine();

                    if (choice == 1) {

                        while (true) {
                            System.out.println("Choose the student query to perform: ");
                            System.out.println("1. Add students");
                            System.out.println("2. Enter as a student");
                            System.out.println("3. Back");

                            System.out.print("\nEnter choice: ");
                            choice = sc.nextInt(); sc.nextLine();

                            if (choice == 1) {
                                int n;
                                System.out.print("How many students to be added: ");
                                n = sc.nextInt(); sc.nextLine();

                                String name, branch;
                                int rno;
                                double cgpa;

                                for (int i = 0; i < n; i++) {
                                    System.out.print("Enter name for student " + (i+1) + ": " );
                                    name = sc.nextLine();
                                    System.out.print("Enter roll number for student " + (i+1) + ": " );
                                    rno = sc.nextInt(); sc.nextLine();
                                    System.out.print("Enter cgpa for student " + (i+1) + ": " );
                                    cgpa = sc.nextDouble(); sc.nextLine();
                                    if (cgpa > 10) {
                                        System.out.println("Invalid cgpa \n");
                                        continue;
                                    }
                                    System.out.print("Enter branch for student " + (i+1) + ": " );
                                    branch = sc.nextLine();
                                    Student student = new Student(name.toLowerCase().trim(), rno, cgpa, branch, pcell);
                                    bOutcome = pcell.addStudent(student);
                                    if (bOutcome) {
                                        System.out.println("Student added \n");
                                    } else {
                                        System.out.println("A student already exists with same roll number \n");
                                    }
                                }

                            } else if (choice == 2) {
                                System.out.print("Enter name: ");
                                String name = sc.nextLine();
                                System.out.print("Enter roll number: ");
                                int rno = sc.nextInt(); sc.nextLine();
                                Student student = pcell.getStudent(name.toLowerCase().trim(), rno);

                                if (student != null) {
                                    while (true) {
                                        System.out.println("Welcome, " + student.getName());
                                        System.out.println("Choose the query to perform: ");
                                        System.out.println("1. Register for placement drive");
                                        System.out.println("2. Register for company");
                                        System.out.println("3. Get all available companies");
                                        System.out.println("4. Get current status");
                                        System.out.println("5. Update CGPA");
                                        System.out.println("6. Show latest offer");
                                        System.out.println("7. Accept offer");
                                        System.out.println("8. Reject offer");
                                        System.out.println("9. Back");

                                        System.out.print("\nEnter choice: ");
                                        choice = sc.nextInt(); sc.nextLine();

                                        if (choice == 1) {
                                            System.out.print("Enter time of registration (DD/MM/YYYY hh:mm): ");
                                            String time = sc.nextLine();
                                            outcome = student.registerForPlacement(time.trim());
                                            if (outcome == 0) {
                                                System.out.println("Registered successfully \n");
                                            } else if (outcome == -1) {
                                                System.out.println("Student already registered \n");
                                            } else if (outcome == -2) {
                                                System.out.println("Invalid input for time \n");
                                            } else if (outcome == -3) {
                                                System.out.println("Registration not yet started \n");
                                            } else if (outcome == -4) {
                                                System.out.println("Missed registration deadline \n");
                                            }

                                        } else if (choice == 2) {
                                            String company;
                                            System.out.print("Enter name of company: ");
                                            company = sc.nextLine();
                                            outcome = student.registerForCompany(company.toLowerCase().trim());
                                            if (outcome == 0) {
                                                System.out.println("Registered for " + company.toLowerCase().trim() + "\n");
                                            } else if (outcome == -1) {
                                                System.out.println("You can't perform this operation (not registered/placed/blocked) \n");
                                            } else if (outcome == -2) {
                                                System.out.println("Company not found \n");
                                            } else if (outcome == -3) {
                                                System.out.println("You are not eligible for " + company.toLowerCase().trim() + "\n");
                                            }

                                        } else if (choice == 3) {
                                            ArrayList<Company> availableCompanies = student.getAvailableCompanies();
                                            if (availableCompanies != null) {
                                                System.out.println("List of available companies: ");
                                                for (Company c : availableCompanies) {
                                                    System.out.println("Company name: " + c.getName());
                                                    System.out.println("Company role offering: " + c.getRole());
                                                    System.out.println("Company package: " + c.getCtc() + " LPA");
                                                    System.out.println("Company cgpa criteria: " + c.getCgpa() + "\n");
                                                }
                                            } else {
                                                System.out.println("You can't perform this operation (not registered/placed/blocked) \n");
                                            }

                                        } else if (choice == 4) {
                                            outcome = student.getStatus();
                                            if (outcome == 0) {
                                                System.out.println("Status = Not placed yet \n");
                                            } else if (outcome == 1) {
                                                System.out.println("Status = Placed \n");
                                            } else if (outcome == -1) {
                                                System.out.println("Status = Blocked \n");
                                            }

                                        } else if (choice == 5) {
                                            double cgpa;
                                            System.out.print("Enter new cgpa: ");
                                            cgpa = sc.nextDouble(); sc.nextLine();
                                            if (cgpa > 10) {
                                                System.out.println("Invalid cgpa \n");
                                                continue;
                                            }
                                            student.updateCGPA(cgpa);

                                        } else if (choice == 6) {
                                            Offer latestOffer = student.getLatestOffer();
                                            if (latestOffer != null) {
                                                System.out.println("Latest offer is as follows: ");
                                                System.out.println("Company name: " + latestOffer.getCompany().getName());
                                                System.out.println("Company role offering: " + latestOffer.getRole());
                                                System.out.println("Company package: " + latestOffer.getCtc() + " LPA \n");
                                            } else {
                                                System.out.println("No offer found \n");
                                            }

                                        } else if (choice == 7) {
                                            outcome = student.answerOffer(1);
                                            if (outcome == 0) {
                                                System.out.println("Offer accepted \n");
                                            } else if (outcome == -1) {
                                                System.out.println("You can't perform this operation (not registered/placed/blocked) \n");
                                            } else if (outcome == -2) {
                                                System.out.println("You don't have any offers \n");
                                            }

                                        } else if (choice == 8) {
                                            outcome = student.answerOffer(0);
                                            if (outcome == 0) {
                                                System.out.println("Offer rejected \n");
                                            } else if (outcome == -1) {
                                                System.out.println("You can't perform this operation (not registered/placed/blocked) \n");
                                            } else if (outcome == -2) {
                                                System.out.println("You don't have any offers \n");
                                            } else if (outcome == -3) {
                                                System.out.println("Offer rejected");
                                                System.out.println("You have been blocked, as a result of rejecting all offers while unplaced \n");
                                            }

                                        } else if (choice == 9) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input! \n");
                                        }
                                    }

                                } else {
                                    System.out.println("No such student found \n");
                                }

                            } else if (choice == 3) {
                                break;

                            } else {
                                System.out.println("Invalid input! \n");
                            }
                        }

                    } else if (choice == 2) {

                        while (true) {
                            System.out.println("Choose the company query to perform: ");
                            System.out.println("1. Add company");
                            System.out.println("2. Enter as a company");
                            System.out.println("3. Get available companies");
                            System.out.println("4. Back");

                            System.out.print("\nEnter choice: ");
                            choice = sc.nextInt(); sc.nextLine();

                            if (choice == 1) {
                                String name, role;
                                double ctc, cgpa;
                                System.out.print("Enter company name: ");
                                name = sc.nextLine();
                                System.out.print("Enter role: ");
                                role = sc.nextLine();
                                System.out.print("Enter ctc: ");
                                ctc = sc.nextDouble(); sc.nextLine();
                                System.out.print("Enter cgpa requirement: ");
                                cgpa = sc.nextDouble(); sc.nextLine();
                                if (cgpa > 10) {
                                    System.out.println("Invalid cgpa");
                                    continue;
                                }
                                Company company = new Company(name.toLowerCase().trim(), role, ctc, cgpa, pcell);
                                bOutcome = pcell.addCompany(company);
                                if (bOutcome) {
                                    System.out.println("Company added \n");
                                } else {
                                    System.out.println("A company already exists with same name \n");
                                }

                            } else if (choice == 2) {
                                System.out.print("Enter name of company: ");
                                String name = sc.nextLine();
                                Company company = pcell.getCompany(name.toLowerCase().trim());

                                if (company != null) {
                                    while (true) {
                                        System.out.println("Welcome, " + company.getName());
                                        System.out.println("Choose query to perform: ");
                                        System.out.println("1. Update role");
                                        System.out.println("2. Update package");
                                        System.out.println("3. Update CGPA criteria");
                                        System.out.println("4. Register to institute placement drive");
                                        System.out.println("5. Get selected students");
                                        System.out.println("6. Back");

                                        System.out.print("\nEnter choice: ");
                                        choice = sc.nextInt(); sc.nextLine();

                                        if (choice == 1) {
                                            System.out.print("Enter new role: ");
                                            String role = sc.nextLine();
                                            company.updateRole(role);
                                            System.out.println("Role updated \n");

                                        } else if (choice == 2) {
                                            System.out.print("Enter new package: ");
                                            double ctc = sc.nextDouble(); sc.nextLine();
                                            company.update(0, ctc);
                                            System.out.println("Package updated \n");

                                        } else if (choice == 3) {
                                            System.out.print("Enter new cgpa: ");
                                            double cgpa = sc.nextDouble(); sc.nextLine();
                                            bOutcome = company.update(1, cgpa);
                                            if (!bOutcome) {
                                                System.out.println("Invalid cgpa \n");
                                            } else {
                                                System.out.println("CGPA criteria updated \n");
                                            }

                                        } else if (choice == 4) {
                                            System.out.print("Enter time of registration (DD/MM/YYYY hh:mm): ");
                                            String time = sc.nextLine();
                                            outcome = company.registerForPlacements(time.trim());
                                            if (outcome == 0) {
                                                System.out.println("Registered successfully \n");
                                            } else if (outcome == -1) {
                                                System.out.println("Company already registered \n");
                                            } else if (outcome == -2) {
                                                System.out.println("Invalid input for time \n");
                                            } else if (outcome == -3) {
                                                System.out.println("Registration not yet started \n");
                                            } else if (outcome == -4) {
                                                System.out.println("Missed registration deadline \n");
                                            }

                                        } else if (choice == 5) {
                                            ArrayList<Student> selectedStudents = company.getSelectedStudents();
                                            if (selectedStudents != null) {
                                                System.out.println("Following students have been selected (and offered): ");
                                                for (Student s : selectedStudents) {
                                                    System.out.println("Name = " + s.getName());
                                                    System.out.println("Roll number = " + s.getRollNumber() + "\n");
                                                }
                                            } else {
                                                System.out.println("Company not registered / no student has registered for the company \n");
                                            }

                                        } else if (choice == 6) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input! \n");
                                        }
                                    }

                                } else {
                                    System.out.println("No such company found \n");
                                }

                            } else if (choice == 3) {
                                System.out.println("List of companies added: ");
                                ArrayList<Company> companies = pcell.returnAllCompanies();
                                for (int i = 0; i < companies.size(); i++) {
                                    System.out.println((i+1) + ". " + companies.get(i).getName());
                                }
                                System.out.print("\n");

                            } else if (choice == 4) {
                                break;

                            } else {
                                System.out.println("Invalid input! \n");
                            }

                        }

                    } else if (choice == 3) {

                        while (true) {
                            System.out.println("Welcome to " + pcell);
                            System.out.println("1. Open student registrations");
                            System.out.println("2. Open company registrations");
                            System.out.println("3. Get number of student registrations");
                            System.out.println("4. Get number of company registrations");
                            System.out.println("5. Get number of placed/unplaced/blocked students");
                            System.out.println("6. Get student details");
                            System.out.println("7. Get company details");
                            System.out.println("8. Get average package");
                            System.out.println("9. Get company process results");
                            System.out.println("10. Back");

                            System.out.print("\nEnter choice: ");
                            choice = sc.nextInt(); sc.nextLine();

                            if (choice == 1) {
                                String start, end;
                                System.out.print("Enter opening time for student registrations (DD/MM/YYYY hh:mm): ");
                                start = sc.nextLine();
                                System.out.print("Enter closing time for student registrations (DD/MM/YYYY hh:mm): ");
                                end = sc.nextLine();
                                outcome = pcell.openStudentRegistrations(start, end);
                                if (outcome == 0) {
                                    System.out.println("Student registrations opened successfully \n");
                                } else if (outcome == -1) {
                                    System.out.println("Invalid input for time \n");
                                } else if (outcome == -2) {
                                    System.out.println("Closing time is before opening time \n");
                                } else if (outcome == -3) {
                                    System.out.println("Company registrations not started yet \n");
                                } else if (outcome == -4) {
                                    System.out.println("Student registrations must start after company registrations end \n");
                                }

                            } else if (choice == 2) {
                                String start, end;
                                System.out.print("Enter opening time for company registrations (DD/MM/YYYY hh:mm): ");
                                start = sc.nextLine();
                                System.out.print("Enter closing time for company registrations (DD/MM/YYYY hh:mm): ");
                                end = sc.nextLine();
                                outcome = pcell.openCompanyRegistrations(start, end);
                                if (outcome == 0) {
                                    System.out.println("Company registrations opened successfully \n");
                                } else if (outcome == -1) {
                                    System.out.println("Invalid input for time \n");
                                } else if (outcome == -2) {
                                    System.out.println("Closing time is before opening time \n");
                                }

                            } else if (choice == 3) {
                                System.out.println("Total number of student registrations = " + pcell.getStudentRegistrations() + "\n");

                            } else if (choice == 4) {
                                System.out.println("Total number of company registrations = " + pcell.getCompanyRegistrations() + "\n");

                            } else if (choice == 5) {
                                int[] data = pcell.getPUBnumbers();
                                System.out.println("Number of placed students = " + data[0]);
                                System.out.println("Number of unplaced students = " + data[1]);
                                System.out.println("Number of blocked students = " + data[2] + "\n");

                            } else if (choice == 6) {
                                System.out.print("Enter name: ");
                                String name = sc.nextLine();
                                System.out.print("Enter roll number: ");
                                int rno = sc.nextInt(); sc.nextLine();
                                Student student = pcell.getStudent(name.toLowerCase().trim(), rno);
                                if (student != null) {
                                    System.out.println("Student applied for following companies: ");
                                    ArrayList<Company> appliedFor = pcell.companiesStudentRegistered(student);
                                    for (int i = 0; i < appliedFor.size(); i++) {
                                        System.out.println((i+1) + ". " + appliedFor.get(i).getName());
                                    }
                                    System.out.print("\n");
                                    System.out.println("Student has offers from following companies: ");
                                    ArrayList<Offer> offers = pcell.getStudentOffers(student);
                                    for (int i = 0; i < offers.size(); i++) {
                                        System.out.println((i+1) + ". " + offers.get(i).getCompany().getName());
                                    }
                                    System.out.print("\n");
                                } else {
                                    System.out.println("Student not found \n");
                                }

                            } else if (choice == 7) {
                                System.out.print("Enter name of company: ");
                                String name = sc.nextLine();
                                Company company = pcell.getCompany(name.toLowerCase().trim());
                                if (company != null) {
                                    System.out.println(company);
                                    ArrayList<Student> selectedStudents = company.getSelectedStudents();
                                    if (selectedStudents != null) {
                                        System.out.println("Company selected (and offered) the following students: ");
                                        for (Student s : selectedStudents) {
                                            System.out.println("Name = " + s.getName());
                                            System.out.println("Roll number = " + s.getRollNumber() + "\n");
                                        }
                                    } else {
                                        System.out.println("Company not registered / no student has registered for the company \n");
                                    }
                                } else {
                                    System.out.println("Company not found \n");
                                }

                            } else if (choice == 8) {
                                System.out.println("Average package = " + pcell.getAveragePackage() + " LPA \n");

                            } else if (choice == 9) {
                                System.out.print("Enter name of company: ");
                                String name = sc.nextLine();
                                Company company = pcell.getCompany(name.toLowerCase().trim());

                                if (company != null) {
                                    ArrayList<Student> joining = pcell.getCompanyProcessResults(company);
                                    if (joining != null) {
                                        System.out.println("Following students accepted when company offered them: ");
                                        for (Student s : joining) {
                                            System.out.println("Name = " + s.getName());
                                            System.out.println("Roll number = " + s.getRollNumber() + "\n");
                                        }
                                    } else {
                                        System.out.println("Company has not conducted selection of students yet \n");
                                    }
                                } else {
                                    System.out.println("Company not found \n");
                                }

                            } else if (choice == 10) {
                                break;

                            } else {
                                System.out.println("Invalid input! \n");
                            }
                        }

                    } else if (choice == 4) {
                        break;

                    } else {
                        System.out.println("Invalid input! \n");
                    }
                }

            } else if (choice == 2) {
                break;

            } else {
                System.out.println("Invalid input! \n");
            }
        }
        sc.close();
    }
}