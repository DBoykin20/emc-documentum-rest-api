using Emc.Documentum.Rest.Sample.Http.DataModel;
using Emc.Documentum.Rest.Sample.Http.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample
{
    public class RepositoryDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get product info --> \r\n\t" +
            " get repositories feed --> \r\n\t get repository --> \r\n\t get inline repositories feed --> \r\n\t find repository from inline feed" +
            "\r\nPressy any key to run > ");
            Console.ReadKey();

            Console.WriteLine("\r\n[GET @ home document resource at " + homeDocumentUri + "]");
            HomeDoc home = client.Get<HomeDoc>(homeDocumentUri);
            home.SetClient(client);
            if (printResult) Console.WriteLine(home.ToString());

            Console.WriteLine("\r\n[GET @ product info resource]");
            ProductInfo productInfo = home.GetProductInfo();
            if (printResult) Console.WriteLine(productInfo.ToString());

            Console.WriteLine("\r\n[GET @ repositories feed resource]");
            Feed<OutlineAtomContent> repositories = home.GetRepositories<OutlineAtomContent>(null);
            if (printResult) Console.WriteLine(repositories.ToString());

            Console.WriteLine("\r\n[GET @ repository resource '" + repositoryName + "']");
            Repository repository = repositories.GetEntry<Repository>(repositoryName);
            if (printResult) Console.WriteLine(repository.ToString());

            Console.WriteLine("\r\n[GET @ inline repositories feed resource]");
            Feed<Repository> repositories2 = home.GetRepositories<Repository>(new FeedGetOptions { Inline = true });
            if (printResult) Console.WriteLine(repositories2.ToString());

            Console.WriteLine("\r\n[FIND @ repository '" + repositoryName + "' from the inline feed]");
            Repository repository2 = repositories2.FindInlineEntry(repositoryName);
            if (printResult) Console.WriteLine(repository2.ToString());
        }
    }
}
