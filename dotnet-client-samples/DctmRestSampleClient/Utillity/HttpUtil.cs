using Emc.Documentum.Rest.Sample.Http.DataModel;
using Emc.Documentum.Rest.Sample.Http.Net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emc.Documentum.Rest.Sample.Http.Utillity
{
    public class HttpUtil
    {
        public static Feed<T> GetFeed<T>(List<Link> links, string rel, RawHttpClient client, FeedGetOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            Feed<T> feed = client.Get<Feed<T>>(followingUri, options == null ? null : options.ToQueryList());
            feed.Client = client;
            return feed;
        }

        public static T GetSingleton<T>(List<Link> links, string rel, RawHttpClient client, SingleGetOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            T result = client.Get<T>(followingUri, options == null ? null : options.ToQueryList());
            (result as Executable).SetClient(client);
            return result;
        }

        public static T Put<T>(List<Link> links, string rel, T input, RawHttpClient client, GenericOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            T result = client.Put<T>(followingUri, input, options == null ? null : options.ToQueryList());
            (result as Executable).SetClient(client);
            return result;
        }

        public static R Put<T, R>(List<Link> links, string rel, T input, RawHttpClient client, GenericOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            R result = client.Put<T, R>(followingUri, input, options == null ? null : options.ToQueryList());
            (result as Executable).SetClient(client);
            return result;
        }

        public static R Post<T, R>(List<Link> links, string rel, T input, RawHttpClient client, GenericOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            R result = client.Post<T, R>(followingUri, input, options == null ? null : options.ToQueryList());
            (result as Executable).SetClient(client);
            return result;
        }

        public static T Post<T>(List<Link> links, string rel, T input, RawHttpClient client, GenericOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            T result = client.Post<T>(followingUri, input, options == null ? null : options.ToQueryList());
            (result as Executable).SetClient(client);
            return result;
        }

        public static T Post<T>(List<Link> links, string rel, T input, IDictionary<Stream, string> otherParts, RawHttpClient client, GenericOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            T result = client.PostMultiparts<T>(followingUri, input, otherParts, options == null ? null : options.ToQueryList());
            (result as Executable).SetClient(client);
            return result;
        }

        public static T Post<T>(List<Link> links, string rel, Stream input, string mime, RawHttpClient client, GenericOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            T result = client.PostRaw<T>(followingUri, input, mime, options == null ? null : options.ToQueryList());
            (result as Executable).SetClient(client);
            return result;
        }

        public static void Delete(List<Link> links, string rel, RawHttpClient client, GenericOptions options)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                rel);
            client.Delete(followingUri, options == null ? null : options.ToQueryList());
        }

        public static T Self<T>(List<Link> links, RawHttpClient client)
        {
            string followingUri = LinkUtil.FindLink(
                links,
                LinkUtil.SELF.Rel);
            T result = client.Get<T>(followingUri);
            (result as Executable).SetClient(client);
            return result;
        }
    }
}
