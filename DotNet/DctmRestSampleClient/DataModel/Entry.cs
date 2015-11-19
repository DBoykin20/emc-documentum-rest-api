using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.DataModel
{
    [DataContract(Name = "entry", Namespace = "http://www.w3.org/2005/Atom")]  
    public partial class Entry<T>: Linkable
    {
        [DataMember(Name = "id")]
        public string Id { get; set; }

        [DataMember(Name = "title")]
        public string Title { get; set; }

        [DataMember(Name = "updated")]
        public string Updated { get; set; }

        [DataMember(Name = "summary")]
        public string Summary { get; set; }

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

        [DataMember(Name = "content", IsRequired = false)]
        public T Content { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "Entry{{id: {0}, title: {1}, summary: {2}, updated: {3}, ",
                this.Id,
                this.Title,
                this.Summary,
                this.Updated);
            
            if(this.Authors != null)
            {
                builder.Append("authors<");
            
                foreach (Author author in this.Authors)
                {
                    builder.Append(author.Name).Append(" ");
                }
                builder.Append(">, ");
            }
            
            builder.Append("content: ").Append(this.Content.ToString());
            builder.Append("}");
            return builder.ToString();
        }
    }

    [DataContract(Name = "content", Namespace = "http://www.w3.org/2005/Atom")]  
    public class OutlineAtomContent : IContent
    {
        [DataMember(Name = "src")]
        public string Src { get; set; }

        [DataMember(Name = "content-type")]
        public string ContentType { get; set; }

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendFormat(
                "Content{{src: {0}, content-type: {1}}}",
                this.Src,
                this.ContentType);
            return builder.ToString();
        }
    }

    public interface IContent
    {

    }
}
