drop schema if exists getmydrivercard;

create database getmydrivercard;
use getmydrivercard;

create table users
(
	user_id int not null auto_increment primary key,
    username varchar(50) not null unique key,
    password varchar(255) default null,
    facebook_id varchar(255) default null,
    google_id varchar(255) default null
);

create table roles
(
	role_id int not null primary key,
    role_name varchar(11) default null
);

create table users_roles
(
	user_id int not null,
    role_id int not null,
    primary key(user_id, role_id),
    constraint foreign key(user_id) references users(user_id),
	constraint foreign key(role_id) references roles(role_id)
);

create table applicants_details
(
	details_id int not null auto_increment primary key,
    egn varchar(10) not null,
    first_name varchar(50) not null,
    middle_name varchar(50) not null,
    last_name varchar(50) not null,
    birthdate varchar(50) not null,
    email varchar(255) not null,
    address varchar(100) not null,
    phone_number varchar(50) not null
);

create table attachments
(
	attachment_id int not null auto_increment primary key,
    selfie_pic longtext not null,
    user_id_card_pic longtext default null,
    signature_screenshot longtext not null,
    driver_lic_pic longtext default null,
    prev_driver_card_pic longtext default null
);

create table card_renewals_details
(
	renewal_id int not null auto_increment primary key,
    renewal_reason varchar(10) not null,
    renewal_first_name varchar(50) default null,
    renewal_middle_name varchar(50) default null,
    renewal_last_name varchar(50) default null,
    renewal_address varchar(100) default null
);

create table card_replacements_details
(
	replacement_id int not null auto_increment primary key,
    replacement_reason varchar(10) not null,
    prev_tach_card_country varchar(20) default null,
    prev_tach_card_num varchar(50) default null,
    driving_lic_country varchar(20) default null,
    driving_lic_num varchar(20) default null,
    replacement_incident_date varchar(20) default null,
    replacement_incident_place varchar(20) default null
);

create table card_requests
(
	request_id int not null auto_increment primary key,
    type varchar(10) not null,
    status varchar(20) not null,
    request_details_id int not null,
    request_attachment_id int not null,
    request_user_id int not null,
	request_renewal_id int default null,
    request_replacement_id int default null,
    constraint foreign key(request_details_id) references applicants_details(details_id),
    constraint foreign key(request_attachment_id) references attachments(attachment_id),
    constraint foreign key(request_user_id) references users(user_id),
    constraint foreign key(request_renewal_id) references card_renewals_details(renewal_id),
    constraint foreign key(request_replacement_id) references card_replacements_details(replacement_id)
);