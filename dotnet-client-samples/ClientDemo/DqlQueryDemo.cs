using Emc.Documentum.Rest.Sample.Http.DataModel;
using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample
{
    public class DqlQueryDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get inline repositories feed --> \r\n\t" +
            " find repository from inline feed --> \r\n\t execute DQL" +
            "\r\nPressy any key to run > ");
            Console.ReadKey();

            Console.WriteLine("[GET @ home document resource at " + homeDocumentUri + "]");
            HomeDoc home = client.Get<HomeDoc>(homeDocumentUri);
            home.SetClient(client);
            if (printResult) Console.WriteLine(home);

            Console.WriteLine("[GET @ inline repositories feed resource]");
            Feed<Repository> repositories = home.GetRepositories<Repository>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(repositories);

            Console.WriteLine("[FIND @ repository '" + repositoryName + "'from the inline feed]");
            Repository repository = repositories.FindInlineEntry(repositoryName);
            if (printResult) Console.WriteLine(repository);

            Console.WriteLine("EXECUTE @ DQL query on repository '" + repositoryName + "'], with page size 2");
            Feed<PersistentObject> queryResult = repository.ExecuteDQL("select * from dm_cabinet", new FeedGetOptions() { ItemsPerPage = 2});
            if (printResult) Console.WriteLine(queryResult);
        }
    }
}
