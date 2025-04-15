package lis.shop.billion.controller;

import lis.shop.billion.controller.AvatarController;
import lis.shop.billion.entity.UserDetails;
import lis.shop.billion.service.ImageService;
import lis.shop.billion.service.SecurityService;
import lis.shop.billion.service.UserDetailsCustomService;
import lis.shop.billion.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.hamcrest.Matchers.containsString;


@WebMvcTest(AvatarController.class)
class AvatarControllerTest {
// TODO Сделать тесты робочими
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private UserService userService;

    @MockBean
    private ImageService imageService;

    @MockBean
    private UserDetailsCustomService userDetailsService;

    @Test
    void testGetAvatar_Success() throws Exception {
        String email = "test@example.com";
        String photoName = "avatar.jpg";
        byte[] content = "dummy image content".getBytes();
        Resource resource = new ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return photoName;
            }
        };

        Mockito.when(securityService.getAuthenticatedUserEmail()).thenReturn(email);
        Mockito.when(userService.findPhotoNameByEmail(email)).thenReturn(photoName);
        Mockito.when(imageService.loadImage(photoName)).thenReturn(resource);

        mockMvc.perform(get("/api/user/avatar"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=\"avatar.jpg\""));
    }

    @Test
    void testGetAvatar_NotFound() throws Exception {
        Mockito.when(securityService.getAuthenticatedUserEmail()).thenReturn("test@example.com");
        Mockito.when(userService.findPhotoNameByEmail("test@example.com")).thenReturn(null);

        mockMvc.perform(get("/api/user/avatar"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUploadAvatar_EmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "photo", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]
        );

        mockMvc.perform(multipart("/api/user/avatar").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Файл пустий"));
    }

    @Test
    void testUploadAvatar_FailureOnSave() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "photo", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, "some image".getBytes()
        );

        Mockito.when(securityService.getAuthenticatedUserEmail()).thenReturn("test@example.com");
        Mockito.when(imageService.saveImage(any())).thenThrow(new IOException("Disk full"));

        mockMvc.perform(multipart("/api/user/avatar").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Не вдалося зберегти файл"));
    }
}