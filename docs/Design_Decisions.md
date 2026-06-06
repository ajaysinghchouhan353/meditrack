# Design Decisions — MediTrack

Generated: 2026-06-06

This document captures notable design decisions made while implementing MediTrack. It is intended to help maintainers and reviewers understand trade-offs and rationale.

## 1. Project Structure
- Keep a layered structure: `entity`, `service`, `util`, `demo`, `exception`, `constants`.
- Rationale: clear separation of concerns enhances testability and maintainability.

## 2. Package Naming
- Interfaces consolidated under `com.airtribe.meditrack.interfaces` (was `iface`).
- Rationale: `interfaces` is more explicit and aligns with common Java conventions.

## 3. Persistence
- Dual persistence strategy: CSV export/import (`CSVUtil`) for human-readable backups and Java serialization (`DataStore.serialize()`) for fast binary snapshots.
- Rationale: CSV for portability and audit; serialization for complete object graph snapshots and quick restore.

## 4. Repository/Storage
- Implemented a generic `DataStore<T extends Searchable>` using thread-safe collections (ConcurrentHashMap and synchronized lists where needed).
- Rationale: type-safety, centralized access, and easier swapping of backend storage.

## 5. Service Layer
- Core business logic lives in service classes (`*Service`): `DoctorService`, `PatientService`, `AppointmentService`, `BillService`.
- Rationale: decouples domain model from orchestration and UI, enabling reuse and testing.

## 6. Design Patterns
- Strategy: `BillingStrategy` with concrete implementations (Standard, Premium, Discounted, Emergency) selected at runtime by `BillFactory`.
- Observer: `AppointmentListener` interface and `NotificationService` to decouple event producers from consumers.
- Factory: `BillFactory` centralizes bill creation and strategy selection.
- Template Method: `AbstractReportTemplate` used by `AppointmentAnalyticsReport` to standardize report structure.

## 7. Error Handling and Validation
- Centralized validation in `Validator` with custom exceptions (`InvalidDataException`, `AppointmentNotFoundException`).
- Rationale: consistent error messages and predictable exception types for calling code.

## 8. Concurrency
- Use thread-safe collections and careful synchronization where required (ID generation uses `AtomicLong`).
- Rationale: allow safe concurrent operations for future multi-threaded enhancements (reminders, background tasks).

## 9. Demo & Tests
- Include `QuickDemo` for a 60-second overview and `DemoRunner` for a comprehensive showcase. `TestRunner` is a manual test harness.
- Rationale: easy verification without a CI system; demos help reviewers quickly validate implemented features.

## 10. Documentation
- Keep essential docs (`Setup_Instructions.md`, `DEMO_OUTPUT.md`, `Design_Decisions.md`) in `docs/`. Other in-depth docs were pruned to keep the repo focused.

## 11. Build & Tooling
- Maven (`pom.xml`) manages build lifecycle. `maven-javadoc-plugin` is configured for JavaDoc generation.
- Rationale: standard Java build tooling ensures portability and easy CI integration.

## 12. Future Improvements (non-blocking)
- Add automated unit tests (JUnit) and integrate CI (GitHub Actions) to run build, tests, and JavaDoc.
- Replace Java serialization with a more robust format (e.g., JSON with Jackson) for compatibility.
- Add an integration test harness and sample dataset for regression testing.

---

If you'd like, I can expand any section (e.g., include code pointers, sequence diagrams, or commit references).
