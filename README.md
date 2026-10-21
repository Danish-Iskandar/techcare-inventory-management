<p align="center">
  <img src="src/main/resources/PNGs/TECHCARE-LOGO.png" alt="TechCare logo" width="120">
</p>

<h1 align="center">TechCare Inventory Management</h1>

<p align="center">
  A JavaFX desktop point-of-sale and inventory system for a small bingsu dessert business, backed by a MySQL database.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk" alt="Java 21">
  <img src="https://img.shields.io/badge/JavaFX-21-blue" alt="JavaFX 21">
  <img src="https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven" alt="Maven">
  <img src="https://img.shields.io/badge/License-MIT-green" alt="MIT License">
</p>

---

## About

TechCare Inventory Management was built by a group of Computer Science students at **Universiti Teknologi PETRONAS (UTP)** as the final project for **TEB1043 Object-Oriented Programming**. It supports the day-to-day running of our MPU4 group's bingsu stall: taking orders at the counter, tracking which orders are paid or pending, keeping stock of ingredients and utensils, and recording money coming in and going out.

## Features

- **Login** – staff sign in with a username and password checked against the `userinfo` table.
- **Dashboard** – a central hub with a live clock and date, linking to every module.
- **Cashier** – build an order by picking a bingsu flavour (Chocolate, Mango, Bandung, Honeydew) and toppings (Waffle, Ice Cream, Jelly, Choc Chip, Caramel Syrup, Chocolate Syrup). The total updates as items are selected, and the order is saved as *Paid* or *Pending* depending on the payment option.
- **Order List** – view all orders in a table, edit order details, update payment status, or delete orders.
- **Inventory** – switch between **Ingredients** and **Utensils**, then add, edit (name, quantity, remarks) or remove items.
- **Monetary** – a ledger of financial statements with running totals of money in and money out; new statements can be added manually, and paid orders are recorded automatically.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| UI | JavaFX 21 (FXML + CSS), FontAwesomeFX icons, JFoenix |
| Database | MySQL 8 (via MySQL Connector/J 8.3.0) |
| Build | Maven (wrapper included) |

## OOP Concepts Applied

- **Encapsulation** – model classes (`Items`, `Orders`, `Statements`) keep fields private behind getters and setters.
- **MVC-style separation** – FXML views in `resources`, controller classes in `org.example.inventorymanagement`, and data models in `Models`.
- **Singleton pattern** – `ItemStateSingleton` shares the selected inventory category (ingredient or utensil) across screens.
- **Inheritance & interfaces** – `MainApp` extends JavaFX `Application`; controllers implement `Initializable`.

## Project Structure

```
techcare-inventory-management/
├── Database/
│   └── mytechcare.sql              # MySQL schema and sample data
├── lib/                            # Local JARs (MySQL Connector/J, FontAwesomeFX)
├── src/main/java/
│   ├── Models/                     # Items, Orders, Statements, ItemStateSingleton
│   ├── org/example/inventorymanagement/
│   │   ├── MainApp.java            # Application entry point
│   │   ├── DBConnect.java          # Database connection helper
│   │   ├── LoginController.java
│   │   ├── DashboardController.java
│   │   ├── CashierController.java
│   │   ├── OrderListController.java
│   │   ├── InventoryController.java
│   │   ├── MonetaryController.java
│   │   └── Add*/Edit*/Remove* controllers for the pop-up forms
│   └── module-info.java
├── src/main/resources/
│   ├── org/example/inventorymanagement/*.fxml   # Screen layouts
│   ├── CSS/                        # Login and dashboard styles
│   └── PNGs/                       # Logo and navigation icons
└── pom.xml
```

## Database Schema

| Table | Purpose |
|---|---|
| `userinfo` | Login accounts and admin access flag |
| `orderlist` | Customer orders: name, phone, flavour, toppings, status, timestamp |
| `ingredientlist` | Ingredient stock with quantity and remarks |
| `utensillist` | Utensil stock with quantity and remarks |
| `statement` | Financial ledger entries (money in / money out) |

## Getting Started

### Prerequisites

- JDK 21
- MySQL Server 8 (local or hosted)
- An IDE with JavaFX support, such as IntelliJ IDEA (recommended — project settings are included)

### 1. Clone the repository

```bash
git clone https://github.com/Danish-Iskandar/techcare-inventory-management.git
cd techcare-inventory-management
```

### 2. Set up the database

Create the database and import the schema:

```bash
mysql -u root -p -e "CREATE DATABASE mytechcare;"
mysql -u root -p mytechcare < Database/mytechcare.sql
```

Then create a login account:

```sql
INSERT INTO userinfo (idUserInfo, Username, Password, `Admin Access`)
VALUES (1, 'your_username', 'your_password', 1);
```

### 3. Configure the connection

Update the JDBC URL, username and password in `DBConnect.java` and in the controllers that open their own connection, so they point to your MySQL instance, for example:

```java
DriverManager.getConnection("jdbc:mysql://localhost:3306/mytechcare", "your_db_user", "your_db_password");
```

### 4. Add the libraries

Add the JARs in `lib/` (MySQL Connector/J and FontAwesomeFX) and JFoenix to the project's module path. In IntelliJ: **File → Project Structure → Libraries → +**.

### 5. Run

Run `MainApp` from the IDE, or with Maven:

```bash
./mvnw clean javafx:run
```

> The window icon is loaded with a Windows-style relative path, so run the app from the project root on Windows, or change the path in `MainApp.java` to `src/main/resources/PNGs/TECHCARE-LOGO.png` on macOS/Linux.

## Known Limitations

- Database credentials are hard-coded in source rather than loaded from configuration.
- User passwords are stored and compared in plain text.
- Some delete queries build SQL with string concatenation instead of prepared statements.
- There is no role-based access control yet, although the `Admin Access` column is in place for it.

## Team

Developed by Computer Science students at Universiti Teknologi PETRONAS for TEB1043 Object-Oriented Programming.
MUHAMMAD DANISH ISKANDAR BIN ZAHRUL-IKRAM
MOHAMAD SHUKRI BIN ABD AJED

## License

This project is licensed under the [MIT License](LICENSE).
