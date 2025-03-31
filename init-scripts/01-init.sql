-- Asegúrate de tener instalada la extensión uuid-ossp para UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabla de Mangas
CREATE TABLE mangas (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        title VARCHAR(255) NOT NULL,
                        author VARCHAR(255),
                        description TEXT,
                        chapters_count INT DEFAULT 0,
                        volumes_count INT DEFAULT 0,
                        favicon_url VARCHAR(255),
                        cover_url VARCHAR(255),
                        rating DOUBLE PRECISION,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Volúmenes
CREATE TABLE volumes (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         manga_id UUID NOT NULL,
                         volume_number INT NOT NULL,
                         title VARCHAR(255),
                         cover_url VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (manga_id) REFERENCES mangas(id) ON DELETE CASCADE
);

-- Tabla de Capítulos
CREATE TABLE chapters (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          volume_id UUID NOT NULL,
                          chapter_number VARCHAR(50) NOT NULL,
                          title VARCHAR(255),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (volume_id) REFERENCES volumes(id) ON DELETE CASCADE
);

-- Tabla de Proveedores (Providers)
CREATE TABLE providers (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           provider_name VARCHAR(100) NOT NULL,
                           provided_lang VARCHAR(10) NOT NULL,
                           logo_url VARCHAR(255),
                           is_active BOOLEAN DEFAULT TRUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Tabla de Fuentes de Capítulo (ChapterSource)
CREATE TABLE chapter_sources (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 chapter_id UUID NOT NULL,
                                 provider_id UUID NOT NULL,
                                 language_code VARCHAR(10) NOT NULL,
                                 is_active BOOLEAN DEFAULT TRUE,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (chapter_id) REFERENCES chapters(id) ON DELETE CASCADE,
                                 FOREIGN KEY (provider_id) REFERENCES providers(id) ON DELETE CASCADE
);

-- Tabla de Páginas
CREATE TABLE pages (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       chapter_source_id UUID NOT NULL,
                       page_number INT NOT NULL,
                       image_url VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (chapter_source_id) REFERENCES chapter_sources(id) ON DELETE CASCADE
);

-- Función y Trigger para contar capítulos por manga
CREATE OR REPLACE FUNCTION update_chapters_count()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE mangas
    SET chapters_count = (
        SELECT COUNT(*) FROM chapters c
                                 JOIN volumes v ON c.volume_id = v.id
        WHERE v.manga_id = (SELECT manga_id FROM volumes WHERE id = NEW.volume_id)
    ),
        updated_at = CURRENT_TIMESTAMP
    WHERE id = (SELECT manga_id FROM volumes WHERE id = NEW.volume_id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER chapters_after_insert
    AFTER INSERT ON chapters
    FOR EACH ROW
EXECUTE FUNCTION update_chapters_count();

CREATE TRIGGER chapters_after_delete
    AFTER DELETE ON chapters
    FOR EACH ROW
EXECUTE FUNCTION update_chapters_count();

-- Función y Trigger para contar volúmenes por manga
CREATE OR REPLACE FUNCTION update_volumes_count()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE mangas
    SET volumes_count = (
        SELECT COUNT(*) FROM volumes WHERE manga_id = NEW.manga_id
    ),
        updated_at = CURRENT_TIMESTAMP
    WHERE id = NEW.manga_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER volumes_after_insert
    AFTER INSERT ON volumes
    FOR EACH ROW
EXECUTE FUNCTION update_volumes_count();

CREATE TRIGGER volumes_after_delete
    AFTER DELETE ON volumes
    FOR EACH ROW
EXECUTE FUNCTION update_volumes_count();

-- Índices recomendados
CREATE INDEX idx_volumes_manga_id ON volumes(manga_id);
CREATE INDEX idx_chapters_volume_id ON chapters(volume_id);
CREATE INDEX idx_chapter_sources_chapter_id ON chapter_sources(chapter_id);
CREATE INDEX idx_chapter_sources_provider_id ON chapter_sources(provider_id);
CREATE INDEX idx_pages_chapter_source_id ON pages(chapter_source_id);
