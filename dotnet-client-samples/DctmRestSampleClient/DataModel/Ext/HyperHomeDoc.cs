using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    public partial class HomeDoc : Executable
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
        /// Get product info resource
        /// </summary>
        /// <returns></returns>
        public ProductInfo GetProductInfo()
        {           
            string productInfoUri = this.Resources.About.Href;
            return Client.Get<ProductInfo>(productInfoUri);
        }

        /// <summary>
        /// Get repositories feed
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetRepositories<T>(FeedGetOptions options)
        {
            string repositoriesUri = this.Resources.Repositories.Href;
            Feed<T> feed = Client.Get<Feed<T>>(repositoriesUri, options == null ? null : options.ToQueryList());
            feed.Client = Client;
            return feed;
        }
    }
}
