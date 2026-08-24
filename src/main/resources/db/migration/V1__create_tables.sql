CREATE TABLE tb_customer(
    id UUID PRIMARY KEY default GEN_RANDOM_UUID(),
    full_name VARCHAR(255) NOT NULL,
    document VARCHAR(14) NOT NULL UNIQUE,
    whatsapp_number VARCHAR(13) NOT NULL,
    document_type VARCHAR(4) NOT NULL,
    is_active BOOLEAN NOT NULL,
    CHECK (
        (document_type = 'CPF' AND length(document) = 11)
        OR
        (document_type = 'CNPJ' AND length(document) = 14)
        )
);

CREATE TABLE tb_invoice(
    id UUID PRIMARY KEY default GEN_RANDOM_UUID(),
    issued_at TIMESTAMP NOT NULL,
    due_date DATE NOT NULL,
    gross_value NUMERIC(10,2) NOT NULL,
    discount NUMERIC(10,2) NOT NULL,
    net_value NUMERIC(10,2) NOT NULL,
    status VARCHAR(15) NOT NULL,
    note TEXT,
    customer_id UUID NOT NULL,
    FOREIGN KEY(customer_id) REFERENCES tb_customer(id)
);

CREATE TABLE tb_payment(
    id UUID PRIMARY KEY default GEN_RANDOM_UUID(),
    payment_date TIMESTAMP NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    payment_method VARCHAR(12) NOT NULL,
    invoice_id UUID NOT NULL,
    FOREIGN KEY(invoice_id) REFERENCES tb_invoice(id)
);