package com.weframe.product.personalized.controller;

import com.weframe.controllers.ResponseGenerator;
import com.weframe.product.personalized.model.BackMat;
import com.weframe.product.personalized.service.PersonalizedProductService;
import com.weframe.user.service.security.UserIdentityResolver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/back-mats")
public class BackMatController extends AbstractPersonalizedProductController<BackMat> {

	public BackMatController(final ResponseGenerator<BackMat> responseGenerator,
							 final PersonalizedProductService<BackMat> service,
							 final UserIdentityResolver userIdentityResolver) {
		super(responseGenerator, service, userIdentityResolver);
	}


}
