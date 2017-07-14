package com.weframe.product.pictureframe.service;

import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.exception.InvalidPictureFramePersistenceException;

import java.util.List;

public interface PictureFrameRepository {

	PictureFrame persist(final PictureFrame pictureFrame)
			throws InvalidPictureFramePersistenceException;
	void remove(final Long id)
			throws InvalidPictureFramePersistenceException;
	PictureFrame get(final String userEmail, final Long id)
			throws InvalidPictureFramePersistenceException;
	List<PictureFrame> get(final String userEmail, final int page, final int size)
			throws InvalidPictureFramePersistenceException;

}
