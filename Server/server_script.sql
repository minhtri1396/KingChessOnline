create database 1412278_1412573_1412648_Server;
use 1412278_1412573_1412648_Server;

create table ACCOUNT (
	username varchar(50) not null primary key,
    password varchar(50),
    userID int
);

create table USER (
	id int auto_increment primary key,
    username varchar(50),
    name nvarchar(50),
    level int
);



insert into USER (username, name, level) values ('minhtri1396', 'Tri Dao', '99');
insert into ACCOUNT (username, password, userID) values ('minhtri1396', '123456789', (select id from USER u where u.username = 'minhtri1396'));


insert into USER (username, name, level) values ('lamvn', 'Lâm Thồn', '10');
insert into ACCOUNT (username, password, userID) values ('lamvn', '123456789', (select id from USER u where u.username = 'lamvn'));


insert into USER (username, name, level) values ('nxvu', 'Vũ Cơ', '50');
insert into ACCOUNT (username, password, userID) values ('nxvu', '123456789', (select id from USER u where u.username = 'nxvu'));
