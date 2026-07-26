package com.community.demo.integrated;

import com.community.demo.auth.security.SecurityUtils;
import com.community.demo.users.UserController;
import com.community.demo.users.UserService;
import com.community.demo.users.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class WebControllerIntegratedTests {

    private static final Long USER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Nested
    @DisplayName("회원 정보 조회")
    class ReadUser {

        @Test
        @DisplayName("GET /users/me 요청을 처리한다")
        void success() throws Exception {
            // given
            Authentication authentication = mock(Authentication.class);
            ReadUserResponseDto serviceResponse =
                    mock(ReadUserResponseDto.class);

            when(userService.readUser(USER_ID))
                    .thenReturn(serviceResponse);

            try (MockedStatic<SecurityUtils> securityUtils =
                         mockStatic(SecurityUtils.class)) {

                securityUtils.when(
                                () -> SecurityUtils.resolveAuthentication(
                                        authentication
                                )
                        )
                        .thenReturn(USER_ID);

                // when & then
                mockMvc.perform(
                                get("/users/me")
                                        .principal(authentication)
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(
                                MediaType.APPLICATION_JSON
                        ))
                        .andExpect(jsonPath("$").exists());

                securityUtils.verify(
                        () -> SecurityUtils.resolveAuthentication(authentication)
                );

                verify(userService).readUser(USER_ID);
            }
        }
    }

    @Nested
    @DisplayName("회원 생성")
    class CreateUser {

        @Test
        @DisplayName("POST /users 요청을 DTO로 바인딩하고 201을 반환한다")
        void success() throws Exception {
            // given
            UserResponseDto serviceResponse =
                    mock(UserResponseDto.class);

            when(userService.createUser(any(UserRequestDto.class)))
                    .thenReturn(serviceResponse);

            // when & then
            mockMvc.perform(
                            post("/users")
                                    .contentType(
                                            MediaType.APPLICATION_FORM_URLENCODED
                                    )
                                    .param("email", "test@test.com")
                                    .param("password", "Password123!")
                                    .param("nickname", "testUser")
                                    .param(
                                            "profileImageUrl",
                                            "/profile/default.png"
                                    )
                    )
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(jsonPath("$").exists());

            ArgumentCaptor<UserRequestDto> captor =
                    ArgumentCaptor.forClass(UserRequestDto.class);

            verify(userService).createUser(captor.capture());

            UserRequestDto request = captor.getValue();

            assertAll(
                    () -> assertEquals(
                            "test@test.com",
                            request.getEmail()
                    ),
                    () -> assertEquals(
                            "Password123!",
                            request.getPassword()
                    ),
                    () -> assertEquals(
                            "testUser",
                            request.getNickname()
                    ),
                    () -> assertEquals(
                            "/profile/default.png",
                            request.getProfileImageUrl()
                    )
            );
        }

        @Test
        @DisplayName("지원하지 않는 HTTP 메서드는 405를 반환한다")
        void methodNotAllowed() throws Exception {
            mockMvc.perform(
                            get("/users")
                    )
                    .andExpect(status().isMethodNotAllowed());

            verifyNoInteractions(userService);
        }
    }

    @Nested
    @DisplayName("회원 정보 수정")
    class UpdateUser {

        @Test
        @DisplayName("PUT /users/me 요청을 DTO로 바인딩한다")
        void success() throws Exception {
            // when & then
            mockMvc.perform(
                            put("/users/me")
                                    .contentType(
                                            MediaType.APPLICATION_FORM_URLENCODED
                                    )
                                    .param("userId", String.valueOf(USER_ID))
                                    .param("nickname", "newNickname")
                                    .param(
                                            "profileImageUrl",
                                            "/profile/new.png"
                                    )
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(jsonPath("$").exists());

            ArgumentCaptor<UpdateUserRequestDto> captor =
                    ArgumentCaptor.forClass(UpdateUserRequestDto.class);

            verify(userService).updateUser(
                    eq(USER_ID),
                    captor.capture()
            );

            UpdateUserRequestDto request = captor.getValue();

            assertAll(
                    () -> assertEquals(
                            "newNickname",
                            request.getNewNickname()
                    ),
                    () -> assertEquals(
                            "/profile/new.png",
                            request.getNewProfileImageUrl()
                    )
            );
        }
    }

    @Nested
    @DisplayName("회원 비밀번호 수정")
    class UpdatePassword {

        @Test
        @DisplayName("PATCH /users/me/password 요청을 DTO로 바인딩한다")
        void success() throws Exception {
            // when & then
            mockMvc.perform(
                            patch("/users/me/password")
                                    .contentType(
                                            MediaType.APPLICATION_FORM_URLENCODED
                                    )
                                    .param("userId", String.valueOf(USER_ID))
                                    .param(
                                            "password",
                                            "NewPassword123!"
                                    )
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(jsonPath("$").exists());

            ArgumentCaptor<UpdatePasswordRequestDto> captor =
                    ArgumentCaptor.forClass(
                            UpdatePasswordRequestDto.class
                    );

            verify(userService).updatePassword(
                    eq(USER_ID),
                    captor.capture()
            );

            assertEquals(
                    "NewPassword123!",
                    captor.getValue().getNewPassword()
            );
        }
    }

    @Nested
    @DisplayName("회원 삭제")
    class DeleteUser {

        @Test
        @DisplayName("DELETE /users/me 요청을 처리한다")
        void success() throws Exception {
            // when & then
            mockMvc.perform(
                            delete("/users/me")
                                    .param(
                                            "userId",
                                            String.valueOf(USER_ID)
                                    )
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(jsonPath("$").exists());

            verify(userService).deleteUser(USER_ID);
        }
    }

    @Nested
    @DisplayName("이메일 중복 검사")
    class ValidateEmail {

        @Test
        @DisplayName("GET /users/dup/email의 email 파라미터를 전달한다")
        void success() throws Exception {
            // given
            String email = "newuser@test.com";

            // when & then
            mockMvc.perform(
                            get("/users/dup/email")
                                    .param("email", email)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(jsonPath("$").exists());

            verify(userService).isValidEmail(email);
        }
    }

    @Nested
    @DisplayName("닉네임 중복 검사")
    class ValidateNickname {

        @Test
        @DisplayName("GET /users/dup/nickname의 nickname 파라미터를 전달한다")
        void success() throws Exception {
            // given
            String nickname = "newNickname";

            // when & then
            mockMvc.perform(
                            get("/users/dup/nickname")
                                    .param("nickname", nickname)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(jsonPath("$").exists());

            verify(userService).isValidNickname(nickname);
        }
    }
}