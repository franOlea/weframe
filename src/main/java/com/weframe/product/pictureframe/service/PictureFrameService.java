package com.weframe.product.pictureframe.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.exception.InvalidPictureFramePersistenceException;
import com.weframe.user.service.persistence.UserService;
import org.apache.log4j.Logger;

import java.util.List;

public abstract class PictureFrameService {

	protected static final Logger logger = Logger.getLogger(PictureFrameService.class);

	protected final PictureFrameRepository repository;
	protected final UserService userService;

	public PictureFrameService(final PictureFrameRepository repository,
							   final UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}

	public abstract PictureFrame getById(final Long id,
										 final String userIdentity)
			throws InvalidPictureFramePersistenceException, EmptyResultException;

	public abstract List<PictureFrame> getAll(final String userIdentity,
											  final int page,
											  final int size)
			throws InvalidPictureFramePersistenceException, EmptyResultException;

	public abstract PictureFrame persist(final String userIdentity,
										 final PictureFrame pictureFrame)
			throws InvalidPictureFramePersistenceException;

	public abstract void delete(final Long id)
			throws InvalidPictureFramePersistenceException;

}
