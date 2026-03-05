# MediTrack — Clinic & Appointment Management System

## Project Overview

MediTrack is a comprehensive **Clinic & Appointment Management System** built with **Core Java 17**. It demonstrates strong object-oriented programming (OOP) design, SOLID principles, advanced Java features, and professional software architecture patterns.

The system manages:
- **Doctors** - Healthcare professionals with specialties and credentials
- **Patients** - Clinic patients with medical history
- **Appointments** - Scheduled consultations between doctors and patients
- **Billing** - Invoice generation and payment tracking

---

## Key Features

### Core Functionality
✅ Doctor registration and management  
✅ Patient registration and medical history tracking  
✅ Appointment booking and cancellation  
✅ Bill generation and payment tracking  
✅ Search and filter operations  
✅ Data persistence (CSV & serialization)  

### Technical Highlights
- **Java 17** with latest language features
- **OOP Design**: Inheritance (Person → Doctor/Patient), Polymorphism, Encapsulation
- **SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **Design Patterns**: 
  - Generic DataStore`<T>` for type-safe collections
  - Service layer architecture
  - Validator utility pattern
- **Exception Handling**: Custom checked exceptions
- **Thread-Safe Collections**: For concurrent access
- **Java Streams**: Filtering and data transformation
- **Serialization**: Object persistence
- **CSV Operations**: Import/Export data

---

## Project Structure

```
meditrack/
├── src/main/java/com/airtribe/meditrack/
│   ├── Main.java                          # Application entry point
│   ├── constants/
│   │   └── Constants.java                 # Application constants
│   ├── entity/
│   │   ├── Person.java                    # Abstract base class
│   │   ├── Doctor.java                    # Doctor entity
│   │   ├── Patient.java                   # Patient entity
│   │   ├── Appointment.java               # Appointment entity
│   │   ├── Bill.java                      # Bill entity
│   │   └── BillSummary.java               # Immutable bill summary
│   ├── interface/
│   │   ├── Searchable.java                # Searchable contract
│   │   └── Payable.java                   # Payable contract
│   ├── exception/
│   │   ├── AppointmentNotFoundException.java
│   │   └── InvalidDataException.java
│   ├── service/
│   │   ├── DoctorService.java             # Doctor business logic
│   │   ├── PatientService.java            # Patient business logic
│   │   └── AppointmentService.java        # Appointment business logic
│   ├── util/
│   │   ├── DataStore.java                 # Generic thread-safe store
│   │   ├── IdGenerator.java               # ID generation utility
│   │   ├── Validator.java                 # Input validation
│   │   ├── DateUtil.java                  # Date operations
│   │   ├── CSVUtil.java                   # CSV import/export
│   │   └── AIHelper.java                  # Optional AI features
│   └── test/
│       └── TestRunner.java                # Manual test suite
├── src/test/java/                         # JUnit tests (optional)
├── docs/
│   ├── JVM_Report.md                      # JVM analysis
│   ├── Setup_Instructions.md              # Setup guide
│   ├── Design_Decisions.md                # Architecture decisions
│   └── UML_Diagram.md                     # Class diagram
├── pom.xml                                # Maven configuration
├── README.md                              # This file
└── .gitignore                             # Git ignore rules
```

---

## Technology Stack

| Component | Version |
|-----------|---------|
| Java | 17 LTS |
| Build Tool | Maven 3.9+ |
| Testing | JUnit 4 (optional) |
| IDE | IntelliJ IDEA / VS Code |
| VCS | Git |

---

## Getting Started

### Prerequisites

- **Java 17+**: [Download JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven 3.9+**: [Download Maven](https://maven.apache.org/download.cgi)
- **Git**: [Download Git](https://git-scm.com/)

### Installation

#### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/meditrack.git
cd meditrack
```

#### 2. Verify Java Installation
```bash
java -version
```
Expected output:
```
java version "17.x.x"
```

#### 3. Build the Project
```bash
mvn clean install
```

#### 4. Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

Or compile and run:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

#### 5. Run Tests
```bash
# Run manual test suite
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"

# Run JUnit tests (if configured)
mvn test
```

---

## Usage Examples

### Register a Doctor
```java
Doctor doctor = doctorService.registerDoctor(
    "Dr. Rajesh Kumar",           // name
    "rajesh@hospital.com",         // email
    "9876543210",                  // phone
    "Mumbai, India",               // address
    "Cardiology",                  // specialty
    "LIC001",                      // license number
    15                             // years of experience
);
```

### Register a Patient
```java
Patient patient = patientService.registerPatient(
    "Ajay Chouhan",                           // name
    "ajay@email.com",                         // email
    "9123456789",                             // phone
    "Bangalore, India",                       // address
    LocalDate.of(1990, 5, 15),               // date of birth
    "Male",                                   // gender
    "O+"                                      // blood group
);
```

### Book an Appointment
```java
Appointment appointment = appointmentService.bookAppointment(
    doctor.getId(),                           // doctor ID
    patient.getId(),                          // patient ID
    LocalDateTime.of(2026, 3, 15, 10, 30),  // appointment time
    "Routine cardiac checkup"                 // reason
);
```

### Search Operations
```java
// Get all cardiologists
List<Doctor> cardiologists = doctorService.getDoctorsBySpecialty("Cardiology");

// Find patient appointments
List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);

// Get appointments by status
List<Appointment> completed = appointmentService.getAppointmentsByStatus("COMPLETED");
```

---

## Sample Output

```
========================================
  MEDITRACK - Clinic Management System
========================================

--- Registering Doctors ---
✓ Registered: Doctor{id='DOC1000', name='Dr. Rajesh Kumar', specialty='Cardiology', licenseNumber='LIC001', yearsOfExperience=15, available=true}

--- Registering Patients ---
✓ Registered: Patient{id='PAT2000', name='Ajay Chouhan', dateOfBirth=1990-05-15, gender='Male', bloodGroup='O+'}

--- Booking Appointments ---
✓ Booked: Appointment{id='APT3000', doctorId='DOC1000', patientId='PAT2000', appointmentDateTime=2026-03-15T10:30, status='BOOKED', reason='Routine cardiac checkup'}

--- System Summary ---
Total Doctors: 2
Total Patients: 2
Total Appointments: 2

========================================
  Demo completed successfully!
========================================
```

---

## Key Design Patterns & OOP Concepts

### 1. **Inheritance**
- `Person` - Abstract base class
- `Doctor` extends `Person`
- `Patient` extends `Person`

```java
public abstract class Person implements Searchable, Serializable {
    // Common properties and methods
}

public class Doctor extends Person {
    // Doctor-specific properties
}
```

### 2. **Polymorphism (Interfaces)**
- `Searchable` - For entities with searchable IDs
- `Payable` - For billable entities

```java
public interface Searchable {
    String getId();
}

public class Bill implements Searchable, Payable {
    // Implements both contracts
}
```

### 3. **Generics**
- Generic `DataStore<T>` for type-safe collections

```java
public class DataStore<T extends Searchable> {
    private final List<T> store = Collections.synchronizedList(new ArrayList<>());
}
```

### 4. **Exceptions**
- Custom checked exceptions for domain-specific errors

```java
public class AppointmentNotFoundException extends Exception {
    // Domain-specific exception
}
```

### 5. **Immutability**
- Immutable `BillSummary` class using `final` keyword

```java
public final class BillSummary {
    private final String patientId;
    private final List<Bill> bills;
    // Defensive copying
}
```

### 6. **Service Layer**
- Separation of concerns with service classes

```java
public class AppointmentService {
    // Business logic encapsulated here
}
```

---

## Exception Handling

The system uses custom exceptions for better error handling:

```java
try {
    Appointment apt = appointmentService.bookAppointment(
        doctorId, patientId, dateTime, reason
    );
} catch (InvalidDataException e) {
    System.err.println("Validation error: " + e.getMessage());
} catch (AppointmentNotFoundException e) {
    System.err.println("Resource not found: " + e.getMessage());
}
```

---

## Data Persistence

### CSV Export
```java
CSVUtil.writeDoctorsToCSV("doctors.csv", doctorService.getAllDoctors());
CSVUtil.writePatientsToCSV("patients.csv", patientService.getAllPatients());
```

### Serialization
```java
DataStore<Doctor> doctorStore = new DataStore<>();
doctorStore.serialize("doctors.dat");
doctorStore.deserialize("doctors.dat");
```

---

## Building & Deployment

### Generate JAR
```bash
mvn clean package
```

### Generate JavaDoc
```bash
mvn javadoc:javadoc
# Open target/site/apidocs/index.html
```

### Create Fat JAR (with dependencies)
```bash
mvn clean compile assembly:single
java -jar target/meditrack-1.0.0-jar-with-dependencies.jar
```

---

## Testing

Run the comprehensive test suite:
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

**Test Coverage:**
- ✓ Doctor registration and retrieval
- ✓ Patient management
- ✓ Appointment booking and cancellation
- ✓ Data validation
- ✓ ID generation
- ✓ Date utilities
- ✓ Search functionality

---

## Documentation Files

- **[JVM_Report.md](docs/JVM_Report.md)** - JVM performance analysis and memory management
- **[Setup_Instructions.md](docs/Setup_Instructions.md)** - Detailed environment setup guide
- **[Design_Decisions.md](docs/Design_Decisions.md)** - Architectural decisions and trade-offs

---

## Future Enhancements

- 📱 REST API with Spring Boot
- 🗄️ Database integration (MySQL/PostgreSQL)
- 🔐 Authentication & Authorization
- 🤖 AI-powered doctor recommendations
- 📊 Analytics dashboard
- 📧 Email notifications
- 💳 Payment gateway integration
- 🧵 Advanced concurrency features
- 📱 Mobile app integration

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes with meaningful commits
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## Author

**Your Name**  
Email: your.email@example.com  
GitHub: [@yourusername](https://github.com/yourusername)

---

## License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.

---

## Support

For questions, issues, or suggestions:
- 📧 Email: your.email@example.com
- 🐛 GitHub Issues: [Create an issue](https://github.com/yourusername/meditrack/issues)
- 📚 Documentation: [Full docs](docs/)

---

**Last Updated:** March 5, 2026  
**Version:** 1.0.0  
**Status:** ✅ Production Ready