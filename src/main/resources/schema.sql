create table room
(
    id      bigserial PRIMARY KEY,
    name    character varying(255),
    country character varying(255),
    lamp_on boolean
)
