package com.weframe.product.pictureframe.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.PictureFrameRepository;
import com.weframe.product.pictureframe.service.PictureFrameService;
import com.weframe.product.pictureframe.service.exception.InvalidPictureFramePersistenceException;

import java.util.List;

public class PictureFrameServiceImpl extends PictureFrameService {

	public PictureFrameServiceImpl(final PictureFrameRepository repository) {
		super(repository);
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
	public PictureFrame persist(final PictureFrame pictureFrame)
			throws InvalidPictureFramePersistenceException {
		return repository.persist(pictureFrame);
	}

	@Override
	public void delete(final Long id)
			throws InvalidPictureFramePersistenceException {
		repository.remove(id);
	}
}
