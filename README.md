# Event Photography and Videography Booking System

A web-based application for managing photography and videography bookings for events. Users can browse services, manage bookings, and interact with the system through a clear, responsive interface—built as part of **SE1020 – Object Oriented Programming** (project assignment).

## About this project

This system applies object-oriented design to a real-world booking domain: scheduling and managing event photo and video services. Data is persisted using file read/write (or optional SQL storage where allowed), with a Java-backed web stack suitable for collaborative development on GitHub.

---

## Requirements

The following requirements align with the SE1020 project specification.

### Overview

- Develop a **web-based** application that lets users manage and interact with a collection of **items or services** (here: photography/videography bookings and related entities).
- Build using **Java web technologies** in **IntelliJ IDEA**.
- Use **file read/write** for data storage **instead of a database**, unless a proper **MySQL/SQL** connection is chosen as allowed by the brief.

### Objectives

- Apply **OOP**: encapsulation, inheritance, and polymorphism.
- Implement **at least 3 of CRUD** (Create, Read, Update, Delete) using **file handling**.
- Provide a **user-friendly** interface.

### Development environment

| Area | Specification |
|------|----------------|
| IDE | IntelliJ IDEA |
| Stack | Java, Spring Boot, HTML/CSS, JavaScript (UI enhancements) |
| UI | Templates; **Tailwind CSS** or **Bootstrap** allowed for design and responsiveness |
| Storage | File read/write (e.g. text files) **or** MySQL/SQL |
| Version control | **GitHub** for collaboration |

### Deliverables

1. **Source code**: `.java`, `.jsp`, and configuration files.
2. **Documentation**: class diagrams; final report (including **Git commit history**).
3. **Viva**: each member explains contribution and demonstrates functionality; **sample data files** (e.g. `items.txt`, `users.txt`); present Git history; questioning focuses on **backend Java**, **OOP**, and **file handling**.

### Individual contribution (sample rubric from brief)

Acceptable scope includes, for example:

- **User management** — any **three** of: register user (Create), search user (Read), update user (Update), delete user (Delete).
- Backend with **JSP/Servlets** and **file read/write** for data.
- **Minimum 3 UIs.**

---

## Implementation (Maven WAR)

- **Java:** 17 · **Build:** Maven (`pom.xml`) · **Web:** JSP + Servlets (Jakarta EE / Tomcat 10+)
- **Persistence:** `src/main/webapp/WEB-INF/data/bookings.txt` (tab-separated values)
- **Deep dive:** see [`docs/HOW_IT_WORKS.md`](docs/HOW_IT_WORKS.md) and [`docs/SAMPLE_BOOKINGS_FORMAT.md`](docs/SAMPLE_BOOKINGS_FORMAT.md)

### Run in IntelliJ IDEA (summary)

1. Open the project as a **Maven** project and set the SDK to **Java 17**.
2. Add a local **Tomcat 10.x** (or other Servlet 6 / JSP 3.1) server run configuration.
3. Deploy this module’s **WAR** artifact (`event-booking-system:war`); context path is usually `/event_booking_system` or `/event-booking-system` depending on your Tomcat artifact name.
4. Run the server and open the configured URL; the welcome page is **`index.jsp`**.

Build from the command line: `mvn clean package` → `target/event-booking-system.war`.

### Source layout

```
src/main/java/com/eventbooking/
  model/           Booking, MediaPackage, PhotographyPackage, VideographyPackage
  service/         BookingService, PackageCatalog
  util/            BookingFileStore, BookingDataPaths
  web/             Servlets, AppContext
src/main/webapp/
  index.jsp        Home
  css/app.css      Extra styles
  WEB-INF/web.xml
  WEB-INF/data/bookings.txt   Sample + live data file
  WEB-INF/jsp/     List, form, search, packages views + include/navbar.jspf
```

## Repository

Clone this repository and open the project in IntelliJ IDEA.
