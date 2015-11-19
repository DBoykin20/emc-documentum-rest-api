using System.ServiceModel;
using System.ServiceModel.Web;
using System.Runtime.Serialization;
using System.Collections.Generic;
using System.Text;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "product-info", Namespace = "http://identifiers.emc.com/vocab/documentum")]  
    public partial class ProductInfo : Linkable
    {
        [DataMember(Name = "properties")]
        public ProductInfoProperties Properties { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "ProductInfo{{product: {0}, product_version: {1}, major: {2}, minor: {3}, build_number: {4}, revision_number: {5}}}",
                this.Properties.Product,
                this.Properties.ProductVersion,
                this.Properties.Major,
                this.Properties.Minor,
                this.Properties.BuildNumber,
                this.Properties.RevisionNumber);
            return builder.ToString();
        }
    }

    [DataContract(Name = "properties", Namespace = "http://identifiers.emc.com/vocab/documentum")]
    public class ProductInfoProperties
    {
        [DataMember(Name = "product")]
        public string Product { get; set; }

        [DataMember(Name = "product_version")]
        public string ProductVersion { get; set; }

        [DataMember(Name = "major")]
        public string Major { get; set; }

        [DataMember(Name = "minor")]
        public string Minor { get; set; }

        [DataMember(Name = "build_number")]
        public string BuildNumber { get; set; }

        [DataMember(Name = "revision_number")]
        public string RevisionNumber { get; set; }
    }
}
