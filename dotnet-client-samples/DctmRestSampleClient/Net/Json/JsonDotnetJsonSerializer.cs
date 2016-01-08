using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using Newtonsoft.Json.Serialization;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.Net
{
    /// <summary>
    /// JSON serializer in the Json.NET library
    /// </summary>
    public class JsonDotnetJsonSerializer : AbstractJsonSerializer
    {
        private JsonSerializer SERIALIZER;
        public JsonDotnetJsonSerializer()
        {
            SERIALIZER = new JsonSerializer();
            SERIALIZER.Converters.Add(new JavaScriptDateTimeConverter());
            SERIALIZER.NullValueHandling = NullValueHandling.Ignore;
            SERIALIZER.DateFormatHandling = DateFormatHandling.IsoDateFormat;
            SERIALIZER.MissingMemberHandling = MissingMemberHandling.Ignore;
            //SERIALIZER.TraceWriter = new MemoryTraceWriter();
        }

        public override T ReadObject<T>(Stream input)
        {           
            JsonReader reader = new JsonTextReader(new StreamReader(input));
            T obj = SERIALIZER.Deserialize<T>(reader);
            return obj;
        }

        public override void WriteObject<T>(Stream output, T obj)
        {          
            JsonWriter writer = new JsonTextWriter(new StreamWriter(output));
            SERIALIZER.Serialize(writer, obj);
            writer.Flush();
            output.Position = 0;
            //Console.WriteLine(SERIALIZER.TraceWriter);
        }
    }
}
