create table if not exists room(
                                      room_id serial primary key ,
                                      name varchar(300),
                                      id uuid unique
);

create table if not exists "key"(
                                   key_id serial primary key ,
                                   owner varchar(300),
                                   id uuid unique
);