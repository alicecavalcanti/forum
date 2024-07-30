create table respostas(
    id INT AUTO_INCREMENT not null,
    mensagem varchar(300) not null,
    data_criacao datetime not null,
    autor_id int not null,
    topico_id int not null,
    solucao bit not null,

    primary key(id),
    foreign key(autor_id) references usuario(id),
    foreign key(topico_id) references topico(id)
);
