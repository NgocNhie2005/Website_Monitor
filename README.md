# Website Monitor — Java Implementation

## Package Structure
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
└── channel/
    └── NotificationChannel.java

## Coupling Metrics

### Definitions
- **Ca (Afferent Coupling)** — how many other classes depend on this class (incoming)
- **Ce (Efferent Coupling)** — how many classes this class depends on (outgoing)
- **Instability (I)** — `I = Ce / (Ca + Ce)` → 0 = maximally stable, 1 = maximally unstable

### Per-Class Analysis

| Class | Ca | Ce | I | Notes |
|---|---|---|---|---|
| `User` | 0 | 1 | 1.00 | Depends on `Subscription`; nothing depends on `User` |
| `Subscription` | 3 | 2 | 0.40 | Used by `User`, `MonitoringScheduler`x2; depends on `NotiPreference`, `Status` |
| `NotiPreference` | 2 | 1 | 0.33 | Used by `Subscription`, `MonitoringScheduler`; depends on `Frequency` |
| `Frequency` | 1 | 0 | 0.00 | Pure enum, only used by `NotiPreference` |
| `Status` | 1 | 0 | 0.00 | Pure enum, only used by `Subscription` |
| `Website` | 1 | 0 | 0.00 | Used by `MonitoringScheduler`; no outgoing deps |
| `MonitoringScheduler` | 0 | 4 | 1.00 | Orchestrator; depends on `Subscription`, `Website`, `Notification`, `NotificationChannel` |
| `Notification` | 2 | 1 | 0.33 | Used by `MonitoringScheduler`, `Main`; depends on `NotificationChannel` |
| `NotificationChannel` | 2 | 1 | 0.33 | Used by `Notification`, `MonitoringScheduler`; depends on `Notification` |

> Note: `Main` is excluded as an application bootstrap class.

### Key Observations
- `Frequency`, `Status`, `Website` are **maximally stable** (I = 0) — good candidates to be depended upon.
- `User` and `MonitoringScheduler` are **maximally unstable** (I = 1) — they are leaves or orchestrators.
- `MonitoringScheduler` has the **highest efferent coupling (Ce = 4)**, making it a coupling hotspot.

## Package-Level Metrics

| Package | Ca | Ce | I |
|---|---|---|---|
| `model` | 3 | 0 | 0.00 |
| `scheduler` | 0 | 3 | 1.00 |
| `notification` | 2 | 1 | 0.33 |
| `channel` | 2 | 1 | 0.33 |

## Options to Reduce Coupling Between Packages

### 1. Introduce Interfaces / Abstractions
Extract interfaces like `INotificationChannel` and `INotification` in a shared `api` package.
`MonitoringScheduler` then depends on abstractions, not concrete classes — applying the **Dependency Inversion Principle (DIP)**.

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

## Building & Running

```powershell
# Navigate to source root
cd src/main/java

# Compile
javac -d out (Get-ChildItem -Recurse -Filter "*.java" | % { $_.FullName })

# Run
java -cp out com.monitor.Main
```