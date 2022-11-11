drop table if exists  users CASCADE;
drop table if exists participations CASCADE;
drop table if exists locations CASCADE;
drop table if exists categories CASCADE;
drop table if exists compilations CASCADE;
drop table if exists events CASCADE;
drop table if exists compilations_events CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS locations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(150),
    lat DECIMAL NOT NULL,
    lon DECIMAL NOT NULL,
    radius DECIMAL,
    UNIQUE (lat, lon)
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(255) NOT NULL,
    pinned BOOLEAN,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation TEXT NOT NULL,
    category_id BIGINT NOT NULL,
    created_on TIMESTAMP,
    description TEXT,
    event_date TIMESTAMP  NOT NULL,
    initiator_id BIGINT NOT NULL,
    location_id BIGINT ,
    paid BOOLEAN NOT NULL,
    participant_limit INTEGER,
    published_on TIMESTAMP,
    request_moderation BOOLEAN,
    state VARCHAR(100),
    title VARCHAR(255) NOT NULL ,
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    CONSTRAINT fk_initiator FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS participations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created TIMESTAMP NOT NULL,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    status VARCHAR(100),
    CONSTRAINT pk_participation PRIMARY KEY (id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT fk_requester FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilations_events (
    compilation_id BIGINT NOT NULL,
    event_id       BIGINT NOT NULL,
    CONSTRAINT pk_compilation_ev PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_compilation_event FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE ,
    CONSTRAINT fk_event_compilation FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);


