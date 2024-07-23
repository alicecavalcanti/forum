create table usuario_role(
    id BIGINT not null AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    primary key(id),
    foreign key (usuario_id) references usuario(id),
    foreign key (role_id) references role(id)
);

INSERT INTO usuario_role VALUES (1,1,1)