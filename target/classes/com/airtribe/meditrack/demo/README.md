# MediTrack Demo Package

This package contains comprehensive demonstration files showcasing all features of the MediTrack application.

## Demo Files

### 1. **QuickDemo.java** ⚡ (Recommended for first-time users)

**Purpose**: 60-second overview of core functionality  
**Duration**: ~1 minute  
**Features Covered**:
- Doctor registration
- Patient registration
- Appointment booking
- Appointment workflow (confirm → complete)
- Bill generation with Strategy Pattern
- Payment processing
- Observer Pattern notifications

**How to Run**:
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.QuickDemo"
```

**Expected Output**: Simple, clean demonstration of the complete workflow from doctor registration to payment processing.

---

### 2. **DemoRunner.java** 📚 (Comprehensive demonstration)

**Purpose**: Complete showcase of ALL MediTrack features  
**Duration**: ~2-3 minutes  
**Features Covered**:

1. **Doctor Management** (CRUD operations)
   - Register multiple doctors with different specializations
   - Search by specialty
   - Update doctor information
   - List all doctors

2. **Patient Management** (CRUD operations)
   - Register multiple patients
   - Update medical history
   - Clone patients (Cloneable interface demo)
   - Search by blood group

3. **Appointment Management**
   - Book appointments
   - Confirm appointments
   - Complete appointments with notes
   - Cancel appointments
   - Search by status

4. **Billing System** (Strategy Pattern)
   - StandardBillingStrategy
   - PremiumBillingStrategy (10% service charge)
   - DiscountedBillingStrategy (15% discount)
   - EmergencyBillingStrategy (25% surcharge)
   - Payment processing
   - BillSummary (Immutable Value Object)

5. **Notification System** (Observer Pattern)
   - Automatic notifications on appointment events
   - ConsoleNotificationListener demonstration

6. **Advanced Search**
   - Search doctors by specialization
   - Search appointments by status
   - Search patients by blood group
   - Filter unpaid bills

7. **Enum Usage**
   - AppointmentStatus enum
   - Specialization enum
   - Enum methods demonstration

8. **Design Patterns Showcase**
   - Generic Repository Pattern (DataStore<T>)
   - Strategy Pattern (BillingStrategy)
   - Observer Pattern (AppointmentListener)
   - Factory Pattern (BillFactory)
   - Service Layer Pattern
   - Immutable Value Object (BillSummary)

9. **Exception Handling**
   - InvalidDataException
   - AppointmentNotFoundException
   - Validation exceptions

10. **Data Persistence**
    - CSV export/import capabilities
    - Java serialization
    - Thread-safe storage

**How to Run**:
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.DemoRunner"
```

**Expected Output**: Detailed demonstration with statistics, using formatted console output with symbols (✓, ✗, →, etc.)

---

## Quick Start Guide

### First Time Users

1. **Start with QuickDemo**:
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.QuickDemo"
   ```

2. **Then explore DemoRunner**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.demo.DemoRunner"
   ```

3. **Finally, try the interactive Main application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
   ```

### For Developers

These demos are excellent for:
- Understanding the system architecture
- Learning how services interact
- Seeing design patterns in action
- Testing after code changes
- Demonstrating features to stakeholders

---

## Demo Output Features

Both demos include:
- ✓ Success indicators
- ✗ Error indicators
- → Action indicators
- ⚠ Warning indicators
- Formatted sections and headers
- Detailed statistics
- Real-time SLF4J logging (check logs/ folder)

---

## What You'll Learn

### Object-Oriented Design
- Inheritance hierarchies
- Interface implementations
- Abstract classes
- Polymorphism in action

### SOLID Principles
- Single Responsibility: Each service has one purpose
- Open/Closed: Extensible via interfaces
- Liskov Substitution: Subtypes work seamlessly
- Interface Segregation: Focused interfaces
- Dependency Inversion: Program to interfaces

### Design Patterns
- **Strategy Pattern**: Multiple billing strategies selected at runtime
- **Observer Pattern**: Event-driven notifications
- **Factory Pattern**: Bill creation abstraction
- **Repository Pattern**: Generic data storage
- **Service Layer**: Business logic separation
- **Value Object**: Immutable data transfer

### Modern Java Features
- Generics with bounded types
- Stream API and lambda expressions
- Optional for null safety
- Enums with methods
- LocalDate/LocalDateTime
- Try-with-resources

---

## Customization

You can modify these demos to:
- Test specific features
- Add new use cases
- Demonstrate custom scenarios
- Create integration tests
- Benchmark performance

---

## Troubleshooting

**Issue**: `ClassNotFoundException`  
**Solution**: Run `mvn clean install` first

**Issue**: Logging output too verbose  
**Solution**: Adjust `src/main/resources/logback.xml` log level

**Issue**: Want to see more details  
**Solution**: Run DemoRunner instead of QuickDemo

**Issue**: Want less output  
**Solution**: Run QuickDemo instead of DemoRunner

---

## Next Steps

After running the demos:

1. Explore the source code in `src/main/java/com/airtribe/meditrack/`
2. Read `/docs/PROJECT_SUMMARY.md` for architecture details
3. Check `/docs/JVM_Report.md` for JVM insights
4. Try the interactive Main application
5. Write your own test scenarios using TestRunner

---

## Contributing

To add new demos:
1. Create a new class in this package
2. Follow the naming convention: `[Feature]Demo.java`
3. Include clear console output
4. Add documentation in this README
5. Update the main README if needed

---

**Package**: `com.airtribe.meditrack.demo`  
**Created**: March 6, 2026  
**Version**: 1.0.0  
**Maintainers**: Airtribe MediTrack Team
