package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class ReminderDAOImpl implements ReminderDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private SessionFactory sessionFactory;

	@Autowired
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) {
		sessionFactory.getCurrentSession().save(reminder);
		return true;
	}

	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		sessionFactory.getCurrentSession().update(reminder);
		return true;
	}

	/*
	 * Remove an existing reminder
	 */

	public boolean deleteReminder(int reminderId) {
		boolean flag = false;
		try {
			if(getReminderById(reminderId)==null) {	
				flag = false;
			}else {
				sessionFactory.getCurrentSession().detach(getReminderById(reminderId));
				flag = true;
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}catch (ReminderNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * Retrieve details of a specific reminder
	 */

	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Reminder reminder = session.get(Reminder.class, reminderId);
		if (reminder ==null) {
			throw new ReminderNotFoundException("ReminderNotFoundException");
		}
		return reminder;

	}

	/*
	 * Retrieve details of all reminders by userId
	 */

	public List<Reminder> getAllReminderByUserId(String userId) {
		String hql = "FROM Reminder reminder where reminderCreatedBy = :userId ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId);
		List result = query.getResultList();
		return result;

	}

}
