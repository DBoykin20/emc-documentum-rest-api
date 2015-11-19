using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    public partial class User : PersistentObject
    {
        /// <summary>
        /// Get the home cabinet (default folder) resource of the user
        /// </summary>
        /// <param name="options"></param>
        /// <returns></returns>
        public Folder GetHomeCabinet(SingleGetOptions options)
        {
            return HttpUtil.GetSingleton<Folder>(this.Links, LinkUtil.DEFAULT_FOLDER.Rel, this.Client, options);
        }

        /// <summary>
        /// Get the groups feed of which this user is a member
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetParentGroups<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                LinkUtil.PARENT.Rel,
                this.Client,
                options);
        }
        
    }
}
