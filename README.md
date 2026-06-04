# Website Monitor - Java Implementation

## Package Structure

```text
com.monitor
├── Main.java                          (entry point)
├── model/
│   ├── User.java
│   ├── Subscription.java
│   ├── NotiPreference.java
│   ├── Website.java
│   ├── Status.java        (enum)
│   └── Frequency.java     (enum)
├── scheduler/
│   └── MonitoringScheduler.java
├── notification/
│   └── Notification.java
├── observer/
│   ├── WebsiteObserver.java
│   └── WebsiteSubject.java
├── channel/
│   └── NotificationChannel.java
└── strategy/
    ├── ComparisonStrategy.java
    ├── ContentSizeStrategy.java
    ├── HtmlContentStrategy.java
    └── TextContentStrategy.java
```

## Coupling Metrics

### Definitions
- **Ca (Afferent Coupling)** - how many other classes depend on this class (incoming)
- **Ce (Efferent Coupling)** - how many classes this class depends on (outgoing)
- **Instability (I)** — `I = Ce / (Ca + Ce)` -> 0 = maximally stable, 1 = maximally unstable

### Only project classes/interfaces are counted. Java library classes such as `List`, `String`, `LocalDateTime`, and `UUID` are excluded.
---

### Per-Class Analysis

| Class | Ca | Ce | I | Notes |
|---|---|---|---|---|
| `User` | 0 | 1 | 1.00 | Depends on `Subscription`; no incoming dependencies |
| `Subscription` | 2 | 2 | 0.50 | Used by `User` and `MonitoringScheduler`; depends on `NotiPreference` and `Status` |
| `NotiPreference` | 1 | 1 | 0.50 | Used by `Subscription`; depends on `Frequency` |
| `Frequency` | 1 | 0 | 0.00 | Enum used only by `NotiPreference` |
| `Status` | 1 | 0 | 0.00 | Enum used only by `Subscription` |
| `Website` | 3 | 0 | 0.00 | Used by `MonitoringScheduler`, `Notification`, and `WebsiteObserver`; no outgoing dependencies |
| `MonitoringScheduler` | 0 | 4 | 1.00 | Depends on `Subscription`, `Website`, `WebsiteObserver`, and `WebsiteSubject` |
| `Notification` | 0 | 3 | 1.00 | Depends on `NotificationChannel`, `Website`, and `WebsiteObserver` |
| `NotificationChannel` | 1 | 1 | 0.50 | Used by `Notification`; depends on `Notification` |
| `WebsiteObserver` | 2 | 1 | 0.33 | Implemented/used by `Notification` and `MonitoringScheduler`; depends on `Website` |
| `WebsiteSubject` | 1 | 1 | 0.50 | Implemented by `MonitoringScheduler`; depends on `WebsiteObserver` |

> Note: `Main` is excluded as an application bootstrap class.

### Key Observations
- `Frequency`, `Status`, and `Website` are maximally stable (`I = 0`), making them good foundational components.
- `User`, `MonitoringScheduler`, and `Notification` are highly unstable (`I = 1`) because they depend on multiple other classes while no classes depend on them.
- `MonitoringScheduler` has the highest efferent coupling (`Ce = 4`), making it the main orchestration component of the system.
- The system follows a layered structure:
  - Stable core model classes (`Website`, enums)
  - Observer abstractions (`WebsiteObserver`, `WebsiteSubject`)
  - Service/controller classes (`MonitoringScheduler`, `Notification`)

## Package-Level Metrics

| Package | Ca | Ce | I |
|---|---|---|---|
| `model` | 3 | 0 | 0.00 |
| `scheduler` | 0 | 4 | 1.00 |
| `notification` | 1 | 3 | 0.75 |
| `channel` | 1 | 1 | 0.50 |
| `observer` | 2 | 2 | 0.50 |

## Options to Reduce Coupling Between Packages

### 1. Introduce Interfaces / Abstractions
Extract interfaces like `INotificationChannel` and `INotification` in a shared `api` package.
`MonitoringScheduler` then depends on abstractions, not concrete classes - applying the **Dependency Inversion Principle (DIP)**.

### 2. Use an Event/Observer Pattern
Instead of `MonitoringScheduler` calling `Notification` directly, publish a `WebsiteChangedEvent`.
`Notification` and `NotificationChannel` subscribe to those events.

### 3. Dependency Injection (DI) / IoC Container
Use Spring or Guice to inject `NotificationChannel` implementations into `MonitoringScheduler`.
The scheduler no longer instantiates concrete channels, breaking the compile-time dependency.

### 4. Separate the `channel` and `notification` Packages Further
Move the `deliver(channel)` logic to a `NotificationService` in a separate `service` package,
keeping `Notification` a pure data object with no outgoing dependencies.

### 5. Apply the Stable-Dependencies Principle (SDP)
Ensure packages only depend on packages with lower instability.
Extracting a shared `api` package (I=0) gives both a stable abstraction to depend on.

## Coding Conventions

### Naming
- **Classes**: PascalCase -> `MonitoringScheduler`, `HtmlContentStrategy`
- **Methods & variables**: camelCase -> `hasChanged()`, `lastContent`
- **Constants & enums**: UPPER_SNAKE_CASE -> `Status.ACTIVE`, `Frequency.HOURLY`
- **Interfaces**: plain PascalCase, no `I` prefix -> `ComparisonStrategy`, `WebsiteObserver`
- **Packages**: all lowercase -> `com.monitor.strategy`

### Class Design
- One class per file; filename matches class name exactly
- All fields are `private`, exposed only via getters/setters
- Constructors set sensible defaults (e.g. `HtmlContentStrategy` as default comparison strategy)
- Interfaces used to define contracts (`WebsiteObserver`, `WebsiteSubject`, `ComparisonStrategy`)

### Methods
- Short, single-purpose methods (max ~20 lines)
- Boolean methods named as questions: `hasChanged()`, `validate()`
- Void methods named as commands: `generate()`, `deliver()`, `scheduleCheck()`

### Formatting
- 4-space indentation
- Opening brace `{` on the same line as the declaration
- One blank line between methods
- No redundant comments - code is written to be self-explanatory

### Design Patterns Applied
- **Observer pattern**: `Website` (Subject) notifies `Subscription` (Observer) on change
- **Strategy pattern**: interchangeable comparison strategies via `ComparisonStrategy` interface
- **Dependency Injection**: strategies and channels injected via constructor

## Building & Running

```powershell
# Navigate to source root
cd src/main/java

# Compile
javac -d out (Get-ChildItem -Recurse -Filter "*.java" | % { $_.FullName })

# Run
java -cp out com.monitor.Main
```