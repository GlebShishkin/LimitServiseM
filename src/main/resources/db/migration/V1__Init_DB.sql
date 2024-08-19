create table user_limits (
    userid int8 not null,
    limit_sum  numeric not null,
    primary key (userid)
);