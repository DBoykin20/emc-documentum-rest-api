using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    public partial class Feed<T> : Executable
    {
        private RawHttpClient _client;
        public void SetClient(RawHttpClient client)
        {
            _client = client;
        }

        public RawHttpClient Client 
        {
            get{ return _client;}
            set{ this._client = value;}
        }
        
        /// <summary>
        /// Find entry by atom entry title
        /// </summary>
        /// <typeparam name="R"></typeparam>
        /// <param name="title"></param>
        /// <returns></returns>
        public R GetEntry<R>(string title) where R : Executable
        {
            string repositoryUri = AtomUtil.FindEntryHref(this, title);
            R obj = Client.Get<R>(repositoryUri);
            (obj as Executable).SetClient(Client);
            return obj;
        }

        /// <summary>
        /// Find inline entry content
        /// </summary>
        /// <param name="title"></param>
        /// <returns></returns>
        public T FindInlineEntry(string title)
        {
            T entry =AtomUtil.FindInlineEntry(this, title);
            (entry as Executable).SetClient(this.Client);
            return entry;
        }

        /// <summary>
        /// Find entry by atom entry summary
        /// </summary>
        /// <param name="title"></param>
        /// <returns></returns>
        public T FindInlineEntryBySummary(string title)
        {
            T entry = AtomUtil.FindInlineEntryBySummary(this, title);
            (entry as Executable).SetClient(this.Client);
            return entry;
        }

        /// <summary>
        /// Get current page feed
        /// </summary>
        /// <returns></returns>
        public Feed<T> CurrentPage()
        {
            return HttpUtil.Self<Feed<T>>(this.Links, this.Client);
        }

        /// <summary>
        /// Get next page feed
        /// </summary>
        /// <returns></returns>
        public Feed<T> NextPage()
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.PAGING_NEXT.Rel,
                this.Client,
                null);
        }

        /// <summary>
        /// Get previous page feed
        /// </summary>
        /// <returns></returns>
        public Feed<T> PreviousPage()
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.PAGING_PREV.Rel,
                this.Client,
                null);
        }

        /// <summary>
        /// Get first page feed
        /// </summary>
        /// <returns></returns>
        public Feed<T> FirstPage()
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.PAGING_FIRST.Rel,
                this.Client,
                null);
        }

        /// <summary>
        /// Get last page feed
        /// </summary>
        /// <returns></returns>
        public Feed<T> LastPage()
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.PAGING_LAST.Rel,
                this.Client,
                null);
        }
    }
}
