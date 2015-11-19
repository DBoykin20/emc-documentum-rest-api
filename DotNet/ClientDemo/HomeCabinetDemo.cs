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
    public class HomeCabinetDemo
    {
        public static void Run(RawHttpClient client, string homeDocumentUri, string repositoryName, bool printResult)
        {
            Console.Write("Workflow of below demo: \r\n\t get home document --> \r\n\t get inline repositories feed --> \r\n\t " +
            "find repository from inline feed --> \r\n\t get current user --> \r\n\t get user default folder" +
            "\r\nPressy any key to run > ");
            Console.ReadKey();

            Console.WriteLine("\r\n[GET @ home document resource at " + homeDocumentUri + "]");
            HomeDoc home = client.Get<HomeDoc>(homeDocumentUri);
            home.SetClient(client);
            if (printResult) Console.WriteLine(home);

            Console.WriteLine("\r\n[GET @ inline repositories feed resource]");
            Feed<Repository> repositories = home.GetRepositories<Repository>(new FeedGetOptions { Inline = true, Links = true });
            if (printResult) Console.WriteLine(repositories);

            Console.WriteLine("\r\n[FIND @ repository '" + repositoryName + "'from the inline feed]");
            Repository repository = repositories.FindInlineEntry(repositoryName);
            if (printResult) Console.WriteLine(repository);

            Console.WriteLine("\r\n[GET @ current user resource from the repository]");
            User currentUser = repository.GetCurrentUser(null);
            if (printResult) Console.WriteLine(currentUser);

            Console.WriteLine("\r\nGET @ default folder resource for user '" + ObjectUtil.FindProperty(currentUser, "user_name") + "' with customized view and links]");
            Folder homeCabinet = currentUser.GetHomeCabinet(new SingleGetOptions { View="object_name,r_object_id,r_folder_path", Links = false});
            if (printResult) Console.WriteLine(homeCabinet);
        }
    }
}
