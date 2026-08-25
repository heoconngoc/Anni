# AGENTS.md — Điểm xuất phát cho AI agent / lập trình viên mới

Project: **Anni Arcade (J4U Arcade)** — bộ sưu tập mini-game desktop Java Swing,
làm quà cá nhân, đang được nâng cấp dần thành ứng dụng client-server.

## Quy tắc làm việc bắt buộc

1. **Đọc tài liệu theo thứ tự trước khi sửa code bất kỳ điều gì:**
   1. [`docs/PROGRESS.md`](docs/PROGRESS.md) — project đang ở đâu, việc kế tiếp là gì
   2. [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) — kiến trúc hiện tại và đích đến
   3. [`docs/CODING_CONVENTIONS.md`](docs/CODING_CONVENTIONS.md) — quy tắc viết code
   4. [`docs/DEFINITION_OF_DONE.md`](docs/DEFINITION_OF_DONE.md) — tiêu chí hoàn thành một phần việc
   5. [`docs/DECISIONS.md`](docs/DECISIONS.md) — các quyết định kỹ thuật đã chốt (đừng đảo ngược nếu không có lý do mới)

2. **Không làm việc ngoài roadmap** trong `PROGRESS.md`. Muốn thêm việc mới → ghi đề xuất vào mục "Đề xuất / Việc tiếp theo" rồi mới làm.

3. **Sau mỗi phase/mục việc:** cập nhật ngay `PROGRESS.md` (trạng thái, vướng mắc, bước tiếp theo) — đây là bộ nhớ chung giữa các agent.

4. **Mọi thay đổi phải qua Definition of Done** trước khi commit.

5. **Commit theo Conventional Commits** (`feat:`, `fix:`, `refactor:`, `docs:`, `test:`, `chore:`), mỗi phase ít nhất 1 commit riêng.

6. **Tuyệt đối không commit:** `.env`, key/mật khẩu hardcode, file build (`target/`, `bin/`), `.DS_Store`.

## Lệnh thường dùng

```bash
mvn clean package                    # build + test toàn bộ 3 module
mvn clean verify                     # build + test (bước gate của DoD)
java -jar app/target/anni-arcade.jar     # chạy app desktop từ jar
java -jar server/target/anni-server.jar  # chạy backend (port 8080)
mvn compile exec:java -pl app        # chạy app trực tiếp khi dev
ANNI_GUI_PROBE=1 mvn test            # bật thêm test GUI thật (focus D13)
```

Multi-module Maven: `common` (hợp đồng dữ liệu) → `app` (desktop Swing) +
`server` (Spring Boot, Phase 7).

Yêu cầu: JDK 17+, Maven 3.8+.

> **Lỗi "Unresolved compilation problem" khi `exec:java`?** Đó là class hỏng do IDE
> (Eclipse/VS Code Java) ghi vào `target/classes` khi workspace chưa nhận diện package mới.
> Chạy `mvn clean compile exec:java` (luôn có `clean`) và refresh/clean workspace của IDE.

## Cấu trúc thư mục

```
common/src/main/java/com/dat/anni/data/    GameCatalog, ScoreEntry + repo/ interfaces (dùng chung)
app/                                       App desktop Swing (client hiện tại)
└── src/main/java/com/dat/anni/
    ├── main/        Main.java — entry point
    ├── gui/         Khung giao diện chung (GUI, MainPanel, BasePanel, ScoreHubPanel)
    ├── config/      Config.java — đọc .env (credentials, cấu hình app)
    ├── util/        UiUtils.java (font cache), SoundManager.java (SFX)
    ├── game/<ten-game>/   Mỗi game: *_StartPanel, *_RulePanel, *Panel + class logic
    └── data/        Database(SQLite), AppSession, ScoreService, sqlite/ impls
app/src/main/resources/    fonts/, imgs/, gifs/, icons/, sfx/
server/                                    Spring Boot backend (Phase 7)
└── src/main/java/com/dat/anni/server/     ServerApplication, repo/ JDBC, web/ REST API
```
