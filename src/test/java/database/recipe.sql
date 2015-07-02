INSERT INTO recipes (title, created_at, pharmacy_id, recipe_progress_status_id)
VALUES ('Тестовый рецепт Apteka',NOW(),2,1);
SELECT * FROM recipes WHERE id = last_insert_id();
UPDATE recipes SET title = 'Обновленный тестовый рецепт Apteka' WHERE id = last_insert_id();
SELECT * FROM recipes WHERE id = last_insert_id();
DELETE FROM recipes WHERE id = last_insert_id();
SELECT * FROM recipes WHERE id = last_insert_id();
