#insert Roles
INSERT INTO Roles (name)
VALUE ('Doctor');

INSERT INTO Roles (name)
  VALUE ('Patient');


#insert DOCTORS

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (1,'doctor1','Ивано Сергей Генадьевич','b053f39eb48a55286416d91a72f589c1','12345','Спб, ул. Косатых 23', '123666');#, 'ivanov.segey@apteka.ru','Сергей');
#login:doctor1 pass: doc1pass

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (1,'doctor2','Агенкова Светлана Николаевна','6864522ca1925fddefaf2866f71558cb','12345','Спб, ул. Петра 123', '342345');#, 'agenkova.svetlana@apteka.ru','Сергей');
#login:doctor2 pass: doc2pass

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (1,'doctor3','Петрович Антон Валерьевич','200ec3444b554ebc3b1f489514c12b36','12345','Спб, ул. Н. Проспект 1', '897655');#, 'anton.valeryevich@apteka.ru','Антон');
#login:doctor3 pass: doc3pass

#insert PATIENTS
INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (2,'patient1','Иванов Петр Егорыч','f8a6286059798970cc07bc3ed85f41de','12345','Спб ул. Ленина д 45', '125676');#, 'ivanovp@apteka.ru','Петр');
#login:patient1 pass: pat1pass

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (2,'patient2','Егоров Савелий Егорыч','6f3036bf323000948d2f222d9fcbe0e5','12345','Спб ул. Новая площадь д 495', '908909');#, 'seveliyegorov@apteka.ru','Савелий');
#login:patient2 pass: pat2pass

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (2,'patient3','Аглы Арина Петровна','ebec976cddae44236f58dda4e0434182','12345','Спб ул. Старый город д 12', '908909');#, 'arina.petrovna@apteka.ru','Арина');
#login:patient3 pass: pat3pass

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (2,'patient4','Дережко Алина Николаевна','190734863fa8b530fc48e8ccc88363c4','12345','Спб ул. Старый город д 12', '898909');#, 'alina.nikolaevna@apteka.ru','Алина');
#login:patient4 pass: pat4pass

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (2,'patient4','Познеркич Владимир Антонович','190734863fa8b530fc48e8ccc88363c4','12345','Спб ул. Усатых д 72', '121256');#, 'vladimir.antonovich@apteka.ru','Владимир');
#login:patient4 pass: pat4pass

INSERT INTO users (role_id,login,full_name,hash,salt,address,medical_policy_number)#,email,name)
VALUES (2,'patient5','Аленк Валентина Сафроновна','428a77153563f62f40d98719bce20985','12345','Спб ул. Полосатых д 19', '121256');#, 'valentinas@apteka.ru','Валентина');
#login:patient5 pass: pat5pass

#insert Meterials

INSERT INTO Materials (name)
  VALUE ('Шалфей');

INSERT INTO Materials (name)
  VALUE ('Ромашка');

INSERT INTO Materials (name)
  VALUE ('Пустырник');

INSERT INTO Materials (name)
  VALUE ('Корень имбиря');

INSERT INTO Materials (name)
  VALUE ('Солодка');

INSERT INTO Materials (name)
  VALUE ('Полынь');

INSERT INTO Materials (name)
  VALUE ('Мех овцы');

INSERT INTO Materials (name)
  VALUE ('Шкура медведя');

INSERT INTO Materials (name)
  VALUE ('Копыта коровы');

INSERT INTO Materials (name)
  VALUE ('Хвост осла');

INSERT INTO Materials (name)
  VALUE ('Уши собаки');

#insert StorehouseInventory
INSERT INTO storehouse_inventory (count, material_id)
VALUES (4,1);

INSERT INTO storehouse_inventory (count, material_id)
VALUES (14,2);

INSERT INTO storehouse_inventory (count, material_id)
VALUES (44,5);

INSERT INTO storehouse_inventory (count, material_id)
VALUES (12,8);

#insert Drugs
INSERT INTO Drugs (name, description)
VALUES ('Синяя смесь','От головной боли');

INSERT INTO Drugs (name, description)
VALUES ('Красная смесь','От живота');

INSERT INTO Drugs (name, description)
VALUES ('Шипящий отвар','От ушибов');

INSERT INTO Drugs (name, description)
VALUES ('Газированный напиток','От изжоги');

INSERT INTO Drugs (name, description)
VALUES ('Настойка горькая','От температуры');

INSERT INTO Drugs (name, description)
VALUES ('Мазь лечебная','От переломов');

SELECT * FROM Materials;
#insert Ingredients
INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (1,1,1);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (1,2,2);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (1,3,4);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (2,2,2);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (2,8,4);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (2,7,10);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (3,1,1);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (3,5,1);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (3,9,2);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (4,11,10);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (4,10,11);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (5,2,115);

INSERT INTO Ingredients (drug_id, material_id, count)
VALUES (5,6,6);

#insert Recipes
INSERT INTO Recipes (title, created_at)
VALUES ('От головной боли','12.04.2015');

INSERT INTO Recipes (title, created_at)
VALUES ('От несварения желудка','12.04.2015');

INSERT INTO Recipes (title, created_at)
VALUES ('От перелома ноги','22.04.2015');

INSERT INTO Recipes (title, created_at)
VALUES ('От простуды','06.03.2015');

#insert DrugProgressStatuses
INSERT INTO drug_progress_statuses (title)
  VALUE ('Новый');

INSERT INTO drug_progress_statuses (title)
  VALUE ('Принят в обработку');

INSERT INTO drug_progress_statuses (title)
  VALUE ('В процессе изготовления');

INSERT INTO drug_progress_statuses (title)
  VALUE ('Собран');

#insert RecipesHasDrugs
INSERT INTO recipes_has_drugs (count, drug_id, progress_status_id, recipe_id)
VALUES (3,1,1,1);

INSERT INTO recipes_has_drugs (count, drug_id, progress_status_id, recipe_id)
VALUES (1,2,1,2);

INSERT INTO recipes_has_drugs (count, drug_id, progress_status_id, recipe_id)
VALUES (4,6,1,3);

INSERT INTO recipes_has_drugs (count, drug_id, progress_status_id, recipe_id)
VALUES (1,5,1,4);

#insert Diagnoses
INSERT INTO Diagnoses (patient_user_id, doctor_user_id, recipe_id, diagnosis, complaints, created_at)
VALUES (1,1,1,'Головная боль','Сильная головная боль,головокружение','12.04.2015');

INSERT INTO Diagnoses (patient_user_id, doctor_user_id, recipe_id, diagnosis, complaints, created_at)
VALUES (2,1,2,'Отравление','Сильная боль в желудке,головокружение','22.04.2015');

INSERT INTO Diagnoses (patient_user_id, doctor_user_id, recipe_id, diagnosis, complaints, created_at)
VALUES (2,1,2,'Перелом','Сильная боль в бедре ноги,головокружение','12.03.2015');

INSERT INTO Diagnoses (patient_user_id, doctor_user_id, recipe_id, diagnosis, complaints, created_at)
VALUES (2,1,2,'ОРЗ','Кашель,температура','12.04.2015');
