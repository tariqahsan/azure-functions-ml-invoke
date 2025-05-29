package mil.dtic.tagger;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

/**
 * Azure Functions with HTTP Trigger.
 */
// public class MLInvokeFunction {
//     /**
//      * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
//      * 1. curl -d "HTTP Body" {your host}/api/HttpExample
//      * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
//      */
//     @FunctionName("HttpExample")
//     public HttpResponseMessage run(
//             @HttpTrigger(
//                 name = "req",
//                 methods = {HttpMethod.GET, HttpMethod.POST},
//                 authLevel = AuthorizationLevel.ANONYMOUS)
//                 HttpRequestMessage<Optional<String>> request,
//             final ExecutionContext context) {
//         context.getLogger().info("Java HTTP trigger processed a request.");

//         // Parse query parameter
//         final String query = request.getQueryParameters().get("name");
//         final String name = request.getBody().orElse(query);

//         if (name == null) {
//             return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
//         } else {
//             return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
//         }
//     }
// }

// import com.microsoft.azure.functions.annotation.*;
// import com.microsoft.azure.functions.*;

// import java.io.OutputStream;
// import java.net.HttpURLConnection;
// import java.net.URI;
// import java.net.URL;
// import java.util.Optional;
// import java.util.Scanner;

// public class MLInvokeFunction {
//     @FunctionName("invokeAML")
//     public HttpResponseMessage run(
//         @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION)
//         HttpRequestMessage<Optional<String>> request,
//         final ExecutionContext context) {

//         context.getLogger().info("Java HTTP trigger processed a request.");

//         try {
//             String amlUrl = System.getenv("AML_ENDPOINT_URL");
//             String amlKey = System.getenv("AML_API_KEY");

//             URI uri = new URI(amlUrl);
//             URL url = uri.toURL(); // Safe conversion as URL(String) being deprecated
//             //URL url = new URL(amlUrl);
//             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//             conn.setRequestMethod("POST");
//             conn.setRequestProperty("Content-Type", "application/json");
//             conn.setRequestProperty("Authorization", "Bearer " + amlKey);
//             conn.setDoOutput(true);

//             // Send JSON input
//             String jsonInput = request.getBody().orElse("{}");
//             try (OutputStream os = conn.getOutputStream()) {
//                 os.write(jsonInput.getBytes());
//                 os.flush();
//             }

//             // Read the response
//             Scanner scanner = new Scanner(conn.getInputStream());
//             StringBuilder response = new StringBuilder();
//             while (scanner.hasNext()) {
//                 response.append(scanner.nextLine());
//             }
//             scanner.close();

//             return request.createResponseBuilder(HttpStatus.OK).body(response.toString()).build();

//         } catch (Exception e) {
//             context.getLogger().severe("Error: " + e.getMessage());
//             return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
//                           .body("Error invoking AML: " + e.getMessage()).build();
//         }
//     }
// }

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;

/**
 * Azure Function with HTTP Trigger to invoke an AML endpoint
 */
public class InvokeAMLFunction {

    @FunctionName("invokeAML")
    public HttpResponseMessage run(
        @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<String> request,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java HTTP trigger - invoking Azure ML endpoint.");

        // AML endpoint and API keys stored as environment variables
        String amlEndpoint = System.getenv("AML_ENDPOINT_URL");
        String amlKey = System.getenv("AML_API_KEY");

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(amlEndpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + amlKey)
                .POST(BodyPublishers.ofString(request.getBody()))
                .build();

            HttpResponse<String> amlResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return request.createResponseBuilder(HttpStatus.valueOf(amlResponse.statusCode()))
                    .header("Content-Type", "application/json")
                    .body(amlResponse.body())
                    .build();

        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            context.getLogger().severe("Error calling AML endpoint: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Failed to invoke AML endpoint.\"}")
                    .build();
        } catch (Exception e) {
            context.getLogger().severe("Unexpected error: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Unexpected error occurred.\"}")
                    .build();
        }
    }
}
