package com.weframe.product.personalized.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.personalized.model.PictureFrameComponent;

import java.util.List;

public class PersonalizedProductService<T extends PictureFrameComponent> {

	protected final PersonalizedProductRepository<T> repository;

	public PersonalizedProductService(final PersonalizedProductRepository<T> repository) {
		this.repository = repository;
	}

	public T getById(final Long id, final String userIdentity) throws EmptyResultException, InvalidPersonalizedProductPersistenceException {
		return null;
	}

	public List<T> getAllByUserIdentity(final String userIdentity) throws EmptyResultException, InvalidPersonalizedProductPersistenceException {
		return null;
	}

	public T getByPictureFrameId(final Long id) throws EmptyResultException, InvalidPersonalizedProductPersistenceException {
		return null;
	}

	public void persist(final T t) throws InvalidPersonalizedProductPersistenceException {

	}

	public void delete(final Long id) throws InvalidPersonalizedProductPersistenceException {

	}

}
