# 🎯 Patient Management System — Complete Interview Preparation Guide

> **Who is this for?** You know C++ and competitive programming. This is your first project. This guide explains **every single concept** used in this project from absolute scratch — Java basics, OOP, Spring Boot, databases, system design, and more — with interview questions and answers at the end of each section.

---

## Table of Contents

1. [Java Fundamentals (Coming from C++)](#1-java-fundamentals-coming-from-c)
2. [Object-Oriented Programming (OOP) Concepts](#2-object-oriented-programming-oop-concepts)
3. [How the Internet & HTTP Work](#3-how-the-internet--http-work)
4. [What is Spring Boot & Why Use It](#4-what-is-spring-boot--why-use-it)
5. [Project Architecture — Microservices](#5-project-architecture--microservices)
6. [The Layered Architecture Pattern](#6-the-layered-architecture-pattern)
7. [Dependency Injection & Inversion of Control (IoC)](#7-dependency-injection--inversion-of-control-ioc)
8. [Spring Annotations — The Full List Used](#8-spring-annotations--the-full-list-used)
9. [REST API — Controllers & HTTP Methods](#9-rest-api--controllers--http-methods)
10. [Data Transfer Objects (DTOs)](#10-data-transfer-objects-dtos)
11. [Mapper Pattern](#11-mapper-pattern)
12. [Validation](#12-validation)
13. [Exception Handling](#13-exception-handling)
14. [Database & JPA (Java Persistence API)](#14-database--jpa-java-persistence-api)
15. [SQL Concepts Used](#15-sql-concepts-used)
16. [UUID — Universal Unique Identifier](#16-uuid--universal-unique-identifier)
17. [Authentication & Authorization (Auth Service)](#17-authentication--authorization-auth-service)
18. [JWT (JSON Web Token)](#18-jwt-json-web-token)
19. [Password Hashing with BCrypt](#19-password-hashing-with-bcrypt)
20. [Spring Security Basics](#20-spring-security-basics)
21. [API Gateway Pattern](#21-api-gateway-pattern)
22. [gRPC (Google Remote Procedure Call)](#22-grpc-google-remote-procedure-call)
23. [Protocol Buffers (Protobuf)](#23-protocol-buffers-protobuf)
24. [Apache Kafka — Event-Driven Architecture](#24-apache-kafka--event-driven-architecture)
25. [Docker & Containerization](#25-docker--containerization)
26. [Maven — Build Tool](#26-maven--build-tool)
27. [Logging with SLF4J](#27-logging-with-slf4j)
28. [Java Streams & Functional Programming](#28-java-streams--functional-programming)
29. [Optional in Java](#29-optional-in-java)
30. [Generics in Java](#30-generics-in-java)
31. [Interfaces in Java](#31-interfaces-in-java)
32. [Design Patterns Used](#32-design-patterns-used)
33. [System Design Concepts](#33-system-design-concepts)
34. [SOLID Principles](#34-solid-principles)
35. [Complete Functionality Flows — Every Feature Line-by-Line](#35-complete-functionality-flows--every-feature-line-by-line)
36. [Common Interview Questions — Rapid Fire](#36-common-interview-questions--rapid-fire)
37. [Project-Specific Interview Questions — Deep Dive](#37-project-specific-interview-questions--deep-dive)

---

## 1. Java Fundamentals (Coming from C++)

### How Java Differs from C++

| Feature | C++ | Java |
|---------|-----|------|
| Memory Management | Manual (`new`/`delete`) | Automatic (Garbage Collector) |
| Pointers | Direct pointer manipulation | No pointers (only references) |
| Multiple Inheritance | Supported via classes | Only via interfaces |
| Header Files | `.h` files needed | No header files |
| Compilation | Compiles to machine code | Compiles to bytecode → JVM runs it |
| Platform | Platform-specific binary | "Write once, run anywhere" (JVM) |
| Strings | `char[]` or `std::string` | `String` is an object (immutable) |

### Key Java Concepts Used in This Project

**1. Packages** — Like namespaces in C++. `com.pm.patientservice.controller` means the file lives in `com/pm/patientservice/controller/` folder.

**2. Access Modifiers:**
- `public` — accessible from anywhere
- `private` — accessible only within the class
- `protected` — accessible within the package + subclasses
- (default) — accessible within the package only

**3. `final` keyword** — Like `const` in C++. Used in `LoginResponseDTO`:
```java
private final String token;  // once assigned, cannot change
```

**4. `static` keyword** — Belongs to the class, not to an instance. Used in `PatientMapper`:
```java
public static PatientResponseDTO toDto(Patient patient) { ... }
// Call: PatientMapper.toDto(patient) — no need to create an object
```

### Interview Q&A

**Q: What is JVM, JRE, and JDK?**
> **A:** **JDK** (Java Development Kit) = tools to write + compile Java. **JRE** (Java Runtime Environment) = what you need to run Java programs. **JVM** (Java Virtual Machine) = the engine inside JRE that actually executes the bytecode. Think of it as: JDK ⊃ JRE ⊃ JVM.
>
> ```
> C++ compilation:  main.cpp  → compiler → main.exe (machine code, OS-specific)
> Java compilation: Main.java → javac   → Main.class (bytecode, platform-independent)
>                                          → JVM runs Main.class on ANY OS
> ```
> In your project, `mvn clean package` compiles all `.java` files to `.class` files inside `target/classes/`, then packages them into `patient-service-0.0.1-SNAPSHOT.jar`. The Dockerfile runs `java -jar app.jar` which starts the JVM.

**Q: Why doesn't Java have pointers?**
> **A:** Java uses references instead of pointers for safety. No pointer arithmetic means no accidental memory corruption, buffer overflows, or dangling pointers. The Garbage Collector handles memory deallocation automatically.
>
> ```cpp
> // C++ — dangerous pointer manipulation:
> Patient* p = new Patient();
> p = p + 1;  // pointer arithmetic — might crash or corrupt memory
> delete p;    // must manually free
> ```
> ```java
> // Java — safe references only:
> Patient patient = new Patient();  // 'patient' is a REFERENCE, not a pointer
> // patient = patient + 1;  ← COMPILE ERROR! No pointer arithmetic!
> // No delete needed — Garbage Collector frees it when no references exist
> patient = null;  // GC will now clean up the Patient object
> ```

**Q: What is Garbage Collection?**
> **A:** In C++, you manually `delete` objects. In Java, the Garbage Collector (GC) automatically frees memory of objects that are no longer referenced. You never call `free()` or `delete`. The GC runs in the background and identifies unreachable objects using algorithms like Mark-and-Sweep.
>
> ```java
> // In PatientService.getPatients():
> public List<PatientResponseDTO> getPatients() {
>     List<Patient> patients = patientRepository.findAll(); // Object created on heap
>     return patients.stream().map(PatientMapper::toDto).toList();
>     // After this method returns, 'patients' (the entity list) has no references
>     // The GC will automatically free it — you NEVER write: delete patients;
> }
> ```
> In C++, forgetting to `delete` causes memory leaks. In Java, it's impossible — the GC handles it.

---

## 2. Object-Oriented Programming (OOP) Concepts

Your project uses ALL four pillars of OOP. Let's see exactly where.

### Pillar 1: Encapsulation

**What:** Wrapping data (variables) and methods together in a class, and hiding internal details using access modifiers.

**Where in your project:**
```java
// Patient.java — fields are private, accessed only via getters/setters
public class Patient {
    @NotNull
    private String name;       // PRIVATE — can't access directly from outside

    public String getName() {  // PUBLIC getter — controlled access
        return name;
    }
    public void setName(String name) {  // PUBLIC setter — controlled modification
        this.name = name;
    }
}
```

**C++ analogy:** Same as making members `private` and providing `public` getter/setter methods in a C++ class.

### Pillar 2: Inheritance

**What:** A class acquires properties/methods of another class. `extends` for classes, `implements` for interfaces.

**Where in your project:**
```java
// EmailAlreadyExistsException extends RuntimeException
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);  // calls parent constructor
    }
}

// BillingGrpcService extends BillingServiceImplBase (generated class)
public class BillingGrpcService extends BillingServiceImplBase { ... }

// PatientRepository extends JpaRepository (interface inheritance)
public interface PatientRepository extends JpaRepository<Patient, UUID> { ... }
```

**C++ analogy:** `class Dog : public Animal { }` — same concept with `extends` keyword.

### Pillar 3: Polymorphism

**What:** One interface, many implementations. Two types:
- **Compile-time (Overloading):** Same method name, different parameters
- **Runtime (Overriding):** Subclass provides its own implementation of parent method

**Where in your project:**
```java
// RUNTIME polymorphism — @Override
@Override
public void createBillingAccount(BillingRequest billingRequest, 
    StreamObserver<BillingResponse> responseObserver) {
    // BillingGrpcService overrides the method from BillingServiceImplBase
}
```

```java
// PasswordEncoder is an INTERFACE — BCryptPasswordEncoder is ONE implementation
// Spring can inject any PasswordEncoder implementation
PasswordEncoder passwordEncoder;  // could be BCrypt, Argon2, PBKDF2, etc.
```

### Pillar 4: Abstraction

**What:** Hiding complex implementation details and showing only the necessary features.

**Where in your project:**
```java
// PatientRepository — you NEVER write SQL!
// JpaRepository ABSTRACTS away all database operations
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);  // Spring auto-generates the SQL query!
}
```

```java
// KafkaTemplate ABSTRACTS away the complexity of connecting to Kafka, 
// serializing data, handling retries, etc.
kafkaTemplate.send("patient", event.toByteArray());  // One line to send a message!
```

### Interview Q&A

**Q: What are the 4 pillars of OOP? Give examples from your project.**
> **A:** (1) **Encapsulation** — Patient entity has private fields with public getters/setters. (2) **Inheritance** — `EmailAlreadyExistsException extends RuntimeException`, `PatientRepository extends JpaRepository`. (3) **Polymorphism** — `BillingGrpcService` overrides `createBillingAccount()` from the generated base class; `PasswordEncoder` interface has multiple implementations. (4) **Abstraction** — `JpaRepository` hides all SQL complexity; `KafkaTemplate` hides messaging complexity.

**Q: What is the difference between an abstract class and an interface?**
> **A:** An **abstract class** can have both abstract (unimplemented) and concrete (implemented) methods, constructors, and instance variables. An **interface** (before Java 8) only had method signatures. Since Java 8, interfaces can have `default` methods. A class can implement **multiple interfaces** but extend only **one class**. In this project, `PatientRepository` is an interface extending `JpaRepository` interface — we get multiple inheritance of behavior through interfaces.
>
> ```java
> // INTERFACE — from your project (PatientRepository.java):
> public interface PatientRepository extends JpaRepository<Patient, UUID> {
>     boolean existsByEmail(String email);  // abstract method — Spring generates implementation
> }
> // A class can implement MULTIPLE interfaces:
> // public class MyClass implements Serializable, Comparable<MyClass> { }
>
> // ABSTRACT CLASS — example (not in your project, but for comparison):
> public abstract class BaseService {
>     protected final Logger log = LoggerFactory.getLogger(getClass()); // concrete field
>     public void logAction(String msg) { log.info(msg); }             // concrete method
>     public abstract void execute();                                    // abstract method
> }
> // A class can extend only ONE abstract class:
> // public class PatientService extends BaseService { ... }
> ```

**Q: What is `this` and `super` in Java?**
> **A:** `this` refers to the current object instance (like `this->` in C++). `super` refers to the parent class. `super(message)` in `EmailAlreadyExistsException` calls the `RuntimeException` constructor. `this.patientRepository = patientRepository` in the service constructor assigns the parameter to the instance field.
>
> ```java
> // 'this' — from PatientService.java constructor:
> public PatientService(PatientRepository patientRepository, ...) {
>     this.patientRepository = patientRepository;
>     //  ↑ instance field         ↑ constructor parameter (same name!)
>     // 'this.' disambiguates: "assign the parameter to MY field"
> }
>
> // 'super' — from EmailAlreadyExistsException.java:
> public class EmailAlreadyExistsException extends RuntimeException {
>     public EmailAlreadyExistsException(String message) {
>         super(message);  // calls RuntimeException(String message) constructor
>         // The parent class stores it so getMessage() works later
>     }
> }
> ```

**Q: What is method overloading vs overriding?**
> **A:** **Overloading** = same method name, different parameter types/count (compile-time). **Overriding** = subclass redefines a parent's method with same signature (runtime). In this project, `BillingGrpcService.createBillingAccount()` overrides the parent's method.
>
> ```java
> // OVERRIDING — from BillingGrpcService.java:
> public class BillingGrpcService extends BillingServiceImplBase {
>     @Override  // ← tells compiler: "I'm replacing the parent's version"
>     public void createBillingAccount(BillingRequest billingRequest,
>             StreamObserver<BillingResponse> responseObserver) {
>         // Parent class (BillingServiceImplBase) has a default implementation
>         // that throws UNIMPLEMENTED error. We OVERRIDE it with real logic.
>     }
> }
>
> // OVERLOADING — example (not in your project but good to know):
> public class MathUtils {
>     public int add(int a, int b) { return a + b; }        // version 1
>     public double add(double a, double b) { return a + b; } // version 2 — SAME name, different params
>     public int add(int a, int b, int c) { return a+b+c; }   // version 3 — different param count
> }
> ```

---

## 3. How the Internet & HTTP Work

### What Happens When You Hit an API?

```
Your Browser/Postman            Internet              Your Server
     |                             |                      |
     |------ HTTP Request -------->|--------------------->|
     |    (GET /patients)          |                      | Controller receives
     |                             |                      | Service processes
     |                             |                      | Repository queries DB
     |<----- HTTP Response --------|<---------------------|
     |   (200 OK + JSON data)      |                      |
```

### HTTP Methods (CRUD Operations)

| HTTP Method | CRUD Operation | Your Endpoint | What It Does |
|-------------|---------------|---------------|-------------|
| `GET` | Read | `GET /patients` | Get all patients |
| `POST` | Create | `POST /patients` | Create a new patient |
| `PUT` | Update | `PUT /patients/{id}` | Update entire patient |
| `DELETE` | Delete | `DELETE /patients/{id}` | Delete a patient |

### HTTP Status Codes Used in Your Project

| Code | Meaning | Where Used |
|------|---------|-----------|
| `200 OK` | Success | `ResponseEntity.ok()` — getting/creating patients |
| `204 No Content` | Success, no body | `ResponseEntity.noContent()` — after deleting |
| `400 Bad Request` | Client error | Validation failures, duplicate email |
| `401 Unauthorized` | Not authenticated | Invalid/missing JWT token |
| `503 Service Unavailable` | Backend down | gRPC billing service unreachable |

### What is JSON?

JSON (JavaScript Object Notation) is the data format your API sends/receives:
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "address": "123 Main St",
    "dateOfBirth": "1985-06-15"
}
```
Think of it like a C++ `map<string, string>` but as text.

### Interview Q&A

**Q: What is a REST API?**
> **A:** REST (Representational State Transfer) is an architectural style for designing APIs over HTTP. Key principles: (1) **Stateless** — each request contains all needed info, server doesn't store client state. (2) **Resource-based** — URLs represent resources (`/patients`, `/patients/123`). (3) **HTTP methods** define actions (GET=read, POST=create, PUT=update, DELETE=delete). (4) **Uniform interface** — consistent URL structure.
>
> ```java
> // All 4 RESTful endpoints from PatientController.java:
> @RestController
> @RequestMapping("/patients")    // Base resource URL
> public class PatientController {
>     @GetMapping                   // GET    /patients        → Read all
>     @PostMapping                  // POST   /patients        → Create
>     @PutMapping("/{id}")          // PUT    /patients/{uuid} → Update
>     @DeleteMapping("/{id}")       // DELETE /patients/{uuid} → Delete
> }
> // Each URL represents a RESOURCE (patient), HTTP method defines the ACTION
> ```

**Q: What is the difference between PUT and PATCH?**
> **A:** `PUT` replaces the entire resource. `PATCH` updates only the specified fields. My project uses `PUT` which means you must send ALL fields even if only one changed.
>
> ```java
> // PUT (your project) — PatientService.updatePatient():
> patient.setName(patientRequestDTO.getName());       // MUST send name
> patient.setEmail(patientRequestDTO.getEmail());     // MUST send email
> patient.setAddress(patientRequestDTO.getAddress()); // MUST send address
> patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth())); // MUST send DOB
> // ALL fields are replaced — if you forget 'address', it becomes null!
>
> // PATCH (hypothetical — not in your project):
> if (patientRequestDTO.getName() != null) patient.setName(patientRequestDTO.getName());
> if (patientRequestDTO.getEmail() != null) patient.setEmail(patientRequestDTO.getEmail());
> // Only updates fields that were sent — others stay unchanged
> ```

**Q: What is the difference between `@PathVariable` and `@RequestParam`?**
> **A:** `@PathVariable` extracts from the URL path: `/patients/{id}` → `@PathVariable UUID id`. `@RequestParam` extracts from query parameters: `/patients?name=John` → `@RequestParam String name`. My project uses `@PathVariable` for the patient ID in update/delete operations.
>
> ```java
> // @PathVariable — from PatientController.java:
> @PutMapping("/{id}")   // URL: /patients/123e4567-e89b-12d3-a456-426614174000
> public ResponseEntity<PatientResponseDTO> updatePatient(
>     @PathVariable UUID id,  // id = UUID("123e4567-e89b-12d3-a456-426614174000")
>     @RequestBody PatientRequestDTO dto) { ... }
>
> // @RequestParam (not in your project, but common):
> @GetMapping  // URL: /patients?page=0&size=10&sort=name
> public ResponseEntity<Page<Patient>> getPatients(
>     @RequestParam(defaultValue = "0") int page,    // page = 0
>     @RequestParam(defaultValue = "10") int size) {  // size = 10
>     ...
> }
> ```

---

## 4. What is Spring Boot & Why Use It

### The Problem Before Spring Boot

Without Spring Boot, to build a web app in Java you'd need to:
1. Manually configure a web server (Tomcat)
2. Write 50+ lines of XML configuration
3. Manage all library versions yourself
4. Set up database connections manually

### What Spring Boot Does

Spring Boot is an **opinionated framework** that gives you everything pre-configured:

```java
@SpringBootApplication  // THIS ONE LINE sets up everything!
public class PatientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApplication.class, args);
        // Boom! Web server running, database connected, APIs ready
    }
}
```

`@SpringBootApplication` is actually 3 annotations combined:
- `@SpringBootConfiguration` — marks this as a configuration class
- `@EnableAutoConfiguration` — automatically configures based on dependencies in pom.xml
- `@ComponentScan` — scans for classes annotated with `@Component`, `@Service`, `@Controller`, etc.

### Spring Boot Starters Used in Your Project

| Starter | Purpose | Service |
|---------|---------|---------|
| `spring-boot-starter-web` | REST APIs, embedded Tomcat | patient, auth, analytics |
| `spring-boot-starter-data-jpa` | Database ORM (JPA/Hibernate) | patient, auth |
| `spring-boot-starter-validation` | Bean validation (`@NotNull` etc.) | patient |
| `spring-boot-starter-security` | Authentication/Authorization | auth |
| `spring-cloud-starter-gateway` | API Gateway routing | api-gateway |

### Interview Q&A

**Q: What is Spring Boot? How is it different from Spring?**
> **A:** **Spring** is a comprehensive framework for Java enterprise applications with features like DI, AOP, MVC, etc. but requires a lot of configuration. **Spring Boot** is built ON TOP of Spring — it provides auto-configuration, embedded servers, and starter dependencies to get you running with zero configuration. It follows "convention over configuration."

**Q: What is auto-configuration in Spring Boot?**
> **A:** Spring Boot examines your classpath (what libraries are in your pom.xml) and automatically configures beans. For example, if it sees `spring-boot-starter-data-jpa` + `h2` in dependencies, it automatically configures an in-memory H2 database, a DataSource, and an EntityManager. You don't write any configuration code.
>
> ```xml
> <!-- In patient-service pom.xml — just adding these dependencies triggers auto-config: -->
> <dependency>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-data-jpa</artifactId>
>     <!-- Auto-config: creates EntityManagerFactory, TransactionManager -->
> </dependency>
> <dependency>
>     <groupId>com.h2database</groupId>
>     <artifactId>h2</artifactId>
>     <!-- Auto-config: creates H2 in-memory DataSource, connection pool -->
> </dependency>
> <dependency>
>     <groupId>org.springframework.kafka</groupId>
>     <artifactId>spring-kafka</artifactId>
>     <!-- Auto-config: creates KafkaTemplate bean → injected into KafkaProducer -->
> </dependency>
> ```
> ```java
> // You NEVER wrote: new KafkaTemplate<>() or new DataSource()
> // Spring Boot auto-creates them! Your KafkaProducer just declares it needs one:
> public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
>     this.kafkaTemplate = kafkaTemplate;  // Auto-configured bean injected here
> }
> ```

**Q: What does `@SpringBootApplication` do?**
> **A:** It's a meta-annotation combining `@SpringBootConfiguration`, `@EnableAutoConfiguration`, and `@ComponentScan`. It marks the main class, enables auto-configuration, and scans the package and sub-packages for Spring-managed beans.
>
> ```java
> // From PatientServiceApplication.java:
> @SpringBootApplication  // = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
> public class PatientServiceApplication {
>     public static void main(String[] args) {
>         SpringApplication.run(PatientServiceApplication.class, args);
>     }
> }
> // @ComponentScan scans com.pm.patientservice and ALL sub-packages:
> //   com.pm.patientservice.controller  → finds PatientController (@RestController)
> //   com.pm.patientservice.service      → finds PatientService (@Service)
> //   com.pm.patientservice.grpc         → finds BillingServiceGrpcClient (@Service)
> //   com.pm.patientservice.kafka        → finds KafkaProducer (@Service)
> //   com.pm.patientservice.exception    → finds GlobalExceptionHandler (@ControllerAdvice)
> //   com.pm.patientservice.repsitory    → finds PatientRepository (extends JpaRepository)
> ```

---

## 5. Project Architecture — Microservices

### Your Project: 5 Independent Services

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT (Browser/Postman)                  │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                    ┌──────▼──────┐
                    │ API GATEWAY │  Port 4004
                    │  (Routing)  │  Routes requests to correct service
                    └──┬──────┬───┘
                       │      │
          ┌────────────▼┐   ┌▼────────────────┐
          │AUTH SERVICE  │   │ PATIENT SERVICE  │  Port 4000
          │  Port 4005   │   │ (Main CRUD)      │
          │  JWT tokens  │   │                  │
          └──────────────┘   └──┬──────────┬───┘
                                │  gRPC    │ Kafka
                         ┌──────▼───┐  ┌───▼──────────┐
                         │ BILLING   │  │ ANALYTICS    │
                         │ SERVICE   │  │ SERVICE      │
                         │ Port 4001 │  │ Port 4002    │
                         │ gRPC:9001 │  │ Kafka Consumer│
                         └───────────┘  └──────────────┘
```

### Monolith vs Microservices

**Monolithic Architecture:**
```
One Giant Application
├── Patient Code
├── Auth Code
├── Billing Code
└── Analytics Code
→ If billing breaks, EVERYTHING breaks
→ To update billing, you redeploy EVERYTHING
```

**Microservices Architecture (Your Project):**
```
5 Separate Applications
├── Patient Service    (independent deploy)
├── Auth Service       (independent deploy)
├── Billing Service    (independent deploy)
├── Analytics Service  (independent deploy)
└── API Gateway        (independent deploy)
→ If billing breaks, patients still work!
→ Each can be scaled independently
```

### How Your Services Communicate

| From → To | Method | Why |
|-----------|--------|-----|
| Client → API Gateway | HTTP/REST | Standard web protocol |
| API Gateway → Auth/Patient | HTTP/REST | Route forwarding |
| Patient → Billing | **gRPC** | Synchronous, fast, type-safe inter-service call |
| Patient → Analytics | **Kafka** | Asynchronous event, fire-and-forget |

### Interview Q&A

**Q: What are microservices? Why did you use them?**
> **A:** Microservices is an architectural style where an application is a collection of small, independently deployable services, each running its own process and communicating over a network. I used microservices because: (1) **Independent deployment** — I can update billing without touching patient logic. (2) **Technology flexibility** — each service could use a different database or even language. (3) **Scalability** — if patient service gets heavy traffic, I can run 10 instances of it without scaling billing. (4) **Fault isolation** — if analytics service crashes, patients can still be created.
>
> ```java
> // Each service is a SEPARATE Spring Boot application with its own main():
>
> // patient-service (port 4000):
> @SpringBootApplication
> public class PatientServiceApplication {
>     public static void main(String[] args) {
>         SpringApplication.run(PatientServiceApplication.class, args);  // Starts on port 4000
>     }
> }
>
> // auth-service (port 4005):
> @SpringBootApplication
> public class AuthServiceApplication {
>     public static void main(String[] args) {
>         SpringApplication.run(AuthServiceApplication.class, args);  // Starts on port 4005
>     }
> }
>
> // Each has its own pom.xml, own database, own Docker container.
> // They communicate via REST, gRPC, and Kafka — NOT by sharing code.
> ```

**Q: What are the disadvantages of microservices?**
> **A:** (1) **Network complexity** — services must communicate over network (latency, failures). (2) **Data consistency** — no single database transaction across services. (3) **Operational overhead** — need to deploy, monitor, and manage multiple services. (4) **Debugging difficulty** — a request flows through multiple services. (5) **Distributed tracing** needed to track requests across services.
>
> ```java
> // Example of network complexity in PatientService.createPatient():
> Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
> // ↑ Local DB call — fast, reliable
>
> billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), ...);
> // ↑ NETWORK call to billing-service — can fail! Can be slow! Can timeout!
>
> kafkaProducer.sendEvent(newPatient);
> // ↑ NETWORK call to Kafka broker — can fail! Broker might be down!
>
> // In a monolith, ALL of this would be local method calls — no network failures.
> // That's the trade-off of microservices.
> ```

**Q: When would you choose monolith over microservices?**
> **A:** For small teams, early-stage products, or simple applications. Microservices add complexity — if you don't need independent scaling or deployment, a well-structured monolith is simpler and faster. Start monolith, extract to microservices when needed.

---

## 6. The Layered Architecture Pattern

Each service follows a **3-layer architecture**:

```
┌─────────────────────────────────────────┐
│         CONTROLLER LAYER                │  ← Receives HTTP request
│   PatientController.java                │  ← Validates input
│   (@RestController)                     │  ← Returns HTTP response
├─────────────────────────────────────────┤
│         SERVICE LAYER                    │  ← Business logic
│   PatientService.java                    │  ← Validation rules
│   (@Service)                             │  ← Orchestrates operations
├─────────────────────────────────────────┤
│         REPOSITORY LAYER                 │  ← Database access
│   PatientRepository.java                 │  ← CRUD operations
│   (@Repository / extends JpaRepository)  │  ← SQL query generation
├─────────────────────────────────────────┤
│         DATABASE                         │  ← H2 / PostgreSQL
└─────────────────────────────────────────┘
```

### Why Separate Layers?

1. **Separation of Concerns** — Each layer has ONE job
2. **Testability** — You can test service logic without a database
3. **Maintainability** — Change database without touching controllers
4. **Reusability** — Multiple controllers can use the same service

### Flow of a Request: `POST /patients`

```
1. HTTP Request arrives → PatientController.createPatient()
2. Controller validates DTO → @Validated annotation
3. Controller calls → PatientService.createPatient()
4. Service checks business rules → email not duplicate?
5. Service calls → PatientMapper.toModel() → converts DTO to Entity
6. Service calls → PatientRepository.save() → saves to DB
7. Service calls → BillingServiceGrpcClient → creates billing account
8. Service calls → KafkaProducer.sendEvent() → publishes event
9. Service returns → PatientMapper.toDto() → converts Entity to DTO
10. Controller wraps in ResponseEntity → returns HTTP 200 + JSON
```

### Interview Q&A

**Q: What is the layered architecture pattern?**
> **A:** It separates the application into horizontal layers (Controller → Service → Repository → Database), each with a specific responsibility. The controller handles HTTP concerns, the service contains business logic, and the repository handles database access. Each layer only communicates with the layer directly below it.
>
> ```java
> // LAYER 1 — Controller (HTTP concerns):
> @RestController
> public class PatientController {
>     private final PatientService patientService;  // calls Service layer ↓
>     @GetMapping
>     public ResponseEntity<List<PatientResponseDTO>> getPatients() {
>         return ResponseEntity.ok().body(patientService.getPatients());
>     }
> }
>
> // LAYER 2 — Service (Business logic):
> @Service
> public class PatientService {
>     private final PatientRepository patientRepository;  // calls Repository layer ↓
>     public List<PatientResponseDTO> getPatients() {
>         List<Patient> patients = patientRepository.findAll();
>         return patients.stream().map(PatientMapper::toDto).toList();
>     }
> }
>
> // LAYER 3 — Repository (Database access):
> public interface PatientRepository extends JpaRepository<Patient, UUID> { }
> // ↓ Talks to H2/PostgreSQL database
> ```

**Q: Why not put business logic in the controller?**
> **A:** Violates **Single Responsibility Principle**. If the same logic is needed from another controller (say a SOAP endpoint or a scheduled job), you'd have to duplicate it. By keeping it in the service layer, any entry point can reuse the same business logic.
>
> ```java
> // ❌ BAD — business logic in controller (what NOT to do):
> @PostMapping
> public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO dto) {
>     if (patientRepository.existsByEmail(dto.getEmail())) {  // business logic here!
>         throw new EmailAlreadyExistsException("...");
>     }
>     Patient patient = patientRepository.save(PatientMapper.toModel(dto));
>     billingClient.createBillingAccount(...);  // business logic here!
>     kafkaProducer.sendEvent(patient);          // business logic here!
>     return ResponseEntity.ok(PatientMapper.toDto(patient));
> }
>
> // ✅ GOOD — your actual project, logic in service:
> @PostMapping
> public ResponseEntity<PatientResponseDTO> createPatient(@Validated(...) @RequestBody PatientRequestDTO dto) {
>     PatientResponseDTO result = patientService.createPatient(dto);  // ONE line — delegates to service
>     return ResponseEntity.ok().body(result);
> }
> // Now a scheduled job, another controller, or a message listener can ALL call:
> //   patientService.createPatient(dto)  — same logic, no duplication!
> ```

---

## 7. Dependency Injection & Inversion of Control (IoC)

### The Problem (Without DI)

In C++, you'd do:
```cpp
class PatientController {
    PatientService* service;
public:
    PatientController() {
        service = new PatientService();  // Controller CREATES its own dependency
        // But what if PatientService needs a Repository? 
        // And Repository needs a DataSource?
        // You'd have to create ALL of them manually!
    }
};
```

### The Solution (Dependency Injection)

Spring creates objects and **injects** them where needed:
```java
@RestController
public class PatientController {
    private final PatientService patientService;

    // Spring sees this constructor and AUTOMATICALLY provides a PatientService object!
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
}
```

### How It Works — The Spring IoC Container

```
Spring starts up →
  Scans all classes →
    Finds @Component, @Service, @Repository, @Controller, @RestController →
      Creates objects (called "beans") →
        Sees constructor parameters →
          Injects matching beans automatically

patientRepository bean  ──────┐
billingServiceGrpcClient bean ─┼──→ PatientService bean ──→ PatientController bean
kafkaProducer bean ────────────┘
```

### Constructor Injection (Used in Your Project)

```java
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    // CONSTRUCTOR INJECTION — Spring passes all 3 dependencies
    public PatientService(PatientRepository patientRepository, 
                          BillingServiceGrpcClient billingServiceGrpcClient, 
                          KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }
}
```

### Why Constructor Injection Over Field Injection?

```java
// ❌ Field Injection (not used in your project, but you might be asked)
@Service
public class PatientService {
    @Autowired  // Spring injects directly into the field
    private PatientRepository patientRepository;
}

// ✅ Constructor Injection (used in your project)
@Service
public class PatientService {
    private final PatientRepository patientRepository;  // Can be final!
    
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
}
```

**Why constructor is better:**
1. Fields can be `final` → immutable, thread-safe
2. Impossible to create an object without dependencies
3. Easy to test — just pass mock objects in constructor
4. No reflection magic needed

### Interview Q&A

**Q: What is Dependency Injection?**
> **A:** DI is a design pattern where an object receives its dependencies from an external source (the Spring container) rather than creating them itself. In my project, `PatientController` doesn't create `PatientService` — Spring creates it and passes it via the constructor. This makes code loosely coupled, testable, and follows the Dependency Inversion Principle.
>
> ```java
> // WITHOUT DI (tight coupling):
> public class PatientController {
>     private PatientService service = new PatientService(
>         new PatientRepository(),          // must create all dependencies manually!
>         new BillingServiceGrpcClient(),   // and their dependencies too!
>         new KafkaProducer()               // nightmare to test — can't use mocks!
>     );
> }
>
> // WITH DI (your project — loose coupling):
> @RestController
> public class PatientController {
>     private final PatientService patientService;
>     public PatientController(PatientService patientService) {
>         this.patientService = patientService;  // Spring injects it automatically!
>     }
>     // For testing: new PatientController(mockService) — easy!
> }
> ```

**Q: What is IoC (Inversion of Control)?**
> **A:** IoC means the framework controls the flow and object creation, not your code. Normally, YOU create objects. With IoC, the Spring container creates objects and manages their lifecycle. DI is a specific form of IoC.
>
> ```java
> // Normal control: YOU create everything:
> PatientRepository repo = new PatientRepository();
> BillingServiceGrpcClient billing = new BillingServiceGrpcClient("localhost", 9001);
> KafkaProducer kafka = new KafkaProducer(new KafkaTemplate<>());
> PatientService service = new PatientService(repo, billing, kafka);
> PatientController controller = new PatientController(service);
>
> // Inversion of Control: SPRING creates everything:
> // You just annotate classes:
> @Service public class PatientService { ... }      // Spring creates this
> @RestController public class PatientController { ... } // Spring creates this
> // Spring figures out the dependency graph and creates objects in the right order!
> ```

**Q: What is a Spring Bean?**
> **A:** A bean is an object that the Spring IoC container manages. Any class annotated with `@Component`, `@Service`, `@Repository`, `@Controller`, or defined in `@Configuration` with `@Bean` becomes a Spring-managed bean. By default, beans are **singletons** — only one instance exists in the entire application.
>
> ```java
> // These are ALL Spring Beans in your patient-service:
> @RestController   PatientController       // Only 1 instance exists
> @Service          PatientService          // Only 1 instance exists
> @Service          BillingServiceGrpcClient // Only 1 instance exists
> @Service          KafkaProducer           // Only 1 instance exists
> @ControllerAdvice GlobalExceptionHandler  // Only 1 instance exists
> // PatientRepository                      // Spring creates a proxy bean at runtime
>
> // BCryptPasswordEncoder is also a bean, but created via @Bean method:
> @Configuration
> public class SecurityConfig {
>     @Bean  // This method's return value becomes a managed bean
>     public PasswordEncoder passwordEncoder() {
>         return new BCryptPasswordEncoder();  // Only 1 instance, shared everywhere
>     }
> }
> ```

**Q: What is the difference between `@Component`, `@Service`, `@Repository`, and `@Controller`?**
> **A:** Functionally, they all register a class as a Spring bean. The difference is **semantic** (helps readability): `@Controller`/`@RestController` = web layer, `@Service` = business logic layer, `@Repository` = data access layer (also translates DB exceptions to Spring exceptions). `@Component` is the generic one — others are specializations.
>
> ```java
> // All from your project — same effect (creates a bean), different meaning:
> @RestController   // WEB LAYER — handles HTTP requests
> public class PatientController { ... }
>
> @Service          // BUSINESS LOGIC LAYER — contains rules
> public class PatientService { ... }
>
> @Service          // Also business logic — external service communication
> public class BillingServiceGrpcClient { ... }
>
> // @Repository    // DATA ACCESS LAYER — database operations
> public interface PatientRepository extends JpaRepository<Patient, UUID> { ... }
> // PatientRepository doesn't need @Repository explicitly because
> // extending JpaRepository triggers auto-detection by Spring Data!
>
> @ControllerAdvice // SPECIAL — built on @Component, applies to all controllers
> public class GlobalExceptionHandler { ... }
> ```

---

## 8. Spring Annotations — The Full List Used

### Class-Level Annotations

| Annotation | Used On | Purpose | Example |
|-----------|---------|---------|---------|
| `@SpringBootApplication` | Main class | Enables auto-config + component scanning | `PatientServiceApplication` |
| `@RestController` | Controller class | Marks as REST API controller (= `@Controller` + `@ResponseBody`) | `PatientController` |
| `@Service` | Service class | Marks as business logic bean | `PatientService`, `KafkaProducer` |
| `@Repository` | Repository interface | Marks as data access bean | `PatientRepository` |
| `@Component` | Any class | Generic Spring-managed bean | `JwUtil` uses `@Component` |
| `@Configuration` | Config class | Defines bean configurations | `SecurityConfig` |
| `@ControllerAdvice` | Exception handler | Global exception handling for all controllers | `GlobalExceptionHandler` |
| `@GrpcService` | gRPC server class | Registers as a gRPC service | `BillingGrpcService` |
| `@Entity` | Model class | Maps class to database table | `Patient`, `User` |
| `@Table` | Model class | Specifies table name | `@Table(name = "users")` |

### Method-Level Annotations

| Annotation | Purpose | Example |
|-----------|---------|---------|
| `@GetMapping` | Handle GET requests | `getPatients()` |
| `@PostMapping` | Handle POST requests | `createPatient()` |
| `@PutMapping("/{id}")` | Handle PUT requests | `updatePatient()` |
| `@DeleteMapping("/{id}")` | Handle DELETE requests | `deletePatient()` |
| `@RequestMapping("/patients")` | Base URL prefix for all endpoints | On `PatientController` class |
| `@ExceptionHandler(...)` | Handle specific exception type | In `GlobalExceptionHandler` |
| `@Bean` | Register method return value as a bean | `passwordEncoder()`, `securityFilterChain()` |
| `@KafkaListener` | Listen for Kafka messages | `consumeEvent()` in analytics |
| `@Override` | Overrides parent method | `createBillingAccount()` in billing |
| `@Operation` | Swagger API documentation | `login()` and `validateToken()` in auth |

### Field/Parameter Annotations

| Annotation | Purpose | Example |
|-----------|---------|---------|
| `@Id` | Primary key | `UUID id` in `Patient` |
| `@GeneratedValue` | Auto-generate value | `GenerationType.AUTO` for UUID |
| `@Column` | Column configuration | `@Column(unique = true)` |
| `@NotNull` | Must not be null | Fields in `Patient` entity |
| `@NotBlank` | Must not be null/empty/whitespace | Fields in DTOs |
| `@Email` | Must be valid email format | `email` fields |
| `@Size` | String length constraint | `@Size(max=100)` on name |
| `@Value("${...}")` | Inject property from application.properties | gRPC host/port in `BillingServiceGrpcClient` |
| `@PathVariable` | Extract from URL path | `UUID id` in update/delete |
| `@RequestBody` | Parse JSON body into object | `PatientRequestDTO` in create/update |
| `@RequestHeader` | Extract HTTP header | `Authorization` header in auth |
| `@Validated` | Trigger validation on parameter | DTOs in controller methods |

---

## 9. REST API — Controllers & HTTP Methods

### PatientController Breakdown

```java
@RestController          // This class handles HTTP requests and returns JSON
@RequestMapping("/patients")  // All endpoints start with /patients

public class PatientController {
    private final PatientService patientService;

    // Constructor Injection
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET http://localhost:4000/patients
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> list = patientService.getPatients();
        return ResponseEntity.ok().body(list);
        // Returns: 200 OK + JSON array of patients
    }

    // POST http://localhost:4000/patients  (with JSON body)
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
        @Validated({Default.class, CreatePatientValidationGroup.class}) 
        @RequestBody PatientRequestDTO patientRequestDTO) {
        // @RequestBody → Spring automatically converts JSON to Java object
        // @Validated → Runs validation annotations on the DTO
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    // PUT http://localhost:4000/patients/123e4567-e89b-12d3-a456-426614174000
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
        @PathVariable UUID id,  // Extracts UUID from URL
        @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    // DELETE http://localhost:4000/patients/123e4567-e89b-12d3-a456-426614174000
    @DeleteMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
```

### ResponseEntity — What Is It?

`ResponseEntity<T>` wraps your response with HTTP status code + headers + body:
```java
ResponseEntity.ok().body(data);          // 200 OK + data
ResponseEntity.noContent().build();       // 204 No Content, no body
ResponseEntity.badRequest().body(errors); // 400 Bad Request + errors
ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errors); // 503
```

### Interview Q&A

**Q: What is `@RestController` vs `@Controller`?**
> **A:** `@Controller` returns view names (for HTML pages). `@RestController` = `@Controller` + `@ResponseBody`, meaning every method's return value is automatically serialized to JSON and written to the HTTP response body. My project uses `@RestController` because it's a REST API, not a web page server.
>
> ```java
> // @Controller (for web pages — NOT used in your project):
> @Controller
> public class WebController {
>     @GetMapping("/home")
>     public String home() {
>         return "home";  // Returns the NAME of an HTML template (home.html)
>     }
> }
>
> // @RestController (your project — for REST APIs):
> @RestController  // = @Controller + @ResponseBody
> public class PatientController {
>     @GetMapping
>     public ResponseEntity<List<PatientResponseDTO>> getPatients() {
>         return ResponseEntity.ok().body(list);
>         // Returns Java objects → Jackson converts to JSON automatically
>         // Client receives: [{"id":"123","name":"John",...}, ...]
>     }
> }
> ```

**Q: How does Spring convert JSON to a Java object and vice versa?**
> **A:** Spring uses **Jackson** (a JSON library included by default). When a request comes with JSON body, `@RequestBody` tells Spring to use Jackson's `ObjectMapper` to deserialize JSON into the Java object. When returning, Jackson serializes the Java object back to JSON. Field names in JSON must match Java field names (or use `@JsonProperty`).
>
> ```java
> // CLIENT sends this JSON:
> // { "name": "Alice", "email": "alice@test.com", "address": "123 St" }
>
> // @RequestBody — Jackson reads JSON and calls setters:
> @PostMapping
> public ResponseEntity<PatientResponseDTO> createPatient(
>     @RequestBody PatientRequestDTO dto) {
>     // Jackson internally does:
>     //   PatientRequestDTO dto = new PatientRequestDTO();
>     //   dto.setName("Alice");           // matches JSON key "name"
>     //   dto.setEmail("alice@test.com");  // matches JSON key "email"
>     //   dto.setAddress("123 St");        // matches JSON key "address"
>     ...
>     return ResponseEntity.ok().body(responseDTO);
>     // Jackson internally does:
>     //   { "id": "uuid", "name": "Alice", "email": "alice@test.com", ... }
>     //   Reads each getter: getId() → "id", getName() → "name", etc.
> }
> ```

**Q: What is `@RequestMapping`?**
> **A:** It maps HTTP requests to handler methods/classes. On a class, it defines the base path (`@RequestMapping("/patients")` means all endpoints start with `/patients`). On methods, it can specify method type + path. `@GetMapping`, `@PostMapping`, etc. are shortcuts for `@RequestMapping(method=GET)`.
>
> ```java
> // From PatientController.java:
> @RestController
> @RequestMapping("/patients")       // All methods in this class start with /patients
> public class PatientController {
>     @GetMapping                     // GET /patients
>     // equivalent to: @RequestMapping(value = "/", method = RequestMethod.GET)
>
>     @PostMapping                    // POST /patients
>     // equivalent to: @RequestMapping(value = "/", method = RequestMethod.POST)
>
>     @PutMapping("/{id}")            // PUT /patients/{id}
>     // equivalent to: @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
>
>     @DeleteMapping("/{id}")         // DELETE /patients/{id}
>     // equivalent to: @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
> }
>
> // From AuthController.java (no @RequestMapping on class):
> @RestController
> public class AuthController {
>     @PostMapping("/login")          // POST /login
>     @GetMapping("/validate")        // GET  /validate
> }
> ```

---

## 10. Data Transfer Objects (DTOs)

### Why DTOs?

```
Problem: Your Patient entity has database-specific fields (JPA annotations, 
         registeredDate, etc.) that the API client doesn't need to see.

Solution: Create separate objects for:
  → PatientRequestDTO  — what the client SENDS (input)
  → PatientResponseDTO — what the client RECEIVES (output)
  → Patient entity     — what the DATABASE stores
```

### Visual Flow

```
Client sends JSON → PatientRequestDTO → PatientMapper → Patient (Entity) → Database
Database reads  → Patient (Entity) → PatientMapper → PatientResponseDTO → JSON to Client
```

### PatientRequestDTO — Input

```java
public class PatientRequestDTO {
    @NotBlank                                           // Can't be null or ""
    @Size(max=100, message="Name cannot exceed 100 characters")
    private String name;

    @NotBlank
    @Email(message = "Email should be valid")           // Must be valid email
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    @NotBlank(groups=CreatePatientValidationGroup.class, message="Registered Date is required")
    private String registeredDate;   // Only required when CREATING, not when UPDATING!
    
    // getters + setters
}
```

### PatientResponseDTO — Output

```java
public class PatientResponseDTO {
    private String id;           // UUID as String
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
    // NOTE: registeredDate is NOT sent back to client — deliberate choice!
    // getters + setters
}
```

### Why Not Use the Entity Directly?

| Without DTO | With DTO |
|------------|----------|
| Internal DB structure exposed | Client sees only what you want |
| Changing DB = changing API | Can change DB without affecting API |
| Security risk (passwords, internal IDs) | Filter sensitive data |
| Validation mixed with persistence | Clean separation |

### Interview Q&A

**Q: What is a DTO? Why use it?**
> **A:** A DTO (Data Transfer Object) is a simple object that carries data between layers or over the network. It decouples the API contract from the internal domain model. In my project, `PatientRequestDTO` captures client input with validation annotations, while `PatientResponseDTO` controls what data goes back. The `Patient` entity has JPA/database annotations that clients shouldn't see. If I change my database schema, the API contract stays the same.
>
> ```java
> // Entity (talks to DATABASE) — has JPA annotations:
> @Entity
> public class Patient {
>     @Id @GeneratedValue(strategy = GenerationType.AUTO)
>     private UUID id;                    // Generated by DB
>     @NotNull private String name;
>     @NotNull @Email @Column(unique = true)
>     private String email;
>     @NotNull private LocalDate dateOfBirth;   // LocalDate type
>     @NotNull private LocalDate registeredDate; // Client shouldn't change this
> }
>
> // Request DTO (comes from CLIENT) — has validation annotations:
> public class PatientRequestDTO {
>     @NotBlank @Size(max=100) private String name;    // String, not LocalDate!
>     @NotBlank @Email private String email;
>     @NotBlank private String dateOfBirth;              // String — client sends "1985-06-15"
>     @NotBlank(groups=CreatePatientValidationGroup.class)
>     private String registeredDate;                     // Only required for CREATE
> }
>
> // Response DTO (sent to CLIENT) — no annotations, clean:
> public class PatientResponseDTO {
>     private String id;            // UUID converted to String
>     private String name;
>     private String email;
>     private String address;
>     private String dateOfBirth;
>     // NO registeredDate — client doesn't need to see it!
> }
> ```

**Q: What is the difference between an Entity and a DTO?**
> **A:** An **Entity** is mapped to a database table (`@Entity`, `@Id`, `@Column`) and represents persisted data. A **DTO** is a plain data carrier for communication between layers/systems. Entities have JPA lifecycle, DTOs don't. In my project, `Patient` is an entity with JPA annotations; `PatientRequestDTO` and `PatientResponseDTO` are DTOs with validation annotations.
>
> ```java
> // ENTITY (Patient.java) — mapped to DB table:
> @Entity                          // JPA annotation → maps to 'patient' table
> public class Patient {
>     @Id @GeneratedValue           // Primary key, auto-generated
>     private UUID id;
>     @Column(unique = true)        // DB constraint
>     private String email;
>     private LocalDate dateOfBirth; // Java type → SQL DATE column
> }
>
> // DTO (PatientRequestDTO.java) — just data carrier:
> public class PatientRequestDTO {  // No @Entity! Not mapped to any table.
>     @NotBlank                      // Validation annotation, NOT DB annotation
>     private String name;
>     @Email                         // Format check, NOT DB constraint
>     private String email;
>     private String dateOfBirth;    // String! (client sends "1985-06-15")
> }
> // Entity uses LocalDate, DTO uses String → Mapper converts between them
> ```

---

## 11. Mapper Pattern

### What It Does

Converts between Entity and DTO:

```java
public class PatientMapper {
    // Entity → DTO (for sending to client)
    public static PatientResponseDTO toDto(Patient patient) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(patient.getId().toString());    // UUID → String
        dto.setName(patient.getName());
        dto.setAddress(patient.getAddress());
        dto.setEmail(patient.getEmail());
        dto.setDateOfBirth(patient.getDateOfBirth().toString()); // LocalDate → String
        return dto;
    }

    // DTO → Entity (for saving to database)
    public static Patient toModel(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));   // String → LocalDate
        patient.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate())); // String → LocalDate
        return patient;
    }
}
```

**Note:** Methods are `static` — no need to create a `PatientMapper` object. Called as `PatientMapper.toDto(patient)`.

### Interview Q&A

**Q: Why use a separate Mapper class instead of converting in the service?**
> **A:** Separation of concerns. The service handles business logic; the mapper handles data transformation. If the DTO structure changes, only the mapper changes. It's also reusable — multiple service methods can use the same mapper. In larger projects, libraries like **MapStruct** can auto-generate mappers at compile time.
>
> ```java
> // WITHOUT Mapper (conversion logic scattered in service):
> public PatientResponseDTO getPatient(UUID id) {
>     Patient p = repo.findById(id).orElseThrow(...);
>     PatientResponseDTO dto = new PatientResponseDTO();  // Conversion HERE
>     dto.setId(p.getId().toString());
>     dto.setName(p.getName());
>     // ... repeated in EVERY method that returns a DTO!
> }
>
> // WITH Mapper (clean, reusable):
> public PatientResponseDTO getPatient(UUID id) {
>     Patient p = repo.findById(id).orElseThrow(...);
>     return PatientMapper.toDto(p);  // One line! Mapper handles details.
> }
> // PatientMapper.toDto() is used in getPatients(), createPatient(), updatePatient()
> // If DTO changes → update ONE place (PatientMapper), not 3+ service methods.
> ```

---

## 12. Validation

### Bean Validation (JSR 380)

Your project uses Jakarta Bean Validation annotations on DTOs:

```java
@NotBlank    // Must not be null, not empty "", not whitespace "   "
@NotNull     // Must not be null (but can be empty "")
@Email       // Must match email format (xxx@yyy.zzz)
@Size(max=100) // String length constraint
```

### Validation Groups

```java
// This is a MARKER INTERFACE — has no methods, just a "tag"
public interface CreatePatientValidationGroup { }
```

```java
// In PatientRequestDTO:
@NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered Date is required")
private String registeredDate;
```

```java
// In Controller:
@PostMapping
public ResponseEntity<PatientResponseDTO> createPatient(
    @Validated({Default.class, CreatePatientValidationGroup.class})  // ← Validates BOTH groups
    @RequestBody PatientRequestDTO dto) { ... }

@PutMapping("/{id}")
public ResponseEntity<PatientResponseDTO> updatePatient(
    @PathVariable UUID id,
    @Validated({Default.class})  // ← Only default group, so registeredDate is NOT validated!
    @RequestBody PatientRequestDTO dto) { ... }
```

**Why?** When creating a patient, `registeredDate` is required. When updating, it's not needed (you can't change when someone registered). Validation groups let you reuse the same DTO with different validation rules.

### How Validation Errors Are Handled

When validation fails, Spring throws `MethodArgumentNotValidException`. Your `GlobalExceptionHandler` catches it:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationException(
    MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
}
// Response: {"email": "Email should be valid", "name": "must not be blank"}
```

### Interview Q&A

**Q: What is Bean Validation?**
> **A:** Bean Validation (JSR 380) is a Java specification that lets you declare constraints on fields using annotations like `@NotNull`, `@Email`, `@Size`. Spring integrates with this — when you use `@Validated` on a controller parameter, Spring automatically validates the object before your method executes. If validation fails, it throws `MethodArgumentNotValidException`.
>
> ```java
> // From PatientRequestDTO.java — annotations declare the rules:
> @NotBlank(message = "Name is required")
> private String name;
>
> @NotBlank(message = "Email is required")
> @Email(message = "Email should be valid")
> private String email;
>
> // From PatientController.java — @Validated triggers validation:
> @PostMapping
> public ResponseEntity<PatientResponseDTO> createPatient(
>     @Validated(CreatePatientValidationGroup.class) @RequestBody PatientRequestDTO dto) {
>     // If name is blank or email is invalid → Spring throws MethodArgumentNotValidException
>     // BEFORE your code even runs! GlobalExceptionHandler catches it.
> }
> ```

**Q: What is the difference between `@Valid` and `@Validated`?**
> **A:** `@Valid` is from Jakarta (standard Java). `@Validated` is from Spring. They both trigger validation, but `@Validated` supports **validation groups** — you can selectively validate certain fields based on the operation. My project uses `@Validated` with groups to require `registeredDate` only during creation, not updates.
>
> ```java
> // Validation GROUP interface (just a marker — no methods):
> public interface CreatePatientValidationGroup {}
>
> // In PatientRequestDTO — registeredDate is ONLY required for CREATE:
> @NotNull(groups = CreatePatientValidationGroup.class)  // Only validated in CREATE group
> private LocalDate registeredDate;
>
> // In PatientController:
> @PostMapping  // CREATE — registeredDate IS validated:
> public ResponseEntity<PatientResponseDTO> createPatient(
>     @Validated(CreatePatientValidationGroup.class) @RequestBody PatientRequestDTO dto) { }
>
> @PutMapping("/{id}")  // UPDATE — registeredDate is NOT validated:
> public ResponseEntity<PatientResponseDTO> updatePatient(
>     @PathVariable UUID id, @Validated @RequestBody PatientRequestDTO dto) { }
> //                         ↑ No group specified → Default group only
> ```

---

## 13. Exception Handling

### Custom Exceptions

```java
// Thrown when email already exists
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);  // Pass message to parent RuntimeException
    }
}

// Thrown when patient not found
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message, UUID id) {
        super(message);
    }
}
```

**C++ analogy:** Like `throw std::runtime_error("message")` but with custom exception classes.

### How Exceptions Work in Java

```
RuntimeException (unchecked — don't need try-catch)
├── EmailAlreadyExistsException
├── PatientNotFoundException
└── StatusRuntimeException (gRPC)

Exception (checked — MUST handle with try-catch)
├── IOException
├── SQLException
└── etc.
```

### Global Exception Handler (`@ControllerAdvice`)

Instead of try-catch in every controller method, you have ONE class that catches all exceptions:

```java
@ControllerAdvice  // Applies to ALL controllers globally
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)  // Catches this specific exception
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(
        EmailAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Message", "Email already exists!");
        return ResponseEntity.badRequest().body(errors);  // 400 Bad Request
    }

    @ExceptionHandler(StatusRuntimeException.class)  // gRPC service call failed
    public ResponseEntity<Map<String, String>> handleGrpcStatusRuntimeException(
        StatusRuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Message", "A downstream service is currently unavailable.");
        errors.put("gRPCStatus", ex.getStatus().getCode().name());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errors); // 503
    }
}
```

### Interview Q&A

**Q: What is `@ControllerAdvice`?**
> **A:** It's a Spring annotation that allows you to write global exception handlers, model attributes, and data binders that apply to all controllers. In my project, `GlobalExceptionHandler` uses `@ControllerAdvice` + `@ExceptionHandler` to catch exceptions from any controller and convert them into proper HTTP error responses. Without this, the client would get a raw 500 Internal Server Error with a stack trace.
>
> ```java
> // WITHOUT @ControllerAdvice (messy — try-catch in EVERY controller method):
> @PostMapping
> public ResponseEntity<?> createPatient(@RequestBody PatientRequestDTO dto) {
>     try {
>         return ResponseEntity.ok(patientService.createPatient(dto));
>     } catch (EmailAlreadyExistsException e) {
>         return ResponseEntity.badRequest().body(Map.of("Message", e.getMessage()));
>     } catch (StatusRuntimeException e) {
>         return ResponseEntity.status(503).body(Map.of("Message", "Service down"));
>     }
> }  // Repeat for EVERY method in EVERY controller!
>
> // WITH @ControllerAdvice (clean — handle ALL exceptions in ONE place):
> @ControllerAdvice
> public class GlobalExceptionHandler {
>     @ExceptionHandler(EmailAlreadyExistsException.class)
>     public ResponseEntity<Map<String, String>> handleEmail(EmailAlreadyExistsException ex) {
>         return ResponseEntity.badRequest().body(Map.of("Message", "Email already exists!"));
>     }
> }  // Controllers stay clean — just throw the exception, handler catches it.
> ```

**Q: What is the difference between checked and unchecked exceptions?**
> **A:** **Checked exceptions** (extend `Exception`) must be declared in the method signature or caught — the compiler enforces this. **Unchecked exceptions** (extend `RuntimeException`) don't need to be declared or caught. In my project, all custom exceptions extend `RuntimeException` (unchecked) because they represent programming/logic errors that should be handled globally via `@ControllerAdvice`, not littered with try-catch blocks everywhere.
>
> ```java
> // UNCHECKED (your project — extends RuntimeException):
> public class PatientNotFoundException extends RuntimeException { ... }
> // Service method just throws it — NO "throws" clause needed:
> public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto) {
>     Patient patient = patientRepository.findById(id)
>         .orElseThrow(() -> new PatientNotFoundException("Not found", id));
> }
>
> // CHECKED (hypothetical — extends Exception):
> public class PatientNotFoundException extends Exception { ... }
> // Now EVERY method in the chain must declare it:
> public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto)
>     throws PatientNotFoundException { ... }  // ← Forced by compiler!
> // And the controller must have: throws PatientNotFoundException
> // And the caller must have: throws PatientNotFoundException
> // ... ALL THE WAY UP! Very verbose.
> ```

**Q: Why use `RuntimeException` instead of `Exception` for custom exceptions?**
> **A:** Using `RuntimeException` keeps the code clean — service and Controller methods don't need `throws EmailAlreadyExistsException` in their signatures. The `@ControllerAdvice` pattern handles them centrally. Using checked exceptions would force every method in the call chain to declare them, creating boilerplate.
>
> ```java
> // Your approach (unchecked — clean):
> public class EmailAlreadyExistsException extends RuntimeException {
>     public EmailAlreadyExistsException(String message) {
>         super(message);  // Pass message to parent
>     }
> }
> // Anywhere in code: just throw it!
> if (patientRepository.existsByEmail(email)) {
>     throw new EmailAlreadyExistsException("Email already exists: " + email);
> }
> // GlobalExceptionHandler catches it — no try-catch needed anywhere else.
> ```

---

## 14. Database & JPA (Java Persistence API)

### What is JPA?

JPA is a **specification** (set of rules) for mapping Java objects to database tables. **Hibernate** is the **implementation** (actual code that does the work). Think of it like: JPA = the interface, Hibernate = the class that implements it.

### Entity Mapping

```java
@Entity                  // This class maps to a database table
public class Patient {
    @Id                  // This field is the PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.AUTO)  // Auto-generate the value
    private UUID id;

    @NotNull             // Database NOT NULL constraint
    private String name;

    @NotNull
    @Email
    @Column(unique = true)   // Database UNIQUE constraint
    private String email;

    @NotNull
    private String address;

    @NotNull
    private LocalDate dateOfBirth;   // Maps to DATE column in SQL

    @NotNull
    private LocalDate registeredDate;
    
    // getters + setters
}
```

**How it maps to SQL:**
```sql
CREATE TABLE patient (
    id              UUID PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    address         VARCHAR(255) NOT NULL,
    date_of_birth   DATE NOT NULL,
    registered_date DATE NOT NULL
);
```

**Note:** `dateOfBirth` (camelCase in Java) → `date_of_birth` (snake_case in SQL). JPA does this conversion automatically!

### Repository Pattern

```java
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    // JpaRepository provides these FOR FREE:
    // - findAll()         → SELECT * FROM patient
    // - findById(id)      → SELECT * FROM patient WHERE id = ?
    // - save(entity)      → INSERT or UPDATE
    // - deleteById(id)    → DELETE FROM patient WHERE id = ?
    // - count()           → SELECT COUNT(*) FROM patient
    // - existsById(id)    → SELECT EXISTS(...)

    // Custom query methods — Spring generates SQL from the method name!
    boolean existsByEmail(String email);
    // → SELECT COUNT(*) > 0 FROM patient WHERE email = ?

    boolean existsByEmailAndIdNot(String email, UUID id);
    // → SELECT COUNT(*) > 0 FROM patient WHERE email = ? AND id != ?
}
```

### Query Derivation Magic

Spring Data JPA reads method names and generates SQL:

| Method Name | Generated SQL |
|-------------|--------------|
| `findByEmail(String email)` | `WHERE email = ?` |
| `findByNameAndEmail(...)` | `WHERE name = ? AND email = ?` |
| `findByNameOrEmail(...)` | `WHERE name = ? OR email = ?` |
| `existsByEmail(String email)` | `SELECT CASE WHEN COUNT > 0...WHERE email = ?` |
| `findByDateOfBirthBefore(LocalDate date)` | `WHERE date_of_birth < ?` |
| `countByRole(String role)` | `SELECT COUNT(*) WHERE role = ?` |

### Database Used

Your project supports two databases:

- **H2** — In-memory database for development/testing. Data disappears when app stops. Configured via `data.sql` files for initial seed data.
- **PostgreSQL** — Production database (configured in commented-out properties). Data persists permanently.

### Interview Q&A

**Q: What is JPA? How is it different from Hibernate?**
> **A:** JPA (Java Persistence API) is a **specification** — a set of interfaces and annotations that define how Java objects map to relational databases. Hibernate is the most popular **implementation** of JPA. Spring Data JPA is a layer on top that adds the repository pattern. In my project, I define entities with JPA annotations (`@Entity`, `@Id`), and Hibernate (included by Spring Boot) does the actual SQL generation and execution.
>
> ```java
> // You write THIS (JPA annotations — the specification):
> @Entity                               // JPA annotation
> public class Patient {
>     @Id                               // JPA annotation
>     @GeneratedValue(strategy = GenerationType.AUTO)  // JPA annotation
>     private UUID id;
>     @Column(unique = true)            // JPA annotation
>     private String email;
> }
>
> // HIBERNATE (the implementation) generates THIS SQL behind the scenes:
> // CREATE TABLE patient (id UUID PRIMARY KEY, email VARCHAR(255) UNIQUE, ...)
> // INSERT INTO patient (id, email, ...) VALUES (?, ?, ...)
> // SELECT * FROM patient WHERE email = ?
>
> // SPRING DATA JPA adds THIS magic on top:
> public interface PatientRepository extends JpaRepository<Patient, UUID> {
>     boolean existsByEmail(String email);  // You write the method name
>     // Spring Data generates: SELECT COUNT(*) > 0 FROM patient WHERE email = ?
> }
> // You NEVER write SQL or Hibernate code!
> ```

**Q: What is ORM?**
> **A:** ORM (Object-Relational Mapping) automatically converts between Java objects and database tables. Instead of writing SQL, you work with Java objects. `Patient patient = new Patient()` → JPA/Hibernate converts this to SQL and handles the database operations. It eliminates the "impedance mismatch" between OOP and relational databases.
>
> ```java
> // WITHOUT ORM (manual SQL):
> String sql = "INSERT INTO patient (id, name, email) VALUES (?, ?, ?)";
> PreparedStatement stmt = connection.prepareStatement(sql);
> stmt.setObject(1, UUID.randomUUID());
> stmt.setString(2, "Alice");
> stmt.setString(3, "alice@test.com");
> stmt.executeUpdate();
> // Tedious, error-prone, lots of boilerplate!
>
> // WITH ORM (your project):
> Patient patient = new Patient();
> patient.setName("Alice");
> patient.setEmail("alice@test.com");
> patientRepository.save(patient);  // ONE LINE! Hibernate generates the SQL
> // ORM maps: Patient.class ↔ patient table, patient.name ↔ name column
> ```

**Q: What is `JpaRepository`?**
> **A:** `JpaRepository<Patient, UUID>` is a Spring Data interface that provides CRUD operations automatically. The first type parameter (`Patient`) is the entity class, the second (`UUID`) is the primary key type. By extending it, my `PatientRepository` gets `findAll()`, `save()`, `deleteById()`, etc. for free. I can also define custom methods like `existsByEmail()` and Spring generates the SQL from the method name.

**Q: What is `ddl-auto` in JPA?**
> **A:** `spring.jpa.hibernate.ddl-auto` controls how Hibernate manages the database schema. Values: `create` (drop + create tables each startup), `update` (modify tables to match entities), `validate` (only check, don't change), `none` (do nothing). For development, `update` is common. For production, `validate` or `none` with migration tools (like Flyway/Liquibase) is preferred.
>
> ```properties
> # From patient-service application.properties:
> spring.jpa.hibernate.ddl-auto=update
> # Hibernate reads @Entity Patient class and:
> #   1. Checks if 'patient' table exists
> #   2. If not → CREATE TABLE patient (id UUID, name VARCHAR, ...)
> #   3. If yes → compares columns, adds missing ones (e.g., new field)
> #   4. NEVER deletes columns (safe for data)
>
> # For PRODUCTION you'd use:
> spring.jpa.hibernate.ddl-auto=validate
> # Only CHECKS if table matches entity — throws error if schema is wrong
> # Use Flyway/Liquibase for actual schema changes (version-controlled SQL scripts)
> ```

---

## 15. SQL Concepts Used

Your `data.sql` files demonstrate several SQL concepts:

### DDL (Data Definition Language)
```sql
CREATE TABLE IF NOT EXISTS patient (
    id              UUID PRIMARY KEY,       -- Primary Key constraint
    name            VARCHAR(255) NOT NULL,  -- NOT NULL constraint
    email           VARCHAR(255) UNIQUE NOT NULL, -- UNIQUE + NOT NULL
    address         VARCHAR(255) NOT NULL,
    date_of_birth   DATE NOT NULL,
    registered_date DATE NOT NULL
);
```

### DML (Data Manipulation Language)
```sql
-- Conditional INSERT (only if not exists)
INSERT INTO patient (id, name, email, address, date_of_birth, registered_date)
SELECT '123e4567-e89b-12d3-a456-426614174000', 'John Doe', 'john.doe@example.com',
       '123 Main St', '1985-06-15', '2024-01-10'
WHERE NOT EXISTS (
    SELECT 1 FROM patient WHERE id = '123e4567-e89b-12d3-a456-426614174000'
);
```

### Key SQL Concepts

| Concept | How Used | Explanation |
|---------|----------|-------------|
| **PRIMARY KEY** | `id UUID PRIMARY KEY` | Uniquely identifies each row. No duplicates, no nulls. |
| **NOT NULL** | All columns | Column must have a value, can't be empty |
| **UNIQUE** | `email VARCHAR(255) UNIQUE` | No two rows can have the same email |
| **UUID** | For IDs | Universal Unique Identifier instead of auto-increment integer |
| **VARCHAR(255)** | String columns | Variable-length string, max 255 characters |
| **DATE** | Birth/registration dates | Stores date without time |
| **Subquery** | `WHERE NOT EXISTS (SELECT 1 ...)` | Prevents duplicate inserts |

### Interview Q&A

**Q: What is a Primary Key?**
> **A:** A column (or set of columns) that uniquely identifies each row in a table. It must be unique and cannot be NULL. In my project, `id UUID PRIMARY KEY` is the primary key for the `patient` table.

**Q: What is the difference between PRIMARY KEY and UNIQUE?**
> **A:** Both enforce uniqueness. But a table can have only ONE primary key, while it can have MULTIPLE unique constraints. Primary key cannot be null; unique columns CAN be null (one null allowed in most databases). In my project, `id` is the primary key and `email` has a unique constraint — both ensure no duplicates, but for different purposes.

**Q: Why use UUID instead of auto-increment integer for IDs?**
> **A:** In a microservices architecture, multiple services/instances might generate IDs independently. Auto-increment integers would conflict (two services both create ID=1). UUIDs are globally unique — you can generate them anywhere without coordination. They're also harder to guess (security benefit: users can't enumerate IDs like `/patients/1`, `/patients/2`).

**Q: What are ACID properties?**
> **A:** **Atomicity** — a transaction is all-or-nothing. **Consistency** — DB moves from one valid state to another. **Isolation** — concurrent transactions don't interfere. **Durability** — committed data survives crashes. JPA/Hibernate manages transactions that guarantee these properties.

---

## 16. UUID — Universal Unique Identifier

### What Is It?

A 128-bit number that is globally unique:
```
123e4567-e89b-12d3-a456-426614174000
```

Format: `8-4-4-4-12` hexadecimal characters.

### How It's Used in Your Project

```java
import java.util.UUID;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;  // JPA auto-generates a unique UUID for each new patient
```

### Why UUID Over Integer?

| Auto-Increment Integer | UUID |
|----------------------|------|
| `1, 2, 3, 4...` | `123e4567-e89b-...` |
| Simple, short | Long (36 chars) |
| Predictable (security risk) | Random (can't guess) |
| Conflicts in distributed systems | Globally unique |
| Must query DB to get next ID | Can generate without DB |

---

## 17. Authentication & Authorization (Auth Service)

### Authentication vs Authorization

| Authentication | Authorization |
|---------------|--------------|
| "Who are you?" | "What can you do?" |
| Verify identity (login) | Check permissions |
| Your project: Login with email/password | Your project: Role-based (`ADMIN`) |

### Auth Flow in Your Project

```
1. Client sends POST /auth/login with {email, password}
2. AuthController calls AuthService.authenticate()
3. AuthService finds user by email → UserService → UserRepository → Database
4. Compares hashed password using BCryptPasswordEncoder
5. If match → generates JWT token → returns to client
6. Client stores token
7. For subsequent requests, client sends: Authorization: Bearer <token>
8. API Gateway intercepts request → validates token with auth service
9. If valid → forwards request to patient-service
10. If invalid → returns 401 Unauthorized
```

### AuthService Code Explanation

```java
public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
    Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
        // Step 1: Find user by email (returns Optional<User>)
        .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
        // Step 2: Check if raw password matches stored hash
        .map(u -> jwUtil.generateToken(u.getEmail(), u.getRole()));
        // Step 3: If password matches, generate JWT token
    return token;
    // Returns Optional.empty() if email not found OR password doesn't match
}
```

### Interview Q&A

**Q: How does authentication work in your project?**
> **A:** The client sends email and password to `/auth/login`. The `AuthService` looks up the user in the database, uses `BCryptPasswordEncoder` to verify the password against the stored hash. If valid, it generates a JWT token containing the email and role, which is returned to the client. For subsequent requests, the client includes the token in the `Authorization` header. The API Gateway validates the token before forwarding requests to downstream services.
>
> ```java
> // Step 1: Client calls POST /login with {email, password}
> // AuthController.java:
> Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);
>
> // Step 2: AuthService.authenticate() — the entire auth chain in ONE line:
> Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
>     // ↑ SQL: SELECT * FROM users WHERE email = 'testuser@test.com'
>     .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
>     // ↑ BCrypt: compare "password123" with stored hash "$2b$12$7hoRZfJ..."
>     .map(u -> jwUtil.generateToken(u.getEmail(), u.getRole()));
>     // ↑ Create JWT: {sub:"testuser@test.com", role:"ADMIN", exp:...}
>
> // Step 3: Returns token → client stores it
> // Step 4: All future requests include: Authorization: Bearer eyJhbG...
> // Step 5: API Gateway calls GET /validate → AuthService.validateToken()
> ```

**Q: What's the difference between session-based and token-based authentication?**
> **A:** **Session-based:** Server stores session data in memory; client gets a session ID cookie. **Token-based (used in my project):** Server generates a self-contained JWT token; client stores and sends it with each request. Token-based is **stateless** — the server doesn't need to store anything, making it ideal for microservices where requests might hit different server instances.
>
> ```java
> // SESSION-BASED (not your project):
> // Server: sessionStore.put("session123", {userId: 42, role: "ADMIN"});
> // Client cookie: JSESSIONID=session123
> // Problem: if request hits a DIFFERENT server instance, session doesn't exist!
>
> // TOKEN-BASED (your project):
> // AuthService returns JWT containing ALL needed info:
> String token = jwUtil.generateToken(u.getEmail(), u.getRole());
> // Token payload: {sub:"admin@test.com", role:"ADMIN", exp:1709892000}
> // Client sends: Authorization: Bearer eyJhbGciOi...
> // ANY server can validate it — just needs the secret key!
> // Perfect for microservices: API Gateway, Patient Service, or ANY instance
> // can verify the token independently.
> ```

---

## 18. JWT (JSON Web Token)

### Structure

A JWT has 3 parts separated by dots:
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZSI6IkFETUlOIn0.abc123
|_______ HEADER ________||__________________ PAYLOAD _____________________||_ SIGNATURE _|
```

**Header:** Algorithm + token type
```json
{"alg": "HS256", "typ": "JWT"}
```

**Payload:** Data (claims)
```json
{
  "sub": "test@test.com",    // subject (email)
  "role": "ADMIN",           // custom claim
  "iat": 1709856000,         // issued at timestamp
  "exp": 1709892000          // expiration timestamp (10 hours later)
}
```

**Signature:** `HMAC-SHA256(base64(header) + "." + base64(payload), secretKey)`
- If anyone modifies the payload, the signature won't match → tamper-proof!

### JWT Code in Your Project

```java
@Component
public class JwUtil {
    private final Key secretKey;

    // Constructor — reads secret from application.properties
    public JwUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);  // Create HMAC-SHA key
    }

    // Generate token
    public String generateToken(String email, String role) {
        return Jwts.builder()
            .subject(email)                    // who the token is for
            .claim("role", role)               // custom data
            .issuedAt(new Date())              // when it was created
            .expiration(new Date(System.currentTimeMillis() + 1000*60*60*10)) // 10 hours
            .signWith(secretKey)               // sign with secret key
            .compact();                        // build the token string
    }

    // Validate token
    public void validateToken(String token) {
        Jwts.parser()
            .verifyWith((SecretKey) secretKey)  // use same key to verify
            .build()
            .parseSignedClaims(token);          // throws exception if invalid/expired
    }
}
```

### Interview Q&A

**Q: What is JWT? How does it work?**
> **A:** JWT is a compact, URL-safe token format. It has 3 Base64-encoded parts: Header (algorithm), Payload (claims/data), and Signature. The server signs the token with a secret key. When the client sends it back, the server verifies the signature using the same key. If the payload was tampered with, the signature won't match. JWTs are self-contained — the server doesn't need to store session data, just verify the signature.

**Q: Is JWT encrypted?**
> **A:** No! The payload is only Base64-encoded, not encrypted. Anyone can decode it and read the contents. **Never put passwords or sensitive data in JWT.** The signature only ensures the token hasn't been tampered with (integrity), not that it's secret (confidentiality). If you need encryption, use JWE (JSON Web Encryption).
>
> ```java
> // JWT token from your project:
> // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZSI6IkFETUlOIn0.xxx
> //         Header             .             Payload                              .Signature
>
> // ANYONE can decode the payload (it's just Base64!):
> // Base64.decode("eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZSI6IkFETUlOIn0")
> // → {"sub":"test@test.com","role":"ADMIN"}
> // So NEVER put password, SSN, credit card in JWT!
>
> // The SIGNATURE ensures nobody changed the payload:
> // If attacker changes "role":"USER" to "role":"ADMIN" → signature won't match
> // → jwUtil.validateToken() returns false
> ```

**Q: What happens if someone steals a JWT?**
> **A:** They can impersonate the user until the token expires. Mitigation strategies: (1) Short expiration times (my project uses 10 hours). (2) Use HTTPS to prevent interception. (3) Implement refresh tokens. (4) Token blacklisting for logout. (5) Store tokens securely (HttpOnly cookies, not localStorage).
>
> ```java
> // From JwUtil.java — tokens expire after 10 hours:
> .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
> //                                                   10h * 60m * 60s * 1000ms
>
> // If stolen token is used after 10 hours:
> // Jwts.parser().parseSignedClaims(token)
> // → throws ExpiredJwtException → validateToken() returns false → 401 Unauthorized
>
> // Better approach: short-lived access token (15 min) + long-lived refresh token
> // Access token expires quickly → less damage if stolen
> // Refresh token used only to get a new access token
> ```

**Q: What's the difference between symmetric and asymmetric JWT signing?**
> **A:** **Symmetric** (used in my project): Same secret key signs and verifies. Simple but requires sharing the secret between services. **Asymmetric** (RS256): Private key signs, public key verifies. Better for microservices — only auth service has the private key; other services just need the public key.
>
> ```java
> // SYMMETRIC (your project — HS256):
> // Auth-service signs: HMAC-SHA256(header + payload, SECRET_KEY)
> // Any verifier needs: the SAME SECRET_KEY
> private final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
> // Problem: if billing-service needs to verify tokens, it also needs the secret.
> //          If ANY service is compromised, attacker can forge tokens!
>
> // ASYMMETRIC (RS256 — better for production):
> // Auth-service signs with: PRIVATE key (only auth-service has this)
> // Anyone verifies with:    PUBLIC key (can be shared freely)
> // If billing-service is compromised, attacker can't forge tokens —
> //   they only have the public key!
> ```

---

## 19. Password Hashing with BCrypt

### Why Not Store Passwords in Plain Text?

If a hacker gets your database:
- Plain text: `password123` → they know everyone's password immediately
- Hashed: `$2b$12$7hoRZfJ...` → they can't reverse this back to the password

### How BCrypt Works

```
Input:  "password123"
          ↓
BCrypt.hash(password, salt, cost_factor)
          ↓
Output: "$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu"
         |  |  |__________________________|_______________________________|
         |  |  |          Salt             |           Hash                |
         |  |  Cost Factor (12 rounds)
         |  Version (2b)
         Algorithm identifier
```

### In Your Project

```java
// SecurityConfig.java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();  // Creates BCrypt encoder with default strength 10
}

// AuthService.java — comparing passwords
passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword())
// matches("password123", "$2b$12$7hoRZfJ...")  → true
// BCrypt extracts the salt from the stored hash and re-hashes the input to compare
```

### data.sql — Pre-hashed password
```sql
INSERT INTO "users" (id, email, password, role)
SELECT '223e4567-...', 'testuser@test.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'ADMIN'
-- This hash corresponds to some password that was hashed offline
```

### Interview Q&A

**Q: Why use BCrypt instead of SHA-256 for passwords?**
> **A:** SHA-256 is a fast hash — an attacker can try billions of passwords per second. BCrypt is **intentionally slow** (configurable cost factor). With cost factor 12, it takes ~250ms per hash. This makes brute-force attacks impractical. BCrypt also automatically handles salting, preventing rainbow table attacks. SHA-256 requires manual salting.
>
> ```java
> // SHA-256 (FAST — bad for passwords):
> // Hash("password123") → instant (~1 nanosecond per hash)
> // Attacker tries 1 BILLION passwords/second! Cracks most passwords in minutes.
>
> // BCrypt (SLOW — good for passwords):
> new BCryptPasswordEncoder(12)  // Cost factor 12 = 2^12 = 4096 iterations
> // Hash("password123") → ~250ms per hash
> // Attacker tries 4 passwords/second. Would take YEARS to crack!
>
> // Your project:
> passwordEncoder.matches("password123", "$2b$12$7hoRZfJ...");
> //                       ↑ user input   ↑ stored hash in DB
> // BCrypt extracts salt from stored hash, re-hashes input, and compares.
> ```

**Q: What is a salt?**
> **A:** A random value added to the password before hashing. Without salt, two users with password "abc" would have identical hashes. With salt, each gets a unique hash. BCrypt generates a random salt for each password and stores it as part of the hash string. This prevents precomputed hash attacks (rainbow tables).
>
> ```java
> // WITHOUT salt (dangerous):
> // User1: SHA256("password123") → "ef92b778..."
> // User2: SHA256("password123") → "ef92b778..."  // SAME HASH!
> // Attacker sees identical hashes → knows they have the same password.
> // Rainbow table: Pre-computed list of hash → password. Just look it up!
>
> // WITH salt (BCrypt in your project):
> // User1: BCrypt("password123", salt="X3kf9a") → "$2b$12$X3kf9a...qZ8m"
> // User2: BCrypt("password123", salt="Y7pq2b") → "$2b$12$Y7pq2b...rT5w"
> // Different hashes even though SAME password! Rainbow tables useless.
> // Salt is stored IN the hash: $2b$12$[SALT][HASH]
> ```

---

## 20. Spring Security Basics

### Your Project's Security Configuration

```java
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll())   // Allow ALL requests without authentication
            .csrf(AbstractHttpConfigurer::disable);  // Disable CSRF protection
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### Why `permitAll()`?

The auth service itself doesn't need auth — it IS the auth service! Anyone needs to be able to call `/login` to authenticate. The actual protection happens at the **API Gateway level** where JWT tokens are validated before forwarding requests.

### Why Disable CSRF?

CSRF (Cross-Site Request Forgery) protection is for browser-based applications with cookies. Since your project uses JWT tokens (sent in headers, not cookies), CSRF is not applicable. REST APIs are inherently safe from CSRF because they use custom headers.

### What is `SecurityFilterChain`?

Spring Security works as a chain of filters that intercept every HTTP request:
```
Request → Filter 1 (CORS) → Filter 2 (CSRF) → Filter 3 (Auth) → ... → Controller
```
You configure this chain to define which requests need authentication and which don't.

### Interview Q&A

**Q: What is Spring Security?**
> **A:** Spring Security is a framework providing authentication, authorization, and security features for Spring applications. It works as a filter chain that intercepts HTTP requests. In my project, the auth service's SecurityConfig permits all requests (since it's the login endpoint) and provides a BCryptPasswordEncoder bean for secure password hashing.
>
> ```java
> // From SecurityConfig.java in auth-service:
> @Configuration
> public class SecurityConfig {
>     @Bean
>     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
>         http.authorizeHttpRequests(authorize -> authorize
>             .anyRequest().permitAll())       // Allow ALL requests (it's the auth service!)
>             .csrf(AbstractHttpConfigurer::disable);  // Disable CSRF (using JWT, not cookies)
>         return http.build();
>     }
>
>     @Bean
>     public PasswordEncoder passwordEncoder() {
>         return new BCryptPasswordEncoder(); // Shared across AuthService for password hashing
>     }
> }
>
> // WITHOUT this config, Spring Security would AUTO-BLOCK everything:
> //   → Generate random password at startup
> //   → Redirect to a login FORM
> //   → Block all API calls including /login itself!
> ```

**Q: What is CSRF? Why did you disable it?**
> **A:** CSRF (Cross-Site Request Forgery) is an attack where a malicious website tricks a user's browser into making unwanted requests to your site using the user's session cookie. CSRF protection is needed when using cookie-based sessions. My project uses JWT tokens in headers (not cookies), so CSRF is not a concern. REST APIs with token-based auth should disable CSRF.
>
> ```java
> // CSRF attack scenario (cookie-based — NOT your project):
> // 1. User logs into bank.com → browser stores session cookie
> // 2. User visits evil.com
> // 3. evil.com has: <form action="bank.com/transfer" method="POST">
> //    <input name="amount" value="10000"><input name="to" value="hacker">
> // 4. Browser auto-attaches bank.com cookie → Bank thinks it's legitimate!
>
> // Why YOUR project is IMMUNE:
> // Client sends: Authorization: Bearer eyJhbGciOi...  (in HEADER)
> // Browsers do NOT auto-attach headers to cross-origin requests!
> // So: http.csrf(AbstractHttpConfigurer::disable);  // Safe to disable
> ```

---

## 21. API Gateway Pattern

### What Is It?

A **single entry point** for all client requests. Instead of clients knowing about 5 different services, they only talk to the gateway:

```
WITHOUT Gateway:                    WITH Gateway:
Client → patient-service:4000       Client → api-gateway:4004
Client → auth-service:4005                  ├→ patient-service:4000
Client → billing-service:4001               ├→ auth-service:4005
(Client knows all services!)                └→ billing-service:4001
                                    (Client knows only gateway!)
```

### Your API Gateway Configuration

```yaml
server:
  port: 4004

spring:
  cloud:
    gateway:
      routes:
        # Route 1: /auth/** → auth-service:4005
        - id: auth-service-route
          uri: http://auth-service:4005
          predicates:
            - Path=/auth/**          # Match any URL starting with /auth/
          filters:
            - StripPrefix=1          # Remove /auth from the URL
            # /auth/login → /login

        # Route 2: /api/patients/** → patient-service:4000
        - id: patient-service-route
          uri: http://patient-service:4000
          predicates:
            - Path=/api/patients/**
          filters:
            - StripPrefix=1
            # /api/patients → /patients
```

### `StripPrefix` Filter Explained

```
Client sends:  GET http://api-gateway:4004/api/patients
StripPrefix=1 removes "api" → forwards as: GET http://patient-service:4000/patients
```

### JWT Validation at Gateway Level

```java
@Component
public class JwtValidationGatewayFilterFactory 
    extends AbstractGatewayFilterFactory<Object> {
    
    private final WebClient webClient;  // Non-blocking HTTP client

    public JwtValidationGatewayFilterFactory(
        WebClient.Builder webClientBuilder,
        @Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            // 1. Extract Authorization header
            String token = exchange.getRequest().getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
            // 2. Check if token exists and starts with "Bearer "
            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse()  // Return 401 Unauthorized
            }
            // 3. Call auth-service to validate token
            // 4. If valid, forward request to downstream service
        });
    }
}
```

### Interview Q&A

**Q: What is an API Gateway? Why use one?**
> **A:** An API Gateway is a single entry point for all client requests in a microservices architecture. Benefits: (1) **Single URL** — clients don't need to know about individual services. (2) **Cross-cutting concerns** — authentication, logging, rate limiting handled in one place. (3) **Load balancing** — can distribute requests across service instances. (4) **API composition** — can aggregate responses from multiple services. In my project, the gateway routes `/auth/**` to auth-service and `/api/patients/**` to patient-service, and validates JWT tokens before forwarding.
>
> ```yaml
> # From api-gateway application.yml:
> spring:
>   cloud:
>     gateway:
>       routes:
>         - id: auth-service-route
>           uri: http://auth-service:4005
>           predicates:
>             - Path=/auth/**           # /auth/login → auth-service:4005/login
>           filters:
>             - StripPrefix=1
>
>         - id: patient-service-route
>           uri: http://patient-service:4000
>           predicates:
>             - Path=/api/patients/**   # /api/patients → patient-service:4000/patients
>           filters:
>             - StripPrefix=1
>             - JwtValidation           # Custom filter: validates JWT before forwarding
> ```
> ```
> Client only knows: http://localhost:4004  (gateway)
> Client NEVER calls: http://localhost:4000  (patient-service directly)
> ```

**Q: What is Spring Cloud Gateway?**
> **A:** A reactive, non-blocking API gateway built on Spring WebFlux (Project Reactor). Unlike Spring MVC which uses one thread per request, Spring Cloud Gateway uses Netty's event loop for high concurrency. It supports route matching (predicates), request/response modification (filters), and integrates with Spring Cloud service discovery.
>
> ```java
> // Key difference from Spring MVC:
> // Spring MVC (patient-service, auth-service): ONE thread per request
> //   Thread-1: handles GET /patients → blocked waiting for DB → returns response
> //   Thread-2: handles POST /patients → blocked waiting for gRPC → returns response
> //   100 concurrent requests = 100 threads!
>
> // Spring WebFlux (api-gateway): Event loop, NON-BLOCKING
> //   Single thread handles thousands of requests!
> //   That's why the gateway uses WebClient (non-blocking) not RestTemplate (blocking):
> this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
> // WebClient returns Mono<T>/Flux<T> — reactive types that don't block threads
> ```

**Q: What's the difference between `WebClient` and `RestTemplate`?**
> **A:** `RestTemplate` is synchronous (blocking) — the thread waits for the response. `WebClient` is asynchronous and non-blocking — the thread can do other work while waiting. Spring Cloud Gateway uses `WebClient` because it's built on reactive principles. `RestTemplate` is deprecated in newer Spring versions in favor of `WebClient`.
>
> ```java
> // RestTemplate (BLOCKING — old way, NOT used in gateway):
> RestTemplate restTemplate = new RestTemplate();
> String result = restTemplate.getForObject("http://auth-service:4005/validate", String.class);
> // Thread is STUCK here until auth-service responds!
> // If auth-service takes 5 seconds, this thread does NOTHING for 5 seconds.
>
> // WebClient (NON-BLOCKING — used in your JwtValidationGatewayFilterFactory):
> this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
> webClient.get()
>     .uri("/validate")
>     .header(HttpHeaders.AUTHORIZATION, authHeader)
>     .retrieve()
>     .toBodilessEntity();
> // Thread is FREE while waiting → can handle other requests!
> // When auth-service responds, a callback processes the result.
> ```

---

## 22. gRPC (Google Remote Procedure Call)

### What Is gRPC?

gRPC is a high-performance framework for service-to-service communication. Instead of HTTP + JSON, it uses HTTP/2 + Protocol Buffers (binary format).

```
REST API Call:
  Client → HTTP POST /billing/create → JSON body → Parse JSON → Process

gRPC Call:
  Client → billingService.createBillingAccount(request) → Binary → Process
  It feels like calling a local method, but it executes on a remote server!
```

### REST vs gRPC Comparison

| Feature | REST | gRPC |
|---------|------|------|
| Protocol | HTTP/1.1 | HTTP/2 |
| Data Format | JSON (text) | Protobuf (binary) |
| Speed | Slower | 2-10x faster |
| Schema | Optional (OpenAPI) | Required (.proto) |
| Browser Support | Native | Requires proxy |
| Streaming | Limited | Full bidirectional |
| Best For | Public APIs | Internal microservice communication |

### How gRPC Works in Your Project

```
Patient Service (CLIENT)              Billing Service (SERVER)
         |                                    |
         |--- gRPC Request (binary) -------->|
         |    createBillingAccount()          |
         |    {patientId, name, email}        |
         |                                    | Process request
         |<-- gRPC Response (binary) ---------|
         |    {accountId: "12345",            |
         |     status: "ACTIVE"}              |
```

### gRPC Client (Patient Service)

```java
@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
        @Value("${billing.service.address}") String serverAddress,
        @Value("${billing.service.grpc.port:9001}") int serverPort) {
        // Create a communication channel to the billing service
        ManagedChannel channel = ManagedChannelBuilder
            .forAddress(serverAddress, serverPort)  // localhost:9001
            .usePlaintext()      // No TLS for development
            .build();
        // Create a blocking stub (waits for response)
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        // Build the request using generated Protobuf builder
        BillingRequest request = BillingRequest.newBuilder()
            .setPatientId(patientId)
            .setName(name)
            .setEmail(email)
            .build();
        // Make the RPC call — feels like a local method call!
        BillingResponse response = blockingStub.createBillingAccount(request);
        return response;
    }
}
```

### gRPC Server (Billing Service)

```java
@GrpcService  // Registers this class as a gRPC service
public class BillingGrpcService extends BillingServiceImplBase {
    
    @Override
    public void createBillingAccount(BillingRequest billingRequest,
        StreamObserver<BillingResponse> responseObserver) {
        // 1. Business logic (create billing account in DB, etc.)
        
        // 2. Build response
        BillingResponse response = BillingResponse.newBuilder()
            .setAccountId("12345")
            .setStatus("ACTIVE")
            .build();
        
        // 3. Send response back to client
        responseObserver.onNext(response);     // Send the response
        responseObserver.onCompleted();         // Signal: "I'm done"
    }
}
```

### `StreamObserver` Pattern

This is the **Observer Design Pattern**:
```java
responseObserver.onNext(response);    // "Here's your data"
responseObserver.onCompleted();       // "I'm done sending"
// If error: responseObserver.onError(exception);
```

### Interview Q&A

**Q: What is gRPC? Why did you use it?**
> **A:** gRPC is a high-performance RPC framework by Google that uses HTTP/2 and Protocol Buffers. I used it for patient-service → billing-service communication because: (1) It's faster than REST (binary format, HTTP/2 multiplexing). (2) It's strongly typed — the `.proto` file defines the exact contract. (3) It generates client/server code automatically. (4) It's ideal for internal service-to-service communication where browser compatibility isn't needed.
>
> ```java
> // gRPC feels like calling a LOCAL method, but executes on a REMOTE server:
>
> // CLIENT side (BillingServiceGrpcClient.java in patient-service):
> BillingResponse response = blockingStub.createBillingAccount(request);
> // Looks like a normal method call! But internally:
> //   1. Serializes 'request' to binary (Protobuf) — ~20 bytes
> //   2. Sends over HTTP/2 to billing-service:9001
> //   3. Billing service deserializes, processes, responds
> //   4. Response deserialized back to BillingResponse object
>
> // Compare with REST (hypothetical):
> // ResponseEntity<BillingResponse> response = restTemplate
> //     .postForEntity("http://billing-service:4001/billing", jsonRequest, ...);
> //   1. Serializes to JSON text: {"patientId":"abc",...} — ~100 bytes
> //   2. Sends over HTTP/1.1
> //   3. Text parsing on both sides — slower!
> ```

**Q: What is a blocking stub vs async stub in gRPC?**
> **A:** A **blocking stub** (`newBlockingStub`) waits for the response — the calling thread is blocked until the server responds. An **async stub** (`newStub`) returns immediately and uses callbacks/listeners for the response. My project uses a blocking stub because the patient creation must wait for the billing account to be created before responding to the client.
>
> ```java
> // BLOCKING STUB (your project — BillingServiceGrpcClient.java):
> blockingStub = BillingServiceGrpc.newBlockingStub(channel);
> BillingResponse response = blockingStub.createBillingAccount(request);
> // Thread STOPS here until billing-service responds.
> // Next line only runs AFTER billing response is received.
> log.info("Billing account created: {}", response.getAccountId());
>
> // ASYNC STUB (not in your project, but for comparison):
> asyncStub = BillingServiceGrpc.newStub(channel);  // non-blocking
> asyncStub.createBillingAccount(request, new StreamObserver<BillingResponse>() {
>     @Override
>     public void onNext(BillingResponse response) {
>         // This callback runs LATER when response arrives
>         log.info("Got response: {}", response.getAccountId());
>     }
>     @Override public void onCompleted() { }
>     @Override public void onError(Throwable t) { }
> });
> // Code continues IMMEDIATELY — doesn't wait for response!
> ```

**Q: What is `usePlaintext()`?**
> **A:** By default, gRPC uses TLS (encrypted communication). `usePlaintext()` disables TLS for local development. In production, you'd remove this and configure proper TLS certificates for security.
>
> ```java
> // From BillingServiceGrpcClient.java:
> ManagedChannel channel = ManagedChannelBuilder
>     .forAddress(serverAddress, serverPort)
>     .usePlaintext()  // No TLS — data sent in plain text (HTTP/2 without encryption)
>     .build();
>
> // Development: usePlaintext() — fast, no cert setup needed
> // Production:  remove usePlaintext() + add TLS certificates:
> // ManagedChannelBuilder.forAddress(host, port)
> //     .useTransportSecurity()  // Enable TLS
> //     .sslContext(sslContext)   // Provide certificate
> //     .build();
> ```

---

## 23. Protocol Buffers (Protobuf)

### What Is Protobuf?

A language-neutral, platform-neutral way to define data structures. You write a `.proto` file, and a compiler generates Java classes automatically.

### Your Proto Files

**billing_service.proto:**
```protobuf
syntax = "proto3";                    // Use protobuf version 3

option java_multiple_files = true;    // Generate separate Java files
option java_package = "billing";      // Package for generated classes

// Define the service (like an interface)
service BillingService {
  rpc CreateBillingAccount(BillingRequest) returns (BillingResponse);
}

// Define request message (like a struct/class)
message BillingRequest {
  string patientId = 1;   // Field number 1 (used in binary encoding, NOT value)
  string name = 2;        // Field number 2
  string email = 3;       // Field number 3
}

// Define response message
message BillingResponse {
  string accountId = 1;
  string status = 2;
}
```

**patient_event.proto:**
```protobuf
syntax = "proto3";
package patient.events;
option java_multiple_files = true;

message PatientEvent {
  string patientId = 1;
  string name = 2;
  string email = 3;
  string event_type = 4;
}
```

### What Gets Generated

From `billing_service.proto`, the protobuf compiler generates:
1. `BillingRequest.java` — message class with builder pattern
2. `BillingResponse.java` — message class with builder pattern
3. `BillingServiceGrpc.java` — client stubs and server base class

### Builder Pattern in Generated Code

```java
// Protobuf objects are IMMUTABLE — you build them using the Builder Pattern
BillingRequest request = BillingRequest.newBuilder()  // Create builder
    .setPatientId("123")    // Set fields
    .setName("John")
    .setEmail("john@test.com")
    .build();               // Create immutable object

// You CANNOT do: request.setName("Jane") — immutable!
```

### Interview Q&A

**Q: What is Protocol Buffers? Why use it instead of JSON?**
> **A:** Protocol Buffers (Protobuf) is Google's binary serialization format. Compared to JSON: (1) **Smaller** — binary encoding is 3-10x smaller than JSON. (2) **Faster** — no text parsing, direct binary reading. (3) **Strongly typed** — schema is defined in `.proto` files, ensuring type safety. (4) **Code generation** — client/server code auto-generated from `.proto` files. (5) **Backward compatible** — field numbers allow schema evolution without breaking existing clients.
>
> ```protobuf
> // From billing_service.proto:
> message BillingRequest {
>   string patientId = 1;  // Field number (NOT default value)
>   string name = 2;
>   string email = 3;
> }
> ```
> ```
> // Size comparison:
> // JSON:     {"patientId":"abc","name":"John","email":"j@t.com"}  → ~60 bytes (text)
> // Protobuf: [0x0A 0x03 0x61 0x62 0x63 0x12 0x04 ...]            → ~20 bytes (binary)
> //           Field 1=abc  Field 2=John  Field 3=j@t.com
> // 3x smaller! And parsing is ~10x faster (no JSON text parsing)
> ```

**Q: What are field numbers in protobuf (the `= 1`, `= 2`)?**
> **A:** They're not default values! They're unique identifiers used in binary encoding. The wire format stores field_number + value, not field_name. This makes it compact and allows backward compatibility — you can add new fields with new numbers without breaking old clients that don't know about them.
>
> ```protobuf
> message BillingRequest {
>   string patientId = 1;  // Binary: [field 1] [length] [value bytes]
>   string name = 2;       // Binary: [field 2] [length] [value bytes]
>   string email = 3;      // Binary: [field 3] [length] [value bytes]
> }
>
> // Adding a new field later (backward compatible):
> message BillingRequest {
>   string patientId = 1;
>   string name = 2;
>   string email = 3;
>   string phone = 4;      // New field! Old clients just ignore field 4.
>                           // Old clients still work — they don't know about field 4.
> }
> // NEVER reuse or change existing field numbers!
> // NEVER use = 0 (reserved by protobuf)
> ```

---

## 24. Apache Kafka — Event-Driven Architecture

### What Is Kafka?

A distributed event streaming platform. Think of it as a super-fast, persistent message queue:

```
Producer (Patient Service)  →  Kafka Broker  →  Consumer (Analytics Service)
"Hey, a new patient was         [Topic: patient]      "Oh, a new patient!
 created! Here's the data."     Stores messages         Let me log it."
```

### Key Concepts

| Concept | Analogy | In Your Project |
|---------|---------|----------------|
| **Producer** | Person sending a letter | `KafkaProducer` in patient-service |
| **Consumer** | Person receiving a letter | `KafkaConsumer` in analytics-service |
| **Topic** | Mailbox labeled "patient" | `"patient"` topic |
| **Broker** | Post office | Kafka server (localhost:9092) |
| **Consumer Group** | Department receiving mail | `"analytics-service"` |
| **Offset** | Bookmark in a book | Tracks which messages have been read |

### Synchronous vs Asynchronous Communication

```
SYNCHRONOUS (REST/gRPC) — Used for Patient → Billing:
  Patient Service: "Create billing account" → WAITS → Billing Service responds → CONTINUES
  ✅ Guaranteed response
  ❌ Both services must be running

ASYNCHRONOUS (Kafka) — Used for Patient → Analytics:
  Patient Service: "Patient created event" → SENDS → MOVES ON (doesn't wait)
  Analytics Service: Picks up message whenever it's ready
  ✅ Services can be independent
  ✅ If analytics is down, message waits in Kafka
  ❌ No immediate response
```

### Kafka Producer (Patient Service)

```java
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    // KafkaTemplate<KeyType, ValueType>
    // Key: String (used for partitioning)
    // Value: byte[] (serialized protobuf message)

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        // 1. Build protobuf event
        PatientEvent event = PatientEvent.newBuilder()
            .setPatientId(patient.getId().toString())
            .setName(patient.getName())
            .setEmail(patient.getEmail())
            .setEventType("PATIENT_CREATED")
            .build();

        // 2. Send to Kafka topic "patient" as byte array
        kafkaTemplate.send("patient", event.toByteArray());
        // Fire and forget — doesn't wait for analytics to process
    }
}
```

### Kafka Consumer (Analytics Service)

```java
@Service
public class KafkaConsumer {
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    // Automatically listens for new messages on "patient" topic
    // groupId ensures each message is consumed by one consumer in the group
    public void consumeEvent(byte[] event) {
        PatientEvent patientEvent = PatientEvent.parseFrom(event);
        // Deserialize protobuf from bytes
        log.info("Received Patient Event: patientId={}, name={}", 
            patientEvent.getPatientId(), patientEvent.getName());
    }
}
```

### Kafka Configuration

```properties
# Producer (patient-service)
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.ByteArraySerializer

# Consumer (analytics-service)
spring.kafka.consumer.group-id=analytics-service
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.ByteArrayDeserializer
spring.kafka.consumer.auto-offset-reset=earliest
# earliest = read all messages from beginning if no offset saved
# latest = read only new messages
```

### Interview Q&A

**Q: What is Kafka? Why did you use it?**
> **A:** Apache Kafka is a distributed event streaming platform for building real-time data pipelines. I used it for communication between patient-service and analytics-service because: (1) **Decoupling** — patient-service doesn't need to know about analytics-service. (2) **Resilience** — if analytics-service is down, messages are stored in Kafka until it comes back up. (3) **Scalability** — can add more consumers without changing the producer. (4) **Asynchronous** — patient creation doesn't wait for analytics processing.
>
> ```java
> // PRODUCER (patient-service — KafkaProducer.java):
> public void sendEvent(Patient patient) {
>     PatientEvent event = PatientEvent.newBuilder()
>         .setPatientId(patient.getId().toString())
>         .setName(patient.getName())
>         .setEmail(patient.getEmail())
>         .setEventType("PATIENT_CREATED")
>         .build();
>     kafkaTemplate.send("patient", event.toByteArray());
>     // Fire and forget! Patient service moves on immediately.
> }
>
> // CONSUMER (analytics-service — KafkaConsumer.java):
> @KafkaListener(topics = "patient", groupId = "analytics-service")
> public void consumeEvent(byte[] event) {
>     PatientEvent patientEvent = PatientEvent.parseFrom(event);
>     log.info("Received Patient Event: patientId={}", patientEvent.getPatientId());
>     // Processes independently — even if patient-service is now down!
> }
> // If analytics-service was DOWN during patient creation,
> // Kafka stores the message. When it restarts, it picks up from where it left off.
> ```

**Q: What is the difference between Kafka and a traditional message queue (like RabbitMQ)?**
> **A:** Key differences: (1) **Persistence** — Kafka persists messages on disk; MQs typically delete after consumption. (2) **Consumer groups** — Kafka allows multiple consumer groups to independently read the same messages. (3) **Ordering** — Kafka guarantees ordering within a partition. (4) **Throughput** — Kafka handles millions of messages/second. (5) **Replay** — Kafka consumers can re-read old messages by resetting offsets.
>
> ```
> KAFKA (your project):             RABBITMQ (traditional):
> ┌────────────────┐              ┌────────────────┐
> │  Topic: patient  │              │  Queue: patient  │
> │  [msg1][msg2]    │              │  [msg1][msg2]    │
> └────────────────┘              └────────────────┘
>  ↓              ↓                 ↓ (deleted after read!)
> analytics  billing            analytics
> (group 1)  (group 2)          (message gone!)
> Both read ALL messages!      Once read = gone.
> Can replay from offset 0.    No replay possible.
> ```

**Q: What is a Consumer Group?**
> **A:** A set of consumers that cooperate to consume messages from a topic. Within a group, each message is delivered to exactly ONE consumer (load balancing). Different groups each get their own copy of every message (broadcast). In my project, the group `analytics-service` ensures if I have 3 analytics instances, each message is processed by only one of them.
>
> ```java
> // Your KafkaConsumer.java:
> @KafkaListener(topics = "patient", groupId = "analytics-service")
> // groupId = "analytics-service"
>
> // Scenario: 3 instances of analytics-service running
> // Instance 1: @KafkaListener(groupId = "analytics-service")  → gets msg 1, 4, 7
> // Instance 2: @KafkaListener(groupId = "analytics-service")  → gets msg 2, 5, 8
> // Instance 3: @KafkaListener(groupId = "analytics-service")  → gets msg 3, 6, 9
> // Each message processed by EXACTLY ONE instance (load balanced!)
>
> // If you add a SECOND group (e.g., notification-service):
> // @KafkaListener(groupId = "notification-service")  → gets ALL messages too!
> // Both groups independently consume the same topic.
> ```

**Q: What is `auto-offset-reset`?**
> **A:** It determines where a new consumer group starts reading: `earliest` = from the first available message (replay all history), `latest` = only new messages from now. My project uses `earliest` so analytics processes all patient events even if it starts after patients were created.
>
> ```properties
> # From analytics-service application.properties:
> spring.kafka.consumer.auto-offset-reset=earliest
> # First time analytics-service starts:
> #   earliest → "Read ALL messages from offset 0" (catches up on all past events)
> #   latest   → "Only read NEW messages from now" (misses past events)
>
> # Scenario:
> # 10 patients created BEFORE analytics starts for the first time
> # earliest: analytics processes all 10 events on startup
> # latest:   analytics only sees patients created AFTER it started (missed 10!)
> ```

---

## 25. Docker & Containerization

### What Is Docker?

Docker packages your application + all its dependencies into a **container** — a lightweight, isolated environment that runs the same everywhere.

```
Without Docker:
  "Works on my machine!" → Different Java version, different OS, missing dependencies

With Docker:
  Application + JDK 21 + all libraries → Container → Runs IDENTICALLY everywhere
```

### Your Dockerfile (Patient Service)

```dockerfile
# STAGE 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS builder
# Start from an image that has Maven + Java 21

WORKDIR /app
# Set working directory inside the container

COPY pom.xml .
# Copy only pom.xml first
RUN mvn dependency:go-offline -B
# Download all dependencies (this layer is CACHED if pom.xml hasn't changed)

COPY src ./src
# Copy source code
RUN mvn clean package
# Build the application → creates a .jar file

# STAGE 2: Run the application
FROM eclipse-temurin:21-jdk-jammy AS runner
# Start from a clean, smaller image with just Java 21

WORKDIR /app
COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar
# Copy ONLY the .jar from the build stage (not Maven, not source code)

EXPOSE 4000
# Document that this container listens on port 4000

ENTRYPOINT ["java", "-jar", "app.jar"]
# Command to run when container starts
```

### Multi-Stage Build Explained

```
Stage 1 (Builder): Maven + JDK + Source Code + Dependencies = ~800MB
                    ↓ builds ↓
                    app.jar (50MB)

Stage 2 (Runner):  JDK + app.jar = ~300MB  (Maven and source code EXCLUDED)

Without multi-stage: Image would be ~800MB
With multi-stage:    Image is ~300MB (62% smaller!)
```

### Key Docker Concepts

| Concept | Explanation |
|---------|-------------|
| **Image** | A read-only template (like a class definition) |
| **Container** | A running instance of an image (like an object) |
| **Dockerfile** | Instructions to build an image (like a recipe) |
| **Layer** | Each Dockerfile instruction creates a cacheable layer |
| **EXPOSE** | Documents the port (doesn't actually open it) |
| **ENTRYPOINT** | The command that runs when the container starts |

### Interview Q&A

**Q: What is Docker? Why use it?**
> **A:** Docker is a containerization platform that packages applications with their dependencies into isolated containers. Benefits: (1) **Consistency** — same environment in development, testing, and production. (2) **Isolation** — each service runs in its own container with its own dependencies. (3) **Portability** — containers run on any machine with Docker. (4) **Scalability** — easily spin up multiple instances. In my project, each microservice has its own Dockerfile, enabling independent deployment.
>
> ```dockerfile
> # From patient-service Dockerfile:
> # STAGE 1: Build (heavy Maven image ~800MB)
> FROM maven:3.9.9-amazoncorretto-21 AS build
> COPY pom.xml .
> COPY src ./src
> RUN mvn clean package -DskipTests
>
> # STAGE 2: Run (lightweight JDK image ~200MB) 
> FROM amazoncorretto:21-alpine
> COPY --from=build /target/*.jar app.jar   # Only copy the .jar from stage 1
> ENTRYPOINT ["java", "-jar", "app.jar"]
>
> # Without Docker:
> #   "Works on my machine" — different Java versions, different OS, different configs
> # With Docker:
> #   Same image runs EVERYWHERE — dev, test, prod, any cloud
> ```

**Q: What is a multi-stage Docker build?**
> **A:** A Dockerfile with multiple `FROM` instructions. Each stage produces an intermediate image. The final stage copies only needed artifacts from previous stages. In my project, stage 1 uses a heavy Maven image to build the .jar, and stage 2 uses a lightweight JDK image with just the .jar. This reduces the final image size significantly.
>
> ```dockerfile
> # Why multi-stage matters — size comparison:
> # Single stage (Maven image):  ~800MB — includes Maven, source code, .m2 cache
> # Multi-stage (JDK-only image): ~200MB — just JDK + your 30MB .jar
>
> # Stage 1: BUILD environment (thrown away after build!)
> FROM maven:3.9.9-amazoncorretto-21 AS build   # 800MB image
> COPY pom.xml .
> COPY src ./src
> RUN mvn clean package -DskipTests               # Produces target/*.jar
>
> # Stage 2: RUN environment (this is the final image)
> FROM amazoncorretto:21-alpine                    # 200MB image
> COPY --from=build /target/*.jar app.jar          # Only the .jar from stage 1!
> ENTRYPOINT ["java", "-jar", "app.jar"]
> # Stage 1 image is discarded — never shipped to production!
> ```

**Q: What is the difference between a Docker image and a container?**
> **A:** An **image** is a read-only template containing the application, runtime, libraries, and environment. A **container** is a running instance of an image with a writable layer. Analogy: Image = Class definition, Container = Object instance. You can create multiple containers from one image.
>
> ```bash
> # IMAGE = blueprint (read-only)
> docker build -t patient-service:latest .    # Creates an IMAGE
>
> # CONTAINER = running instance (writable)
> docker run -p 4000:4000 patient-service:latest   # Creates a CONTAINER from image
> docker run -p 4001:4000 patient-service:latest   # Creates ANOTHER container (same image!)
>
> # C++ analogy:   Image = class Patient { ... };    
> #                Container = Patient p1;  Patient p2;  (instances)
> # Java analogy:  Image = Patient.class
> #                Container = new Patient();  new Patient();  (objects)
> ```

**Q: What is Docker Compose?**
> **A:** A tool for defining and running multi-container Docker applications using a YAML file. In a microservices project like mine, you'd use docker-compose.yml to define all 5 services + databases + Kafka, and start everything with one command: `docker-compose up`.
>
> ```yaml
> # docker-compose.yml (example for your project):
> services:
>   patient-service:
>     build: ./patient-service
>     ports: ["4000:4000"]
>     depends_on: [kafka, billing-service]
>
>   billing-service:
>     build: ./billing-service
>     ports: ["4001:4001", "9001:9001"]
>
>   auth-service:
>     build: ./auth-service
>     ports: ["4005:4005"]
>
>   api-gateway:
>     build: ./api-gateway
>     ports: ["4004:4004"]
>     depends_on: [auth-service, patient-service]
>
>   analytics-service:
>     build: ./analytics-service
>     ports: ["4002:4002"]
>     depends_on: [kafka]
>
>   kafka:
>     image: confluentinc/cp-kafka:latest
>     ports: ["9092:9092"]
>
> # One command starts EVERYTHING:
> # docker-compose up -d
> # Containers communicate by service name: http://auth-service:4005
> ```

---

## 26. Maven — Build Tool

### What Is Maven?

Maven is a build automation tool for Java projects. It:
1. Downloads libraries (dependencies) from the internet
2. Compiles your code
3. Runs tests
4. Packages your app into a `.jar` file

Think of it like `pip` (Python) + `make` (C/C++) combined.

### pom.xml — Project Object Model

```xml
<project>
    <!-- Parent: Spring Boot manages common dependency versions -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.1</version>
    </parent>

    <!-- Your project's identity -->
    <groupId>com.pm</groupId>            <!-- Organization -->
    <artifactId>patient-service</artifactId>  <!-- Project name -->
    <version>0.0.1-SNAPSHOT</version>     <!-- Version -->

    <properties>
        <java.version>21</java.version>   <!-- Java version -->
    </properties>

    <dependencies>
        <!-- Each dependency is a library your project needs -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!-- No version needed — managed by parent! -->
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>  <!-- Only needed when running, not compiling -->
        </dependency>
    </dependencies>
</project>
```

### Dependency Scope

| Scope | When Available | Example |
|-------|---------------|---------|
| `compile` (default) | Compile + Runtime + Test | spring-boot-starter-web |
| `runtime` | Runtime + Test (not compile) | postgresql driver |
| `test` | Only during tests | spring-boot-starter-test |
| `provided` | Compile (not packaged in jar) | annotations-api |

### Multi-Module Project

Your root `pom.xml`:
```xml
<packaging>pom</packaging>  <!-- This is a parent project, not a build -->
<modules>
    <module>patient-service</module>  <!-- Sub-module -->
</modules>
```

### Interview Q&A

**Q: What is Maven? What is pom.xml?**
> **A:** Maven is a build tool and dependency manager for Java. pom.xml (Project Object Model) is the configuration file that defines the project's dependencies, build process, and metadata. Maven downloads dependencies from Maven Central Repository, compiles code, runs tests, and packages the application. The parent POM (`spring-boot-starter-parent`) provides default configurations and dependency version management.
>
> ```xml
> <!-- From patient-service/pom.xml: -->
> <parent>
>     <artifactId>spring-boot-starter-parent</artifactId>
>     <version>3.4.1</version>
>     <!-- This parent POM defines versions for 300+ libraries!
>          So you don't need <version> on most dependencies -->
> </parent>
>
> <dependencies>
>     <dependency>
>         <groupId>org.springframework.boot</groupId>
>         <artifactId>spring-boot-starter-web</artifactId>
>         <!-- No <version> needed! Parent manages it. -->
>     </dependency>
> </dependencies>
> ```
> ```bash
> # Maven commands:
> mvn clean package    # Delete old build → compile → test → create .jar
> mvn clean install    # Same as above + install to local .m2 repository
> mvn test              # Run only tests
> # Equivalent in C++: cmake --build . && ctest
> ```

**Q: What is a SNAPSHOT version?**
> **A:** `0.0.1-SNAPSHOT` means this is a development version. SNAPSHOT versions are mutable (can change). When you're ready for release, you remove `-SNAPSHOT` to create an immutable release version like `1.0.0`.
>
> ```xml
> <!-- Development (your project): -->
> <version>0.0.1-SNAPSHOT</version>
> <!-- SNAPSHOT = "work in progress". Maven re-downloads latest on every build.
>    You can publish 0.0.1-SNAPSHOT 100 times — it keeps updating. -->
>
> <!-- Release (production): -->
> <version>1.0.0</version>
> <!-- Once published, NEVER changes. Immutable. Like a git tag. -->
> ```

---

## 27. Logging with SLF4J

### What Is Logging?

Instead of `System.out.println()` (like `cout` in C++), Java uses a logging framework:

```java
private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

log.info("Connecting to Billing Service at {}:{}", serverAddress, serverPort);
// Output: 2024-01-15 10:23:45.123  INFO  BillingServiceGrpcClient : Connecting to Billing Service at localhost:9001

log.warn("Email already exists: {}", email);
// Output: 2024-01-15 10:23:46.456  WARN  GlobalExceptionHandler : Email already exists: john@test.com

log.error("Failed to call gRPC: status={}", e.getStatus().getCode());
// Output: 2024-01-15 10:23:47.789  ERROR BillingServiceGrpcClient : Failed to call gRPC: status=UNAVAILABLE
```

### Log Levels (in increasing severity)

| Level | When to Use | Example in Your Project |
|-------|------------|------------------------|
| `TRACE` | Very detailed debugging | (not used) |
| `DEBUG` | Detailed for developers | (not used) |
| `INFO` | Normal operation events | "Connecting to Billing Service", "Received Patient Event" |
| `WARN` | Potential problems | "Email already exists", "Patient not found" |
| `ERROR` | Things that broke | "gRPC service call failed", "Error sending Kafka event" |

### Why Not `System.out.println()`?

1. No log levels — can't filter what shows
2. No timestamps
3. No class names
4. Can't redirect to files
5. No performance optimization (logging can be disabled per level)

### Interview Q&A

**Q: What logging framework do you use?**
> **A:** SLF4J (Simple Logging Facade for Java) as the API, with Logback (included by Spring Boot) as the implementation. SLF4J is a facade — it lets you switch logging implementations without changing your code. The `{}` placeholders are evaluated lazily (only if that log level is enabled), which is more efficient than string concatenation.
>
> ```java
> // From BillingServiceGrpcClient.java:
> private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
>
> // GOOD: Lazy evaluation with {} placeholders
> log.info("Connecting to Billing Service at {}:{}", serverAddress, serverPort);
> // If INFO level is disabled → string is NEVER constructed (fast!)
>
> // BAD: String concatenation (ALWAYS evaluated, even if logging is off)
> log.info("Connecting to Billing Service at " + serverAddress + ":" + serverPort);
> // Creates the string even if INFO is disabled → wastes CPU
>
> // WORST: System.out.println (no levels, no timestamps, no class names)
> System.out.println("Connecting...");
> // Output: Connecting...
>
> // vs SLF4J output:
> // 2024-01-15 10:23:45.123 INFO BillingServiceGrpcClient : Connecting to Billing Service at localhost:9001
> //            ↑ timestamp      ↑ level  ↑ class name          ↑ message
> ```

---

## 28. Java Streams & Functional Programming

### What Are Streams?

Streams let you process collections declaratively (like SQL queries on data in memory):

```java
// C++ way:
vector<PatientResponseDTO> result;
for (Patient p : patients) {
    result.push_back(PatientMapper.toDto(p));
}

// Java Streams way (used in your project):
List<PatientResponseDTO> result = patients.stream()
    .map(PatientMapper::toDto)    // Transform each Patient → PatientResponseDTO
    .toList();                     // Collect results into a List
```

### Streams Used in Your Project

**1. Map operation (transform each element):**
```java
// PatientService.getPatients()
patients.stream()
    .map(PatientMapper::toDto)  // Method reference — same as: .map(p -> PatientMapper.toDto(p))
    .toList();
```

**2. Filter + Map chain:**
```java
// AuthService.authenticate()
userService.findByEmail(loginRequestDTO.getEmail())     // Optional<User>
    .filter(u -> passwordEncoder.matches(               // Keep only if password matches
        loginRequestDTO.getPassword(), u.getPassword()))
    .map(u -> jwUtil.generateToken(u.getEmail(), u.getRole()));  // Generate token
```

**3. ForEach:**
```java
// GlobalExceptionHandler
ex.getBindingResult().getFieldErrors()
    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
```

### Lambda Expressions

```java
// Full lambda
(u) -> { return passwordEncoder.matches(password, u.getPassword()); }

// Simplified (single expression, no braces needed, return is implicit)
u -> passwordEncoder.matches(password, u.getPassword())

// Method reference (when lambda just calls a single method)
PatientMapper::toDto    // Same as: patient -> PatientMapper.toDto(patient)
```

### Interview Q&A

**Q: What are Java Streams?**
> **A:** Streams are a functional programming API (since Java 8) for processing sequences of elements. They support operations like `map` (transform), `filter` (select), `reduce` (aggregate), `collect` (gather results). Streams are lazy (operations are chained but only execute when a terminal operation like `toList()` is called) and can be parallelized easily. In my project, I use streams to convert lists of Patient entities to DTOs.
>
> ```java
> // From PatientService.getPatients():
> List<PatientResponseDTO> result = patientRepository.findAll()  // [Patient, Patient, Patient]
>     .stream()                    // Create a stream pipeline
>     .map(PatientMapper::toDto)   // Transform each: Patient → PatientResponseDTO
>     .toList();                   // Terminal operation → executes the pipeline
>
> // Lazy evaluation: .map() doesn't run until .toList() is called!
> // Stream equivalent of SQL: SELECT toDto(p) FROM patients p
>
> // From AuthService.authenticate():
> Optional<String> token = userService.findByEmail(email)  // Optional<User>
>     .filter(u -> passwordEncoder.matches(password, u.getPassword()))  // keep if match
>     .map(u -> jwUtil.generateToken(u.getEmail(), u.getRole()));       // User → token
> // Chain of: find → filter → transform (all in one readable pipeline)
> ```

**Q: What is a lambda expression?**
> **A:** A concise way to represent an anonymous function. `u -> passwordEncoder.matches(...)` is a lambda that takes parameter `u` and returns the result. Lambdas implement functional interfaces (interfaces with exactly one abstract method). They're Java's equivalent of function pointers in C++.
>
> ```java
> // C++ equivalent of a lambda:
> auto matches = [&](User u) { return passwordEncoder.matches(password, u.getPassword()); };
>
> // Java lambda (from AuthService):
> .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
> //       ↑ parameter    ↑ body (single expression = implicit return)
>
> // Full form vs short form:
> .filter((User u) -> { return passwordEncoder.matches(password, u.getPassword()); })  // Full
> .filter(u -> passwordEncoder.matches(password, u.getPassword()))                      // Short
> // Both are identical — Java infers the type and adds implicit return
> ```

**Q: What is a method reference (`::` operator)?**
> **A:** A shorthand for a lambda that calls a single method. `PatientMapper::toDto` is equivalent to `patient -> PatientMapper.toDto(patient)`. Types: (1) Static method: `ClassName::method`. (2) Instance method: `object::method`. (3) Constructor: `ClassName::new`.
>
> ```java
> // ALL of these are equivalent:
> patients.stream().map(patient -> PatientMapper.toDto(patient))  // Lambda
> patients.stream().map(PatientMapper::toDto)                      // Method reference
>
> // Types of method references from your project:
> // 1. Static method:   PatientMapper::toDto       → calls PatientMapper.toDto(patient)
> // 2. Instance method:  error::getField            → calls error.getField()
> // 3. Constructor:      ArrayList::new             → calls new ArrayList()
>
> // From GlobalExceptionHandler:
> ex.getBindingResult().getFieldErrors()
>     .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
> //          ↑ Can't use method reference here — calling TWO methods on 'error'
> ```

---

## 29. Optional in Java

### What Is Optional?

A container that may or may not contain a value. It replaces `null` checks:

```java
// C++ way (dangerous):
User* user = findByEmail("test@test.com");
if (user != nullptr) {
    // use user
}
// If you forget the null check → segfault!

// Java Optional way (safe):
Optional<User> userOpt = userRepository.findByEmail("test@test.com");
// You're FORCED to handle the absence case
```

### Used in Your Project

```java
// UserRepository
Optional<User> findByEmail(String email);

// AuthService
Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
    .filter(u -> passwordEncoder.matches(...))  // Optional still empty if no match
    .map(u -> jwUtil.generateToken(...));        // Transform User → String token

if (tokenOptional.isEmpty()) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}
String token = tokenOptional.get();  // Safe to get — we checked first

// PatientService
Patient patient = patientRepository.findById(id)
    .orElseThrow(() -> new PatientNotFoundException("Patient not found", id));
    // If Optional is empty → throw exception
    // If Optional has value → unwrap and return
```

### Common Optional Methods

| Method | What It Does |
|--------|-------------|
| `Optional.of(value)` | Create Optional with non-null value |
| `Optional.empty()` | Create empty Optional |
| `optional.isPresent()` | Returns true if value exists |
| `optional.isEmpty()` | Returns true if empty |
| `optional.get()` | Get the value (throws if empty!) |
| `optional.orElse(default)` | Get value or return default |
| `optional.orElseThrow(...)` | Get value or throw exception |
| `optional.map(fn)` | Transform if present |
| `optional.filter(pred)` | Keep if predicate matches |

### Interview Q&A

**Q: What is Optional in Java? Why use it?**
> **A:** `Optional<T>` is a container that may or may not hold a non-null value. It prevents `NullPointerException` by making the possibility of absence explicit. In my project, `findByEmail()` returns `Optional<User>` instead of `User` — the caller is forced to handle the case where no user exists, using methods like `orElseThrow()`, `map()`, or `isEmpty()`.

---

## 30. Generics in Java

### What Are Generics?

Like C++ templates — allow classes/methods to work with any type while maintaining type safety:

```java
// Without generics:
List list = new ArrayList();
list.add("hello");
list.add(123);              // No compile error!
String s = (String) list.get(1);  // CRASH at runtime — it's an Integer!

// With generics:
List<String> list = new ArrayList<>();
list.add("hello");
list.add(123);              // COMPILE ERROR! Type safety at compile time.
```

### Generics in Your Project

```java
// JpaRepository<EntityType, PrimaryKeyType>
public interface PatientRepository extends JpaRepository<Patient, UUID> { }

// ResponseEntity<BodyType>
ResponseEntity<List<PatientResponseDTO>>   // Response body is a list of DTOs
ResponseEntity<PatientResponseDTO>          // Response body is a single DTO
ResponseEntity<Map<String, String>>         // Response body is a map
ResponseEntity<Void>                        // No body

// KafkaTemplate<KeyType, ValueType>
KafkaTemplate<String, byte[]>              // String keys, byte array values

// Optional<ValueType>
Optional<User>                             // May or may not contain a User
Optional<String>                           // May or may not contain a String
```

### Interview Q&A

**Q: What are generics in Java?**
> **A:** Generics enable types (classes, interfaces, methods) to be parameterized with type arguments, providing compile-time type safety and eliminating the need for casts. `JpaRepository<Patient, UUID>` means "a repository for Patient entities with UUID primary keys." Without generics, `findById()` would return `Object`, requiring manual casting and risking `ClassCastException`.
>
> ```java
> // Generics in YOUR project — every one is parameterized:
> JpaRepository<Patient, UUID>          // Entity=Patient, PK=UUID
> ResponseEntity<List<PatientResponseDTO>>  // Body=List of DTOs
> KafkaTemplate<String, byte[]>         // Key=String, Value=byte[]
> Optional<User>                         // May contain a User
>
> // WITHOUT generics (dangerous):
> JpaRepository repo = ...;
> Object result = repo.findById(uuid);     // Returns Object!
> Patient p = (Patient) result;             // Manual cast — could crash at runtime!
>
> // WITH generics (safe):
> JpaRepository<Patient, UUID> repo = ...;
> Optional<Patient> result = repo.findById(uuid); // Correct type automatically!
> // No casting needed. Wrong type = COMPILE error, not runtime crash.
>
> // C++ comparison: Java generics = C++ templates
> // vector<Patient> in C++ ≈ List<Patient> in Java
> ```

**Q: What is type erasure?**
> **A:** Unlike C++ templates (which generate different code for each type), Java generics are erased at runtime. `List<String>` and `List<Integer>` become the same `List` at runtime. This means you can't do `new T()` or `instanceof List<String>`. This is a key Java-C++ difference.
>
> ```java
> // C++ templates: DIFFERENT code generated for each type
> // vector<int> and vector<string> are DIFFERENT classes at runtime
>
> // Java generics: SAME code at runtime (type info erased)
> List<Patient> patients = new ArrayList<>();
> List<User> users = new ArrayList<>();
> patients.getClass() == users.getClass();  // TRUE! Both are just ArrayList at runtime
>
> // What the compiler does:
> // Your code:    Optional<Patient> opt = repo.findById(uuid);
> // After erasure: Optional opt = repo.findById(uuid);  // <Patient> is gone!
>
> // This is why you CAN'T do:
> // if (list instanceof List<Patient>) { }  // COMPILE ERROR — no type info at runtime
> // T obj = new T();                        // COMPILE ERROR — T doesn't exist at runtime
> ```

---

## 31. Interfaces in Java

### What Is an Interface?

A contract that defines what methods a class must implement:

```java
// C++ equivalent:
class Printable {
public:
    virtual void print() = 0;  // Pure virtual function (abstract)
};

// Java:
public interface Printable {
    void print();  // Abstract by default
}
```

### Interfaces in Your Project

**1. Repository Interface:**
```java
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    // Spring DATA generates the implementation! You never write SQL.
}
```

**2. Marker Interface (Validation Group):**
```java
public interface CreatePatientValidationGroup {
    // No methods! It's just a "tag" to group validations
}
```

**3. Functional Interfaces (used by lambdas):**
```java
// GatewayFilter is a functional interface — has one abstract method
// So you can implement it with a lambda:
return ((exchange, chain) -> { ... });
```

### Interview Q&A

**Q: Can an interface have method implementations?**
> **A:** Yes, since Java 8! Interfaces can have `default` methods (with implementation) and `static` methods. This allows adding methods to interfaces without breaking existing implementations. `JpaRepository` uses default methods extensively.
>
> ```java
> // Example: JpaRepository already provides implementations via defaults:
> public interface JpaRepository<T, ID> extends ... {
>     List<T> findAll();       // Implemented by Spring (not default, but generated)
>     Optional<T> findById(ID id);
>     T save(T entity);
>     void deleteById(ID id);
>     // You get 15+ methods WITHOUT writing a single line of code!
> }
>
> // Your PatientRepository adds ONE custom method:
> public interface PatientRepository extends JpaRepository<Patient, UUID> {
>     boolean existsByEmail(String email);  // Spring auto-implements from method name!
> }
>
> // Java 8 default method example:
> public interface Loggable {
>     default void log(String msg) {  // Has a body! Not abstract.
>         System.out.println(this.getClass().getSimpleName() + ": " + msg);
>     }
> }
> ```

**Q: Why use interfaces instead of abstract classes?**
> **A:** (1) A class can implement multiple interfaces but extend only one class. (2) Interfaces define "what" (contract), abstract classes define "partially how" (partial implementation). (3) Used for loose coupling — code depends on the interface, not the concrete class. In my project, `PatientRepository` is an interface — Spring generates the implementation class at runtime.
>
> ```java
> // YOUR PROJECT: PatientRepository is just an INTERFACE
> public interface PatientRepository extends JpaRepository<Patient, UUID> {
>     boolean existsByEmail(String email);
> }
> // You NEVER write an implementation class!
> // Spring creates a PROXY class at runtime that implements all methods.
> // You code against the INTERFACE — you never know the actual class name.
>
> // Why this is powerful:
> // PatientService depends on PatientRepository interface:
> private final PatientRepository patientRepository;  // Interface!
> // Spring could inject:
> //   → H2PatientRepository (for dev)
> //   → PostgresPatientRepository (for prod)
> //   → MockPatientRepository (for tests)
> // PatientService doesn't care — it only knows the interface!
> ```

---

## 32. Design Patterns Used

### 1. Repository Pattern
**Where:** `PatientRepository`, `UserRepository`
**What:** Separates data access logic from business logic. The service layer doesn't know if data comes from H2, PostgreSQL, MongoDB, or a file — it just calls `repository.findAll()`.

### 2. DTO (Data Transfer Object) Pattern
**Where:** `PatientRequestDTO`, `PatientResponseDTO`, `LoginRequestDTO`, `LoginResponseDTO`
**What:** Separate objects for data transfer between layers/systems. Prevents exposing internal entity structure.

### 3. Builder Pattern
**Where:** Protobuf generated classes, `ResponseEntity`
**What:** Step-by-step object construction for complex objects:
```java
BillingRequest.newBuilder()
    .setPatientId(patientId)
    .setName(name)
    .setEmail(email)
    .build();
```

### 4. Factory Pattern
**Where:** `AbstractGatewayFilterFactory`
**What:** The gateway filter factory creates filter instances. The framework calls `apply()` to get a filter — you don't create it directly.

### 5. Observer Pattern
**Where:** `StreamObserver` in gRPC, Kafka Producer/Consumer
**What:** An object (observer) subscribes to events from another object (subject). `responseObserver.onNext(response)` notifies the observer. Kafka consumers observe topic messages.

### 6. Mapper Pattern
**Where:** `PatientMapper`
**What:** Converts between different object representations (Entity ↔ DTO).

### 7. Singleton Pattern
**Where:** All Spring beans (default scope)
**What:** Only ONE instance of each bean exists in the application. `PatientService`, `PatientRepository`, etc., are all singletons managed by Spring.

### 8. Proxy Pattern
**Where:** Spring Data JPA repositories, gRPC stubs
**What:** `PatientRepository` is an interface — Spring creates a proxy class at runtime that implements the interface and handles database calls. `BillingServiceBlockingStub` is a proxy for remote gRPC calls.

### Interview Q&A

**Q: What design patterns are used in your project?**
> **A:** (1) **Repository** — separates data access (PatientRepository). (2) **DTO** — separates API contract from domain model. (3) **Builder** — protobuf objects and ResponseEntity use builder pattern. (4) **Singleton** — all Spring beans are singletons by default. (5) **Observer** — gRPC StreamObserver and Kafka pub/sub. (6) **Proxy** — Spring generates runtime proxies for JPA repositories. (7) **Factory** — Gateway filter factory creates filters.
>
> ```java
> // 1. REPOSITORY: PatientRepository — hides DB details from service
> public interface PatientRepository extends JpaRepository<Patient, UUID> { }
>
> // 2. DTO: Separate objects for API vs database
> PatientRequestDTO request = ...;    // What client SENDS
> Patient entity = ...;               // What DB STORES
> PatientResponseDTO response = ...;  // What client RECEIVES
>
> // 3. BUILDER: Step-by-step construction (Protobuf)
> BillingRequest.newBuilder().setPatientId("123").setName("John").build();
>
> // 4. SINGLETON: ONE instance per bean
> @Service  // Spring creates exactly ONE PatientService for the entire app
> public class PatientService { }
>
> // 5. OBSERVER: Kafka consumer "observes" topic for events
> @KafkaListener(topics = "patient")  // Notified when new message arrives
> public void consumeEvent(byte[] event) { ... }
>
> // 6. PROXY: PatientRepository is an interface — Spring creates implementation at runtime
> // 7. FACTORY: JwtValidationGatewayFilterFactory.apply() creates filter instances
> ```

**Q: What is the Builder pattern? Where is it used?**
> **A:** Builder separates object construction from representation, allowing step-by-step creation of complex objects. In my project, Protocol Buffers uses it: `BillingRequest.newBuilder().setPatientId("123").setName("John").build()`. The object is immutable after `build()` is called. This is better than a constructor with many parameters because it's readable and allows optional fields.
>
> ```java
> // From BillingServiceGrpcClient.java — Builder pattern (Protobuf):
> BillingRequest request = BillingRequest.newBuilder()
>     .setPatientId(savedPatient.getId().toString())  // Step 1
>     .setName(savedPatient.getName())                 // Step 2
>     .setEmail(savedPatient.getEmail())               // Step 3
>     .build();                                         // Finalize! Object is now IMMUTABLE.
>
> // Why not a constructor?
> // new BillingRequest("abc", "John", "john@test.com")  // Which param is which??
> // Builder is self-documenting: .setName("John") — you KNOW what it is.
>
> // ResponseEntity also uses builder:
> return ResponseEntity.ok().body(patients);           // 200 + body
> return ResponseEntity.status(HttpStatus.CREATED).body(dto);  // 201 + body
> return ResponseEntity.noContent().build();           // 204, no body
> ```

---

## 33. System Design Concepts

### 1. Synchronous vs Asynchronous Communication

```
Synchronous (REST, gRPC):
  A calls B → A WAITS → B responds → A continues
  Used: Patient → Billing (must create billing before responding to user)

Asynchronous (Kafka):
  A sends message → A continues immediately → B processes later
  Used: Patient → Analytics (analytics can process whenever)
```

### 2. Tight Coupling vs Loose Coupling

```
Tight Coupling:
  PatientService directly creates BillingService objects
  → If BillingService changes, PatientService must change too

Loose Coupling (your project):
  PatientService depends on BillingServiceGrpcClient (abstraction)
  → If billing changes internally, client doesn't need to know
  
  PatientService → Kafka → Analytics (don't even know each other!)
```

### 3. Vertical vs Horizontal Scaling

```
Vertical Scaling: Make one server bigger (more CPU, RAM)
  Limit: One machine can only be so big

Horizontal Scaling: Add more servers (multiple instances)
  With microservices: Run 5 instances of patient-service
  → API Gateway distributes requests across them (load balancing)
```

### 4. Single Point of Failure

The API Gateway is a single point of failure — if it goes down, nothing works. In production:
- Run multiple gateway instances behind a load balancer
- Use Kubernetes for automatic failover

### 5. Event-Driven Architecture

Your project uses this with Kafka:
```
Event: "Patient Created"
  → Analytics service logs it
  → (Future) Notification service emails the patient
  → (Future) Insurance service creates a claim
  → All without patient-service knowing about them!
```

### 6. CAP Theorem

| Property | Meaning | Your Project |
|----------|---------|-------------|
| **Consistency** | All nodes see the same data | Each service has its own DB |
| **Availability** | System responds to every request | Kafka ensures no message loss |
| **Partition Tolerance** | Works despite network failures | Microservices tolerate service failures |

In distributed systems, you can only guarantee 2 of 3. Your project favors AP (Availability + Partition Tolerance) with eventual consistency via Kafka.

### Interview Q&A

**Q: What is eventual consistency?**
> **A:** In a distributed system, after a write, all reads will *eventually* return the updated value, but not necessarily immediately. In my project, when a patient is created, the analytics service doesn't get the event instantly — there's a delay through Kafka. Eventually, analytics catches up. This is a trade-off for availability and partition tolerance.
>
> ```java
> // Timeline example in your project:
> // T=0ms:  POST /patients → PatientService creates patient in DB
> // T=5ms:  kafkaTemplate.send("patient", event)  → sent to Kafka broker
> // T=10ms: Response sent to client: {"id": "abc", "name": "John"}
> //         Client sees: patient created!
> //
> // T=50ms: Kafka delivers message to analytics-service consumer
> // T=55ms: KafkaConsumer.consumeEvent() processes the event
> //         Analytics service NOW knows about the patient
> //
> // Between T=10ms and T=55ms → INCONSISTENCY!
> // Patient exists in patient DB, but analytics doesn't know about it yet.
> // This is "eventual consistency" — analytics will EVENTUALLY catch up.
> ```

**Q: What happens if the billing service is down when creating a patient?**
> **A:** Since gRPC is synchronous, the patient creation will fail with an error. The `GlobalExceptionHandler` catches `StatusRuntimeException` and returns a 503 (Service Unavailable) to the client. The patient is NOT saved (the exception occurs before success). This is a deliberate design choice — billing account is required for a patient.
>
> ```java
> // What happens step by step:
> // PatientService.createPatient():
> Patient savedPatient = patientRepository.save(patient);       // ✅ Patient saved to DB
> billingServiceGrpcClient.createBillingAccount(saved...);      // ❌ gRPC call FAILS!
> // ↑ Throws StatusRuntimeException(UNAVAILABLE)
> // Exception propagates up → GlobalExceptionHandler catches it?
> //
> // Problem: Patient is saved but billing account doesn't exist!
> // Fix would be @Transactional:
> @Transactional  // If ANY exception occurs, ROLLBACK the patient save
> public PatientResponseDTO createPatient(PatientRequestDTO dto) {
>     Patient saved = patientRepository.save(patient);  // Part of transaction
>     billingClient.createBillingAccount(...);           // If this fails → rollback save
>     kafkaProducer.sendEvent(saved);                    // If this fails → rollback all
>     return PatientMapper.toDto(saved);
> }
> ```

**Q: What happens if the analytics service is down when creating a patient?**
> **A:** The patient is still created successfully! Kafka stores the event, and when analytics comes back up, it reads the message from where it left off (using offsets). This is the benefit of asynchronous communication with a persistent message broker.
>
> ```java
> // Step-by-step when analytics is DOWN:
> // PatientService.createPatient():
> Patient saved = patientRepository.save(patient);     // ✅ Saved to DB
> billingClient.createBillingAccount(saved...);         // ✅ Billing created
> kafkaProducer.sendEvent(saved);                       // ✅ Sent to Kafka BROKER
> return PatientMapper.toDto(saved);                    // ✅ Client gets response!
>
> // The Kafka message sits in the "patient" topic on the BROKER.
> // Analytics service is down → nobody reads it. But it's SAFE in Kafka.
>
> // Later, analytics-service comes back up:
> @KafkaListener(topics = "patient", groupId = "analytics-service")
> public void consumeEvent(byte[] event) {
>     // Kafka tells consumer: "you last read offset 42, here's offset 43"
>     // Consumer picks up RIGHT where it left off!
>     PatientEvent patientEvent = PatientEvent.parseFrom(event);
>     log.info("Received Patient Event: {}", patientEvent.getPatientId());
> }
> // This is why Kafka > REST for analytics: resilience to downtime!
> ```

**Q: How would you handle a scenario where billing succeeds but Kafka fails?**
> **A:** This is a distributed transaction problem. Options: (1) **Saga Pattern** — compensating transactions (delete the billing account if Kafka fails). (2) **Outbox Pattern** — write the event to a local database table (outbox), then a separate process sends it to Kafka. (3) **Accept eventual consistency** — retry Kafka send with a dead-letter queue for failures.
>
> ```java
> // CURRENT CODE (has the problem):
> Patient saved = patientRepository.save(patient);  // ✅ Saved
> billingClient.createBillingAccount(saved...);      // ✅ Billing created
> kafkaProducer.sendEvent(saved);                    // ❌ Kafka fails!
> // Result: Patient + Billing exist, but Analytics never gets notified!
>
> // FIX 1: OUTBOX PATTERN (most reliable):
> @Transactional
> public PatientResponseDTO createPatient(PatientRequestDTO dto) {
>     Patient saved = patientRepository.save(patient);
>     billingClient.createBillingAccount(saved...);
>     // Instead of Kafka directly, save event to DB table:
>     outboxRepository.save(new OutboxEvent("patient", event.toByteArray()));
>     // A background job polls outbox table and sends to Kafka.
>     // If Kafka fails, the job retries. If app crashes, event is in DB.
> }
>
> // FIX 2: SAGA PATTERN (compensating transactions):
> try {
>     billingClient.createBillingAccount(saved);
>     kafkaProducer.sendEvent(saved);
> } catch (KafkaException e) {
>     billingClient.deleteBillingAccount(saved.getId()); // Undo billing!
>     patientRepository.delete(saved);                    // Undo patient!
>     throw new RuntimeException("Patient creation rolled back");
> }
> ```

---

## 34. SOLID Principles

### S — Single Responsibility Principle

Each class has ONE reason to change.

| Class | Responsibility |
|-------|---------------|
| `PatientController` | Handle HTTP requests |
| `PatientService` | Business logic |
| `PatientRepository` | Database access |
| `PatientMapper` | DTO ↔ Entity conversion |
| `GlobalExceptionHandler` | Error response formatting |
| `KafkaProducer` | Event publishing |
| `BillingServiceGrpcClient` | External service communication |

### O — Open/Closed Principle

Open for extension, closed for modification.

**Example:** `GlobalExceptionHandler` — to handle a new exception type, you ADD a new `@ExceptionHandler` method. You don't modify existing handlers.

**Example:** `JpaRepository` — Spring added methods like `findAll()`, `save()`, `delete()`. You extend it without modifying it.

### L — Liskov Substitution Principle

Subtypes must be substitutable for their base types.

**Example:** `EmailAlreadyExistsException` extends `RuntimeException`. Anywhere a `RuntimeException` is expected, your custom exception works correctly.

### I — Interface Segregation Principle

Don't force clients to depend on interfaces they don't use.

**Example:** `PatientRepository` extends `JpaRepository` which provides exactly the methods needed. Validation group `CreatePatientValidationGroup` is a separate interface so update operations don't need it.

### D — Dependency Inversion Principle

Depend on abstractions, not concretions.

**Example:**
```java
// AuthService doesn't depend on BCryptPasswordEncoder (concrete class)
// It depends on PasswordEncoder (interface)
private final PasswordEncoder passwordEncoder;
// Tomorrow you can switch to Argon2PasswordEncoder without changing AuthService!
```

### Interview Q&A

**Q: Explain SOLID principles with examples from your project.**
> **A:** Here's each SOLID principle with concrete code from my project:
>
> ```java
> // S — SINGLE RESPONSIBILITY: Each class has ONE job
> PatientController  → handles HTTP requests ONLY
> PatientService     → business logic ONLY
> PatientRepository  → database access ONLY
> PatientMapper      → DTO ↔ Entity conversion ONLY
> KafkaProducer      → event publishing ONLY
> // If DB changes → only Repository changes. Controller doesn't care.
>
> // O — OPEN/CLOSED: Open for extension, closed for modification
> @ControllerAdvice
> public class GlobalExceptionHandler {
>     @ExceptionHandler(PatientNotFoundException.class)    // Handler 1
>     @ExceptionHandler(EmailAlreadyExistsException.class) // Handler 2
>     // To add a new exception type → ADD a new method. Don't modify existing ones!
>     @ExceptionHandler(BillingException.class)            // Just add this! ← EXTENSION
> }
>
> // L — LISKOV SUBSTITUTION: Subtypes replace parent types
> // Anywhere RuntimeException is expected, our custom exceptions work:
> catch (RuntimeException e) {  // This catches BOTH:
>     // PatientNotFoundException (extends RuntimeException) ✓
>     // EmailAlreadyExistsException (extends RuntimeException) ✓
> }
>
> // I — INTERFACE SEGREGATION: Don't force unused methods
> // PatientRepository only extends JpaRepository (CRUD methods it needs)
> // It does NOT implement a giant "EverythingRepository" interface
> // CreatePatientValidationGroup is a separate validation group interface 
> //   → update operations don't need to implement it
>
> // D — DEPENDENCY INVERSION: Depend on abstractions
> @Service
> public class AuthService {
>     private final PasswordEncoder passwordEncoder;  // INTERFACE, not BCryptPasswordEncoder!
>     // Tomorrow: switch to Argon2PasswordEncoder → ZERO changes to AuthService
> }
> ```

---

## 35. Complete Functionality Flows — Every Feature Line-by-Line

This section walks you through **every single operation** in the project — what happens internally at each step, which file runs, which line executes, what data looks like at each stage, and how the classes talk to each other.

---

### FLOW 1: Application Startup — What Happens When You Run the Project?

When you run `PatientServiceApplication.main()`, a LOT happens behind the scenes:

```
Step 1: main() calls SpringApplication.run()
   └─ File: PatientServiceApplication.java
   └─ Code: SpringApplication.run(PatientServiceApplication.class, args);

Step 2: Spring creates the ApplicationContext (IoC Container)
   └─ This is the "brain" — it will manage all objects (beans)

Step 3: Component Scan begins
   └─ Spring scans package `com.pm.patientservice` and ALL sub-packages
   └─ Finds classes annotated with:
       @RestController → PatientController
       @Service        → PatientService, BillingServiceGrpcClient, KafkaProducer
       @Repository     → PatientRepository

Step 4: Auto-Configuration kicks in
   └─ Sees `spring-boot-starter-web` → Starts embedded Tomcat on port 4000
   └─ Sees `spring-boot-starter-data-jpa` + `h2` → Creates in-memory H2 database
   └─ Sees `spring-kafka` → Creates KafkaTemplate bean
   └─ Reads application.properties for custom settings

Step 5: Bean Creation & Dependency Injection
   └─ Creates PatientRepository (Spring generates implementation class at runtime)
   └─ Creates BillingServiceGrpcClient:
       - Reads billing.service.address=localhost from properties
       - Reads billing.service.grpc.port=9001 from properties
       - Creates ManagedChannel to localhost:9001
       - Creates BillingServiceBlockingStub
   └─ Creates KafkaProducer:
       - Injects auto-configured KafkaTemplate<String, byte[]>
   └─ Creates PatientService:
       - Injects PatientRepository, BillingServiceGrpcClient, KafkaProducer
   └─ Creates PatientController:
       - Injects PatientService
   └─ Creates GlobalExceptionHandler (registered as global advice)

Step 6: Database Initialization
   └─ JPA/Hibernate reads @Entity classes → Creates table schema
   └─ Runs data.sql → Inserts seed data (10 sample patients)

Step 7: Application Ready!
   └─ Tomcat listening on port 4000
   └─ All REST endpoints registered:
       GET    /patients
       POST   /patients
       PUT    /patients/{id}
       DELETE /patients/{id}
   └─ Swagger UI available at /swagger-ui.html
```

**The same process happens for each microservice** (auth-service on port 4005, billing-service on port 4001, analytics-service on port 4002, api-gateway on port 4004).

**Key Insight:** You never called `new PatientService()` or `new PatientController()` anywhere. Spring did it all automatically through component scanning and dependency injection.

---

### FLOW 2: GET All Patients — `GET /patients`

This is the simplest flow. Let's trace every single step:

**Step 1: HTTP Request Arrives**
```
Client (Postman/Browser) sends:
GET http://localhost:4000/patients
Headers: Content-Type: application/json
Body: (none — GET requests don't have a body)
```

**Step 2: Tomcat receives the request**
```
Embedded Tomcat (running inside Spring Boot) receives the raw HTTP request.
Spring's DispatcherServlet takes over.
DispatcherServlet is the "front controller" — it looks at the URL and HTTP method
to find which controller method should handle this request.
```

**Step 3: DispatcherServlet matches the route**
```
URL: /patients   +   Method: GET
Matches: PatientController class (@RequestMapping("/patients"))
         + getPatients() method (@GetMapping)
```

**Step 4: PatientController.getPatients() executes**
```java
// File: PatientController.java, Line ~27
@GetMapping
public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    // Calls the service layer
    List<PatientResponseDTO> list = patientService.getPatients();
    // Wraps in ResponseEntity with 200 OK status
    return ResponseEntity.ok().body(list);
}
```

**Step 5: PatientService.getPatients() executes**
```java
// File: PatientService.java, Line ~30
public List<PatientResponseDTO> getPatients() {
    // Calls the repository layer
    List<Patient> patients = patientRepository.findAll();
    // findAll() is provided by JpaRepository — we never wrote this method!
    
    // Converts each Patient entity to PatientResponseDTO using streams
    return patients.stream()          // Create a stream from the list
        .map(PatientMapper::toDto)    // For each Patient, call PatientMapper.toDto()
        .toList();                     // Collect results back into a List
}
```

**Step 6: PatientRepository.findAll() executes**
```
Spring Data JPA's auto-generated implementation runs:
  → Hibernate creates SQL: SELECT id, name, email, address, date_of_birth, registered_date FROM patient
  → H2 database executes the query
  → Returns ResultSet with all rows
  → Hibernate maps each row to a Patient Java object:
      Row: {id=123e4567..., name="John Doe", email="john.doe@example.com", ...}
      Patient object: id=UUID, name="John Doe", email="john.doe@example.com", ...
  → Returns List<Patient> with all patients
```

**Step 7: PatientMapper.toDto() converts each entity**
```java
// File: PatientMapper.java
// Called ONCE for each patient in the list
public static PatientResponseDTO toDto(Patient patient) {
    PatientResponseDTO dto = new PatientResponseDTO();   // Create new DTO
    dto.setId(patient.getId().toString());               // UUID → "123e4567-e89b-..."
    dto.setName(patient.getName());                      // "John Doe"
    dto.setAddress(patient.getAddress());                // "123 Main St"
    dto.setEmail(patient.getEmail());                    // "john.doe@example.com"
    dto.setDateOfBirth(patient.getDateOfBirth().toString()); // LocalDate → "1985-06-15"
    return dto;
    // NOTE: registeredDate is NOT copied — client doesn't need it
}
```

**Step 8: Response sent back**
```
Spring takes the List<PatientResponseDTO> from ResponseEntity
Jackson (JSON library) converts it to JSON:

HTTP Response:
  Status: 200 OK
  Headers: Content-Type: application/json
  Body:
  [
    {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "name": "John Doe",
      "email": "john.doe@example.com",
      "address": "123 Main St, Springfield",
      "dateOfBirth": "1985-06-15"
    },
    {
      "id": "123e4567-e89b-12d3-a456-426614174001",
      "name": "Jane Smith",
      "email": "jane.smith@example.com",
      "address": "456 Elm St, Shelbyville",
      "dateOfBirth": "1990-09-23"
    }
    // ... more patients
  ]
```

**Complete data transformation:**
```
Database Row  →  Patient Entity  →  PatientResponseDTO  →  JSON
(SQL result)     (Java object)      (Java object)          (text sent to client)
```

---

### FLOW 3: CREATE a Patient — `POST /patients` (Most Complex Flow)

This is the **most important flow** — it touches ALL services, gRPC, and Kafka.

**Step 1: HTTP Request Arrives**
```
Client sends:
POST http://localhost:4000/patients
Headers: Content-Type: application/json
Body:
{
    "name": "Alice Johnson",
    "email": "alice@example.com",
    "address": "789 Oak St",
    "dateOfBirth": "1995-03-15",
    "registeredDate": "2024-01-20"
}
```

**Step 2: Spring deserializes JSON → PatientRequestDTO**
```
Jackson reads the JSON body.
Looks at PatientRequestDTO class → finds matching field names.
Calls setters:
  dto.setName("Alice Johnson")
  dto.setEmail("alice@example.com")
  dto.setAddress("789 Oak St")
  dto.setDateOfBirth("1995-03-15")
  dto.setRegisteredDate("2024-01-20")

Now we have a PatientRequestDTO object in memory.
```

**Step 3: Validation triggers (@Validated)**
```java
// File: PatientController.java, Line ~33
@PostMapping
public ResponseEntity<PatientResponseDTO> createPatient(
    @Validated({Default.class, CreatePatientValidationGroup.class})
    @RequestBody PatientRequestDTO patientRequestDTO) {
```
```
Spring sees @Validated → runs Bean Validation on the DTO.
Checks BOTH Default group AND CreatePatientValidationGroup:

✅ @NotBlank on name → "Alice Johnson" is not blank → PASS
✅ @Size(max=100) on name → 14 chars < 100 → PASS
✅ @NotBlank on email → "alice@example.com" is not blank → PASS
✅ @Email on email → valid format → PASS
✅ @NotBlank on address → "789 Oak St" is not blank → PASS
✅ @NotBlank on dateOfBirth → "1995-03-15" is not blank → PASS
✅ @NotBlank(groups=CreatePatientValidationGroup.class) on registeredDate
   → "2024-01-20" is not blank → PASS
   (This check ONLY runs because we included CreatePatientValidationGroup!)

All validations pass → proceed to controller method body.
```

**Step 3b: What if validation FAILS?**
```
If email was "not-an-email":
  → @Email validation fails
  → Spring throws MethodArgumentNotValidException
  → GlobalExceptionHandler.handleValidationException() catches it:

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(...) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

  → Client receives:
    Status: 400 Bad Request
    Body: { "email": "Email should be valid" }
  → Controller method NEVER executes.
```

**Step 4: Controller calls Service**
```java
// File: PatientController.java
PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
```

**Step 5: Service checks for duplicate email**
```java
// File: PatientService.java, Line ~36
public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
        throw new EmailAlreadyExistsException(
            "A patient with this email already exists" + patientRequestDTO.getEmail());
    }
```
```
patientRepository.existsByEmail("alice@example.com")
  → Hibernate generates: SELECT COUNT(*) FROM patient WHERE email = 'alice@example.com'
  → If count > 0 → returns true → EmailAlreadyExistsException thrown
  → GlobalExceptionHandler catches it → returns 400 + "Email already exists!"
  
  → If count = 0 → returns false → continue (email is unique)
```

**Step 6: Mapper converts DTO → Entity**
```java
// File: PatientService.java
Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
```
```java
// File: PatientMapper.java — toModel() runs first
public static Patient toModel(PatientRequestDTO dto) {
    Patient patient = new Patient();                          // New empty entity
    patient.setName(dto.getName());                           // "Alice Johnson"
    patient.setAddress(dto.getAddress());                     // "789 Oak St"
    patient.setEmail(dto.getEmail());                         // "alice@example.com"
    patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));     // "1995-03-15" → LocalDate
    patient.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate())); // "2024-01-20" → LocalDate
    return patient;
    // NOTE: patient.id is NULL at this point — database will generate it
}
```

**Step 7: Repository saves to database**
```
patientRepository.save(patient)
  → Hibernate sees patient.id is null → this is an INSERT (not UPDATE)
  → @GeneratedValue(strategy = GenerationType.AUTO) → generates new UUID
  → SQL: INSERT INTO patient (id, name, email, address, date_of_birth, registered_date)
         VALUES ('abc-def-123-...', 'Alice Johnson', 'alice@example.com',
                 '789 Oak St', '1995-03-15', '2024-01-20')
  → Database stores the row
  → Returns Patient object with id NOW SET to the generated UUID
  → newPatient.getId() is now 'abc-def-123-...' (no longer null)
```

**Step 8: gRPC call to Billing Service (SYNCHRONOUS)**
```java
// File: PatientService.java
billingServiceGrpcClient.createBillingAccount(
    newPatient.getId().toString(),  // "abc-def-123-..."
    newPatient.getName(),           // "Alice Johnson"
    newPatient.getEmail()           // "alice@example.com"
);
```
```java
// File: BillingServiceGrpcClient.java
public BillingResponse createBillingAccount(String patientId, String name, String email) {
    // Build Protobuf request object using Builder pattern
    BillingRequest request = BillingRequest.newBuilder()
        .setPatientId(patientId)   // "abc-def-123-..."
        .setName(name)             // "Alice Johnson"
        .setEmail(email)           // "alice@example.com"
        .build();
    // build() creates an IMMUTABLE BillingRequest object
    
    // Send via gRPC to billing-service at localhost:9001
    BillingResponse response = blockingStub.createBillingAccount(request);
    // This line BLOCKS (waits) until billing-service responds!
```
```
NETWORK CALL HAPPENS HERE:
  Patient Service ──HTTP/2 binary──→ Billing Service (port 9001)
  
  The gRPC framework:
  1. Serializes BillingRequest to binary bytes (Protobuf format)
  2. Sends over HTTP/2 to billing-service:9001
  3. Billing service's gRPC server receives the bytes
  4. Deserializes back to BillingRequest Java object
```
```java
// File: BillingGrpcService.java (BILLING SERVICE — different microservice!)
@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    @Override
    public void createBillingAccount(
        BillingRequest billingRequest,
        StreamObserver<BillingResponse> responseObserver) {
        
        log.info("Request received for patientId: {}", billingRequest.getPatientId());
        // Logs: "Request received for patientId: abc-def-123-..."
        
        // Build response (in real app, would save to billing database)
        BillingResponse response = BillingResponse.newBuilder()
            .setAccountId("12345")    // Generated billing account ID
            .setStatus("ACTIVE")       // Account is active
            .build();
        
        responseObserver.onNext(response);    // Send response back to caller
        responseObserver.onCompleted();        // Signal: "I'm done, no more data"
    }
}
```
```
Billing-service sends BillingResponse back:
  Billing Service ──HTTP/2 binary──→ Patient Service
  
  Back in BillingServiceGrpcClient:
  response.getAccountId() = "12345"
  response.getStatus() = "ACTIVE"
  log.info("Received response: accountId=12345, status=ACTIVE")
  
  Returns the BillingResponse to PatientService.
```

**Step 8b: What if Billing Service is DOWN?**
```
  blockingStub.createBillingAccount(request) throws StatusRuntimeException
    → Status code: UNAVAILABLE
    → Exception propagates up: Service → Controller → GlobalExceptionHandler
    
  GlobalExceptionHandler.handleGrpcStatusRuntimeException():
    → Logs: "gRPC service call failed: UNAVAILABLE"
    → Returns to client:
      Status: 503 Service Unavailable
      Body: {
        "Message": "A downstream service is currently unavailable.",
        "gRPCStatus": "UNAVAILABLE"
      }
    → IMPORTANT: The patient was already saved to DB in Step 7!
    → This is a distributed consistency problem (discussed in System Design section)
```

**Step 9: Kafka event published (ASYNCHRONOUS)**
```java
// File: PatientService.java
kafkaProducer.sendEvent(newPatient);
```
```java
// File: KafkaProducer.java
public void sendEvent(Patient patient) {
    // Build Protobuf event message
    PatientEvent event = PatientEvent.newBuilder()
        .setPatientId(patient.getId().toString())  // "abc-def-123-..."
        .setName(patient.getName())                 // "Alice Johnson"
        .setEmail(patient.getEmail())               // "alice@example.com"
        .setEventType("PATIENT_CREATED")            // What happened
        .build();
    
    // Send to Kafka topic "patient" as byte array
    kafkaTemplate.send("patient", event.toByteArray());
    // event.toByteArray() → serializes protobuf to compact binary format
    // This is ASYNCHRONOUS — does NOT wait for analytics to process!
    // KafkaTemplate sends to the Kafka broker and returns immediately.
}
```
```
KAFKA PIPELINE:
  1. KafkaTemplate serializes the key (String) and value (byte[])
     using StringSerializer and ByteArraySerializer (configured in application.properties)
  2. Sends to Kafka broker at localhost:9092
  3. Kafka broker stores the message in topic "patient", partition 0, offset N
  4. Kafka acknowledges receipt to the producer
  5. The message is now DURABLE — stored on disk even if services restart
```

**Step 10: Kafka consumer receives event (in Analytics Service — DIFFERENT MICROSERVICE)**
```java
// File: KafkaConsumer.java (ANALYTICS SERVICE)
@Service
public class KafkaConsumer {
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        // This method is called AUTOMATICALLY whenever a new message
        // appears on the "patient" topic
        
        PatientEvent patientEvent = PatientEvent.parseFrom(event);
        // Deserializes binary bytes back to PatientEvent protobuf object
        
        log.info("Received Patient Event: patientId={}, name={}, email={}",
            patientEvent.getPatientId(),   // "abc-def-123-..."
            patientEvent.getName(),         // "Alice Johnson"
            patientEvent.getEmail());       // "alice@example.com"
        
        // In a real app: save to analytics database, update dashboards,
        // trigger notifications, etc.
    }
}
```
```
TIMING:
  → The Kafka consumer may process this event milliseconds or seconds
    AFTER the patient creation response was already sent to the client.
  → The client does NOT wait for this.
  → If analytics-service is down, the message waits in Kafka.
    When analytics starts back up, it reads all pending messages from
    its last committed offset.
```

**Step 11: Response returned to client**
```java
// Back in PatientService.java:
return PatientMapper.toDto(newPatient);
```
```java
// PatientMapper.toDto() converts the saved entity (with generated UUID) to DTO:
// dto.id = "abc-def-123-..." (now has the generated ID!)
// dto.name = "Alice Johnson"
// dto.email = "alice@example.com"
// etc.
```
```java
// Back in PatientController.java:
return ResponseEntity.ok().body(patientResponseDTO);
```
```
Final HTTP Response to Client:
  Status: 200 OK
  Headers: Content-Type: application/json
  Body:
  {
    "id": "abc-def-123-456-789",
    "name": "Alice Johnson",
    "email": "alice@example.com",
    "address": "789 Oak St",
    "dateOfBirth": "1995-03-15"
  }
```

**Complete flow summary:**
```
 CLIENT                    API GATEWAY               PATIENT SERVICE                  BILLING SERVICE        KAFKA         ANALYTICS SERVICE
   |                          |                           |                               |                  |                  |
   |--- POST /patients ------>|                           |                               |                  |                  |
   |                          |--- forward to :4000 ----->|                               |                  |                  |
   |                          |                           |-- validate DTO                |                  |                  |
   |                          |                           |-- check email unique           |                  |                  |
   |                          |                           |-- save to DB                   |                  |                  |
   |                          |                           |-- gRPC call ------------------>|                  |                  |
   |                          |                           |<- BillingResponse -------------|                  |                  |
   |                          |                           |-- send Kafka event ------------|-->|              |                  |
   |                          |                           |                               |   | store msg    |                  |
   |<-- 200 OK + JSON --------|<-- forward response ------|                               |   |              |                  |
   |                          |                           |                               |   |-- deliver -->|                  |
   |                          |                           |                               |   |              |-- log event      |
```

---

### FLOW 4: UPDATE a Patient — `PUT /patients/{id}`

**Step 1: HTTP Request**
```
PUT http://localhost:4000/patients/123e4567-e89b-12d3-a456-426614174000
Headers: Content-Type: application/json
Body:
{
    "name": "John Doe Updated",
    "email": "john.new@example.com",
    "address": "456 New Address St",
    "dateOfBirth": "1985-06-15"
}
```

**Step 2: Controller receives request**
```java
// File: PatientController.java
@PutMapping("/{id}")
public ResponseEntity<PatientResponseDTO> updatePatient(
    @PathVariable UUID id,
    // @PathVariable extracts "123e4567-e89b-12d3-a456-426614174000" from URL
    // Spring automatically converts the String to a UUID object!
    @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
    // @Validated({Default.class}) — validates ALL annotations that have
    // NO group or belong to Default group.
    // CreatePatientValidationGroup is NOT included → registeredDate is NOT validated!
    // This means the client doesn't need to send registeredDate when updating.
```

**Step 3: Service finds existing patient**
```java
// File: PatientService.java
public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: ", id));
```
```
patientRepository.findById(UUID)
  → Hibernate: SELECT * FROM patient WHERE id = '123e4567-e89b-12d3-a456-426614174000'
  → If found: returns Optional.of(patient)
     → .orElseThrow() unwraps the Optional → returns the Patient object
  → If NOT found: returns Optional.empty()
     → .orElseThrow() executes the lambda → throws PatientNotFoundException
     → GlobalExceptionHandler catches it → 400 + "Patient not found!"
```

**Step 4: Service checks email uniqueness (excluding current patient)**
```java
    if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
        throw new EmailAlreadyExistsException(...);
    }
```
```
 existsByEmailAndIdNot("john.new@example.com", UUID(123e4567...))
   → SQL: SELECT COUNT(*) FROM patient WHERE email = 'john.new@example.com' AND id != '123e4567...'
   → KEY POINT: "AndIdNot" means exclude the CURRENT patient!
   → Without this, updating a patient without changing their email would
     trigger "email already exists" (because THEIR OWN email exists in DB).
   → This only checks if ANOTHER patient has the same email.
```

**Step 5: Service updates the entity fields**
```java
    patient.setName(patientRequestDTO.getName());            // "John Doe Updated"
    patient.setEmail(patientRequestDTO.getEmail());          // "john.new@example.com"
    patient.setAddress(patientRequestDTO.getAddress());      // "456 New Address St"
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth())); // 1985-06-15
    
    Patient updatedPatient = patientRepository.save(patient);
```
```
patientRepository.save(patient)
  → Hibernate sees patient.id is NOT null (it has 123e4567...)
  → This is an UPDATE (not INSERT)!
  → SQL: UPDATE patient SET name='John Doe Updated', email='john.new@example.com',
         address='456 New Address St', date_of_birth='1985-06-15'
         WHERE id = '123e4567-e89b-12d3-a456-426614174000'
  → Returns the updated Patient entity
```

**Step 6: Convert and return**
```java
    return PatientMapper.toDto(patient);  // Entity → DTO → JSON → 200 OK
}
```

**Key difference from CREATE:**
- No gRPC call to billing (billing account already exists)
- No Kafka event (only creation events are published)
- `registeredDate` is not validated (not in the validated group)
- `findById` + `save` = UPDATE, not INSERT

---

### FLOW 5: DELETE a Patient — `DELETE /patients/{id}`

**Step 1: HTTP Request**
```
DELETE http://localhost:4000/patients/123e4567-e89b-12d3-a456-426614174000
Body: (none)
```

**Step 2: Controller receives request**
```java
// File: PatientController.java
@DeleteMapping("/{id}")
public ResponseEntity<PatientResponseDTO> deletePatient(@PathVariable UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();  // 204 No Content
}
```

**Step 3: Service deletes**
```java
// File: PatientService.java
public void deletePatient(UUID id) {
    patientRepository.deleteById(id);
    // → SQL: DELETE FROM patient WHERE id = '123e4567-e89b-12d3-a456-426614174000'
    // → If id doesn't exist: JPA silently does nothing (no exception by default)
}
```

**Step 4: Response**
```
HTTP Response:
  Status: 204 No Content
  Body: (empty — noContent() means no body)
```

**Note:** The return type says `ResponseEntity<PatientResponseDTO>` but `noContent().build()` returns no body. This is a minor inconsistency — ideally the return type should be `ResponseEntity<Void>`. The code still works because Java generics are erased at runtime.

---

### FLOW 6: User Login — `POST /auth/login`

This flow involves the Auth Service and demonstrates authentication.

**Step 1: HTTP Request**
```
POST http://localhost:4005/login
Headers: Content-Type: application/json
Body:
{
    "email": "testuser@test.com",
    "password": "password123"
}
```

**Step 2: AuthController receives request**
```java
// File: AuthController.java
@PostMapping("/login")
public ResponseEntity<LoginResponseDTO> login(
    @RequestBody LoginRequestDTO loginRequestDTO) {
    // loginRequestDTO.getEmail() = "testuser@test.com"
    // loginRequestDTO.getPassword() = "password123" (plain text from client)
    
    Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);
```

**Step 3: AuthService authenticates**
```java
// File: AuthService.java
public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
    Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
```
```
Step 3a: Find user by email
  userService.findByEmail("testuser@test.com")
    → userRepository.findByEmail("testuser@test.com")
    → SQL: SELECT * FROM users WHERE email = 'testuser@test.com'
    → Found! Returns Optional.of(User{id=223e4567..., email=testuser@test.com,
        password=$2b$12$7hoRZfJ..., role=ADMIN})
```
```java
        .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
```
```
Step 3b: Verify password
  passwordEncoder.matches("password123", "$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy...")
  
  BCryptPasswordEncoder internally:
  1. Extracts salt from the stored hash: "$2b$12$7hoRZfJrRKD2nIm2vHLs7O"
  2. Hashes "password123" with that SAME salt and cost factor 12
  3. Compares the result with the stored hash
  4. If they match → returns true → filter KEEPS the user
  5. If no match → returns false → filter REMOVES the user → Optional becomes empty
```
```java
        .map(u -> jwUtil.generateToken(u.getEmail(), u.getRole()));
```
```
Step 3c: Generate JWT token (only runs if password matched)
  jwUtil.generateToken("testuser@test.com", "ADMIN")
```
```java
// File: JwUtil.java
public String generateToken(String email, String role) {
    return Jwts.builder()
        .subject(email)           // Payload: {"sub": "testuser@test.com"}
        .claim("role", role)      // Payload: {"role": "ADMIN"}
        .issuedAt(new Date())     // Payload: {"iat": 1709856000}
        .expiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
        // 10 hours from now: 1000ms × 60s × 60min × 10hr = 36,000,000ms
        .signWith(secretKey)      // Sign with HMAC-SHA key (from jwt.secret property)
        .compact();               // Build final token string
}

// Returns: "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXNlckB0ZXN0LmNvbSIsInJvbGUiOiJBRE1JTiIs..."
```

**Step 4: Controller returns token**
```java
// Back in AuthController:
if (tokenOptional.isEmpty()) {
    // Email not found OR password didn't match
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    // 401 Unauthorized, no body
}
String token = tokenOptional.get();  // Extract token string from Optional
return ResponseEntity.ok(new LoginResponseDTO(token));
```
```
HTTP Response:
  Status: 200 OK
  Body:
  {
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXNlckB0ZXN0LmNvbSIs..."
  }
```

**The client stores this token and includes it in all future requests as:**
```
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXN...
```

---

### FLOW 7: Token Validation — `GET /auth/validate`

This is called by the API Gateway before forwarding requests.

**Step 1: HTTP Request**
```
GET http://localhost:4005/validate
Headers:
  Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXN...
```

**Step 2: AuthController validates**
```java
// File: AuthController.java
@GetMapping("/validate")
public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader) {
    // @RequestHeader extracts the "Authorization" header value
    // authHeader = "Bearer eyJhbGciOiJIUzM4NCJ9..."
    
    // Check header format
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
    }
    
    // Extract token (remove "Bearer " prefix — 7 characters)
    // authHeader.substring(7) = "eyJhbGciOiJIUzM4NCJ9..."
    return authService.validateToken(authHeader.substring(7))
        ? ResponseEntity.ok().build()                              // 200 OK
        : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 401
}
```

**Step 3: AuthService validates**
```java
// File: AuthService.java
public boolean validateToken(String token) {
    try {
        jwUtil.validateToken(token);
        return true;
    } catch (JwtException e) {
        return false;
    }
}
```
```java
// File: JwUtil.java
public void validateToken(String token) {
    Jwts.parser()
        .verifyWith((SecretKey) secretKey)  // Use same secret key that signed the token
        .build()
        .parseSignedClaims(token);          // Parse and verify
}
```
```
What parseSignedClaims() does internally:
  1. Splits token into three parts: header.payload.signature
  2. Decodes header → gets algorithm (HS384)
  3. Takes header + "." + payload → re-computes signature using secretKey
  4. Compares computed signature with the token's signature
     → If different → throws SignatureException ("Invalid JWT signature")
  5. Decodes payload → checks expiration claim
     → If expired → throws ExpiredJwtException
  6. If all checks pass → returns without exception → token is VALID

Possible failure scenarios:
  - Token was tampered with → signature mismatch → JwtException
  - Token expired (> 10 hours old) → ExpiredJwtException → JwtException
  - Token is random garbage → MalformedJwtException → JwtException
  - Token signed with different key → SignatureException → JwtException
  All caught by catch(JwtException) → returns false → 401 Unauthorized
```

---

### FLOW 8: API Gateway Routing — Full Request Lifecycle

When using the API Gateway, the client talks to `localhost:4004` and never directly to services.

**Scenario: Client creates a patient through the gateway**

```
POST http://localhost:4004/api/patients
Headers:
  Content-Type: application/json
  Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
Body: { "name": "Alice", "email": "alice@test.com", ... }
```

**Step 1: Spring Cloud Gateway receives request**
```
Gateway checks all route PREDICATES to find a match:

Route 1: auth-service-route
  Predicate: Path=/auth/**
  Does "/api/patients" match "/auth/**"? → NO

Route 2: patient-service-route
  Predicate: Path=/api/patients/**
  Does "/api/patients" match "/api/patients/**"? → YES! ✅

Selected route: patient-service-route
  uri: http://patient-service:4000
  filters:
    - StripPrefix=1
```

**Step 2: JWT Validation Filter (if configured)**
```java
// File: JwtValidationGatewayFilterFactory.java
// This filter runs BEFORE forwarding the request

// 1. Extract token from Authorization header
String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

// 2. If no token or wrong format → 401 immediately (request never reaches patient-service)
if (token == null || !token.startsWith("Bearer ")) {
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
}

// 3. Call auth-service to validate token
//    Uses WebClient (non-blocking HTTP client) to call:
//    GET http://auth-service:4005/validate
//    Headers: Authorization: Bearer <token>

// 4. If auth-service returns 200 → token valid → proceed to Step 3
// 5. If auth-service returns 401 → token invalid → return 401 to client
```

**Step 3: StripPrefix filter modifies URL**
```
Original URL:  /api/patients
StripPrefix=1: removes first path segment → /patients

So the gateway forwards:
  POST http://patient-service:4000/patients
  (with the same headers and body)
```

**Step 4: Patient Service processes normally**
```
From here, it's exactly the same as FLOW 3 (Create Patient).
Forwards the request to patient-service:4000/patients
Patient service processes it, returns response.
```

**Step 5: Gateway forwards response back to client**
```
Patient service returns: 200 OK + JSON body
Gateway forwards the same response to the client.
Client sees the response as if it came from the gateway.
```

**Full hop diagram:**
```
Client                    API Gateway (:4004)         Auth Service (:4005)     Patient Service (:4000)
  |                            |                           |                        |
  |-- POST /api/patients ----->|                           |                        |
  |  + Authorization header    |                           |                        |
  |                            |-- GET /validate --------->|                        |
  |                            |   + Authorization header  |                        |
  |                            |                           |-- validate JWT          |
  |                            |<-- 200 OK ----------------|                        |
  |                            |                                                    |
  |                            |-- POST /patients (StripPrefix applied) ----------->|
  |                            |   + original body + headers                         |
  |                            |                                                    |
  |                            |                          Patient processes request  |
  |                            |                          (validate, save, gRPC,     |
  |                            |                           Kafka, etc.)              |
  |                            |                                                    |
  |                            |<-- 200 OK + patient JSON ---------------------------|
  |<-- 200 OK + patient JSON --|                                                    |
```

---

### FLOW 9: Billing Service gRPC Server — How It Starts and Listens

**Step 1: Billing Service application starts**
```
BillingServiceApplication.main() → SpringApplication.run()
  → Component scan finds @GrpcService on BillingGrpcService
  → grpc-spring-boot-starter auto-configuration kicks in:
    - Reads grpc.server.port=9001 from application.properties
    - Creates a gRPC server on port 9001
    - Registers BillingGrpcService as a gRPC service handler
    - Maps BillingService/CreateBillingAccount RPC to BillingGrpcService.createBillingAccount()
  → Also starts Tomcat on port 4001 (for health checks, etc.)
```

**Step 2: Waiting for requests**
```
The gRPC server on port 9001 is now listening.

When a request comes in:
  1. gRPC framework receives the HTTP/2 request
  2. Reads the path: /BillingService/CreateBillingAccount
  3. Deserializes the binary body → BillingRequest object
  4. Calls BillingGrpcService.createBillingAccount(request, responseObserver)
  5. Your code runs, calls responseObserver.onNext() then .onCompleted()
  6. gRPC framework serializes BillingResponse → sends back over HTTP/2
```

**Understanding StreamObserver (Observer Pattern):**
```java
// StreamObserver<BillingResponse> responseObserver

// Think of it as a "callback channel" back to the caller:
responseObserver.onNext(response);     // "Here's your data" (can be called multiple times for streaming)
responseObserver.onCompleted();        // "I'm done sending" (must be called exactly once)
// responseObserver.onError(exception); // "Something went wrong" (alternative to onCompleted)

// For Unary RPC (your project): exactly ONE onNext() followed by ONE onCompleted()
// For Server Streaming: MULTIPLE onNext() followed by ONE onCompleted()
```

---

### FLOW 10: Kafka Full Pipeline — Producer to Consumer

Let's trace a Kafka message from creation to consumption:

**Phase 1: Configuration (at startup)**

**Producer side (patient-service application.properties):**
```properties
# How to convert message key to bytes for Kafka
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
# How to convert message value to bytes for Kafka
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.ByteArraySerializer
# ByteArraySerializer = "don't transform, already bytes" (because Protobuf gives us bytes)
```

**Consumer side (analytics-service application.properties):**
```properties
# Kafka broker address
spring.kafka.bootstrap-servers=${SPRING_KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
# Consumer group — identifies this consumer
spring.kafka.consumer.group-id=analytics-service
# How to convert bytes back to message key
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
# How to convert bytes back to message value
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.ByteArrayDeserializer
# Where to start reading if no offset is stored
spring.kafka.consumer.auto-offset-reset=earliest
```

**Phase 2: Message Production (in patient-service)**
```
1. PatientService calls kafkaProducer.sendEvent(newPatient)
2. KafkaProducer builds PatientEvent protobuf → calls .toByteArray()
   PatientEvent binary: [0x0A 0x12 0x61 0x62 0x63 ...] (compact binary, NOT JSON text)
3. kafkaTemplate.send("patient", eventBytes)
4. KafkaTemplate:
   a. Looks up partition for topic "patient" (default: round-robin or key-based)
   b. Creates ProducerRecord: {topic="patient", partition=0, key=null, value=bytes}
   c. Serializes: key → StringSerializer → null bytes
                  value → ByteArraySerializer → passes through unchanged
   d. Sends to Kafka broker at localhost:9092 via TCP
5. Kafka broker:
   a. Receives the record
   b. Appends to partition 0 of topic "patient" at next offset
   c. Writes to disk (commit log)
   d. Sends acknowledgment back to producer
   e. Message is now stored: topic=patient, partition=0, offset=42
```

**Phase 3: Message Consumption (in analytics-service)**
```
1. At startup, Spring sees @KafkaListener(topics="patient", groupId="analytics-service")
2. Creates a KafkaMessageListenerContainer that:
   a. Connects to Kafka broker at localhost:9092
   b. Subscribes to topic "patient" as part of group "analytics-service"
   c. Checks: "Where did I leave off?" (committed offset for this group)
      - If first time + auto-offset-reset=earliest → start from offset 0 (read ALL messages)
      - If previously committed → resume from last committed offset
3. Kafka broker sends any unread messages to the consumer
4. For each message:
   a. ByteArrayDeserializer converts raw bytes → byte[] Java array
   b. Spring calls consumeEvent(byte[] event)
   c. Your code: PatientEvent.parseFrom(event) → deserialize protobuf
   d. Your code: log the event details
   e. After processing, consumer commits the offset:
      "I've processed up to offset 42 on partition 0"
5. Next poll: Kafka only sends messages with offset > 42
```

**What happens if analytics-service crashes and restarts?**
```
1. Consumer restarts → connects to Kafka
2. Asks: "What's my last committed offset for topic 'patient'?"
3. Kafka says: "Offset 42 on partition 0"
4. Consumer resumes from offset 43 → no messages lost!
5. (But if it crashed BEFORE committing offset 42, it might re-process message 42.
   This is "at-least-once" delivery — duplicates possible.)
```

---

### FLOW 11: Data Seeding with data.sql — Application Startup

Both patient-service and auth-service have `data.sql` files for initial data.

**What happens at startup:**
```
1. Spring Boot starts
2. JPA/Hibernate scans @Entity classes:
   - Patient.java → creates schema for "patient" table
   - User.java → creates schema for "users" table
3. Hibernate DDL auto generates CREATE TABLE statements
4. Spring detects data.sql in src/main/resources/
5. Executes data.sql AFTER tables are created:
```

**patient-service data.sql:**
```sql
-- Step 1: Create table if not exists (safety net)
CREATE TABLE IF NOT EXISTS patient (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    registered_date DATE NOT NULL
);

-- Step 2: Insert only if not already present (idempotent)
INSERT INTO patient (id, name, email, address, date_of_birth, registered_date)
SELECT '123e4567-e89b-12d3-a456-426614174000', 'John Doe', 'john.doe@example.com',
       '123 Main St, Springfield', '1985-06-15', '2024-01-10'
WHERE NOT EXISTS (
    SELECT 1 FROM patient WHERE id = '123e4567-e89b-12d3-a456-426614174000'
);
```
```
Why "WHERE NOT EXISTS"?
  → If the app restarts and data.sql runs AGAIN, it won't INSERT duplicates.
  → This makes the script IDEMPOTENT (running it multiple times has the same effect).
  → Without it: second run would fail with "duplicate primary key" error.
```

**auth-service data.sql:**
```sql
INSERT INTO "users" (id, email, password, role)
SELECT '223e4567-e89b-12d3-a456-426614174006', 'testuser@test.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM "users" WHERE id = '223e4567-e89b-12d3-a456-426614174006'
       OR email = 'testuser@test.com'
);
```
```
Key points:
  - Table name "users" is in DOUBLE QUOTES because 'user' is a reserved SQL keyword
  - Password is PRE-HASHED with BCrypt — you can't store plain text!
    '$2b$12$7hoRZfJ...' is the BCrypt hash of some password that was hashed offline
  - Role is 'ADMIN' — this is the role included in the JWT token
  - The WHERE NOT EXISTS checks BOTH id AND email to handle edge cases
```

---

### FLOW 12: Error Handling — Every Possible Error Path

Let's map every error that can happen and how it's handled:

**Error 1: Validation Failure (any endpoint with @Validated)**
```
Trigger: Client sends invalid data (e.g., blank name, bad email format)
Exception: MethodArgumentNotValidException
Handler: GlobalExceptionHandler.handleValidationException()
Response: 400 Bad Request
Body: {"fieldName": "error message", ...}

Example:
  Request: {"name": "", "email": "not-email"}
  Response: {"name": "must not be blank", "email": "Email should be valid"}

How it works:
  1. @Validated triggers Bean Validation on the DTO
  2. Multiple fields can fail simultaneously
  3. Spring collects ALL errors into BindingResult
  4. Throws MethodArgumentNotValidException with BindingResult
  5. Handler extracts field name → error message pairs
  6. Returns Map as JSON
```

**Error 2: Duplicate Email (POST /patients or PUT /patients/{id})**
```
Trigger: Email already used by another patient
Exception: EmailAlreadyExistsException (custom, extends RuntimeException)
Handler: GlobalExceptionHandler.handleEmailAlreadyExistsException()
Response: 400 Bad Request
Body: {"Message": "Email already exists!"}

Flow:
  1. Service calls patientRepository.existsByEmail(email)
  2. Returns true → throw new EmailAlreadyExistsException(...)
  3. Exception bubbles up: Service → Controller → DispatcherServlet → ControllerAdvice
  4. @ExceptionHandler(EmailAlreadyExistsException.class) catches it
  5. Logs warning: "Email already exists: ..."
  6. Returns 400 + error map
```

**Error 3: Patient Not Found (PUT /patients/{id})**
```
Trigger: ID doesn't exist in database
Exception: PatientNotFoundException (custom, extends RuntimeException)
Handler: GlobalExceptionHandler.handlePatientNotFoundException()
Response: 400 Bad Request
Body: {"Message": "Patient not found!"}

Flow:
  1. patientRepository.findById(id) returns Optional.empty()
  2. .orElseThrow(() -> new PatientNotFoundException(...))
  3. Lambda executes → creates and throws exception
  4. Caught by GlobalExceptionHandler → 400 + error message
```

**Error 4: Billing Service Unavailable (POST /patients — gRPC)**
```
Trigger: Billing service is down or unreachable
Exception: StatusRuntimeException (from gRPC library)
Handler: GlobalExceptionHandler.handleGrpcStatusRuntimeException()
Response: 503 Service Unavailable
Body: {
  "Message": "A downstream service is currently unavailable. Please try again later.",
  "gRPCStatus": "UNAVAILABLE"
}

Possible gRPC status codes:
  - UNAVAILABLE: service is down or connection refused
  - DEADLINE_EXCEEDED: operation took too long
  - INTERNAL: bug in billing service
  - UNIMPLEMENTED: method doesn't exist on server
```

**Error 5: Kafka Send Failure (POST /patients — fire-and-forget)**
```
Trigger: Kafka broker is down
Handling: Try-catch inside KafkaProducer.sendEvent()
Response: NONE — this error is SWALLOWED!

Code:
  try {
      kafkaTemplate.send("patient", event.toByteArray());
  } catch (Exception e) {
      log.error("Error sending PatientCreated event: {}", event);
  }

  → The patient is STILL created and returned to the client!
  → Only error gets logged
  → The analytics service just never gets the event
  → Design choice: analytics is non-critical, so we don't fail the request
```

**Error 6: Invalid JWT Token (GET /auth/validate)**
```
Trigger: Token expired, tampered with, or malformed
Exception: JwtException (caught in AuthService)
Response: 401 Unauthorized (no body)

Flow:
  1. JwUtil.validateToken() → Jwts.parser().parseSignedClaims(token)
  2. Signature invalid → throws SignatureException → JwtException → caught → false
  3. Token expired → throws ExpiredJwtException → JwtException → caught → false
  4. AuthService returns false → Controller returns 401
```

**Error 7: Wrong Credentials (POST /auth/login)**
```
Trigger: Email doesn't exist OR password doesn't match
Response: 401 Unauthorized (no body)

Flow:
  1. userService.findByEmail(email)
     → If email not in DB: Optional.empty() → filter() skipped → map() skipped
       → returns Optional.empty()
  2. .filter(u -> passwordEncoder.matches(...))
     → If password wrong: returns false → filter removes element
       → returns Optional.empty()
  3. tokenOptional.isEmpty() → true
  4. Returns 401 Unauthorized

Security note: Same 401 for "email not found" and "wrong password"
  → Prevents attackers from knowing which emails exist in your system
```

**Error propagation path:**
```
Exception thrown in:      Service / Repository / gRPC Client
                             ↓
                        Controller method (not caught here)
                             ↓
                        DispatcherServlet
                             ↓
                        @ControllerAdvice (GlobalExceptionHandler)
                             ↓
                        Matches @ExceptionHandler for the exception class
                             ↓
                        Returns ResponseEntity with error status + body
                             ↓
                        Jackson serializes to JSON
                             ↓
                        HTTP Response to client
```

---

### FLOW 13: Swagger/OpenAPI Documentation

Both patient-service and auth-service include `springdoc-openapi-starter-webmvc-ui`.

**How it works:**
```
1. At startup, SpringDoc scans all @RestController classes
2. Reads @GetMapping, @PostMapping, etc. → builds API specification
3. Reads @RequestBody, @PathVariable → documents parameters
4. Reads @Operation annotations (like in AuthController) → adds descriptions
5. Generates OpenAPI 3.0 JSON specification at:
   → http://localhost:4000/v3/api-docs (JSON)
6. Serves Swagger UI (interactive web page) at:
   → http://localhost:4000/swagger-ui.html
7. API Gateway routes /api-docs/patients → patient-service /v3/api-docs
```

**From AuthController:**
```java
@Operation(summary = "Generate token on user login")  // Shows in Swagger UI
@PostMapping("/login")
public ResponseEntity<LoginResponseDTO> login(...) { ... }

@Operation(summary = "Validate token")  // Shows in Swagger UI
@GetMapping("/validate")
public ResponseEntity<Void> validateToken(...) { ... }
```

Swagger UI lets you **test APIs from the browser** — you can fill in parameters, click "Execute", and see the response. Very useful for development and for demonstrating your API to others.

---

### FLOW 14: Spring Cloud Gateway Route Matching — All Routes Explained

```yaml
# Route 1: Auth Service
- id: auth-service-route
  uri: http://auth-service:4005      # Forward to this service
  predicates:
    - Path=/auth/**                   # Match /auth/login, /auth/validate, etc.
  filters:
    - StripPrefix=1                   # /auth/login → /login
```
```
Examples:
  POST /auth/login      → POST http://auth-service:4005/login
  GET  /auth/validate    → GET  http://auth-service:4005/validate
  GET  /auth/anything    → GET  http://auth-service:4005/anything
```

```yaml
# Route 2: Patient Service
- id: patient-service-route
  uri: http://patient-service:4000
  predicates:
    - Path=/api/patients/**
  filters:
    - StripPrefix=1
```
```
Examples:
  GET    /api/patients        → GET    http://patient-service:4000/patients
  POST   /api/patients        → POST   http://patient-service:4000/patients
  PUT    /api/patients/{id}   → PUT    http://patient-service:4000/patients/{id}
  DELETE /api/patients/{id}   → DELETE http://patient-service:4000/patients/{id}
```

```yaml
# Route 3: API Documentation
- id: api-docs-patient-route
  uri: http://patient-service:4000
  predicates:
    - Path=/api-docs/patients
  filters:
    - RewritePath=/api-docs/patients, /v3/api-docs
```
```
Examples:
  GET /api-docs/patients → GET http://patient-service:4000/v3/api-docs
  This exposes the patient-service OpenAPI spec through the gateway.
  RewritePath completely replaces the path (not just stripping prefix).
```

**How route matching order works:**
```
Request: POST /api/patients

1. Check Route 1: Path=/auth/**     → /api/patients does NOT start with /auth → SKIP
2. Check Route 2: Path=/api/patients/** → /api/patients MATCHES → USE THIS ROUTE
3. Route 3 never checked (first match wins)
```

---

### FLOW 15: Security Config — Why Auth Service Permits All Requests

**Common confusion:** "If the auth service has Spring Security, why doesn't it block requests?"

```java
// File: SecurityConfig.java (auth-service)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll())     // ALL requests are allowed without authentication
            .csrf(AbstractHttpConfigurer::disable);  // Disable CSRF
        return http.build();
    }
}
```

**Why permitAll()?**
```
The auth-service IS the authentication provider.
Clients need to call /login WITHOUT a token (they don't have one yet!).
Clients need to call /validate (this is called by the API Gateway internally).

If we required authentication to access /login, users could never log in!
It would be like: "You need a key to enter the building where keys are issued."

Without this config, Spring Security would:
  → AUTO-generate a random password at startup
  → Redirect all requests to a login FORM
  → Block all API calls
  → Because spring-boot-starter-security defaults to "secure everything"
```

**Why disable CSRF?**
```
CSRF attacks exploit browser cookies:
  1. You're logged into bank.com (browser has session cookie)
  2. You visit evil.com
  3. evil.com has a hidden form that POSTs to bank.com/transfer
  4. Browser includes your bank.com cookie automatically!
  5. Bank thinks it's a legitimate request

CSRF protection: server embeds a token in forms, requires it in requests.

Why we don't need it:
  → Our API uses JWT tokens in the Authorization HEADER
  → Headers are NOT automatically included by browsers (unlike cookies)
  → External sites CANNOT set custom headers on cross-origin requests
  → Therefore CSRF attacks are impossible with header-based tokens
```

**BCryptPasswordEncoder bean:**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```
```
This creates a SINGLETON BCryptPasswordEncoder bean.
Spring DI injects it into AuthService.
Shared across all requests (BCryptPasswordEncoder is thread-safe).

Why a @Bean method instead of @Component on BCryptPasswordEncoder?
  → BCryptPasswordEncoder is from Spring Security library — you can't add @Component to it.
  → @Bean on a method = "register THIS object as a bean in the IoC container."
```

---

### Summary: Which Files Are Involved in Each Operation

| Operation | Files Involved (in order) |
|-----------|---------------------------|
| **GET /patients** | PatientController → PatientService → PatientRepository → PatientMapper |
| **POST /patients** | PatientController → PatientService → PatientRepository → PatientMapper → BillingServiceGrpcClient → (Billing: BillingGrpcService) → KafkaProducer → (Analytics: KafkaConsumer) + GlobalExceptionHandler (on error) |
| **PUT /patients/{id}** | PatientController → PatientService → PatientRepository → PatientMapper + GlobalExceptionHandler (on error) |
| **DELETE /patients/{id}** | PatientController → PatientService → PatientRepository |
| **POST /login** | AuthController → AuthService → UserService → UserRepository → JwUtil |
| **GET /validate** | AuthController → AuthService → JwUtil |
| **Gateway routing** | JwtValidationGatewayFilterFactory → (Auth: AuthController) → StripPrefix → downstream service |
| **App startup** | Application class → ComponentScan → BeanCreation → DI → SchemaGeneration → data.sql |
| **Error handling** | Any layer throws → GlobalExceptionHandler catches → ResponseEntity |

---

## 36. Common Interview Questions — Rapid Fire

### Java Core

**Q: What is the difference between `==` and `.equals()` in Java?**
> **A:** `==` compares memory references (are they the same object?). `.equals()` compares values. For `String s1 = new String("abc"); String s2 = new String("abc");`: `s1 == s2` is `false` (different objects), `s1.equals(s2)` is `true` (same content). In C++ terms: `==` is like comparing pointers, `.equals()` is like comparing what the pointers point to.
>
> ```java
> String s1 = new String("hello");
> String s2 = new String("hello");
> s1 == s2       // false — different objects in memory (like comparing pointers in C++)
> s1.equals(s2)  // true  — same content (like strcmp() == 0)
>
> // In YOUR project (Patient.java):
> UUID id1 = patient1.getId();
> UUID id2 = patient2.getId();
> id1 == id2       // MIGHT be false even if same UUID value!
> id1.equals(id2)  // Correct way to compare UUIDs
> ```

**Q: What is `final` in Java?**
> **A:** `final` variable = constant (can't reassign). `final` method = can't override. `final` class = can't extend. In my project: `private final PatientService patientService` means the reference can't change after construction (immutability).
>
> ```java
> // From PatientController.java:
> private final PatientService patientService;  // final → can never be reassigned
> // patientService = new PatientService();  // COMPILE ERROR after constructor!
>
> // final variable: can't reassign
> final int MAX_SIZE = 100;  // MAX_SIZE = 200; → COMPILE ERROR
>
> // final method: can't override in subclass
> // final class: can't extend (String is final → you can't extend String)
>
> // C++ equivalent: const (but final is on the REFERENCE, not the object!)
> // final List<String> list = new ArrayList<>();
> // list.add("hello");  // ✅ OK — modifying the OBJECT is fine
> // list = new ArrayList<>();  // ❌ ERROR — can't reassign the REFERENCE
> ```

**Q: What is the difference between `String`, `StringBuilder`, and `StringBuffer`?**
> **A:** `String` is immutable (every "modification" creates a new object). `StringBuilder` is mutable and not thread-safe (faster). `StringBuffer` is mutable and thread-safe (slower). For concatenation in a loop, use `StringBuilder`.
>
> ```java
> // String (IMMUTABLE — creates new object each time):
> String s = "hello";
> s = s + " world";  // Creates a NEW String object! Old "hello" becomes garbage.
> // In a loop: "a" + "b" + "c" + ... creates N intermediate objects!
>
> // StringBuilder (MUTABLE — modifies in place, fast):
> StringBuilder sb = new StringBuilder();
> sb.append("hello").append(" world");  // Same object, no garbage created
>
> // StringBuffer: same as StringBuilder but synchronized (thread-safe, slower)
> // In 99% of cases, use StringBuilder (single-threaded is fine)
> ```

**Q: What is `HashMap`? How does it work internally?**
> **A:** A key-value data structure using hashing. Internally, it's an array of linked lists (or trees for large chains). `hashCode()` determines the bucket, `equals()` resolves collisions. Time complexity: O(1) average for get/put. In my project, `HashMap<String, String>` is used for error responses.
>
> ```java
> // From GlobalExceptionHandler.java:
> Map<String, String> errors = new HashMap<>();
> errors.put("Message", "Patient not found!");  // O(1) put
> // Internally: hashCode("Message") % 16 = bucket 5 → store at index 5
>
> // Another usage (validation errors):
> ex.getBindingResult().getFieldErrors()
>     .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
> // Stores: {"name": "must not be blank", "email": "Email should be valid"}
> ```

### Spring Boot

**Q: What is the Spring Bean lifecycle?**
> **A:** Container creates bean → DI (inject dependencies) → `@PostConstruct` method → Bean is ready for use → `@PreDestroy` method → Bean destroyed. Spring manages the entire lifecycle.
>
> ```java
> @Service
> public class PatientService {  // Spring manages this bean's lifecycle
>     private final PatientRepository repo;        // Step 2: DI
>     private final BillingServiceGrpcClient grpc;  // Step 2: DI
>     private final KafkaProducer kafka;             // Step 2: DI
>
>     // Step 1: Spring calls constructor (creates bean)
>     public PatientService(PatientRepository repo, BillingServiceGrpcClient grpc,
>                          KafkaProducer kafka) {
>         this.repo = repo; this.grpc = grpc; this.kafka = kafka;
>     }
>
>     // Step 3: @PostConstruct runs AFTER injection (if you had one)
>     // @PostConstruct public void init() { log.info("Service ready!"); }
>
>     // ... bean is now READY and handles requests ...
>
>     // Step 4: @PreDestroy runs on shutdown (if you had one)
>     // @PreDestroy public void cleanup() { log.info("Shutting down..."); }
> }
> ```

**Q: What is the default bean scope? What other scopes exist?**
> **A:** Default is **singleton** (one instance per Spring container). Other scopes: **prototype** (new instance each time), **request** (one per HTTP request), **session** (one per HTTP session), **application** (one per servlet context).
>
> ```java
> // SINGLETON (default in your project — ALL your beans):
> @Service  // Singleton by default
> public class PatientService { }  // ONE instance handles ALL requests
> // 1000 concurrent requests → same PatientService object
>
> // PROTOTYPE (new instance each time):
> @Scope("prototype")
> @Component
> public class RequestLogger { }  // New object every time it's injected
>
> // REQUEST (one per HTTP request):
> @Scope("request")
> @Component
> public class RequestContext { }  // Fresh for each HTTP request
>
> // Why singleton is the default?
> // Creating objects is expensive. One PatientService is enough —
> // it has no mutable state (all fields are final and thread-safe).
> ```

**Q: How does Spring auto-configuration work?**
> **A:** Spring Boot checks the classpath for existing libraries and automatically configures beans. It uses `@ConditionalOnClass`, `@ConditionalOnMissingBean`, etc. For example, if `spring-boot-starter-data-jpa` and `h2` are on the classpath, it auto-configures a DataSource, EntityManagerFactory, and TransactionManager.
>
> ```xml
> <!-- In your pom.xml: -->
> <dependency>
>     <artifactId>spring-boot-starter-data-jpa</artifactId>  <!-- JPA on classpath -->
> </dependency>
> <dependency>
>     <artifactId>h2</artifactId>  <!-- H2 driver on classpath -->
> </dependency>
> ```
> ```java
> // Spring sees JPA + H2 and AUTO-CREATES these beans (you never write them):
> // @ConditionalOnClass(DataSource.class)  → JPA is on classpath? Yes!
> // @ConditionalOnClass(H2.class)          → H2 is on classpath? Yes!
> // Auto-creates: DataSource(url="jdbc:h2:mem:testdb")
> //               EntityManagerFactory
> //               TransactionManager
> //               JpaRepository implementations
> // You just write: JpaRepository<Patient, UUID> and it WORKS!
> ```

**Q: What is `application.properties` vs `application.yml`?**
> **A:** Both configure Spring Boot. `.properties` uses `key=value` format. `.yml` uses YAML format with indentation for hierarchy. YAML is more readable for nested configurations. In my project, the API Gateway uses YAML (better for complex route definitions), while other services use properties (simpler configuration).
>
> ```properties
> # patient-service application.properties (flat key=value):
> spring.datasource.url=jdbc:h2:mem:patientdb
> spring.jpa.hibernate.ddl-auto=update
> billing.service.address=localhost
> billing.service.grpc.port=9001
> ```
> ```yaml
> # api-gateway application.yml (nested hierarchy):
> spring:
>   cloud:
>     gateway:
>       routes:
>         - id: auth-service
>           uri: http://auth-service:4005
>           predicates:
>             - Path=/auth/**
> # YAML is much more readable for deeply nested config like gateway routes!
> ```

### Database

**Q: What is normalization?**
> **A:** Organizing database tables to reduce redundancy. **1NF:** each column has atomic values. **2NF:** no partial dependency on composite key. **3NF:** no transitive dependencies. My `patient` table is in 3NF — each column depends only on the primary key `id`.
>
> ```sql
> -- Your patient table (3NF — every column depends ONLY on the primary key):
> CREATE TABLE patient (
>     id UUID PRIMARY KEY,           -- PK
>     name VARCHAR(255),             -- depends on id only ✓
>     email VARCHAR(255) UNIQUE,     -- depends on id only ✓
>     address VARCHAR(255),          -- depends on id only ✓
>     date_of_birth DATE,            -- depends on id only ✓
>     registered_date DATE           -- depends on id only ✓
> );
> -- NOT normalized (bad): storing "city" and "state" with full address
> -- because city depends on zipcode, not on patient id (transitive dependency)
> ```

**Q: What are indexes? Why are they important?**
> **A:** Indexes are data structures (usually B-trees) that speed up queries. `Patient.email` has a UNIQUE constraint which creates an index. Without it, `existsByEmail()` would scan every row (O(n)). With it, it's O(log n). Trade-off: indexes slow down writes because they must be updated.
>
> ```java
> // From Patient.java:
> @Column(unique = true, nullable = false)
> private String email;
> // UNIQUE constraint → database auto-creates a B-tree INDEX on email column!
>
> // PatientService calls:
> patientRepository.existsByEmail("john@test.com");
> // SQL: SELECT EXISTS(SELECT 1 FROM patient WHERE email = 'john@test.com')
> // WITHOUT index: Full table scan → O(n) — checks EVERY row
> // WITH index:    B-tree lookup   → O(log n) — binary search in tree
> //               10,000 patients: 10,000 comparisons vs ~14 comparisons!
> ```

**Q: What is the N+1 problem?**
> **A:** When fetching a list of entities, if each entity triggers a separate query for related data = 1 query for the list + N queries for related data. Solved with JOIN FETCH, `@EntityGraph`, or batch fetching. My project doesn't have relationships between entities, so this doesn't apply, but it's a common JPA issue.
>
> ```java
> // Example (NOT in your project, but common in JPA):
> @Entity
> public class Doctor {
>     @OneToMany(fetch = FetchType.LAZY)
>     private List<Patient> patients;  // Lazy loaded
> }
>
> // N+1 problem:
> List<Doctor> doctors = doctorRepository.findAll(); // 1 query: SELECT * FROM doctors
> for (Doctor d : doctors) {
>     d.getPatients();  // N queries! SELECT * FROM patients WHERE doctor_id = 1
>                       //             SELECT * FROM patients WHERE doctor_id = 2
>                       //             ... (one per doctor!)
> }
> // Total: 1 + N queries! If 100 doctors → 101 queries!
>
> // Fix: JOIN FETCH in one query:
> @Query("SELECT d FROM Doctor d JOIN FETCH d.patients")
> List<Doctor> findAllWithPatients();  // 1 query with JOIN
> ```

### Microservices

**Q: How do microservices discover each other?**
> **A:** (1) **Hardcoded URLs** (my project — services know each other's addresses). (2) **Service Discovery** (Eureka, Consul) — services register themselves and discover others dynamically. (3) **DNS-based** (Kubernetes) — each service gets a DNS name.
>
> ```java
> // YOUR PROJECT: Hardcoded URLs in application.properties:
> billing.service.address=billing-service   // Direct hostname
> billing.service.grpc.port=9001
>
> // With Eureka (Service Discovery):
> // 1. Each service registers on startup:
> //    "I am patient-service, running at 10.0.1.5:4000"
> //    "I am billing-service, running at 10.0.1.8:9001"
> // 2. To call billing, patient-service asks Eureka:
> //    "Where is billing-service?" → "10.0.1.8:9001"
> // 3. If billing-service moves to a new server, Eureka auto-updates!
>
> // With Kubernetes:
> // billing-service.default.svc.cluster.local  → DNS resolves automatically
> ```

**Q: What is Circuit Breaker pattern?**
> **A:** If a downstream service keeps failing, the circuit breaker "opens" and immediately fails requests without calling the service. After a timeout, it "half-opens" and tries one request. If successful, it "closes" again. Used with libraries like Resilience4j. My project doesn't implement it but should for the billing gRPC call.
>
> ```java
> // How it would look in your BillingServiceGrpcClient with Resilience4j:
> @CircuitBreaker(name = "billingService", fallbackMethod = "billingFallback")
> public BillingResponse createBillingAccount(BillingRequest request) {
>     return blockingStub.createBillingAccount(request);
>     // If billing-service fails 5 times in a row:
>     //   Circuit OPENS → next 30 seconds, this method is NOT called
>     //   → billingFallback() runs instead (instant, no timeout waiting)
>     //   After 30s → HALF-OPEN → tries ONE real call
>     //   If success → CLOSED (normal). If fail → OPEN again.
> }
>
> public BillingResponse billingFallback(BillingRequest request, Exception e) {
>     log.error("Billing service unavailable, using fallback");
>     return BillingResponse.newBuilder().setAccountId("PENDING").build();
> }
> ```

**Q: How would you implement rate limiting?**
> **A:** At the API Gateway level. Options: (1) Spring Cloud Gateway's built-in `RequestRateLimiter` filter with Redis. (2) Token bucket or sliding window algorithms. (3) Third-party services like AWS API Gateway.
>
> ```yaml
> # In api-gateway application.yml:
> spring:
>   cloud:
>     gateway:
>       routes:
>         - id: patient-service
>           uri: http://patient-service:4000
>           filters:
>             - name: RequestRateLimiter
>               args:
>                 redis-rate-limiter.replenishRate: 10  # 10 requests/second
>                 redis-rate-limiter.burstCapacity: 20  # Allow burst up to 20
>           # Uses Token Bucket algorithm:
>           # Bucket starts with 20 tokens. Each request uses 1 token.
>           # 10 tokens added per second. If bucket empty → 429 Too Many Requests.
> ```

### Docker

**Q: What is the difference between `CMD` and `ENTRYPOINT`?**
> **A:** `ENTRYPOINT` sets the main command (hard to override). `CMD` sets default arguments (easily overridden). My Dockerfile uses `ENTRYPOINT ["java", "-jar", "app.jar"]` — the app always runs Java, and you can't accidentally override it with a different command.
>
> ```dockerfile
> # Your Dockerfile:
> ENTRYPOINT ["java", "-jar", "app.jar"]
> # docker run patient-service               → runs: java -jar app.jar
> # docker run patient-service /bin/bash      → runs: java -jar app.jar /bin/bash (appends!)
>
> # If you used CMD instead:
> CMD ["java", "-jar", "app.jar"]
> # docker run patient-service               → runs: java -jar app.jar
> # docker run patient-service /bin/bash      → runs: /bin/bash (REPLACES CMD!)
>
> # Best practice (use both):
> ENTRYPOINT ["java", "-jar", "app.jar"]   # Always run Java
> CMD ["--spring.profiles.active=prod"]     # Default profile, overridable
> # docker run patient-service --spring.profiles.active=dev  → overrides CMD only
> ```

**Q: How do Docker containers communicate?**
> **A:** (1) **Docker Bridge Network** (default) — containers on the same network can reach each other by container name. (2) **Docker Compose** — creates a shared network; services use service names as hostnames (e.g., `http://auth-service:4005` in my gateway config). (3) **Host network** — containers share the host's network.
>
> ```yaml
> # In your api-gateway application.yml:
> spring:
>   cloud:
>     gateway:
>       routes:
>         - uri: http://auth-service:4005    # Container name as hostname!
>         - uri: http://patient-service:4000
>
> # Docker Compose creates a network: patient-management_default
> # All containers join this network and resolve each other by SERVICE NAME:
> #   auth-service    → 172.18.0.2:4005
> #   patient-service → 172.18.0.3:4000
> #   billing-service → 172.18.0.4:9001
> # No hardcoded IPs needed!
> ```

### Kafka

**Q: How does Kafka guarantee message ordering?**
> **A:** Kafka guarantees ordering within a **partition** only. A topic can have multiple partitions. Messages with the same key go to the same partition. In my project, the topic is "patient" — if I want ordering per patient, I'd use `patientId` as the key.
>
> ```java
> // Current code (no key → round-robin across partitions):
> kafkaTemplate.send("patient", event.toByteArray());
> // Msg1 → partition 0, Msg2 → partition 1, Msg3 → partition 0
> // Order NOT guaranteed across partitions!
>
> // With key (ordering per patient guaranteed):
> kafkaTemplate.send("patient", patientId, event.toByteArray());
> //                  topic      key         value
> // All events for patient "abc" → always partition 0 (hash("abc") % numPartitions)
> // All events for patient "xyz" → always partition 1
> // Within each partition: FIFO ordering guaranteed!
> ```

**Q: What is the difference between `at-most-once`, `at-least-once`, and `exactly-once` delivery?**
> **A:** **At-most-once:** Message may be lost but never duplicated (commit offset before processing). **At-least-once:** Message never lost but may be duplicated (process before committing offset — my project's default). **Exactly-once:** No loss, no duplication (requires transactional processing). Kafka supports all three; the choice affects performance and complexity.
>
> ```java
> // AT-MOST-ONCE:
> consumer.commitOffset(43);        // Step 1: mark as "done"
> process(message);                  // Step 2: process
> // If crash between step 1 and 2 → message LOST (offset moved but not processed)
>
> // AT-LEAST-ONCE (your project's default):
> process(message);                  // Step 1: process
> consumer.commitOffset(43);         // Step 2: mark as "done"
> // If crash between step 1 and 2 → message processed AGAIN (duplicate)
> // Your KafkaConsumer:
> @KafkaListener(topics = "patient")
> public void consumeEvent(byte[] event) {
>     PatientEvent e = PatientEvent.parseFrom(event);  // Process first
>     log.info("Received: {}", e.getPatientId());      // Then auto-commit
> }
>
> // EXACTLY-ONCE:
> // Requires Kafka transactions + idempotent producers (most complex)
> ```

### gRPC

**Q: What are the 4 types of gRPC communication?**
> **A:** (1) **Unary** (my project) — client sends one request, server sends one response. (2) **Server streaming** — client sends one request, server sends a stream of responses. (3) **Client streaming** — client sends a stream, server sends one response. (4) **Bidirectional streaming** — both send streams simultaneously.
>
> ```protobuf
> // In your billing_service.proto:
> service BillingService {
>   rpc CreateBillingAccount (BillingRequest) returns (BillingResponse);  // UNARY
> }
> ```
> ```protobuf
> // All 4 types in protobuf syntax:
> service Example {
>   rpc Unary (Request) returns (Response);                    // 1:1
>   rpc ServerStream (Request) returns (stream Response);      // 1:many
>   rpc ClientStream (stream Request) returns (Response);      // many:1
>   rpc BiDiStream (stream Request) returns (stream Response); // many:many
> }
> // The "stream" keyword makes it streaming!
> // Your project uses Unary (simplest): one request → one response
> ```

---

## 37. Project-Specific Interview Questions — Deep Dive

> These are the questions an interviewer will ask **specifically about YOUR Patient Management System**. They go beyond generic concept questions and probe your understanding of design decisions, trade-offs, failure scenarios, and the actual code you wrote.

---

### Architecture & Design Decisions

**Q1: Walk me through your overall architecture. Why did you choose microservices over a monolith?**

> **A:** My Patient Management System has 5 microservices:
> - **Patient Service** (port 4000) — CRUD operations, JPA, gRPC client, Kafka producer
> - **Auth Service** (port 4005) — JWT authentication, BCrypt password hashing
> - **API Gateway** (port 4004) — Spring Cloud Gateway, route matching, JWT validation filter
> - **Billing Service** (port 4001 HTTP, 9001 gRPC) — gRPC server for billing account creation
> - **Analytics Service** (port 4002) — Kafka consumer for patient event logging
>
> **Why microservices?** Each service has a single responsibility, can be deployed independently, can scale independently (e.g., Patient Service handles more traffic than Billing), and can use different technologies if needed. If one service crashes (e.g., Analytics), the others keep working.
>
> **Honest answer:** For a project this size, a monolith would actually be simpler. I chose microservices to learn inter-service communication patterns (REST, gRPC, Kafka) and demonstrate understanding of distributed systems.

---

**Q2: Why did you use gRPC for the Billing Service but Kafka for the Analytics Service? Why not REST for both?**

> **A:** It's about the **communication pattern required**:
>
> | | Billing (gRPC) | Analytics (Kafka) |
> |---|---|---|
> | **Pattern** | Synchronous (request-response) | Asynchronous (fire-and-forget) |
> | **Why?** | Patient creation MUST wait for billing account creation — it's part of the same logical operation | Analytics is just logging — patient creation shouldn't fail or wait because analytics is busy |
> | **What if it's down?** | Patient creation fails (which is correct — no patient without a billing account) | Patient is still created; analytics event is queued in Kafka and processed later |
> | **Performance** | gRPC uses HTTP/2 + binary Protobuf — much faster than REST + JSON | Kafka gives durability — events survive service restarts |
>
> I could have used REST for billing, but gRPC is ~10x faster due to binary serialization and HTTP/2 multiplexing. For analytics, REST would mean the patient service waits unnecessarily; Kafka decouples the services completely.

---

**Q3: Explain the API Gateway pattern. What does your gateway actually do?**

> **A:** The API Gateway is the **single entry point** for all client requests. Clients never talk directly to backend services. My gateway does 3 things:
>
> 1. **Route matching** — Maps external URLs to internal services:
>    - `/api/patients/**` → `http://patient-service:4000`
>    - `/auth/**` → `http://auth-service:4005`
>    - `/api/billing/**` → `http://billing-service:4001`
>
> 2. **JWT validation** — My custom `JwtValidationGatewayFilterFactory` intercepts every request to protected routes, extracts the `Authorization: Bearer <token>` header, calls the Auth Service's `/auth/validate` endpoint, and either passes the request through or returns 401 Unauthorized.
>
> 3. **Decoupling** — Clients only know one URL (`localhost:4004`). Backend services can move, scale, or change ports without clients knowing.
>
> The auth routes (`/auth/**`) are configured **without** the JWT filter — otherwise you couldn't even log in (chicken-and-egg problem).

---

**Q4: What happens if the Billing Service is down when a patient is being created?**

> **A:** The `BillingServiceGrpcClient.createBillingAccount()` call will throw a `StatusRuntimeException` from gRPC. Currently, this exception propagates up through the `PatientService` and results in a **500 Internal Server Error** response.
>
> **The problem:** The patient has already been saved to the database (line `Patient savedPatient = patientRepository.save(...)` happens before the gRPC call). So we have a **data inconsistency** — a patient exists without a billing account.
>
> **How I would fix it:**
> 1. **Database transaction rollback** — Wrap the entire operation in `@Transactional` so if gRPC fails, the patient save is rolled back
> 2. **Circuit breaker** — Use Resilience4j to detect that Billing Service is down and fail fast instead of waiting for timeout
> 3. **Saga pattern** — Use compensating transactions: if billing fails, delete the patient
> 4. **Outbox pattern** — Save a "pending billing" event in the same DB transaction, then process it asynchronously

---

**Q5: What's the data consistency issue in your create patient flow? How would you solve it?**

> **A:** In `PatientService.createPatient()`, the execution order is:
> ```
> 1. Validate DTO
> 2. Check email uniqueness
> 3. Save patient to DB          ← COMMITTED
> 4. Call Billing gRPC            ← CAN FAIL
> 5. Publish Kafka event          ← CAN FAIL
> 6. Return response
> ```
>
> If step 4 or 5 fails, the patient already exists in the database. This means:
> - **Patient without billing account** if gRPC fails
> - **Patient without analytics event** if Kafka fails (less critical)
>
> **Solutions:**
> - **`@Transactional` annotation** — Rolls back DB save if any step fails. But gRPC and Kafka are not part of the DB transaction, so this only partially helps.
> - **Saga pattern** — Define compensating actions: if billing fails → delete patient
> - **Transactional outbox** — Save the event in the same DB transaction as the patient. A separate process reads the outbox and sends to Kafka/gRPC. This guarantees atomicity.
> - **2-Phase Commit** — Coordinate distributed transaction across services (complex and slow — rarely used in microservices)

---

### Authentication & Security

**Q6: Walk me through what happens from login to accessing a protected endpoint.**

> **A:** Complete flow:
>
> **Step 1: Login**
> - Client sends `POST /auth/login` with `{ "email": "admin@test.com", "password": "password123" }`
> - Auth Service receives it → `AuthController.login()` → `AuthService.authenticate()`
> - `UserService.findByEmail()` queries the DB → finds the user
> - `BCryptPasswordEncoder.matches("password123", "$2a$10$...")` verifies password against stored hash
> - `JwtUtil.generateToken(email, role)` creates a JWT with `sub=admin@test.com`, `role=ADMIN`, `exp=24h`, signed with a 256-bit secret key
> - Returns `{ "token": "eyJhbGciOiJIUzI1NiJ9..." }`
>
> **Step 2: Access Protected Endpoint**
> - Client sends `GET /api/patients` with header `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...`
> - Request hits API Gateway (port 4004)
> - Gateway's `JwtValidationGatewayFilterFactory.apply()` intercepts the request
> - Filter extracts the token from the Authorization header
> - Filter calls Auth Service: `GET /auth/validate?token=eyJhbGciOiJIUzI1NiJ9...`
> - Auth Service's `AuthController.validateToken()` → `JwtUtil.validateToken()` verifies signature, checks expiry
> - If valid → Auth Service returns 200 → Gateway passes request to Patient Service
> - If invalid → Auth Service returns error → Gateway returns 401 Unauthorized

---

**Q7: How does JWT work in your project? Can you decode one?**

> **A:** A JWT has 3 parts separated by dots: `header.payload.signature`
>
> ```
> Header:  {"alg": "HS256"}                        → Base64 → eyJhbGciOiJIUzI1NiJ9
> Payload: {"sub": "admin@test.com",                → Base64 → eyJzdWIiOiJhZG1pbkB...
>           "role": "ADMIN",
>           "iat": 1700000000,
>           "exp": 1700086400}
> Signature: HMACSHA256(base64(header) + "." +      → Prevents tampering
>            base64(payload), secretKey)
> ```
>
> **Important:** The payload is **NOT encrypted** — anyone can Base64-decode it and read the email/role. The signature only guarantees **integrity** (nobody tampered with it) and **authenticity** (it was created by our Auth Service's secret key).
>
> In my code, `JwtUtil` uses the `io.jsonwebtoken (jjwt 0.12.6)` library. The secret key is stored in `application.properties` as a plain string and converted to a `SecretKey` using `Keys.hmacShaKeyFor(secretKey.getBytes())`.

---

**Q8: Why did you store the JWT secret key in `application.properties`? Is that secure?**

> **A:** No, it's **not secure for production**. The secret key is currently hardcoded as a plain text string:
> ```properties
> jwt.secret=YTJkOGU0ZjVhNmIzMmMxZDdlOGZhNWI5MTJjNGUwNTY3Nzg5YWJjZGVmMTIzNDU2Nzg5MGFiY2RlZjEyMzQ1Ng==
> ```
>
> **For production, I would:**
> 1. Use **environment variables**: `jwt.secret=${JWT_SECRET}` and set it at deployment
> 2. Use **Spring Cloud Config** for centralized configuration
> 3. Use **AWS Secrets Manager** or **HashiCorp Vault** for secret management
> 4. Use **RSA key pairs** instead of HMAC — the Auth Service holds the private key (signs), and other services hold the public key (verify). This way, even if a service is compromised, it can't create new tokens.

---

**Q9: Why BCrypt for password hashing? Why not SHA-256?**

> **A:** BCrypt is specifically designed for passwords; SHA-256 is not.
>
> | Feature | BCrypt | SHA-256 |
> |---------|--------|---------|
> | Speed | Intentionally SLOW | Very FAST |
> | Salt | Built-in random salt per password | Must add salt manually |
> | Work factor | Configurable (can be made slower as hardware improves) | Fixed speed |
> | Rainbow table attack | Immune (each hash is unique due to salt) | Vulnerable without salt |
>
> In my code: `new BCryptPasswordEncoder().encode("password123")` → `$2a$10$random_salt_here...hash_here`
>
> The `$2a$10$` means BCrypt version 2a with cost factor 10 (2^10 = 1024 rounds). An attacker can try billions of SHA-256 hashes per second, but only thousands of BCrypt hashes per second.

---

**Q10: Your Auth Service's `SecurityConfig` disables CSRF and permits all requests. Isn't that insecure?**

> **A:** My `SecurityConfig` does:
> ```java
> http.csrf(csrf -> csrf.disable())
>     .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
> ```
>
> **Why CSRF is disabled:** CSRF protection is for browser-based sessions with cookies. My project uses **stateless JWT tokens** sent in headers — the browser doesn't automatically attach them like cookies, so CSRF attacks don't apply.
>
> **Why permitAll():** The Auth Service itself doesn't do authorization checking — it only provides login + token validation endpoints. The **actual authorization** happens at the **API Gateway** level (JWT filter), not at the Auth Service level. If I had role-based access (e.g., only ADMIN can delete patients), I'd implement that in the Patient Service's security config using the role from the JWT.

---

### Patient Service Deep Dive

**Q11: Explain your validation strategy. What are validation groups and why did you use them?**

> **A:** The problem: When **creating** a patient, the `registeredDate` is required. When **updating**, it should be optional (you're only updating certain fields).
>
> My solution uses **Jakarta Bean Validation groups**:
> ```java
> // Marker interface (empty — it's just a label)
> public interface CreatePatientValidationGroup {}
>
> // In PatientRequestDTO:
> @NotNull(groups = CreatePatientValidationGroup.class)
> private LocalDate registeredDate;  // Only required during creation
>
> @NotBlank  // No group = applies to ALL operations
> private String name;               // Always required
> ```
>
> In the controller:
> - `@Validated(CreatePatientValidationGroup.class)` on the POST (create) method — validates `registeredDate`
> - `@Validated` on the PUT (update) method — only validates fields without groups (`name`, `email`, `address`)
>
> This avoids needing separate `CreatePatientDTO` and `UpdatePatientDTO` classes.

---

**Q12: How do you handle duplicate email addresses?**

> **A:** In `PatientService.createPatient()`:
> ```java
> if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
>     throw new EmailAlreadyExistsException("A patient with this email already exists: "
>         + patientRequestDTO.getEmail());
> }
> ```
>
> The `existsByEmail()` method is a **Spring Data JPA derived query** — Spring auto-generates the SQL: `SELECT COUNT(*) > 0 FROM patient WHERE email = ?`. No implementation code needed.
>
> The `EmailAlreadyExistsException` is a custom exception caught by `GlobalExceptionHandler`:
> ```java
> @ExceptionHandler(EmailAlreadyExistsException.class)
> public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(...) {
>     return ResponseEntity.status(HttpStatus.CONFLICT)  // 409
>         .body(Map.of("error", ex.getMessage()));
> }
> ```
>
> **Potential race condition:** If two requests try to create patients with the same email simultaneously, both `existsByEmail()` checks could return `false`, and both saves would proceed. One would fail at the DB level (if a unique constraint exists) or both would succeed (data corruption). **Fix:** Add `@Column(unique = true)` on the `email` field in the `Patient` entity and handle `DataIntegrityViolationException`, OR use a database-level unique index.

---

**Q13: Your `PatientMapper` is a utility class with static methods. Why not make it a Spring `@Component`?**

> **A:** `PatientMapper` has only static methods and no state or dependencies:
> ```java
> public class PatientMapper {
>     public static PatientResponseDTO toDTO(Patient patient) { ... }
>     public static Patient toModel(PatientRequestDTO dto) { ... }
> }
> ```
>
> Making it a `@Component` would add it to the Spring container unnecessarily. It has no dependencies to inject, no configuration to manage, no lifecycle to control.
>
> **When I WOULD use `@Component`:** If the mapper needed to call another service or repository (e.g., translating a country code to a country name by querying a DB), then it needs dependency injection and should be a Spring bean.
>
> **Alternative approach:** Libraries like **MapStruct** generate mapper implementations at compile time — they're more scalable for large DTOs with many fields.

---

**Q14: Explain your exception handling strategy with `@RestControllerAdvice`.**

> **A:** I use **global exception handling** — a single class catches all exceptions from all controllers:
>
> ```java
> @RestControllerAdvice
> public class GlobalExceptionHandler {
>     @ExceptionHandler(EmailAlreadyExistsException.class) → 409 CONFLICT
>     @ExceptionHandler(PatientNotFoundException.class)    → 404 NOT FOUND
>     @ExceptionHandler(MethodArgumentNotValidException.class) → 400 BAD REQUEST (validation)
>     @ExceptionHandler(Exception.class)                   → 500 INTERNAL SERVER ERROR (catch-all)
> }
> ```
>
> **How it works:** `@RestControllerAdvice` = `@ControllerAdvice` + `@ResponseBody`. Spring intercepts any exception thrown from a controller and checks if any handler matches. The most specific match wins. So `EmailAlreadyExistsException` hits its specific handler, not the generic `Exception` handler.
>
> **Why this pattern?**
> - Controllers stay clean — no try-catch blocks
> - Consistent error response format across all endpoints
> - All error handling logic is centralized in one place
> - Easy to add new exception types

---

**Q15: What's the difference between `Patient` (entity) and `PatientRequestDTO` / `PatientResponseDTO`? Why three classes?**

> **A:**
> | Class | Purpose | What it contains |
> |-------|---------|-----------------|
> | `Patient` | JPA entity — maps to DB table | All fields + JPA annotations (`@Entity`, `@Id`, `@GeneratedValue`) |
> | `PatientRequestDTO` | What client SENDS | Only fields client should set + validation annotations |
> | `PatientResponseDTO` | What client RECEIVES | Only fields client should see (includes `id`) |
>
> **Why not just use `Patient` everywhere?**
> 1. **Security** — `Patient` entity has an `id` field with `@GeneratedValue`. If the client sends `"id": "some-uuid"` in a POST request and you use `Patient` directly, JPA might use that ID instead of generating one.
> 2. **Decoupling** — If I rename a DB column, the API response shouldn't change. DTOs insulate the API from DB changes.
> 3. **Flexibility** — Response might include computed fields (e.g., `age` calculated from `dateOfBirth`) that don't exist in the DB.
> 4. **Validation** — Validation annotations on a DTO are clean; mixing them with JPA annotations on an entity is messy.

---

### gRPC & Inter-Service Communication

**Q16: Walk me through the gRPC flow from Patient Service to Billing Service.**

> **A:**
>
> **1. Proto Definition** (`billing_service.proto`):
> ```protobuf
> service BillingService {
>     rpc CreateBillingAccount(CreateBillingAccountRequest) returns (CreateBillingAccountResponse);
> }
> ```
>
> **2. Maven Compilation** — The `protobuf-maven-plugin` reads this `.proto` file and auto-generates Java classes: `BillingServiceGrpc` (stub + base class), `CreateBillingAccountRequest`, `CreateBillingAccountResponse`.
>
> **3. Billing Service (Server Side)** — `BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase`:
> ```java
> @Override
> public void createBillingAccount(CreateBillingAccountRequest request,
>                                   StreamObserver<CreateBillingAccountResponse> responseObserver) {
>     // Build response
>     responseObserver.onNext(response);    // Send response
>     responseObserver.onCompleted();       // Close stream
> }
> ```
>
> **4. Patient Service (Client Side)** — `BillingServiceGrpcClient`:
> ```java
> ManagedChannel channel = ManagedChannelBuilder.forAddress("billing-service", 9001)
>     .usePlaintext().build();
> BillingServiceGrpc.BillingServiceBlockingStub stub =
>     BillingServiceGrpc.newBlockingStub(channel);
> CreateBillingAccountResponse response = stub.createBillingAccount(request);
> ```
>
> **Key point:** The `ManagedChannel` is created in `@PostConstruct` (once at startup) and reused for all requests. The `BlockingStub` means the call is synchronous — the patient service thread waits for the billing response.

---

**Q17: What is Protocol Buffers and why is it faster than JSON?**

> **A:** In my project, the `.proto` files define the message format:
> ```protobuf
> message CreateBillingAccountRequest {
>     string patient_id = 1;    // "1" is the field number, not a default value
>     string patient_name = 2;
>     string patient_email = 3;
> }
> ```
>
> **Why faster than JSON:**
> | | JSON | Protobuf |
> |---|---|---|
> | Format | Text-based: `{"patient_id": "abc"}` | Binary: `[0a 03 61 62 63]` |
> | Field names | Sent every time | Replaced by field numbers (1-3 bytes) |
> | Size | ~100 bytes for this message | ~20 bytes |
> | Parsing | String parsing (slow) | Direct binary read (fast) |
> | Schema | Optional (loose) | Required (strict, type-safe) |
>
> Field numbers (1, 2, 3) are what get sent over the wire — the names (`patient_id`) are only for human readability in the `.proto` file. This is why you should never change field numbers in production.

---

**Q18: What is the `StreamObserver` pattern in gRPC? Why not just use `return`?**

> **A:** gRPC uses the **Observer pattern** for responses because it supports streaming:
> ```java
> public void createBillingAccount(Request request,
>                                   StreamObserver<Response> responseObserver) {
>     responseObserver.onNext(response);      // "Here's a response"
>     responseObserver.onCompleted();          // "I'm done sending"
> }
> ```
>
> For **unary** calls (my project), `onNext()` is called once and then `onCompleted()`. But the same interface supports:
> - **Server streaming:** Call `onNext()` multiple times, then `onCompleted()`
> - **Error:** Call `responseObserver.onError(exception)` instead
>
> The `StreamObserver` is a **callback** — gRPC framework calls `onNext()` on the client side when the server sends data. The reason you can't just `return` is that gRPC needs to handle the response asynchronously and support streaming — a simple return value can't do that.

---

### Kafka & Event-Driven Architecture

**Q19: What happens to Kafka messages when the Analytics Service is down?**

> **A:** This is one of Kafka's biggest advantages — **messages are persisted on disk**. When Patient Service publishes a `patient.created` event, Kafka stores it in the `patient` topic's partition log on the **broker** (not on the analytics service).
>
> When Analytics Service comes back up, the Kafka consumer resumes from its **last committed offset** and processes all missed messages in order.
>
> In my code, the consumer uses `@KafkaListener(topics = "patient", groupId = "analytics-service")`:
> - `topics = "patient"` — listens to the "patient" topic
> - `groupId = "analytics-service"` — Kafka tracks which messages this group has consumed
>
> **Default retention:** Kafka keeps messages for 7 days (configurable). Even if Analytics Service is down for a week, no data is lost.

---

**Q20: How does your Kafka producer serialize messages? What format are they in?**

> **A:** Looking at `KafkaProducer.java`:
> ```java
> kafkaTemplate.send("patient", event.toString());
> ```
> And `patient_event.proto` defines the event format, but the actual serialization uses `event.toString()` which produces Protobuf's **text format** (human-readable, not binary).
>
> **This is actually a design choice:** I'm using Protobuf to define the message structure but serializing as a string. Alternatively, I could:
> 1. Use `event.toByteArray()` with a `ByteArraySerializer` — true binary Protobuf (most efficient)
> 2. Use JSON serialization with `JsonSerializer` — more common in Spring Kafka
> 3. Use Confluent's `KafkaProtobufSerializer` — integrates with Schema Registry
>
> On the consumer side in `KafkaConsumer.java`, the message is received as a `String` and logged. In a real system, I'd deserialize it back to a `PatientEvent` object for structured processing.

---

**Q21: If you needed to guarantee that patient events are processed in order, how would you do it?**

> **A:** Kafka guarantees ordering **within a partition** only. My `patient` topic might have multiple partitions.
>
> To guarantee ordering per patient:
> ```java
> // Use patientId as the key — same key always goes to same partition
> kafkaTemplate.send("patient", patientId, event.toString());
> ```
>
> All events for patient "abc-123" go to the same partition → consumed in order.
>
> **Trade-off:** If I use one partition, I get perfect ordering but no parallelism. With multiple partitions, I get parallelism but only per-key ordering. My current code doesn't specify a key, so Kafka uses round-robin partition assignment (no ordering guarantee).

---

### Database & JPA

**Q22: Explain how `data.sql` works. When does it run?**

> **A:** My `patient-service/src/main/resources/data.sql` has ~10 INSERT statements that pre-populate the patient table.
>
> **When it runs:** Spring Boot auto-executes `data.sql` at startup AFTER the schema is created. With `spring.jpa.defer-datasource-initialization=true` in my `application.properties`, Spring ensures:
> 1. JPA/Hibernate creates the tables from `@Entity` annotations
> 2. Then `data.sql` runs to populate data
>
> Without `defer-datasource-initialization=true`, `data.sql` runs BEFORE JPA creates tables → fails because the table doesn't exist yet.
>
> **For H2 (in-memory):** Data is lost on restart, so `data.sql` runs every time — perfect for development.
> **For PostgreSQL (production):** You'd want to use Flyway or Liquibase for migrations instead, and `data.sql` only for initial seed data.

---

**Q23: Your `Patient` entity uses `@GeneratedValue(strategy = GenerationType.UUID)`. What other strategies exist and why UUID?**

> **A:**
> | Strategy | How it works | When to use |
> |----------|-------------|-------------|
> | `UUID` (my project) | JPA generates a random UUID | Distributed systems — no central sequence needed |
> | `IDENTITY` | Database auto-increment (1, 2, 3...) | Simple apps with single DB |
> | `SEQUENCE` | DB sequence object | PostgreSQL, Oracle — batch-friendly |
> | `TABLE` | Separate table stores the next ID | Legacy — avoid it (slow) |
> | `AUTO` | JPA picks the best strategy | When you don't care |
>
> **Why UUID for my project?** In a microservices architecture, multiple instances of Patient Service might be inserting simultaneously. Auto-increment requires a centralized counter (the DB), which becomes a bottleneck. UUIDs are generated independently — no coordination needed. They're also non-guessable, unlike sequential IDs (`/patients/1`, `/patients/2`...).

---

**Q24: What's the difference between `JpaRepository`, `CrudRepository`, and `Repository`?**

> **A:** They form an inheritance chain:
> ```
> Repository (marker — empty)
>   └── CrudRepository (basic CRUD: save, findById, delete, count)
>       └── ListCrudRepository (returns List instead of Iterable)
>           └── JpaRepository (adds: flush, saveAndFlush, findAll with Sort/Pageable, batch deletes)
> ```
>
> My project uses `JpaRepository<Patient, UUID>`:
> - `Patient` — the entity type
> - `UUID` — the primary key type
>
> I get ALL these methods for free: `save()`, `findById()`, `findAll()`, `deleteById()`, `existsById()`, `count()`, plus I added `existsByEmail()` and `existsByEmailAndIdNot()` as derived queries.

---

### Docker & Deployment

**Q25: Explain your Dockerfile. What is multi-stage build and why use it?**

> **A:** My Dockerfile has two stages:
> ```dockerfile
> # Stage 1: BUILD (large image — has Maven, JDK, source code)
> FROM maven:3.9.9-eclipse-temurin-21 AS build
> COPY . .
> RUN mvn clean package -DskipTests
>
> # Stage 2: RUN (small image — only JRE + the JAR)
> FROM eclipse-temurin:21-jre
> COPY --from=build target/*.jar app.jar
> ENTRYPOINT ["java", "-jar", "app.jar"]
> ```
>
> **Why multi-stage?**
> | | Without multi-stage | With multi-stage |
> |---|---|---|
> | Image size | ~800MB (includes Maven, JDK, source code, .git) | ~300MB (only JRE + JAR) |
> | Security | Exposes source code and build tools | Production image has no build tools |
> | Build time | Same | Same (building happens in stage 1) |
>
> The `COPY --from=build` copies ONLY the compiled JAR from stage 1. Everything else (Maven, source code, test files) is discarded.

---

**Q26: How do your Docker containers communicate with each other?**

> **A:** Looking at my `api-gateway`'s `application.yml`:
> ```yaml
> uri: http://patient-service:4000
> uri: http://auth-service:4005
> uri: http://billing-service:4001
> ```
>
> The services use **container names** as hostnames (not `localhost`). In Docker Compose (or Docker network), containers on the same network can resolve each other by name. Docker has a built-in DNS server.
>
> For gRPC, the Patient Service connects to:
> ```java
> ManagedChannelBuilder.forAddress("billing-service", 9001)
> ```
>
> **Why not `localhost`?** Each container has its own network namespace. `localhost` inside the Patient Service container refers to the Patient Service container itself, not the Billing Service.

---

### Error Handling & Edge Cases

**Q27: What are all the error responses your API can return? List every error path.**

> **A:**
> | Error | HTTP Status | When it happens | Handler |
> |-------|-------------|-----------------|---------|
> | Validation failure | 400 Bad Request | Missing required field, invalid email format | `MethodArgumentNotValidException` handler |
> | Invalid/expired JWT | 401 Unauthorized | Token expired, tampered, or missing | API Gateway JWT filter |
> | Wrong credentials | 401 Unauthorized | Login with wrong email/password | `AuthService` catches and returns error |
> | Patient not found | 404 Not Found | GET/PUT/DELETE with non-existent UUID | `PatientNotFoundException` handler |
> | Duplicate email | 409 Conflict | CREATE with email that already exists | `EmailAlreadyExistsException` handler |
> | Billing service down | 500 Internal Server Error | gRPC call fails | Unhandled `StatusRuntimeException` |
> | Kafka broker down | 500 Internal Server Error | Kafka publish fails | Unhandled exception |
> | Any unexpected error | 500 Internal Server Error | NullPointer, DB issue, etc. | Generic `Exception` handler |

---

**Q28: What happens during your UPDATE flow if someone tries to change the email to one that already exists?**

> **A:** In `PatientService.updatePatient()`:
> ```java
> if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), patientId)) {
>     throw new EmailAlreadyExistsException("...");
> }
> ```
>
> The `existsByEmailAndIdNot(email, id)` method checks: "Does any patient **other than this one** have this email?" The `IdNot` part is crucial — without it, updating a patient without changing their email would throw an error (because their own email already exists in the DB).
>
> Spring Data JPA generates: `SELECT COUNT(*) > 0 FROM patient WHERE email = ? AND id != ?`

---

### Improvements & Production Readiness

**Q29: If you were to deploy this to production, what changes would you make?**

> **A:**
> 1. **Database** — Switch from H2 to PostgreSQL (profile-based config with `application-prod.properties`)
> 2. **Secrets** — Move JWT secret and DB credentials to environment variables or AWS Secrets Manager
> 3. **Circuit breaker** — Add Resilience4j around the gRPC call to handle Billing Service failures gracefully
> 4. **Service discovery** — Replace hardcoded URLs with Eureka or Kubernetes DNS
> 5. **Caching** — Add Redis for frequently accessed patient data
> 6. **Rate limiting** — API Gateway rate limiting to prevent DDoS
> 7. **Health checks** — Spring Actuator `/health` endpoint for container orchestration
> 8. **Distributed tracing** — Zipkin/Jaeger for tracking requests across services
> 9. **Centralized logging** — ELK stack (Elasticsearch + Logstash + Kibana)
> 10. **CI/CD** — GitHub Actions → build → test → push Docker images → deploy to Kubernetes
> 11. **HTTPS/TLS** — Encrypt all traffic, especially gRPC (currently using `usePlaintext()`)
> 12. **Database migrations** — Flyway instead of `data.sql`

---

**Q30: Your gRPC client creates a `ManagedChannel` in `@PostConstruct`. What if the billing service URL changes?**

> **A:** Currently, the billing service address is hardcoded:
> ```java
> @Value("${billing.service.address:billing-service}")
> private String billingServiceAddress;
>
> @Value("${billing.service.grpc.port:9001}")
> private int billingServiceGrpcPort;
> ```
>
> It reads from `application.properties` with defaults. If the URL changes, I'd need to restart the service.
>
> **Better approaches:**
> 1. **Service discovery (Eureka/Consul)** — Service registers itself; client looks up the address dynamically
> 2. **Kubernetes service** — K8s DNS resolves `billing-service` to the correct pod IP automatically
> 3. **gRPC name resolver** — gRPC has built-in support for DNS-based and custom resolvers
> 4. **Spring Cloud LoadBalancer** — For client-side load balancing across multiple Billing Service instances

---

### Scenario-Based Questions

**Q31: A customer reports that creating a patient sometimes works and sometimes returns 500. How would you debug this?**

> **A:** This is likely the **Billing Service gRPC call** failing intermittently.
>
> **Debugging steps:**
> 1. **Check logs** — Look at Patient Service logs for `StatusRuntimeException` stack traces
> 2. **Check Billing Service** — Is it running? Is port 9001 accessible?
> 3. **Check the gRPC channel** — The `ManagedChannel` might be in a broken state if the connection was lost
> 4. **Check resource limits** — Billing Service might be running out of memory/threads under load
> 5. **Network issues** — Docker networking might have DNS resolution failures
>
> **Fix:** Add:
> - Retry logic with exponential backoff
> - Circuit breaker to fail fast instead of waiting for timeout
> - Health check endpoint for the Billing Service
> - Structured logging with correlation IDs to trace requests across services

---

**Q32: The API is slow. Where would you look for bottlenecks?**

> **A:** Potential bottlenecks in order of likelihood:
>
> 1. **Database queries** — `findAll()` returns ALL patients with no pagination. Add `Pageable` parameter.
> 2. **gRPC call in create flow** — Synchronous call blocks the thread. If Billing Service is slow, Patient Service becomes slow.
> 3. **JWT validation at gateway** — Every request makes an HTTP call to Auth Service. Cache validated tokens with TTL.
> 4. **Missing database indexes** — `existsByEmail()` does a full table scan without an index on `email`.
> 5. **N+1 query problem** — Not currently an issue (no relationships), but adding relationships could trigger it.
> 6. **Kafka producer** — If Kafka broker is slow, `kafkaTemplate.send()` blocks.
>
> **How to diagnose:** Add Spring Actuator + Micrometer metrics, enable SQL logging (`spring.jpa.show-sql=true`), use distributed tracing (Zipkin) to see where time is spent across services.

---

**Q33: How would you add role-based access control (e.g., only ADMINs can delete patients)?**

> **A:** My JWT already contains the `role` claim. Here's what I'd add:
>
> **Option 1: At the API Gateway** — The JWT filter already calls `/auth/validate`. Extend it to extract the `role` claim and add it as a request header:
> ```java
> exchange.getRequest().mutate().header("X-User-Role", role).build();
> ```
> Then in Patient Service's controller:
> ```java
> @DeleteMapping("/{id}")
> public ResponseEntity<Void> delete(@PathVariable UUID id,
>                                     @RequestHeader("X-User-Role") String role) {
>     if (!"ADMIN".equals(role)) return ResponseEntity.status(403).build();
>     ...
> }
> ```
>
> **Option 2: Spring Security in Patient Service** — Add a security filter that reads the JWT (or the forwarded header) and uses `@PreAuthorize("hasRole('ADMIN')")` on the delete method.
>
> **Option 2 is better** because it's declarative and Spring handles it automatically.

---

**Q34: How would you add pagination to the GET all patients endpoint?**

> **A:** Change the repository and controller:
>
> ```java
> // Repository — already supported by JpaRepository
> Page<Patient> findAll(Pageable pageable);
>
> // Controller
> @GetMapping
> public ResponseEntity<Page<PatientResponseDTO>> getPatients(
>         @RequestParam(defaultValue = "0") int page,
>         @RequestParam(defaultValue = "10") int size) {
>     Page<Patient> patients = patientService.getPatients(PageRequest.of(page, size));
>     Page<PatientResponseDTO> dtos = patients.map(PatientMapper::toDTO);
>     return ResponseEntity.ok(dtos);
> }
> ```
>
> Client calls: `GET /api/patients?page=0&size=10`
>
> Response includes: content (the patients), totalElements, totalPages, number (current page), size.

---

**Q35: What if you need to support both SQL and NoSQL databases in different services?**

> **A:** That's exactly why microservices exist — **polyglot persistence**. Each service manages its own database and can use any technology:
>
> - **Patient Service** → PostgreSQL (relational — structured patient data)
> - **Analytics Service** → MongoDB (document store — flexible log entries)
> - **Billing Service** → PostgreSQL (relational — financial data needs ACID)
>
> Services never share databases directly. They communicate via APIs (REST/gRPC) or events (Kafka). This is called the **Database per service** pattern. My project already follows this — Patient Service and Auth Service each have their own H2/PostgreSQL instance.

---

### Tricky/Gotcha Questions

**Q36: Your `PatientMapper.toModel()` doesn't set the `id`. Why?**

> **A:** Because the entity uses `@GeneratedValue(strategy = GenerationType.UUID)`:
> ```java
> @Id
> @GeneratedValue(strategy = GenerationType.UUID)
> private UUID id;
> ```
>
> JPA generates the UUID automatically when `patientRepository.save()` is called. If the mapper set an ID, and the client sent a malicious ID, it could overwrite an existing patient. The mapper intentionally excludes `id` from the request → model mapping.

---

**Q37: What happens if the Kafka broker is down when a patient is created?**

> **A:** The `kafkaTemplate.send("patient", event.toString())` will throw an exception. Since this call happens AFTER the patient is saved and the billing account is created, the patient exists but the analytics event is lost.
>
> **Current behavior:** The exception propagates up and the client gets a 500 error — even though the patient WAS created successfully.
>
> **Better approach:**
> 1. Wrap Kafka send in try-catch and log the failure (don't fail the whole request for a non-critical operation)
> 2. Use the **Transactional Outbox** pattern — save the event to a DB table first, then a separate process sends it to Kafka
> 3. Use Kafka's built-in retry mechanism with `spring.kafka.producer.retries=3`

---

**Q38: Your `@PostConstruct` in `BillingServiceGrpcClient` creates the channel once. What about connection pooling or load balancing?**

> **A:** gRPC's `ManagedChannel` already handles connection management internally — it maintains a pool of HTTP/2 connections and multiplexes RPC calls across them. A single `ManagedChannel` can handle thousands of concurrent RPCs.
>
> For **load balancing** across multiple Billing Service instances:
> ```java
> ManagedChannelBuilder.forTarget("dns:///billing-service:9001")
>     .defaultLoadBalancingPolicy("round_robin")
>     .build();
> ```
>
> gRPC supports client-side load balancing policies: `pick_first` (default — use one address), `round_robin` (rotate across all addresses). With Kubernetes, the DNS name resolves to multiple pod IPs, and gRPC distributes calls across them.

---

**Q39: Why does your `PatientService` take a `PatientRepository` in the constructor instead of using `@Autowired` on the field?**

> **A:** My service uses **constructor injection**:
> ```java
> @Service
> public class PatientService {
>     private final PatientRepository patientRepository;
>
>     public PatientService(PatientRepository patientRepository) {
>         this.patientRepository = patientRepository;
>     }
> }
> ```
>
> Advantages over field injection (`@Autowired private PatientRepository repo;`):
> 1. **Immutability** — Field is `final`, can't be changed after construction
> 2. **Testability** — In tests, you can pass a mock repository via the constructor, no reflection needed
> 3. **Fail-fast** — If the dependency is missing, the app fails at startup (not at runtime when the field is first accessed)
> 4. **No Spring dependency** — The constructor doesn't need `@Autowired` (Spring auto-detects single constructors)
> 5. **Required dependencies are explicit** — You can see all dependencies in the constructor signature

---

**Q40: An interviewer shows you this code and asks "What's wrong?": `patientRepository.findById(id).get()`**

> **A:** `.get()` on an `Optional` throws `NoSuchElementException` if the value is absent (patient not found). This is **unsafe** because it bypasses the whole purpose of `Optional`.
>
> My code correctly handles this:
> ```java
> Patient patient = patientRepository.findById(id)
>     .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
> ```
>
> `.orElseThrow()` lets me throw a **custom** exception (`PatientNotFoundException`) which my `GlobalExceptionHandler` catches and returns a proper 404 response. Using `.get()` would give a generic 500 with an unhelpful error message.

---

## Bonus: How to Explain Your Project in an Interview

### 30-Second Elevator Pitch

> "I built a **Patient Management System** using a **microservices architecture** with Java and Spring Boot. It has 5 services: a **Patient Service** for CRUD operations with JPA and PostgreSQL, an **Auth Service** with JWT-based authentication and BCrypt password hashing, an **API Gateway** using Spring Cloud Gateway for routing and token validation, a **Billing Service** that communicates via **gRPC** with Protocol Buffers for high-performance inter-service calls, and an **Analytics Service** that consumes **Kafka** events asynchronously. Each service is containerized with **Docker** and follows a layered architecture with proper DTO patterns, validation groups, and global exception handling."

### Common Follow-Up Questions

1. **"Walk me through what happens when a user creates a patient."**
   > Client sends POST to API Gateway → Gateway validates JWT → Routes to Patient Service → Controller validates DTO → Service checks email uniqueness → Saves to DB → Calls Billing Service via gRPC → Publishes event to Kafka → Returns response.

2. **"Why did you choose gRPC for billing but Kafka for analytics?"**
   > Billing is **synchronous** — patient creation must wait for billing account creation; gRPC is fast and type-safe. Analytics is **asynchronous** — it's just logging; the patient creation shouldn't depend on or wait for analytics processing. Kafka also provides durability — events survive service restarts.

3. **"What would you add if you had more time?"**
   > Circuit breaker (Resilience4j) for the gRPC call, Redis caching for frequently accessed patients, Kubernetes deployment with service discovery (Eureka), centralized logging (ELK stack), and distributed tracing (Zipkin/Jaeger).

---

## Quick Reference: Technologies & Their Purpose

| Technology | Purpose | Where Used |
|-----------|---------|-----------|
| Java 21 | Programming language | All services |
| Spring Boot 3.4 | Application framework | All services |
| Spring Data JPA | Database ORM | patient-service, auth-service |
| Spring Security | Auth framework | auth-service |
| Spring Cloud Gateway | API Gateway | api-gateway |
| H2 Database | In-memory DB (development) | patient-service, auth-service |
| PostgreSQL | Production database | patient-service, auth-service |
| gRPC + Protobuf | Fast inter-service RPC | patient ↔ billing |
| Apache Kafka | Event streaming | patient → analytics |
| JWT (jjwt) | Token auth | auth-service |
| BCrypt | Password hashing | auth-service |
| Maven | Build tool + dependency management | All services |
| Docker | Containerization | All services |
| SLF4J + Logback | Logging | All services |
| Swagger/OpenAPI | API documentation | patient-service, auth-service |

---

*Good luck with your interviews! You've built something impressive for a first project. Remember: interviewers love when you can explain **why** you chose something, not just **what** you used.*
