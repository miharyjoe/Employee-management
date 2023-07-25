CREATE TABLE IF NOT EXISTS employee_telephones (
                                                   employee_id BIGINT,
                                                   telephones VARCHAR,
                                                   FOREIGN KEY (employee_id) REFERENCES employee(id)
    );
ALTER TABLE employee_telephones
    ADD CONSTRAINT uq_employee_telephones_telephones UNIQUE (telephones);
ALTER TABLE employee_telephones
    ADD CONSTRAINT fk_employee_telephones_employee_id FOREIGN KEY (employee_id) REFERENCES employee(id);