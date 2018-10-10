-- Table publishers holds a publisher's name, their address, their phone number, and their email address.
-- 
CREATE TABLE publishers (
	publishername VARCHAR(40) NOT NULL,
	publisheraddress VARCHAR(50) NOT NULL,
	publisherphone VARCHAR(20) NOT NULL,
	publisheremail VARCHAR(40) NOT NULL,
	CONSTRAINT publishers_pk
	PRIMARY KEY(publishername));
	

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
    