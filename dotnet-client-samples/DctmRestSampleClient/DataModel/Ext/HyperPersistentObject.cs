using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    public partial class PersistentObject : Executable
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
        /// Get current persistent object resource
        /// </summary>
        /// <returns></returns>
        public PersistentObject ReGet()
        {
            return HttpUtil.Self<PersistentObject>(this.Links, this.Client);
        }

        /// <summary>
        /// Paritially update the persistent object resource
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="obj"></param>
        /// <returns></returns>
        public T PartialUpdate<T>(T obj) where T : PersistentObject
        {
            T updated = Client.Post<T>(selfLink(), obj);
            updated.Client = Client;
            return updated;
        }

        /// <summary>
        /// Completely update the persistent object resource
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="obj"></param>
        /// <returns></returns>
        public T CompleteUpdate<T>(T obj) where T : PersistentObject
        {
            T updated = Client.Put<T>(selfLink(), obj);
            updated.Client = Client;
            return updated;
        }

        /// <summary>
        /// Delete te persistent object resource
        /// </summary>
        /// <param name="options"></param>
        public void Delete(GenericOptions options)
        {
            Client.Delete(selfLink(), options == null ? null : options.ToQueryList());
        }

        /// <summary>
        /// Get a reference object representation of this resource with a href link
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <returns></returns>
        public T GetHrefObject<T>() where T : PersistentObject
        {
            T hrefObj = (T)Activator.CreateInstance(typeof(T));
            hrefObj.Href = selfLink();
            hrefObj.SetClient(this.Client);
            return hrefObj;
        }

        private string selfLink()
        {
            string self = LinkUtil.FindLink(this.Links, LinkUtil.EDIT.Rel);
            if (self == null)
            {
                self = LinkUtil.FindLink(this.Links, LinkUtil.SELF.Rel);
            }
            return self;
        }
    }
}
