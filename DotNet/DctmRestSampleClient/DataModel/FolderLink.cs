using System.ServiceModel;
using System.ServiceModel.Web;
using System.Runtime.Serialization;
using System.Collections.Generic;
using System.Text;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "folder-link", Namespace = "http://identifiers.emc.com/vocab/documentum")]  
    public partial class FolderLink : Linkable
    {
        [DataMember(Name = "href")]
        public string Href { get; set; }

        [DataMember(Name = "parent-id")]
        public string ParentId { get; set; }

        [DataMember(Name = "child-id")]
        public string ChildId { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "FolderLink{{href: {0}, parent-id: {1}, child-id: {2}}}",
                this.Href,
                this.ParentId,
                this.ChildId);
            return builder.ToString();
        }
    }
}
