INSERT INTO recipes (title, created_at, pharmacy_id, recipe_progress_status_id) VALUES ('Тестовый рецепт Apteka',NOW(),2,1);

SET @lastInsertId = last_insert_id();
SELECT * FROM recipes WHERE id = @lastInsertId;

INSERT INTO recipes_has_drugs (recipe_id, drug_id, progress_status_id, count, done) VALUES (@lastInsertId,1,1,1,1);
SET @lastInserIdRecipeHasDrug1 = last_insert_id();

INSERT INTO recipes_has_drugs (recipe_id, drug_id, progress_status_id, count, done) VALUES (@lastInsertId,2,1,2,0);
SET @lastInserIdRecipeHasDrug2 = last_insert_id();

INSERT INTO recipes_has_drugs (recipe_id, drug_id, progress_status_id, count, done) VALUES (@lastInsertId,3,1,10,1);
SET @lastInserIdRecipeHasDrug3 = last_insert_id();

SELECT * FROM recipes_has_drugs WHERE
  id = @lastInserIdRecipeHasDrug1 OR
  id = @lastInserIdRecipeHasDrug2 OR
  id = @lastInserIdRecipeHasDrug3;

DELETE FROM recipes WHERE id = @lastInsertId;
SELECT * FROM recipes WHERE id = @lastInsertId;
SELECT * FROM recipes_has_drugs WHERE
  id = @lastInserIdRecipeHasDrug1 OR
  id = @lastInserIdRecipeHasDrug2 OR
  id = @lastInserIdRecipeHasDrug3;