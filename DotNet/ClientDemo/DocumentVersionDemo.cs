using Emc.Documentum.Rest.Sample.Http.DataModel;
using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample
{
    public class DocumentVersionDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get inline repositories feed --> \r\n\t" +
            " find repository from inline feed --> \r\n\t get inline cabinets feed --> \r\n\t find cabinet from inline feed --> \r\n\t " +
            "create document under cabinet --> \r\n\t checkout document --> \r\n\t checkin document with content --> \r\n\t get version history feed --> \r\n\t delete all versions" +
            "\r\nPressy any key to run > ");
            Console.ReadKey();

            Console.WriteLine("[GET @ home document resource at " + homeDocumentUri + "]");
            HomeDoc home = client.Get<HomeDoc>(homeDocumentUri);
            home.SetClient(client);
            if (printResult) Console.WriteLine(home);

            Console.WriteLine("[GET @ inline repositories feed resource]");
            Feed<Repository> repositories = home.GetRepositories<Repository>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(repositories);

            Console.WriteLine("[FIND @ repository '" + repositoryName + "' from the inline feed]");
            Repository repository = repositories.FindInlineEntry(repositoryName);
            if (printResult) Console.WriteLine(repository);

            Console.WriteLine("[GET @ inline cabinets feed resource]");
            Feed<Cabinet> cabinets = repository.GetCabinets<Cabinet>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(cabinets);

            Console.WriteLine("[FIND @ cabinet '" + client.UserName + "' from the inline feed]");
            Cabinet cabinet = cabinets.FindInlineEntry(client.UserName);
            if (printResult) Console.WriteLine(cabinet);

            Console.WriteLine("CREATE @ document resource to cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "']");
            Document subDoc = ObjectUtil.NewRandomDocument("sub-doc-");
            Document doc = cabinet.CreateSubDocument(subDoc, null);
            if (printResult) Console.WriteLine(doc);

            Console.WriteLine("[CHECKOUT @ document resource '" + ObjectUtil.FindProperty(doc, "object_name") + "']");
            Document checkedout = doc.Checkout(null);
            if (printResult) Console.WriteLine(checkedout);

            Console.WriteLine("CHECKIN @ document resource '" + ObjectUtil.FindProperty(doc, "object_name") + "' with both properties and content]");
            Document checkinDoc = ObjectUtil.NewRandomDocument("doc-v2-");
            FileInfo file = ObjectUtil.NewTextFileWithLength(2048);
            GenericOptions options = new GenericOptions();
            options.SetQuery("format", "crtext");
            options.SetQuery("page", 0);
            options.SetQuery("primary", true);
            options.SetQuery("version-label", "my-rest-demo");
            Document doc2 = checkedout.CheckinMinor(checkinDoc, file.OpenRead(), "text/plain", options);
            if (printResult) Console.WriteLine(doc2);

            Console.WriteLine("GET @ version history feed resource '" + ObjectUtil.FindProperty(doc2, "object_name") + "']");
            Feed<OutlineAtomContent> versions = doc2.GetVersionHistory<OutlineAtomContent>(null);
            if (printResult) Console.WriteLine(versions);

            Console.WriteLine("DELETE @ doc resource '" + ObjectUtil.FindProperty(doc2, "object_name") + "' with all versions]");
            GenericOptions options2 = new GenericOptions();
            options2.SetQuery("del-version", "all");
            doc2.Delete(options);
        }
    }
}
