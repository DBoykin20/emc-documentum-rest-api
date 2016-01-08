using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample
{
    class CommandlineMenu
    {
        public static char PrintMenu()
        {
            
            Console.WriteLine("Please choose the demo instance to run:");
            Console.WriteLine("  1 ----- Repository Demo");
            Console.WriteLine("  2 ----- User Home Cabinet Demo");
            Console.WriteLine("  3 ----- Folder Demo");
            Console.WriteLine("  4 ----- Document Demo");
            Console.WriteLine("  5 ----- Document Content Demo");
            Console.WriteLine("  6 ----- Document Version Demo");
            Console.WriteLine("  7 ----- Document Copy Move Link Demo");
            Console.WriteLine("  8 ----- DQL Query Demo");
            Console.WriteLine("  a ----- All");
            Console.WriteLine("  c ----- Clear the console");
            Console.WriteLine("  x ----- Exit the demo");
            Console.Write("\r\nCommand > ");

            ConsoleKeyInfo key = Console.ReadKey();
            Console.WriteLine();
            return key.KeyChar;
        }
    }
}
