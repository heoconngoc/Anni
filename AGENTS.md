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
mvn clean package          # build + chạy toàn bộ test
mvn clean verify           # build + test (bước gate của DoD)
java -jar target/anni-arcade.jar   # chạy app từ jar
mvn compile exec:java      # chạy app trực tiếp khi dev
```

Yêu cầu: JDK 17+, Maven 3.8+.

## Cấu trúc thư mục

```
src/main/java/com/dat/anni/
├── main/        Main.java — entry point
├── gui/         Khung giao diện chung (GUI, MainPanel, các panel điều hướng)
├── game/<ten-game>/   Mỗi game: *_StartPanel, *_RulePanel, *Panel + class logic
└── (tương lai) config/, data/, net/
src/main/resources/    fonts/, imgs/, gifs/, icons/
server/                (Phase 7) Spring Boot backend
```
