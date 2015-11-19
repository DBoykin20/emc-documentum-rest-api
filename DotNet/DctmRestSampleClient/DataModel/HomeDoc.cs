using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "services", Namespace = "http://identifiers.emc.com/vocab/documentum")] 
    public partial class HomeDoc
    {
        [DataMember(Name = "resources")]
        public Resources Resources { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("HomeDocument{");
            builder.Append("repositories: ").Append(this.Resources.Repositories.ToString());
            builder.Append(", about: ").Append(this.Resources.About.ToString());

            builder.Append("}");
            return builder.ToString();
        }
    }

    [DataContract(Name = "resources", Namespace = "http://identifiers.emc.com/vocab/documentum")]
    public class Resources
    {
        [DataMember(Name = "http://identifiers.emc.com/linkrel/repositories")]
        public Resource Repositories { get; set; }

        [DataMember(Name = "about")]
        public Resource About { get; set; }
    }

    [DataContract(Name = "resources", Namespace = "http://identifiers.emc.com/vocab/documentum")]
    public class Resource
    {
        [DataMember(Name = "href")]
        public string Href { get; set; }

        [DataMember(Name = "hints")]
        public Hints Hints { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "Resource{{href: {0}, hints_allow: {1}, hints_representations: {2}}}",
                this.Href,
                string.Join(", ", this.Hints.Allow),
                string.Join(", ", this.Hints.Representations));
            return builder.ToString();
        }
    }

    [DataContract(Name = "hints", Namespace = "http://identifiers.emc.com/vocab/documentum")]
    public class Hints
    {
        [DataMember(Name = "allow")]
        public List<string> Allow { get; set; }

        [DataMember(Name = "representations")]
        public List<string> Representations { get; set; }
    }
}
