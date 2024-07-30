create table usuario_role(
    id INT AUTO_INCREMENT not null,
    usuario_id int NOT NULL,
    role_id int NOT NULL,

    primary key(id),
    foreign key (usuario_id) references usuario(id),
    foreign key (role_id) references role(id)
);
