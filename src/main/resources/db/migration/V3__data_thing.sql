-- Load
INSERT INTO thing_type(id, title, description, typeasjson)
VALUES (1, 'Lampe',
        'Lampe connectée', '["Light"]');


INSERT INTO thing_type(id, title, description, typeasjson)
VALUES (2, 'Interrupteur',
        'Interrupteur connecté', '["OnOffSwitch"]');


INSERT INTO thing_type(id, title, description, typeasjson)
VALUES (3, 'Thermostat',
        'Thermostat connecté', '["Thermostat"]');

INSERT INTO thing_type(id, title, description, typeasjson)
VALUES (4, 'Capteur de température',
        'Capteur de température connecté', '["TemperatureSensor"]');

INSERT INTO thing_type(id, title, description, typeasjson)
VALUES (5, 'Capteur de mouvement',
        'Capteur de mouvement', '["MotionSensor"]');