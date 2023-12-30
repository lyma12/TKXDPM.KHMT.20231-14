create database aims;

use aims;
begin;


create table Media(
	id integer primary key auto_increment not null,
    type set('book', 'cd', 'dvd') not null,
    category varchar(45) not null,
    price integer not null,
    quantity integer not null,
    title varchar(45) not null,
    value integer not null,
    imageUrl varchar(45) not null,
    barcode varchar(45),
    ngay_nhat_kho date,
    size integer,
    description varchar(200),
    weight float not null,
    support_rush_order boolean not null
);

create table CD(
	id integer primary key not null,
    artist varchar(45) not null,
    recordLabel varchar(45) not null,
    type varchar(45) not null,
    releaseDate date not null,
    tracklist varchar(45) not null,
    constraint fk_CD_Media1
		foreign key(id)
        references Media(id)
);

create table Book(
	id integer primary key not null,
    author varchar(45) not null,
    type varchar(45) not null,
    paperType varchar(45) not null,
    publisher varchar(45) not null,
    papes integer not null,
    language varchar(45) not null,
    publiction_data date not null,
    constraint fk_Book_Media
		foreign key(id)
        references Media(id)
);

create table DVD(
	id integer primary key not null,
    director varchar(45) not null,
    studio varchar(200),
    type varchar(45) not null,
    typeDisc varchar(45) not null,
    releaseDate date not null,
    language varchar(45) not null,
    subtitle varchar(45) not null,
    constraint fk_DVD_Media1
		foreign key(id)
        references Media(id)
);



create table DeleveryInfo(
	id integer primary key auto_increment not null,
    name varchar(45) not null,
    province varchar(45) not null,
    instructions varchar(200),
    address varchar(100)
);

create table Card(
	id integer primary key not null,
    cardCode varchar(15) not null,
    owner varchar(45) NOT NULL,
    cvvCode varchar(3) not null,
    dateExpired varchar(4) not null
);


create table aims.Order(
	id integer not null,
    shippingFees varchar(45),
    deleveryInfoId integer not null,
    primary key(id, deleveryInfoId),
    accept boolean not null,
    constraint fk_Order_DeleveryInfo1
		foreign key(deleveryInfoId)
        references deleveryInfo(id)
);
CREATE INDEX fk_Order_deleveryInfo1_idx ON aims.Order(deleveryInfoId); 

create table OrderMedia(
	orderID integer not null,
    price integer not null,
    quantity integer not null,
    mediaId integer not null,
    primary key(orderID, mediaId),
    constraint fk_ordermedia_order
		foreign key(orderID)
        references aims.Order(id),
	constraint fk_OrderMedia_Media1
		foreign key(mediaID)
        references Media(id)
);

create index fk_ordermedia_order_idx on aims.OrderMedia(orderID);
create index fk_OrderMedia_Media1_idx on OrderMedia(mediaId);

create table Invoice(
	id integer primary key not null,
    totalAmount integer not null,
    orderId integer not null,
    constraint fk_Invoice_Order1
		foreign key(orderId)
        references aims.Order(id)
);

create index fk_Invoice_Order1_idx on Invoice(orderId);

create table PaymentTransaction(
	id integer not null,
    createAt datetime not null,
    content varchar(45) not null,
    method varchar(45),
    cardId integer not null,
    invoiceId integer not null,
    primary key(id, cardId, invoiceId),
    constraint fk_PamentTransaction_Card1
		foreign key (cardId)
        references Card(id),
	constraint fk_pamentTransaction_Invoice1
		foreign key(invoiceId)
        references Invoice(id)
);


create index fk_PaymentTransaction_Card1_idx on PaymentTransaction(cardId);
create index fk_PaymentTransaction_Invoice1_idx on PaymentTransaction(invoiceId);

CREATE TABLE User (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(255) NOT NULL,
  salt VARCHAR(16) NOT NULL,
  role set('admin', 'product_manager', 'both') NOT NULL,
  address VARCHAR(100),
  email VARCHAR(255) NOT NULL UNIQUE,
  ho_ten VARCHAR(45) NOT NULL,
  phone VARCHAR(10),
);


create table Log_product_action(
    id_user integer not null,
    id_media integer not null,
    details text,
    action enum('add', 'delete', 'update') not null,
    primary key (id_user, id_media),
    constraint fk_Log_User
		foreign key (id_user)
        references User(id),
    constraint fk_Log_Media
		foreign key (id_media)
        references Media(id)
);

create index fk_Log_Media_idx on Log_product_action(id_media);
commit;