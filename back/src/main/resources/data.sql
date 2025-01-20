 -- Insertion des utilisateurs
INSERT INTO `user` (id, username, email, password) VALUES
(1, 'jean_dev', 'jean@dev.com', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG'), -- mot de passe: Password123
(2, 'marie_code', 'marie@code.com', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG'),
(3, 'pierre_tech', 'pierre@tech.com', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG');

-- Insertion des thèmes
INSERT INTO theme (id, title, description, is_subscribed) VALUES
(1, 'Java Spring Boot', 'Tout sur le développement avec Spring Boot', false),
(2, 'JavaScript Modern', 'ES6+, React, Vue.js et plus encore', false),
(3, 'DevOps', 'Docker, Kubernetes, CI/CD', false),
(4, 'Architecture Microservices', 'Patterns et bonnes pratiques', false);

-- Insertion des articles
INSERT INTO article (id, title, created_at, content, user_id, theme_id) VALUES
(1, 'Guide complet Spring Security', '2024-03-15', 'Spring Security est un framework puissant pour la sécurité des applications Java...', 1, 1),
(2, 'React vs Vue en 2024', '2024-03-16', 'Comparaison détaillée des deux frameworks les plus populaires...', 2, 2),
(3, 'Docker pour les débutants', '2024-03-17', 'Guide pas à pas pour débuter avec Docker...', 3, 3),
(4, 'Bonnes pratiques API REST', '2024-03-18', 'Les meilleures pratiques pour concevoir des API REST...', 1, 4);

-- Insertion des commentaires
INSERT INTO comment (id, created_at, content, article_id, user_id) VALUES
(1, '2024-03-15', 'Excellent article ! Très utile pour comprendre les bases de Spring Security.', 1, 2),
(2, '2024-03-16', 'Pourriez-vous détailler la partie sur JWT ?', 1, 3),
(3, '2024-03-16', 'La comparaison est très pertinente, merci !', 2, 1),
(4, '2024-03-17', 'Super introduction à Docker, parfait pour débuter.', 3, 2),
(5, '2024-03-18', 'Ces pratiques m''ont beaucoup aidé dans mon dernier projet.', 4, 3);