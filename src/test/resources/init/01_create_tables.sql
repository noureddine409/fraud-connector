CREATE TABLE IF NOT EXISTS public.event_logs
(
    id
    BIGINT
    PRIMARY
    KEY,
    actor
    VARCHAR
(
    255
),
    classname VARCHAR
(
    255
),
    codebanqueassocie VARCHAR
(
    255
),
    codepaysassocie VARCHAR
(
    255
),
    datecreated text,
    eventname VARCHAR
(
    255
),
    ipaddress VARCHAR
(
    255
),
    lastupdated text,
    newvalue TEXT,
    oldvalue TEXT,
    persistedobjectid INTEGER,
    persistedobjectversion INTEGER,
    plateforme VARCHAR
(
    255
),
    propertyname VARCHAR
(
    255
),
    uri TEXT,
    ref1 TEXT,
    ref2 TEXT,
    ref3 TEXT,
    ipaddress2 VARCHAR
(
    255
),
    motif TEXT,
    mac_address VARCHAR
(
    255
),
    activitytime text,
    contrat_id VARCHAR
(
    255
),
    xforwarded VARCHAR
(
    255
)
    )
