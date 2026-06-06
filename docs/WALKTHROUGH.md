# Quick Walkthrough

1. Build the project:

```powershell
mvn -q clean compile
```

2. Run the manual test harness:

```powershell
mvn -q exec:java "-Dexec.mainClass=com.airtribe.meditrack.test.TestRunner"
```

3. Start the application (from `Main`):

```powershell
mvn -q exec:java "-Dexec.mainClass=com.airtribe.meditrack.Main"
```

4. Where to find generated docs:
- JavaDoc: `target/site/apidocs/index.html`
- Domain model (Mermaid): `docs/DOMAIN_MODEL.md`

5. Notes:
- Interfaces live under `com.airtribe.meditrack.interfaces`.
- Repository branch: `dev` (commits pushed).
