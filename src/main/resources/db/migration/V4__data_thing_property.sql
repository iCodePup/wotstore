-- Load
INSERT INTO thing_property(name, value, thingid, metadataasjson)
VALUES ('OnOffProperty',
        'false',
        1,
        '{"unit":"boolean","@type":"OnOffProperty","description":"The current status (on/off)","readOnly":false,"title":"OnOff","type":"boolean"}');


INSERT INTO thing_property(name, value, thingid, metadataasjson)
VALUES ('OnOffProperty',
        'false',
        2,
        '{"unit":"boolean","@type":"OnOffProperty","description":"The current status (on/off)","readOnly":false,"title":"OnOff","type":"boolean"}');

INSERT INTO thing_property(name, value, thingid, metadataasjson)
VALUES ('TemperatureProperty',
        '20',
        3,
        '{"unit":"integer","@type":"TemperatureProperty","description":"The current temperature in celcius", "readOnly":false,"title":"Temperature","type":"integer"}');

INSERT INTO thing_property(name, value, thingid, metadataasjson)
VALUES ('TargetTemperatureProperty',
        '15',
        3,
        '{"unit":"integer","@type":"TargetTemperatureProperty","description":"The target temperature in celcius", "readOnly":false,"title":"Target Temperature","type":"integer"}');

INSERT INTO thing_property(name, value, thingid, metadataasjson)
VALUES ('TemperatureProperty',
        '20',
        4,
        '{"unit":"integer","@type":"TemperatureProperty","description":"The current temperature in celcius", "readOnly":false,"title":"Temperature","type":"integer"}');

INSERT INTO thing_property(name, value, thingid, metadataasjson)
VALUES ('MotionProperty',
        '20',
        5,
        '{"unit":"boolean","@type":"MotionProperty","description":"The current motion status", "readOnly":false,"title":"Motion","type":"boolean"}');