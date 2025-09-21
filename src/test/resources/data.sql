INSERT INTO students (name, kana_name, nick_name, email, area, age, sex, remark, isDeleted) VALUES
('三橋桃子', 'ミハシモモコ', 'モモカン', 'mihashi.momoko@example.com', '京都', 19, '女性', NULL, 0),
('佐藤花子', 'サトウハナコ', 'ハナハナ', 'hanako.sato@example.com', '東京', 29, '女性', NULL, 0),
('佐野康太', 'サノコウタ', 'コータン', 'sano.kota@example.com', '青森', 24, '男性', '', 0),
('安藤丈', 'アンドウジョウ', 'ジョウアン', 'ando.jo@example.com', '広島', 18, '男性', '', 0),
('山本健太', 'ヤマモトケンタ', 'ケンケン', 'kenta.yamamoto@example.com', '札幌', 22, '男性', NULL, 0);

INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at) VALUES
(1, 'Javaプログラミング基礎', NULL, NULL),
(2, 'ウェブ開発入門', '2025-07-05 09:00:00', '2025-09-30 17:00:00'),
(3, 'データベース設計', '2025-07-10 14:00:00', '2025-08-20 12:00:00'),
(4, 'データサイエンスのためのPython', '2025-07-15 11:00:00', '2025-09-10 15:00:00'),
(5, 'クラウドコンピューティング基礎', '2025-08-01 13:00:00', '2025-10-05 18:00:00');

