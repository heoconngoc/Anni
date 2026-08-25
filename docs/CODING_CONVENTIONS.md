# CODING_CONVENTIONS.md

Rules for writing code in this repository. Keep new code consistent with what
is already here.

## Formatting

- **Indentation: TABs** (existing codebase standard — stay consistent).
- Every Swing class declares `private static final long serialVersionUID = 1L;`
- Explicit imports only — no wildcards like `import java.awt.*;`

## Naming

- Packages: lowercase, no underscores → `com.dat.anni.game.snake`
- Classes/interfaces: `PascalCase`; panels end in `Panel`, rule screens
  `Xxx_RulePanel`, start screens `Xxx_StartPanel`
- Methods/variables: `camelCase`; constants: `UPPER_SNAKE_CASE`
- Swing components: type prefix + purpose → `btStart`, `lbTitle`, `tfUser`, `pnMain`
- Test methods: `should<Expectation>_when<Condition>()` — e.g.
  `shouldIncreaseSpeed_whenFoodEaten`

## Swing class pattern (mandatory)

```java
public class XxxPanel extends BasePanel implements MainPanelAware {
    public XxxPanel() {
        initPanel();   // layout, fonts, background
        addComps();    // create + add components
        addEvents();   // listeners
    }

    @Override
    public void setMainPanel(MainPanel main) { ... }
}
```

- **Navigation:** only through `main.show(MainPanel.CONSTANT)`. Never switch
  screens with `setVisible(true/false)` (hiding/showing inner components is
  still fine).
- **Game loop:** panels hosting a running game also implement `Navigable`;
  stop/reset timers in `onLeave()`, restart them in `onEnter()`.
- **Key-driven games:** `onEnter()` must call `game.requestFocusInWindow()` —
  see D13. Never rely on Swing's automatic focus transfer.
- **Timers:** game logic classes must NOT start timers in their constructors;
  lifecycle is owned by the wrapper panel via `onEnter()`.
- **Listeners only dispatch** — call game/service methods inside
  `actionPerformed`; do not write business logic there.
- Panels are display shells only; game logic lives in the game classes.

## Resources (fonts/images/sounds)

- Live under module `src/main/resources/`, loaded from the classpath root:
  `getResource("/imgs/x.png")`.
- Always close streams with try-with-resources.
- Load fonts exclusively via `UiUtils.loadFont(path, size)` (cached) — never
  copy-paste font-loading try/catch blocks.
- New panels extend `BasePanel` (frame reference + background image + null
  layout built in); pass the background path via `super("...")`. To draw an
  overlay, override `paintComponent` and call `super.paintComponent(g)` first.
- New asset filenames: lowercase, no spaces (use `-` or `_`).

## Exceptions & logging

- No `printStackTrace()` in new code. Client side: log to `System.err` with
  context ("Failed to load font X, falling back to Arial"). Server side:
  SLF4J (`log.error(...)`).
- Catch specific exceptions; catch broad `Exception` only at outermost
  boundaries (main/listeners).

## Sensitive configuration

- Never hardcode passwords/keys/personal URLs in source. Put them in `.env`
  and read them through `Config`. `.env` is never committed.

## Git

- Conventional Commits: `feat:` / `fix:` / `refactor:` / `docs:` / `test:` / `chore:`
- First line ≤ 72 characters; English messages preferred for this public repo.
- Never commit: `target/`, `bin/`, `.DS_Store`, `.env`, IDE files.

## Testing

- JUnit 5 (+ MockMvc for the server); tests required for all new logic, DAO
  and API code. Structure AAA (Arrange–Act–Assert); one test = one behavior.
- Swing GUI panels are not unit-tested by policy (D05). The one sanctioned
  end-to-end GUI suite is `GuiFocusRegressionTest`, gated behind
  `ANNI_GUI_PROBE=1` and self-skipping without a display.

## Other prohibitions

- New dependency? Record the rationale in `DECISIONS.md` first.
- New navigable panels must implement `MainPanelAware` (plus `Navigable` when
  they host a game loop or per-screen state).
