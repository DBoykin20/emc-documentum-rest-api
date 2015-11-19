using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract]
    public abstract class Linkable
    {
        private List<Link> _links = new List<Link>();
        [DataMember(Name = "links")]
        private List<Link> LinksForBinding
        {
            get
            {
                return Links == null || Links.Count == 0 ? null : Links;
            }
            set
            {
                Links = value;
            }
        }

        public List<Link> Links
        {
            get
            {
                if (_links == null)
                {
                    _links = new List<Link>();
                }
                return _links;
            }
            set
            {
                _links = value;
            }
        }
    }
}
