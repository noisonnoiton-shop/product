--sequence
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;

INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Esprit Ruffle Shirt', 1, 'active', 100000, 80000, 5, 4000, 76000, 'images/product-01.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Herschel supply', 1, 'active', 100000, 100000, 10, 10000, 90000, 'images/product-02.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Only Check Trouser', 2, 'active', 100000, 80000, 5, 4000, 76000, 'images/product-03.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Classic Trench Coat', 1, 'active', 100000, 200000, 20, 40000, 160000, 'images/product-04.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Front Pocket Jumper', 1, 'active', 100000, 100000, 10, 10000, 90000, 'images/product-05.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Vintage Inspired Classic', 5, 'active', 100000, 500000, 10, 50000, 450000, 'images/product-06.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Shirt in Stretch Cotton', 1, 'active', 100000, 80000, 5, 4000, 76000, 'images/product-07.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Pieces Metallic Printed', 1, 'active', 100000, 100000, 5, 5000, 95000, 'images/product-08.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Converse All Star Hi Plimsolls', 4, 'active', 100000, 50000, 5, 2500, 47500, 'images/product-09.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Femme T-Shirt In Stripe', 1, 'active', 100000, 80000, 5, 4000, 76000, 'images/product-10.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Herschel supply', 2, 'active', 100000, 80000, 5, 4000, 76000, 'images/product-11.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Herschel belt', 2, 'active', 100000, 80000, 10, 8000, 72000, 'images/product-12.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('T-Shirt with Sleeve', 1, 'active', 100000, 100000, 10, 10000, 90000, 'images/product-13.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Pretty Little Thing', 1, 'active', 100000, 80000, 5, 4000, 76000, 'images/product-14.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Mini Silver Mesh Watch', 5, 'active', 100000, 1000000, 10, 100000, 900000, 'images/product-15.jpg', NOW());
INSERT INTO product (name, category_id, active, amount, original_price, sale_percentage, sale_price, result_price, img, created_at) VALUES
('Square Neck Back', 1, 'active', 100000, 100000, 10, 10000, 90000, 'images/product-16.jpg', NOW());

INSERT INTO product_event
(id, product_id, domain, event_type, payload, tx_id, created_at)
VALUES
(1,1,'product'
, 'ProductAmountAdded'
, '{ 
	"id": 1
  	, "name": "Esprit Ruffle Shirt"
  	, "categoryName": "women"
  	, "active": "active"
  	, "amount": 100
  	, "originalPrice": 80000
  	, "salePercentage": 5
  	, "salePrice": 4000
  	, "resultPrice": 76000
  	, "img": "images/product-01.jpg"
   }'
, 'tx123123125'
, NOW());

INSERT INTO category (name, priority, active, created_at) VALUES
('women', 1, 'active', NOW());
INSERT INTO category (name, priority, active, created_at) VALUES
('men', 2, 'active', NOW());
INSERT INTO category (name, priority, active, created_at) VALUES
('bag', 3, 'active', NOW());
INSERT INTO category (name, priority, active, created_at) VALUES
('shoes', 4, 'active', NOW());
INSERT INTO category (name, priority, active, created_at) VALUES
('watches', 5, 'active', NOW());
  
INSERT INTO category_event (id, category_id, domain, event_type, payload, tx_id, created_at)
VALUES
(1,1,'product','CategoryCreated'
, '{
	"id": 1
	, "name": "women"
	, "priority": 1
   }'
, 'tx123123126' 
, NOW());
INSERT INTO category_event (id, category_id, domain, event_type, payload, tx_id, created_at)
VALUES
(2,2,'product','CategoryCreated'
, '{
	"id": 2
	, "name": "men"
	, "priority": 2
   }'
, 'tx123123126' 
, NOW());
INSERT INTO category_event (id, category_id, domain, event_type, payload, tx_id, created_at)
VALUES
(3,3,'product','CategoryCreated'
, '{
	"id": 3
	, "name": "bag"
	, "priority": 3
   }'
, 'tx123123126' 
, NOW());
INSERT INTO category_event (id, category_id, domain, event_type, payload, tx_id, created_at)
VALUES
(4,4,'product','CategoryCreated'
, '{
	"id": 4
	, "name": "shoes"
	, "priority": 4
   }'
, 'tx123123126' 
, NOW());
INSERT INTO category_event (id, category_id, domain, event_type, payload, tx_id, created_at)
VALUES
(5,5,'product','CategoryCreated'
, '{
	"id": 5
	, "name": "watches"
	, "priority": 5
   }'
, 'tx123123126' 
, NOW());