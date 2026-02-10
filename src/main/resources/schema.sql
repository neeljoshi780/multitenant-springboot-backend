-- =========================================================
-- MASTER TABLE: TENANTS
-- Stores company-level information for multi-tenant system
-- Each record represents one registered organization
-- =========================================================
CREATE TABLE IF NOT EXISTS tenants (
    id BINARY(16) NOT NULL,

    company_code VARCHAR(50) NOT NULL UNIQUE,
    company_name VARCHAR(150) NOT NULL,
    company_email VARCHAR(150) NOT NULL UNIQUE,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
);

-- =========================================================
-- MASTER TABLE: TENANT_DB_CONFIG
-- Stores physical database configuration for each tenant
-- Used for dynamic datasource routing
-- =========================================================
CREATE TABLE IF NOT EXISTS tenant_db_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    tenant_id BINARY(16) NOT NULL UNIQUE,

    db_name VARCHAR(150) NOT NULL UNIQUE,
    db_host VARCHAR(100) NOT NULL,
    db_port INT NOT NULL,
    db_username VARCHAR(100),
    db_password VARCHAR(255),

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_tenant_db_config_tenant
    FOREIGN KEY (tenant_id)
    REFERENCES tenants(id)
    ON DELETE CASCADE
);