CREATE TABLE tb_message_template(
    id UUID PRIMARY KEY default GEN_RANDOM_UUID(),
    message TEXT NOT NULL,
    message_type VARCHAR(30) NOT NULL UNIQUE
);