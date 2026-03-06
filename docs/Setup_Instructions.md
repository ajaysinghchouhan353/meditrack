# Setup Instructions - MediTrack

## System Requirements

### Minimum Requirements
- **OS**: Windows 10+, macOS 10.14+, Ubuntu 18.04+
- **RAM**: 4GB minimum (8GB recommended)
- **Disk Space**: 500MB for JDK + 100MB for project
- **Internet**: Required for Maven dependencies

### Recommended Setup
- **OS**: Windows 11, macOS 12+, Ubuntu 22.04+
- **RAM**: 8GB or more
- **SSD**: For faster builds and compilation
- **Internet**: Broadband for dependency downloads

---

## Step 1: Install Java Development Kit (JDK) 17

### Windows

1. Download JDK 17 from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. Run the installer:
   ```
   jdk-17.0.x_windows-x64_bin.exe
   ```
3. Follow the installation wizard (use defaults)
4. Install to default location: `C:\Program Files\Java\jdk-17.0.x`

5. Set JAVA_HOME environment variable:
   - Right-click "This PC" → Properties
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - New system variable:
     - Variable name: `JAVA_HOME`
     - Variable value: `C:\Program Files\Java\jdk-17.0.x`
   - Add to PATH: Add `%JAVA_HOME%\bin`

6. Verify installation:
   ```bash
   java -version
   javac -version
   ```

### macOS

1. Download JDK 17 or use Homebrew:
   ```bash
   brew install openjdk@17
   ```

2. Set JAVA_HOME:
   ```bash
   echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
   echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
   source ~/.zshrc
   ```

3. Verify:
   ```bash
   java -version
   javac -version
   ```

### Ubuntu/Linux

1. Install OpenJDK 17:
   ```bash
   sudo apt-get update
   sudo apt-get install openjdk-17-jdk
   ```

2. Set JAVA_HOME:
   ```bash
   echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
   echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
   source ~/.bashrc
   ```

3. Verify:
   ```bash
   java -version
   javac -version
   ```

---

## Step 2: Install Maven 3.9+

### Windows

1. Download Maven from [apache.org](https://maven.apache.org/download.cgi)
   - Download: `apache-maven-3.9.x-bin.zip`

2. Extract to a folder:
   ```
   C:\apache-maven-3.9.x
   ```

3. Set M2_HOME and update PATH:
   - New system variable:
     - Variable name: `M2_HOME`
     - Variable value: `C:\apache-maven-3.9.x`
   - Add to PATH: Add `%M2_HOME%\bin`

4. Verify:
   ```bash
   mvn -version
   ```

### macOS

1. Install using Homebrew:
   ```bash
   brew install maven
   ```

2. Verify:
   ```bash
   mvn -version
   ```

### Ubuntu/Linux

1. Install Maven:
   ```bash
   sudo apt-get install maven
   ```

2. Verify:
   ```bash
   mvn -version
   ```

---

## Step 3: Install Git

### Windows

1. Download from [git-scm.com](https://git-scm.com/)
2. Run installer and follow defaults
3. Verify:
   ```bash
   git --version
   ```

### macOS

1. Install using Homebrew:
   ```bash
   brew install git
   ```

2. Verify:
   ```bash
   git --version
   ```

### Ubuntu/Linux

1. Install Git:
   ```bash
   sudo apt-get install git
   ```

2. Verify:
   ```bash
   git --version
   ```

---

## Step 4: Clone and Setup MediTrack Project

### 1. Create Project Directory

```bash
# Create a workspace directory
mkdir -p ~/Projects
cd ~/Projects
```

### 2. Clone Repository

```bash
git clone https://github.com/yourusername/meditrack.git
cd meditrack
```

Or if no remote repository:
```bash
# Already in project directory
cd meditrack
```

### 3. Verify Project Structure

```bash
# List directory structure
ls -la          # macOS/Linux
dir /s          # Windows

# Should see:
# ├── src/
# ├── docs/
# ├── pom.xml
# ├── README.md
# └── .gitignore
```

### 4. Check Java and Maven Versions

```bash
java -version   # Should show JDK 17.x.x
javac -version  # Should show javac 17.x.x
mvn -version    # Should show Apache Maven 3.9.x
```

---

## Step 5: Build the Project

### Clean Build

```bash
mvn clean install
```

**Expected Output:**
```
[INFO] --------< com.airtribe:meditrack >--------
[INFO] Building MediTrack 1.0.0
[INFO] --------
...
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXs
[INFO] Finished at: 2026-03-05T...
```

### Compile Only (without tests)

```bash
mvn clean compile
```

### Skip Tests During Build

```bash
mvn clean install -DskipTests
```

---

## Step 6: Run the Application

### Option 1: Using Maven

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

### Option 2: Build JAR and Run

```bash
# Create executable JAR
mvn clean package

# Run the JAR
java -jar target/meditrack-1.0.0.jar
```

### Option 3: Create Fat JAR (with dependencies)

```bash
mvn clean compile assembly:single
java -jar target/meditrack-1.0.0-jar-with-dependencies.jar
```

---

## Step 7: Run Tests

### Run Manual Test Suite

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

**Expected Output:**
```
=====================================
  MEDITRACK - TEST RUNNER
=====================================

✓ PASS: Doctor Registration
✓ PASS: Doctor Retrieval
...

=====================================
  TEST SUMMARY
=====================================
Total Tests: 20
Passed: 20
Failed: 0
Success Rate: 100%
```

### Run JUnit Tests (if available)

```bash
mvn test
```

---

## Step 8: IDE Setup

### IntelliJ IDEA

1. Open IntelliJ IDEA
2. File → Open → Navigate to project folder
3. Select the `pom.xml` file
4. Choose "Open as Project"
5. Maven should auto-detect and import dependencies
6. Wait for indexing to complete

**Run Application:**
- Right-click `Main.java` → Run

**Run Tests:**
- Right-click `TestRunner.java` → Run

### VS Code

1. Install extensions:
   - Extension Pack for Java
   - Maven for Java
   - Project Manager for Java

2. Open workspace:
   ```bash
   code .
   ```

3. VS Code auto-detects Maven project

4. Run Application:
   - Press `Ctrl+Shift+D` → Choose Java
   - Or use: `mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"`

---

## Step 9: Generate Documentation

### Generate JavaDoc

```bash
mvn javadoc:javadoc
```

Access documentation:
- Open: `target/site/apidocs/index.html` in browser

### Generate Project Reports

```bash
mvn site
```

Access reports:
- Open: `target/site/index.html` in browser

---

## Troubleshooting

### Issue: Java not found

**Solution:**
```bash
# Check JAVA_HOME
echo %JAVA_HOME%          # Windows
echo $JAVA_HOME            # macOS/Linux

# Reinstall/verify JDK installation
java -version
```

### Issue: Maven not found

**Solution:**
```bash
# Check M2_HOME
echo %M2_HOME%            # Windows
echo $M2_HOME              # macOS/Linux

# Reinstall Maven
mvn -version
```

### Issue: Port already in use

**Solution:**
- Change port in application properties
- Or stop the service using the port

### Issue: Build fails - Missing dependencies

**Solution:**
```bash
# Clear Maven cache
mvn clean

# Force update dependencies
mvn clean install -U
```

### Issue: Class not found during execution

**Solution:**
```bash
# Rebuild project
mvn clean compile

# Check classpath
mvn dependency:resolve
```

### Issue: Permission denied on Unix

**Solution:**
```bash
# Make scripts executable
chmod +x mvn
chmod +x java
```

---

## Verify Installation

Run verification script:

```bash
echo "Java Version:"
java -version

echo ""
echo "Maven Version:"
mvn -version

echo ""
echo "Build Project:"
mvn clean compile

echo ""
echo "Run Application:"
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"

echo ""
echo "Run Tests:"
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

---

## Next Steps

1. Read [README.md](../README.md) for project overview
2. Review [Design_Decisions.md](Design_Decisions.md) for architecture
3. Explore source code in `src/main/java`
4. Run tests and experiment with the API

---

## Getting Help

- 📚 Documentation: See `docs/` folder
- 🐛 Issues: Check GitHub Issues
- 💬 Discussion: Create GitHub Discussion
- 📧 Email: your.email@example.com

---

**Setup Date:** March 5, 2026  
**Last Updated:** March 5, 2026  
**Status:** ✅ Ready for Development
