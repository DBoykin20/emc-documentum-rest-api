using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;
using Newtonsoft.Json.Linq;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "po", Namespace = "http://identifiers.emc.com/vocab/documentum")] 
    public partial class PersistentObject : Linkable
    {
        [DataMember(Name = "href")]
        public string Href { get; set; }

        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "type")]
        public string Type { get; set; }

        [DataMember(Name = "definition")]
        public string Definition { get; set; }

        private Dictionary<string, object> _propereties = new Dictionary<string, object>();
        [DataMember(Name = "properties")]
        private Dictionary<string, object> PropertiesToBinding
        {
            get
            {
                return Properties == null || Properties.Count == 0 ? null : Properties;
            }
            set
            {
                Properties = value;
            }
        }

        public Dictionary<string, object> Properties
        {
            get
            {
                if (_propereties == null)
                {
                    _propereties = new Dictionary<string, object>();
                }
                return _propereties;
            }
            set
            {
                _propereties = value;
            }
        }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "{0}{{name: {1}, type: {2},  definition: {3}, properties[",
                this.GetType().Name,
                this.Name,
                this.Type,
                this.Definition);
            bool first = true;
            foreach (var property in this.Properties)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    builder.Append(", ");
                }
                builder.AppendFormat("{0}: {1}", 
                    property.Key, 
                    ToStringValue(property.Value));
            }
            builder.Append("]}}");
            return builder.ToString();
        }

        private string ToStringValue(object value)
        {
            var newv = value;
            if (value is Newtonsoft.Json.Linq.JArray)
            {
                JToken[] tokens  = ((Newtonsoft.Json.Linq.JArray) value).ToArray();
                newv = new string[tokens.Length];
                for (int k = 0; k < tokens.Length; k++ )
                {
                    ((string[])newv)[k] = tokens[k].ToString();
                }
            }
            if (newv != null && newv.GetType().IsArray)
            {
                StringBuilder b = new StringBuilder();
                b.Append("<");
                foreach(object item in (Array) newv)
                {
                    b.Append(item).Append(" ");
                }
                b.Append(">");
                return b.ToString();
            }
            else{
                return newv == null ? "" : newv.ToString();
            }
        }
    }
}
