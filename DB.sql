
Create database gameServer
use gameserver
Create table player
(
	id int  AUTO_INCREMENT,
	username varchar(20),
    gender char(5),
    score int,
	birthday date,
    email varchar(50),
    loginname nvarchar(20) unique not null,
    pass char(20),
    lv 	int, /* cấp độ */
    primary key(id)
)
insert into player (username,gender,score,birthday,email,loginname,pass,lv) value ('Lâm','nam',0,'1996/1/21','lam0196vn@xxx.com','lam0196xx','xxxx',1)
select * from player
drop table player