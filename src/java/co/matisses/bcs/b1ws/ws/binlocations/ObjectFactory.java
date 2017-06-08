package co.matisses.bcs.b1ws.ws.binlocations;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the co.matisses.b1ws.binlocation package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: co.matisses.b1ws.binlocation
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BinLocationCollectionParams }
     *
     */
    public BinLocationCollectionParams createBinLocationCollectionParams() {
        return new BinLocationCollectionParams();
    }

    /**
     * Create an instance of {@link BinLocationCollectionParams.BinLocationParams
     * }
     *
     */
    public BinLocationCollectionParams.BinLocationParams createBinLocationCollectionParamsBinLocationParams() {
        return new BinLocationCollectionParams.BinLocationParams();
    }

    /**
     * Create an instance of {@link co.matisses.b1ws.binlocation.BinLocationParams
     * }
     *
     */
    public co.matisses.bcs.b1ws.ws.binlocations.BinLocationParams createBinLocationParams() {
        return new co.matisses.bcs.b1ws.ws.binlocations.BinLocationParams();
    }

    /**
     * Create an instance of {@link BinLocation }
     *
     */
    public BinLocation createBinLocation() {
        return new BinLocation();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     *
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link MsgHeader }
     *
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link GetResponse }
     *
     */
    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    /**
     * Create an instance of {@link GetListResponse }
     *
     */
    public GetListResponse createGetListResponse() {
        return new GetListResponse();
    }

    /**
     * Create an instance of {@link AddResponse }
     *
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link UpdateResponse }
     *
     */
    public UpdateResponse createUpdateResponse() {
        return new UpdateResponse();
    }

    /**
     * Create an instance of {@link GetList }
     *
     */
    public GetList createGetList() {
        return new GetList();
    }

    /**
     * Create an instance of {@link Update }
     *
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link Add }
     *
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link Delete }
     *
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link Get }
     *
     */
    public Get createGet() {
        return new Get();
    }

}
