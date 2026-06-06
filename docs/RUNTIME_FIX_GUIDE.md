# Runtime Fix Guide - MediTrack

## Common Issues

- **Java not found**: Verify `JAVA_HOME` and `PATH`.
- **Maven not found**: Verify `M2_HOME` and Maven installation.
- **Port or file issues**: Re-run from a clean workspace and ensure the `data/` directory is writable.
- **Build errors after edits**: Run `mvn clean compile` to refresh generated output.

## Recovery Commands

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main" -Dexec.args="--loadData"
```