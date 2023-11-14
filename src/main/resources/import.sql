-- INSERT INTERMEDIARY
INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, created_date, modified_date) VALUES (1, 'abc1@naver.com', '{bcrypt}$2a$10$Llg2MwZS/oOv/2/ozaice.49CU35kK6W9kYEb.oqyTy6vmh7E4yv2', '한호정', 'https://connectdog.site', 'authImageUrl', 'profileImageUrl', '안녕하세요.', '인스타그램: @hoxjeong', 'AUTH_INTERMEDIARY', false, false, now(), now());
-- INSERT VOLUNTEER
INSERT INTO volunteer (id, email, password, nickname, profile_image_num, name, phone, role, is_option_agr, notification, created_date, modified_date) VALUES (1, 'abc@naver.com', '{bcrypt}$2a$10$VieltvcRaI/rJnaRHuRPju9rqM9BvmKRkmn./oOninx7fOGT/q2De', '봉사자하노정', 1, '한호정', '01022223333', 'AUTH_VOLUNTEER', false, false, now(), now());
-- INSERT DOG
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (1, '포포', 0, 1, 4.6, '밥을 잘 먹음', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (2, '남2포포', 1, 0, 4.6, '밥을 잘 먹음', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (3, '여3포포', 2, 1, 4.6, '밥을 잘 먹음', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (4, '여4포포', 2, 1, 4.6, '밥을 잘 먹음', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (5, '남5포포', 1, 0, 4.6, '밥을 잘 먹음', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (6, '남6포포', 1, 0, 4.6, '밥을 잘 먹음', now(), now());
-- INSERT POST
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (1, 0, '서울시 성북구', '경기 고양시', '2023-11-13', '2023-11-25', '13:00', false, '이동봉사 공고입니다.', 1, 1, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (2, 0, '서울시 성북구', '경기 고양시', '2023-11-13', '2023-11-25', '13:00', false, '이동봉사 공고입니다.', 1, 2, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (3, 1, '서울시 성북구', '경기 고양시', '2023-11-13', '2023-11-25', '13:00', false, '이동봉사 공고입니다.', 1, 3, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (4, 2, '서울시 성북구', '경기 고양시', '2023-11-13', '2023-11-25', '13:00', false, '이동봉사 공고입니다.', 1, 4, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (5, 3, '서울시 성북구', '경기 고양시', '2023-11-13', '2023-11-25', '13:00', false, '이동봉사 공고입니다.', 1, 5, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (6, 4, '서울시 성북구', '경기 고양시', '2023-11-13', '2023-11-25', '13:00', false, '이동봉사 공고입니다.', 1, 6, now(), now());
-- INSERT POST_IMAGE
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (1, 'image1', 1, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (2, 'image2', 1, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (3, 'image3', 1, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (4, 'image1', 2, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (5, 'image2', 2, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (6, 'image1', 3, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (7, 'image2', 3, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (8, 'image1', 4, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (9, 'image2', 4, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (10, 'image1', 5, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (11, 'image2', 5, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (12, 'image1', 6, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (13, 'image2', 6, now(), now());
-- UPDATE POST MAIN IMAGE
UPDATE post SET main_image_id = 1 WHERE id = 1;
UPDATE post SET main_image_id = 4 WHERE id = 2;
UPDATE post SET main_image_id = 6 WHERE id = 3;
UPDATE post SET main_image_id = 8 WHERE id = 4;
UPDATE post SET main_image_id = 10 WHERE id = 5;
UPDATE post SET main_image_id = 12 WHERE id = 6;
-- INSERT APPLICATION
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (1, 0, '한호정', '01022223333', 'BMW', '이동봉사 신청합니다!', 3, 1, 1,now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (2, 1, '한호정', '01022223333', 'BMW', '이동봉사 신청합니다!', 4, 1, 1,now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (3, 2, '한호정', '01022223333', 'BMW', '이동봉사 신청합니다!', 5, 1, 1,now(), now());
