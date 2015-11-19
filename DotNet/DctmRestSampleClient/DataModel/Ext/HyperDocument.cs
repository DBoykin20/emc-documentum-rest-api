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
    public partial class Document : PersistentObject
    {
        /// <summary>
        /// Whether the document can be updated
        /// </summary>
        /// <returns></returns>
        public bool CanUpdate()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.EDIT.Rel) != null;
        }

        /// <summary>
        /// Whether the document can be deleted
        /// </summary>
        /// <returns></returns>
        public bool CanDelete()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.DELETE.Rel) != null;
        }

        /// <summary>
        /// Whether the document can be checked out
        /// </summary>
        /// <returns></returns>
        public bool CanCheckout()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.CHECKOUT.Rel) != null;
        }

        /// <summary>
        /// Whether the document can be checked in
        /// </summary>
        /// <returns></returns>
        public bool CanCheckin()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.CHECKIN_NEXT_MAJOR.Rel) != null
                || LinkUtil.FindLink(this.Links, LinkUtil.CHECKIN_NEXT_MINOR.Rel) != null
                || LinkUtil.FindLink(this.Links, LinkUtil.CHECKIN_BRANCH_VERSION.Rel) != null;
        }

        /// <summary>
        /// Cancel check out the document
        /// </summary>
        /// <returns></returns>
        public bool CanCancelCheckout()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.CANCEL_CHECKOUT.Rel) != null;
        }

        /// <summary>
        /// Whether the document has a predecessor version
        /// </summary>
        /// <returns></returns>
        public bool HasPredecessorVersion()
        {
            return LinkUtil.FindLink(this.Links, LinkUtil.PREDECESSOR_VERSION.Rel) != null;
        }

        /// <summary>
        /// Get the cabinet resource where this document locates at 
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Cabinet GetCabinet(SingleGetOptions options)
        {
            return HttpUtil.GetSingleton<Cabinet>(
                this.Links,
                LinkUtil.CABINET.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get the parent folder resoruce of this document
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Folder GetParentFolder(SingleGetOptions options)
        {
            return HttpUtil.GetSingleton<Folder>(
                this.Links,
                LinkUtil.PARENT.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get the primary content resource of this document
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public ContentMeta GetPrimaryContent(SingleGetOptions options)
        {
            options.SetQuery("media-url-policy", "all");
            return HttpUtil.GetSingleton<ContentMeta>(
                this.Links,
                LinkUtil.PRIMARY_CONTENT.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get contents feed of this document
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetContents<T>(FeedGetOptions options)
        {
            options.SetQuery("media-url-policy", "all");
            return HttpUtil.GetFeed<T>(
                this.Links,
                LinkUtil.CONTENTS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Create a new content (rendition) for this document
        /// </summary>
        /// <param name="contentStream"></param>
        /// <param name="mimeType"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public ContentMeta CreateContent(Stream contentStream, string mimeType, GenericOptions options)
        {
            return HttpUtil.Post<ContentMeta>(
                this.Links,
                LinkUtil.CONTENTS.Rel,
                contentStream,
                mimeType,
                this.Client,
                options);
        }

        /// <summary>
        /// Get version history of this document
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetVersionHistory<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                LinkUtil.VERSIONS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get current version of this document
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document GetCurrentVersion(SingleGetOptions options)
        {
            return HttpUtil.GetSingleton<Document>(
                this.Links,
                LinkUtil.CURRENT_VERSION.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get the predecessor version of this document
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document GetPredessorVersion(SingleGetOptions options)
        {
            return HttpUtil.GetSingleton<Document>(
                this.Links,
                LinkUtil.PREDECESSOR_VERSION.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Checkout the document
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document Checkout(GenericOptions options)
        {
            return HttpUtil.Put<Document>(
                this.Links,
                LinkUtil.CHECKOUT.Rel,
                null,
                this.Client,
                options);
        }

        /// <summary>
        /// Cancel checkout the document
        /// </summary>
        /// <param name="options"></param>
        public void CancelCheckout(GenericOptions options)
        {
            HttpUtil.Delete(
                this.Links, 
                LinkUtil.CANCEL_CHECKOUT.Rel, 
                this.Client, options);
        }

        /// <summary>
        /// Checkin a new document as next major version
        /// </summary>
        /// <param name="newDoc"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document CheckinMajor(Document newDoc, GenericOptions options)
        {
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.CHECKIN_NEXT_MAJOR.Rel,
                newDoc,
                this.Client,
                options);
        }

        /// <summary>
        /// Checkin a new document as next minor version
        /// </summary>
        /// <param name="newDoc"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document CheckinMinor(Document newDoc, GenericOptions options)
        {
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.CHECKIN_NEXT_MINOR.Rel,
                newDoc,
                this.Client,
                options);
        }

        /// <summary>
        /// Checkin a new document as branch version
        /// </summary>
        /// <param name="newDoc"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document CheckinBranch(Document newDoc, GenericOptions options)
        {
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.CHECKIN_BRANCH_VERSION.Rel,
                newDoc,
                this.Client,
                options);
        }

        /// <summary>
        /// Check in a new document with content as next major version
        /// </summary>
        /// <param name="newDoc"></param>
        /// <param name="contentStream"></param>
        /// <param name="mimeType"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document CheckinMajor(Document newDoc, Stream contentStream, string mimeType, GenericOptions options)
        {
            IDictionary<Stream, string> otherParts = new Dictionary<Stream, string>();
            otherParts.Add(contentStream, mimeType);
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.CHECKIN_NEXT_MAJOR.Rel,
                newDoc,
                otherParts,
                this.Client,
                options);
        }

        /// <summary>
        /// Checkin a new document with content as next minor version
        /// </summary>
        /// <param name="newDoc"></param>
        /// <param name="contentStream"></param>
        /// <param name="mimeType"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document CheckinMinor(Document newDoc, Stream contentStream, string mimeType, GenericOptions options)
        {
            IDictionary<Stream, string> otherParts = new Dictionary<Stream, string>();
            otherParts.Add(contentStream, mimeType);
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.CHECKIN_NEXT_MINOR.Rel,
                newDoc,
                otherParts,
                this.Client,
                options);
        }

        /// <summary>
        /// Check in a new document with content as branch version
        /// </summary>
        /// <param name="newDoc"></param>
        /// <param name="contentStream"></param>
        /// <param name="mimeType"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Document CheckinBranch(Document newDoc, Stream contentStream, string mimeType, GenericOptions options)
        {
            IDictionary<Stream, string> otherParts = new Dictionary<Stream, string>();
            otherParts.Add(contentStream, mimeType);
            return HttpUtil.Post<Document>(
                this.Links,
                LinkUtil.CHECKIN_BRANCH_VERSION.Rel,
                newDoc,
                otherParts,
                this.Client,
                options);
        }

        /// <summary>
        /// Get folder links feed of this document
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<FolderLink> GetFolderLinks(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<FolderLink>(
                this.Links,
                LinkUtil.PARENT_LINKS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Link this document to a new folder
        /// </summary>
        /// <param name="newObj"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public FolderLink LinkTo(Folder newObj, GenericOptions options)
        {
            return HttpUtil.Post<Folder, FolderLink>(
                this.Links,
                LinkUtil.PARENT_LINKS.Rel,
                newObj,
                this.Client,
                options);
        }
    }
}
