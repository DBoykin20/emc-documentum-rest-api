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
    public class DocumentDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get inline repositories feed --> \r\n\t" +
            " find repository from inline feed --> \r\n\t get inline cabinets feed --> \r\n\t find cabinet from inline feed --> \r\n\t " +
            "create document under cabinet --> \r\n\t get documents under cabinet --> \r\n\t update document --> \r\n\t delete document" +
            "\r\nPressy any key to run > ");
            Console.ReadKey();

            Console.WriteLine("[GET @ home document resource at " + homeDocumentUri + "]");
            HomeDoc home = client.Get<HomeDoc>(homeDocumentUri);
            home.SetClient(client);
            if (printResult) Console.WriteLine(home);

            Console.WriteLine("[GET @ inline repositories feed resource]");
            Feed<Repository> repositories = home.GetRepositories<Repository>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(repositories);

            Console.WriteLine("[FIND @ repository resource '" + repositoryName + "' from the inline feed]");
            Repository repository = repositories.FindInlineEntry(repositoryName);
            if (printResult) Console.WriteLine(repository);

            Console.WriteLine("[GET @ inline cabinets feed resource]");
            Feed<Cabinet> cabinets = repository.GetCabinets<Cabinet>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(cabinets);

            Console.WriteLine("[FIND @ cabinet '" + client.UserName + "' from the inline feed]");
            Cabinet cabinet = cabinets.FindInlineEntry(client.UserName);
            if (printResult) Console.WriteLine(cabinet);

            Console.WriteLine("CREATE @ document resource to the cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "']");
            Document subDoc = ObjectUtil.NewRandomDocument("sub-doc-");
            Document doc = cabinet.CreateSubDocument(subDoc, null);
            if (printResult) Console.WriteLine(doc);

            Console.WriteLine("[GET @ documents resource under the cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "]");
            Feed<OutlineAtomContent> docs = cabinet.GetDocuments<OutlineAtomContent>(new FeedGetOptions { Inline = false});
            if (printResult) Console.WriteLine(docs);

            Console.WriteLine("UPDATE @ document resource '" + ObjectUtil.FindProperty(doc, "object_name") + "']");
            Document updateDoc = ObjectUtil.NewRandomDocument("sub-doc-modify-");
            Document doc2 = doc.PartialUpdate(updateDoc);
            if (printResult) Console.WriteLine(doc2);

            Console.WriteLine("DELETE @ doc resource '" + ObjectUtil.FindProperty(doc2, "object_name") + "']");           
            doc2.Delete(null);
        }
    }
}
