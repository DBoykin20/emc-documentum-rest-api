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
    public class DocumentCopyMoveLinkDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get inline repositories feed --> \r\n\t" +
            " find repository from inline feed --> \r\n\t get inline cabinets feed --> \r\n\t find cabinet from inline feed --> \r\n\t " +
            "create folder a under cabinet --> \r\n\t create folder b under folder a --> \r\n\t create folder c under folder b --> \r\n\t " +
            "create document under cabinet --> \r\n\t copy document to folder a --> \r\n\t link document to folder b --> \r\n\t " +
            "link document to folder c --> \r\n\t get inline document's folder links feed --> \r\n\t find document's primary folder link with cabinet from inline feed --> \r\n\t " +
            "move document's primary folder link from cabinet to folder a --> \r\n\t delete document's folder link with folder a --> \r\n\t deep delete folder a --> \r\n\t " +
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

            Console.WriteLine("CREATE @ folder resource to the cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "']");
            Folder subFolder = ObjectUtil.NewRandomFolder("sub-folderA-");
            Folder folder = cabinet.CreateSubFolder(subFolder, null);
            if (printResult) Console.WriteLine(folder);

            Console.WriteLine("CREATE @ folder resource to the folder '" + ObjectUtil.FindProperty(folder, "object_name") + "']");
            Folder subFolder2 = ObjectUtil.NewRandomFolder("sub-folderB-");
            Folder folder2 = folder.CreateSubFolder(subFolder2, null);
            if (printResult) Console.WriteLine(folder2);

            Console.WriteLine("CREATE @ folder resource to the folder '" + ObjectUtil.FindProperty(folder2, "object_name") + "']");
            Folder subFolder3 = ObjectUtil.NewRandomFolder("sub-folderC-");
            Folder folder3 = folder2.CreateSubFolder(subFolder3, null);
            if (printResult) Console.WriteLine(folder3);

            Console.WriteLine("CREATE @ document resource to the cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "']");
            Document subDoc = ObjectUtil.NewRandomDocument("sub-doc-");
            Document doc = cabinet.CreateSubDocument(subDoc, null);
            if (printResult) Console.WriteLine(doc);

            Console.WriteLine("[COPY @ document resource '" + ObjectUtil.FindProperty(folder, "object_name") + "' to folder '" + ObjectUtil.FindProperty(folder, "object_name") + "]");
            Document copyDoc = doc.GetHrefObject<Document>();
            Document copiedDoc = folder.CreateSubObject<Document>(copyDoc, null);
            if (printResult) Console.WriteLine(copiedDoc);

            Console.WriteLine("LINK @ document resource '" + ObjectUtil.FindProperty(doc, "object_name") + "' to folder '" + ObjectUtil.FindProperty(folder2, "object_name") + "]");
            Document linkDoc = doc.GetHrefObject<Document>();
            FolderLink folderLink = folder2.LinkFrom(linkDoc, null);
            if (printResult) Console.WriteLine(folderLink);

            Console.WriteLine("LINK @ document resource '" + ObjectUtil.FindProperty(doc, "object_name") + "' to folder '" + ObjectUtil.FindProperty(folder3, "object_name") + "]");
            Folder linkFolder = folder3.GetHrefObject<Folder>();
            FolderLink folderLink2 = doc.LinkTo(linkFolder, null);
            if (printResult) Console.WriteLine(folderLink2);

            Console.WriteLine("[GET @ inline document folder links feed resource]");
            Feed<FolderLink> links = doc.GetFolderLinks(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(links);

            Console.WriteLine("FIND @ document folder link between doc '" + ObjectUtil.FindProperty(doc, "object_name") + "' and cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "' from the inline feed]");
            FolderLink folderLink3 = links.FindInlineEntryBySummary((string)ObjectUtil.FindProperty(cabinet, "r_object_id"));
            if (printResult) Console.WriteLine(folderLink3);

            Console.WriteLine("MOVE @ document resource '" + ObjectUtil.FindProperty(doc, "object_name") + 
                "' from cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") +
                "' to folder '" + ObjectUtil.FindProperty(folder, "object_name") + "']");
            Folder moveTo = folder.GetHrefObject<Folder>();
            FolderLink movedFolderLink = folderLink3.MoveTo(moveTo, null);
            if (printResult) Console.WriteLine(movedFolderLink);

            Console.WriteLine("DELETE @ document folder link between doc '" + ObjectUtil.FindProperty(doc, "object_name") + "' and folder '" + ObjectUtil.FindProperty(folder, "object_name") + "']");
            movedFolderLink.Remove();

            Console.WriteLine("DELETE @ folder resource '" + ObjectUtil.FindProperty(folder, "object_name") + "' with all decendeants]");
            GenericOptions options = new GenericOptions();
            options.SetQuery("del-non-empty", true);
            options.SetQuery("del-all-links", true);
            folder.Delete(options);
        }
    }
}
