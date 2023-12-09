INSERT INTO public.role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO public."user" (id, first_name, last_name, email, password) VALUES (2, 'Юзер', 'Юзеров', 'user@user.user', '$2a$10$TD0wsaKh8/7fOgYFmAVQzOXjOIlvU4XlgNnnXYINopqxC6bOCFU22');
INSERT INTO public."user" (id, first_name, last_name, email, password) VALUES (1, 'Админ', 'Админов', 'vladislav.cazanczev@yandex.ru', '$2a$10$TD0wsaKh8/7fOgYFmAVQzOXjOIlvU4XlgNnnXYINopqxC6bOCFU22');

INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO public.user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO public.category (id, name, parent_category_id) VALUES (1, 'Смартфоны', null);
INSERT INTO public.category (id, name, parent_category_id) VALUES (2, 'Apple', 1);
INSERT INTO public.category (id, name, parent_category_id) VALUES (3, 'Samsung', 1);
INSERT INTO public.category (id, name, parent_category_id) VALUES (4, 'Сопутствующие товары', 1);
INSERT INTO public.category (id, name, parent_category_id) VALUES (5, 'Наушники', 4);
INSERT INTO public.category (id, name, parent_category_id) VALUES (6, 'Чехлы', 4);
INSERT INTO public.category (id, name, parent_category_id) VALUES (7, 'Аудиотехника', null);
INSERT INTO public.category (id, name, parent_category_id) VALUES (8, 'Портативные колонки', 7);
INSERT INTO public.category (id, name, parent_category_id) VALUES (9, 'Наушники', 7);

INSERT INTO public.item (id, name, description, image, price, sale_price, available_count) VALUES (1, 'Смартфон Apple iPhone 14', 'Lorem Ipsum', 'images/1jjd123mad.jpg', 13999, null, 10);
INSERT INTO public.item (id, name, description, image, price, sale_price, available_count) VALUES (4, 'Наушники Apple AirPods Pro ', 'Lorem Ipsum', 'images/s1j1ds.jpg', 11999, null, 0);
INSERT INTO public.item (id, name, description, image, price, sale_price, available_count) VALUES (6, 'Смартфон Huawei P50', 'Lorem Ipsum', 'images/asd123.jpg', 11999, null, 0);
INSERT INTO public.item (id, name, description, image, price, sale_price, available_count) VALUES (7, 'Умная колонка Яндекс Станция', 'Lorem Ipsum', 'images/321sda.jpg', 11999, null, 0);
INSERT INTO public.item (id, name, description, image, price, sale_price, available_count) VALUES (2, 'Смартфон Apple iPhone 13', 'Lorem Ipsum', 'images/sad13121s.jpg', 11999, null, 7);
INSERT INTO public.item (id, name, description, image, price, sale_price, available_count) VALUES (3, 'Смартфон Samsung Galaxy S8 ', 'Lorem Ipsum', 'images/sad13121s.jpg', 11999, null, 0);
INSERT INTO public.item (id, name, description, image, price, sale_price, available_count) VALUES (5, 'Чехол для Huawei P50 ', 'Lorem Ipsum Ipsum', 'images/sad13121s.jpg', 12000, null, 10);

INSERT INTO public.item_category (item_id, category_id) VALUES (1, 2);
INSERT INTO public.item_category (item_id, category_id) VALUES (2, 2);
INSERT INTO public.item_category (item_id, category_id) VALUES (3, 3);
INSERT INTO public.item_category (item_id, category_id) VALUES (4, 5);
INSERT INTO public.item_category (item_id, category_id) VALUES (6, 1);
INSERT INTO public.item_category (item_id, category_id) VALUES (7, 8);
INSERT INTO public.item_category (item_id, category_id) VALUES (4, 9);
INSERT INTO public.item_category (item_id, category_id) VALUES (5, 2);
INSERT INTO public.item_category (item_id, category_id) VALUES (5, 1);
INSERT INTO public.item_category (item_id, category_id) VALUES (5, 3);


