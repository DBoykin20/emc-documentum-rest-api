using Emc.Documentum.Rest.Sample.Http.Net;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    public partial class Group : PersistentObject
    {
        /// <summary>
        /// Get groups feed of which this group is a member
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

        /// <summary>
        /// Get groups feed which are members of this group
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetSubGroups<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                LinkUtil.GROUPS.Rel,
                this.Client,
                options);
        }

        /// <summary>
        /// Get users feed which are members of this group
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="options"></param>
        /// <returns></returns>
        public Feed<T> GetGroupUsers<T>(FeedGetOptions options)
        {
            return HttpUtil.GetFeed<T>(
                this.Links,
                LinkUtil.USERS.Rel,
                this.Client,
                options);
        }
        
    }
}
