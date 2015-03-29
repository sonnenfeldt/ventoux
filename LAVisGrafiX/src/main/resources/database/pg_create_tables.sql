drop table if exists lvg_asset cascade;
drop table if exists lvg_keyword cascade;
drop table if exists lvg_property cascade;
drop table if exists lvg_category cascade;
drop table if exists lvg_gallery cascade;
drop table if exists lvg_metric cascade;
drop table if exists lvg_user cascade;
drop table if exists lvg_search cascade;
drop table if exists lvg_view cascade;
drop table if exists lvg_property_allowed_values cascade;
drop table if exists lvg_category_classifying_asset cascade;
drop table if exists lvg_gallery_contains_asset cascade;
drop table if exists lvg_metric_collected_for_asset cascade;
drop table if exists lvg_asset_has_property cascade;
drop table if exists lvg_user_rated_asset cascade;
drop table if exists lvg_user_submitted_search cascade;
drop table if exists lvg_user_selected_view cascade;
drop table if exists lvg_view_contains_search cascade;


create table lvg_asset (id uuid primary key, name varchar(255), location varchar(1024) not null, thumbnail_location varchar(1024) not null, description text);
create table lvg_keyword (id uuid primary key, name varchar(255) not null, description text);
create table lvg_property (id uuid primary key, name varchar(255) not null, description text);
create table lvg_category (id uuid primary key, name varchar(255) not null, description text);
create table lvg_gallery (id uuid primary key, name varchar(255) not null, description text);
create table lvg_metric (id uuid primary key, name varchar(255) not null, description text);
create table lvg_user (id uuid primary key, family_name varchar(255) not null, first_name varchar(255) not null);
create table lvg_search (id uuid primary key, category varchar(255), keyword varchar(255), rating int, property varchar(255));
create table lvg_view (id uuid primary key, name varchar(255) not null);

create table lvg_property_allowed_values (id uuid primary key, property_id uuid references lvg_property (id), name varchar(255) not null);


create table lvg_category_classifying_asset (category_id uuid references lvg_category(id) , asset_id uuid references lvg_asset(id));
create table lvg_gallery_contains_asset (gallery_id uuid references lvg_gallery(id) , asset_id uuid references lvg_asset(id));
create table lvg_metric_collected_for_asset (metric_id uuid references lvg_metric(id), asset_id uuid references lvg_asset(id), cummulated_value float8 not null);
create table lvg_asset_has_property (asset_id uuid references lvg_asset(id), property_id uuid references lvg_property(id), value_id uuid references lvg_property_allowed_values(id));
create table lvg_user_rated_asset (user_id uuid references lvg_user(id), asset_id uuid references lvg_asset(id), rating int);
create table lvg_user_submitted_search (user_id uuid references lvg_user(id), search_id uuid references lvg_search(id));
create table lvg_user_selected_view (user_id uuid references lvg_user(id), view_id uuid references lvg_view(id));
create table lvg_view_contains_search ( view_id uuid references lvg_view(id), search_id uuid references lvg_search(id));


create index lvg_asset_index on lvg_asset(name);
create index lvg_keyword_index on lvg_keyword(name);
create index lvg_property_index on lvg_property(name);
create index lvg_category_index on lvg_category(name);
create index lvg_gallery_index on lvg_gallery(name);
create index lvg_metric_index on lvg_metric(name);
create index lvg_user_index on lvg_user(family_name, first_name);