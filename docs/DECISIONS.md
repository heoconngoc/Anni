# DECISIONS.md — Sổ quyết định kỹ thuật (ADR rút gọn)

> Mỗi quyết định lớn: bối cảnh → lựa chọn → lý do. Đảo ngược quyết định cũ phải thêm
> mục mới (không sửa mục cũ) kèm lý do đủ mạnh.

## D01 — Giữ Java 17, build bằng Maven
- **Bối cảnh:** project gốc Java 17 + Maven + Eclipse song song.
- **Quyết định:** chuẩn hóa về Maven duy nhất, bỏ file Eclipse.
- **Lý do:** VS Code/IntelliJ đều làm việc trực tiếp với `pom.xml`; một nguồn sự thật duy nhất.

## D02 — Cấu hình nhạy cảm dùng dotenv-java (Phase 2) ✅
- **Bối cảnh:** user/mật khẩu login hardcode trong `MenuPanel`; dependency dotenv-java đã có sẵn trong pom nhưng chưa dùng.
- **Quyết định:** tạo `.env` (+ `.env.example` commit) và class `Config` load 1 lần với `ignoreIfMissing()` + giá trị fallback.
- **Lý do:** tận dụng dependency hiện có; app quà cá nhân vẫn chạy được ngay cả khi thiếu `.env`.
- **Bổ sung khi thực hiện (2026-08-25):** fallback cho secret là **giá trị rỗng**, không
  bake sẵn credentials vào source — thiếu `.env` thì app vẫn mở/chơi chế độ khách nhưng
  không login được. Kèm theo: thêm JUnit 5 từ Phase 2 (sớm hơn kế hoạch Phase 5) để test
  logic parse của `Config`.
- **Bổ sung 2 (2026-08-25):** toàn bộ nội dung thư cá nhân (guest + Special 1/2/3) cũng đưa
  vào `.env` (`LETTER_GUEST`, `LETTER_SPECIAL_1..3`) vì là dữ liệu riêng tư của tác giả.
  Placeholder `{name}` + `\n` xuống dòng, xử lý trong `Config.formatLetter()`; khung HTML
  (style) vẫn nằm ở code — tách nội dung khỏi trình bày.

## D03 — Tầng dữ liệu: SQLite trước, server sau, chung interface repository (Option C)
- **Bối cảnh:** cần lưu high scores, user profile, lịch sử chơi; cân nhắc embedded DB vs backend thật.
- **Quyết định:** Phase 5 SQLite nhúng qua JDBC; Phase 7 Spring Boot + PostgreSQL đứng sau cùng interface; Phase 8 HTTP impl tự fallback về SQLite khi offline.
- **Lý do:** giảm rủi ro từng bước, game code không phụ thuộc nguồn dữ liệu, luôn giữ khả năng chơi offline.

## D04 — Backend chọn Spring Boot (Phase 7)
- **Bối cảnh:** so sánh Spring Boot / Quarkus / Javalin / plain JDK HttpServer cho ~4–5 endpoint.
- **Quyết định:** Spring Boot, multi-module Maven (`client/`, `server/`), PostgreSQL prod + H2 dev, Flyway migration.
- **Lý do:** chuẩn ngành dễ học/dễ bàn giao cho agent sau; hệ sinh thái (JPA, Flyway, test) đầy đủ; chấp nhận "nặng hơn cần thiết" như chi phí học tập.

## D05 — Phạm vi test: logic + DAO, không GUI Swing
- **Quyết định:** JUnit 5 + Mockito cho game logic, service, DAO, REST controller (MockMvc). Không viết unit test cho panel Swing.
- **Lý do:** test GUI Swing tốn công bảo trì cao, giá trị/thiếu hụt thấp với app cá nhân; logic mới là nơi bug tập trung.

## D06 — CI GitHub Actions + Docker (Phase 9)
- **Quyết định:** giữ CI dù solo dev; Dockerfile + docker-compose (server + PostgreSQL).
- **Lý do:** CI = trọng tài khách quan cho nhiều agent cùng tham gia + môi trường sạch bắt lỗi kiểu "chỉ chạy trên máy tôi"; Docker biến việc dựng full-stack thành 1 lệnh.

## D07 — Quy trình tài liệu-first
- **Quyết định:** `PROGRESS.md` là bộ nhớ chung, cập nhật ngay sau mỗi phase; mọi phase phải qua DoD; Conventional Commits.
- **Lý do:** project được thiết kế để agent khác tiếp quản liên tục — docs là hợp đồng giao tiếp.

## D08 — Navigation CardLayout chuyển sang named-card ✅ (Phase 3)
- **Bối cảnh:** điều hướng cũ dựa side-effect của `setVisible(true/false)` — fragile, đã kiểm chứng thực nghiệm.
- **Quyết định:** đăng ký panel theo hằng tên trong `MainPanel` + `CardLayout.show(parent, name)`
  (API chính thức, tự quản lý visibility — đã test riêng để xác nhận); xóa toàn bộ self-hiding.
- Kèm theo: `Navigable` (`onEnter`/`onLeave`) để dừng/reset game loop khi rời panel;
  `MainPanelAware` bắt buộc cho mọi panel (fail-fast khi thiếu wiring).
- **Trạng thái:** đã hoàn thành Phase 3. Cấm quay lại pattern cũ.

## D09 — BasePanel kế thừa + UiUtils.loadFont ✅ (Phase 4)
- **Quyết định:** mọi panel điều hướng `extends BasePanel` (main + nền + layout null);
  font chỉ nạp qua `UiUtils.loadFont(path, size)` có cache; overlay riêng thì override
  `paintComponent` và gọi `super` trước.
- Game logic class (PacMan, SnakeGame...) vẫn extends JPanel — không ép qua BasePanel.
- **Trạng thái:** đã hoàn thành Phase 4.

## D10 — Data layer: repository interface + SQLite trước ✅ (Phase 5)
- **Quyết định:** GUI chỉ đụng `ScoreService`; bên dưới là `UserRepository`/`ScoreRepository`
  interfaces + impl SQLite. Phase 8 thay impl bằng HTTP client, GUI không đổi.
- SQLite dùng 1 connection duy nhất cho cả app (desktop, EDT đơn luồng là chính).
- Điểm hiện nay đều là "càng cao càng tốt"; game kiểu thời gian/lượt ít hơn sẽ thêm sau.
- Game KHÔNG được start Timer trong constructor (chỉ start ở wrapper `onEnter`) —
  tránh chạy ngầm + ghi điểm rác (bug thật đã bắt được trong Phase 5).
- File DB mặc định `anni.db` (gitignored), override bằng `DB_PATH` trong .env.

## D11 — Âm thanh SFX: resource-first, tự tổng hợp làm fallback ✅ (Phase 6)
- **Quyết định:** `SoundManager` nạp `/sfx/<ten>.wav` nếu có; thiếu thì tự sinh sóng sine
  (click / game-over thang / win arpeggio). Không bắt buộc asset, ai muốn thay âm thật
  chỉ việc bỏ file wav vào resources/sfx với đúng tên.
- SFX chạy thread riêng, mọi lỗi âm thanh bị nuốt (không làm chết game).
- Không viết unit test cho âm thanh (theo D05).

## D12 — Icon app dùng lại icon game có sẵn ✅ (Phase 6)
- **Quyết định:** icon cửa sổ = `/icons/PacMan Icon.jpg`. Muốn đổi → thay đường dẫn trong
  GUI hoặc bỏ file riêng rồi trỏ tới. macOS dock icon không đổi bằng setIconImage
  (cần jpackage/bundle riêng) — chấp nhận, không làm trong phase này.
