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
    public partial class Cabinet : PersistentObject
    {
        /// <summary>
        /// Whether this cabinet can be updated
        /// </summary>
        /// <returns></returns>
        public bool CanUpdate()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.EDIT.Rel) != null;
        }

        /// <summary>
        /// Whether this cabinet can be deleted
        /// </summary>
        /// <returns></returns>
        public bool CanDelete()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.DELETE.Rel) != null;
        }

        /// <summary>
        /// Get child folders from this cabinet
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetFolders<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                LinkUtil.FOLDERS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get child documents from this cabinet
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetDocuments<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                LinkUtil.DOCUMENTS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Create a sub folder resource under this folder
        /// </summary>
        /// <param name="newObj"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Folder CreateSubFolder(Folder newObj, GenericOptions options)
        {
            return HttpUtil.Post<Folder>(
                this.Links,
                LinkUtil.FOLDERS.Rel,
                newObj,
                this.Client,
                options);
        }

        /// <summary>
        /// Create a document resource under this cabinet
        /// </summary>
        /// <param name="newObj"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document CreateSubDocument(Document newObj, GenericOptions options)
        {
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.DOCUMENTS.Rel,
                newObj,
                this.Client,
                options);
        }

        /// <summary>
        /// Import a contentful document with content stream under this cabinet
        /// </summary>
        /// <param name="newObj"></param>
        /// <param name="otherPartStream"></param>
        /// <param name="otherPartMime"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document ImportDocumentWithContent(Document newObj, Stream otherPartStream, string otherPartMime, GenericOptions options)
        {
            Dictionary<Stream, string> otherParts = new Dictionary<Stream, string>();
            otherParts.Add(otherPartStream, otherPartMime);
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.DOCUMENTS.Rel,
                newObj,
                otherParts,
                this.Client,
                options);
        }      
    }
}
