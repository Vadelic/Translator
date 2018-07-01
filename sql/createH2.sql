CREATE SCHEMA translatorDB
  DEFAULT CHARACTER SET utf8;


create table LANGUAGE (
  ID          INTEGER not null primary key AUTO_INCREMENT,
  CODE        VARCHAR(5),
  DESCRIPTION VARCHAR(30)
);

INSERT INTO LANGUAGE (code, description) VALUES ('ru', 'Русский');
INSERT INTO LANGUAGE (code, description) VALUES ('it', 'Italiano');
INSERT INTO LANGUAGE (code, description) VALUES ('fi', 'Suomi');
INSERT INTO LANGUAGE (code, description) VALUES ('en', 'English');

create table WORD (
  ID       INTEGER not null    primary key AUTO_INCREMENT,
  PHONEME  VARCHAR(255),
  RESOURCE VARCHAR(255),
  SUBJECT  VARCHAR(255),
  WORD     VARCHAR(255),
  LANG_ID  INTEGER  CONSTRAINT word_lang_id_fk  REFERENCES language (id)

);

create table LANGUAGE_PACK (
  ID        INTEGER not null primary key AUTO_INCREMENT,
  RESOURCE  VARCHAR(255),
  TRANSLATE VARCHAR(255),
  LANG_ID   INTEGER
  CONSTRAINT pack_lang_id_fk REFERENCES language (id)
);

create table SENTENCE (
  ID                 INTEGER not null    primary key AUTO_INCREMENT,
  RESOURCE           VARCHAR(255),
  SENTENCE_ORIGINAL  VARCHAR(255),
  SENTENCE_TRANSLATE VARCHAR(255),
);

create table PACK_SENTENCES_LINK (
  LANGUAGE_PACK_ID INTEGER not null
    constraint FK_pack_to_sentence references LANGUAGE_PACK (id),
  SENTENCES_ID     INTEGER not null
    constraint FK_sentence_to_pack references SENTENCE (id)
);

create table WORD_TRANSLATE_PACKS_LINK
(
  WORD_ID            INTEGER not null
    constraint FK_words_to_pack references WORD (id),
  TRANSLATE_PACKS_ID INTEGER not null
    constraint FK_pack_to_words references LANGUAGE_PACK (id)