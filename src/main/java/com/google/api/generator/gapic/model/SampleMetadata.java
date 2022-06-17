package com.google.api.generator.gapic.model;

import com.google.api.generator.gapic.composer.samplecode.SampleMetadataComposer;
import java.util.List;

// Defined in cl/413363919
public class SampleMetadata {
  public final SampleIndex sampleIndex;

  //  ex: clientLibraryName = com.google.cloud:google-cloud-redis
  public SampleMetadata(
      String clientLibraryName, List<Api> apiList, List<GapicClass> gapicClasses) {
    //  Library version cannot be updated on metadata generation, but on library release
    //  ex: libraryVersion = 2.5.0
    // TODO: Use @sofialeon's helper function to update java post processor - see
    // https://github.com/googleapis/synthtool/pull/1356
    String libraryVersion = "generated";
    ClientLibrary clientLibrary =
        new ClientLibrary(clientLibraryName, libraryVersion, Language.JAVA, apiList);
    this.sampleIndex =
        new SampleIndex(clientLibrary, SampleMetadataComposer.composeSnippets(gapicClasses));
  }

  private static class SampleIndex {
    private final ClientLibrary clientLibrary;
    private final List<Snippet> snippets;

    public SampleIndex(ClientLibrary clientLibrary, List<Snippet> snippets) {
      this.clientLibrary = clientLibrary;
      this.snippets = snippets;
    }
  }

  // One sample.
  public static class Snippet {
    // Does not include the square brackets or the START or END indicators.
    private final String regionTag;

    // The title of the snippet, for human consumption mostly.
    private final String title;

    // A description of the snippet, for human consumption mostly.
    private final String description;

    // Path should be relative to the GitHub repo root and should not include branch, tag,
    // commitish, etc.
    private final String file;

    private final Language language;

    private final ClientMethod clientMethod;

    private final boolean canonical;

    private final Origin origin;

    // The different segments of the snippet. Must contain the FULL segment always. May be overlap
    // between segments.
    private final List<Segment> segment;

    public Snippet(
        String regionTag,
        String title,
        String description,
        String file,
        Language language,
        ClientMethod clientMethod,
        boolean canonical,
        Origin origin,
        List<Segment> segment) {
      this.regionTag = regionTag;
      this.title = title;
      this.description = description;
      this.file = file;
      this.language = language;
      this.clientMethod = clientMethod;
      this.canonical = canonical;
      this.origin = origin;
      this.segment = segment;
    }

    // The origin of the snippet.
    public enum Origin {
      // The snippet is generated from the API definition only, no configuration (SnippetGen 1.0)
      API_DEFINITION,

      // The snippet is generated from the API definition and a specific snippet configuration
      // (SnippetGen 2.0)
      CONFIG,
    }

    // A segment of the snippet.
    public static class Segment {
      // The line where this segment begins, inclusive.
      // For the FULL segment, this will be the START region tag line + 1.
      private final int start;

      // The line where this segment ends, inclusive.
      // For the FULL segment, this will be the END region tag line - 1.
      private final int end;

      // The type of the segment.
      private final SegmentType type;

      public Segment(int start, int end, SegmentType type) {
        this.start = start;
        this.end = end;
        this.type = type;
      }

      // The type of the segment.
      // Basically describes what this segment shows.
      private enum SegmentType {
        // The full sample including import statements.
        FULL,

        // A shorter version of the full sample, may not include imports/langauge specific
        // initialization
        SHORT,

        // The segment contains the service client initialization code only.
        CLIENT_INITIALIZATION,

        // The segment contains the request initialization code only.
        REQUEST_INITIALIZATION,

        // The segment contains the request execution code only.
        REQUEST_EXECUTION,

        // The segment contains the response handling code only.
        RESPONSE_HANDLING,
      }
    }
  }

  // A client library method.
  public static class ClientMethod {
    // The short name of the method, usually the name it is declared with.
    // This may not be unique within the service client because of overloads.
    private final String shortName;

    // The fully qualified name of the method, which is the short_name qualified
    // by the full_name of the service client.
    // Redundant, but present to make it easier for consumers to obtain it.
    // This may not be unique within the service client because of overloads.
    private final String fullName;

    // Is method synchronous or asynchronous
    private final boolean async;

    // Parameters of this method in the same order as they appear on the method declaration.
    // Must be empty if the method has no parameters.
    private final List<Parameter> parameter;

    // Fully qualified type name of this method result, if any.
    private final String resultType;

    // The service client this method is declared in.
    private final ServiceClient client;

    // The service method this client method is for.
    private final Method method;

    public ClientMethod(
        String shortName,
        String fullName,
        boolean async,
        List<Parameter> parameter,
        String resultType,
        ServiceClient client,
        Method method) {
      this.shortName = shortName;
      this.fullName = fullName;
      this.async = async;
      this.parameter = parameter;
      this.resultType = resultType;
      this.client = client;
      this.method = method;
    }

    // A method parameter as described by its type and name.
    public static class Parameter {
      // Fully qualified type name of this parameter.
      private final String type;

      // Name of the parameter as it appears on the method declaration.
      private final String name;

      public Parameter(String type, String name) {
        this.type = type;
        this.name = name;
      }
    }
  }

  // A service client defined in the client library specified in Index.
  public static class ServiceClient {
    // The short name of the service client, usually the name it is declared with.
    private final String shortName;

    // The fully qualified name of the service client, which is the short_name
    // qualified by the namespace/package/type name this client is declared in.
    private final String fullName;

    public ServiceClient(String shortName, String fullName) {
      this.shortName = shortName;
      this.fullName = fullName;
    }
  }

  // A client library.
  private static class ClientLibrary {
    // The name of the client library.
    // Examples: "Google.Cloud.Translate.V3",
    // "cloud.google.com/go/translate/apiv3".
    private final String name;

    // The full version of the client library.
    // Cannot be updated on metadata generation, but on library release.
    // Examples: "4.3.0", "2.5.2-beta01"
    private final String version;

    // The programming language the library is written in.
    private final Language language;

    // The APIs this client library is for.
    // Some languages bundle several APIs on the same client library.
    private final List<Api> api;

    public ClientLibrary(String name, String version, Language language, List<Api> api) {
      this.name = name;
      this.version = version;
      this.language = language;
      this.api = api;
    }
  }

  public static class Method {
    // The short name of the method, which is the name used to
    // declare it within the proto file. This is unique within the service,
    // but may not be unique within the API.
    private final String shortName;

    // The full name of the method, which is the short name qualified
    // by the full name of the service in which it is declared.
    // This is globally unique.
    private final String fullName;

    // The service this method is declared in.
    private final Service service;

    public Method(String shortName, String fullName, Service service) {
      this.shortName = shortName;
      this.fullName = fullName;
      this.service = service;
    }
  }

  // A service defined in the API the client library referenced in Index is for.
  public static class Service {
    // The short name of the service, which is the name used to
    // declare it within the proto file. This is usually, but not
    // absolutely necessarily, unique within an API.
    // Example: "TranslationService"
    private final String shortName;

    // The full name of the service, which is the short name qualified
    // by the package of the proto in which it is declared.
    // This is globally unique.
    // Example: "google.cloud.translate.v3.TranslationService"
    private final String fullName;

    public Service(String shortName, String fullName) {
      this.shortName = shortName;
      this.fullName = fullName;
    }
  }

  // An API
  public static class Api {
    // The ID of the API, identical to the protobuf package
    // ending with a version number.
    // Example: "google.cloud.translate.v3"
    private final String id;

    // The full version inferred from the end of the ID.
    // Examples: "v3", "v2beta1", "v1beta"
    private final String version;

    public Api(String id, String version) {
      this.id = id;
      this.version = version;
    }
  }

  // A programming language
  public enum Language {
    JAVA;
  }
}
