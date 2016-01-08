using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Emc.Documentum.Rest.Sample.Http.Net
{
    /// <summary>
    /// key value pairs of URI query parameters
    /// </summary>
    public class GenericOptions
    {
        private List<KeyValuePair<string, object>> pa = new List<KeyValuePair<string, object>>();

        public void SetQuery(String name, object value)
        {
            pa.Add(new KeyValuePair<string, object>(name, value));
        }

        public virtual List<KeyValuePair<string, object>> ToQueryList()
        {          
            return pa;
        }
    }
}
