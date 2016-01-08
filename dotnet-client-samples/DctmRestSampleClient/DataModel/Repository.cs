using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "repository", Namespace = "http://identifiers.emc.com/vocab/documentum")] 
    public partial class Repository : Linkable
    {
        [DataMember(Name = "id")]
        public string Id { get; set; }

        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "description")]
        public string Description { get; set; }

        [DataMember(Name = "servers")]
        public List<Server> Servers { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "Repository{{id: {0}, description: {1}, name: {2}, ",
                this.Id,
                this.Description,
                this.Name);
            bool first = true;
            foreach(Server svr in this.Servers) 
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    builder.Append(", ");
                }
                builder.AppendFormat(
                    "server: <docbroker: {0}, name:{1}, host:{2}, version:{3}>",
                    svr.Docbroker,
                    svr.Name,
                    svr.Host,
                    svr.Version);
            }
            builder.Append("}");
            return builder.ToString();
        }
    }

    [DataContract(Name = "server", Namespace = "http://identifiers.emc.com/vocab/documentum")] 
    public class Server
    {
        [DataMember(Name = "docbroker")]
        public string Docbroker { get; set; }

        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "host")]
        public string Host { get; set; }

        [DataMember(Name = "version")]
        public string Version { get; set; }
    }
}
