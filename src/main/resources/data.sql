INSERT INTO role(id, name)
VALUES (1, 'ACTIVE_USER');
INSERT INTO role(id, name)
VALUES (2, 'BLOCK_USER');
INSERT INTO role(id, name)
VALUES (3, 'SERVICE_ADMIN');

INSERT INTO Member(is_delete, role_id, email, member_name, nickname, password, uuid)
VALUES (false, 3, 'admin@admin.admin', 'park','admin park', '$2a$10$.f.TCAvMWWL2m5VV8GHxOOcJSjLkYW1CKA8E2EgA.rYxRCOeFQJ8a',
        'f5583164-825a-4c59-99e8-3e4d31a0cc1a')