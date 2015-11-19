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
    public class FolderDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get inline repositories feed --> \r\n\t" +
            " find repository from inline feed --> \r\n\t get inline cabinets feed --> \r\n\t find cabinet from inline feed --> \r\n\t " + 
            "create folder under cabinet --> \r\n\t get folders under cabinet --> \r\n\t update folder --> \r\n\t delete folder" +
            "\r\nPressy any key to run > ");
            Console.ReadKey();

            Console.WriteLine("\r\n[GET @ home document resource at " + homeDocumentUri + "]");
            HomeDoc home = client.Get<HomeDoc>(homeDocumentUri);
            home.SetClient(client);
            if (printResult) Console.WriteLine(home);

            Console.WriteLine("\r\n[GET @ inline repositories feed resource]");
            Feed<Repository> repositories = home.GetRepositories<Repository>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(repositories);

            Console.WriteLine("\r\n[FIND @ repository '" + repositoryName + "' from the inline feed]");
            Repository repository = repositories.FindInlineEntry(repositoryName);
            if (printResult) Console.WriteLine(repository);

            Console.WriteLine("\r\n[GET @ inline cabinets resource]");
            Feed<Cabinet> cabinets = repository.GetCabinets<Cabinet>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(cabinets);

            Console.WriteLine("\r\n[FIND @ cabinet '" + client.UserName + "' from the inline feed]");
            Cabinet cabinet = cabinets.FindInlineEntry(client.UserName);
            if (printResult) Console.WriteLine(cabinet);

            Console.WriteLine("\r\n[CREATE @ folder resource under cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "']");
            Folder subFolder = ObjectUtil.NewRandomFolder("sub-folder-");
            Folder folder = cabinet.CreateSubFolder(subFolder, null);
            if (printResult) Console.WriteLine(folder);

            Console.WriteLine("\r\n[GET @ folders feed resource under cabinet '" + ObjectUtil.FindProperty(cabinet, "object_name") + "' with customized filter and view]");
            Feed<Folder> folders = cabinet.GetFolders<Folder>(new FeedGetOptions { Inline = true, Filter = "starts-with(object_name, 'sub-folder-')", View = ":all"});
            if (printResult) Console.WriteLine(folders);

            Console.WriteLine("\r\n[UPDATE @ folder resource '" + ObjectUtil.FindProperty(folder, "object_name") + "']");
            Folder updateFolder = ObjectUtil.NewRandomFolder("sub-folder-modify-");
            Folder folder2 = folder.PartialUpdate(updateFolder);
            if (printResult) Console.WriteLine(folder2);

            Console.WriteLine("\r\n[DELETE @ folder resource '" + ObjectUtil.FindProperty(folder2, "object_name") + "']");
            folder.Delete(null);
        }
    }
}
