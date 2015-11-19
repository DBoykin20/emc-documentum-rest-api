using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.Net
{
    public abstract class AbstractJsonSerializer
    {
        public abstract T ReadObject<T>(Stream input);

        public abstract void WriteObject<T>(Stream output, T obj);
    }
}
