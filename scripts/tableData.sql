--------------------------------------------------------
--  File created - Tuesday-June-07-2016   
--------------------------------------------------------
REM INSERTING into TEST.AUTHORS
SET DEFINE OFF;
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (1,'John Smith',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (2,'Noname noname',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (3,'John Silver',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (4,'Jane Doe',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (5,'User user',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (6,'Ran dom guy',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (7,'Bran Tarth',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (8,'Eilin O Connoer',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (9,'Tara Black',null);
Insert into TEST.AUTHORS (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (10,'New author 2',null);
REM INSERTING into TEST.COMMENTS
SET DEFINE OFF;
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (82,3,'Comment3',to_timestamp('01-JUN-16 10.55.46.994000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (83,1,'Comment1 ',to_timestamp('06-JUN-16 10.55.57.378000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (84,4,'Comment4',to_timestamp('07-JUN-16 10.56.14.017000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (77,1,'Some text',to_timestamp('07-JUN-16 10.54.19.377000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (78,2,'Random guy opinion',to_timestamp('07-JUN-16 10.54.37.528000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (79,2,'Very importnat comment',to_timestamp('07-JUN-16 10.54.54.477000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (80,1,'Comment1',to_timestamp('01-JUN-16 10.55.11.589000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.COMMENTS (COMMENT_ID,NEWS_ID,COMMENT_TEXT,CREATION_DATE) values (81,3,'Test Test Tes',to_timestamp('15-JUN-16 10.55.33.763000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
REM INSERTING into TEST.NEWS
SET DEFINE OFF;
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (1,'Мальчик вернул  шишку','Мальчик вернул в заповедник украденную шишку','Мальчик вернул в заповедник украденную шишку',to_timestamp('07-JUN-16 10.44.40.204000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.44.43.072000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (2,'Эксперты забраковали носки','Красноярские эксперты забраковали большинство мужских носков','Красноярские эксперты забраковали большинство мужских носков',to_timestamp('07-JUN-16 10.46.18.003000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.46.23.947000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (3,'Молодежная политика','Молодёжную политику Иркутской области будет курировать 60-летний министр','Молодёжную политику Иркутской области будет курировать 60-летний министр',to_timestamp('07-JUN-16 10.47.06.322000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.47.09.010000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (4,'Breaking news','Breaking news!','Warning! Attention!',to_timestamp('07-JUN-16 10.42.24.584000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.42.33.639000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (5,'Пьяный житель Тульской области','Пьяный житель Тульской области угнал асфальтоукладчик и раскатал двор','Пьяный житель Тульской области угнал асфальтоукладчик и раскатал двор',to_timestamp('07-JUN-16 10.43.38.537000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.43.41.410000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (6,'Рекорд по числу пьяных','В пермском крае в день запрета продажи алкоголя был побит рекорд по числу пьяных','В пермском крае в день запрета продажи алкоголя был побит рекорд по числу пьяных',to_timestamp('07-JUN-16 10.47.45.334000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.47.50.454000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (7,'Застрявший мужчина','Мужчина хотел снять кота с дерева и сам там застрял','Мужчина хотел снять кота с дерева и сам там застрял',to_timestamp('07-JUN-16 10.48.29.491000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.48.31.612000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (8,'Чиновник брал взятки вискасом','Калужский чиновник брал взятки «Вискасом» и виски','Калужский чиновник брал взятки «Вискасом» и виски',to_timestamp('07-JUN-16 10.50.04.686000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.50.07.006000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (9,'News1','News1','News1',to_timestamp('07-JUN-16 10.50.55.851000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.50.58.347000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
Insert into TEST.NEWS (NEWS_ID,TITLE,SHORT_TEXT,FULL_TEXT,CREATION_DATE,MODIFICATION_DATE) values (10,'News2','News2','News2',to_timestamp('07-JUN-16 10.51.16.524000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('07-JUN-16 10.51.19.034000000 AM','DD-MON-RR HH.MI.SSXFF AM'));
REM INSERTING into TEST.NEWS_AUTHORS
SET DEFINE OFF;
Insert into TEST.NEWS_AUTHORS (NEWS_ID,AUTHOR_ID) values (1,1);
Insert into TEST.NEWS_AUTHORS (NEWS_ID,AUTHOR_ID) values (2,5);
Insert into TEST.NEWS_AUTHORS (NEWS_ID,AUTHOR_ID) values (3,4);
Insert into TEST.NEWS_AUTHORS (NEWS_ID,AUTHOR_ID) values (4,2);
Insert into TEST.NEWS_AUTHORS (NEWS_ID,AUTHOR_ID) values (5,7);
Insert into TEST.NEWS_AUTHORS (NEWS_ID,AUTHOR_ID) values (6,3);
Insert into TEST.NEWS_AUTHORS (NEWS_ID,AUTHOR_ID) values (7,6);
REM INSERTING into TEST.NEWS_TAGS
SET DEFINE OFF;
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (1,1);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (1,2);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (1,4);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (1,7);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (2,2);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (2,3);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (2,6);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (3,4);
Insert into TEST.NEWS_TAGS (NEWS_ID,TAG_ID) values (5,5);
REM INSERTING into TEST.ROLES
SET DEFINE OFF;
Insert into TEST.ROLES (ROLE_ID,ROLE_NAME) values (1,'guest');
Insert into TEST.ROLES (ROLE_ID,ROLE_NAME) values (2,'creader');
Insert into TEST.ROLES (ROLE_ID,ROLE_NAME) values (3,'author');
Insert into TEST.ROLES (ROLE_ID,ROLE_NAME) values (4,'admin');
REM INSERTING into TEST.TAGS
SET DEFINE OFF;
Insert into TEST.TAGS (TAG_ID,TAG_NAME) values (1,'tag1');
Insert into TEST.TAGS (TAG_ID,TAG_NAME) values (2,'funny');
Insert into TEST.TAGS (TAG_ID,TAG_NAME) values (3,'random');
Insert into TEST.TAGS (TAG_ID,TAG_NAME) values (4,'comment');
Insert into TEST.TAGS (TAG_ID,TAG_NAME) values (5,'tag3');
Insert into TEST.TAGS (TAG_ID,TAG_NAME) values (6,'tag4');
Insert into TEST.TAGS (TAG_ID,TAG_NAME) values (7,'tag');
REM INSERTING into TEST.USERS
SET DEFINE OFF;
Insert into TEST.USERS (USER_ID,USER_NAME,LOGIN,PASSWORD,ROLE_ID) values (1,'user','user','user',1);
Insert into TEST.USERS (USER_ID,USER_NAME,LOGIN,PASSWORD,ROLE_ID) values (2,'login','login','login',2);
Insert into TEST.USERS (USER_ID,USER_NAME,LOGIN,PASSWORD,ROLE_ID) values (3,'admin','admin','admin',3);
Insert into TEST.USERS (USER_ID,USER_NAME,LOGIN,PASSWORD,ROLE_ID) values (4,'rreader','reader','reader',4);
Insert into TEST.USERS (USER_ID,USER_NAME,LOGIN,PASSWORD,ROLE_ID) values (5,'new guy','guy','qwerty',3);
