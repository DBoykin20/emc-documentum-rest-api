using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "link", Namespace = "http://identifiers.emc.com/vocab/documentum")]  
    public class Link
    {
        [DataMember(Name = "rel")]
        public string Rel { get; set; }

        [DataMember(Name = "href")]
        public string Href { get; set; }

        [DataMember(Name = "hreftemplate")]
        public string Hreftemplate { get; set; }

        [DataMember(Name = "title")]
        public string Title { get; set; }

        [DataMember(Name = "type")]
        public string Type { get; set; }

        public override string ToString()
        {
            return String.Format(
                "Link{{{0}: {1}}}",
                this.Rel,
                String.IsNullOrEmpty(this.Href) ? this.Hreftemplate : this.Href);
        }
    }
}
