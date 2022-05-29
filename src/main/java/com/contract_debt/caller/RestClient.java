package com.contract_debt.caller;

import com.common.exception.BusinessException;
import com.common.util.ErrorCode;
import com.common.util.Json;
import com.dslplatform.json.JsonReader;
import com.dslplatform.json.JsonWriter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class RestClient {
    private static final Logger log = LogManager.getLogger(RestClient.class);
    private final HttpClient httpClient;
    public final Map<String, String> headers = Collections.singletonMap("Content-Type", "application/json");


    /**
     * Call API for GET method
     * Handle if have errors, (ex: 400, 500, ...)
     *
     * @param reader  decoder body response
     * @param url     destination url
     * @param headers headers
     * @param <RS>    type response data
     * @return CompletableFuture<RS>
     */
    public <RS> CompletableFuture<RS> getNHandleError(JsonReader.ReadObject<RS> reader, String url, Map<String, String> headers) {
        return get(url, headers)
                .thenApply(response -> handlerResponse(reader, url, response));
    }

    /**
     * Call API for GET method
     *
     * @param url     destination url
     * @param headers headers
     * @return CompletableFuture<HttpResponse < byte [ ]>>
     */
    public CompletableFuture<HttpResponse<byte[]>> get(String url, Map<String, String> headers) {
        var httpRequest = addHeader(url, headers).GET().build();

        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }

    /**
     * Call API for PUT method
     * Handle if have errors, (ex: 400, 500, ...)
     *
     * @param request Object Body
     * @param writer  encoder body request
     * @param reader  decoder body response
     * @param url     destination url
     * @param headers headers
     * @param <RS>    type response data
     * @return CompletableFuture<RS>
     */
    public <RQ, RS> CompletableFuture<RS> putNHandleError(RQ request, JsonWriter.WriteObject<RQ> writer, JsonReader.ReadObject<RS> reader, String url, Map<String, String> headers) {
        return put(request, writer, url, headers)
                .thenApply(response -> handlerResponse(reader, url, response));
    }

    /**
     * Call API for PUT method
     *
     * @param request Object Body
     * @param writer  encoder body request
     * @param url     destination url
     * @param headers headers
     * @param <RQ>    class request body
     * @return CompletableFuture<HttpResponse < byte [ ]>>
     */
    public <RQ> CompletableFuture<HttpResponse<byte[]>> put(RQ request, JsonWriter.WriteObject<RQ> writer, String url, Map<String, String> headers) {
        HttpRequest.BodyPublisher bodyPublisher = (request == null) ?
                HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofByteArray(Json.encode(request, writer));
        var httpRequest = addHeader(url, headers)
                .PUT(bodyPublisher)
                .build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }

    /**
     * Call API for POST method
     * Handle if have errors, (ex: 400, 500, ...)
     *
     * @param request Object Body
     * @param writer  encoder body request
     * @param reader  decoder body response
     * @param url     destination url
     * @param headers headers
     * @param <RS>    type response data
     * @return CompletableFuture<RS>
     */
    public <RQ, RS> CompletableFuture<RS> postNHandleError(RQ request, JsonWriter.WriteObject<RQ> writer, JsonReader.ReadObject<RS> reader, String url, Map<String, String> headers) {
        return post(request, writer, url, headers)
                .thenApply(response -> handlerResponse(reader, url, response));
    }


    /**
     * Call API for POST method
     *
     * @param request Object Body
     * @param writer  encoder body request
     * @param url     destination url
     * @param headers headers
     * @param <RQ>    class request body
     * @return CompletableFuture<RS>
     */
    public <RQ> CompletableFuture<HttpResponse<byte[]>> post(RQ request, JsonWriter.WriteObject<RQ> writer, String url, Map<String, String> headers) {
        var body = Json.encode(request, writer);
        var httpRequest = addHeader(url, headers)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }

    /**
     * Handle response
     * throw Exception when
     *
     * @param reader
     * @param url
     * @param response
     * @param <RS>
     * @return
     */
    private <RS> RS handlerResponse(JsonReader.ReadObject<RS> reader, String url, HttpResponse<byte[]> response) {
        if (response.statusCode() == 200) {
            log.info("Send result {} to {} successful", response.body(), url);
            return Json.decode(response.body(), reader);
        } else if (response.statusCode() / 100 == 4) {
            log.error("Send result to {} rejected, because: {}", url, response.body());
            throw new BusinessException(ErrorCode.INVALID_REQUEST, new String(response.body(),
                    StandardCharsets.UTF_8));
        } else {
            log.error("Send result to {} got error, because: {}", url, response.body());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, new String(response.body(), StandardCharsets.UTF_8));
        }
    }

    /**
     * Add header into request
     *
     * @param url
     * @param headers
     * @return
     */
    private HttpRequest.Builder addHeader(String url, Map<String, String> headers) {
        var requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url));
        headers.forEach(requestBuilder::header);
        return requestBuilder;
    }
}
