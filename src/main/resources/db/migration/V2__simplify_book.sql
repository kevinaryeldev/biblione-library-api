-- Drop M2M join tables (old references to entity tables)
DROP TABLE IF EXISTS book_authors;
DROP TABLE IF EXISTS book_categories;

-- Drop standalone entity tables
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS categories;

-- Remove synopsis column from books
ALTER TABLE books DROP COLUMN IF EXISTS synopsis;

-- New element-collection tables (stored directly in book)
CREATE TABLE book_authors (
    book_id     UUID         NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    author_name VARCHAR(200) NOT NULL,
    PRIMARY KEY (book_id, author_name)
);

CREATE TABLE book_categories (
    book_id       UUID        NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    category_name VARCHAR(80) NOT NULL,
    PRIMARY KEY (book_id, category_name)
);
