package com.github.rsrini7.api.data;

import com.yahoo.elide.Elide;
import com.yahoo.elide.ElideResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.Map;

import static com.github.rsrini7.api.data.JsonApiMediaType.JSON_API_MEDIA_TYPE;


/**
 * JSON-API compliant API.
 * Explicit api for /json-api/<entity>. 
 * But, automatic api available from /api/<entity> - Same as /json-api/<entity>
 * Swagger docs accessable from /swdocs
 * Graphql accessable from /api/graphql
 */
@RestController
@RequestMapping(path = DataController.PATH_PREFIX)
public class DataController {

  public static final String PATH_PREFIX = "/json-api";

  private final Elide elide;

  public DataController(Elide elide) {
    this.elide = elide;
  }

  @RequestMapping(
    method = RequestMethod.GET,
    produces = JSON_API_MEDIA_TYPE,
    value = {"/{entity}", "/{entity}/{id}/relationships/{entity2}", "/{entity}/{id}/{child}", "/{entity}/{id}"})
    public ResponseEntity<String> get(@RequestParam final Map<String, String> allRequestParams,
                                    final HttpServletRequest request) {
    ElideResponse response = elide.get(
      getJsonApiPath(request),
      new MultivaluedHashMap<>(allRequestParams),
            null

    );
    return wrapResponse(response);
  }

 @RequestMapping(
    method = RequestMethod.POST,
    consumes = {JSON_API_MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE},
    produces = JSON_API_MEDIA_TYPE,
    value = {"/{entity}", "/{entity}/{id}/relationships/{entity2}", "/{entity}/{id}/{child}", "/{entity}/{id}"})
  public ResponseEntity<String> post(@RequestBody final String body,
                                     final HttpServletRequest request) {
    ElideResponse response = elide.post(
      getJsonApiPath(request),
      body,
      null
    );
    return wrapResponse(response);
  }

  private ResponseEntity<String> wrapResponse(ElideResponse response) {
    return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
  }

  private static String getJsonApiPath(HttpServletRequest request) {
    return ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).replace(PATH_PREFIX, "");
  }

}
