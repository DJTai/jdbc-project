-- Table publishers holds a publisher's name, their address, their phone number, and their email address.
CREATE TABLE publishers (
	publishername VARCHAR(40) NOT NULL, -- Name of the publisher
	publisheraddress VARCHAR(50) NOT NULL, -- Address of the publisher
	publisherphone VARCHAR(20) NOT NULL, -- Phone number of the publisher
	publisheremail VARCHAR(40) NOT NULL, -- Email address of the publisher
	CONSTRAINT publishers_pk
	PRIMARY KEY(publishername)
);
	
-- Holds the group's name, head writer, year formed, and subject they write about
CREATE TABLE writingGroups(
    groupName VARCHAR(30) NOT NULL,  -- Name of the group
    headWriter VARCHAR(30) NOT NULL, -- Head writer of the group
    yearFormed INTEGER NOT NULL,     -- Year the group was formed
    subject VARCHAR(20) NOT NULL,    -- Subject that the group writes about
    CONSTRAINT writingGroups_pk
        PRIMARY KEY(groupName)
);

/* Holds the information on the books that Writing Groups have written and 
Publishers have Published */
CREATE TABLE books(
    groupName VARCHAR(30) NOT NULL,      -- Name of the group who wrote the book
    bookTitle VARCHAR(120) NOT NULL,     -- Title of the book
    publisherName VARCHAR(40) NOT NULL,  -- Publishers name
    yearPublished INTEGER NOT NULL,      -- Year the book was published
    numberPages INTEGER NOT NULL,        -- Number of pages in the book
    CONSTRAINT books_pk PRIMARY KEY(groupName, bookTitle),
    CONSTRAINT books_ck UNIQUE (bookTitle, publisherName),
    CONSTRAINT books_fk01 FOREIGN KEY (groupName)
        REFERENCES writingGroups(groupName),
    CONSTRAINT books_fk02 FOREIGN KEY (publisherName)
        REFERENCES publishers(publisherName)
);

-- Test values for WRITING GROUPS table
INSERT INTO writingGroups(groupName, headWriter, yearFormed, subject)
VALUES ('Riders Block', 'Chelsea Marfil', 2018, 'Fiction'),
('Avengers Writers', 'Jason Aaron', 1937, 'Comics'),
('W.W. Writers', 'James Tynion IV', 1934, 'Comics'),
('Spawn Writers', 'Todd McFarlane', 1992, 'Comics'),
('Comment Blocks', 'David Taitingfong', 2018, 'Non-fiction'),
('To Be Or Not To Be', 'William S', 1616, 'Poetry'),
('Read Wine', 'Rafi Feliciano', 2016, 'History'),
('Left on Read', 'Chelsea Marfil', 2016, 'Science Fiction'),
('Riding Writers', 'William Harley', 1903, 'History'),
('Only SQLs', 'Dave Brown', 2012, 'Non-fiction'),
('LOL', 'Tina Fey', 2016, 'Humor'),
('Psyched Out', 'Malcolm Gladwell', 2012, 'Self-Help'),
('Mozilla', 'Mitchell Baker', 2005, 'Business'),
('Found My Fitness', 'Rhonda Patrick', 2012, 'Nutrition'),
('Nutrition Facts', 'Michael Greger', 2000, 'Nutrition'),
('Sleeping At Terminals', 'Tom Hanks', 2000, 'Travel'),
('Read-N-Out', 'Lynsi Snyder', 2013, 'Business'),
('Pen & Tell', 'David Taitingfong', 2016, 'Humor');

-- Test values for PUBLISHERS table
INSERT INTO publishers(publisherName, publisherAddress, publisherPhone,
publisherEmail)
VALUES ('Marvel Comics', '500 Marvelous Way', '562-555-1234', 'marvel@marvel.com'),
VALUES ('DC Comics', '777 Dark Knight Ln', '', ''),
VALUES ('', '', '', ''),
VALUES ('', '', '', ''),
VALUES ('', '', '', ''),
VALUES ('', '', '', '');

