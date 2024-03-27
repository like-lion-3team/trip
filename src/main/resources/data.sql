INSERT INTO role(id, name)
VALUES (1, 'ACTIVE_USER');
INSERT INTO role(id, name)
VALUES (2, 'BLOCK_USER');
INSERT INTO role(id, name)
VALUES (3, 'SERVICE_ADMIN');

INSERT INTO Member(is_delete, role_id, email, member_name, nickname, password, uuid)
VALUES (false, 3, 'admin@admin.admin', 'park','admin park', '$2a$10$.f.TCAvMWWL2m5VV8GHxOOcJSjLkYW1CKA8E2EgA.rYxRCOeFQJ8a',
        'f5583164-825a-4c59-99e8-3e4d31a0cc1a');

INSERT INTO Member(is_delete, role_id, email, member_name, nickname, password, uuid) VALUES (false, 3, 'janderson@yahoo.com', 'Scott Lopez', 'hpowell', '$2a$10$.f.TCAvMWWL2m5VV8GHxOOcJSjLkYW1CKA8E2EgA.rYxRCOeFQJ8a', '5eb24c70-cae3-4d03-a39f-dfca5a46e573');
INSERT INTO Member(is_delete, role_id, email, member_name, nickname, password, uuid) VALUES (false, 3, 'vharmon@yahoo.com', 'Holly Fowler', 'nperry', '$2a$10$.f.TCAvMWWL2m5VV8GHxOOcJSjLkYW1CKA8E2EgA.rYxRCOeFQJ8a', 'df24da56-b78b-4da4-a391-988852b057b3');
INSERT INTO Member(is_delete, role_id, email, member_name, nickname, password, uuid) VALUES (false, 3, 'jacobmaynard@holland.com', 'Kristina Tyler', 'susanrivas', '$2a$10$.f.TCAvMWWL2m5VV8GHxOOcJSjLkYW1CKA8E2EgA.rYxRCOeFQJ8a', 'e44c50ff-8043-4207-b827-c9c9e55db06d');
INSERT INTO Member(is_delete, role_id, email, member_name, nickname, password, uuid) VALUES (false, 1, 'gallowaydaniel@jones.biz', 'Edward Ford', 'lopezjesse', '$2a$10$.f.TCAvMWWL2m5VV8GHxOOcJSjLkYW1CKA8E2EgA.rYxRCOeFQJ8a', 'cb8eb061-4ad6-465c-a0e5-65b5ab2bfa5b');
INSERT INTO Member(is_delete, role_id, email, member_name, nickname, password, uuid) VALUES (false, 3, 'jjones@mcneil-brennan.com', 'Anthony Long', 'kbryant', '$2a$10$.f.TCAvMWWL2m5VV8GHxOOcJSjLkYW1CKA8E2EgA.rYxRCOeFQJ8a', '7e1c07d3-c538-4213-aa2e-45d1794017b7');