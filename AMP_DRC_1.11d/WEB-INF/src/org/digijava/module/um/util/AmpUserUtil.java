package org.digijava.module.um.util;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.kernel.user.User;
import org.digijava.kernel.util.UserUtils;
import org.digijava.module.aim.dbentity.AmpUserExtension;
import org.digijava.module.aim.dbentity.AmpUserExtensionPK;
import org.digijava.module.aim.exception.AimException;

/**
 * Methods for working with User related tasks.
 * TODO Let's move some user methods from DbUtil to this class, because DbUtil gets very huge.
 * @author Irakli Kobiashvili
 *
 */
public class AmpUserUtil {
	private static Logger logger = Logger.getLogger(AmpUserUtil.class);

	public static Collection<User> getAllUsers(boolean getBanned) {
		Session session = null;
		Query qry = null;
		Collection<User> users = new ArrayList<User>();

		try {
			session = PersistenceManager.getRequestDBSession();
			String queryString = "select u from " + User.class.getName() + " u"
					+ " where u.banned=:banned order by u.email";
			qry = session.createQuery(queryString);
			if ( getBanned )
				qry.setInteger("banned", 1);
			else
				qry.setInteger("banned", 0);
			users = qry.list();
		} catch (Exception e) {
			logger.error("Unable to get user");
			logger.error("Exception " + e);
			e.printStackTrace();
		}
		return users;
	}

    public static Collection<User> getAllNotVerifiedUsers() {
        Session session = null;
        Query qry = null;
        Collection<User> users = new ArrayList<User>();

        try {
            session = PersistenceManager.getRequestDBSession();
            String queryString = "select u from " + User.class.getName() + " u"
                    + " where u.emailVerified=:emailVerified order by u.email";
            qry = session.createQuery(queryString);
            qry.setBoolean("emailVerified", true);
            users = qry.list();
        } catch (Exception e) {
            logger.error("Unable to get user");
            logger.error("Exception " + e);
            e.printStackTrace();
        }
        return users;
	}


    public static void deleteUser(Long userId) {
        Session session = null;
        Transaction tx=null;

        try {
            session = PersistenceManager.getRequestDBSession();
            tx=session.beginTransaction();
            User user=(User)session.load(User.class,userId);
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            if(tx!=null){
                try{
                    tx.rollback();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
	}

	public static AmpUserExtension getAmpUserExtension(Long userId)
			throws AimException {
		User user = UserUtils.getUser(userId);
		return getAmpUserExtension(user);
	}

	public static AmpUserExtension getAmpUserExtension(User user)
			throws AimException {
		AmpUserExtensionPK key = new AmpUserExtensionPK(user);
		return getAmpUserExtension(key);
	}

	/**
	 * Retrieves user extension with pk class.
	 * @param key primary key for user extension
	 * @return db entity
	 * @throws AimException
	 */
	public static AmpUserExtension getAmpUserExtension(AmpUserExtensionPK key)
			throws AimException {
		AmpUserExtension result = null;
		try {
			Session session = PersistenceManager.getRequestDBSession();
			result = (AmpUserExtension) session.load(AmpUserExtension.class,
					key);
		} catch (ObjectNotFoundException notFounExetion){
			logger.debug("User Extension not found for user"+key.getUser().getEmail());
			return null;
		} catch (Exception e) {
			throw new AimException("Cannot retrive AmpUserExtension", e);
		}
		return result;
	}

	/**
	 * Save user extensions.
	 * @param userExtension db entity to save, with correct id class.
	 * @throws AimException
	 */
	public static void saveAmpUserExtension(AmpUserExtension userExtension)
			throws AimException {
		Transaction tx=null;
		try {
			Session session = PersistenceManager.getRequestDBSession();
			tx = session.beginTransaction();
			session.save(userExtension);
			tx.commit();
		} catch (Exception e) {
			if (tx!=null){
				try {
					tx.rollback();
				} catch (Exception e1) {
					throw new AimException("Cannot rollback userExtension save operation",e1);
				}
			}
			throw new AimException("Cannot save user extension",e);
		}
	}

}