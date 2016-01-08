using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    public partial class ContentMeta : PersistentObject
    {
        /// <summary>
        /// Get the associated document resource of this content
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document GetParentDocument(SingleGetOptions options)
        {
            return HttpUtil.GetSingleton<Document>(this.Links, LinkUtil.PARENT.Rel, this.Client, options);
        }

        /// <summary>
        /// Download the content media of this content to a local file
        /// </summary>
        /// <returns></returns>
        public FileInfo DownloadContentMedia()
        {
            string contentMediaUri = LinkUtil.FindLink(this.Links, LinkUtil.CONTENT_MEDIA.Rel);
            string fileName = (string)ObjectUtil.FindProperty(this, "object_name");
            string fileExtension = (string)ObjectUtil.FindProperty(this, "dos_extension");
            if (String.IsNullOrEmpty(fileName))
            {
                fileName = "temp-" + System.Guid.NewGuid().ToString();
            }
            if (String.IsNullOrEmpty(fileExtension))
            {
                fileName = "txt";
            }
            string fullPath = Path.Combine(Path.GetTempPath(), fileName + "." + fileExtension);

            using (Stream media = Client.GetRaw(contentMediaUri))
            {
                FileStream fs = File.Create(fullPath);
                media.CopyTo(fs);
                fs.Close();
            }

            return new FileInfo(fullPath); 
        }
        
    }
}
