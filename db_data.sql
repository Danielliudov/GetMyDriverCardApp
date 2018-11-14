use getmydrivercard;

insert into roles(role_id, role_name) 
	values (1, 'user'), (2, 'admin');

insert into users(username, password)
	values ('momchenceto', '$2a$10$n/N9z2Fce3p4yVAci5UueOxxyAOtTQPY0VRmW9dbE3okXCIYEctdK'),
			('admin', '$2a$10$n/N9z2Fce3p4yVAci5UueOxxyAOtTQPY0VRmW9dbE3okXCIYEctdK');

insert into users_roles(user_id, role_id) 
	values (1, 1), (2, 1), (2, 2);
    
