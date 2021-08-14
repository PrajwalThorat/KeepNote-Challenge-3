package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private SessionFactory sessionFactory;
	
	@Autowired
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		sessionFactory.getCurrentSession().save(category);
		return true;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		try {
			Category exitCategory = getCategoryById(categoryId);
			if(exitCategory!=null) {
				sessionFactory.getCurrentSession().delete(exitCategory);
			}
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}
		return true;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		
		try {
			Category exitCategory = getCategoryById(category.getCategoryId());
			if(exitCategory!=null) {
				sessionFactory.getCurrentSession().update(category);
			}
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}
		return true;

	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Category category = session.get(Category.class, categoryId);
		if(category==null) {
			throw new CategoryNotFoundException("CategoryNotFoundException");
		}
		return category;

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		String hql = "FROM Category category where categoryCreatedBy = : userId";
		 Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId);
		 List result = query.getResultList();
 		return result;

	}

}
