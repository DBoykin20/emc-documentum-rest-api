using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Net.Http.Headers;
using Emc.Documentum.Rest.Sample.Http.Utillity;
using System.Runtime.Serialization;

namespace Emc.Documentum.Rest.Sample.Http.Net
{    
    public class RawHttpClient
    {
        private string authorizationHeader;
        private HttpClient httpClient;
        private MediaTypeWithQualityHeaderValue JSON_GENERIC_MEDIA_TYPE;
        private MediaTypeWithQualityHeaderValue JSON_VND_MEDIA_TYPE;
        private AbstractJsonSerializer _jsonSerializer;
        private string username;
        public string UserName 
        {
            get { return username; }
        } 

        public RawHttpClient(string userName, string password)
        {
            var httpClientHandler = new HttpClientHandler();
            //do not use client credentials as it tries to send the http request anonymously
            //httpClientHandler.Credentials = new System.Net.NetworkCredential(userName, password);
            string authInfo = userName + ":" + password;
            authorizationHeader = "Basic " + Convert.ToBase64String(Encoding.Default.GetBytes(authInfo));
            httpClient = new HttpClient(httpClientHandler);
            username = userName;

            JSON_GENERIC_MEDIA_TYPE = new MediaTypeWithQualityHeaderValue("application/*+json");
            JSON_VND_MEDIA_TYPE = new MediaTypeWithQualityHeaderValue("application/vnd.emc.documentum+json");
        }

        public AbstractJsonSerializer JsonSerializer
        {
            get
            {
                if (_jsonSerializer == null)
                {
                    _jsonSerializer = new DefaultDataContractJsonSerializer();
                }
                return _jsonSerializer;
            }
            set
            {
                _jsonSerializer = value;
            }
        }

        public T Get<T>(string uri, List<KeyValuePair<string, object>> query)
        {
            string requestUri = UriUtil.BuildUri(uri, query);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, requestUri);
            request.Headers.Accept.Add(JSON_GENERIC_MEDIA_TYPE);
            SetBasicAuthHeader(request);
            HttpCompletionOption option = HttpCompletionOption.ResponseContentRead;
            Task<HttpResponseMessage> response = httpClient.SendAsync(request, option);
            HttpResponseMessage message = response.Result;
            message.EnsureSuccessStatusCode();
            Task<Stream> result = message.Content.ReadAsStreamAsync();
            T obj = JsonSerializer.ReadObject<T>(result.Result);
            return obj;
        }

        public Stream GetRaw(string uri)
        {
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, uri);
            request.Headers.Accept.Add(JSON_GENERIC_MEDIA_TYPE);
            SetBasicAuthHeader(request);
            HttpCompletionOption option = HttpCompletionOption.ResponseContentRead;
            Task<HttpResponseMessage> response = httpClient.SendAsync(request, option);
            HttpResponseMessage message = response.Result;
            return message.Content.ReadAsStreamAsync().Result;
        }

        public T Get<T>(string uri)
        {
            return Get<T>(uri, null);
        }

        public R Post<T, R>(string uri, T requestBody, List<KeyValuePair<string, object>> query)
        {
            string requestUri = UriUtil.BuildUri(uri, query);
            using (MemoryStream ms = new MemoryStream())
            {
                JsonSerializer.WriteObject(ms, requestBody);
                byte[] requestInJson = ms.ToArray();
                HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, requestUri);
                request.Content = new ByteArrayContent(requestInJson);
                request.Content.Headers.ContentType = JSON_VND_MEDIA_TYPE;
                request.Headers.Accept.Add(JSON_GENERIC_MEDIA_TYPE);
                SetBasicAuthHeader(request);
                HttpCompletionOption option = HttpCompletionOption.ResponseContentRead;
                Task<HttpResponseMessage> response = httpClient.SendAsync(request, option);
                HttpResponseMessage message = response.Result;
                message.EnsureSuccessStatusCode();
                Task<Stream> result = message.Content.ReadAsStreamAsync();

                R obj = JsonSerializer.ReadObject<R>(result.Result);
                return obj;
            }
        }

        public T Post<T>(string uri, T requestBody, List<KeyValuePair<string, object>> query)
        {
            return Post<T, T>(uri, requestBody, query);           
        }

        public T Post<T>(string uri, T requestBody)
        {
            return Post<T>(uri, requestBody, null);
        }

        public T PostMultiparts<T>(string uri, T requestBody, IDictionary<Stream, string> otherParts, List<KeyValuePair<string, object>> query)
        {
            string requestUri = UriUtil.BuildUri(uri, query);
            using (var multiPartStream = new MultipartFormDataContent())
            {              
                MemoryStream stream = new MemoryStream();
                JsonSerializer.WriteObject(stream, requestBody);
                ByteArrayContent firstPart = new ByteArrayContent(stream.GetBuffer());
                firstPart.Headers.ContentType = JSON_VND_MEDIA_TYPE;
                firstPart.Headers.ContentDisposition = new ContentDispositionHeaderValue("form-data") { Name = "metadata"};
                multiPartStream.Add(firstPart);
                stream.Close();
                if (otherParts != null)
                {
                    foreach(var other in otherParts)
                    {
                        StreamContent otherContent = new StreamContent(other.Key);
                        otherContent.Headers.ContentType = new MediaTypeHeaderValue(other.Value);
                        otherContent.Headers.ContentDisposition = new ContentDispositionHeaderValue("form-data") { Name = "binary" };
                        multiPartStream.Add(otherContent);
                    }
                }
                HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, requestUri);
                request.Content = multiPartStream;
                request.Headers.Accept.Add(JSON_GENERIC_MEDIA_TYPE);
                SetBasicAuthHeader(request);
                HttpCompletionOption option = HttpCompletionOption.ResponseContentRead;
                Task<HttpResponseMessage> response = httpClient.SendAsync(request, option);
                HttpResponseMessage message = response.Result;
                message.EnsureSuccessStatusCode();
                Task<Stream> result = message.Content.ReadAsStreamAsync();

                T obj = JsonSerializer.ReadObject<T>(result.Result);
                foreach (var other in otherParts)
                {
                    other.Key.Close();
                }
                return obj;
            }
        }

        public T PostRaw<T>(string uri, Stream requestBody, string mimeType, List<KeyValuePair<string, object>> query)
        {
            string requestUri = UriUtil.BuildUri(uri, query);
            using (requestBody)
            {
                HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, requestUri);
                request.Content = new StreamContent(requestBody);
                request.Content.Headers.ContentType = new MediaTypeWithQualityHeaderValue(mimeType);
                request.Headers.Accept.Add(JSON_GENERIC_MEDIA_TYPE);
                SetBasicAuthHeader(request);
                HttpCompletionOption option = HttpCompletionOption.ResponseContentRead;
                Task<HttpResponseMessage> response = httpClient.SendAsync(request, option);
                HttpResponseMessage message = response.Result;
                message.EnsureSuccessStatusCode();
                Task<Stream> result = message.Content.ReadAsStreamAsync();

                T obj = JsonSerializer.ReadObject<T>(result.Result);
                return obj;
            }
        }

        public R Put<T, R>(string uri, T requestBody, List<KeyValuePair<string, object>> query)
        {
            string requestUri = UriUtil.BuildUri(uri, query);
            using (MemoryStream stream = new MemoryStream())
            {
                HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Put, requestUri);
                JsonSerializer.WriteObject(stream, requestBody);
                request.Content = new ByteArrayContent(stream.GetBuffer());
                request.Content.Headers.ContentType = JSON_VND_MEDIA_TYPE;
                request.Headers.Accept.Add(JSON_GENERIC_MEDIA_TYPE);
                SetBasicAuthHeader(request);
                HttpCompletionOption option = HttpCompletionOption.ResponseContentRead;
                Task<HttpResponseMessage> response = httpClient.SendAsync(request, option);
                HttpResponseMessage message = response.Result;
                message.EnsureSuccessStatusCode();
                Task<Stream> result = message.Content.ReadAsStreamAsync();

                R obj = JsonSerializer.ReadObject<R>(result.Result);
                return obj;
            }
        }

        public T Put<T>(string uri, T requestBody, List<KeyValuePair<string, object>> query)
        {
            return Put<T, T>(uri, requestBody, query);
        }

        public T Put<T>(string uri, T requestBody)
        {
            return Put<T>(uri, requestBody, null);
        }

        public void Delete(string uri, List<KeyValuePair<string, object>> query)
        {
            string requestUri = UriUtil.BuildUri(uri, query);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Delete, requestUri);
            request.Headers.Accept.Add(JSON_GENERIC_MEDIA_TYPE);
            SetBasicAuthHeader(request);
            HttpCompletionOption option = HttpCompletionOption.ResponseContentRead;
            Task<HttpResponseMessage> response = httpClient.SendAsync(request, option);
            HttpResponseMessage message = response.Result;
            message.EnsureSuccessStatusCode();
        }

        public void Delete(string uri)
        {
            Delete(uri, null);
        }

        public void SetBasicAuthHeader(HttpRequestMessage request)
        {           
            request.Headers.Add("Authorization", this.authorizationHeader);
        }
    }
}
