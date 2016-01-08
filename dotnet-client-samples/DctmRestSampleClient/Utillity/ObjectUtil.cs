using Emc.Documentum.Rest.Sample.Http.DataModel;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.Utillity
{
    public class ObjectUtil
    {
        public static Folder NewRandomFolder(string namePrefix)
        {
            Folder obj = new Folder();
            obj.Name = "folder";
            obj.Type = "dm_folder";
            obj.Properties.Add("object_name", namePrefix + System.Guid.NewGuid().ToString().Substring(0, 8));
            obj.Properties.Add("r_object_type", "dm_folder");
            return obj;
        }

        public static Document NewRandomDocument(string namePrefix)
        {
            Document obj = new Document();
            obj.Name = "document";
            obj.Type = "dm_document";
            obj.Properties.Add("object_name", namePrefix + System.Guid.NewGuid().ToString().Substring(0, 8));
            obj.Properties.Add("r_object_type", "dm_document");
            return obj;
        }

        public static System.IO.FileInfo NewTextFileWithLength(int length)
        {
            Random random = new Random((int)DateTime.Now.Ticks);
            string path = Path.GetTempFileName();
            FileInfo file = new FileInfo(path);
            StreamWriter writer = file.CreateText();
            char[] texts = new char[length];
            for (int k = 0; k < length; k++ )
            {
                texts[k] = Convert.ToChar(Convert.ToInt32(Math.Floor(26 * random.NextDouble() + 65)));
            }
            writer.Write(texts);
            writer.Flush();
            writer.Close();
            return file;
        }   
 
        public static object FindProperty(PersistentObject obj, string propertyName)
        {
            return obj.Properties.ContainsKey(propertyName) ? obj.Properties[propertyName] : null;
        }
    }
}
