create table usuario(
     id bigint generated by default as identity not null,
     nome varchar(50) not null,
     email varchar(50) not null,
     primary key (id)
);
insert into usuario values (1, 'Alice Pereira', 'alice@email.com')
