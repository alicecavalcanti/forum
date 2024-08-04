create table usuario(
     id INT AUTO_INCREMENT not null unique,
     nome varchar(50) not null,
     email varchar(50) not null unique,
     password varchar(100) not null,
     primary key (id)
);
