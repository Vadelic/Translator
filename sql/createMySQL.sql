CREATE SCHEMA translatorDB
  DEFAULT CHARACTER SET utf8;
USE translatorDB;

create table LANGUAGE (
  ID          INTEGER not null primary key AUTO_INCREMENT,
   VERSION     TIMESTAMP,
 CODE        VARCHAR(5),
  DESCRIPTION VARCHAR(30)
);

INSERT INTO LANGUAGE (code, description) VALUES ('ru', 'Русский');
INSERT INTO LANGUAGE (code, description) VALUES ('it', 'Italiano');
INSERT INTO LANGUAGE (code, description) VALUES ('fi', 'Suomi');
INSERT INTO LANGUAGE (code, description) VALUES ('en', 'English');

create table WORD (
  ID       INTEGER not null    primary key AUTO_INCREMENT,
    VERSION     TIMESTAMP,
PHONEME  VARCHAR(255),
  RESOURCE VARCHAR(255),
  SUBJECT  VARCHAR(255),
  WORD     VARCHAR(255),
  LANG_ID  INTEGER,

  CONSTRAINT word_lang_id_fk FOREIGN KEY (LANG_ID) REFERENCES language (id)
    on update cascade
    on delete cascade
);

create table LANGUAGE_PACK (
  ID        INTEGER not null primary key AUTO_INCREMENT,
   VERSION     TIMESTAMP,
 RESOURCE  VARCHAR(255),
  TRANSLATE VARCHAR(255),
  LANG_ID   INTEGER,
  CONSTRAINT pack_lang_id_fk FOREIGN KEY (LANG_ID) REFERENCES language (id)
    on update cascade
    on delete cascade
);

create table SENTENCE (
  ID                 INTEGER not null    primary key AUTO_INCREMENT,
   VERSION     TIMESTAMP,
 RESOURCE           VARCHAR(255),
  SENTENCE_ORIGINAL  VARCHAR(255),
  SENTENCE_TRANSLATE VARCHAR(255)
);

create table PACK_SENTENCES_LINK (
  LANGUAGE_PACK_ID INTEGER not null,
  constraint FK_pack_to_sentence FOREIGN KEY (LANGUAGE_PACK_ID) references LANGUAGE_PACK (id)
    on update cascade
    on delete cascade,
  SENTENCES_ID     INTEGER not null,
  constraint FK_sentence_to_pack FOREIGN KEY (SENTENCES_ID) references SENTENCE (id)
    on update cascade
    on delete cascade
);

create table WORD_TRANSLATE_PACKS_LINK
(
  WORD_ID            INTEGER not null,
  constraint FK_words_to_pack FOREIGN KEY (WORD_ID) references WORD (id)
    on update cascade
    on delete cascade,
  TRANSLATE_PACKS_ID INTEGER not null,
  constraint FK_pack_to_words FOREIGN KEY (TRANSLATE_PACKS_ID) references LANGUAGE_PACK (id)
    on update cascade
    on delete cascade;
        CONSTRAINT WORD_TRANSLATE_PACKS_LINK_WORD_ID_TRANSLATE_PACKS_ID_pk UNIQUE (WORD_ID, TRANSLATE_PACKS_ID);
);