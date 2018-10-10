-- Table publishers holds a publisher's name, their address, their phone number, and their email address.
-- 
CREATE TABLE publishers (
	publishername VARCHAR(40) NOT NULL,
	publisheraddress VARCHAR(50) NOT NULL,
	publisherphone VARCHAR(20) NOT NULL,
	publisheremail VARCHAR(40) NOT NULL,
	CONSTRAINT publishers_pk
	PRIMARY KEY(publishername));
	
