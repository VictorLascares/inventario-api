CREATE TABLE categories (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE suppliers (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    rfc         VARCHAR(13)  UNIQUE,
    email       VARCHAR(150),
    phone       VARCHAR(20),
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE products (
    id          BIGSERIAL     PRIMARY KEY,
    sku         VARCHAR(50)   NOT NULL UNIQUE,
    name        VARCHAR(150)  NOT NULL,
    description TEXT,
    price       NUMERIC(12,2) NOT NULL CHECK (price >= 0),
    stock       INTEGER       NOT NULL DEFAULT 0 CHECK (stock >= 0),
    min_stock   INTEGER       NOT NULL DEFAULT 0 CHECK (min_stock >= 0),
    active      BOOLEAN       NOT NULL DEFAULT TRUE,
    category_id BIGINT        NOT NULL REFERENCES categories (id),
    supplier_id BIGINT        REFERENCES suppliers (id),
    version     BIGINT        NOT NULL DEFAULT 0,
    created_at  TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ   NOT NULL DEFAULT now()
);

CREATE INDEX idx_products_category ON products (category_id);
CREATE INDEX idx_products_supplier ON products (supplier_id);
CREATE INDEX idx_products_name     ON products (lower(name));