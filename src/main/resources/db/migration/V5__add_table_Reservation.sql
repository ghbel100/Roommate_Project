create table if not exists reservation(
                              reservation_id serial PRIMARY KEY,
                              tag date,
                              start_time time,
                              end_time time,
                              arbeitsplatz_id int,
                              foreign key (arbeitsplatz_id) REFERENCES arbeitsplatz(arbeitsplatz_id)

);
