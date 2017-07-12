package com.weframe.product.personalized.model;

import com.weframe.product.pictureframe.PictureFrame;

import javax.persistence.*;

@MappedSuperclass
public abstract class PictureFrameComponent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PICTURE_FRAME", nullable = false)
	private PictureFrame pictureFrame;

	public PictureFrameComponent() {
	}

	public PictureFrameComponent(final Long id,
								 final PictureFrame pictureFrame) {
		this.id = id;
		this.pictureFrame = pictureFrame;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public PictureFrame getPictureFrame() {
		return pictureFrame;
	}

	public void setPictureFrame(final PictureFrame pictureFrame) {
		this.pictureFrame = pictureFrame;
	}

}
