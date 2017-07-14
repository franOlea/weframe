package com.weframe.product.pictureframe.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.PictureFrameRepository;
import com.weframe.product.pictureframe.service.PictureFrameService;
import com.weframe.user.service.persistence.UserService;

import java.util.Collection;

public class PictureFrameServiceImpl extends PictureFrameService {

	public PictureFrameServiceImpl(final PictureFrameRepository repository,
								   final UserService userService) {
		super(repository, userService);
	}

	@Override
	public PictureFrame getById(final Long id,
								final String userIdentity)
			throws InvalidGenericProductPersistenceException, EmptyResultException {
		return null;
	}

	@Override
	public Collection<PictureFrame> getAll(final String userIdentity,
										   final int page,
										   final int size)
			throws InvalidGenericProductPersistenceException, EmptyResultException {
		return null;
	}

	@Override
	public void persist(final PictureFrame pictureFrame)
			throws InvalidGenericProductPersistenceException {

	}

	@Override
	public void delete(final Long id)
			throws InvalidGenericProductPersistenceException {

	}
}
