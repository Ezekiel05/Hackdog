namespace Hackdog.Migrations
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<Hackdog.Models.HackdogAppDB>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(Hackdog.Models.HackdogAppDB context)
        {
            if (!context.SuperMarkets.Any())
            {
                context.SuperMarkets.AddOrUpdate(i => i.Id,
                    new Models.SuperMarket() { Id = 1, Name = "SM Supermarket" },
                    new Models.SuperMarket() { Id = 2, Name = "SM Hypermarket" }
                );
            }

            if (!context.Isles.Any())
            {
                context.Isles.AddOrUpdate(i => i.Id,
                    //Supermarket 1
                    new Models.Isle() { Id = 1, Name = "Beverages", SuperMarket = context.SuperMarkets.Find(1) },
                    new Models.Isle() { Id = 2, Name = "Household", SuperMarket = context.SuperMarkets.Find(1) },
                    new Models.Isle() { Id = 3, Name = "Dairy", SuperMarket = context.SuperMarkets.Find(1) },
                    new Models.Isle() { Id = 4, Name = "Imported Goods", SuperMarket = context.SuperMarkets.Find(1) },

                    //Supermarket 2
                    new Models.Isle() { Id = 5, Name = "Beverages", SuperMarket = context.SuperMarkets.Find(2) },
                    new Models.Isle() { Id = 6, Name = "Household", SuperMarket = context.SuperMarkets.Find(2) },
                    new Models.Isle() { Id = 7, Name = "Dairy", SuperMarket = context.SuperMarkets.Find(2) },
                    new Models.Isle() { Id = 8, Name = "Imported Goods", SuperMarket = context.SuperMarkets.Find(2) }
                );
            }

            if (!context.Products.Any())
            {
                context.Products.AddOrUpdate(b => b.Barcode,
                    //Supermarket 1
                    new Models.Product() { Barcode = "4800014534234", Stocks = 10, Name = "Nestea", Price = 24.75, Isle = context.Isles.Find(1), ExpirationDate = DateTime.Parse("03/13/2020") },
                    new Models.Product() { Barcode = "4801981107971", Stocks = 10, Name = "Wilkins Pure", Price = 16.00, Isle = context.Isles.Find(1), ExpirationDate = DateTime.Parse("06/20/2020") },
                    new Models.Product() { Barcode = "4800047865152", Stocks = 10, Name = "GreenCross Alcohol", Price = 29.75, Isle = context.Isles.Find(2), ExpirationDate = DateTime.Parse("06/21/2020") },
                    new Models.Product() { Barcode = "4809013300215", Stocks = 10, Name = "Kojic Acid Soap", Price = 50.25, Isle = context.Isles.Find(2), ExpirationDate = DateTime.Parse("06/22/2020") },
                    new Models.Product() { Barcode = "8850567104039", Stocks = 10, Name = "Milk", Price = 84.75, Isle = context.Isles.Find(3), ExpirationDate = DateTime.Parse("06/23/2020") },
                    new Models.Product() { Barcode = "4806502163924", Stocks = 10, Name = "Butter", Price = 43.75, Isle = context.Isles.Find(3), ExpirationDate = DateTime.Parse("06/24/2020") },
                    new Models.Product() { Barcode = "9904036413005", Stocks = 10, Name = "Nissin Cup Noodles Cheese Curry", Price = 119.75, Isle = context.Isles.Find(4), ExpirationDate = DateTime.Parse("06/24/2020") },

                    //Supermarket 2
                    new Models.Product() { Barcode = "4800014534234", Stocks = 10, Name = "Nestea", Price = 24.75, Isle = context.Isles.Find(5), ExpirationDate = DateTime.Parse("03/13/2020") },
                    new Models.Product() { Barcode = "4801981107971", Stocks = 10, Name = "Wilkins Pure", Price = 16.00, Isle = context.Isles.Find(5), ExpirationDate = DateTime.Parse("06/20/2020") },
                    new Models.Product() { Barcode = "4800047865152", Stocks = 10, Name = "GreenCross Alcohol", Price = 29.75, Isle = context.Isles.Find(6), ExpirationDate = DateTime.Parse("06/21/2020") },
                    new Models.Product() { Barcode = "4809013300215", Stocks = 10, Name = "Kojic Acid Soap", Price = 50.25, Isle = context.Isles.Find(6), ExpirationDate = DateTime.Parse("06/22/2020") },
                    new Models.Product() { Barcode = "8850567104039", Stocks = 10, Name = "Milk", Price = 84.75, Isle = context.Isles.Find(7), ExpirationDate = DateTime.Parse("06/23/2020") },
                    new Models.Product() { Barcode = "4806502163924", Stocks = 10, Name = "Butter", Price = 43.75, Isle = context.Isles.Find(7), ExpirationDate = DateTime.Parse("06/24/2020") },
                    new Models.Product() { Barcode = "9904036413005", Stocks = 10, Name = "Nissin Cup Noodles Cheese Curry", Price = 119.75, Isle = context.Isles.Find(8), ExpirationDate = DateTime.Parse("06/24/2020") }
                );
            }
        }
    }
}
