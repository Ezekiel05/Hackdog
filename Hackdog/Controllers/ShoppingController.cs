using Hackdog.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Migrations;
using System.Linq;
using System.Web.Http;
using System.Web.Http.Description;

namespace Hackdog.Controllers
{
    [RoutePrefix("api/v1")]
    public class ShoppingController : ApiController
    {
        private HackdogAppDB db = new HackdogAppDB();

        [HttpGet]
        [Route("GetAllSuperMarkets")]
        [ResponseType(typeof(IList<SuperMarket>))]
        public IHttpActionResult GetAllSuperMarkets()
        {
            IList<SuperMarket> superMarkets = db.SuperMarkets.ToList();

            return Ok(superMarkets);
        }

        [HttpGet]
        [Route("GetAllIsles")]
        [ResponseType(typeof(IList<Isle>))]
        public IHttpActionResult GetAllIsles()
        {
            IList<Isle> isles = db.Isles
                .Include("Supermarket")
                .ToList();

            return Ok(isles);
        }

        [HttpGet]
        [Route("GetAllProducts")]
        [ResponseType(typeof(IList<Product>))]
        public IHttpActionResult GetAllProducts()
        {
            List<Product> products = db.Products
                .Include("Isle")
                .ToList();

            IList<int> isleIds = products.Select(i => i.Isle.Id).ToList();

            IList<Isle> isles = db.Isles
                .Include("Supermarket")
                .Where(i => isleIds.Contains(i.Id))
                .ToList();

            products.ForEach(i =>
                i.Isle.SuperMarket = isles
                    .Where(s => i.Isle.Id == s.Id)
                    .Select(s => s.SuperMarket)
                    .FirstOrDefault()
             );

            return Ok(products);
        }

        [HttpGet]
        [Route("GetIslesBySupermarketId")]
        [ResponseType(typeof(IList<Isle>))]
        public IHttpActionResult GetIslesBySupermarketId(int Id)
        {
            IList<Isle> isles = db.Isles
                .Include("Supermarket")
                .Where(s => s.SuperMarket.Id.Equals(Id))
                .ToList();

            return Ok(isles);
        }

        [HttpGet]
        [Route("GetProductsBySupermarketId")]
        [ResponseType(typeof(IList<Product>))]
        public IHttpActionResult GetProductsBySupermarketId(int Id)
        {
            List<Product> products = db.Products
                .Include("Isle")
                .ToList();

            IList<int> isleIds = products.Select(i => i.Isle.Id).ToList();

            IList<Isle> isles = db.Isles
                .Include("Supermarket")
                .Where(i => i.Id.Equals(Id))
                .ToList();

            products.ForEach(i =>
                i.Isle.SuperMarket = isles
                    .Where(s => i.Isle.Id == s.Id)
                    .Select(s => s.SuperMarket)
                    .FirstOrDefault()
             );

            return Ok(products);
        }

        [HttpGet]
        [Route("GetProductsByIsleId")]
        [ResponseType(typeof(IList<Product>))]
        public IHttpActionResult GetProductsByIsleId(int Id)
        {
            List<Product> products = db.Products
                .Include("Isle")
                .Where(i => i.Isle.Id.Equals(Id))
                .ToList();

            IList<int> isleIds = products.Select(i => i.Isle.Id).ToList();

            IList<Isle> isles = db.Isles
                .Include("Supermarket")
                .Where(i => isleIds.Contains(i.Id))
                .ToList();

            products.ForEach(i =>
                i.Isle.SuperMarket = isles
                    .Where(s => i.Isle.Id == s.Id)
                    .Select(s => s.SuperMarket)
                    .FirstOrDefault()
             );

            return Ok(products);
        }

        [HttpGet]
        [Route("GetProductsByBarcode")]
        [ResponseType(typeof(IList<Product>))]
        public IHttpActionResult GetProductsByBarcode(string barcode)
        {
            List<Product> products = db.Products
                .Include("Isle")
                .Where(b => b.Barcode.Equals(barcode))
                .ToList();

            IList<int> isleIds = products.Select(i => i.Isle.Id).ToList();

            IList<Isle> isles = db.Isles
                .Include("Supermarket")
                .Where(i => isleIds.Contains(i.Id))
                .ToList();

            products.ForEach(i =>
                i.Isle.SuperMarket = isles
                    .Where(s => i.Isle.Id == s.Id)
                    .Select(s => s.SuperMarket)
                    .FirstOrDefault()
             );

            return Ok(products);
        }

        [HttpPost]
        [Route("AddToCart")]
        public IHttpActionResult AddToCart(string username, int productId, int count, bool IsWishListItem)
        {
            Product product = db.Products.Where(p => p.Id.Equals(productId)).FirstOrDefault();
            if (product.Stocks >= count)
            {
                product.Stocks -= count;
                db.Products.AddOrUpdate(product);

                Cart cart = new Cart()
                {
                    Username = username,
                    Product = product,
                    Count = count,
                    TotalPrice = product.Price * count,
                    IsPurchased = false,
                    IsAddedAsWishlist = IsWishListItem
                };

                db.Carts.Add(cart);
                db.SaveChanges();

                return Ok();
            }
            else
            {
                return BadRequest();
            }
        }

        [HttpPost]
        [Route("PurchaseCheckedCartItems")]
        public IHttpActionResult PurchaseCheckedCartItems(string username, IList<Cart> cartItems)
        {
            Cart[] selectedItems = cartItems.Where(i => !i.IsPurchased && i.IsForPurchase).ToArray();
            db.Carts.AddOrUpdate(selectedItems);
            db.SaveChanges();

            return Ok();
        }

        [HttpGet]
        [ResponseType(typeof(Cart))]
        [Route("ShowCartItemsByUser")]
        public IHttpActionResult ShowCartItemsByUser(string username)
        {
            IList<Cart> cartItems = db.Carts.Include("Product").ToList();
            return Ok(cartItems);
        }

        [HttpGet]
        [ResponseType(typeof(Cart))]
        [Route("RemoveCartItem")]
        public IHttpActionResult RemoveCartItems(string username, IList<Cart> cartItems)
        {
            db.Carts.RemoveRange(cartItems);
            db.SaveChanges();
            return Ok();
        }

        [HttpPost]
        [ResponseType(typeof(Product))]
        [Route("AddNewProduct")]
        public IHttpActionResult AddNewProduct(string barcode, string name, double price, DateTime expirationDate, int stocks, int isleId)
        {
            Isle isle = db.Isles.Find(isleId);

            Product product = new Product()
            {
                Barcode = barcode,
                Name = name,
                Price = price,
                ExpirationDate = expirationDate,
                Stocks = stocks,
                Isle = isle
            };

            db.Products.Add(product);
            db.SaveChanges();

            return Ok();
        }
    }
}
