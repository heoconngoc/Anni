# DEFINITION_OF_DONE.md — Định nghĩa hoàn thành

> Một phase/mục việc chỉ được commit khi **toàn bộ checklist dưới đây đạt**.
> Không bỏ bước, không đánh dấu "sẽ làm sau".

## Checklist DoD

### 1. Code
- [ ] Tuân thủ `CODING_CONVENTIONS.md` (tab, đặt tên, pattern Swing, không printStackTrace...).
- [ ] Không có code chết, comment rác, TODO dở dang (hoặc TODO đã ghi vào `PROGRESS.md → Vướng mắc`).

### 2. Build & Test
- [ ] `mvn clean verify` chạy thành công từ đầu đến cuối.
- [ ] Mọi logic/DAO mới hoặc bị sửa **có test tương ứng** (JUnit 5/Mockito — trừ GUI Swing theo D05).
- [ ] **Toàn bộ test pass** (không skip test để cho xanh).

### 3. Kiểm thử tay (smoke test)
- [ ] Tính năng liên quan đã mở app chạy thử thật: luồng chính + 1 luồng lỗi.
- [ ] Với phase đổi tài nguyên/điều hướng: duyệt qua TẤT CẢ màn hình và 8 game.

### 4. Tài liệu
- [ ] `docs/PROGRESS.md`: cập nhật trạng thái phase, mục "Đang làm", "Việc tiếp theo", "Vướng mắc".
- [ ] Nếu thay đổi kiến trúc → cập nhật `ARCHITECTURE.md`.
- [ ] Nếu ra/thay quyết định kỹ thuật → thêm mục mới trong `DECISIONS.md`.

### 5. Commit
- [ ] `git diff` tự review trước khi stage — không commit thừa file rác.
- [ ] Chỉ stage đúng file thuộc phase này (`git add` từng đường dẫn, không `git add .` mù quáng).
- [ ] Message theo Conventional Commits, mô tả đúng việc đã làm.
- [ ] Đảm bảo KHÔNG có `.env`, secret, `target/`, `bin/`, `.DS_Store` trong staged files.

### 6. Bàn giao
- [ ] Trạng thái project tại điểm commit này chạy được ngay sau `mvn clean package`
      (agent khác checkout là build/run được, không cần bước tay nào ngoài docs ghi rõ).

## Quy trình 1 phase (tóm tắt)
```
Đọc PROGRESS.md → Code → mvn clean verify → smoke test → cập nhật docs
→ git diff review → stage chọn lọc → commit (Conventional Commits) → phase kế
```

## Ngoại lệ
Nếu bị chặn giữa phase (thiếu thông tin, bug khó...): commit WIP với prefix `wip:` là
**không được phép**. Thay vào đó ghi chi tiết vào `PROGRESS.md → Vướng mắc`, revert về
trạng thái sạch, và để phase ở trạng thái 🔄.
