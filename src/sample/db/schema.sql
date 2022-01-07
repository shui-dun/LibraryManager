create table book
(
  ID       integer
    primary key,
  name     text not null,
  author   text not null,
  press    text not null,
  category text not null,
  amount   integer default 0 not null
);

create table manager
(
  ID         integer not null
    primary key,
  last_name  text    not null,
  first_name text    not null,
  gender     text    default '保密' not null,
  birth      text    not null,
  password   integer default '1234' not null
);

create table student
(
  ID         integer
    primary key,
  last_name  text    not null,
  first_name text    not null,
  gender     text    default '保密' not null,
  class      integer not null,
  faculty    text    not null,
  password   integer default '1234' not null
);

create table borrow
(
  book_ID     integer,
  borrower_ID integer,
  borrow_time text default date('now', 'localtime'),
  constraint borrow_book_ID_borrower_ID_pk
  primary key (book_ID, borrower_ID)
);