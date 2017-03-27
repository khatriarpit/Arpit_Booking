package com.emxcel.dms.core.business.services.common.generic;

import java.io.Serializable;
import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.model.generic.DMSEntity;

/**
 * <p>
 * Service racine pour la gestion des entités.
 * </p>
 *
 * @param <T>
 */
public interface DMSEntityService<K extends Serializable & Comparable<K>, E extends DMSEntity<K, ?>>
		extends TransactionalAspectAwareService {

	/**
	 * Crée l'entité dans la base de données. Mis à part dans les tests pour
	 * faire des sauvegardes simples, utiliser create() car il est possible
	 * qu'il y ait des listeners sur la création d'une entité.
	 * 
	 * @param entity
	 *            entité
	 */
	void save(E entity) throws ServiceException;

	/**
	 * Met à jour l'entité dans la base de données.
	 * 
	 * @param entity
	 */
	void update(E entity) throws ServiceException;

	/**
	 * Crée l'entité dans la base de données.
	 * 
	 * @param entity
	 *            entité
	 */
	void create(E entity) throws ServiceException;

	/**
	 * Supprime l'entité de la base de données
	 * 
	 * @param entity
	 *            entité
	 */
	void delete(E entity) throws ServiceException;

	/**
	 * Retourne une entité à partir de son id.
	 * 
	 * @param id
	 *            identifiant
	 * @return entité
	 */
	E getById(K id);

	/**
	 * Renvoie la liste de l'ensemble des entités de ce type.
	 * 
	 * @return liste d'entités
	 */
	List<E> list();

	/**
	 * Compte le nombre d'entités de ce type présentes dans la base.
	 * 
	 * @return nombre d'entités
	 */
	Long count();

	/**
	 * Flushe la session.
	 */
	void flush();
	
	/**
	 * Crée l'entité dans la base de données. Mis à part dans les tests pour
	 * faire des sauvegardes simples, utiliser create() car il est possible
	 * qu'il y ait des listeners sur la création d'une entité.
	 * 
	 * @param entity
	 *            entité
	 */
	Long saveReturnID(E entity) throws ServiceException;
	
	/**
	 * Crée l'entité dans la base de données. Mis à part dans les tests pour
	 * faire des sauvegardes simples, utiliser create() car il est possible
	 * qu'il y ait des listeners sur la création d'une entité.
	 * 
	 * @param entity
	 *            entité
	 */
	E saveReturnEntity(E entity) throws ServiceException;
}