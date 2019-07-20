using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Hackdog.Models
{
    public class Cart
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public Product Product { get; set; }
        public int Count { get; set; }
        public double TotalPrice { get; set; }
        public bool IsPurchased { get; set; }
        public bool IsForPurchase { get; set; }
        public bool IsAddedAsWishlist { get; set; }
    }
}