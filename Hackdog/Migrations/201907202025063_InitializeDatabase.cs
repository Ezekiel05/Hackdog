namespace Hackdog.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitializeDatabase : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Carts",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Username = c.String(),
                        Count = c.Int(nullable: false),
                        TotalPrice = c.Double(nullable: false),
                        IsPurchased = c.Boolean(nullable: false),
                        IsForPurchase = c.Boolean(nullable: false),
                        IsAddedAsWishlist = c.Boolean(nullable: false),
                        Product_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Products", t => t.Product_Id)
                .Index(t => t.Product_Id);
            
            CreateTable(
                "dbo.Products",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Barcode = c.String(),
                        Name = c.String(),
                        Price = c.Double(nullable: false),
                        Description = c.String(),
                        Stocks = c.Int(nullable: false),
                        Isle_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Isles", t => t.Isle_Id)
                .Index(t => t.Isle_Id);
            
            CreateTable(
                "dbo.Isles",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        SuperMarket_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.SuperMarkets", t => t.SuperMarket_Id)
                .Index(t => t.SuperMarket_Id);
            
            CreateTable(
                "dbo.SuperMarkets",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Carts", "Product_Id", "dbo.Products");
            DropForeignKey("dbo.Products", "Isle_Id", "dbo.Isles");
            DropForeignKey("dbo.Isles", "SuperMarket_Id", "dbo.SuperMarkets");
            DropIndex("dbo.Isles", new[] { "SuperMarket_Id" });
            DropIndex("dbo.Products", new[] { "Isle_Id" });
            DropIndex("dbo.Carts", new[] { "Product_Id" });
            DropTable("dbo.SuperMarkets");
            DropTable("dbo.Isles");
            DropTable("dbo.Products");
            DropTable("dbo.Carts");
        }
    }
}
