# ARCHITECTURE.md — Kiến trúc hiện tại & đích đến

## Kiến trúc HIỆN TẠI (sau Phase 1)

Ứng dụng desktop Java Swing đơn khối (monolith), chạy hoàn toàn local.

```
┌──────────────────────────────────────────────────────┐
│ Main (entry point)                                   │
│   └── GUI extends JFrame (1000×700, CardLayout)      │
│        └── MainPanel extends JPanel (CardLayout)     │
│             ├── StartPanel → MenuPanel (login)       │
│             ├── NormalPanel / SpecialPanel(1,2,3)    │
│             ├── GamePanel / Game2Panel               │
│             └── 8 game × 3 panel:                    │
│                 <Game>_StartPanel / _RulePanel       │
│                 <Game>Panel (bọc class logic game)   │
└──────────────────────────────────────────────────────┘
```

### Các module chính
| Package | Vai trò |
|---|---|
| `com.dat.anni.main` | Entry point |
| `com.dat.anni.gui` | Khung app, điều hướng, login |
| `com.dat.anni.config` | `Config` — đọc `.env` (credentials login, cấu hình), không fallback secret |
| `com.dat.anni.game.<tên-game>` | 8 game: pacman, snake, minesweeper, whacamole, spaceinvaders, chormedinosaur, flappybird, matchcard |

### Mô hình một game
- Class logic (`SnakeGame`, `PacMan`...) extends JPanel, game loop bằng `javax.swing.Timer`,
  input qua KeyListener/MouseListener, vẽ tay trong `paintComponent`.
- Điểm cao lưu qua `java.util.prefs.Preferences`.

### Điều hướng (đã biết fragile — sửa ở Phase 3)
- Toàn bộ panel được tạo sẵn trong `MainPanel.addComps()`.
- Chuyển màn hình = gọi `main.showXxx()` + panel cũ tự `setVisible(false)` — dựa vào
  side-effect của `CardLayout.layoutContainer`, không phải API chính thức.
- **Hệ quả:** mọi tài nguyên được load lúc khởi động → thiếu 1 file là app chết ngay.

## Kiến trúc ĐÍCH ĐẾN (sau Phase 9)

```
anni/ (multi-module Maven)
├── client/  Swing app
│    └── Repository interfaces ←─ ScoreService ←─ GUI/Games
│         ├── impl SQLite (offline, luôn có)          [Phase 5]
│         └── impl HTTP  → gọi server, fallback SQLite [Phase 8]
└── server/  Spring Boot REST API                      [Phase 7]
     ├── POST /api/users · GET/POST /api/scores · GET /api/history
     ├── PostgreSQL (prod) / H2 (dev), Flyway migration
     └── SLF4J + Logback
CI: GitHub Actions build+test cả 2 module               [Phase 9]
Deploy dev: docker compose up (server + PostgreSQL)     [Phase 9]
```

### Nguyên tắc thiết kế xuyên suốt
1. **Repository pattern:** game/GUI chỉ biết interface (`ScoreRepository`, `UserRepository`,
   `HistoryRepository`), không biết nguồn dữ liệu là gì → thay SQLite ↔ HTTP không sửa game code.
2. **Offline-first:** mất mạng/mất server, app vẫn chơi và lưu điểm cục bộ bình thường.
3. **Không đưa logic nghiệp vụ vào listener Swing** — listener chỉ điều phối.
