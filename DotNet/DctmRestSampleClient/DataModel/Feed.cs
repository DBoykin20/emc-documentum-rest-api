using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "feed", Namespace = "http://www.w3.org/2005/Atom")]  
    public partial class Feed<T> : Linkable
    {
        [DataMember(Name = "id")]
        public string Id { get; set; }

        [DataMember(Name = "title")]
        public string Title { get; set; }

        [DataMember(Name = "updated")]
        public string Updated { get; set; }

        private List<Author> _authors = new List<Author>();
        [DataMember(Name = "author")]
        public List<Author> Authors
        {
            get
            {
                if (_authors == null)
                {
                    _authors = new List<Author>();
                }
                return _authors;
            }
            set
            {
                _authors = value;
            }
        }

        private List<Entry<T>> _entries = new List<Entry<T>>();
        [DataMember(Name = "entries")]
        public List<Entry<T>> Entries
        {
            get
            {
                if (_entries == null)
                {
                    _entries = new List<Entry<T>>();
                }
                return _entries;
            }
            set
            {
                _entries = value;
            }
        }


        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "Feed{{id: {0}, title: {1}, updated: {2}",
                this.Id,
                this.Title,
                this.Updated);
            builder.Append(", ");
            if (this.Authors != null)
            {
                builder.Append("authors<");
                foreach (Author author in this.Authors)
                {
                    builder.Append(author.Name).Append(" ");
                }
                builder.Append(">, ");
            }
            builder.Append("entries[");
            bool first = true;
            foreach (Entry<T> entry in this.Entries)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    builder.Append(", ");
                }
                builder.Append(entry.ToString());
            }
            builder.Append("]}\r\n");
            return builder.ToString();
        }
    }

    [DataContract(Name = "author", Namespace = "http://www.w3.org/2005/Atom")]  
    public class Author
    {
        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "url")]
        public string Url { get; set; }

        [DataMember(Name = "email")]
        public string Email { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "\t[name: {0}, url: {1}, email: {2}],\r\n",
                this.Name,
                this.Url,
                this.Email);
            return builder.ToString();
        }
    }
}
