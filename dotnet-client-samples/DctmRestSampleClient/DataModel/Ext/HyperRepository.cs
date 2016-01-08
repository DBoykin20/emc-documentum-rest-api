using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    public partial class Repository : Executable
    {
        private RawHttpClient _client;
        public void SetClient(RawHttpClient client)
        {
            _client = client;
        }

        public RawHttpClient Client
        {
            get { return _client; }
            set { this._client = value; }
        }

        /// <summary>
        /// Get current login user resource
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public User GetCurrentUser(SingleGetOptions options)
        {
            return HttpUtil.GetSingleton<User>(
                this.Links,
                LinkUtil.CURRENT_USER.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get cabinets feed in this repository
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetCabinets<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.CABINETS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get users feed in this repository
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetUsers<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.USERS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get groups in this repository
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetGroups<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.GROUPS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get checked out sysobjects in this repository
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetCheckedOutObjects<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                Emc.Documentum.Rest.Sample.Http.Utillity.LinkUtil.CHECKED_OUT_OBJECTS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Execute a DQL query in this repository
        /// </summary>
        /// <param name="dql"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<PersistentObject> ExecuteDQL(string dql, FeedGetOptions options)
        {
            string dqlUri = LinkUtil.FindLink(this.Links, LinkUtil.DQL.Rel);
            string dqlUriWithoutTemplateParams = dqlUri.Substring(0, dqlUri.IndexOf("{"));
            List<KeyValuePair<string, object>> pa = options.ToQueryList();
            pa.Add(new KeyValuePair<string, object>("dql", dql));
            return this.Client.Get<Feed<PersistentObject>>(dqlUriWithoutTemplateParams, pa);
        }
    }
}
