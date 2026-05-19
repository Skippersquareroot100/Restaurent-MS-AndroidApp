# BiteFood Android App

BiteFood is a Kotlin-based Android restaurant management app built with the Android View system and Room. It combines a simple customer ordering flow with an admin inventory screen in a single local-first mobile application.

## Overview

The app currently supports:

- User registration with local persistence
- User login using Room-stored credentials
- Admin login with hardcoded credentials
- Admin management of food prices and stock quantities
- Customer browsing of available menu items
- Order quantity selection and bill calculation
- Local data storage with Room for users and food records

This project is implemented as a single Android application module and does not currently use a remote API or cloud backend.

## Tech Stack

- Kotlin
- Android SDK with AppCompat Activities
- XML layouts
- Room database
- Kotlin coroutines
- Gradle Kotlin DSL

## Application Flow

### Customer flow

1. A user registers from the registration screen.
2. The user logs in from the main login screen.
3. After successful login, the user enters the homepage.
4. The homepage shows menu items and their available quantities.
5. Selecting an item opens the bill page.
6. The user adjusts quantity and confirms the order.

### Admin flow

1. The admin logs in from the same login screen.
2. The app checks the hardcoded admin credentials:
   - Username: `samia123`
   - Password: `123`
3. The admin opens the dashboard and can update:
   - Food price
   - Food quantity
4. Inventory updates are stored in the local Room database and reflected in the customer homepage.

## Data Layer

The app uses Room to persist two entities:

- `users`
  - `id`
  - `email`
  - `userName`
  - `passWord`
- `foods`
  - `name`
  - `price`
  - `quantity`

### Database behavior

- The Room database file is named `bitefood_db`.
- Default food rows are inserted from the admin dashboard if they do not already exist.
- Inventory values shown on the homepage are loaded from Room.
- Login for normal users is validated against Room.

## Project Structure

```text
app/src/main/java/com/example/bitefood/
├── AdminDashboard.kt
├── BillpageActivity.kt
├── HomepageActivity.kt
├── LoginPageActivity.kt
├── RegistrationActivity.kt
├── dao/
│   ├── FoodDao.kt
│   └── UserDao.kt
├── db/
│   ├── AppDatabase.kt
│   └── DatabaseProvider.kt
└── model/
    ├── Food.kt
    └── User.kt
```

## Key Screens

- `LoginPageActivity`: Entry point, user login, admin login, location permission request
- `RegistrationActivity`: User account creation
- `HomepageActivity`: Customer menu and available item quantities
- `BillpageActivity`: Item quantity adjustment and total bill calculation
- `AdminDashboard`: Price and stock management for menu items

## Menu Items

The current default menu consists of:

- Soup
- Burger
- Pizza
- Bento
- Cappuccino
- Fried Chicken

## Permissions

The app requests:

- `ACCESS_FINE_LOCATION`
- `ACCESS_COARSE_LOCATION`

These permissions are declared in the manifest, and fine location is requested at runtime from the login screen.

## Build Requirements

- Android Studio with Gradle support
- JDK 11
- Android SDK with `compileSdk 36`
- Minimum SDK: `24`
- Target SDK: `36`

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/Skippersquareroot100/Restaurent-MS-AndroidApp.git
   ```

2. Open the project in Android Studio.
3. Let Gradle sync the dependencies.
4. Build and run the `app` module on an emulator or Android device.

## Running From Command Line

Use the Gradle wrapper from the project root:

```bash
./gradlew assembleDebug
```

## Current Limitations

- There is no remote backend or API integration yet.
- Admin authentication is hardcoded and should be replaced before production use.
- User passwords are stored as plain text in the local database.
- Order confirmation currently shows success feedback but does not reduce stock or store order history.
- The app uses a simple multi-activity architecture without a repository or domain layer.
- There are only placeholder unit and instrumentation tests at the moment.

## Suggested Next Improvements

- Replace hardcoded admin credentials with a secure auth flow
- Hash and salt user passwords before persistence
- Add order history and stock deduction on checkout
- Introduce ViewModel-based state handling
- Add validation for duplicate usernames during registration
- Add automated tests for login, inventory updates, and billing logic
- Connect the app to a proper backend if multi-user sync is required

## Repository Notes

- Root project name: `BiteFood`
- Application ID: `com.example.bitefood`
- Primary persistence approach: local Room database
- UI approach: XML layouts with AppCompat activities

## License

No license file is currently included in this repository. Add one if you plan to distribute or open-source the project formally.
