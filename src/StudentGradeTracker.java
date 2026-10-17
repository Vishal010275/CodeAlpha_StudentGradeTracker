import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private int id;
    private String name;
    private double[] marks;

    public Student(int id, String name, double[] marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAverage() {
        double sum = 0;

        for (double mark : marks) {
            sum += mark;
        }

        return sum / marks.length;
    }

    public double getHighest() {
        double highest = marks[0];

        for (double mark : marks) {
            if (mark > highest) {
                highest = mark;
            }
        }

        return highest;
    }

    public double getLowest() {
        double lowest = marks[0];

        for (double mark : marks) {
            if (mark < lowest) {
                lowest = mark;
            }
        }

        return lowest;
    }

    public String getGrade() {
        double average = getAverage();

        if (average >= 90) {
            return "A+";
        } else if (average >= 80) {
            return "A";
        } else if (average >= 70) {
            return "B";
        } else if (average >= 60) {
            return "C";
        } else if (average >= 40) {
            return "D";
        } else {
            return "F";
        }
    }

    public void display() {
        System.out.printf(
                "%-8d %-20s %-12.2f %-12.2f %-12.2f %-8s%n",
                id, name, getAverage(), getHighest(),
                getLowest(), getGrade()
        );
    }
}

public class StudentGradeTracker {

    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Student> students = new ArrayList<>();

    private static final int SUBJECTS = 3;

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n=================================");
            System.out.println("       STUDENT GRADE TRACKER");
            System.out.println("=================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Class Summary");
            System.out.println("4. Search Student by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    displayAllStudents();
                    break;

                case 3:
                    displayClassSummary();
                    break;

                case 4:
                    searchStudent();
                    break;

                case 5:
                    System.out.println("Thank you for using Student Grade Tracker!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        sc.close();
    }

    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid integer: ");
            sc.next();
        }

        return sc.nextInt();
    }

    private static double readMarks(int subjectNumber) {
        double marks;

        while (true) {
            System.out.print("Enter marks for subject " + subjectNumber
                    + " (0-100): ");

            if (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
                continue;
            }

            marks = sc.nextDouble();

            if (marks >= 0 && marks <= 100) {
                return marks;
            }

            System.out.println("Marks must be between 0 and 100.");
        }
    }

    private static void addStudent() {

        System.out.print("Enter student ID: ");
        int id = readInt();

        for (Student student : students) {
            if (student.getId() == id) {
                System.out.println("This student ID already exists.");
                return;
            }
        }

        sc.nextLine();

        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return;
        }

        double[] marks = new double[SUBJECTS];

        for (int i = 0; i < SUBJECTS; i++) {
            marks[i] = readMarks(i + 1);
        }

        Student student = new Student(id, name, marks);
        students.add(student);

        System.out.println("Student added successfully!");
    }

    private static void displayAllStudents() {

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        System.out.println("\nSTUDENT REPORT");
        System.out.printf(
                "%-8s %-20s %-12s %-12s %-12s %-8s%n",
                "ID", "Name", "Average", "Highest",
                "Lowest", "Grade"
        );

        System.out.println("--------------------------------------------------------------------------");

        for (Student student : students) {
            student.display();
        }
    }

    private static void displayClassSummary() {

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        double totalAverage = 0;
        Student topStudent = students.get(0);
        Student lowestStudent = students.get(0);

        for (Student student : students) {
            totalAverage += student.getAverage();

            if (student.getAverage() > topStudent.getAverage()) {
                topStudent = student;
            }

            if (student.getAverage() < lowestStudent.getAverage()) {
                lowestStudent = student;
            }
        }

        System.out.println("\n========== CLASS SUMMARY ==========");
        System.out.println("Total students: " + students.size());

        System.out.printf("Class average: %.2f%n",
                totalAverage / students.size());

        System.out.println("Top student: " + topStudent.getName()
                + " (Average: " + String.format("%.2f",
                topStudent.getAverage()) + ")");

        System.out.println("Lowest-performing student: "
                + lowestStudent.getName()
                + " (Average: " + String.format("%.2f",
                lowestStudent.getAverage()) + ")");
    }

    private static void searchStudent() {

        System.out.print("Enter student ID to search: ");
        int id = readInt();

        for (Student student : students) {
            if (student.getId() == id) {
                System.out.println("\nStudent found!");
                System.out.println("ID: " + student.getId());
                System.out.println("Name: " + student.getName());

                System.out.printf("Average: %.2f%n",
                        student.getAverage());

                System.out.println("Highest marks: "
                        + student.getHighest());

                System.out.println("Lowest marks: "
                        + student.getLowest());

                System.out.println("Grade: " + student.getGrade());
                return;
            }
        }

        System.out.println("Student not found.");
    }
}

