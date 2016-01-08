using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.Net
{
    /// <summary>
    /// Feed related query parameters
    /// </summary>
    public class FeedGetOptions : SingleGetOptions
    {
        private bool _inline = false;
        private bool _inlineSpecified = false;
        /// <summary>
        /// Specify whether to return inline resources in the atom entry content
        /// </summary>
        public Boolean Inline 
        {
            get { return _inline; }
            set { _inlineSpecified = true; _inline = value; }
        }

        private string _filter = null;
        private bool _filterSpecified = false;
        /// <summary>
        /// Specify a filter expression to filter items within a colleciton
        /// </summary>
        public String Filter
        {
            get { return _filter; }
            set { _filterSpecified = true; _filter = value; }
        }

        private int _pageNumber = 0;
        private bool _pageNumberSpecified = false;
        /// <summary>
        /// Specify current page number
        /// </summary>
        public Int32 PageNumber 
        {
            get { return _pageNumber;}
            set { _pageNumberSpecified = true; _pageNumber = value; } 
        }

        private int _itemsPerPage = 0;
        private bool _itemsPerPageSpecified = false;
        /// <summary>
        /// Specify the maximal count of items for a page
        /// </summary>
        public Int32 ItemsPerPage
        {
            get { return _itemsPerPage; }
            set { _itemsPerPageSpecified = true; _itemsPerPage = value; }
        }

        private bool _includeTotal = false;
        private bool _includeTotalSpecified = false;
        /// <summary>
        /// Specify whether to return the total actual count of items on the server
        /// </summary>
        public Boolean IncludeTotal
        {
            get { return _includeTotal; }
            set { _includeTotalSpecified = true; _includeTotal = value; }
        }

        public override List<KeyValuePair<string, object>> ToQueryList()
        {
            List<KeyValuePair<string, object>> pa = base.ToQueryList();
            if (_inlineSpecified)
            {
                pa.Add(new KeyValuePair<string, object>("inline", Inline));
            }
            if (_filterSpecified)
            {
                pa.Add(new KeyValuePair<string, object>("filter", Filter));
            }
            if (_pageNumberSpecified)
            {
                pa.Add(new KeyValuePair<string, object>("page", PageNumber));
            }
            if (_itemsPerPageSpecified)
            {
                pa.Add(new KeyValuePair<string, object>("items-per-page", ItemsPerPage));
            }
            if (_includeTotalSpecified)
            {
                pa.Add(new KeyValuePair<string, object>("incldue-total", IncludeTotal));
            }
            return pa;
        }
    }
}
