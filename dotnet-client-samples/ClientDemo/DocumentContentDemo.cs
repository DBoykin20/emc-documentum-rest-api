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
    public class DocumentContentDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get inline repositories feed --> \r\n\t" +
            " find repository from inline feed --> \r\n\t get inline cabinets feed --> \r\n\t find cabinet from inline feed --> \r\n\t " +
            "import document and content under cabinet --> \r\n\t get document primary content --> \r\n\t download primary content --> \r\n\t " +
            "add rendition --> \r\n\t get document contents feed --> \r\n\tdelete primary content --> \r\n\t delete document" +
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

            Console.WriteLine("IMPORT @ document resource to the cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "' with primary content]");
            Document subDoc = ObjectUtil.NewRandomDocument("sub-doc-");
            FileInfo file = ObjectUtil.NewTextFileWithLength(1024);
            GenericOptions options = new GenericOptions();
            options.SetQuery("format", "text");
            Document doc = cabinet.ImportDocumentWithContent(subDoc, file.OpenRead(), "text/plain", options);
            file.Delete();
            if (printResult) Console.WriteLine(doc);

            Console.WriteLine("GET @ primary content resource for the document '" + ObjectUtil.FindProperty(doc, "object_name") + "']");
            SingleGetOptions options2 = new SingleGetOptions();
            options2.SetQuery("media-url-policy", "local");
            ContentMeta primaryContentMeta = doc.GetPrimaryContent(options2);
            if (printResult) Console.WriteLine(primaryContentMeta);

            Console.WriteLine("DOWNLOAD @ primary content media for the document '" + ObjectUtil.FindProperty(doc, "object_name") + "']");
            FileInfo downloadedContentFile = primaryContentMeta.DownloadContentMedia();
            if (printResult) Console.WriteLine("The content file is downloaded to: " + downloadedContentFile);
            downloadedContentFile.Delete();

            Console.WriteLine("CREATE @ additional content (rendition) resource for the document '" + ObjectUtil.FindProperty(doc, "object_name") + "']");
            FileInfo file2 = ObjectUtil.NewTextFileWithLength(2048);
            GenericOptions options3 = new GenericOptions();
            options3.SetQuery("format", "crtext");
            options3.SetQuery("page", 0);
            options3.SetQuery("primary", false);
            ContentMeta renditionMeta = doc.CreateContent(file2.OpenRead(), "text/plain", options3);
            if (printResult) Console.WriteLine(renditionMeta);

            Console.WriteLine("[GET @ contents feed resource for the document '" + ObjectUtil.FindProperty(doc, "object_name") + "]");
            Feed<ContentMeta> contents = doc.GetContents<ContentMeta>(new FeedGetOptions { Inline = true });
            if (printResult) Console.WriteLine(contents);

            Console.WriteLine("DELETE @ primary content resource for the document '" + ObjectUtil.FindProperty(doc, "object_name") + "']");
            primaryContentMeta.Delete(null);

            Console.WriteLine("DELETE @ doc resource '" + ObjectUtil.FindProperty(doc, "object_name") + "']");
            doc.Delete(null);
        }
    }
}
