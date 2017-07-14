package com.weframe.product.pictureframe.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.PictureFrameRepository;
import com.weframe.product.pictureframe.service.PictureFrameService;
import com.weframe.product.pictureframe.service.exception.InvalidPictureFramePersistenceException;
import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;

import java.util.List;

public class PictureFrameServiceImpl extends PictureFrameService {

	public PictureFrameServiceImpl(final PictureFrameRepository repository,
								   final UserService userService) {
		super(repository, userService);
	}

	@Override
	public PictureFrame getById(final Long id,
								final String userIdentity)
			throws InvalidPictureFramePersistenceException, EmptyResultException {
		return repository.get(userIdentity, id);
	}

	@Override
	public List<PictureFrame> getAll(final String userIdentity,
									 final int page,
									 final int size)
			throws InvalidPictureFramePersistenceException, EmptyResultException {
		return repository.get(userIdentity, page, size);
	}

	@Override
	public PictureFrame persist(final String userIdentity,
								final PictureFrame pictureFrame)
			throws InvalidPictureFramePersistenceException {
		try {
			User owner = userService.getByEmail(userIdentity);
			pictureFrame.setUser(owner);
			return repository.persist(pictureFrame);
		} catch (EmptyResultException | InvalidUserPersistenceException e) {
			throw new InvalidPictureFramePersistenceException(e);
		}
	}

	@Override
	public void delete(final Long id)
			throws InvalidPictureFramePersistenceException {
		repository.remove(id);
	}
}
