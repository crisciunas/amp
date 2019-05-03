package org.digijava.kernel.ampapi.endpoints.contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants;
import org.digijava.kernel.ampapi.endpoints.common.EPConstants;
import org.digijava.kernel.ampapi.endpoints.common.EndpointUtils;
import org.digijava.kernel.ampapi.endpoints.errors.ApiError;
import org.digijava.kernel.ampapi.endpoints.errors.ApiErrorMessage;
import org.digijava.kernel.ampapi.endpoints.errors.ApiErrorResponse;
import org.digijava.kernel.ampapi.endpoints.exception.ApiExceptionMapper;
import org.digijava.kernel.ampapi.endpoints.util.JsonBean;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.aim.dbentity.AmpContact;
import org.digijava.module.aim.util.ContactInfoUtil;

/**
 * @author Octavian Ciubotaru
 */
public final class ContactUtil {

    private ContactUtil() {
    }

    public static Collection<JsonBean> getContacts(List<Long> ids) {
        Map<Long, JsonBean> jsonContacts = new TreeMap<>();
        ids = new ArrayList<>(new TreeSet<>(ids));
        ContactExporter exporter = new ContactExporter();

        for (int fromIndex = 0; fromIndex < ids.size(); fromIndex += ActivityEPConstants.BATCH_DB_QUERY_SIZE) {
            // for simplicity using the same DB batch size as for activities
            int end = Math.min(ids.size(), fromIndex + ActivityEPConstants.BATCH_DB_QUERY_SIZE);
            List<Long> currentIds = ids.subList(fromIndex, end);
            List<AmpContact> contacts = ContactInfoUtil.getContacts(currentIds);
            contacts.forEach(contact -> {
                JsonBean result;
                try {
                    result = exporter.export(contact);
                } catch (Exception e) {
                    result = ApiError.toError(ApiExceptionMapper.INTERNAL_ERROR.withDetails(e.getMessage()));
                    result.set(ContactEPConstants.ID, contact.getId());
                } finally {
                    PersistenceManager.getSession().evict(contact);
                }
                jsonContacts.put(contact.getId(), result);
            });
            PersistenceManager.getSession().clear();
        }
        // Always succeed on normal exit, no matter if some activities export failed
        EndpointUtils.setResponseStatusMarker(HttpServletResponse.SC_OK);
        return jsonContacts.values();
    }

    public static JsonBean getContact(Long id) {
        AmpContact contact = (AmpContact) PersistenceManager.getSession().get(AmpContact.class, id);

        if (contact == null) {
            ApiErrorResponse.reportResourceNotFound(ContactErrors.CONTACT_NOT_FOUND);
        }

        return new ContactExporter().export(contact);
    }
}
