USE [master]
GO
/****** Object:  Database [Light_House_Shop]    Script Date: 2/26/2025 6:41:20 PM ******/
CREATE DATABASE [Light_House_Shop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Light_House_Shop', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.LIGHT_HOUSE2K3\MSSQL\DATA\Light_House_Shop.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Light_House_Shop_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.LIGHT_HOUSE2K3\MSSQL\DATA\Light_House_Shop_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Light_House_Shop] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Light_House_Shop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Light_House_Shop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Light_House_Shop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Light_House_Shop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Light_House_Shop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Light_House_Shop] SET ARITHABORT OFF 
GO
ALTER DATABASE [Light_House_Shop] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Light_House_Shop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Light_House_Shop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Light_House_Shop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Light_House_Shop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Light_House_Shop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Light_House_Shop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Light_House_Shop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Light_House_Shop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Light_House_Shop] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Light_House_Shop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Light_House_Shop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Light_House_Shop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Light_House_Shop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Light_House_Shop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Light_House_Shop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Light_House_Shop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Light_House_Shop] SET RECOVERY FULL 
GO
ALTER DATABASE [Light_House_Shop] SET  MULTI_USER 
GO
ALTER DATABASE [Light_House_Shop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Light_House_Shop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Light_House_Shop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Light_House_Shop] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Light_House_Shop] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Light_House_Shop] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Light_House_Shop', N'ON'
GO
ALTER DATABASE [Light_House_Shop] SET QUERY_STORE = ON
GO
ALTER DATABASE [Light_House_Shop] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Light_House_Shop]
GO
/****** Object:  UserDefinedFunction [dbo].[getRating]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   FUNCTION [dbo].[getRating] (@ProductID INT)
RETURNS INT
AS
BEGIN
    DECLARE @AverageRating DECIMAL(3,2)
    
    -- Tính trung bình rating
    SELECT @AverageRating = AVG(CAST(Rating AS DECIMAL(3,2)))
    FROM Reviews
    WHERE ProductID = @ProductID
    
    -- Trả về giá trị được làm tròn lên, nếu không có thì trả về 0
    RETURN ROUND(ISNULL(@AverageRating, 0), 0);
END;
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[CategoryID] [int] NOT NULL,
	[CategoryName] [nvarchar](50) NOT NULL,
	[Description] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Inventory]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Inventory](
	[InventoryID] [int] NOT NULL,
	[ProductID] [int] NULL,
	[Quantity] [int] NOT NULL,
	[LastUpdated] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[InventoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[OrderID] [int] NULL,
	[ProductID] [int] NULL,
	[Quantity] [int] NOT NULL,
	[OrderDetailID] [int] IDENTITY(1,1) NOT NULL,
	[Size] [nvarchar](100) NULL,
	[Topping] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderDetailID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[ShipperID] [int] NULL,
	[PaymentID] [int] NULL,
	[OrderDate] [datetime] NULL,
	[TotalAmount] [decimal](10, 2) NOT NULL,
	[ShippingFee] [decimal](10, 2) NULL,
	[Status] [nvarchar](20) NULL,
	[UserID] [int] NULL,
	[OrderID] [int] IDENTITY(1,1) NOT NULL,
	[ShippingAddress] [nvarchar](255) NULL,
 CONSTRAINT [PK_Orders] PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Payment]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Payment](
	[PaymentID] [int] NOT NULL,
	[PaymentMethod] [nvarchar](50) NOT NULL,
	[Description] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[PaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[ProductID] [int] NOT NULL,
	[CategoryID] [int] NULL,
	[ProductName] [nvarchar](100) NOT NULL,
	[Description] [nvarchar](255) NULL,
	[Image] [nvarchar](255) NULL,
	[Size] [nvarchar](255) NULL,
	[Topping] [nvarchar](255) NULL,
	[Price] [money] NULL,
	[CreatedAt] [datetime] NULL,
	[Status] [nvarchar](20) NULL,
	[PromotionID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Promotions]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotions](
	[PromotionID] [int] NOT NULL,
	[PromotionName] [nvarchar](100) NOT NULL,
	[Description] [nvarchar](255) NULL,
	[DiscountPercent] [decimal](4, 2) NULL,
	[StartDate] [datetime] NULL,
	[EndDate] [datetime] NULL,
	[Status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[PromotionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reviews]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reviews](
	[ReviewID] [int] NOT NULL,
	[ProductID] [int] NULL,
	[UserID] [int] NULL,
	[Rating] [int] NULL,
	[Comment] [nvarchar](255) NULL,
	[ReviewDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[ReviewID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Shipper]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Shipper](
	[ShipperID] [int] NOT NULL,
	[UserID] [int] NULL,
	[VehicleType] [nvarchar](50) NULL,
	[VehicleNumber] [nvarchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[ShipperID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Suppliers]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Suppliers](
	[SupplierID] [int] NOT NULL,
	[SupplierName] [nvarchar](100) NOT NULL,
	[ContactName] [nvarchar](100) NULL,
	[Phone] [nvarchar](15) NULL,
	[Email] [nvarchar](100) NULL,
	[Address] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[SupplierID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](255) NULL,
	[Password] [nvarchar](255) NULL,
	[Email] [nvarchar](255) NULL,
	[FullName] [nvarchar](255) NULL,
	[Phone] [nvarchar](20) NULL,
	[Role] [nvarchar](50) NULL,
	[CreatedAt] [datetime] NULL,
	[Token] [nvarchar](255) NULL,
	[ExpireToken] [datetime] NULL,
	[isTokenUsed] [int] NULL,
	[VerificationCode] [nvarchar](255) NULL,
	[Address] [nvarchar](255) NULL,
	[Status] [bit] NULL,
	[Image] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Wallet]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Wallet](
	[WalletID] [int] NOT NULL,
	[UserID] [int] NULL,
	[Balance] [decimal](10, 2) NULL,
	[LastUpdated] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[WalletID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WalletTransactions]    Script Date: 2/26/2025 6:41:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WalletTransactions](
	[TransactionID] [int] NOT NULL,
	[WalletID] [int] NULL,
	[Amount] [decimal](10, 2) NOT NULL,
	[TransactionDate] [datetime] NULL,
	[Note] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[TransactionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (1, N'Coffee', N'Types of coffee')
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (2, N'Cake', N'Types of cake')
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (3, N'Milk Tea', N'Types of milk tea')
GO
INSERT [dbo].[Inventory] ([InventoryID], [ProductID], [Quantity], [LastUpdated]) VALUES (1, 1, 58, CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Inventory] ([InventoryID], [ProductID], [Quantity], [LastUpdated]) VALUES (2, 2, 100, CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Inventory] ([InventoryID], [ProductID], [Quantity], [LastUpdated]) VALUES (3, 3, 92, CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Inventory] ([InventoryID], [ProductID], [Quantity], [LastUpdated]) VALUES (4, 4, 35, CAST(N'2024-01-01T00:00:00.000' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[OrderDetails] ON 

INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (1, 1, 1, 1, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (2, 4, 1, 2, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (2, 1, 3, 4, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (83, 1, 3, 80, N'M-10', NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (84, 3, 1, 81, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (85, 23, 5, 82, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (86, 5, 1, 83, N'S-0', N'White pearls-10;Fig tree-10')
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (87, 5, 2, 84, N'S-0', N'White pearls-10;Fig tree-10')
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (88, 21, 1, 85, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (89, 21, 3, 86, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (90, 1, 4, 87, N'M-10', NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (91, 23, 5, 88, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (92, 22, 1, 89, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (93, 22, 3, 90, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (94, 25, 1, 91, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (95, 25, 1, 92, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (96, 26, 5, 93, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (97, 15, 11, 94, N'S-0', N'Coffee Jelly-10;Cream Cheese Macchiato-15')
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (98, 23, 1, 95, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (99, 5, 2, 96, N'S-0', N'White pearls-10')
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (111, 4, 1, 108, NULL, NULL)
INSERT [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [OrderDetailID], [Size], [Topping]) VALUES (117, 8, 2, 114, N'M-10', N'Peach Pieces-10;Cream Cheese Macchiato-15')
SET IDENTITY_INSERT [dbo].[OrderDetails] OFF
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (1, 1, CAST(N'2024-01-01T00:00:00.000' AS DateTime), CAST(64000.00 AS Decimal(10, 2)), CAST(15000.00 AS Decimal(10, 2)), N'Completed', 2, 1, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (1, 2, CAST(N'2024-01-01T00:00:00.000' AS DateTime), CAST(85000.00 AS Decimal(10, 2)), CAST(15000.00 AS Decimal(10, 2)), N'Pending', 2, 2, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (1, 1, CAST(N'2025-02-10T09:40:19.430' AS DateTime), CAST(50000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Pending', 1, 4, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-10T11:20:57.150' AS DateTime), CAST(55000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Pending', 4, 8, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T00:49:08.470' AS DateTime), CAST(168000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 83, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T00:49:21.120' AS DateTime), CAST(19000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 84, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:09:16.837' AS DateTime), CAST(175000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 85, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:09:21.820' AS DateTime), CAST(55000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 86, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:09:23.870' AS DateTime), CAST(110000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 87, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:09:31.270' AS DateTime), CAST(39000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 88, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:09:32.937' AS DateTime), CAST(117000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 89, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:09:39.003' AS DateTime), CAST(224000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 1, 90, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:10:08.890' AS DateTime), CAST(175000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 91, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:10:12.323' AS DateTime), CAST(21000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 92, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:10:13.953' AS DateTime), CAST(63000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 93, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:10:17.607' AS DateTime), CAST(39000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 94, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:10:18.643' AS DateTime), CAST(39000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 95, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:10:27.190' AS DateTime), CAST(145000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 96, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T01:20:54.593' AS DateTime), CAST(715000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 97, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-15T02:10:09.787' AS DateTime), CAST(35000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Completed', 5, 98, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-17T08:02:22.333' AS DateTime), CAST(90000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Pending', 5, 99, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-25T22:19:32.950' AS DateTime), CAST(44000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Pending', 25, 111, NULL)
INSERT [dbo].[Orders] ([ShipperID], [PaymentID], [OrderDate], [TotalAmount], [ShippingFee], [Status], [UserID], [OrderID], [ShippingAddress]) VALUES (NULL, NULL, CAST(N'2025-02-25T22:19:46.157' AS DateTime), CAST(120000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), N'Pending', 25, 117, NULL)
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
INSERT [dbo].[Payment] ([PaymentID], [PaymentMethod], [Description]) VALUES (1, N'Cash', N'Thanh toán bằng tiền mặt')
INSERT [dbo].[Payment] ([PaymentID], [PaymentMethod], [Description]) VALUES (2, N'Momo', N'Thanh toán qua ví Momo')
INSERT [dbo].[Payment] ([PaymentID], [PaymentMethod], [Description]) VALUES (3, N'VNPay', N'Thanh toán qua VNPay')
GO
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (1, 1, N'Iced Espresso', N'An original cup of Espresso starts with quality Arabica beans, mixed with a balanced ratio of Robusta beans, creating a caramel sweetness, mild sourness and consistency.', N'./assets/imgs/hot-espresso.png', N'S-0;M-10;L-15', NULL, 46000.0000, CAST(N'2025-01-20T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (2, 1, N'Iced Cappuccino', N'Cappuccino is a drink that blends the aroma of milk, the fatty taste of cream foam and the rich taste of Espresso coffee. All create a special flavor, a bit gentle, quiet and delicate.', N'./assets/imgs/iced-cappuccino.png', N'S-0;M-5', NULL, 46000.0000, CAST(N'2024-01-01T00:00:00.000' AS DateTime), N'Available', 1)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (3, 2, N'Chocolate Ice Cream Mochi', N'Covered by a fragrant Mochi crust, inside is a layer of cold cream and unique chocolate filling. Order a Mochi for a refreshing day. The product must be kept cool and best consumed within 2 hours after receipt.', N'./assets/imgs/chocolate-Ice-cream-mochi.png', NULL, NULL, 19000.0000, CAST(N'2025-01-20T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (4, 2, N'Mousse Tiramisu', N'The addictive flavor is created by the slight bitterness of coffee and the attractive sweet and fatty egg cream layer', N'./assets/imgs/mousse-tiramisu.png', NULL, NULL, 55000.0000, CAST(N'2025-01-04T00:00:00.000' AS DateTime), N'Available', 1)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (5, 3, N'Four Seasons Flute Oolong Milk Tea', N'Four Quarter Oolong Tea is fragrant with spring flowers, mixed with smooth fragrant milk and cool mist. That is the sweet Loc Yeu Yeu that Nha sends to you.', N'./assets/imgs/oolong-milk-tea-1.png;./assets/imgs/oolong-milk-tea-2.png', N'S-0;M-5', N'White pearls-10;Fig tree-10', 35000.0000, CAST(N'2025-01-20T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (6, 1, N'Cream Cheese Almond Milk Coffee', N'Smooth almond milk espresso paired with smooth, creamy cheese cream. During the festive season, PEOPLE S HAPPINESS can be enjoyed sometimes with your loved ones! *Stir well to double the deliciousness.', N'./assets/imgs/cheese-foam-milk-tea.png', N'S-0;M-5', N'Foam Cheese-10;Peach Pieces-10;Coffee Jelly-10', 59000.0000, CAST(N'2025-01-02T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (7, 1, N'Normal Espresso', N'Make your day more fresh, alert, smooth and smooth with Espresso. This is an unexpected matchmaking between rustic Northwest green tea and Da Lat Arabica coffee. If you want to add a little highlight to your day, remember to look for this surprise!', N'./assets/imgs/normal-espresso.png', N'S-0', N'Coffee Jelly-10', 29000.0000, CAST(N'2025-01-20T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (8, 3, N'BLao Oolong Milk Tea', N'Enjoy the cool taste of the mountains and forests in each sip of the House"s Oolong B"Lao Milk Tea. Each tea leaf is carefully sourced from B"Lao (Lam Dong) to preserve the rich Oolong flavor, mixed with fragrant, attractive milk.', N'./assets/imgs/blao-oolong-milk-tea.png', N'S-0;M-10', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 50000.0000, CAST(N'2025-01-21T20:01:16.683' AS DateTime), N'Available', 3)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (9, 3, N'Four Seasons Oolong Milk Tea', N'Four Quarter Oolong Tea is fragrant with spring flowers, mixed with smooth fragrant milk and cool mist. That"s the sweet Loc Yeu Yeu that Nha sends to you.', N'./assets/imgs/four-seasons-oolong-milk-tea.png', N'S-0;M-10', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 55000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (10, 1, N'Espresso Marble Green Tea', N'Make your day more fresh, alert, smooth and smooth with Espresso Marble Green Tea. If you want to add a little highlight to your day, remember to look for this surprise!', N'./assets/imgs/espresso-marble-green-tea.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 40000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (11, 1, N'A-Me Dao', N'Delight in Americano from 100% Arabica combined with sweet peaches, creating a fresh taste.', N'./assets/imgs/a-me-dao.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 39000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (12, 1, N'A-Me Classic', N'Delight in Americano from 100% Arabica combined with sweet peaches, creating a fresh taste.', N'./assets/imgs/a-me-classic.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 38000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (13, 1, N'A-Me Tuyet Dao', N'Delight in Americano from 100% Arabica combined with sweet peaches, creating a fresh taste.', N'./assets/imgs/a-me-tuyet-dao.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 60000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (14, 1, N'A-Me Quat', N'Delight in Americano from 100% Arabica combined with sweet peaches, creating a fresh taste.', N'./assets/imgs/a-me-quat.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 61000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (15, 1, N'Hot Latte', N'A delicate combination of the bitter taste of pure Espresso coffee mixed with the sweet taste of hot milk, topped with a thin layer of cream, creating a perfect cup of coffee in taste and appearance.', N'./assets/imgs/hot-latte.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 40000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (16, 1, N'Hot Cappuccino', N'Capuchino là th?c u?ng hòa quy?n gi?a huong thom c?a s?a, v? béo c?a b?t kem cùng v? d?m dà t? cà phê Espresso. T?t c? t?o nên m?t huong v? d?c bi?t, m?t chút nh? nhàng, tr?m l?ng và tinh t?.', N'./assets/imgs/hot-cappucino.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 35000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', 2)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (17, 1, N'Hot Caramel Macchiato', N'Caramel Macchiato will bring a pleasant surprise with the rich aroma of milk foam, fresh milk, and the elegant bitterness of premium Espresso coffee.', N'./assets/imgs/hot-caramel-macchiato.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 55000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', 5)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (18, 1, N'Cold Brew Fresh Milk', N'Cool and balanced with the flavor of 100% original Arabica Cau Dat coffee and fragrant fresh milk for each delicious, delicious sip.', N'./assets/imgs/cold-brew-fresh-milk.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 55000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (19, 1, N'Ice Latte', N'Cool and balanced with the flavor of 100% original Arabica Cau Dat coffee and fragrant fresh milk for each delicious, delicious sip.', N'./assets/imgs/ice-latte.png', N'S-0', N'Coffee Jelly-10;Cream Cheese Macchiato-15', 45000.0000, CAST(N'2025-01-20T00:00:00.000' AS DateTime), N'Available', 7)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (20, 1, N'Hot Americano', N'Americano is prepared by adding water at a certain ratio to a cup of Espresso coffee, thereby bringing a gentle flavor and preserving the characteristic coffee scent.', N'./assets/imgs/hot-americano.png', N'S-0', N'Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15', 45000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', 2)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (21, 2, N'Chocolate Bear Mousse', N'With its lovely appearance and sweet, fragrant taste, you definitely have to try it at least once.', N'./assets/imgs/mouse-gau-choco.png', NULL, NULL, 39000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', 2)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (22, 2, N'Blueberry Ice Cream Mochi', N'Covered by a fragrant Mochi shell, inside is a layer of cold cream and a typical fragrant, sweet blueberry filling.', N'./assets/imgs/mochi-viet-quat.png', NULL, NULL, 21000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (23, 2, N'Butter Croissant Condensed Milk', N'The Butter Croissant you loved, now loves with no escape when dipped in condensed milk. Fragrant butter, smooth milk, sweet to the heart!', N'./assets/imgs/croissant-sua-dac.png', NULL, NULL, 35000.0000, CAST(N'2025-01-20T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (24, 2, N'Salted Egg Croissant', N'The Butter Croissant you loved, now loves with no escape when dipped in condensed milk. Fragrant butter, smooth milk, sweet to the heart!', N'./assets/imgs/croissant-trung-muoi.png', NULL, NULL, 30000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (25, 2, N'Choco Croffle', N'Fancy and delicious with a Croffle cake made from Croissant cake base baked in a honeycomb Waffle mold.', N'./assets/imgs/choco-croifle.png', NULL, NULL, 39000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', 4)
INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID]) VALUES (26, 2, N'Butter Croissant', N'Take a bite, the crust is crispy and fragrant with butter, then smooth and melts in your mouth. Extremely sticky when sipping Butter Croissant with coffee.', N'./assets/imgs/croissant.png', NULL, NULL, 29000.0000, CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'Available', NULL)
GO
INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [Description], [DiscountPercent], [StartDate], [EndDate], [Status]) VALUES (1, N'New Year Sale', N'Discount for New Year celebration', CAST(20.00 AS Decimal(4, 2)), CAST(N'2025-01-01T00:00:00.000' AS DateTime), CAST(N'2025-01-10T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [Description], [DiscountPercent], [StartDate], [EndDate], [Status]) VALUES (2, N'Summer Sale', N'Special summer discounts', CAST(15.00 AS Decimal(4, 2)), CAST(N'2025-06-01T00:00:00.000' AS DateTime), CAST(N'2025-06-15T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [Description], [DiscountPercent], [StartDate], [EndDate], [Status]) VALUES (3, N'Black Friday', N'Biggest sale of the year', CAST(50.00 AS Decimal(4, 2)), CAST(N'2025-11-25T00:00:00.000' AS DateTime), CAST(N'2025-11-30T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [Description], [DiscountPercent], [StartDate], [EndDate], [Status]) VALUES (4, N'Christmas Sale', N'Exclusive Christmas discounts', CAST(30.00 AS Decimal(4, 2)), CAST(N'2025-12-20T00:00:00.000' AS DateTime), CAST(N'2025-12-26T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [Description], [DiscountPercent], [StartDate], [EndDate], [Status]) VALUES (5, N'Flash Sale', N'Limited-time discounts', CAST(10.00 AS Decimal(4, 2)), CAST(N'2025-01-15T00:00:00.000' AS DateTime), CAST(N'2025-01-16T00:00:00.000' AS DateTime), 0)
INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [Description], [DiscountPercent], [StartDate], [EndDate], [Status]) VALUES (7, N'Back to School', N'Discounts on school supplies', CAST(25.00 AS Decimal(4, 2)), CAST(N'2025-08-15T00:00:00.000' AS DateTime), CAST(N'2025-08-30T00:00:00.000' AS DateTime), 1)
GO
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (1, 1, 4, 5, N'Cà phê rất ngon!', CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (2, 3, 5, 4, N'Trà sữa ngon, trân châu dẻo', CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (3, 1, 2, 3, N'Cà phê rất tệ!', CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (4, 1, 3, 5, N'Cà phê rất good!', CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (5, 1, 5, 5, N'Cà phê0 rất good!', CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (6, 5, 6, 3, N'T?m T?m', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (7, 12, 2, 4, N'Ngon tuy?t', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (8, 16, 4, 2, N'T?m ?n', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (9, 19, 6, 3, N'T?m T?m', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (10, 24, 7, 4, N'Béo ng?y, tuy?t', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (11, 21, 3, 3, N'An cung du?c', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (12, 26, 5, 5, N'Ngon', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (13, 22, 3, 5, N'Good', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (14, 20, 7, 4, N'Awesome lay', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (15, 22, 6, 3, N'Âmzing', CAST(N'2025-02-07T23:30:14.580' AS DateTime))
INSERT [dbo].[Reviews] ([ReviewID], [ProductID], [UserID], [Rating], [Comment], [ReviewDate]) VALUES (16, 23, 6, 5, N'Tuy?t cú mèo', CAST(N'2025-02-07T23:32:16.030' AS DateTime))
GO
INSERT [dbo].[Shipper] ([ShipperID], [UserID], [VehicleType], [VehicleNumber]) VALUES (1, 3, N'Xe máy', N'29-B1 99999')
GO
INSERT [dbo].[Suppliers] ([SupplierID], [SupplierName], [ContactName], [Phone], [Email], [Address]) VALUES (1, N'Công ty TNHH Cà phê Việt Nam', N'Nguyễn Văn X', N'0123456789', N'supplier1@gmail.com', N'Hà Nội')
INSERT [dbo].[Suppliers] ([SupplierID], [SupplierName], [ContactName], [Phone], [Email], [Address]) VALUES (2, N'Công ty TNHH Trà Thái Nguyên', N'Trần Thị Y', N'0987654321', N'supplier2@gmail.com', N'Thái Nguyên')
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (1, N'admin', N'1', N'admin@gmail.com', N'Admin System', N'0123456789', N'Admin', CAST(N'2024-01-01T00:00:00.000' AS DateTime), NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (2, N'staff1', N'1', N'staff1@gmail.com', N'Nguyễn Văn A', N'0987654321', N'Customer', CAST(N'2024-01-01T00:00:00.000' AS DateTime), NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (3, N'shipper1', N'1', N'shipper1@gmail.com', N'Trần Văn B', N'0369852147', N'Shipper', CAST(N'2024-01-01T00:00:00.000' AS DateTime), NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (4, N'user1', N'1', N'user1@gmail.com', N'Lê Thị C', N'0147852369', N'Customer', CAST(N'2024-01-01T00:00:00.000' AS DateTime), NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (5, N'user2', N'1', N'user2@gmail.com', N'Phạm Văn D', N'0258963147', N'Customer', CAST(N'2024-01-01T00:00:00.000' AS DateTime), NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (6, N'hehe', N'1', N'dang@cmcm.com00', N'dang', N'11000', N'1', CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'11', CAST(N'2025-01-01T00:00:00.000' AS DateTime), 1, N'113', NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (7, N'hehe', N'1', N'dang@cmcm.com00', N'dang', N'11000', N'1', CAST(N'2025-01-01T00:00:00.000' AS DateTime), N'11', CAST(N'2025-01-01T00:00:00.000' AS DateTime), 1, N'113', NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (25, N'adminn', N'1', N'lackguku1@gmail.com', N'Dang Pham', N'0778167802', N'Customer', CAST(N'2025-01-12T13:56:17.790' AS DateTime), N'84d970eb-98aa-468a-9653-5c832525c8b7', CAST(N'2025-01-13T01:53:36.883' AS DateTime), 1, N'190298', NULL, 1, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (30, N'Đăng Phạm', NULL, N'dang096203009696@gmail.com', NULL, NULL, NULL, CAST(N'2025-01-13T10:56:14.923' AS DateTime), N'', CAST(N'2025-01-13T10:56:14.940' AS DateTime), 1, N'', NULL, 1, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (31, N'Phạm Đăng', NULL, N'dangphce180896@fpt.edu.vn', NULL, NULL, N'Customer', CAST(N'2025-01-13T11:33:04.707' AS DateTime), N'', CAST(N'2025-01-13T11:33:04.707' AS DateTime), 1, N'', NULL, 1, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (33, N'H', N'z', N'h@gmail.com', N'U', N'4', N'Customer', CAST(N'2025-01-16T22:58:38.443' AS DateTime), N'f04ca7e9-0c01-4ca7-a102-2b624beada1f', CAST(N'2025-01-16T23:13:38.437' AS DateTime), 1, N'850285', NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (34, N'Phạm Quốc Tự', NULL, N'tuquoc0pro@gmail.com', NULL, NULL, N'Customer', CAST(N'2025-01-18T10:01:42.843' AS DateTime), N'', CAST(N'2025-01-18T10:01:42.843' AS DateTime), 1, N'', NULL, 1, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (35, N'Suri', NULL, N'suritran2580@gmail.com', NULL, NULL, N'Customer', CAST(N'2025-01-18T10:02:33.617' AS DateTime), N'', CAST(N'2025-01-18T10:02:33.617' AS DateTime), 1, N'', NULL, 1, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (36, N'phamtu', N'123456', N'quoctu.se@gmail.com', N'phamtu', N'0374054287', N'Customer', CAST(N'2025-01-18T10:05:01.927' AS DateTime), N'4500d578-88ae-450b-891c-deafafd735c5', CAST(N'2025-01-18T10:20:01.927' AS DateTime), 1, N'335324', NULL, NULL, NULL)
INSERT [dbo].[Users] ([UserID], [Username], [Password], [Email], [FullName], [Phone], [Role], [CreatedAt], [Token], [ExpireToken], [isTokenUsed], [VerificationCode], [Address], [Status], [Image]) VALUES (37, N'phamtudz', N'123456', N'0374054287@gmail.com', N'phamtu', N'0333358078', N'Customer', CAST(N'2025-01-18T10:08:01.950' AS DateTime), N'6007eb49-1e3b-4d10-aa2b-d8da99e4c3dd', CAST(N'2025-01-18T10:23:01.950' AS DateTime), 1, N'713364', NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
INSERT [dbo].[Wallet] ([WalletID], [UserID], [Balance], [LastUpdated]) VALUES (1, 1, CAST(10000000.00 AS Decimal(10, 2)), CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Wallet] ([WalletID], [UserID], [Balance], [LastUpdated]) VALUES (2, 2, CAST(1000000.00 AS Decimal(10, 2)), CAST(N'2024-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Wallet] ([WalletID], [UserID], [Balance], [LastUpdated]) VALUES (3, 3, CAST(5000000.00 AS Decimal(10, 2)), CAST(N'2024-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[WalletTransactions] ([TransactionID], [WalletID], [Amount], [TransactionDate], [Note]) VALUES (1, 1, CAST(10000000.00 AS Decimal(10, 2)), CAST(N'2024-01-01T00:00:00.000' AS DateTime), N'Thanh toán đơn hàng #1')
INSERT [dbo].[WalletTransactions] ([TransactionID], [WalletID], [Amount], [TransactionDate], [Note]) VALUES (2, 2, CAST(1000000.00 AS Decimal(10, 2)), CAST(N'2024-01-01T00:00:00.000' AS DateTime), N'Thanh toán đơn hàng #2')
INSERT [dbo].[WalletTransactions] ([TransactionID], [WalletID], [Amount], [TransactionDate], [Note]) VALUES (3, 3, CAST(5000000.00 AS Decimal(10, 2)), CAST(N'2024-01-01T00:00:00.000' AS DateTime), N'Thanh toán đơn hàng #3')
GO
ALTER TABLE [dbo].[Inventory] ADD  DEFAULT ((0)) FOR [Quantity]
GO
ALTER TABLE [dbo].[Inventory] ADD  DEFAULT (getdate()) FOR [LastUpdated]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT (getdate()) FOR [OrderDate]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT ((0)) FOR [ShippingFee]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT ('Pending') FOR [Status]
GO
ALTER TABLE [dbo].[Products] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Products] ADD  DEFAULT ('Available') FOR [Status]
GO
ALTER TABLE [dbo].[Promotions] ADD  DEFAULT ((1)) FOR [Status]
GO
ALTER TABLE [dbo].[Reviews] ADD  DEFAULT (getdate()) FOR [ReviewDate]
GO
ALTER TABLE [dbo].[Wallet] ADD  DEFAULT ((0)) FOR [Balance]
GO
ALTER TABLE [dbo].[Wallet] ADD  DEFAULT (getdate()) FOR [LastUpdated]
GO
ALTER TABLE [dbo].[WalletTransactions] ADD  DEFAULT (getdate()) FOR [TransactionDate]
GO
ALTER TABLE [dbo].[Inventory]  WITH CHECK ADD FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [PK_Orders_OrderDetails] FOREIGN KEY([OrderID])
REFERENCES [dbo].[Orders] ([OrderID])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [PK_Orders_OrderDetails]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([PaymentID])
REFERENCES [dbo].[Payment] ([PaymentID])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([ShipperID])
REFERENCES [dbo].[Shipper] ([ShipperID])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_UserID] FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_UserID]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Categories] ([CategoryID])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([PromotionID])
REFERENCES [dbo].[Promotions] ([PromotionID])
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD  CONSTRAINT [FK_Reviews_UserID] FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Reviews] CHECK CONSTRAINT [FK_Reviews_UserID]
GO
ALTER TABLE [dbo].[Shipper]  WITH CHECK ADD  CONSTRAINT [FK_Shipper_UserID] FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Shipper] CHECK CONSTRAINT [FK_Shipper_UserID]
GO
ALTER TABLE [dbo].[Wallet]  WITH CHECK ADD  CONSTRAINT [FK_Wallet_UserID] FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Wallet] CHECK CONSTRAINT [FK_Wallet_UserID]
GO
ALTER TABLE [dbo].[WalletTransactions]  WITH CHECK ADD FOREIGN KEY([WalletID])
REFERENCES [dbo].[Wallet] ([WalletID])
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD CHECK  (([Rating]>=(1) AND [Rating]<=(5)))
GO
USE [master]
GO
ALTER DATABASE [Light_House_Shop] SET  READ_WRITE 
GO
