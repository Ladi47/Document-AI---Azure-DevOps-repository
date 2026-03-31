using System;
using Newtonsoft.Json;

namespace SampleProject
{
    class Program
    {
        static void Main(string[] args)
        {
            var demo = new { Message = "Hello Document AI" };
            string json = JsonConvert.SerializeObject(demo);
            Console.WriteLine(json);
        }
    }
}