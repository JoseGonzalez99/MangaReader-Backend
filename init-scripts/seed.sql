-- Limpieza (opcional si es una base en blanco)
TRUNCATE pages, chapter_sources, chapters, volumes, mangas, providers CASCADE;

-- 👉 Manga
INSERT INTO mangas (id, title, author, description, created_at, updated_at)
VALUES (
           '11111111-1111-1111-1111-111111111111',
           'One Punch Man',
           'ONE',
           'Un héroe que derrota a todos de un solo golpe.',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

-- 👉 Volumen
INSERT INTO volumes (id, manga_id, volume_number, title, created_at, updated_at)
VALUES (
           '22222222-2222-2222-2222-222222222222',
           '11111111-1111-1111-1111-111111111111',
           1,
           'Volumen 1',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

-- 👉 Capítulo
INSERT INTO chapters (id, volume_id, chapter_number, title, created_at, updated_at)
VALUES (
           '33333333-3333-3333-3333-333333333333',
           '22222222-2222-2222-2222-222222222222',
           '1',
           'El Comienzo',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

-- 👉 Proveedores
INSERT INTO providers (id, provider_name, provided_lang, logo_url, is_active, created_at, updated_at)
VALUES
    ('44444444-4444-4444-4444-444444444444', 'MangaDex', 'es', 'https://mangadex.org/logo.png', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('55555555-5555-5555-5555-555555555555', 'MangaSee', 'en', 'https://mangasee.com/logo.png', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 👉 Fuentes de capítulo (ChapterSource)
INSERT INTO chapter_sources (id, chapter_id, provider_id, language_code, is_active, created_at, updated_at)
VALUES
    ('66666666-6666-6666-6666-666666666666', '33333333-3333-3333-3333-333333333333', '44444444-4444-4444-4444-444444444444', 'es', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('77777777-7777-7777-7777-777777777777', '33333333-3333-3333-3333-333333333333', '55555555-5555-5555-5555-555555555555', 'en', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 👉 Páginas para cada fuente
-- Español
INSERT INTO pages (id, chapter_source_id, page_number, image_url, created_at, updated_at)
VALUES
    (uuid_generate_v4(), '66666666-6666-6666-6666-666666666666', 1, 'https://cdn.manga.com/onepunch/es/1.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), '66666666-6666-6666-6666-666666666666', 2, 'https://cdn.manga.com/onepunch/es/2.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), '66666666-6666-6666-6666-666666666666', 3, 'https://cdn.manga.com/onepunch/es/3.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inglés
INSERT INTO pages (id, chapter_source_id, page_number, image_url, created_at, updated_at)
VALUES
    (uuid_generate_v4(), '77777777-7777-7777-7777-777777777777', 1, 'https://cdn.manga.com/onepunch/en/1.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), '77777777-7777-7777-7777-777777777777', 2, 'https://cdn.manga.com/onepunch/en/2.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), '77777777-7777-7777-7777-777777777777', 3, 'https://cdn.manga.com/onepunch/en/3.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
