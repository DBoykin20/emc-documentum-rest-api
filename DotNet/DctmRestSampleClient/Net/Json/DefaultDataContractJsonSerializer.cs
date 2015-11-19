using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http.Headers;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.Net
{
    /// <summary>
    /// .NET built-in DataContract JSON serializer
    /// </summary>
    public class DefaultDataContractJsonSerializer : AbstractJsonSerializer
    {
        private MediaTypeWithQualityHeaderValue JSON_GENERIC_MEDIA_TYPE;
        private MediaTypeWithQualityHeaderValue JSON_VND_MEDIA_TYPE;
        private DataContractJsonSerializerSettings JSON_SER_SETTINGS;

        public DefaultDataContractJsonSerializer()
        {
            JSON_GENERIC_MEDIA_TYPE = new MediaTypeWithQualityHeaderValue("application/*+json");
            JSON_VND_MEDIA_TYPE = new MediaTypeWithQualityHeaderValue("application/vnd.emc.documentum+json");
            JSON_SER_SETTINGS = new DataContractJsonSerializerSettings();
            JSON_SER_SETTINGS.UseSimpleDictionaryFormat = true;
            JSON_SER_SETTINGS.DateTimeFormat = new DateTimeFormat("yyyy-MM-ddTHH:mm:ss.fffffffzzz");
        }

        public override T ReadObject<T>(Stream input)
        {
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(T), JSON_SER_SETTINGS);
            T obj = (T)ser.ReadObject(input);
            return obj;
        }

        public override void WriteObject<T>(Stream output, T obj)
        {
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(T), JSON_SER_SETTINGS);
            ser.WriteObject(output, obj);
        }
    }
}
