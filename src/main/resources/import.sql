-- INSERT INTERMEDIARY
INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, created_date, modified_date) VALUES (1, 'abc1@naver.com', '{bcrypt}$2a$10$Llg2MwZS/oOv/2/ozaice.49CU35kK6W9kYEb.oqyTy6vmh7E4yv2', '이동봉사 중개', 'https://connectdog.site', 'authImageUrl', 'profileImageUrl', '안녕하세요.', '인스타그램: @hoxjeong', 'AUTH_INTERMEDIARY', false, false, now(), now());

INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, guide, created_date, modified_date) VALUES (2, 'i2@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '천사들의 구름쉼터', 'https://connectdog2.site', 'authImageUrl', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/intermediary/intermediary2.png', '50마리의 아이들이 지내고 있는 천사들의 구름쉼터입니다 :)', '인스타그램 @angel_cloud\n카카오톡 angelcloud02', 'AUTH_INTERMEDIARY', false, false, '안녕하세요~ 천사들의 구름쉼터 입니다. 저희는 따뜻한 봉사자들과 함께 운영하는 봉사단체로 연락이 조금 느릴 수 있지만 최대한 바로 봉사 관련 문의를 전달드리겠습니다 :)', now(), now());

INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, guide, created_date, modified_date) VALUES (3, 'i3@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '생명사랑', 'https://connectdog3.site', 'authImageUrl', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/intermediary/intermediary3.png', '[사단법인] 생명사랑, 후원 환영', '인스타그램 @lovelifehappy', 'AUTH_INTERMEDIARY', false, false, '인스타그램 DM으로만 문의 받습니다.\n보호소 봉사의 경우 네이버 카페 생명사랑에서 신청해 주세요:)', now(), now());

INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, guide, created_date, modified_date) VALUES (4, 'i4@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '아지네 보호소', 'https://connectdog4.site', 'authImageUrl', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/intermediary/intermediary1.png', '강원도에 위치한 작은 보호소 입니다:)', '카카오톡 ajianimalprotect', 'AUTH_INTERMEDIARY', false, false, '초보 봉사자분들도 환영입니다:) 아이들의 이동을 도와주세요.', now(), now());

INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, guide, created_date, modified_date) VALUES (5, 'i5@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '프리독멍멍', 'https://connectdog5.site', 'authImageUrl', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/intermediary/intermediary4.png', '이동봉사를 전문적으로 진행하는 개인, 프리독멍멍입니다.', '인스타그램 @freedog__\n이메일 freedog2008@naver.com', 'AUTH_INTERMEDIARY', false, false, '❗봉사자 필독사항❗\n1. 대형견의 경우 SUV 차량 보유자만 신청해 주시기 바랍니다.\n2. 아이들 절대로 켄넬에서 꺼내지 말아주세요. 만약 문제 발생 시 저에게 바로 전화주세요.\n3. 신중하게 신청해주세요. 봉사 취소시, 아이들에게 엄청나게 큰 피해가 갑니다ㅠㅠ 제발 부탁드려요', now(), now());

INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, guide, created_date, modified_date) VALUES (6, 'i6@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '천사들의 발자국', 'https://connectdog6.site', 'authImageUrl', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/intermediary/intermediary5.png', '아이들의 한 발자국에 최선을', '전화 010-4739-1908', 'AUTH_INTERMEDIARY', false, false, 'AM 10:00 ~ PM 8:00 에만 연락해 주세요\n보호소 사정상 연락 바로 못 볼 수도 있습니다.', now(), now());


-- INSERT DOG (post status 0 - 모집중)
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (1, '보리', 2, 1, 10.0, '켄넬 교육을 완료했습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (2, '로키', 2, 1, 9.0, '로키는 겁이 조금 많은 강아지예요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (3, '백설', 1, 0, 5.0, '백설이는 사람을 좋아합니다. 백설이와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (4, '베티', 2, 1, 4.5, '베티는 잘 짖지 않습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (5, '테디', 1, 1, 6.0, '테디가 조금 아파서 예민할 수 있습니다. 테디가 짖거나 대소변을 가리지 못해도 잘 품어주실 수 있는 봉사자분이셨으면 좋겠습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (6, '루시', 1, 1, 6.5, '루시는 켄넬 밖을 무서워해서 절대로 켄넬 밖으로 임의로 꺼내지 말아주세요', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (7, '벤지', 2, 0, 7.0, null, now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (8, '몽몽이', 2, 0, 9.5, '차에서 노래를 트시면 노래소리에 맞춰서 짖을 수 있어요', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (9, '주디', 1, 1, 6.3, null, now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (10, '하늘이', 2, 0, 11.1, '사람을 무서워하는 강아지입니다', now(), now());


-- INSERT DOG (post status 2 - 진행중)
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (11, '대박이', 2, 1, 10.0, '켄넬 교육을 완료했습니다.', now(), now());


-- INSERT DOG (post status 3 - 봉사완료)
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (12, '여름이', 2, 1, 9.0, '로키는 겁이 조금 많은 강아지에요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (13, '겨울이', 1, 0, 5.0, '겨울이는 사람을 좋아합니다. 백설이와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (14, '짱구', 2, 1, 4.5, '짱구는 잘 짖지 않습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (15, '로지', 1, 1, 6.0, '로지가 조금 아파서 예민할 수 있습니다. 테디가 짖거나 대소변을 가리지 못해도 잘 품어주실 수 있는 봉사자 분이셨으면 좋겠습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (16, '가을이', 1, 1, 5.0, '가을이가 조금 아파서 예민할 수 있습니다. 가을이가 짖거나 대소변을 가리지 못해도 잘 품어주실 수 있는 봉사자 분이셨으면 좋겠습니다.', now(), now());


-- INSERT POST (status 0 - 모집중)
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (1, 0, '서울 성동구', '부산 소래포구', '2023-11-26', '2023-11-26', '09:00', false, '보리와 함께 이동해 주실 분을 찾습니다~~^^ 보리가 드디어 4개월만에 입양처가 결정되어 이동봉사가 필요하게 되었습니다.\n\n서울부터 부산까지 먼 거리지만 보리의 새 가족을 찾기 위해 도와주실 수 있는 분이셨으면 좋겠습니다.', 2, 1, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (2, 0, '서울 성동구', '대전 동구', '2023-11-25', '2023-11-30', '14:00', true, '안녕하세요^^~ 생명사랑의 터줏대감 로키가 감사하게도 입양처로 이동하게 되었습니다. 따뜻한 입양처로 가게 될 로키의 이동을 도와주실 이동봉사자분을 찾습니다.\n\n로키가 겁이 좀 많아도 사람과 친해지면 잘 치대는 우리 로키와 함께 대전으로 이동해 주실 봉사자분을 애타게 찾아봅니다 ㅎㅎ', 2, 2, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (3, 0, '서울 성동구', '서울 강서구', '2023-11-26', '2023-11-27', '16:00', false, '우리 귀여운 백설이 드디어 입양갑니다!! 소중한 입양처로 이동해 주실 이동봉사자분을 찾아요.\n\n백설이는 저희 보호소에서 제일 순하고 사람을 좋아하는 아이로, 처음 이동봉사를 하시는 분이더라도 쉽게 이동봉사를 진행해 보실 수 있을 거예요.', 2, 3, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (4, 0, '서울 잠실구', '경기 고양시', '2023-11-25', '2023-11-27', '19:00', true, '서울부터 고양까지 이동봉사 해주실 분을 찾습니다. 베티가 급하게 이동해야 할 사정이 생겨 이동봉사자를 구하게 되었습니다. 베티는 조용한 강아지로 이동봉사를 할 때 쉽게 진행하실 수 있으실 거예요.', 2, 4, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (5, 0, '서울 잠실구', '경북 구미시', '2023-11-27', '2023-12-01', '15:00', true, '테디가 건강이 악화되어 경북 구미에 있는 병원으로 급하게 이동이 필요한 상황입니다.\n\n최대한 빨리 이동이 필요한 상황으로 테디의 컨디션이 좋지 않아 그 부분을 이해해 주실 수 있는 봉사자 분이셨으면 좋겠습니다.', 2, 5, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (6, 0, '서울 잠실구', '서울 관악구', '2023-11-28', '2023-12-03', '13:00', true, '겁이 많은 우리 루시가 가족을 찾게 되었습니다. 잠실에서 관악까지 짧은 거리지만 겁이 많은 우리 루시가 짖을 수도 있어 루시를 잘 데리고 가주실 이동봉사자분을 구합니다.', 2, 6, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (7, 0, '대전 서구', '대전 동구', '2023-11-26', '2023-11-28', '11:00', true, '벤지가 급하게 병원을 가야합니다. 벤지의 눈 주위 염증이 심해서 급하게 수술을 진행하기 위해 서구에서 동구로 이동이 필요해 빠르게 이동시켜 주실 수 있는 봉사자분을 찾습니다.', 2, 7, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (8, 0, '경기 고양시', '서울 강남구', '2023-11-13', '2023-11-25', '13:00', false, '병원에서 위탁처 이동입니다. 켄넬이 없어 개인 켄넬 준비가 필요합니다.', 3, 8, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (9, 0, '인천 미추홀구', '서울 서초구', '2023-11-29', '2023-11-29', '16:00', true, '주디가 좋은 가족을 찾았습니다!\n\n임보자님이 스케쥴때문에 직접 픽업을 하지 못해 주디가 무사히 가족의 집인 서초구로 이동할 수 있도록 도와주실 수 있는 분을 찾습니다.', 3, 9, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (10, 0, '서울 광진구', '경기 평택시', '2023-12-01', '2023-12-01', '11:00', true, '하늘이가 단기임보처를 구해 단기임보처로의 이동이 필요합니다. 사람보다는 강아지랑 더 친한 하늘이기에 사람하고 더 가까워지기 위한 연습을 위해 임시보호처로 이동합니다~', 3, 10, now(), now());

-- INSERT POST (status 2 - 진행중)
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (11, 2, '서울 서대문구', '서울 동대문구', '2023-12-12', '2023-12-12', '09:00', true, '우리 귀여운 대박이가 드디어 새로운 가족을 찾아서 떠납니다! 대박이와의 이동을 도와주실 분을 찾습니다!', 2, 11, now(), now());

-- INSERT POST (status 3 - 봉사완료)
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (12, 3, '서울 성북구', '경기 성남시', '2023-02-21', '2023-03-21', '16:00', true, '안녕하세요^^~ 생명사랑의 터줏대감 여름이가 감사하게도 입양처로 이동하게 되었습니다. 따뜻한 입양처로 가게 될 여름이의 이동을 도와주실 이동봉사자분을 찾습니다.\n\n여름이가 겁이 좀 많아도 사람과 친해지면 잘 치대는 우리 여름이와 함께 성남으로 이동해주실 봉사자분을 애타게 찾아봅니다 ㅎㅎ', 2, 12, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (13, 3, '서울 중랑구', '서울 노원구', '2023-04-29', '2023-04-29', '17:00', false, '우리 귀여운 겨울이 드디어 입양갑니다!! 소중한 입양처로 이동해 주실 이동봉사자 분을 찾아요.\n\n겨울이는 저희 보호소에서 제일 순하고 사람을 좋아하는 아이로, 처음 이동봉사를 하시는 분이더라도 쉽게 이동봉사를 진행해 보실 수 있을거예요. ', 2, 13, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (14, 3, '경기 용인시', '경기 수원시', '2023-12-01', '2023-12-01', '11:00', true, '용인부터 수원까지 이동봉사 해주실 분을 찾습니다. 짱구가 급하게 이동해야 할 사정이 생겨 이동봉사자를 구하게 되었습니다. 짱구는 조용한 강아지로 이동봉사를 할 때 쉽게 진행하실 수 있으실 거예요.', 2, 14, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (15, 3, '경기 화성시', '서울 잠실구', '2023-10-14', '2023-10-14', '10:00', true, '로지가 건강이 악화되어 서울 잠실에 있는 병원으로 급하게 이동이 필요한 상황입니다. 최대한 빨리 이동이 필요한 상황으로 로지의 컨디션이 좋지 않아 그 부분을 이해해 주실 수 있는 봉사자 분이셨으면 좋겠습니다.', 2, 15, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (16, 3, '경기 화성시', '서울 잠실구', '2023-10-14', '2023-10-14', '10:00', true, '가을이가 이동봉사 해주실 분을 찾습니다. 최대한 빨리 이동이 필요한 상황으로 로지의 컨디션이 좋지 않아 그 부분을 이해해 주실 수 있는 봉사자 분이셨으면 좋겠습니다.', 3, 16, now(), now());


-- INSERT POST_IMAGE
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (1, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post1.png', 1, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (2, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post2.png', 2, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (3, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post3.png', 3, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (4, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post4.png', 4, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (5, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post5.png', 5, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (6, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post6.png', 6, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (7, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post7.png', 7, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (8, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post8.png', 8, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (9, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post9.png', 9, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (10, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post10.png', 10, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (11, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post11.png', 11, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (12, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post12.png', 12, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (13, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post13.png', 13, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (14, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post14.png', 14, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (15, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post15.png', 15, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (16, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post16.png', 16, now(), now());


-- UPDATE POST MAIN IMAGE
UPDATE post SET main_image_id = 1 WHERE id = 1;
UPDATE post SET main_image_id = 2 WHERE id = 2;
UPDATE post SET main_image_id = 3 WHERE id = 3;
UPDATE post SET main_image_id = 4 WHERE id = 4;
UPDATE post SET main_image_id = 5 WHERE id = 5;
UPDATE post SET main_image_id = 6 WHERE id = 6;
UPDATE post SET main_image_id = 7 WHERE id = 7;
UPDATE post SET main_image_id = 8 WHERE id = 8;
UPDATE post SET main_image_id = 9 WHERE id = 9;
UPDATE post SET main_image_id = 10 WHERE id = 10;
UPDATE post SET main_image_id = 11 WHERE id = 11;
UPDATE post SET main_image_id = 12 WHERE id = 12;
UPDATE post SET main_image_id = 13 WHERE id = 13;
UPDATE post SET main_image_id = 14 WHERE id = 14;
UPDATE post SET main_image_id = 15 WHERE id = 15;
UPDATE post SET main_image_id = 16 WHERE id = 16;

-- INSERT VOLUNTEER
INSERT INTO volunteer (id, email, password, nickname, profile_image_num, name, phone, role, is_option_agr, notification, created_date, modified_date) VALUES (1, 'abc@naver.com', '{bcrypt}$2a$10$VieltvcRaI/rJnaRHuRPju9rqM9BvmKRkmn./oOninx7fOGT/q2De', '이동봉사자', 2, '한호정', '01047391908', 'AUTH_VOLUNTEER', false, false, now(), now());

INSERT INTO volunteer (id, email, password, nickname, profile_image_num, name, phone, role, is_option_agr, notification, created_date, modified_date) VALUES (2, 'v1@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '하얀마음', 1, '민경혁', '01047391908', 'AUTH_VOLUNTEER', false, false, now(), now());


-- INSERT APPLICATION (status 2 - 봉사완료)
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (1, 1, '민경혁', '01047391908', 'BMW1', '이동봉사 신청합니다1!', 11, 2, 2, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (2, 2, '민경혁', '01047391908', 'BMW1', '이동봉사 신청합니다2!', 12, 2, 2, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (3, 2, '민경혁', '01047391908', 'BMW2', '이동봉사 신청합니다3!', 13, 2, 2, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (4, 2, '민경혁', '01047391908', 'BMW3', '이동봉사 신청합니다4!', 14, 2, 2, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (5, 2, '민경혁', '01047391908', 'BMW4', '이동봉사 신청합니다5!', 15, 2, 2, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (6, 2, '한호정', '01047391908', 'BMW5', '이동봉사 신청합니다6!', 16, 3, 1, now(), now());

-- INSERT REVIEW
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (1, '여름이의 새 삶의 첫 걸음을 함께 했어요!! 첫 봉사라 너무 떨렸는데 다행이도 여름이가 잘 따라와줬습니다\n천구름 관계자 분들도 잘 설명해 주셨어요! 다음에 또 봉사할게요~~', 12, 2, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (2, '겨울이의 마지막까지 함께할 수 있어서 너무 좋았어요 우리 겨울이 잘 살아라(누나 잊지 말고!!)', 13, 2, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (3, '출장 가는 길에 보호소 이동을 해야 하는 아이가 있다고 해서 봉사를 신청하게 되었어요! 기존 후기들처럼 엄청 친절하십니다! 초보 이동봉사자 분들도 충분히 봉사 진행할 수 있었습니다! 추천해요!', 14, 2, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (4, '로지의 병원 긴급 이동봉사 공고를 보고 신청하게 되었어요...! 아픈 몸으로 장거리 이동 버티기 힘들었을텐데 버텨줘서 너무 고맙고 꼭 다 나아서 좋은 삶 살았으면 좋겠습니다 화이팅.', 15, 2, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (5, '이동봉사 처음 해봤는데 다시 하고 싶어요. 가을이가 꼭 다 나아서 좋은 삶 살았으면 좋겠습니다! 만나서 반가웠어 잘 지내길 바랄게 :)', 16, 1, now(), now());


-- INSERT REVIEW_IMAGE
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (1, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review12.png', 1, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (2, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review13.png', 2, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (3, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review14.png', 3, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (4, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review15.png', 4, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (5, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review16.png', 5, now(), now());


-- UPDATE REVIEW MAIN IMAGE
UPDATE review SET main_image_id = 1 WHERE id = 1;
UPDATE review SET main_image_id = 2 WHERE id = 2;
UPDATE review SET main_image_id = 3 WHERE id = 3;
UPDATE review SET main_image_id = 4 WHERE id = 4;
UPDATE review SET main_image_id = 5 WHERE id = 5;


-- INSERT DOG_STATUS
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (1, '우리 여름이 집 가서 활짝 웃고있는 거 보세요! 귀여운 여름이와 함께 이동봉사 진행해 주셔서 감사합니다 ^^', 12, 2, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (2, '겨울이의 점점 살과 털이 찌고있어요 ㅎㅎ 겨울이는 잘 먹고 잘 지낸답니다. ', 13, 2, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (3, '짱구는 모래 운동장에서 노는 걸 요즘 즐겨서 흰 털이 노래질 때까지 놀고있대요^^~', 14, 2, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (4, '로지는 이제 사람들 손을 무서워하지 않고 사람들과 함께 잘 노는 강아지가 되어가고 있답니다', 15, 2, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (5, '가을이는 다 나아서 잘 지내고 있답니다 :)', 16, 3, now(), now());


-- INSERT DOG_STATUS_IMAGE
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (1, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus1.png', 1, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (2, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus2.png', 2, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (3, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus3.png', 3, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (4, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus4.png', 4, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (5, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus5.png', 5, now(), now());


-- UPDATE DOG_STATUS_IMAGE
UPDATE dog_status SET main_image_id = 1 WHERE id = 1;
UPDATE dog_status SET main_image_id = 2 WHERE id = 2;
UPDATE dog_status SET main_image_id = 3 WHERE id = 3;
UPDATE dog_status SET main_image_id = 4 WHERE id = 4;
UPDATE dog_status SET main_image_id = 5 WHERE id = 5;


-- INSERT BADGE
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (1, '첫 연결의 설렘\n1마리 연결', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge11.png', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (2, '초심자의 열정\n3마리 연결', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge2.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (3, '소중함을 아니까\n5마리 연결', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge3.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (4, '신뢰의 시작\n10마리 연결', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge4.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (5, '열정 봉사자\n20마리 연결', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge5.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (6, '이동봉사 마스터\n30마리 연결', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge6.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (7, '첫 기록의 뿌듯함\n1개 등록', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge17.png', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (8, '제법 쓸 줄 알아요\n3개 등록', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge8.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (9, '봉사 꿀팁 보유자\n5개 등록', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge9.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (10, '믿음직한 후기\n10개 등록', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge0.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (11, '성실 기록가\n20개 등록', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge0.svg', now(), now());
INSERT INTO badge (id, name, image, created_date, modified_date) VALUES (12, '코넥독 후기왕\n30개 등록', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/badge/badge0.svg', now(), now());


-- INSERT VOLUNTEER_BADGE
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (1, 1, 2, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (2, 2, 2, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (3, 7, 2, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (4, 8, 2, now(), now());

-- INSERT BOOKMARK
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (1, 6, 2, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (2, 7, 2, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (3, 8, 2, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (4, 9, 2, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (5, 10, 2, now(), now());


-- v2
-- INSERT INTERMEDIARY
INSERT INTO intermediary (id, email, password, name, url, auth_image, profile_image, intro, contact, role, is_option_agr, notification, guide, created_date, modified_date) VALUES (7, 'i7@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '감귤시보호소', 'https://connectdog7.site', 'authImageUrl', 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/intermediary/intermediary6.png','감귤시가 운영중인 유기견 보호소입니다', '인스타그램 @gamgull_helper', 'AUTH_INTERMEDIARY', false, false, '감귤시에서 운영하는 감귤시보호소 공식계정입니다. 직원들이 상주하는 평일 9:00~19:0에만 응답이 가능합니다. ', now(), now());


-- INSERT DOG (post status 0 - 모집중)
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (17, '비키', 1, 1, 6.3, '애착 인형과 함께 이동해야 해요', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (18, '푸딩이', 2, 0, 11.1, '푸딩이는 사람을 좋아합니다. 슬기와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (19, '포포', 2, 1, 10.0, '포포와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (20, '꿈돌이', 2, 1, 9.0, '켄넬을 조금 무서워해서 켄넬을 긁을 수 있어요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (21, '별님', 1, 0, 5.0, '침을 조금 많이 흘려요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (22, '햇님', 2, 1, 4.5, '햇님이는 사람을 좋아합니다. 햇님이와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (23, '달님', 1, 1, 6.0, '달님이는 사람을 좋아합니다. 달님이와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (24, '태양이', 2, 1, 10.0, '태양이는 사람을 좋아합니다. 태양이와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (25, '바다', 2, 1, 9.0, '시끄러우면 짖을 수 있어요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (26, '윤슬', 1, 0, 5.0, '자동차를 보면 따라가요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (27, '슬기', 2, 1, 4.5, '슬기는 사람을 좋아합니다. 슬기와 함께 가는 길이 즐거우실 거예요~^^', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (28, '슬구', 1, 1, 6.0, '슬구는 겁이 조금 많은 강아지예요.', now(), now());

-- INSERT DOG (post status 1 - 승인대기중)
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (29, '잔디', 1, 1, 6.5, '산책을 좋아하지만 이동봉사 중에는 시키지 마세요', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (30, '잔디', 1, 1, 6.5, '산책을 좋아하지만 이동봉사 중에는 시키지 마세요', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (31, '잔디', 1, 1, 6.5, '산책을 좋아하지만 이동봉사 중에는 시키지 마세요', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (32, '잔디', 1, 1, 6.5, '산책을 좋아하지만 이동봉사 중에는 시키지 마세요', now(), now());

-- INSERT DOG (post status 2 - 진행중)
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (33, '동글이', 2, 0, 7.0, '켄넬 교육을 완료했습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (34, '동글이', 2, 0, 7.0, '켄넬 교육을 완료했습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (35, '동글이', 2, 0, 7.0, '켄넬 교육을 완료했습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (36, '동글이', 2, 0, 7.0, '켄넬 교육을 완료했습니다.', now(), now());


-- INSERT DOG (post status 3 - 봉사완료)
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (37, '몽이', 2, 1, 10.0, null, now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (38, '코코', 2, 1, 9.0, '겁이 조금 많지만 순해요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (39, '율무', 1, 0, 5.0, '켄넬 교육을 완료했습니다.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (40, '찹쌀이', 2, 1, 4.5, null, now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (41, '수수', 1, 1, 6.0, null, now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (42, '호두', 1, 1, 6.5, null, now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (43, '감자', 2, 0, 7.0, '사람들을 좋아해서 순해요.', now(), now());
INSERT INTO dog (id, name, size, gender, weight, specifics, created_date, modified_date) VALUES (44, '베키', 2, 0, 9.5, '조금 심하게 짖을 수 있어요.', now(), now());



-- INSERT POST (status 0 - 모집중)
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (17, 0, '서울 중량구', '서울 서초구', '2023-12-19', '2023-12-19', '10:30', true, '비키가 좋은 가족을 찾았습니다!\n\n임보자님이 스케쥴 때문에 직접 픽업을 하지 못해 비키가 무사히 가족의 집인 서초구로 이동할 수 있도록 도와주실 수 있는 분을 찾습니다.', 4, 17, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (18, 0, '서울 중구', '서울 노원구', '2023-11-29', '2023-11-29', '10:00', true, '푸딩이가 단기 임보처를 구해 단기 임보처로의 이동이 필요합니다. 사람보다는 강아지랑 더 친한 푸딩이기에 사람하고 더 가까워지기 위한 연습을 위해 임시보호처로 이동합니다~', 4, 18, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (19, 0, '경기 화성시', '경기 안산시', '2023-12-24', '2023-12-24', '08:00', true, '우리 귀여운 포포가 드디어 새로운 가족을 찾아서 떠납니다! 포포의 이동을 도와주실 분을 찾습니다!', 4, 19, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (20, 0, '인천 연수구', '인천 미추홀구', '2023-12-31', '2023-12-31', '06:00', true, '안녕하세요^^~ 꿈돌이가 감사하게도 입양처로 이동하게 되었습니다. 따뜻한 입양처로 가게 될 꿈돌이의 이동을 도와주실 이동봉사자분을 찾습니다.\n\n꿈돌이가 겁이 좀 많아도 사람과 친해지면 잘 치대는 우리 꿈돌이와 함께 미추홀구으로 이동해 주실 봉사자분을 애타게 찾아봅니다 ㅎㅎ', 5, 20, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (21, 0, '서울 은평구', '경기 의정부시', '2024-01-24', '2024-01-24', '14:00', false, '우리 귀여운 별님이가 드디어 입양갑니다!! 소중한 입양처로 이동해 주실 이동봉사자 분을 찾아요.\n\n별님이는 저희 보호소에서 제일 순하고 사람을 좋아하는 아이로, 처음 이동봉사를 하시는 분이더라도 쉽게 이동봉사를 진행해 보실 수 있을 거예요.', 5, 21, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (22, 0, '서울 잠실구', '경기 양주시', '2024-01-03', '2024-01-03', '11:00', true, '서울 잠실구부터 경기 양주까지 먼 길이지만 이동봉사 해주실 분을 찾습니다. 햇님이가 급하게 이동해야 할 사정이 생겨 이동봉사자를 구하게 되었습니다. 햇님이는 조용한 강아지로 이동봉사를 할 때 쉽게 진행하실 수 있으실 거예요.', 5, 22, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (23, 0, '경기 화성시', '서울 잠실구', '2024-01-12', '2024-01-12', '10:00', true, '달님이가 건강이 악화되어 서울 잠실에 있는 병원으로 급하게 이동이 필요한 상황입니다.\n\n최대한 빨리 이동이 필요한 상황으로 달님이의 컨디션이 좋지 않아 그 부분을 이해해 주실 수 있는 봉사자분이셨으면 좋겠습니다.', 6, 23, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (24, 0, '경기 수원시', '경기 용인시', '2024-01-14', '2024-01-14', '11:00', false, '병원에서 위탁처 이동입니다. 켄넬이 없어 개인 켄넬 준비가 필요합니다.', 6, 24, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (25, 0, '서울 서대문구', '경기 평택시', '2023-11-29', '2023-11-29', '09:00', false, '안녕하세요^^~ 따뜻한 입양처로 가게 될 바다의 이동을 도와주실 이동봉사자분을 찾습니다.\n\n바다가 겁이 좀 많아도 사람과 친해지면 잘 치대는 우리 바다와 함께 평택시로 이동해 주실 봉사자분을 애타게 찾아봅니다 ㅎㅎ', 6, 25, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (26, 0, '서울 강남구', '서울 서초구', '2023-11-30', '2023-11-30', '06:00', false, '우리 귀여운 윤슬이 드디어 입양갑니다!! 소중한 입양처로 이동해 주실 이동봉사자 분을 찾아요. \n\n윤슬이는 저희 보호소에서 제일 순하고 사람을 좋아하는 아이로, 처음 이동봉사를 하시는 분이더라도 쉽게 이동봉사를 진행해보실 수 있을 거예요.', 7, 26, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (27, 0, '서울 동대문구', '경기 파주시', '2024-01-23', '2024-01-23', '11:00', true, '용인에서 성남까지 이동봉사 해주실 분을 찾습니다. 슬기가 급하게 이동해야 할 사정이 생겨 이동봉사자를 구하게 되었습니다. 슬기는 조용한 강아지로 이동봉사를 할 때 쉽게 진행하실 수 있으실 거예요.', 7, 27, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (28, 0, '서울 성동구', '부산 소래포구', '2023-11-26', '2023-11-26', '09:00', false, '슬구가 건강이 악화되어 파주에 있는 병원으로 급하게 이동이 필요한 상황입니다.\n\n최대한 빨리 이동이 필요한 상황으로 슬구의 컨디션이 좋지 않아 그 부분을 이해해 주실 수 있는 봉사자분이셨으면 좋겠습니다.', 7, 28, now(), now());


-- INSERT POST (status 1 - 승인대기중)
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (29, 1, '경기 오산시', '경기 화성시', '2023-11-28', '2023-11-28', '10:30', true, '겁이 많은 우리 잔디가 가족을 찾게 되었습니다. 오산부터 화성까지 짧은 거리지만 겁이 많은 우리 잔디가 짖을 수도 있어 잔디를 잘 데리고 가주실 이동봉사자 분을 구합니다.', 4, 29, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (30, 1, '경기 오산시', '경기 화성시', '2023-11-28', '2023-11-28', '10:30', true, '겁이 많은 우리 잔디가 가족을 찾게 되었습니다. 오산부터 화성까지 짧은 거리지만 겁이 많은 우리 잔디가 짖을 수도 있어 잔디를 잘 데리고 가주실 이동봉사자 분을 구합니다.', 5, 30, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (31, 1, '경기 오산시', '경기 화성시', '2023-11-28', '2023-11-28', '10:30', true, '겁이 많은 우리 잔디가 가족을 찾게 되었습니다. 오산부터 화성까지 짧은 거리지만 겁이 많은 우리 잔디가 짖을 수도 있어 잔디를 잘 데리고 가주실 이동봉사자 분을 구합니다.', 6, 31, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date) VALUES (32, 1, '경기 오산시', '경기 화성시', '2023-11-28', '2023-11-28', '10:30', true, '겁이 많은 우리 잔디가 가족을 찾게 되었습니다. 오산부터 화성까지 짧은 거리지만 겁이 많은 우리 잔디가 짖을 수도 있어 잔디를 잘 데리고 가주실 이동봉사자 분을 구합니다.', 7, 32, now(), now());


-- INSERT POST (status 2 - 진행중)
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (33, 2, '서울 도봉구', ' 서울 노원구', '2023-11-26', '2023-11-26', '10:40', true, '동글이가 급하게 병원을 가야합니다. 동글이의 눈 주위 염증이 심해서 급하게 수술을 진행하기 위해 도봉구에서 노원구로 이동이 필요해 빠르게 이동시켜주실 수 있는 봉사자분을 찾습니다.',4, 33, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (34, 2, '서울 도봉구', ' 서울 노원구', '2023-11-26', '2023-11-26', '10:40', true, '동글이가 급하게 병원을 가야합니다. 동글이의 눈 주위 염증이 심해서 급하게 수술을 진행하기 위해 도봉구에서 노원구로 이동이 필요해 빠르게 이동시켜주실 수 있는 봉사자분을 찾습니다.',5, 34, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (35, 2, '서울 도봉구', ' 서울 노원구', '2023-11-26', '2023-11-26', '10:40', true, '동글이가 급하게 병원을 가야합니다. 동글이의 눈 주위 염증이 심해서 급하게 수술을 진행하기 위해 도봉구에서 노원구로 이동이 필요해 빠르게 이동시켜주실 수 있는 봉사자분을 찾습니다.',6, 35, now(), now());
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (36, 2, '서울 도봉구', ' 서울 노원구', '2023-11-26', '2023-11-26', '10:40', true, '동글이가 급하게 병원을 가야합니다. 동글이의 눈 주위 염증이 심해서 급하게 수술을 진행하기 위해 도봉구에서 노원구로 이동이 필요해 빠르게 이동시켜주실 수 있는 봉사자분을 찾습니다.',7, 36, now(), now());


-- INSERT POST (status 3 - 봉사완료)
INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (37, 3, '경기 수원시', '경기 용인시', '2023-10-25', '2023-10-25', '11:00', true, '몽이와 함께 이동해 주실 분을 찾습니다~~^^ 몽이가 드디어 4개월만에 입양처가 결정되어 이동봉사가 필요하게 되었습니다.\n\n수원에서 용인까지 몽이의 새 가족을 찾기 위해 도와주실 수 있는 분이셨으면 좋겠습니다.',4, 37, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (38, 3, '서울 서대문구', '경기 평택시', '2023-04-14', '2023-04-14', '16:00', true, '코코가 겁이 좀 많아도 사람과 친해지면 잘 치대는 우리 먕이와 함께 평택으로 이동해주실 봉사자분을 애타게 찾아봅니다 ㅎㅎ',4, 38, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (39, 3, '서울 강남구', '서울 서초구', '2023-05-17', '2023-05-17', '12:00', true, '우리 귀여운 율무가 드디어 입양갑니다!! 소중한 입양처로 이동해 주실 이동봉사자 분을 찾아요. \n\n율무는 저희 보호소에서 제일 순하고 사람을 좋아하는 아이로, 처음 이동봉사를 하시는 분이더라도 쉽게 이동봉사를 진행해 보실 수 있을 거예요.',5, 39, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (40, 3, '경기 용인시', '경기 성남시', '2023-09-09', '2023-09-09', '14:00', true, '용인부터 성남까지 이동봉사 해주실 분을 찾습니다. 찹쌀이가 급하게 이동해야 할 사정이 생겨 이동봉사자를 구하게 되었습니다. 찹쌀이는 조용한 강아지로 이동봉사를 할 때 쉽게 진행하실 수 있을 거예요.',5, 40, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (41, 3, '서울 동대문구', '경기 파주시', '2023-07-08', '2023-07-08', '16:00', true, '수수가 건강이 악화되어 경기 파주에 있는 병원으로 급하게 이동이 필요한 상황입니다.\n\n최대한 빨리 이동이 필요한 상황으로 수수의 컨디션이 좋지 않아 그 부분을 이해해주실 수 있는 봉사자분이셨으면 좋겠습니다.',6, 41, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (42, 3, '경기 오산시', '경기 화성시', '2023-09-24', '2023-09-24', '16:00', true, '겁이 많은 우리 호두가 가족을 찾게 되었습니다. 오산에서 화성까지 짧은 거리지만 겁이 많은 우리 호두가 짖을 수도 있어 호두를 잘 데리고 가주실 이동봉사자 분을 구합니다.',6, 42, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (43, 3, '경기 양주시', '경기 의정부시', '2023-11-01', '2023-11-01', '18:00', false, '감자가 급하게 병원을 가야합니다. 감자의 눈 주위 염증이 심해서 급하게 수술을 진행하기 위해 양주시에서 의정부시로 이동이 필요해 빠르게 이동시켜주실 수 있는 봉사자분을 찾습니다.',7, 43, now(), now());

INSERT INTO post (id, status, departure_loc, arrival_loc, start_date, end_date, pick_up_time, is_kennel, content, intermediary_id, dog_id, created_date, modified_date)VALUES (44, 3, '경기 안산시', '서울 강남구', '2023-11-20', '2023-11-20', '11:00', false, '병원에서 위탁처 이동입니다. 켄넬이 없어 개인 켄넬 준비가 필요합니다.',7, 44, now(), now());


-- INSERT POST_IMAGE
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (17, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post17.png', 17, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (18, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post18.png', 18, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (19, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post19.png', 19, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (20, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post20.png', 20, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (21, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post21.png', 21, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (22, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post22.png', 22, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (23, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post23.png', 23, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (24, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post24.png', 24, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (25, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post25.png', 25, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (26, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post26.png', 26, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (27, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post27.png', 27, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (28, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post28.png', 28, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (29, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post29.png', 29, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (30, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post29.png', 30, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (31, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post29.png', 31, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (32, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post29.png', 32, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (33, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post30.png', 33, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (34, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post30.png', 34, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (35, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post30.png', 35, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (36, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post30.png', 36, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (37, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post31.png', 37, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (38, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post32.png', 38, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (39, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post33.png', 39, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (40, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post34.png', 40, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (41, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post35.png', 41, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (42, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post36.png', 42, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (43, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post37.png', 43, now(), now());
INSERT INTO post_image (id, image, post_id, created_date, modified_date) VALUES (44, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/post/post38.png', 44, now(), now());

-- UPDATE POST MAIN IMAGE
UPDATE post SET main_image_id = 17 WHERE id = 17;
UPDATE post SET main_image_id = 18 WHERE id = 18;
UPDATE post SET main_image_id = 19 WHERE id = 19;
UPDATE post SET main_image_id = 20 WHERE id = 20;
UPDATE post SET main_image_id = 21 WHERE id = 21;
UPDATE post SET main_image_id = 22 WHERE id = 22;
UPDATE post SET main_image_id = 23 WHERE id = 23;
UPDATE post SET main_image_id = 24 WHERE id = 24;
UPDATE post SET main_image_id = 25 WHERE id = 25;
UPDATE post SET main_image_id = 26 WHERE id = 26;
UPDATE post SET main_image_id = 27 WHERE id = 27;
UPDATE post SET main_image_id = 28 WHERE id = 28;
UPDATE post SET main_image_id = 29 WHERE id = 29;
UPDATE post SET main_image_id = 30 WHERE id = 30;
UPDATE post SET main_image_id = 31 WHERE id = 31;
UPDATE post SET main_image_id = 32 WHERE id = 32;
UPDATE post SET main_image_id = 33 WHERE id = 33;
UPDATE post SET main_image_id = 34 WHERE id = 34;
UPDATE post SET main_image_id = 35 WHERE id = 35;
UPDATE post SET main_image_id = 36 WHERE id = 36;
UPDATE post SET main_image_id = 37 WHERE id = 37;
UPDATE post SET main_image_id = 38 WHERE id = 38;
UPDATE post SET main_image_id = 39 WHERE id = 39;
UPDATE post SET main_image_id = 40 WHERE id = 40;
UPDATE post SET main_image_id = 41 WHERE id = 41;
UPDATE post SET main_image_id = 42 WHERE id = 42;
UPDATE post SET main_image_id = 43 WHERE id = 43;
UPDATE post SET main_image_id = 44 WHERE id = 44;


-- INSERT VOLUNTEER
INSERT INTO volunteer (id, email, password, nickname, profile_image_num, name, phone, role, is_option_agr, notification, created_date, modified_date) VALUES (3, 'v2@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '포포사랑', 3, '이시윤', '01047391908', 'AUTH_VOLUNTEER', false, false, now(), now());
INSERT INTO volunteer (id, email, password, nickname, profile_image_num, name, phone, role, is_option_agr, notification, created_date, modified_date) VALUES (4, 'v3@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '하늘천사', 4, '권예인', '01047391908', 'AUTH_VOLUNTEER', false, false, now(), now());
INSERT INTO volunteer (id, email, password, nickname, profile_image_num, name, phone, role, is_option_agr, notification, created_date, modified_date) VALUES (5, 'v4@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '코코엄마', 5, '강승구', '01047391908', 'AUTH_VOLUNTEER', false, false, now(), now());
INSERT INTO volunteer (id, email, password, nickname, profile_image_num, name, phone, role, is_option_agr, notification, created_date, modified_date) VALUES (6, 'v5@naver.com', '{bcrypt}$2a$10$wkmYUG/qvZFThCzq19yHredRc6u8nAhlAopbDE9p7n6JF6NgtLs8y', '산책가자', 6, '김민주', '01047391908', 'AUTH_VOLUNTEER', false, false, now(), now());


-- INSERT APPLICATION (status 0 - 승인대기중)
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (7, 0, '이시윤', '01047391908', 'BMW6', '이동봉사 신청합니다!', 29, 4, 3, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (8, 0, '권예인', '01047391908', 'BMW7', '이동봉사 신청합니다!', 30, 5, 4, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (9, 0, '강승구', '01047391908', 'BMW8', '이동봉사 신청합니다!', 31, 6, 5, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (10, 0, '김민주', '01047391908', 'BMW9', '이동봉사 신청합니다!', 32, 7, 6, now(), now());

-- INSERT APPLICATION (status 1 - 진행중)
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (11, 1, '이시윤', '01047391908', 'BMW11', '이동봉사 신청합니다!', 33, 4, 3, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (12, 1, '권예인', '01047391908', 'BMW12', '이동봉사 신청합니다!', 34, 5, 4, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (13, 1, '강승구', '01047391908', 'BMW13', '이동봉사 신청합니다!', 35, 6, 5, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (14, 1, '김민주', '01047391908', 'BMW14', '이동봉사 신청합니다!', 36, 7, 6, now(), now());


-- INSERT APPLICATION (status 2 - 봉사완료)
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (15, 2, '이시윤', '01047391908', 'BMW15', '이동봉사 신청합니다!', 37, 4, 3, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (16, 2, '이시윤', '01047391908', 'BMW16', '이동봉사 신청합니다!', 38, 4, 3, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (17, 2, '권예인', '01047391908', 'BMW17', '이동봉사 신청합니다!', 39, 5, 4, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (18, 2, '권예인', '01047391908', 'BMW18', '이동봉사 신청합니다!', 40, 5, 4, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (19, 2, '강승구', '01047391908', 'BMW19', '이동봉사 신청합니다!', 41, 6, 5, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (20, 2, '강승구', '01047391908', 'BMW20', '이동봉사 신청합니다!', 42, 6, 5, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (21, 2, '김민주', '01047391908', 'BMW21', '이동봉사 신청합니다!', 43, 7, 6, now(), now());
INSERT INTO application (id, status, volunteer_name, phone, transportation, content, post_id, intermediary_id, volunteer_id, created_date, modified_date) VALUES (22, 2, '김민주', '01047391908', 'BMW22', '이동봉사 신청합니다!', 44, 7, 6, now(), now());


-- INSERT REVIEW
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (6, '너무 귀여운 강아지와 함께 한 이동봉사!! 인생에서 제일 잘 한 선택이였어요. 꼭 다음에도 만나고 거기서는 건강하게 지내야해 사랑해~~!!!', 37, 3, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (7, '너무 귀엽고 사랑스러워요 코코야 또 만나', 38, 3, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (8, '율무야 거기서는 짖지 말고 개껌과 행복하게 살아야해~~~~', 39, 4, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (9, '첫 이동봉사였던 찹쌀이와의 이동은 행복 그 자체였어요 ', 40, 4, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (10, '수수와 함께 첫 이동봉사를 진행했는데 바로 다음주에 또 이동봉사를 잡았어요! 수수야 너 덕에 이동봉사의 재미를 알게 된 거 같아 병원 가서 잘 나았으면 좋겠다', 41, 5, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (11, '호두야 정말 고양이같은 강아지 호두야 거기서는 잘 놀아야해!!!', 42, 5, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (12, '감자는 고구마를 좋아하는데 가는 길 내내 고구마를 잘 먹고 조용히 갔어요. 수술이 잘 끝나면 좋겠네요', 43, 6, now(), now());
INSERT INTO review (id, content, post_id, volunteer_id, created_date, modified_date) VALUES (13, '베키가 낯을 가려 짖기도 했지만 베키가 사람과 친해지니 금방 순해져서 이동봉사를 진행하기 너무 편했어요', 44, 6, now(), now());


-- INSERT REVIEW_IMAGE
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (6, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review17.png', 6, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (7, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review18.png', 7, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (8, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review19.png', 8, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (9, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review20.png', 9, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (10, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review21.png', 10, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (11, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review22.png', 11, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (12, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review23.png', 12, now(), now());
INSERT INTO review_image (id, image, review_id, created_date, modified_date) VALUES (13, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/review/review24.png', 13, now(), now());


-- UPDATE REVIEW MAIN IMAGE
UPDATE review SET main_image_id = 6 WHERE id = 6;
UPDATE review SET main_image_id = 7 WHERE id = 7;
UPDATE review SET main_image_id = 8 WHERE id = 8;
UPDATE review SET main_image_id = 9 WHERE id = 9;
UPDATE review SET main_image_id = 10 WHERE id = 10;
UPDATE review SET main_image_id = 11 WHERE id = 11;
UPDATE review SET main_image_id = 12 WHERE id = 12;
UPDATE review SET main_image_id = 13 WHERE id = 13;


-- INSERT DOG_STATUS
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (6, '몽이는 새로운 집에서 잘 놀구 있어요!! 너무 귀엽지 않나요..?', 37, 4, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (7, '요즘 1일 1산책 한대요!', 38, 4, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (8, '율무는 새로운 집에서 친구와 잘 지내고 있대요.', 39, 5, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (9, '찹쌀이는 이동 후에도 잘 지내고 있대요.', 40, 5, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (10, '수수의 건강이 많이 좋아져서 다시 건강하게 산책하며 돌아다니고 있어요.', 41, 6, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (11, '루시는 새 가족을 만나고 겁이 많이 없어졌대요. 이제는 먼저 사람을 보면 다가가서 부빈답니다', 42, 6, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (12, '감자는 병원 다녀와서 몸이 많이 좋아졌어요', 43, 7, now(), now());
INSERT INTO dog_status (id, content, post_id, intermediary_id, created_date, modified_date) VALUES (13, '베키가 새로운 가족과 잘 지내고있대요!', 44, 7, now(), now());


-- INSERT DOG_STATUS_IMAGE
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (6, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus6.png', 6, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (7, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus7.png', 7, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (8, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus8.png', 8, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (9, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus9.png', 9, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (10, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus10.png', 10, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (11, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus11.png', 11, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (12, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus12.png', 12, now(), now());
INSERT INTO dog_status_image (id, image, dog_status_id, created_date, modified_date) VALUES (13, 'https://connectdog-image.s3.ap-northeast-2.amazonaws.com/dogStatus/dogStatus13.png', 13, now(), now());


-- UPDATE DOG_STATUS_IMAGE
UPDATE dog_status SET main_image_id = 6 WHERE id = 6;
UPDATE dog_status SET main_image_id = 7 WHERE id = 7;
UPDATE dog_status SET main_image_id = 8 WHERE id = 8;
UPDATE dog_status SET main_image_id = 9 WHERE id = 9;
UPDATE dog_status SET main_image_id = 10 WHERE id = 10;
UPDATE dog_status SET main_image_id = 11 WHERE id = 11;
UPDATE dog_status SET main_image_id = 12 WHERE id = 12;
UPDATE dog_status SET main_image_id = 13 WHERE id = 13;


-- INSERT VOLUNTEER_BADGE
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (5, 1, 3, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (6, 7, 3, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (7, 1, 4, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (8, 7, 4, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (9, 1, 5, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (10, 7, 5, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (11, 1, 6, now(), now());
INSERT INTO volunteer_badge(id, badge_id, volunteer_id, created_date, modified_date) VALUES (12, 7, 6, now(), now());

-- INSERT BOOKMARK
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (6, 6, 3, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (7, 7, 3, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (8, 8, 3, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (9, 9, 3, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (10, 10, 3, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (11, 6, 4, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (12, 7, 4, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (13, 8, 4, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (14, 9, 4, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (15, 10, 4, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (16, 6, 5, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (17, 7, 5, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (18, 8, 5, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (19, 9, 5, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (20, 10, 5, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (21, 6, 6, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (22, 7, 6, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (23, 8, 6, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (24, 9, 6, now(), now());
INSERT INTO bookmark(id, post_id, volunteer_id, created_date, modified_date) VALUES (25, 10, 6, now(), now());