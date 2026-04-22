# `bookings.txt` file format

The application stores every booking as **one line** in `WEB-INF/data/bookings.txt` (under the deployed WAR).

## Rules

- **Encoding:** UTF-8  
- **Delimiter:** tab character (`\t`) between fields — **do not put tabs inside** customer text (the app strips tabs if they appear).  
- **Comments:** lines starting with `#` are ignored.  
- **Columns (exactly 9, in order):**

1. `bookingId` — e.g. `BK001` (new ids are generated automatically when you create from the web UI)  
2. `customerName`  
3. `phone`  
4. `eventType`  
5. `eventDate` — recommended format `yyyy-MM-dd`  
6. `location`  
7. `serviceType` — e.g. `Photography`, `Videography`, `Both`  
8. `packageName`  
9. `specialNotes` — may be empty  

## Example lines (TSV)

Copy/paste into a spreadsheet using “tab” as delimiter to verify columns.

```
BK001	Ama Perera	0771234567	Wedding	2026-05-20	Colombo	Both	Premium Storytelling	Notes go here
BK002	Ruwan Silva	0779876543	Corporate Gala	2026-06-01	Kandy	Videography	Cinematic Package	Include lav mics
```

## Viva tip

Explain that **Create** appends by rewriting the whole file, **Update** replaces one row, and **Delete** removes one row — all implemented by `BookingFileStore` + `BookingService` for clarity.
