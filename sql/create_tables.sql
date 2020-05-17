CREATE TABLE cat (
  cat_id INTEGER NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  name VARCHAR(50),
  birthday DATE,
  breed_id INTEGER,
  person_id INTEGER,
  gender_id INTEGER,
  father_id INTEGER,
  mother_id INTEGER
);

CREATE TABLE gender (
  gender_id INTEGER NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  gender VARCHAR(50)
);

CREATE TABLE person (
  person_id INTEGER NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  name VARCHAR(50)
);

CREATE TABLE breed (
  breed_id INTEGER NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  name VARCHAR(50)
);

CREATE TABLE  exhibition (
  exhibition_id INTEGER NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  title VARCHAR(50),
  city_id INTEGER,
  exhibition_date DATE
);

CREATE TABLE city (
  city_id INTEGER NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  title VARCHAR(50)
);

CREATE TABLE participation (
  participation_id INTEGER NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  cat_id INTEGER,
   exhibition_id INTEGER,
  place INTEGER
);

ALTER TABLE cat ADD FOREIGN KEY (person_id) REFERENCES person (person_id);

ALTER TABLE cat ADD FOREIGN KEY (breed_id) REFERENCES breed (breed_id);

ALTER TABLE participation ADD FOREIGN KEY (cat_id) REFERENCES cat (cat_id);

ALTER TABLE  exhibition ADD FOREIGN KEY (city_id) REFERENCES city (city_id);

ALTER TABLE participation ADD FOREIGN KEY ( exhibition_id) REFERENCES  exhibition ( exhibition_id);

ALTER TABLE cat ADD FOREIGN KEY (gender_id) REFERENCES gender (gender_id);
