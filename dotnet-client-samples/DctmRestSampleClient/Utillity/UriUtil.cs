using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace Emc.Documentum.Rest.Sample.Http.Utillity
{
    public class UriUtil
    {
        public static string BuildUri(string baseUri, List<KeyValuePair<string, object>> query)
        {
            UriBuilder builder = new UriBuilder(baseUri);
            var baseQuery = HttpUtility.ParseQueryString(builder.Query);
            if (query != null)
            {
                foreach(KeyValuePair<string, object> pair in query) 
                {
                    if (pair.Value != null)
                    {
                        baseQuery[pair.Key] = String.Format("{0}", pair.Value);
                    }                  
                }
            }
            builder.Query = baseQuery.ToString();
            return builder.ToString();
        }
    }
}
