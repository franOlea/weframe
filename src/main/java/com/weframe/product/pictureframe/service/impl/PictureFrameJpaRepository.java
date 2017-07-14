package com.weframe.product.pictureframe.service.impl;

import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.PictureFrameRepository;
import com.weframe.product.pictureframe.service.exception.InvalidPictureFramePersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("unused")
public interface PictureFrameJpaRepository extends PictureFrameRepository, JpaRepository<PictureFrame, Long> {

	@Override
	default PictureFrame persist(final PictureFrame pictureFrame)
			throws InvalidPictureFramePersistenceException {
		try {
			return save(pictureFrame);
		} catch(DataAccessException e) {
			throw new InvalidPictureFramePersistenceException(e);
		}
	}

	@Override
	default void remove(final Long id)
			throws InvalidPictureFramePersistenceException {
		try {
			delete(id);
		} catch(DataAccessException e) {
			throw new InvalidPictureFramePersistenceException(e);
		}
	}

	@Override
	default PictureFrame get(final String userEmail, final Long id)
			throws InvalidPictureFramePersistenceException {
		try {
			return findFirstByUserEmailAndId(userEmail, id);
		} catch(DataAccessException e) {
			throw new InvalidPictureFramePersistenceException(e);
		}
	}

	@Override
	default List<PictureFrame> get(final String userEmail,
								   final int page,
								   final int size)
			throws InvalidPictureFramePersistenceException {
		try {
			return findByUserEmail(userEmail, new PageRequest(page, size));
		} catch(DataAccessException e) {
			throw new InvalidPictureFramePersistenceException(e);
		}
	}

	PictureFrame findFirstByUserEmailAndId(final String userEmail,
													   final Long id);
	List<PictureFrame> findByUserEmail(final String userEmail,
											 final Pageable pageable);
}
