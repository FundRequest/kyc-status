create table kyc_entries(
  id bigint(40) primary key,
  address varchar(200) default null,
  referral_key varchar(200) default null,
  referred_by varchar(200) default null,
  status varchar(200) default null,
  message text default null
);

create index idx_referred_by_kyc_entries on kyc_entries(referred_by);