# MediTrack — Clinic & Appointment Management System

> A professional-grade **Clinic & Appointment Management System** demonstrating enterprise Java architecture, advanced OOP principles, design patterns, and modern Java 17 features.

**Version**: 1.0.0 | **Java**: 17 LTS | **Status**: ✅ Production Ready | **Last Updated**: June 6, 2026

---

## 📝 Release Notes (June 6, 2026)

- Version `1.0.0` release: core functionality implemented and verified.
- Major updates: consolidated interface package (`com.airtribe.meditrack.interfaces`), added comprehensive demos, improved persistence and reporting.
-- Demo output: run `com.airtribe.meditrack.demo.DemoRunner` locally to capture demo output (not included in repository).


## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Quick Start](#quick-start)
- [Project Structure](#project-structure)
- [Core Concepts](#core-concepts)
- [Usage Examples](#usage-examples)
- [Architecture](#architecture)
- [Design Patterns](#design-patterns)
- [Documentation](#documentation)
- [Requirements Coverage](#requirements-coverage)
- [Testing](#testing)
- [Contributing](#contributing)

---

## 🎯 Overview

MediTrack is a **comprehensive healthcare management system** built with Java 17 that demonstrates:
- ✅ Strong OOP design with 4 pillars (Encapsulation, Inheritance, Polymorphism, Abstraction)
- ✅ SOLID principles applied throughout the codebase
- ✅ 7+ enterprise design patterns (Singleton, Factory, Strategy, Observer, Template Method, Repository, Service Layer)
- ✅ Modern Java features (Streams, Lambdas, Generics, Enums)
- ✅ Type-safe implementations with comprehensive exception handling
- ✅ Production-ready code with 2,500+ lines across 35+ classes

**Manages**:
- 👨‍⚕️ **Doctors** - Healthcare professionals with specialties and credentials
- 🧑‍🤝‍🧑 **Patients** - Clinic patients with medical history and demographics
- 📅 **Appointments** - Scheduled consultations with enum-based status tracking
- 💰 **Billing** - Multi-strategy invoice generation and payment tracking

---

## ✨ Key Features

### 🏥 Core Functionality
| Feature | Details |
|---------|---------|
| 👨‍⚕️ **Doctor Management** | Register, search by specialty, manage availability |
| 🧑‍⚕️ **Patient Management** | Registration, medical history, contact information |
| 📅 **Appointment System** | Book, cancel, complete with status tracking |
| 💳 **Billing System** | Multiple strategies, tax calculation, payment tracking |
| 🔍 **Advanced Search** | Search doctors by specialty, patients by ID/name, appointments by status |
| 💾 **Data Persistence** | CSV export/import + Java serialization |
| 🔔 **Notifications** | Observer pattern for appointment events |

### 🚀 Technical Highlights
- **Java 17 LTS** with latest language features (records, sealed classes ready)
- **OOP Excellence**: Inheritance, polymorphism, encapsulation, abstraction
- **Type Safety**: Generics, enums (AppointmentStatus, Specialization), bounded types
- **Concurrency**: Thread-safe collections, synchronized blocks, TimerTask
- **Functional Programming**: Streams API, lambda expressions, method references
- **Design Patterns**: 7 enterprise patterns implemented
- **Exception Handling**: Custom exceptions with proper hierarchy
- **Code Quality**: 35+ classes, comprehensive JavaDocs, 0 compile errors

---

## 🚀 Quick Start

### Prerequisites
```bash
# Verify Java 17+ installation
java -version
# Expected: java version "17.x.x" or higher

# Verify Maven 3.9+
mvn --version
# Expected: Apache Maven 3.9.x or higher
```

### Installation & Run (60 seconds)

```bash
# 1. Clone and navigate
git clone https://github.com/yourusername/meditrack.git
cd meditrack

# 2. Build the project
mvn clean install

# 3. Run Quick Demo (Recommended for first-time users!)
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.QuickDemo"

# 4. Run Comprehensive Demo (All features)
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.DemoRunner"

# 5. Run interactive menu
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"

# 6. Load saved data on startup
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main" -Dexec.args="--loadData"

# 7. Or run with demo mode
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main" -Dexec.args="--demo"

# 8. Run manual tests
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

### Packaging (runnable fat JAR)

Build an executable jar with dependencies (assembly):

```bash
# Produces: target/meditrack-1.0.0-jar-with-dependencies.jar
mvn -q clean package assembly:single
```

Run the assembled JAR (demo mode):

```bash
java -jar target/meditrack-1.0.0-jar-with-dependencies.jar --demo
```

On Windows you can use the included `run-demo.bat` which builds the JAR if missing and runs the demo:

```powershell
.\run-demo.bat
```

**Expected Output**:
```
========================================
MEDITRACK - Clinic Management System
========================================

1. Doctor Management
2. Patient Management
3. Appointment Management
4. Billing Management
5. Demonstrate Design Patterns
6. Demonstrate Streams API
7. Demonstrate Cloning
8. Run Full Demo
0. Exit
Select option:
```

---

## 📁 Project Structure

```
meditrack/ (Root)
│
├── src/main/java/com/airtribe/meditrack/
│   ├── Main.java                          ← Entry point (menu + demo modes)
│   │
│   ├── demo/                              ← Demo files (NEW! 📚)
│   │   ├── QuickDemo.java                 ├─ 60-second quick overview
│   │   ├── DemoRunner.java                ├─ Comprehensive feature demo
│   │   └── README.md                      └─ Demo documentation
│   │
│   ├── entity/                            ← Domain model classes (6 classes)
│   │   ├── Person.java                    ┌─ Abstract base class
│   │   ├── Doctor.java                    ├─ Specialization enum reference
│   │   ├── Patient.java                   ├─ Cloneable with deep copy
│   │   ├── Appointment.java               ├─ Status enum, Cloneable
│   │   ├── Bill.java                      ├─ Strategy pattern support
│   │   └── BillSummary.java               └─ Immutable final class
│   │
│   ├── service/                           ← Business logic (6 classes)
│   │   ├── DoctorService.java             ├─ CRUD + specialty search
│   │   ├── PatientService.java            ├─ CRUD + name search
│   │   ├── AppointmentService.java        ├─ Booking, cancellation, observer integration
│   │   ├── BillService.java               ├─ Multiple strategy support
│   │   ├── NotificationService.java       ├─ Observer pattern subject
│   │   └── ConsoleNotificationListener.java└─ Observer pattern observer
│   │
│   ├── util/                              ← Utilities & helpers (9 classes)
│   │   ├── DataStore.java                 ├─ Generic<T extends Searchable> repository
│   │   ├── Validator.java                 ├─ Input validation
│   │   ├── DateUtil.java                  ├─ Date/time operations
│   │   ├── IdGenerator.java               ├─ Unique ID creation (Doctor, Patient, etc.)
│   │   ├── CSVUtil.java                   ├─ CSV read/write operations
│   │   ├── AppConfig.java                 ├─ Singleton with double-checked locking
│   │   ├── BillFactory.java               ├─ Factory pattern with enums
│   │   ├── BillingStrategies.java         ├─ 4 concrete strategy implementations
│   │   └── AIHelper.java                  └─ Optional AI features placeholder
│   │
│   ├── interfaces/                        ← Contracts (4 interfaces)
│   │   ├── Searchable.java                ├─ ID-based search contract
│   │   ├── Payable.java                   ├─ Payment operations contract
│   │   ├── BillingStrategy.java           ├─ Strategy pattern contract
│   │   └── AppointmentListener.java       └─ Observer pattern contract
│   │
│   ├── exception/                         ← Custom exceptions (2 classes)
│   │   ├── AppointmentNotFoundException.java
│   │   └── InvalidDataException.java
│   │
│   ├── constants/                         ← Constants (3 files)
│   │   ├── Constants.java                 ├─ Application-wide constants
│   │   ├── AppointmentStatus.java         ├─ Enum: PENDING, CONFIRMED, COMPLETED, etc.
│   │   └── Specialization.java            └─ Medical specialization enum
│   │
│   ├── enums/                             ← Type-safe enums
│   │   └── (reserved for future enums)
│   │
│   └── test/                              ← Test suite (1 class)
│       └── TestRunner.java                └─ Manual tests (18 test cases)
│
├── src/test/java/                         ← JUnit test directory (optional)
│
├── docs/                                  ← Documentation
│   ├── Setup_Instructions.md              ├─ Environment setup guide
│   ├── JVM_Report.md                      ├─ JVM analysis & performance
│   ├── Design_Decisions.md                ├─ Architecture decisions
│   ├── REQUIREMENTS_VERIFICATION.md       ├─ Requirements traceability
│   ├── REQUIREMENTS_CHECKLIST.md          ├─ Quick validation checklist
│   ├── RUNTIME_FIX_GUIDE.md               └─ Runtime troubleshooting
│
├── pom.xml                                ← Maven configuration
├── README.md                              ← This file
└── .gitignore                             ← Git ignore rules

Total: 35+ Java classes | ~2,500+ lines of code | 0 compile errors
```

---

## 🏛️ Architecture

### Layered Architecture
```
┌─────────────────────────────────────────────────┐
│          UI Layer (Main.java)                   │
│     - Interactive menu system                   │
│     - Console-based user interaction            │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│      Business Logic Layer (Services)            │
│     - DoctorService                             │
│     - PatientService                            │
│     - AppointmentService (with Observer)        │
│     - BillService (with Strategy)               │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│   Data Access Layer (DataStore<T>)              │
│     - Generic repository pattern                │
│     - Thread-safe collections                   │
│     - CSV & serialization support               │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│        Domain Model Layer (Entities)            │
│     - Person, Doctor, Patient                   │
│     - Appointment, Bill, BillSummary            │
│     - Type-safe enums                           │
└─────────────────────────────────────────────────┘
```

---

## 💡 Core Concepts Demonstrated

### 1. **Encapsulation** - Data Hiding & Validation
Private fields with public accessors + centralized validation:
```java
public class Doctor extends Person {
    private Specialization specialty;        // Private field
    private String licenseNumber;
    private int yearsOfExperience;
    
    public void setSpecialty(Specialization specialty) {  // Public setter
        Validator.validateSpecialty(specialty);          // Validation
        this.specialty = specialty;
    }
}
```

### 2. **Inheritance** - Code Reuse & Hierarchy
Abstract base class provides common behavior:
```java
public abstract class Person implements Searchable, Serializable {
    protected String id, name, email, phoneNumber, address;  // Shared fields
    public Person(String id, String name, ...) { ... }
}

public class Doctor extends Person {  // Inherits all Person fields/methods
    private Specialization specialty;
    
    public Doctor(...) {
        super(id, name, email, phone, address);  // Constructor chaining
        this.specialty = specialty;
    }
}
```

### 3. **Polymorphism** - Flexible Behavior
Method overloading and overriding:
```java
// Overloading: Multiple signatures
public Doctor(String id, String name, ..., Specialization specialty, ...) { }
public Doctor(String id, String name, ..., String specialtyStr, ...) { }

// Overriding: Different implementations
@Override
public String toString() {  // Each class has its own toString()
    return "Doctor{...}";
}
```

### 4. **Abstraction** - Interface Contracts
Interfaces define what implementations must do:
```java
public interface Searchable {
    String getId();
}

public interface BillingStrategy {
    double calculateAmount(Bill bill);
    String getStrategyName();
}

// Bill implements both contracts
public class Bill implements Searchable, Payable, BillingStrategy { ... }
```

### 5. **Type Safety** - Enums & Generics
Compile-time safety through type-safe enums:
```java
public enum AppointmentStatus {
    PENDING("PEND", "Waiting for confirmation"),
    CONFIRMED("CONF", "Confirmed by doctor"),
    COMPLETED("COMP", "Appointment completed"),
    CANCELLED("CANC", "Cancelled");
    
    private final String code;
    private final String description;
    // Methods...
}

// Generic repository with bounded type parameter
public class DataStore<T extends Searchable> { ... }
```

### 6. **Immutability** - Thread-Safe Data
Final class with no setters:
```java
public final class BillSummary implements Serializable {
    private final String patientId;
    private final List<Bill> bills;
    private final double totalAmount;
    // Only constructor initialization, no setters
}
```

### 7. **Concurrency** - Thread Safety
Multiple approaches for safe multi-threaded access:
```java
// Synchronized collections
private final List<T> store = Collections.synchronizedList(new ArrayList<>());

// Synchronized block
synchronized (lock) {
    if (instance == null) {
        instance = new AppConfig();
    }
}

// Volatile fields
private static volatile AppConfig instance;

// Callable reminders
reminderTimer.schedule(new TimerTask() { ... }, delay);
```

---

## 🎨 Design Patterns

| Pattern | Location | Purpose |
|---------|----------|---------|
| **Singleton** | `AppConfig.java` | Global configuration with lazy initialization |
| **Factory** | `BillFactory.java` | Create bills with appropriate strategies |
| **Strategy** | `BillingStrategy.java` + 4 implementations | Multiple billing algorithms |
| **Observer** | `NotificationService.java` | Notify listeners of appointment events |
| **Template Method** | `AbstractReportTemplate.java` | Common report workflow in base class |
| **Generic Repository** | `DataStore<T>` | Type-safe data access |
| **Service Layer** | `*Service.java` classes | Encapsulate business logic |

---

## 🧪 Testing

- Automated tests (JUnit / Surefire):

```bash
# Run the automated test suite
mvn -q clean test
```

- Manual test harness (`TestRunner`):

```bash
# Run the TestRunner via Maven exec (manual harness)
mvn -Dexec.mainClass=com.airtribe.meditrack.test.TestRunner org.codehaus.mojo:exec-maven-plugin:3.1.0:java

# Or run from the assembled fat JAR (recommended for repeatable runs)
java -cp target/meditrack-1.0.0-jar-with-dependencies.jar com.airtribe.meditrack.test.TestRunner
```

- Current manual test results (last run): **18/18 passed**. The `TestRunner` file is `src/main/java/com/airtribe/meditrack/test/TestRunner.java`.

---

## 📚 Build & Documentation

- Build and assemble a runnable JAR (with dependencies):

```bash
# Produces: target/meditrack-1.0.0-jar-with-dependencies.jar
mvn -q clean package assembly:single
```

- JavaDoc (API docs):

```bash
# Generate JavaDoc site
mvn javadoc:javadoc
# View: target/site/apidocs/index.html
```

- Artifacts & helper scripts:
    - `run-demo.bat` — Windows helper: builds the fat JAR if missing and runs demo (`--demo`).
    - Assembled JAR: `target/meditrack-1.0.0-jar-with-dependencies.jar` (after assembly).

### Pattern Examples

**Singleton Pattern** (Double-Checked Locking):
```java
public static AppConfig getInstance() {
    if (instance == null) {
        synchronized (lock) {
            if (instance == null) {
                instance = new AppConfig();
            }
        }
    }
    return instance;
}
```

**Strategy Pattern** (Multiple Billing Strategies):
```java
Bill bill = billService.createBill(appointmentId, fee, "PREMIUM");
// Internally selects PremiumBillingStrategy
// Different calculation logic applied automatically
```

**Observer Pattern** (Appointment Notifications):
```java
notificationService.registerListener(new ConsoleNotificationListener());
// When appointment booked → listener notified automatically
appointmentService.bookAppointment(...);  // Triggers notification
```

---

## 🔌 Usage Examples

### Register a Doctor
```java
try {
    Doctor doctor = doctorService.registerDoctor(
        "Dr. Rajesh Kumar",              // Name
        "rajesh@hospital.com",            // Email
        "9876543210",                     // Phone
        "Mumbai, India",                  // Address
        "CARDIOLOGY",                     // Specialty (type-safe string)
        "LIC001",                         // License
        15                                // Years of experience
    );
    System.out.println("Registered: " + doctor);
} catch (InvalidDataException e) {
    System.err.println("Validation failed: " + e.getMessage());
}
```

### Register a Patient
```java
try {
    Patient patient = patientService.registerPatient(
        "Ajay Chouhan",
        "ajay@email.com",
        "9123456789",
        "Bangalore, India",
        LocalDate.of(1990, 5, 15),       // Date of birth
        "Male",                           // Gender
        "O+"                              // Blood group
    );
    System.out.println("Registered: " + patient);
} catch (InvalidDataException e) {
    System.err.println("Validation failed: " + e.getMessage());
}
```

### Book an Appointment
```java
try {
    Appointment appointment = appointmentService.bookAppointment(
        doctor.getId(),
        patient.getId(),
        LocalDateTime.of(2026, 3, 15, 10, 30),
        "Routine cardiac checkup"
    );
    System.out.println("Booked: " + appointment);
    // Observers notified automatically!
} catch (AppointmentNotFoundException e) {
    System.err.println("Doctor or patient not found");
} catch (InvalidDataException e) {
    System.err.println("Invalid appointment details");
}
```

### Search Operations (Streams & Lambdas)

### Create Fat JAR (with dependencies)
```bash
mvn clean compile assembly:single
java -jar target/meditrack-1.0.0-jar-with-dependencies.jar
```

---

## 🎬 Demo Files

MediTrack includes comprehensive demo files showcasing all features:

### QuickDemo ⚡ (60 seconds - Recommended!)

**Perfect for first-time users** - A streamlined demonstration of the complete workflow.

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.QuickDemo"
```

**What it demonstrates:**
- ✓ Doctor registration
- ✓ Patient registration
- ✓ Appointment booking workflow
- ✓ Bill generation with Strategy Pattern
- ✓ Payment processing
- ✓ Observer Pattern notifications

**Output**: Clean, formatted console display with step-by-step progress.

---

### DemoRunner 📚 (Comprehensive - 2-3 minutes)

**For in-depth exploration** - Complete showcase of ALL MediTrack features.

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.DemoRunner"
```

**What it demonstrates:**
1. **Doctor Management**: CRUD operations, search by specialty
2. **Patient Management**: Registration, medical history, Cloneable pattern
3. **Appointment Management**: Book, confirm, complete, cancel
4. **Billing Strategies**: Standard, Premium, Discounted, Emergency billing
5. **Notification System**: Observer pattern in action
6. **Advanced Search**: Multiple search criteria and filters
7. **Enum Usage**: AppointmentStatus, Specialization enums
8. **Design Patterns**: All 8+ patterns demonstrated
9. **Exception Handling**: Custom exceptions in action
10. **Data Persistence**: CSV export/import, serialization

**Output**: Detailed demonstration with statistics and formatted sections.

---

### Demo Package Documentation

For complete demo documentation, see:
```
src/main/java/com/airtribe/meditrack/demo/README.md
```

This includes:
- Detailed feature descriptions
- Customization guides
- Troubleshooting tips
- Learning objectives

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