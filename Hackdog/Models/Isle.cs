using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Hackdog.Models
{
    public class Isle
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public SuperMarket SuperMarket { get; set; }
    }
}