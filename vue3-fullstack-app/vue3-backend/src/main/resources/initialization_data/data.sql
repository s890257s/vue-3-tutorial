INSERT INTO [member] (
        [is_admin],
        [email],
        [password],
        [member_name]
    )
VALUES (
        1,
        'alice@example.com',
        '1234',
        N'愛麗絲'
    ),
    (
        0,
        'bob@example.com',
        '1234',
        N'鮑伯'
    ),
    (
        0,
        'charlie@example.com',
        '1234',
        N'查理'
    ),
    (
        0,
        'dave@example.com',
        '1234',
        N'大衛'
    ),
    (
        0,
        'eve@example.com',
        '1234',
        N'伊芙'
    ),
    (
        0,
        'zoe@example.com',
        '1234',
        N'柔伊'
    );
INSERT INTO [product] ([product_name], [price], [color])
VALUES ('asus ROG Phone 9', 39900, N'黑色'),
    ('asus ROG Phone 9 Pro Edition', 45990, N'黑色'),
    ('asus Zenfone 12 Ultra', 29900, N'黑色'),
    ('asus Zenfone 12 Ultra', 29900, N'綠色'),
    ('asus Zenfone 12 Ultra', 29900, N'粉色'),
    ('google Pixel 9a', 16490, N'黑色'),
    ('google Pixel 9a', 16490, N'藍色'),
    ('google Pixel 9a', 16490, N'粉色'),
    ('google Pixel 9a', 16490, N'白色'),
    ('google Pixel 9 pro', 36490, N'黑色'),
    ('google Pixel 9 pro', 36490, N'灰色'),
    ('google Pixel 9 pro', 36490, N'粉色'),
    ('google Pixel 9 pro', 36490, N'白色'),
    ('google Pixel Pro Fold', 56990, N'黑色'),
    ('google Pixel Pro Fold', 56990, N'白色'),
    ('iphone 15 plus', 29900, N'藍色'),
    ('iphone 15 plus', 29900, N'綠色'),
    ('iphone 15 plus', 29900, N'粉色'),
    ('iphone 15 plus', 29900, N'黃色'),
    ('iphone 16e', 21900, N'白色'),
    ('iphone 16 pro max', 44900, N'黑色'),
    ('iphone 16 pro max', 44900, N'灰色'),
    ('iphone 16 pro max', 44900, N'銀色'),
    ('iphone 16 pro max', 44900, N'白色'),
    ('samsang S25 Edge', 37900, N'黑色'),
    ('samsang S25 Edge', 37900, N'藍色'),
    ('samsang S25 Edge', 37900, N'銀色'),
    ('samsang S25 Ultra', 47900, N'黑色'),
    ('samsang S25 Ultra', 47900, N'藍色'),
    ('samsang S25 Ultra', 47900, N'綠色'),
    ('samsang S25 Ultra', 47900, N'灰色'),
    ('samsang S25 Ultra', 47900, N'粉色'),
    ('samsang S25 Ultra', 47900, N'白色'),
    ('Xiaomi 14T', 12999, N'黑色'),
    ('Xiaomi 14T', 12999, N'藍色'),
    ('Xiaomi 14T', 12999, N'綠色'),
    ('Xiaomi 15 Ultra', 32999, N'黑色'),
    ('Xiaomi 15 Ultra', 32999, N'白色');