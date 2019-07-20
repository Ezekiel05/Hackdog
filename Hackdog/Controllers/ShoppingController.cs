using Hackdog.Models;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Http.Description;

namespace Hackdog.Controllers
{
    public class ShoppingController : ApiController
    {
        private HackdogAppDB db = new HackdogAppDB();

        [HttpGet]
        [Route("api/v1/GetSuperMarkets")]
        [ResponseType(typeof(SuperMarket))]
        public IHttpActionResult GetSuperMarkets()
        {
            IList<SuperMarket> superMarkets = db.SuperMarkets.ToList();

            return Ok(superMarkets);
        }

        [HttpGet]
        [Route("api/v1/GetIslesBySupermarketId")]
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
        [Route("api/v1/GetProductsByIsleId")]
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

        [HttpPost]
        [Route("api/v1/AddToCart")]
        public IHttpActionResult AddToCart(string username, int productId, int count)
        {
            Product product = db.Products.Where(p => p.Id.Equals(productId)).FirstOrDefault();

            Cart cart = new Cart()
            {
                Username = username,
                Product = product,
                Count = count,
                TotalPrice = product.Price * count
            };

            db.Carts.Add(cart);
            db.SaveChanges();

            return Ok();
        }

        [HttpGet]
        [ResponseType(typeof(Cart))]
        [Route("api/v1/ShoShowCartItems")]
        public IHttpActionResult ShowCartItems(string username)
        {
            IList<Cart> cartItems = db.Carts.Include("Product").ToList();
            return Ok(cartItems);
        }

        [HttpGet]
        [ResponseType(typeof(Cart))]
        [Route("api/v1/RemoveCartItem")]
        public IHttpActionResult RemoveCartItems(string username, int cartItemId)
        {
            Cart cartItemToRemove = db.Carts.Where(r => r.Id.Equals(cartItemId)).FirstOrDefault();
            db.Carts.Remove(cartItemToRemove);
            return Ok();
        }
    }
}
