drop table if exists lvg_asset cascade;
drop table if exists lvg_keyword cascade;
drop table if exists lvg_property cascade;
drop table if exists lvg_category cascade;
drop table if exists lvg_gallery cascade;
drop table if exists lvg_metric cascade;
drop table if exists users cascade;
drop table if exists lvg_search cascade;
drop table if exists lvg_view cascade;
drop table if exists authorities cascade;
drop table if exists lvg_property_allowed_values cascade;
drop table if exists lvg_category_classifying_asset cascade;
drop table if exists lvg_gallery_contains_asset cascade;
drop table if exists lvg_metric_collected_for_asset cascade;
drop table if exists lvg_asset_has_property cascade;
drop table if exists lvg_user_rated_asset cascade;
drop table if exists lvg_user_submitted_search cascade;
drop table if exists lvg_user_selected_view cascade;
drop table if exists lvg_view_contains_search cascade;
drop table if exists lvg_keyword_describing_asset cascade;


create table lvg_asset (id varchar(52) primary key, name varchar(255), location varchar(1024) not null, thumbnail_location varchar(1024) not null, description text);
create table lvg_keyword (id varchar(52) primary key, name varchar(255) not null, description text);
create table lvg_property (id varchar(52) primary key, name varchar(255) not null, description text);
create table lvg_category (id varchar(52) primary key, name varchar(255) not null, description text);
create table lvg_gallery (id varchar(52) primary key, name varchar(255) not null, description text);
create table lvg_metric (id varchar(52) primary key, name varchar(255) not null, description text);
create table users (username varchar not null primary key, password varchar not null, enabled boolean not null, family_name varchar(255) not null, first_name varchar(255) not null);
create table lvg_search (id varchar(52) primary key, category varchar(255), keyword varchar(255), rating int, property varchar(255));
create table lvg_view (id varchar(52) primary key, name varchar(255) not null);
create table authorities (username varchar not null, authority varchar(50) not null, constraint fk_authorities_users foreign key(username) references users(username));

create table lvg_property_allowed_values (id varchar(52) primary key, property_id varchar(52) references lvg_property (id), name varchar(255) not null);


create table lvg_category_classifying_asset (category_id varchar(52) references lvg_category(id) , asset_id varchar(52) references lvg_asset(id));
create table lvg_keyword_describing_asset (keyword_id varchar(52) references lvg_keyword(id) , asset_id varchar(52) references lvg_asset(id));
create table lvg_gallery_contains_asset (gallery_id varchar(52) references lvg_gallery(id) , asset_id varchar(52) references lvg_asset(id));
create table lvg_metric_collected_for_asset (metric_id varchar(52) references lvg_metric(id), asset_id varchar(52) references lvg_asset(id), cummulated_value float8 not null);
create table lvg_asset_has_property (asset_id varchar(52) references lvg_asset(id), property_id varchar(52) references lvg_property(id), value_id varchar(52) references lvg_property_allowed_values(id));
create table lvg_user_rated_asset (username varchar not null references users(username), asset_id varchar(52) references lvg_asset(id), rating varchar not null);
create table lvg_user_submitted_search (username varchar not null references users(username), search_id varchar(52) references lvg_search(id));
create table lvg_user_selected_view (username varchar not null references users(username), view_id varchar(52) references lvg_view(id));
create table lvg_view_contains_search (username varchar not null references users(username), search_id varchar(52) references lvg_search(id));


create index lvg_asset_index on lvg_asset(name);
create index lvg_keyword_index on lvg_keyword(name);
create index lvg_property_index on lvg_property(name);
create index lvg_category_index on lvg_category(name);
create index lvg_gallery_index on lvg_gallery(name);
create index lvg_metric_index on lvg_metric(name);
create index lvg_user_index on users(family_name, first_name);
create index ix_auth_username on authorities(username,authority);


-- create user rudi with password='pass' (bcrypt hash) http://bcrypthashgenerator.apphb.com/
insert into users values('lvg_admin','$2a$10$.HSlHUspYBdMwTP652Za4u7LfN5TcZE6b.AEuY1XrwK1WdsBLgkhO',true,'Sonnenfeldt','Injuquaq');
insert into authorities values('lvg_admin','ROLE_USER');
insert into authorities values('lvg_admin','ROLE_ADMIN');

-- create user john with password='pass'
insert into users values('rudi','$2a$10$.HSlHUspYBdMwTP652Za4u7LfN5TcZE6b.AEuY1XrwK1WdsBLgkhO',true,'Schulze','Ruediger');
insert into authorities values('rudi','ROLE_USER');