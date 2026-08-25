# DECISIONS.md — Architecture Decision Records (condensed)

> Each entry: context → decision → rationale. To reverse an old decision, add
> a NEW entry with strong reasons; never rewrite history.

## D01 — Java 17, built with Maven
- **Context:** original project mixed Java 17 + Maven + Eclipse metadata.
- **Decision:** standardize on Maven alone; drop Eclipse files.
- **Rationale:** VS Code/IntelliJ work directly against `pom.xml`; single
  source of truth.

## D02 — Sensitive configuration via dotenv-java
- **Context:** login credentials were hardcoded in `MenuPanel`; the
  dotenv-java dependency sat unused in the pom.
- **Decision:** `.env` file (+ committed `.env.example`) and a `Config` class
  loading once with `ignoreIfMissing()` and fallback values.
- **Rationale:** reuse an existing dependency; a gift app must still run when
  `.env` is missing.
- **Refinement:** secret fallbacks are **empty strings** — no baked-in
  credentials. Without `.env` the app opens in guest mode but cannot log in.
  Personal letter contents (`LETTER_GUEST`, `LETTER_SPECIAL_1..3`) also live
  in `.env`; `{name}` placeholder and `\n` newlines handled by
  `Config.formatLetter()` — content separated from presentation.

## D03 — Data layer: SQLite first, server later, shared repository interfaces
- **Context:** needed high scores, user profiles, play history; weighed an
  embedded DB vs a real backend.
- **Decision:** Phase order SQLite (embedded JDBC) → Spring Boot +
  PostgreSQL behind the SAME interfaces → HTTP client implementation that
  falls back to SQLite offline.
- **Rationale:** risk reduced step by step; game code never depends on the
  data source; offline play is always preserved.

## D04 — Backend: Spring Boot
- **Context:** compared Spring Boot / Quarkus / Javalin / plain JDK
  HttpServer for ~5 endpoints.
- **Decision:** Spring Boot, multi-module Maven (`common`/`app`/`server`),
  PostgreSQL prod + H2 dev, Flyway migrations.
- **Rationale:** industry standard, rich ecosystem (Flyway, testing), accepts
  being "heavier than strictly necessary" as a learning cost.

## D05 — Test scope: logic + DAO + API, no Swing GUI unit tests
- **Decision:** JUnit 5 for game logic, services, DAOs, REST controllers
  (MockMvc). No unit tests for Swing panels.
- **Rationale:** GUI tests are expensive to maintain with low value here;
  bugs concentrate in logic. One sanctioned exception: the env-gated
  real-GUI focus regression suite (D13).

## D06 — CI GitHub Actions + Docker
- **Decision:** keep CI even as a solo developer; Dockerfile +
  docker-compose (server + PostgreSQL).
- **Rationale:** CI is an impartial referee and catches "works only on my
  machine"; Docker makes full-stack startup a one-liner.

## D07 — Documentation-first workflow
- **Decision:** every phase updated its status document before committing;
  everything gated by a Definition of Done; Conventional Commits throughout.
- **Rationale:** the project was built to be handover-friendly. The roadmap
  (Phases 1–10) is complete; history lives in git log.

## D08 — Navigation switched to named cards
- **Context:** old navigation relied on side effects of
  `setVisible(true/false)` — verified fragile in practice.
- **Decision:** register panels under named constants in `MainPanel`;
  switch screens only via official `CardLayout.show(parent, name)`; delete
  all self-hiding tricks. Added `Navigable` (`onEnter`/`onLeave`) to
  stop/reset game loops off-screen and mandatory `MainPanelAware` wiring
  (fail-fast at registration).
- Reverting to the old pattern is prohibited.

## D09 — BasePanel inheritance + UiUtils.loadFont
- **Decision:** all navigable panels extend `BasePanel` (frame reference,
  background image, null layout); fonts load exclusively through cached
  `UiUtils.loadFont(path, size)`; custom overlays override `paintComponent`
  and call `super` first. Game logic classes stay plain `JPanel`.
- Rationale: deleted ~1,400 lines of duplicated boilerplate across 32 panels.

## D10 — Data layer contract: facade + repositories, timers owned by wrappers
- **Decision:** GUI only touches `ScoreService`; underneath sit
  `UserRepository`/`ScoreRepository` interfaces with a SQLite implementation.
  Games NEVER start timers in constructors (wrapper `onEnter()` does) —
  hidden background games were polluting scores (real bug caught this way).
  Single SQLite connection per app; default DB file `anni.db` (gitignored),
  overridable via `DB_PATH`. Scores are "higher is better" for now;
  time/low-is-better games come later with their own convention.

## D11 — SFX: resource-first, synthesized fallback
- **Decision:** `SoundManager` loads `/sfx/<name>.wav` when present; missing
  files are replaced by synthesized tones (click / game-over sweep / win
  arpeggio). Sounds run on their own thread; audio errors are swallowed so
  they can never kill gameplay. No asset required — drop real wav files into
  `resources/sfx/` anytime.

## D12 — App icon reuses an existing game asset
- **Decision:** window icon = `/icons/PacMan Icon.jpg`. Note: macOS dock icon
  cannot be changed via `setIconImage` (needs jpackage/bundle) — accepted
  limitation.

## D13 — Key-driven games must claim focus in onEnter()
- **Decision:** every wrapper of a keyboard-controlled game calls
  `game.requestFocusInWindow()` inside `Navigable.onEnter()`. Never rely on
  Swing's auto focus transfer (it broke for real after the CardLayout
  refactor).
- **Regression guard:** `WrapperFocusConventionTest` always runs;
  `GuiFocusRegressionTest` (env-gated, real GUI) verifies focus ownership per
  card and that synthetic space input actually moves the dinosaur.

## D14 — Multi-module Maven: common / app / server
- **Decision:** extract shared data contracts (`GameCatalog`, `ScoreEntry`,
  `UserRepository`, `ScoreRepository`) into module `anni-common`; both the
  desktop app and the server depend on it. Package names unchanged so the
  client needed zero import edits.
- **Rationale:** swapping client implementations later touches no contracts;
  no DTO copy-paste between sides.

## D15 — Server DB: H2 in-memory by default, `postgres` profile for production
- **Decision:** dev/test on H2 mem `MODE=PostgreSQL` + Flyway; production
  enables profile `postgres` (`DB_URL`/`DB_USER`/`DB_PASSWORD` via env).
- **Rationale:** fast tests without infrastructure; schema written in
  PostgreSQL dialect so switching environments is just a profile flip.
