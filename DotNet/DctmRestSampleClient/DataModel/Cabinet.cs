using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "cabinet", Namespace = "http://identifiers.emc.com/vocab/documentum")] 
    public partial class Cabinet : PersistentObject
    {
    }
}
