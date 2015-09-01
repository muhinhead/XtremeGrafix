CREAte table DBversion
(
    dbversion_id    int not null auto_increment,
    version_id      int not null,
    version         varchar(12),
    constraint dbversion_pk primary key (dbversion_id)
);

insert into dbversion values(1,1,'0.01');

create table user
(
    user_id int not null auto_increment,
    first_name varchar(16),
    last_name varchar(16),
    initials char(2),
    login varchar(16) not null,
    passwd varchar(32) not null,
    is_admin bit default 0,
    photo       mediumblob,
    constraint user_pk primary key (user_id) 
);

insert into user(user_id, first_name, last_name, initials, login, passwd, is_admin) 
values (1,'Admin', 'Admission', 'AA', 'admin', 'admin', 1);

create table company
(
    company_id int not null auto_increment,
    name varchar(128) not null,
    street varchar(64),
    area_pobox varchar(32),
    city varchar(64),
    postcode varchar(12),
    reg_no varchar(32),
    vat_no varchar(32),
    comments text,
    constraint company_pk primary key (company_id)
);

create table contact
(
    contact_id int not null auto_increment,
    company_id int not null,
    name varchar(32) not null,
    phone varchar(32),
    email varchar(128),
    email1 varchar(128),
    comments text,
    constraint contact_pk primary key (contact_id),
    constraint contact_company_fk foreign key (company_id) references company (company_id) on delete cascade
);

