CREATE SCHEMA translator
  DEFAULT CHARACTER SET utf8;

CREATE TABLE translator.language (
  id          INT         NOT NULL AUTO_INCREMENT,
  code      VARCHAR(5)  NOT NULL,
  description VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX code_UNIQUE (code ASC)
);
 INSERT INTO translator.language (id, code, description) VALUES (1, 'ru', 'Русский');
 INSERT INTO translator.language (id, code, description) VALUES (2, 'it', 'Italiano');
 INSERT INTO translator.language (id, code, description) VALUES (3, 'fi', 'Suomi');
 INSERT INTO translator.language (id, code, description) VALUES (4, 'en', 'English');

CREATE TABLE translator.words (
  id      INT          NOT NULL AUTO_INCREMENT,
  lang_id INT          NOT NULL,
  word    VARCHAR(45)  NOT NULL,
  phoneme VARCHAR(45)  NULL,
  subject VARCHAR(45)  NULL,
  PRIMARY KEY (id),

  CONSTRAINT words_lang_id_word_pk
  UNIQUE (lang_id, word),

  CONSTRAINT words_lang_id_fk
  FOREIGN KEY (lang_id)  REFERENCES translator.language (id)

);

CREATE TABLE translator.translate (
  id               INT         NOT NULL AUTO_INCREMENT,
  word_id          INT         NOT NULL,
  lang_id          INT         NOT NULL,
  translate        VARCHAR(45) NULL,
  site_source      VARCHAR(50) NULL,
  PRIMARY KEY (id),

  CONSTRAINT translate_word_id_lang_id_pk
  UNIQUE (word_id, lang_id),

  CONSTRAINT translate_lang_id_fk
  FOREIGN KEY (lang_id)  REFERENCES translator.language (id),

  CONSTRAINT translate_word_id_fk
  FOREIGN KEY (word_id)  REFERENCES translator.words (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE translator.sentence (
  id                 INT          NOT NULL AUTO_INCREMENT,
  word_id            INT          NOT NULL,
  sentence_original  VARCHAR(300) NULL,
  lang_id            INT          NULL,
  sentence_translate VARCHAR(300) NULL,
  site_source        VARCHAR(50)  NULL,

  PRIMARY KEY (id),

  CONSTRAINT sentence_lang_id_fk
  FOREIGN KEY (lang_id) REFERENCES translator.language (id),

  CONSTRAINT sentence_word_id_fk
  FOREIGN KEY (word_id) REFERENCES translator.words (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
