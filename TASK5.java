//Student Course Registration
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public boolean registerStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    public void displayCourseDetails() {
        System.out.println("Course Code: " + courseCode);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Available Slots: " + getAvailableSlots());
        System.out.println();
    }

    public static void main(String[] args) {
        StudentCourseRegistrationSystem.main(args);
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerForCourse(Course course) {
        if (course.registerStudent()) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for " + course.getTitle());
        } else {
            System.out.println("Registration failed. No available slots.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            course.dropStudent();
            registeredCourses.remove(course);
            System.out.println("Successfully dropped " + course.getTitle());
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + ":");
        if (registeredCourses.isEmpty()) {
            System.out.println("No registered courses.");
        } else {
            for (Course course : registeredCourses) {
                System.out.println(course.getTitle() + " (" + course.getCourseCode() + ")");
            }
        }
        System.out.println();
    }
}

class StudentCourseRegistrationSystem {
    private static List<Course> courseList = new ArrayList<>();
    private static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample courses
        courseList.add(new Course("CS101", "Introduction to Computer Science", "Basic concepts of CS", 3, "Mon-Wed 10:00 AM"));
        courseList.add(new Course("MA101", "Calculus I", "Fundamentals of calculus", 2, "Tue-Thu 11:00 AM"));
        courseList.add(new Course("PH101", "Physics I", "Introduction to Physics", 2, "Mon-Wed 2:00 PM"));

        // Sample student
        Student student = new Student("S1001", "Pratham Singh");
        studentList.add(student);

        while (true) {
            System.out.println("\n--- Student Course Registration System ---");
            System.out.println("1. Display Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Display Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Courses:");
                    for (Course course : courseList) {
                        course.displayCourseDetails();
                    }
                    break;
                case 2:
                    System.out.print("Enter course code to register: ");
                    String courseCode = scanner.nextLine();
                    Course courseToRegister = findCourseByCode(courseCode);
                    if (courseToRegister != null) {
                        student.registerForCourse(courseToRegister);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter course code to drop: ");
                    String courseCodeToDrop = scanner.nextLine();
                    Course courseToDrop = findCourseByCode(courseCodeToDrop);
                    if (courseToDrop != null) {
                        student.dropCourse(courseToDrop);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 4:
                    student.displayRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
