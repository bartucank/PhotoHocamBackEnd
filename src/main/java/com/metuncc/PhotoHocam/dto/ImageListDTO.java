package com.metuncc.PhotoHocam.dto;

import com.metuncc.PhotoHocam.domain.Image;
import lombok.Data;

import java.util.List;

/**
 * this class is used for transferring the data of the image within the application
 */

public class ImageListDTO {

    /**
     * username of the user sent the image
     */
    private String username;

    /**
     * list of the images sent from the specified user
     */
    private List<ImageDTO> imageList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ImageDTO> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageDTO> imageList) {
        this.imageList = imageList;
    }
}
