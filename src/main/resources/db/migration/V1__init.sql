-- Authors
CREATE TABLE authors (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name   VARCHAR(200) NOT NULL,
    bio         TEXT,
    nationality VARCHAR(80)
);

-- Categories
CREATE TABLE categories (
    id        UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    name      VARCHAR(80) NOT NULL UNIQUE,
    parent_id UUID        REFERENCES categories(id)
);

-- Loan Policies
CREATE TABLE loan_policies (
    id                   UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    name                 VARCHAR(80)    NOT NULL,
    loan_days            SMALLINT       NOT NULL,
    max_renewals         SMALLINT       NOT NULL,
    max_simultaneous     SMALLINT       NOT NULL,
    fine_per_day         NUMERIC(6, 2)  NOT NULL,
    reservation_hold_days SMALLINT      NOT NULL,
    is_active            BOOLEAN        NOT NULL DEFAULT true,
    created_at           TIMESTAMPTZ    NOT NULL DEFAULT now()
);

-- Default loan policy seed
INSERT INTO loan_policies (id, name, loan_days, max_renewals, max_simultaneous, fine_per_day, reservation_hold_days, is_active)
VALUES (gen_random_uuid(), 'Política Padrão', 14, 1, 2, 1.00, 2, true);

-- Readers
CREATE TABLE readers (
    id                  UUID         PRIMARY KEY,
    email               VARCHAR(254) NOT NULL,
    name                VARCHAR(120) NOT NULL,
    registration_number VARCHAR(40),
    phone               VARCHAR(20),
    address             VARCHAR(300),
    policy_id           UUID         NOT NULL REFERENCES loan_policies(id),
    is_blocked          BOOLEAN      NOT NULL DEFAULT false,
    blocked_reason      VARCHAR(255),
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- Books
CREATE TABLE books (
    id           UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    isbn_13      VARCHAR(13)  UNIQUE,
    title        VARCHAR(300) NOT NULL,
    subtitle     VARCHAR(300),
    publisher    VARCHAR(200),
    edition      VARCHAR(40),
    publish_year SMALLINT,
    language     VARCHAR(5),
    pages        SMALLINT,
    synopsis     TEXT,
    cover_url    VARCHAR(500),
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- Book-Author M2M
CREATE TABLE book_authors (
    book_id   UUID NOT NULL REFERENCES books(id),
    author_id UUID NOT NULL REFERENCES authors(id),
    PRIMARY KEY (book_id, author_id)
);

-- Book-Category M2M
CREATE TABLE book_categories (
    book_id     UUID NOT NULL REFERENCES books(id),
    category_id UUID NOT NULL REFERENCES categories(id),
    PRIMARY KEY (book_id, category_id)
);

-- Copies
CREATE TABLE copies (
    id               UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    book_id          UUID        NOT NULL REFERENCES books(id),
    barcode          VARCHAR(40) NOT NULL UNIQUE,
    status           VARCHAR(20) NOT NULL,
    acquisition_date DATE,
    discard_date     DATE,
    discard_reason   TEXT,
    notes            TEXT,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Loans
CREATE TABLE loans (
    id            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    copy_id       UUID        NOT NULL REFERENCES copies(id),
    user_id       UUID        NOT NULL REFERENCES readers(id),
    loaned_at     TIMESTAMPTZ NOT NULL,
    due_date      DATE        NOT NULL,
    returned_at   TIMESTAMPTZ,
    renewed_at    TIMESTAMPTZ,
    status        VARCHAR(20) NOT NULL,
    registered_by UUID        NOT NULL
);

-- Fines
CREATE TABLE fines (
    id           UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_id      UUID          NOT NULL REFERENCES loans(id),
    user_id      UUID          NOT NULL REFERENCES readers(id),
    days_overdue SMALLINT      NOT NULL,
    amount       NUMERIC(8, 2) NOT NULL,
    paid_at      TIMESTAMPTZ,
    waived_at    TIMESTAMPTZ,
    created_at   TIMESTAMPTZ   NOT NULL DEFAULT now()
);

-- Reservations
CREATE TABLE reservations (
    id             UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    book_id        UUID        NOT NULL REFERENCES books(id),
    user_id        UUID        NOT NULL REFERENCES readers(id),
    status         VARCHAR(20) NOT NULL,
    queue_position SMALLINT    NOT NULL,
    ready_at       TIMESTAMPTZ,
    expires_at     TIMESTAMPTZ,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Indexes
CREATE INDEX idx_loans_reader_status     ON loans(user_id, status);
CREATE INDEX idx_loans_due_date_status   ON loans(due_date, status);
CREATE INDEX idx_fines_reader            ON fines(user_id);
CREATE INDEX idx_fines_loan              ON fines(loan_id);
CREATE INDEX idx_reservations_reader     ON reservations(user_id, status);
CREATE INDEX idx_reservations_book_queue ON reservations(book_id, queue_position);
CREATE INDEX idx_copies_book_status      ON copies(book_id, status);
