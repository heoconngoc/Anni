# PROGRESS.md — Bộ nhớ chung của dự án

> **Agent/lập trình viên: đọc file này ĐẦU TIÊN.** Cập nhật ngay sau mỗi phase.
> Ký hiệu: ✅ hoàn thành · 🔄 đang làm · ⬜ chưa làm · ❌ bị chặn

## Bảng trạng thái roadmap

| # | Phase | Trạng thái | Commit |
|---|---|---|---|
| 0 | Nền tảng tài liệu (`AGENTS.md`, `docs/`) | ✅ | `docs:` |
| 1 | Chuẩn hóa Maven (package, resources, .gitignore) | 🔄 | |
| 2 | `.env` bảo mật credentials (dotenv-java + Config.java) | ⬜ | |
| 3 | Điều hướng CardLayout chuẩn (named cards, dừng Timer) | ⬜ | |
| 4 | Refactor trùng lặp (UiUtils.loadFont, BasePanel) | ⬜ | |
| 5 | Tầng dữ liệu SQLite (repository interfaces + DAO + JUnit 5/Mockito) | ⬜ | |
| 6 | Trải nghiệm (SoundManager SFX, ScoreHubPanel, icon app) | ⬜ | |
| 7 | Spring Boot backend (multi-module client/server, PostgreSQL/H2, Flyway) | ⬜ | |
| 8 | Client ↔ Server (HTTP repository + fallback SQLite offline) | ⬜ | |
| 9 | CI GitHub Actions + Docker (Dockerfile, docker-compose) | ⬜ | |

## Chi tiết từng phase

### Phase 0 — Nền tảng tài liệu ✅
- Tạo `AGENTS.md`, `docs/PROGRESS.md`, `ARCHITECTURE.md`, `CODING_CONVENTIONS.md`, `DEFINITION_OF_DONE.md`, `DECISIONS.md`.

### Phase 1 — Chuẩn hóa Maven 🔄
- Kế hoạch: đổi package `main.java.com.dat.anni.*` → `com.dat.anni.*`; chuyển tài nguyên sang
  `src/main/resources/`; xóa cấu hình Eclipse (`.classpath`, `.project`, `.settings/`, `bin/`);
  thêm exec + shade plugin; viết lại `.gitignore`.

## 🔄 Đang làm
- **Phase 1 — Chuẩn hóa Maven**

## ⬜ Việc tiếp theo
- **Phase 2**: tạo `.env.example` + class `Config` (dotenv-java, `ignoreIfMissing()` + fallback), bỏ hardcode user/mật khẩu trong `MenuPanel.java`.

## Vướng mắc / Lưu ý kỹ thuật
- Navigation hiện dùng hack `setVisible(true/false)` với CardLayout — hoạt động nhưng fragile; sẽ sửa ở Phase 3, đừng nhân rộng pattern này khi viết panel mới.
- Các game khởi tạo toàn bộ panel ngay từ đầu trong `MainPanel.addComps()` — mọi thiếu tài nguyên sẽ làm app chết ngay lúc mở.

## Đề xuất / Việc tiếp theo (chưa có trong roadmap)
- (ghi đề xuất mới tại đây, chờ duyệt rồi mới đưa vào bảng trên)

## Ghi chú cho agent tiếp theo
- Luôn chạy `mvn clean verify` trước khi kết thúc phiên làm việc; nếu fail phải sửa hoặc ghi rõ vào "Vướng mắc".
- Không đổi quyết định trong `DECISIONS.md` nếu chưa có lý do mới đủ mạnh — ghi nhận thay đổi vào đó trước khi code.
