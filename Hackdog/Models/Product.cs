using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Hackdog.Models
{
    public class Product
    {
        public int Id { get; set; }
        public string Barcode { get; set; }
        public string Name { get; set; }
        public double Price { get; set; }
        public DateTime ExpirationDate { get; set; }
        public int Stocks { get; set; }
        public Isle Isle { get; set; }
    }
}