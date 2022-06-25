DROP TABLE IF EXISTS domain_events;
DROP TABLE IF EXISTS event_people;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS domain_lords;
DROP TABLE IF EXISTS people;
DROP TABLE IF EXISTS domains;


CREATE TABLE events (
	event_id INT NOT NULL AUTO_INCREMENT,
	event_name VARCHAR(50) NOT NULL,
	event_desc VARCHAR(1000) NOT NULL,
	timeline_date INT NOT NULL,
	PRIMARY KEY (event_id),
	UNIQUE KEY(event_name)
	);
	
CREATE TABLE  domains (
	domain_id INT NOT NULL AUTO_INCREMENT,
	domain_name VARCHAR(50) NOT NULL,
	domain_desc VARCHAR(2000),
	region VARCHAR(50),	
	PRIMARY KEY (domain_id),
	UNIQUE KEY(domain_name)
	);

CREATE TABLE people (
	person_id INT  NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	race VARCHAR(30), 	
	description VARCHAR(1000),
	domain_id INT NOT NULL,
	PRIMARY KEY (person_id),
	UNIQUE KEY(name),
	FOREIGN KEY (domain_id) REFERENCES domains (domain_id)
	);

CREATE TABLE domain_lords (
	person_id INT NOT NULL,
    domain_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES people (person_id) ON DELETE CASCADE,
    FOREIGN KEY (domain_id) REFERENCES domains (domain_id) ON DELETE CASCADE   
	);
    
CREATE TABLE domain_events (
	event_id INT NOT NULL,
	domain_id INT NOT NULL,	
	FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE,
	FOREIGN KEY (domain_id) REFERENCES domains (domain_id)ON DELETE CASCADE
	);
	
CREATE TABLE event_people (	
	event_id INT NOT NULL,
	person_id INT NOT NULL,	
	FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE,
	FOREIGN KEY (person_id) REFERENCES people (person_id) ON DELETE CASCADE
	);





