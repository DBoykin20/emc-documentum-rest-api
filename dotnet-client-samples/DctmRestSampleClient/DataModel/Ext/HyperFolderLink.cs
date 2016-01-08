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
    public partial class FolderLink : Executable
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
        /// Move current folder link's target folder to a new folder location
        /// </summary>
        /// <param name="newObj"></param>
        /// <param name="options"></param>
        /// <returns></returns>
        public FolderLink MoveTo(Folder newObj, GenericOptions options)
        {
            return HttpUtil.Put<Folder, FolderLink>(
                this.Links,
                LinkUtil.SELF.Rel,
                newObj,
                this.Client,
                options);
        }

        /// <summary>
        /// Remove the folder link
        /// </summary>
        public void Remove()
        {
            HttpUtil.Delete(
                this.Links,
                LinkUtil.SELF.Rel,
                this.Client,
                null);
        }
    }
}
