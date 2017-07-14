package com.weframe.configuration;

import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.model.Picture;
import com.weframe.picture.model.UserPicture;
import com.weframe.product.generic.model.BackBoard;
import com.weframe.product.generic.model.Frame;
import com.weframe.product.generic.model.FrameGlass;
import com.weframe.product.generic.model.MatType;
import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.user.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class ControllerConfiguration {

    @Bean
    public ResponseGenerator<User> getUserResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<BackBoard> getBackBoardResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<Picture> getPictureResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<Frame> getFrameResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<UserPicture> getUserPictureResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<FrameGlass> getFrameGlassResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<MatType> getMatTypeResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<PictureFrame> getPictureFrameResponseGenerator() {
        return new ResponseGenerator<>();
    }
}
