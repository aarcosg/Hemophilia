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
        name = "bleedApi",
        version = "v1",
        resource = "bleed",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hemophilia.idinfor.us",
                ownerName = "backend.hemophilia.idinfor.us",
                packagePath = ""
        )
)
public class BleedEndpoint {

    private static final Logger logger = Logger.getLogger(BleedEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    /**
     * Returns the {@link Bleed} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Bleed} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "bleed/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Bleed get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Bleed with ID: " + id);
        Bleed bleed = ofy().load().type(Bleed.class).id(id).now();
        if (bleed == null) {
            throw new NotFoundException("Could not find Bleed with ID: " + id);
        }
        return bleed;
    }

    /**
     * Inserts a new {@code Bleed}.
     */
    @ApiMethod(
            name = "insert",
            path = "bleed",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Bleed insert(Bleed bleed) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that bleed.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(bleed).now();
        logger.info("Created Bleed.");

        return ofy().load().entity(bleed).now();
    }

    /**
     * Updates an existing {@code Bleed}.
     *
     * @param id    the ID of the entity to be updated
     * @param bleed the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bleed}
     */
    @ApiMethod(
            name = "update",
            path = "bleed/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Bleed update(@Named("id") Long id, Bleed bleed) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(bleed).now();
        logger.info("Updated Bleed: " + bleed);
        return ofy().load().entity(bleed).now();
    }

    /**
     * Deletes the specified {@code Bleed}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bleed}
     */
    @ApiMethod(
            name = "remove",
            path = "bleed/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Bleed.class).id(id).now();
        logger.info("Deleted Bleed with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "bleed",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Bleed> list(@Named("deviceId") String deviceId) {
        Query<Bleed> query = ofy().load().type(Bleed.class)
                .filter("deviceId",deviceId)
                .order("-time");
        QueryResultIterator<Bleed> queryIterator = query.iterator();
        List<Bleed> bleedList = new ArrayList<>();
        while (queryIterator.hasNext()) {
            bleedList.add(queryIterator.next());
        }
        return CollectionResponse.<Bleed>builder().setItems(bleedList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
    /*public CollectionResponse<Bleed> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Bleed> query = ofy().load().type(Bleed.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Bleed> queryIterator = query.iterator();
        List<Bleed> bleedList = new ArrayList<Bleed>(limit);
        while (queryIterator.hasNext()) {
            bleedList.add(queryIterator.next());
        }
        return CollectionResponse.<Bleed>builder().setItems(bleedList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }*/



    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Bleed.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Bleed with ID: " + id);
        }
    }
}