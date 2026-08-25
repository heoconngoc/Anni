# PROGRESS.md — Bộ nhớ chung của dự án

> **Agent/lập trình viên: đọc file này ĐẦU TIÊN.** Cập nhật ngay sau mỗi phase.
> Ký hiệu: ✅ hoàn thành · 🔄 đang làm · ⬜ chưa làm · ❌ bị chặn

## Bảng trạng thái roadmap

| # | Phase | Trạng thái | Commit |
|---|---|---|---|
| 0 | Nền tảng tài liệu (`AGENTS.md`, `docs/`) | ✅ | `docs:` |
| 1 | Chuẩn hóa Maven (package, resources, .gitignore) | ✅ | `refactor:` |
| 2 | `.env` bảo mật credentials (dotenv-java + Config.java) | ✅ | `feat:` |
| 3 | Điều hướng CardLayout chuẩn (named cards, dừng Timer) | ✅ | `refactor:` |
| 4 | Refactor trùng lặp (UiUtils.loadFont, BasePanel) | ⬜ | |
| 5 | Tầng dữ liệu SQLite (repository interfaces + DAO + JUnit 5/Mockito) | ⬜ | |
| 6 | Trải nghiệm (SoundManager SFX, ScoreHubPanel, icon app) | ⬜ | |
| 7 | Spring Boot backend (multi-module client/server, PostgreSQL/H2, Flyway) | ⬜ | |
| 8 | Client ↔ Server (HTTP repository + fallback SQLite offline) | ⬜ | |
| 9 | CI GitHub Actions + Docker (Dockerfile, docker-compose) | ⬜ | |

## Chi tiết từng phase

### Phase 0 — Nền tảng tài liệu ✅
- Tạo `AGENTS.md`, `docs/PROGRESS.md`, `ARCHITECTURE.md`, `CODING_CONVENTIONS.md`, `DEFINITION_OF_DONE.md`, `DECISIONS.md`.

### Phase 1 — Chuẩn hóa Maven ✅
- Đổi package `main.java.com.dat.anni.*` → `com.dat.anni.*` (43 file Java, 0 sót).
- Chuyển tài nguyên `fonts/`, `imgs/`, `gifs/`, `icons/` sang `src/main/resources/` (đã đối chiếu
  100% đường dẫn `getResource` trong code — khớp hết, kể cả tên file có dấu cách).
- Xóa `.classpath`, `.project`, `.settings/`, `bin/`; gỡ khỏi git tracking (trước đó `bin/*.class`
  bị commit); viết lại `.gitignore`.
- `pom.xml`: UTF-8, pin compiler/surefire, `exec-maven-plugin` (dev), `maven-shade-plugin`
  (fat jar `target/anni-arcade.jar` ~32MB chạy trực tiếp được).
- Kiểm chứng: `mvn clean verify` exit 0; smoke test `java -jar target/anni-arcade.jar`
  chạy 6s không crash, log không có exception.

### Phase 2 — .env bảo mật credentials ✅
- Class `com.dat.anni.config.Config`: load `Dotenv` một lần với `ignoreIfMissing()`;
  parse `APP_VALID_USERS` (trim/lowercase/bỏ đoạn rỗng) và `APP_PASSWORD`.
- **Không fallback secret trong source**: thiếu `.env` → app vẫn chạy ở chế độ khách,
  chỉ mất login đặc biệt (`VALID_USERS` rỗng, password rỗng).
- `.env.example` được commit làm mẫu; `.env` thật đã gitignore (đã kiểm chứng bằng
  `git check-ignore`).
- Thêm JUnit 5 vào pom; `ConfigTest` 6 test cho `parseUsers` — all pass.
- Smoke test 2 kịch bản: chạy jar từ thư mục có `.env` và không có `.env` — đều sạch.
- **Bổ sung (cùng phase):** chuyển toàn bộ **4 bức thư cá nhân** từ hardcode sang `.env` —
  `LETTER_GUEST` (thư khách, placeholder `{name}`), `LETTER_SPECIAL_1/2/3` (bộ thư 3 phần sau
  login). Quy ước `\n` = xuống dòng; helper `Config.formatLetter()` có test. Nội dung gốc được
  trích xuất tự động từ source nên giữ nguyên văn.

### Phase 3 — Điều hướng CardLayout chuẩn ✅
- Viết lại `MainPanel`: 32 panel đăng ký theo hằng tên (`MainPanel.SNAKE_GAME`...),
  điều hướng qua **một** phương thức `show(name)` dùng `CardLayout.show()` chính thức
  (đã kiểm chứng thực nghiệm: show() tự ẩn các card khác, không còn chồng lớp).
- Xóa 33 method `showXxxPanel()` + toàn bộ cặp `show()/setVisible(false)` rải rác
  (~64 call site chuyển đổi).
- Interface `Navigable` (`onEnter`/`onLeave`): MainPanel gọi đúng lúc khi chuyển màn.
  Dừng game loop khi rời panel — sửa lỗi game chạy ngầm trong nền:
  - PacMan/SpaceInvaders: `resetGame()` + `resumeGame()` (mới thêm)
  - ChromeDinosaur/FlappyBird: `resetGame()` (mới thêm)
  - Snake/MatchCards: tái dùng restart; WhacAMole: `stopTimers` + chống double-timer
- Interface `MainPanelAware`: panel mới bắt buộc implement để được nối tham chiếu
  MainPanel — `register()` fail-fast nếu thiếu (bắt được lỗi wiring ngay lúc boot).
- Dọn dead code: `WhacAMole.newGame()`, `SnakeGame.get/setHighScore`.
- Kiểm chứng: `mvn clean verify` xanh (10 test), smoke test boot sạch. Khuyến nghị:
  người dùng click thử tay qua các màn hình + vào giữa trận rồi Back ở từng game.

## 🔄 Đang làm
(không có — mọi phase đã đóng)

## ⬜ Việc tiếp theo
- **Phase 4**: refactor trùng lặp — `UiUtils.loadFont()` thay ~40 khối try-catch,
  `BasePanel` dùng chung background/nút Back.

## Vướng mắc / Lưu ý kỹ thuật
- Navigation hiện dùng hack `setVisible(true/false)` với CardLayout — hoạt động nhưng fragile; sẽ sửa ở Phase 3, đừng nhân rộng pattern này khi viết panel mới.
- Các game khởi tạo toàn bộ panel ngay từ đầu trong `MainPanel.addComps()` — mọi thiếu tài nguyên sẽ làm app chết ngay lúc mở.

## Đề xuất / Việc tiếp theo (chưa có trong roadmap)
- (ghi đề xuất mới tại đây, chờ duyệt rồi mới đưa vào bảng trên)

## Ghi chú cho agent tiếp theo
- Luôn chạy `mvn clean verify` trước khi kết thúc phiên làm việc; nếu fail phải sửa hoặc ghi rõ vào "Vướng mắc".
- Không đổi quyết định trong `DECISIONS.md` nếu chưa có lý do mới đủ mạnh — ghi nhận thay đổi vào đó trước khi code.
