package us.idinfor.hemophilia.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "infusionApi",
        version = "v1",
        resource = "infusion",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hemophilia.idinfor.us",
                ownerName = "backend.hemophilia.idinfor.us",
                packagePath = ""
        )
)
public class InfusionEndpoint {

    private static final Logger logger = Logger.getLogger(InfusionEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;


    /**
     * Returns the {@link Infusion} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Infusion} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "infusion/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Infusion get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Infusion with ID: " + id);
        Infusion infusion = OfyService.ofy().load().type(Infusion.class).id(id).now();
        if (infusion == null) {
            throw new NotFoundException("Could not find Infusion with ID: " + id);
        }
        return infusion;
    }

    /**
     * Inserts a new {@code Infusion}.
     */
    @ApiMethod(
            name = "insert",
            path = "infusion",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Infusion insert(Infusion infusion) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that infusion.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        OfyService.ofy().save().entity(infusion).now();
        logger.info("Created Infusion with ID: " + infusion.getId());

        return ofy().load().entity(infusion).now();
    }

    /**
     * Updates an existing {@code Infusion}.
     *
     * @param id       the ID of the entity to be updated
     * @param infusion the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Infusion}
     */
    @ApiMethod(
            name = "update",
            path = "infusion/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Infusion update(@Named("id") Long id, Infusion infusion) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        OfyService.ofy().save().entity(infusion).now();
        logger.info("Updated Infusion: " + infusion);
        return ofy().load().entity(infusion).now();
    }

    /**
     * Deletes the specified {@code Infusion}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Infusion}
     */
    @ApiMethod(
            name = "remove",
            path = "infusion/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        OfyService.ofy().delete().type(Infusion.class).id(id).now();
        logger.info("Deleted Infusion with ID: " + id);
    }

    /*@ApiMethod(
            name = "list",
            path = "infusion",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Infusion> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Infusion> query = OfyService.ofy().load().type(Infusion.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Infusion> queryIterator = query.iterator();
        List<Infusion> infusionList = new ArrayList<Infusion>(limit);
        while (queryIterator.hasNext()) {
            infusionList.add(queryIterator.next());
        }
        return CollectionResponse.<Infusion>builder().setItems(infusionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }*/

    @ApiMethod(
            name = "list",
            path = "infusion",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Infusion> list(@Named("deviceId") String deviceId) {
        Query<Infusion> query = OfyService.ofy().load().type(Infusion.class)
                .filter("deviceId",deviceId)
                .order("-time");
        QueryResultIterator<Infusion> queryIterator = query.iterator();
        List<Infusion> infusionList = new ArrayList<>();
        while (queryIterator.hasNext()) {
            infusionList.add(queryIterator.next());
        }
        return CollectionResponse.<Infusion>builder().setItems(infusionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }


    private void checkExists(Long id) throws NotFoundException {
        try {
            OfyService.ofy().load().type(Infusion.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Infusion with ID: " + id);
        }
    }
}