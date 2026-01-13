DROP TABLE IF EXISTS [cart_item], [member], [product];

CREATE TABLE [member] (
    [member_id] INT IDENTITY(1,1) PRIMARY KEY,
    [is_admin] BIT DEFAULT 0,
    [email] NVARCHAR(255),
    [password] NVARCHAR(255),
    [member_name] NVARCHAR(255),
    [member_photo] VARBINARY(MAX),
);

CREATE TABLE product (
    [product_id] INT IDENTITY(1,1) PRIMARY KEY,
    [product_name] NVARCHAR(255),
    [price] INT,
    [color] NVARCHAR(255),
    [product_photo] VARBINARY(MAX)
);

CREATE TABLE cart_item (
    [cart_item_id] INT IDENTITY(1,1) PRIMARY KEY,
    [member_id] INT NOT NULL,
    [product_id] INT NOT NULL,
    [quantity] INT,
    FOREIGN KEY ([member_id]) REFERENCES [member]([member_id]),
    FOREIGN KEY ([product_id]) REFERENCES [product]([product_id])
);