insert into eventlog (
    eventlog_id,
    eventlogtype_id,
    operation,
    request,
    response,
    commentary,
    date,
    username,
    ip
) values (
    :eventlog_id,
    :eventlogtype_id,
    :operation,
    to_jsonb(:request),
    to_jsonb(:response),
    :commentary,
    :date,
    :username,
    inet(:ip)
)