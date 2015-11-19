using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Emc.Documentum.Rest.Sample.Http.Net;

namespace Emc.Documentum.Rest.Sample.Http.Net
{
    /// <summary>
    /// Single resource related query parameters
    /// </summary>
    public class SingleGetOptions : GenericOptions
    {
        private string _view = null;
        private bool _viewSpecified = false;
        /// <summary>
        /// Specify properties to return for a resource
        /// </summary>
        public String View
        {
            get { return _view; }
            set { _viewSpecified = true; _view = value; }
        }

        private bool _links = true;
        private bool _linksSpecified = false;
        /// <summary>
        /// Specify whether to return links collection for a resource
        /// </summary>
        public Boolean Links
        {
            get { return _links; }
            set { _linksSpecified = true; _links = value; }
        } 

        public override List<KeyValuePair<string, object>> ToQueryList()
        {
            List<KeyValuePair<string, object>> pa = base.ToQueryList();
            if (_viewSpecified)
            {
                pa.Add(new KeyValuePair<string, object>("view", View));
            }
            if (_linksSpecified)
            {
                pa.Add(new KeyValuePair<string, object>("links", Links));
            }

            return pa;
        }
    }
}
