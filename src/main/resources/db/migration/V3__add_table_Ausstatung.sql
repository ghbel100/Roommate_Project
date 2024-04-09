create table if not exists ausstatung(
                                      name varchar(300),
                                      arbeitsplatz int,
                                        foreign key (arbeitsplatz) references arbeitsplatz(arbeitsplatz_id)

);