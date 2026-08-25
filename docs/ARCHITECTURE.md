# ARCHITECTURE.md

This document describes the architecture of J4U Arcade as it stands today,
after the full Phase 1–10 build-out.

## Big Picture

```
J4U-Arcade/ (multi-module Maven)
├── common   Shared data contracts
│            GameCatalog · ScoreEntry · UserRepository · ScoreRepository
│
├── app      Swing desktop client (the game itself)
│     Main → GUI (JFrame) → MainPanel (named-card navigation)
│       ├── Start / Menu / letters / score hub screens
│       └── 8 games × (start + rules + play) panels
│     Data access:
│       GUI & games ── ScoreService ── ScoreStore
│                                    ├── HttpScoreStore   → REST API
│                                    │                      (fallback ↓)
│                                    └── SqliteScoreStore  → local SQLite
│
└── server   Spring Boot REST API
      ├── web/        ScoreController (REST endpoints)
      ├── repo/       JdbcTemplate repositories implementing the SAME
      │               interfaces as the client's SQLite layer
      └── db/migration/  Flyway SQL (users, scores)

CI:    GitHub Actions — mvn clean verify on every push
Deploy: docker compose up  (server + PostgreSQL)
```

## Modules

| Module | Artifact | Role |
|---|---|---|
| `common/` | `anni-common` | Contracts shared by client and server. No external dependencies. Same package names as before the split, so neither side had to change imports. |
| `app/` | `anni-app` | Desktop Swing client. Shaded jar `anni-arcade.jar`. |
| `server/` | `anni-server` | Spring Boot backend. Executable jar `anni-server.jar`, port 8080. |

### Client packages (`com.dat.anni.*`)

| Package | Role |
|---|---|
| `main` | Entry point |
| `gui` | App shell: `GUI` frame, `MainPanel` navigation, `BasePanel`, `ScoreHubPanel` |
| `config` | `Config` — one-shot `.env` loader (`ignoreIfMissing`), empty-string fallbacks for secrets |
| `util` | `UiUtils` (cached font loading), `SoundManager` (resource-first SFX with synthesized fallback) |
| `game.<name>` | One folder per game: `<Game>_StartPanel`, `<Game>_RulePanel`, `<Game>Panel` + the logic class |
| `data` | `Database` (SQLite), `ScoreService` facade, `ScoreStore` strategy (+ SQLite / HTTP / fallback implementations) |
| `data.http` | `HttpScoreStore` — JDK HttpClient + Jackson against the server API |

### Server packages (`com.dat.anni.server.*`)

| Package | Role |
|---|---|
| root | `ServerApplication` |
| `repo` | JDBC repositories (`JdbcUserRepository`, `JdbcScoreRepository`) implementing `common` interfaces |
| `web` | REST controller, request validation |

## Navigation Model

- Every screen is registered in `MainPanel` under a named constant
  (`MainPanel.SNAKE_GAME`, …). The only way to switch screens is
  `main.show(MainPanel.X)` → official `CardLayout.show()`.
- Panels implement `MainPanelAware` (fail-fast wiring check at registration).
- Panels hosting live content implement `Navigable`:
  - `onEnter()` — reset state, start Swing timers, and grant keyboard focus
    to key-driven game components (`requestFocusInWindow()`);
  - `onLeave()` — stop/reset timers so nothing runs hidden in the background.
- Games never start their own timers in constructors; lifecycle belongs to the
  wrapper panel.

## Data Flow

1. GUI/games call **`ScoreService`** only (facade).
2. `ScoreService` delegates to a **`ScoreStore`** chosen once at startup:
   - `API_URL` set → `FallbackScoreStore(HttpScoreStore, SqliteScoreStore)`
   - otherwise → plain `SqliteScoreStore`.
3. Every call tries HTTP first; any network/HTTP failure silently falls back to
   SQLite. Offline-first by design.
4. Both stores implement the same repository contracts from `common`
   (`UserRepository.upsert`, `ScoreRepository.save/top/best`).

### Persistence

- Client: single-connection SQLite database, default file `anni.db`
  (gitignored), overridable via `DB_PATH`. Tables: `users`, `scores`.
- Server: Flyway-managed schema written in PostgreSQL dialect
  (`V1__init.sql`: users, scores, indexes). Default datasource is H2 in-memory
  `MODE=PostgreSQL`; the `postgres` profile switches to real PostgreSQL via
  `DB_URL` / `DB_USER` / `DB_PASSWORD`.

### REST API

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/scores` | Submit `{username, gameId, score}` (400 on blank names or negative score) |
| `GET` | `/api/games/{gameId}/top?limit=3` | Leaderboard — best run per player, ordered desc (limit capped at 100) |
| `GET` | `/api/users/{username}/best/{gameId}` | Personal best (0 if none) |
| `GET` | `/api/games` | Game catalog from the shared `GameCatalog` enum |

## Design Principles

1. **Repository pattern** — games/GUI only ever see interfaces and facades;
   swapping SQLite ↔ HTTP never touches game code.
2. **Offline-first** — losing the server degrades gracefully to local storage;
   nothing crashes, no score is lost.
3. **No business logic inside Swing listeners** — listeners delegate to game
   classes and services.
