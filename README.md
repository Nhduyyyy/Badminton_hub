create database Asss_BadmintonHub;

use Asss_BadmintonHub;

---------------------------------------------------------
-- 1. Tạo bảng Roles
---------------------------------------------------------
CREATE TABLE Roles (
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    RoleName NVARCHAR(50) NOT NULL UNIQUE,
    Description NVARCHAR(255) NULL
);
GO

-- Chèn dữ liệu mẫu cho bảng Roles
INSERT INTO Roles (RoleName, Description)
VALUES
    ('Admin', 'Quan tri he thong'),
    ('Manager', 'Quan ly chung, nhan vien'),
    ('User', 'Nguoi dung thong thuong');
GO

---------------------------------------------------------
-- 2. Tạo bảng Users
---------------------------------------------------------
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    RoleId INT NOT NULL,
    Username NVARCHAR(50) NOT NULL UNIQUE,
    Password NVARCHAR(255) NOT NULL,
    Email NVARCHAR(100) NOT NULL UNIQUE,
    FullName NVARCHAR(100) NOT NULL,
    PhoneNumber NVARCHAR(20) NULL,
    Avatar NVARCHAR(255) NULL,
    Score INT DEFAULT 0,
    Sex NVARCHAR(10) NULL,
    BirthDate DATE NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    Locked BIT NOT NULL DEFAULT 0,  -- 0: chưa khóa, 1: đã khóa
    CONSTRAINT FK_User_Role FOREIGN KEY (RoleId) REFERENCES Roles(RoleID)
);
GO

ALTER TABLE Users
ADD resetToken VARCHAR(255) NULL,
    resetTokenExpiry DATETIME NULL;

---------------------------------------------------------
-- Chèn dữ liệu vào bảng Users
---------------------------------------------------------
INSERT INTO Users
    (RoleId, Username, Password, Email, FullName, PhoneNumber, Avatar, Score, Sex, BirthDate, CreatedAt, UpdatedAt, Locked)
VALUES
    (1, 'elonmusk', 'Password@123', 'elonmusk@gmail.com', 'Elon Musk', '0901234567', NULL, 100, 'Nam', '1971-06-28', GETDATE(), GETDATE(), 0),
    (1, 'billgates', 'Microsoft@123', 'billgates@gmail.com', 'Bill Gates', '0912345678', NULL, 95, 'Nam', '1955-10-28', GETDATE(), GETDATE(), 0),
    (1, 'jeffbezos', 'Amazon@456!', 'jeffbezos@gmail.com', 'Jeff Bezos', '0923456789', NULL, 90, 'Nam', '1964-01-12', GETDATE(), GETDATE(), 0),
    (2, 'stevejobs', 'Apple@789!', 'stevejobs@gmail.com', 'Steve Jobs', '0934567890', NULL, 85, 'Nam', '1955-02-24', GETDATE(), GETDATE(), 0),
    (2, 'markzuckerberg', 'Meta@321!', 'markzuckerberg@gmail.com', 'Mark Zuckerberg', '0945678901', NULL, 80, 'Nam', '1984-05-14', GETDATE(), GETDATE(), 0),
    (2, 'sundarpichai', 'Google@654$', 'sundarpichai@gmail.com', 'Sundar Pichai', '0956789012', NULL, 78, 'Nam', '1972-06-10', GETDATE(), GETDATE(), 0),
    (3, 'oprahwinfrey', 'TalkShow@987$', 'oprahwinfrey@gmail.com', 'Oprah Winfrey', '0967890123', NULL, 76, 'Nữ', '1954-01-29', GETDATE(), GETDATE(), 0),
    (3, 'tomhanks', 'Oscar@159$', 'tomhanks@gmail.com', 'Tom Hanks', '0978901234', NULL, 74, 'Nam', '1956-07-09', GETDATE(), GETDATE(), 0),
    (3, 'taylorswift', 'Music@753!', 'taylorswift@gmail.com', 'Taylor Swift', '0989012345', NULL, 92, 'Nữ', '1989-12-13', GETDATE(), GETDATE(), 0),
    (3, 'leonardodicaprio', 'Titanic@852$', 'leonardodicaprio@gmail.com', 'Leonardo DiCaprio', '0990123456', NULL, 88, 'Nam', '1974-11-11', GETDATE(), GETDATE(), 0),
    (3, 'cristiano', 'Football@111$', 'cristiano@gmail.com', 'Cristiano Ronaldo', '0902233445', NULL, 99, 'Nam', '1985-02-05', GETDATE(), GETDATE(), 0),
    (3, 'messi', 'Barca@222!', 'messi@gmail.com', 'Lionel Messi', '0913344556', NULL, 98, 'Nam', '1987-06-24', GETDATE(), GETDATE(), 0),
    (3, 'johndoe', 'Secret@333!', 'johndoe@gmail.com', 'John Doe', '0924455667', NULL, 60, 'Nam', '1990-10-10', GETDATE(), GETDATE(), 0),
    (3, 'janedoe', 'Hello@444$', 'janedoe@gmail.com', 'Jane Doe', '0935566778', NULL, 65, 'Nữ', '1991-12-12', GETDATE(), GETDATE(), 0),
    (3, 'eminem', 'RapGod@555!', 'eminem@gmail.com', 'Eminem', '0946677889', NULL, 77, 'Nam', '1972-10-17', GETDATE(), GETDATE(), 0),
    (3, 'adele', 'Singer@666!', 'adele@gmail.com', 'Adele', '0957788990', NULL, 84, 'Nữ', '1988-05-05', GETDATE(), GETDATE(), 0),
    (3, 'robertdowney', 'IronMan@777$', 'robertdowney@gmail.com', 'Robert Downey Jr.', '0968899001', NULL, 90, 'Nam', '1965-04-04', GETDATE(), GETDATE(), 0),
    (3, 'barackobama', 'President@888!', 'barackobama@gmail.com', 'Barack Obama', '0979900112', NULL, 89, 'Nam', '1961-08-04', GETDATE(), GETDATE(), 0),
    (3, 'ladygaga', 'PokerFace@999$', 'ladygaga@gmail.com', 'Lady Gaga', '0988811223', NULL, 82, 'Nữ', '1986-03-28', GETDATE(), GETDATE(), 0);
GO

-----------------------------------------------------------------------------------------
CREATE TABLE Categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(50) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);
insert into Categories(category_name,created_at)
values
(N'Vợt cầu lông',GETDATE()),
(N'Giày cầu lông',GETDATE()),
(N'Quần cầu lông',GETDATE()),
(N'Áo cầu lông',GETDATE()),
(N'Balo cầu lông',GETDATE()),
(N'Cầu lông',GETDATE());
-----------------------------------------------------------------------------------------
CREATE TABLE Brands (
    brand_id INT IDENTITY(1,1) PRIMARY KEY,
    brand_name NVARCHAR(100) UNIQUE NOT NULL
);
insert into Brands(brand_name)
values
('Apacs'),
('Lining'),
('Victor'),
('Yonex');
-----------------------------------------------------------------------------------------
CREATE TABLE Products (
    product_id INT IDENTITY(1,1) PRIMARY KEY,
    product_name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX),
    price DECIMAL(30,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
	status NVARCHAR(20) NOT NULL,
	brand_id INT NOT NULL,
    category_id INT NOT NULL,
    image NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (category_id) REFERENCES Categories(category_id) ON DELETE CASCADE,
	FOREIGN KEY (brand_id) REFERENCES Brands(brand_id) ON DELETE CASCADE
);

INSERT INTO Products (product_name, description, price, stock, status, brand_id, category_id, image, created_at)
VALUES
------------------VỢT----------------
--APACS(8 vợt)
(N'Apacs Z-Ziggler', N'Trọng lượng: 4U (80-84g) | Độ cứng: Trung bình | Điểm cân bằng: Nặng đầu | Khung: High Modulus Graphite | Cán: Graphite.', 1700000, 12, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-apacs-z-ziggler_1740600600.jpg', GETDATE()),
(N'Apacs Nano Fusion Speed 722', N'Trọng lượng: 5U (75-79g) | Độ cứng: Dẻo | Điểm cân bằng: Cân bằng | Khung: Hi-Modulus Graphite | Cán: Graphite.', 1500000, 10, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-apacs-nano-fusion-speed-722-chinh-hang-1.webp', GETDATE()),
(N'Apacs Feather Weight 55', N'Trọng lượng: 6U (58-62g) | Độ cứng: Trung bình | Điểm cân bằng: Nặng đầu | Khung: Hi-Modulus Graphite | Cán: Graphite.', 1900000, 9, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-apacs-feather-weight-100-1.webp', GETDATE()),
(N'Apacs LETHAL Light 1.10', N'Trọng lượng: 4U (80-84g) | Độ cứng: Cứng | Điểm cân bằng: Nặng đầu | Khung: Hi-Modulus Graphite | Cán: Graphite.', 935000, 11, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-apacs-lethal-light-1-10-1.webp', GETDATE()),
(N'Apacs Virtuoso Performance', N'Trọng lượng: 4U (80-84g) | Độ cứng: Cứng | Điểm cân bằng: Cân bằng | Khung: Hi-Modulus Graphite | Cán: Graphite.', 2200000, 8, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-apacs-virtuoso-performance-1.webp', GETDATE()),
(N'Apacs Blizzard Pro', N'Trọng lượng: 4U (80-84g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: Hi-Modulus Graphite | Cán: Graphite.', 2000000, 10, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/gallery/vot-cau-long-apacs-blizzard-pro-chinh-hang_1.webp', GETDATE()),
(N'Apacs EdgeSaber 10', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: Hi-Modulus Graphite | Cán: Graphite.', 725000, 14, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/vot-cau-long-apacs-edge-saber-10-red--1.webp', GETDATE()),
(N'Apacs Tantrum 500', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Nặng đầu | Khung: Hi-Modulus Graphite | Cán: Graphite.', 2400000, 10, N'Còn hàng', 1, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-apacs-tantrum-500-international-1.webp', GETDATE()),
--LINING(8 vợt)
(N'Lining 3D Calibar 900', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Nặng đầu | Khung: Carbon Fiber | Cán: Carbon Fiber.', 3600000, 10, N'Còn hàng', 2, 1, 'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/vot-cau-long-lining-calibar-900-chinh-hang-1.webp', GETDATE()),
(N'Lining Aeronaut 9000', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Cân bằng | Khung: Carbon Fiber | Cán: Carbon Fiber.', 3400000, 8, N'Còn hàng', 2, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-lining-aeronaut-9000-chinh-hang-1.webp', GETDATE()),
(N'Lining Turbo Charging 75', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Cân bằng | Khung: Carbon Fiber | Cán: Carbon Fiber.', 3200000, 12, N'Còn hàng', 2, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-lining-turbo-charging-75-chinh-hang-1.webp', GETDATE()),
(N'Lining Windstorm 700', N'Trọng lượng: 5U (75-79g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: Carbon Fiber | Cán: Carbon Fiber.', 2500000, 14, N'Còn hàng', 2, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-lining-windstorm-700-5.webp', GETDATE()),
(N'Lining 3D Calibar 900B', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Nặng đầu | Khung: Carbon Fiber | Cán: Carbon Fiber.', 3100000, 11, N'Còn hàng', 2, 1, 'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/vot-cau-long-lining-calibar-900b-chinh-hang-5.webp', GETDATE()),
(N'Lining Aeronaut 7000B', N'Trọng lượng: 4U (80-84g) | Độ cứng: Cứng | Điểm cân bằng: Cân bằng | Khung: Carbon Fiber | Cán: Carbon Fiber.', 3300000, 9, N'Còn hàng', 2, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/gallery/vot-cau-long-lining-aeronaut-7000b-noi-dia-trung_1727380332.jpg', GETDATE()),
(N'Lining Windstorm 72', N'Trọng lượng: 6U (72g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: Carbon Fiber | Cán: Carbon Fiber.', 2800000, 13, N'Còn hàng', 2, 1,'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-lining-windstom-72-or-4.webp', GETDATE()),
(N'Lining Turbo Charging 80', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Nặng đầu | Khung: Carbon Fiber | Cán: Carbon Fiber.', 3500000, 10, N'Còn hàng', 2, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-lining-turbo-charging-80-chinh-hang-1.webp', GETDATE()),
--VICTOR (8 vợt)
(N'Victor Thruster Ryuga Metallic', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Nặng đầu | Khung: Ultra High Modulus Graphite + PYROFIL | Cán: Graphite + 7.0 Shaft.', 3200000, 9, N'Còn hàng', 3, 1, 'https://cdn.shopvnb.com/uploads/gallery/vot-cau-long-victor-thruster-ryuga-metallic-ma-taiwan_1709756064.webp', GETDATE()),
(N'Victor Brave Sword 12', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: Ultra High Modulus Graphite + Nano Resin | Cán: Graphite.', 3100000, 15, N'Còn hàng', 3, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-victor-brave-sword-12-xach-tay-1.webp', GETDATE()),
(N'Victor Jetspeed S 12F', N'Trọng lượng: 4U (80-84g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: Ultra High Modulus Graphite + PYROFIL | Cán: Graphite.', 2900000, 12, N'Còn hàng', 3, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-victor-jetspeed-s12-f-chinh-hang-5.webp', GETDATE()),
(N'Victor Thruster TTY – Tai Tzu Ying', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Nhẹ đầu | Khung: High Resilience Modulus Graphite | Cán: Graphite + 6.8 Shaft.', 3500000, 11, N'Còn hàng', 3, 1, 'https://cdn.shopvnb.com/uploads/gallery/vot-cau-long-victor-thruster-tty-ndash-tai-tzu-ying-chinh-hang_1701047671.webp', GETDATE()),
(N'Victor DriveX 9X', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: Ultra High Modulus Graphite + Free Core | Cán: Graphite.', 1700000, 10, N'Còn hàng', 3, 1, 'https://cdn.shopvnb.com/img/600x600/uploads/san_pham/vot-cau-long-victor-drivex-9x-chinh-hang-4.webp', GETDATE()),
(N'Victor Mjolnir Metallic Limited 2024', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: High Resilience Modulus Graphite + Nano Fortify | Cán: Graphite.', 4600000, 3, N'Còn hàng', 3, 1, 'https://cdn.shopvnb.com/uploads/gallery/vot-cau-long-victor-mjolnir-metallic-limited-2024-ma-taiwan_1716431807.webp', GETDATE()),
(N'Victor Challenger 7450F', N'Trọng lượng: 5U (75-79g) | Độ cứng: Trung bình | Điểm cân bằng: Nhẹ đầu | Khung: High Modulus Graphite | Cán: Graphite.', 940000, 14, N'Còn hàng', 3, 1,'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-victor-challenger-7450f-2.webp', GETDATE()),
(N'Victor Thruster Ryuga II Pro', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Nặng đầu | Khung: High Resilience Modulus Graphite + WES2.0 | Cán: Graphite.', 3700000, 7, N'Còn hàng', 3, 1, 'https://cdn.shopvnb.com/uploads/gallery/vot-cau-long-victor-thruster-ryuga-ii-pro-xach-tay_1724890296.jpg', GETDATE()),
-- YONEX (8 vợt)
(N'Yonex Astrox 100 ZZ', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Nặng đầu | Khung: HM Graphite, Namd, Tungsten | Cán: HM Graphite, Namd.', 3500000, 15, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-yonex-astrox-100zz-chinh-hang-1.webp', GETDATE()),
(N'Yonex Nanoflare 800', N'Trọng lượng: 4U (80-84g) | Độ cứng: Cứng | Điểm cân bằng: Nhẹ đầu | Khung: HMG, M40X, EX-HMG | Cán: HMG.', 3200000, 12, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/gallery/vot-cau-long-yonex-nanoflare-800-play-chinh-hang_1700092990.webp', GETDATE()),
(N'Yonex Duora Z Strike', N'Trọng lượng: 3U (85-89g) | Độ cứng: Rất cứng | Điểm cân bằng: Cân bằng | Khung: HMG, Namd, Nanometric DR | Cán: HMG.', 3300000, 10, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-yonex-duora-z-strike-xach-tay-1.webp', GETDATE()),
(N'Yonex Voltric Z Force II', N'Trọng lượng: 3U (85-89g) | Độ cứng: Rất cứng | Điểm cân bằng: Nặng đầu | Khung: HMG, Tungsten | Cán: HMG.', 3400000, 14, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-yonex-voltric-z-force-ld-3.webp', GETDATE()),
(N'Yonex Arcsaber 11 Pro', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Cân bằng | Khung: HMG, Pocketing Booster | Cán: HMG.', 3000000, 11, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-yonex-arcsaber-11-pro-chinh-hang-1.webp', GETDATE()),
(N'Yonex Nanoray Z Speed', N'Trọng lượng: 3U (85-89g) | Độ cứng: Cứng | Điểm cân bằng: Nhẹ đầu | Khung: HMG, Sonic Metal | Cán: HMG.', 2900000, 13, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-yonex-nanoray-z-speed-2016-xach-tay-1.webp', GETDATE()),
(N'Yonex Astrox 88D Pro', N'Trọng lượng: 3U (85-89g) | Độ cứng: Trung bình | Điểm cân bằng: Nặng đầu | Khung: HMG, Namd | Cán: HMG.', 3100000, 9, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-yonex-astrox-88d-pro-chinh-hang-1.webp', GETDATE()),
(N'Yonex Astrox 77', N'Trọng lượng: 4U (80-84g) | Độ cứng: Trung bình | Điểm cân bằng: Nặng đầu | Khung: HMG, Namd | Cán: HMG.', 2800000, 16, N'Còn hàng', 4, 1, 'https://cdn.shopvnb.com/uploads/san_pham/vot-cau-long-yonex-astrox-77-pro-chinh-hang-1.webp', GETDATE()),


----------------GIÀY-------------------
--APACS
(N'Giày cầu lông Apacs CP 211 F Đỏ',  N'Giày cầu lông Apacs CP 211 F màu đỏ, thiết kế bền bỉ, hỗ trợ di chuyển linh hoạt.',  750000, 20, N'Còn hàng', 1, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-apacs-cp-211-f-do-chinh-hang-2.webp',GETDATE()),
(N'Giày cầu lông Apacs CP 215 XY Đen Xám',  N'Giày cầu lông Apacs CP 215 XY màu đen xám, đệm êm, thoải mái khi di chuyển.',  780000, 25, N'Còn hàng', 1, 2, 'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-apacs-cp-215-xy-den-xam-chinh-hang-2.webp',GETDATE()),
--LINING
(N'Giày cầu lông Li-Ning AYTU025-2',  N'Giày cầu lông Li-Ning AYTU025-2, thiết kế hiện đại, hỗ trợ di chuyển linh hoạt.',  1200000, 20, N'Còn hàng', 2, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/giay-cau-long-lining-aytu025-2-chinh-hang_1735614591.webp',GETDATE()),
(N'Giày cầu lông Li-Ning AYTV025-2',  N'Giày cầu lông Li-Ning AYTV025-2, đế bám tốt, phù hợp cho mọi mặt sân.',  1150000, 18, N'Còn hàng', 2, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/giay-cau-long-lining-aytv025-2_1739322995.webp',GETDATE()),
(N'Giày cầu lông Li-Ning AYTU001-2',  N'Giày cầu lông Li-Ning AYTU001-2, êm ái, hỗ trợ giảm chấn khi di chuyển.',  1100000, 22, N'Còn hàng', 2, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-lining-aytu001-2_1733533680.jpg',GETDATE()),
(N'Giày cầu lông Li-Ning AYZU017-1',  N'Giày cầu lông Li-Ning AYZU017-1, thiết kế nhẹ, giúp tăng tốc độ di chuyển.',  1180000, 15, N'Còn hàng', 2, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-lining-ayzu017-1_1733705346.jpg',GETDATE()),
--VICTOR
(N'Giày cầu lông Victor P8500TD',N'Giày cầu lông Victor P8500TD, thiết kế chuyên nghiệp, hỗ trợ tối đa khi thi đấu.',  1450000, 20, N'Còn hàng', 3, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-victor-p8500td_1740420458.jpg',GETDATE()),
(N'Giày cầu lông Victor A391 AB',N'Giày cầu lông Victor A391 AB, độ bám sân tốt, giúp di chuyển linh hoạt.',  1300000, 18, N'Còn hàng', 3, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/giay-cau-long-victor-a391-ab-chinh-hang_1736734627.webp',GETDATE()),
(N'Giày cầu lông Victor A362 AF Trắng',   N'Giày cầu lông Victor A362 AF màu trắng, thiết kế thời trang, chất liệu cao cấp.', 1250000, 15, N'Còn hàng', 3, 2,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/giay-cau-long-victor-a362-af-trang-chinh-hang_1732302686.webp',GETDATE()),
--YONEX
(N'Giày cầu lông Yonex Aerus X2 Mint',  N'Giày cầu lông Yonex Aerus X2 Mint, siêu nhẹ, hỗ trợ di chuyển linh hoạt.',2100000, 20, N'Còn hàng', 4, 2,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/giay-cau-long-yonex-aerus-x2-mint-chinh-hang_1739476570.webp',GETDATE()),
(N'Giày cầu lông Yonex SHB 39EX',N'Giày cầu lông Yonex SHB 39EX, đế bám tốt, thiết kế tối ưu cho thi đấu.',1850000, 18, N'Còn hàng', 4, 2,'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-yonex-shb-39ex_1732908335.webp',GETDATE()),
(N'Giày cầu lông Yonex Valor 1',N'Giày cầu lông Yonex Valor 1, chất liệu cao cấp, đem lại cảm giác thoải mái.',  1950000, 15, N'Còn hàng', 4, 2, 'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-yonex-valor-1_1739304874.webp',GETDATE()),
(N'Giày cầu lông Yonex SHB 65Z4 Women 2025', N'Giày cầu lông Yonex SHB 65Z4 Women phiên bản 2025, êm ái, phù hợp cho nữ.',  2200000, 12, N'Còn hàng', 4, 2,'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/giay-cau-long-yonex-shb-65z4-women-2025_1740429883.webp',GETDATE()),

------------------------QUẦN-----------------------
--APACS
(N'Quần cầu lông Apacs Nữ Đen',N'Quần cầu lông Apacs dành cho nữ, màu đen, thoải mái, phù hợp cho thi đấu và tập luyện.',270000,50, N'Còn hàng', 1,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-yonex-q1-nam-den-logo-trang_1729028295.webp',GETDATE()),
(N'Quần cầu lông Apacs Nam Đen',N'Quần cầu lông Apacs dành cho nam, màu đen, chất liệu thoáng khí, phù hợp thi đấu và tập luyện.',270000,50, N'Còn hàng',1,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-yonex-q1-nam-den-logo-trang_1729028295.webp',GETDATE()),
--LINING
(N'Quần cầu lông Li-Ning Q21 Nam Trắng', N'Quần cầu lông Li-Ning Q21 dành cho nam, màu trắng, thoáng khí, phù hợp thi đấu.',  290000, 50, N'Còn hàng', 2,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-lining-q21-nam-trang_1715643713.webp',GETDATE()),
(N'Quần cầu lông Li-Ning Q20 Nam Đen',  N'Quần cầu lông Li-Ning Q20 dành cho nam, màu đen, chất liệu mềm mại, thoải mái khi vận động.',285000, 45, N'Còn hàng', 2,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-lining-q20-nam-den_1715643656.webp',GETDATE()),
(N'Quần cầu lông Li-Ning Q34 Nam Xanh',N'Quần cầu lông Li-Ning Q34 dành cho nam, màu xanh, chất vải cao cấp, co giãn tốt.',  295000, 40, N'Còn hàng', 2, 3,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-lining-q34-nam-xanh_1733532739.webp',GETDATE()),
(N'Quần cầu lông Li-Ning Nữ Đen Mã 421',  N'Quần cầu lông Li-Ning dành cho nữ, màu đen, nhẹ nhàng, thoáng mát, phù hợp khi tập luyện.',  275000, 35, N'Còn hàng',2, 3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-lining-nu-den-ma-421-1.webp',GETDATE()),
(N'Quần cầu lông Li-Ning Nữ Trắng Mã 059',  N'Quần cầu lông Li-Ning dành cho nữ, màu trắng, thiết kế trẻ trung, năng động.',  280000, 30, N'Còn hàng',2,3,'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/quan-cau-long-lining-nu-trang-ma-059-1.webp',GETDATE()),
--Victor
(N'Quần cầu lông Victor 9208 Đen Đỏ',N'Quần cầu lông Victor 9208 màu đen đỏ, thiết kế thể thao, phù hợp khi thi đấu.',300000, 40, N'Còn hàng', 3, 3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-victor-9208-den-do.webp',GETDATE()),
(N'Quần cầu lông Victor 9208 Đen Vàng',N'Quần cầu lông Victor 9208 màu đen vàng, chất liệu co giãn tốt, thoáng mát.',300000, 35, N'Còn hàng', 3, 3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-victor-9208-den-vang.webp',GETDATE()),
(N'Quần cầu lông Victor Nữ Trắng Mã 435',N'Quần cầu lông Victor dành cho nữ, màu trắng, phong cách trẻ trung, năng động.',280000, 30, N'Còn hàng', 3, 3,'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/quan-cau-long-victor-nu-trang-ma-435-1.webp',GETDATE()),
(N'Quần cầu lông Victor Nữ Trắng Mã 434',N'Quần cầu lông Victor dành cho nữ, màu trắng, thiết kế thoải mái, phù hợp tập luyện.',280000, 30, N'Còn hàng', 3, 3,'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/quan-cau-long-victor-nu-trang-ma-434-1.webp',GETDATE()),
--YONEX
(N'Quần cầu lông Yonex Q1 Nam Đen Logo Trắng',N'Quần cầu lông Yonex Q1 dành cho nam, màu đen với logo trắng, thoáng khí, thoải mái khi chơi thể thao.',300000,50, N'Còn hàng', 4,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-yonex-q1-nam-den-logo-trang_1729028295.webp',GETDATE()),
(N'Quần cầu lông Yonex Q33 Nam Xanh Than Logo Trắng',N'Quần cầu lông Yonex Q33 dành cho nam, màu xanh than với logo trắng, thoải mái và bền bỉ.',300000,50, N'Còn hàng', 4,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-yonex-q1-nam-den-logo-trang_1729028295.webp',GETDATE()),
(N'Quần cầu lông Yonex Q4 Nam Trắng Logo Đen',N'Quần cầu lông Yonex Q4 dành cho nam, màu trắng với logo đen, thiết kế thoải mái, phù hợp khi thi đấu và tập luyện.',300000,50, N'Còn hàng', 4,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-yonex-q1-nam-den-logo-trang_1729028295.webp',GETDATE()),
(N'Quần cầu lông Yonex 9225 Vàng',N'Quần cầu lông Yonex 9225 màu vàng, chất liệu thoáng khí, thoải mái khi vận động.',300000,50, N'Còn hàng', 4,3,'https://cdn.shopvnb.com/img/300x300/uploads/gallery/quan-cau-long-yonex-q1-nam-den-logo-trang_1729028295.webp',GETDATE()),


------------------------ÁO-------------------------------
--APACS
(N'Áo cầu lông Apacs 016 Trắng', N'Áo cầu lông Apacs 016 màu trắng, chất liệu thấm hút mồ hôi tốt.',  450000, 30, N'Còn hàng', 1, 4,'https://cdn.shopvnb.com/img/600x600/uploads/gallery/col-cas-016-white_1735064582.webp',GETDATE()),
(N'Áo cầu lông Apacs 016 Đen', N'Áo cầu lông Apacs 016 màu đen, thiết kế đơn giản, thoáng mát.',  450000, 30, N'Còn hàng', 1, 4, 'https://cdn.shopvnb.com/img/600x600/uploads/gallery/col-cas-016-black_1735064233.webp',GETDATE()),
--LINING
(N'Áo cầu lông Lining VM1066 Đen',  N'Áo cầu lông Lining VM1066 nam, màu đen, chất liệu nhẹ, thoáng khí.',  500000, 20, N'Còn hàng', 2, 4,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-lining-vm1066-nam-den_1726790310.jpg',GETDATE()),
(N'Áo cầu lông Yonex VM1061 Nam Xanh Navy', N'Áo cầu lông nam Yonex VM1061 màu xanh navy chính hãng', 450000, 15, N'Còn hàng', 2, 4, 'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-yonex-vm1061-nam-xanh-navy_1724292917.webp',GETDATE()),
--VICTOR
(N'Áo cầu lông Victor 281 Nữ Trắng',  N'Áo cầu lông Victor 281 dành cho nữ, màu trắng, kiểu dáng thể thao.',  480000, 22, N'Còn hàng', 3, 4,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-victor-281-nu-trang_1722307496.webp',GETDATE()),
(N'Áo cầu lông Victor 281 Nữ Đen Đỏ',  N'Áo cầu lông Victor 281 dành cho nữ, màu đen đỏ, vải co giãn tốt.',  480000, 20, N'Còn hàng', 3, 4,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-victor-281-nu-den-do_1722307218.webp',GETDATE()),
(N'Áo cầu lông Yonex TRM2934 Trắng',  N'Áo cầu lông Yonex TRM2934 màu trắng, thiết kế hiện đại, co giãn tốt.', 600000, 18, N'Còn hàng', 3, 4, 'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-yonex-trm2934-white-chinh-hang_1739842142.webp',GETDATE()),
--YONEX
(N'Áo cầu lông Yonex TRM2932 Bayberry', N'Áo cầu lông Yonex TRM2932 màu bayberry, dành cho vận động viên chuyên nghiệp.',  620000, 15, N'Còn hàng', 4, 4,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-yonex-trm2932-bayberry-chinh-hang_1739836722.webp',GETDATE()),
(N'Áo cầu lông Yonex TRM2934 Đỏ', N'Áo cầu lông Yonex TRM2934 màu đỏ, thiết kế đẹp, phù hợp thi đấu.',  600000, 18, N'Còn hàng', 4, 4,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-yonex-trm2934-high-risk-red-chinh-hang_1739840868.webp',GETDATE()),
(N'Áo cầu lông Yonex VM1061 Xanh Navy',  N'Áo cầu lông Yonex VM1061 dành cho nam, màu xanh navy, vải co giãn.',  550000, 25, N'Còn hàng', 4, 4,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-yonex-vm1061-nam-xanh-navy_1724292940.webp',GETDATE()),
(N'Áo cầu lông Yonex VM1062 Trắng Xanh',   N'Áo cầu lông Yonex VM1062 nam, màu trắng xanh, thiết kế thể thao.',  550000, 20, N'Còn hàng',4, 4,  'https://cdn.shopvnb.com/img/300x300/uploads/gallery/ao-cau-long-yonex-vm1062-nam-trang-xanh_1724292797.webp',GETDATE()),


-----------------------BALO------------------------------
--VICTOR
(N'Balo cầu lông Victor BR3034 Trắng GC', N'Balo cầu lông Victor BR3034 màu trắng chính hãng', 750000, 10, N'Còn hàng', 3, 5, 'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/balo-cau-long-victor-br3034-trang-gc_1737747063.webp',GETDATE()),
(N'Balo cầu lông Victor BR9013CF Đen Xanh', N'Balo cầu lông Victor BR9013CF đen xanh chính hãng', 890000, 8, N'Còn hàng', 3, 5, 'https://cdn.shopvnb.com/img/300x300/uploads/san_pham/balo-cau-long-victor-br9013cf-den-xanh-chinh-hang_1737764452.webp',GETDATE()),
--YONEX
(N'Balo cầu lông Yonex BP108U Xanh Ngọc Hồng', N'Balo cầu lông Yonex BP108U xanh ngọc hồng chính hãng', 990000, 7,N'Còn hàng', 4, 5, 'https://cdn.shopvnb.com/uploads/san_pham/balo-cau-long-yonex-bp108u-xanh-ngoc-hong-gia-cong-2.webp',GETDATE()),
(N'Balo cầu lông Yonex BAG524B0712Z Star White Creme De Peche', N'Balo cầu lông Yonex BAG524B0712Z trắng kem chính hãng', 1200000, 5, N'Còn hàng', 4, 5, 'https://cdn.shopvnb.com/uploads/gallery/balo-cau-long-yonex-bag524b0712z-star-white-creme-de-peche-chinh-hang_1740082071.webp',GETDATE()),


-----------------------CẦU LÔNG----------------------------
-- Lining
(N'Cầu lông Lining A+60', N'Quả cầu lông Lining A+60 tiêu chuẩn, phù hợp cho tập luyện hàng ngày.', 320000, 20, 'Còn hàng', 2, 6, 'https://cdn0921.cdn4s1.com/media/san-pham/cau-long/vot-cau-long/vcl-yonex/z5042729990099_e5b5318fa0dd5b179fd20fb7cf684bcf.jpg',GETDATE()),
(N'Cầu lông Lining A+90', N'Quả cầu lông Lining A+90 cao cấp, độ bền cao, dành cho thi đấu chuyên nghiệp.', 500000, 15, 'Còn hàng', 2, 6, 'https://5.imimg.com/data5/ZY/IF/LJ/ANDROID-15687459/product-jpeg-1000x1000.jpg',GETDATE()),
-- Yonex
(N'Cầu lông Yonex AS-40', N'Quả cầu lông Yonex AS-40 chất lượng cao, phù hợp cho tập luyện và thi đấu chuyên nghiệp.', 500000, 30, 'Còn hàng', 4, 6, 'https://product.hstatic.net/1000387607/product/71hun0rns3l._sl1500__8a3f39ad54cc4bb6b1e4b55c52865195_master.jpg',GETDATE()),
(N'Cầu lông Yonex AS-30', N'Quả cầu lông Yonex AS-30 với độ bền cao, mang lại trải nghiệm tốt cho người chơi.', 400000, 25, 'Còn hàng', 4, 6, 'https://cf.shopee.vn/file/7dd0b4aaa380ca82ceeed769dca6b53d',GETDATE());

-----------------------------------------------------------------------------------------
	CREATE TABLE Orders (
		order_id INT IDENTITY(1,1) PRIMARY KEY,
		user_id INT NOT NULL,
		total_price DECIMAL(30,2) NOT NULL,
		status NVARCHAR(20) CHECK (status IN ('Pending', 'Shipped', 'Delivered', 'Canceled')) NOT NULL DEFAULT 'Pending',
		order_date DATETIME DEFAULT GETDATE(),
		FOREIGN KEY (user_id) REFERENCES Users(UserID) ON DELETE CASCADE
	);

-----------------------------------------------------------------------------------------
CREATE TABLE OrderDetails (
    orderDetail_id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
	product_image NVARCHAR(255),
	product_name NVARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(30,2) NOT NULL,
	total_price DECIMAL(30,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------
CREATE TABLE Reviews (
    review_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------
CREATE TABLE ChatMessages (
    id INT IDENTITY(1,1) PRIMARY KEY,
    sender NVARCHAR(50) NOT NULL,
    text NVARCHAR(MAX) NOT NULL,
    timestamp BIGINT NOT NULL
);

-----------------------------------------------------------------------------------------
CREATE TRIGGER trg_UpdateProductStatus
ON Products
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Đảm bảo stock không bị âm
    UPDATE Products
    SET stock = 0
    WHERE stock < 0;

    -- Cập nhật trạng thái sản phẩm
    UPDATE Products
    SET status =
        CASE
            WHEN stock <= 0 THEN N'Hết hàng'
            ELSE N'Còn hàng'
        END
    WHERE product_id IN (SELECT product_id FROM inserted);
END;

SELECT name, object_definition(object_id)
FROM sys.triggers
WHERE name = 'trg_UpdateProductStatus';

ENABLE TRIGGER trg_UpdateProductStatus ON Products;

UPDATE Products
SET stock = 0
WHERE stock < 0;

UPDATE Products
SET status = 'Hết hàng'
WHERE stock = 0;
