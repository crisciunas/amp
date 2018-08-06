package org.digijava.kernel.ampapi.endpoints.resource;

import org.digijava.kernel.ampapi.endpoints.activity.AmpFieldsEnumerator;
import org.digijava.kernel.ampapi.endpoints.activity.ObjectExporter;

/**
 * @author Viorel Chihai
 */
public class ResourceExporter extends ObjectExporter<AmpResource> {

    public ResourceExporter() {
        super(AmpFieldsEnumerator.PUBLIC_ENUMERATOR.getResourceFields());
    }
}
