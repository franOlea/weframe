package com.weframe.product.pictureframe.service;

import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.exception.InvalidPictureFramePersistenceException;
import com.weframe.user.model.User;

import java.util.Collection;

public interface PictureFrameRepository {

	void persist(final PictureFrame pictureFrame)
			throws InvalidPictureFramePersistenceException;
	void remove(final Long id)
			throws InvalidPictureFramePersistenceException;
	PictureFrame get(final Long id)
			throws InvalidPictureFramePersistenceException;
	Collection<PictureFrame> get(final User user, final int page, final int size)
			throws InvalidPictureFramePersistenceException;

}
