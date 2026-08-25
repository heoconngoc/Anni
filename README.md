# J4U Arcade 🎮

[![CI](https://github.com/heoconngoc/Anni/actions/workflows/ci.yml/badge.svg)](https://github.com/heoconngoc/Anni/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Java](https://img.shields.io/badge/Java-17-orange)

A personal-gift desktop arcade — "Just For You". Eight classic mini-games packed into one
**Java Swing** application, wrapped around a secret-letter experience: guests get a warm
welcome note, and logging in with the right name and password reveals a **three-part hidden
letter** written just for them. Under the hood it grew into a full **client–server** system:
scores sync to a Spring Boot REST backend backed by PostgreSQL, while an offline-first
SQLite layer keeps every game playable anytime, anywhere.

```
Swing games ─▶ ScoreService ─▶ ScoreStore ── HTTP first ──▶ Spring Boot API ─▶ PostgreSQL · H2
                                  │                             ▲
                                  └─▶ SQLite fallback ◀─────────┘  (server unreachable)
.env ─▶ Config ─▶ login gate (users + password) ─▶ guest letter · hidden letter parts 1→2→3
```

## Features

- **Eight classics, one shell** — Snake, Pac-Man, Space Invaders, Chrome Dinosaur, Flappy
  Bird, Whac-A-Mole, Match Cards, Minesweeper; animated menus, per-game rule screens,
  synthesized sound effects, and a global high-score hub.
- **Hidden-letter system** — a guest greeting letter plus a personal three-part letter that
  unlocks after successful login (`{name}` placeholder, `\n` line breaks); all content lives
  in `.env` so the code stays shareable while the words stay private.
- **Offline-first scoring** — every score call goes HTTP-first to the backend and silently
  falls back to local SQLite per call when the server is unreachable; the GUI never knows
  which store answered.
- **Leaderboards** — top-3 per game plus personal bests in an in-app score hub, synced
  across players through the server when online.
- **Real backend** — REST API with Flyway-managed schema (PostgreSQL dialect), H2 in-memory
  for dev, JDBC repositories implementing the *same* interfaces as the client's SQLite layer.
- **CI & Docker** — GitHub Actions runs the full test suite headless on every push;
  `docker compose up` brings up the whole stack with PostgreSQL in one command.

## Screenshots

| Welcome Screen | Game List |
|---|---|
| ![Welcome Screen](docs/images/welcome.png) | ![Game List](docs/images/Game_list.png) |
|---|---|
| Minesweeper | Pacman |
| ![Minesweeper](docs/images/space.png) | ![Pacman](docs/images/dino.png) |


## Tech Stack

| Layer | Tech |
|-------|------|
| Client | Java 17, Swing (custom named-card navigation, BasePanel framework), JDK HttpClient |
| Shared contracts | `anni-common` — GameCatalog, ScoreEntry, UserRepository, ScoreRepository interfaces |
| Backend | Spring Boot 3.3 (Web, JDBC), Flyway migrations |
| Databases | SQLite (offline client storage) · H2 in-memory MODE=PostgreSQL (dev/test) · PostgreSQL 16 (prod profile) |
| Serialization | Jackson |
| Testing | JUnit 5, MockMvc (API), fake-JDK-HttpServer harness, env-gated real-GUI regression suite |
| Build | Maven multi-module, shade plugin (fat jar) |
| Infra | Docker Compose (server + PostgreSQL 16), GitHub Actions CI |

## Quick Start

Prerequisites: [JDK 17](https://adoptium.net) and [Maven 3.8+](https://maven.apache.org).

```bash
git clone https://github.com/heoconngoc/Anni.git && cd Anni
mvn clean install                       # build + test all modules (~26 tests)

java -jar app/target/anni-arcade.jar    # play instantly — fully offline
java -jar server/target/anni-server.jar # optional backend on :8080 (H2)
```

Or run the entire stack with PostgreSQL via Docker:

```bash
cp .env.example .env                    # dev defaults work as-is
docker compose up --build               # server :8080 + postgres :5432
```

| Endpoint | URL |
|----------|-----|
| Submit a score | `POST localhost:8080/api/scores` |
| Leaderboard | `GET localhost:8080/api/games/{gameId}/top?limit=3` |
| Personal best | `GET localhost:8080/api/users/{user}/best/{gameId}` |
| Game catalog | `GET localhost:8080/api/games` |

Point the desktop app at the server by setting `API_URL=http://localhost:8080/api` in `.env`
(leave empty to stay offline). See [`.env.example`](.env.example) for every option.

## 💌 Setting Up the Hidden Letters

This is what makes the app a *gift*. All letter content lives in `.env` — never committed —
so you can publish the repository while keeping the words private.

1. Create your config:

   ```bash
   cp .env.example .env
   ```

2. Choose who can log in and with what password:

   ```ini
   APP_VALID_USERS=mylove          # comma-separated names allowed to log in
   APP_PASSWORD=0000               # the secret password you give them
   ```

3. Write the letters:

   ```ini
   LETTER_GUEST=Hi {name},\nWelcome to our little arcade.\nPick a game and have fun!

   LETTER_SPECIAL_1=Part one — how it all started...\n\nTake your time.
   LETTER_SPECIAL_2=Part two — everything since then...
   LETTER_SPECIAL_3=Part three — and whatever comes next.
   ```

4. Launch the app next to the `.env` and log in with any name from `APP_VALID_USERS` +
   `APP_PASSWORD`. The guest letter greets everyone before login; after login the three
   hidden parts are revealed one by one.

**Conventions inside letter text:** `{name}` is replaced with the entered username; `\n`
produces a real line break. Missing keys degrade gracefully — no `.env` at all means the
app still opens in guest mode with a short default greeting.

## Development Workflow

```bash
mvn compile exec:java -pl app      # run the client in dev mode (needs one mvn install first)
mvn clean verify                   # full test suite — same gate CI enforces
ANNI_GUI_PROBE=1 mvn test          # real-GUI keyboard-focus regression suite (self-skips headless)
java -jar server/target/anni-server.jar              # backend, H2, port 8080
SPRING_PROFILES_ACTIVE=postgres java -jar server/target/anni-server.jar  # against real PostgreSQL
docker compose up --build                            # full stack with PostgreSQL
```

## Project Layout

```
common            anni-common — data contracts shared by both sides
app               Swing desktop client (anni-app)
                  gui/ shell + navigation · game/<name>/ 8 games · data/ stores · util/ fonts & SFX
server            Spring Boot backend (anni-server)
                  web/ REST controller · repo/ JDBC repositories · db/migration/ Flyway SQL
docs              architecture.md · coding-conventions.md · decisions.md (ADRs D01–D15)
Dockerfile        multi-stage build for the server module
docker-compose.yml  server + PostgreSQL 16
```

## Documentation

- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) — system architecture, navigation model, data flow, API contract
- [`docs/CODING_CONVENTIONS.md`](docs/CODING_CONVENTIONS.md) — code style and Swing patterns used across the codebase
- [`docs/DECISIONS.md`](docs/DECISIONS.md) — architecture decision records D01–D15

## Status

All planned phases complete: Maven & config groundwork → navigation refactor → SQLite data
layer → sound & polish → Spring Boot backend → client–server sync → CI → Docker.
License: [MIT](LICENSE).
