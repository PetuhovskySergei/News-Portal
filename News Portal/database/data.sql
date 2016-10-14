insert into author values(1, 'John Johnson');
insert into author values(2, 'Nick Gantz');
insert into author values(3, 'Terry Pratchett');
insert into author values(4, 'Colleen McCullough');
insert into author values(5, 'Theodore Dreiser');

insert into comments values(1, 22, 'First', DATE '29.09.16');
insert into comments values(2, 13, 'lol', DATE '29.09.16');
insert into comments values(3, 53, 'it is because I am black?', DATE '29.09.16');
insert into comments values(4, 24, 'Take my money', DATE '29.09.16');
insert into comments values(5, 41, 'Wow, imma lost my mind how i lost my mind, logical, son', DATE '29.09.16');
insert into comments values(6, 22, 'Second!', DATE '29.09.16');
insert into comments values(7, 22, 'it is because I am asian?', DATE '29.09.16');
insert into comments values(8, 22, 'it is because I am Arabian?', DATE '29.09.16');

insert into news values(22, 'Color of magic', 'A few words about book color of magic from author', 'Tarry told: "How i write this book, i have a lot of drugs, and of course im use these', DATE '25.09.16');
insert into news values(13, 'Good News', 'Now im tell you good news', 'Life it is so cool story about human', DATE '14.09.16');
insert into news values(53, 'Rush for Cash', 'Some people started rush for cash', 'Some text about people who started rush for cash', DATE '17.09.16');
insert into news values(24, 'Women writer', 'Story about woman who a woman', 'Are you woman? Yes, lol', DATE '07.09.16');
insert into news values(41, 'American tragedy', 'We are ask a few question', 'Do you write American Tragady? Yes. Do you like the black people? Of course yes', DATE '20.09.16');

insert into tag values(1, 'Books');
insert into tag values(2, 'Famous People');
insert into tag values(3, 'Racism');
insert into tag values(4, 'Popular News');

insert into news_author values(1, 22);
insert into news_author values(2, 13);
insert into news_author values(3, 53);
insert into news_author values(4, 24);
insert into news_author values(5, 41);

insert into news_tag values(53, 3);
insert into news_tag values(53, 4);
insert into news_tag values(22, 1);
insert into news_tag values(22, 2);
insert into news_tag values(13, 4);
insert into news_tag values(24, 1);
insert into news_tag values(24, 2);
insert into news_tag values(41, 1);
insert into news_tag values(41, 2);
insert into news_tag values(41, 3);