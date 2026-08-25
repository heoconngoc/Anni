# J4U Arcade

[![CI](https://github.com/heoconngoc/Anni/actions/workflows/ci.yml/badge.svg)](https://github.com/heoconngoc/Anni/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Java](https://img.shields.io/badge/Java-17-orange)

Personal-gift desktop arcade — "Just For You". Eight classic mini-games, a
hidden-letter experience for special players, and a full client–server
score system with offline-first SQLite.

```
.env ─▶ Config ─▶ name gate ─▶ guest letter · secret letter 1→2→3
                                        │
                                      HOME
                                   ╱    │     ╲
                              Play   Scores   Thanks
                              ╱
                  game select → game panel → scores
```

## Features

- **Eight games** — Snake, Pac-Man, Space Invaders, Chrome Dinosaur, Flappy
  Bird, Whac-A-Mole, Match Cards, Minesweeper
- **Hidden-letter system** — guests get a greeting; logging in with the right
  name + password reveals a three-part personal letter; all content in `.env`
- **Offline-first scoring** — HTTP to Spring Boot backend first, silent SQLite
  fallback; GUI never knows which store answered
- **Leaderboards** — top-3 per game + personal best, synced via server

## Tech Stack

| Layer | Tech |
|-------|------|
| Client | Java 17, Swing, JDK HttpClient |
| Shared | `anni-common` — GameCatalog, ScoreEntry, repository interfaces |
| Backend | Spring Boot 3.3, Flyway migrations |
| DB | SQLite (client) · H2 dev · PostgreSQL 16 (prod) |
| Infra | Docker Compose, GitHub Actions CI |

## Quick Start

```bash
git clone https://github.com/heoconngoc/Anni.git && cd Anni
cp .env.example .env               # dev defaults work as-is
mvn clean install                  # build + test (~26 tests)

java -jar app/target/anni-arcade.jar              # play offline
java -jar server/target/anni-server.jar           # backend on :8080
docker compose up --build                         # full stack + PostgreSQL
```

Set `API_URL=http://localhost:8080/api` in `.env` to connect to the server.

## Hidden Letters

All content lives in `.env` (never committed):

```ini
APP_VALID_USERS=mylove          # comma-separated names that can log in
APP_PASSWORD=0000

LETTER_GUEST=Hi {name},\nWelcome to our little arcade!

LETTER_SPECIAL_1=Part one — how it all started...
LETTER_SPECIAL_2=Part two — everything since then...
LETTER_SPECIAL_3=Part three — and whatever comes next.

LETTER_THANKS=Thank you for playing, {name}! \nSee you again soon.
```

`{name}` = entered username, `\n` = line break. Missing keys degrade gracefully.

## Development

```bash
mvn compile exec:java -pl app                  # dev run
mvn clean verify                               # full tests
ANNI_GUI_PROBE=1 mvn test                     # GUI focus regression (env-gated)
```

## Project Layout

```
common      data contracts (GameCatalog, ScoreEntry, repositories)
app         Swing client — gui/ shell + navigation · game/ 8 games · data/ stores
server      Spring Boot — REST controller · JDBC repos · Flyway SQL
```

## Documentation

- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) — data flow, navigation model
- [`docs/CODING_CONVENTIONS.md`](docs/CODING_CONVENTIONS.md) — code style
- [`docs/DECISIONS.md`](docs/DECISIONS.md) — ADRs D01–D16

License: [MIT](LICENSE).
