create table kyc_entries(
  id bigint(40) primary key AUTO_INCREMENT,
  address varchar(2000) default null,
  referral_key varchar(2000) default null,
  referred_by varchar(191) default null,
  status varchar(2000) default null,
  message text default null
);

create index idx_referred_by_kyc_entries on kyc_entries(referred_by);