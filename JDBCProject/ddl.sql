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