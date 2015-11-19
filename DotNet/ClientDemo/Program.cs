using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;
using Emc.Documentum.Rest.Sample.Http.DataModel;
using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System.IO;

namespace Emc.Documentum.Rest.Sample
{
    class Program
    {
        private static string homeDocumentUri;
        private static string username;
        private static string password;
        private static RawHttpClient client;
        private static string repositoryName;
        private static bool printResult;

        static void Main(string[] args)
        {   
            // HttpClient setup
            SetupTestData();

            char command = CommandlineMenu.PrintMenu();
            while(command != 'x')
            {
                switch(command)
                {
                    case 'a':
                        RepositoryDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        HomeCabinetDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        FolderDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        DocumentDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        DocumentContentDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        DocumentVersionDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        DqlQueryDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '1' :
                        RepositoryDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '2':
                        HomeCabinetDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '3':
                        FolderDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '4':
                        DocumentDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '5':
                        DocumentContentDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '6':
                        DocumentVersionDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '7':
                        DocumentCopyMoveLinkDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case '8':
                        DqlQueryDemo.Run(client, homeDocumentUri, repositoryName, printResult);
                        break;
                    case 'c':
                        Console.Clear();
                        break;
                    default:
                        if (command != 'x')
                        {
                            Console.WriteLine("Wrong command: '" + command + "', please try again...\r\n");
                        }                       
                        break;
                }
                Console.WriteLine("\r\nDone!!! Press any key to continue...\r\n");
                Console.ReadKey();
                command = CommandlineMenu.PrintMenu();
            }
        }

        private static void SetupTestData()
        {
            Console.WriteLine("$$$$ Demo for the Documentum REST .NET Client Reference Implementation$$$$\r\n");
            string defaultHomeDocumentUrl = @"http://localhost:8080/dctm-rest/services";
            Console.Write("Set the home document URL [" + defaultHomeDocumentUrl + "] :");
            homeDocumentUri = Console.ReadLine();
            if (String.IsNullOrEmpty(homeDocumentUri))
            {
                homeDocumentUri = defaultHomeDocumentUrl;
            }
            string defaultUsername = "dmadmin";
            Console.Write("Set the username [" + defaultUsername + "] :");
            username = Console.ReadLine();
            if (String.IsNullOrEmpty(username))
            {
                username = defaultUsername;
            }
            string defaultPassword = "password";
            Console.Write("Set the user password [" + defaultPassword + "] :");
            password = Console.ReadLine();
            if (String.IsNullOrEmpty(password))
            {
                password = defaultPassword;
            }
            string defaultRepositoryName = "acme";
            Console.Write("Set the repository name [" + defaultRepositoryName + "] :");
            repositoryName = Console.ReadLine();
            if (String.IsNullOrEmpty(repositoryName))
            {
                repositoryName = defaultRepositoryName;
            }
            string defaultPrintResult = "true";
            Console.Write("Whether to print the result [" + defaultPrintResult + "] :");
            string input = Console.ReadLine();
            printResult = String.IsNullOrEmpty(input) ? Boolean.Parse(defaultPrintResult) : Boolean.Parse(input);

            client = new RawHttpClient(username, password);
            // alternatively, you can choose .net default data contract serializer: new DefaultDataContractJsonSerializer();
            client.JsonSerializer = new JsonDotnetJsonSerializer();
            Console.WriteLine();
        }
    }
}
