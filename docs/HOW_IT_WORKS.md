# How this project works (step-by-step)

This guide matches the code so you can explain it clearly in a viva.

## 1. Big picture

1. The browser calls a **URL** mapped to a **Servlet** (for example `/bookings/list`).  
2. The servlet uses **`BookingService`** to read/write bookings.  
3. **`BookingFileStore`** reads or overwrites **`WEB-INF/data/bookings.txt`**.  
4. The servlet puts Java objects into the **`HttpServletRequest`** and **forwards** to a **JSP** page.  
5. The JSP renders **HTML** using **Bootstrap** (and a little **JavaScript** for delete confirm / tab check).

## 2. Folder structure (what each part is for)

```
pom.xml                          Maven build (Java 17, WAR packaging, servlet/JSP/JSTL deps)
src/main/java/com/eventbooking/
  model/                         OOP entities (Booking + package hierarchy)
  service/                       BookingService + PackageCatalog
  util/                          BookingFileStore + path helper
  web/                           Servlets + AppContext
src/main/webapp/
  WEB-INF/web.xml                Web app config (welcome file = index.jsp)
  WEB-INF/data/bookings.txt      Persistent storage (TSV lines)
  WEB-INF/jsp/                   JSP views (not directly URL-accessible)
  css/app.css                    Extra styling
  index.jsp                      Home page (first screen)
```

## 3. Object-oriented ideas used

- **Encapsulation:** `Booking` keeps fields `private` and exposes getters/setters.  
- **Inheritance:** `PhotographyPackage` and `VideographyPackage` extend `MediaPackage`.  
- **Polymorphism:** `PackageCatalog` returns `List<MediaPackage>`; the packages JSP calls `getSummary()`, `getDeliverables()`, and `getPriceLabel()` without caring which concrete class each item is.

## 4. CRUD flow (bookings)

### Create (`/bookings/create`)

1. **GET** shows `booking-form.jsp` with an empty `Booking`.  
2. **POST** reads parameters, builds a `Booking`, calls `BookingService.createNew`.  
3. `createNew` generates the next `BK###` id, adds the booking to the list, and **`writeAll`** saves the file.

### Read / list (`/bookings/list`)

1. **GET** calls `BookingService.listAll()` → `BookingFileStore.readAll()`.  
2. Each non-comment line becomes a `Booking` via `Booking.fromTsvLine`.  
3. The list is shown in `bookings-list.jsp`.

### Read / search (`/bookings/search`)

1. **GET** with `search=1` calls `BookingService.search(...)`.  
2. `Booking.matchesSearch(...)` checks **id exact** match **or** **name substring** match.

### Update (`/bookings/edit`)

1. **GET** loads one booking by id and shows `booking-form.jsp` with fields filled.  
2. **POST** builds an updated `Booking` (same id) and calls `BookingService.update`, which replaces that row and **`writeAll`**.

### Delete (`/bookings/delete`)

1. **POST** with hidden `bookingId` calls `BookingService.deleteById`, which rebuilds the list without that booking and **`writeAll`**.

## 5. File handling (the important viva lines)

- `Booking.toTsvLine()` converts a `Booking` object → **one TSV line**.  
- `Booking.fromTsvLine(String)` parses **one TSV line** → `Booking`.  
- `BookingFileStore.readAll()` reads the whole file into a `List<Booking>`.  
- `BookingFileStore.writeAll(...)` replaces the entire file from the list (simple and easy to justify for a coursework scale).

## 6. UI pages (more than the minimum)

1. `index.jsp` — home / navigation hub  
2. `bookings-list.jsp` — view all + edit/delete actions  
3. `booking-form.jsp` — create + update (two modes, one page)  
4. `booking-search.jsp` — search UI  
5. `packages.jsp` — read-only catalogue page  

## 7. How to run in IntelliJ IDEA (typical)

1. **Import** the folder as a Maven project.  
2. Ensure the project SDK is **Java 17**.  
3. Run on a **Jakarta EE 10 / Tomcat 10+** style server (Servlet 6 / JSP 3.1).  
4. Deploy the generated **`event-booking-system.war`** (Maven package) or use IntelliJ’s Tomcat run configuration pointing at this module.  
5. Open the context root in a browser; you should land on **`index.jsp`**.

If anything fails to write the file, check that the server process has permission to write under the deployed `WEB-INF/data` directory.

## 8. Servlet URL cheat sheet

| URL pattern | Servlet | What it does |
|-------------|---------|----------------|
| `/bookings/list` | `BookingListServlet` | List all |
| `/bookings/create` | `BookingCreateServlet` | Create form + save |
| `/bookings/search` | `BookingSearchServlet` | Search |
| `/bookings/edit` | `BookingEditServlet` | Edit form + save |
| `/bookings/delete` | `BookingDeleteServlet` | Delete (POST) |
| `/packages` | `PackagesServlet` | Show packages |

## 9. Why `AppContext` exists

`AppContext.bookingService(...)` creates **one** `BookingService` per web application and stores it in `ServletContext`, so every servlet shares the same file path logic and store instance.
