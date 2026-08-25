# CODING_CONVENTIONS.md — Quy tắc viết code

## Định dạng
- **Indent: TAB** (chuẩn hiện tại của codebase — giữ nguyên nhất quán).
- Mỗi class Swing có `private static final long serialVersionUID = 1L;`
- Import tường minh, không dùng wildcard `import java.awt.*;`

## Đặt tên
- Package: lowercase không gạch dưới → `com.dat.anni.game.snake`
- Class/interface: `PascalCase` — panel đuôi `Panel`, rule `Xxx_RulePanel`, start `Xxx_StartPanel`
- Method/biến: `camelCase`; hằng số: `UPPER_SNAKE_CASE`
- Component Swing: prefix loại + tên chức năng → `btStart`, `lbTitle`, `tfUser`, `pnMain`
- Test method: `should<Expectation>_when<Condition>()` — ví dụ `shouldIncreaseSpeed_whenFoodEaten`

## Pattern class Swing (bắt buộc)
```java
public class XxxPanel extends JPanel {
    public XxxPanel() {
        initPanel();   // layout, font, background
        addComps();    // tạo + add component
        addEvents();   // listener
    }
}
```
- **Listener chỉ điều phối** — gọi hàm của game/service, KHÔNG viết logic nghiệp vụ trong `actionPerformed`.
- Không đặt logic game vào Panel; panel chỉ là vỏ hiển thị.

## Tài nguyên (fonts/ảnh/âm thanh)
- Chỉ nằm ở `src/main/resources/`, load qua classpath root: `getResource("/imgs/x.png")`.
- Stream luôn đóng bằng try-with-resources.
- Font load qua helper chung (Phase 4 sẽ có `UiUtils.loadFont`) — **cấm copy-paste khối try-catch load font**.
- Filename mới: chữ thường, không dấu cách (dùng `-` hoặc `_`).

## Exception & logging
- Cấm `printStackTrace()` trong code mới. Giai đoạn trước Phase 7: log ra `System.err`
  kèm ngữ cảnh ("Không tải được font X, fallback Arial"). Từ Phase 7: SLF4J (`log.error(...)`).
- Bắt exception cụ thể, không bắt `Exception` trừ ranh giới ngoài cùng (main/listener).

## Cấu hình nhạy cảm
- Cấm hardcode mật khẩu/key/URL cá nhân trong source. Đưa vào `.env`, đọc qua
  `Config` (Phase 2). `.env` không bao giờ commit.

## Git
- Conventional Commits: `feat:` / `fix:` / `refactor:` / `docs:` / `test:` / `chore:`
- 1 phase ≥ 1 commit riêng; message dòng đầu ≤ 72 ký tự, tiếng Anh hoặc tiếng Việt nhất quán.
- Không commit: `target/`, `bin/`, `.DS_Store`, `.env`, file IDE.

## Test (từ Phase 5)
- JUnit 5 + Mockito; test logic và DAO bắt buộc với mọi code mới.
- Cấu trúc AAA (Arrange–Act–Assert), 1 test = 1 hành vi.
- GUI Swing: không bắt buộc unit test (ra quyết định D05).

## Việc cấm khác
- Không nhân rộng pattern điều hướng `setVisible(true/false)` hiện tại — chờ Phase 3 thay bằng named-card.
- Không thêm dependency mới khi chưa ghi vào `DECISIONS.md`.
