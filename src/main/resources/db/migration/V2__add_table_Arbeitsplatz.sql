create table if not exists arbeitsplatz(
                                      arbeitsplatz_id SERIAL PRIMARY KEY ,
                                      name varchar(300),
                                      room_id int,
                                     foreign key(room_id) REFERENCES room(room_id)
);