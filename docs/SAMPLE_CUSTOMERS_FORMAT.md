# `customers.txt` file format

Registered sign-up accounts are stored as **one line per customer** in `WEB-INF/data/customers.txt`.

## Rules

- **Encoding:** UTF-8  
- **Delimiter:** tab (`\t`) between fields — do not put tabs inside names or email.  
- **Comments:** lines starting with `#` are ignored.  
- **Passwords:** never stored in plain text — only Base64 salt + PBKDF2 hash columns.  
- **Columns (exactly 7, in order):**

1. `customerId` — e.g. `CU001` (auto-generated on sign-up)  
2. `fullName`  
3. `email`  
4. `phone`  
5. `saltB64`  
6. `passwordHashB64`  
7. `createdAt` — ISO-8601 instant  

## Example (TSV)

```
CU001	Ama Perera	ama@example.com	0771234567	<base64 salt>	<base64 hash>	2026-05-20T10:00:00Z
```

## Web UI

| Operation | URL |
|-----------|-----|
| Create (sign up) | `/register` |
| Update | `/customers/edit?customerId=CU001` |
| Delete | POST `/customers/delete` |

Same architecture as bookings: `CustomerFileStore` + `CustomerService` + servlets + JSP.
