# DevForge — Forge Better Code

DevForge is a local-first Windows desktop toolkit for password generation, JSON formatting, reusable code snippets, README authoring, and settings/activity review.

## Quick start

1. Extract `DevForge-Windows-Portable.zip` with Windows **Extract All**.
2. Open the extracted `DevForge-Portable` folder.
3. Double-click `Start DevForge.bat`.

The ZIP includes the Windows application, its dependencies, and a Java 21 runtime. You do not need to install Java or Maven, or connect to the internet, to run the packaged app. Keep the extracted folder together. If the app does not open, run `Run with console.bat` to see its error output.

## Features

- Dashboard with original bundled DevForge artwork, live local counts, and recent activity.
- Password generator using `SecureRandom`, configurable length and character categories, ambiguity exclusion, copy support, strength estimate, batches of up to ten, and masked in-memory history. Passwords are not written to the database or activity log.
- JSON formatter with format, minify, validate, open/save, sample input, and configurable indentation.
- Snippet manager with create/edit/delete, search/filter/sort, favorites, clipboard, and JSON import/export.
- README maker with Markdown preview, editing fields, load, copy, and save.
- Settings and activity history with a persistent theme preference.

## Data and privacy

Snippets, activity history, and settings are stored locally in `%LOCALAPPDATA%\DevForge\devforge.db`. Passwords and password history remain in memory while the generator is open; clear the temporary history to remove those values from the app. DevForge does not send data to external services.

## Building from source

The source targets Java 21 and Maven. These steps are for development; the portable ZIP does not need these tools.

```powershell
mvn clean test
mvn javafx:run
mvn clean package
```

The shaded `target/devforge-1.0.0.jar` includes the application dependencies and Windows x64 JavaFX libraries. `run-devforge.bat` runs the app through Maven. A Windows installer is not produced.

## Tests

The Maven test suite covers password generation and validation, JSON operations, README generation, and database initialization. Run it with `mvn test` from the project directory.
