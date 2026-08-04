package com.community.demo.unit.user;

import com.community.demo.ApiResponse;
import com.community.demo.auth.security.SecurityUtils;
import com.community.demo.users.UserController;
import com.community.demo.users.UserService;
import com.community.demo.users.dto.EmailValidationRequestDto;
import com.community.demo.users.dto.NicknameValidationRequestDto;
import com.community.demo.users.dto.ReadUserResponseDto;
import com.community.demo.users.dto.UpdatePasswordRequestDto;
import com.community.demo.users.dto.UpdateUserRequestDto;
import com.community.demo.users.dto.UserRequestDto;
import com.community.demo.users.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    private static final Long USER_ID = 1L;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Nested
    @DisplayName("회원 정보 조회")
    class ReadUser {

        @Test
        @DisplayName("인증 정보에서 회원 ID를 구한 뒤 회원 정보를 조회한다")
        void success() {
            // given
            ReadUserResponseDto serviceResponse =
                    mock(ReadUserResponseDto.class);

            when(userService.readUser(USER_ID))
                    .thenReturn(serviceResponse);

            try (MockedStatic<SecurityUtils> securityUtils =
                         mockStatic(SecurityUtils.class)) {

                securityUtils
                        .when(() ->
                                SecurityUtils.resolveAuthentication(authentication))
                        .thenReturn(USER_ID);

                // when
                ResponseEntity<ApiResponse<ReadUserResponseDto>> response =
                        userController.readUser(authentication);

                // then
                assertAll(
                        () -> assertEquals(
                                HttpStatus.OK,
                                response.getStatusCode()
                        ),
                        () -> assertNotNull(response.getBody())
                );

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
        @DisplayName("회원을 생성하고 201 Created를 반환한다")
        void success() {
            // given
            UserRequestDto request = mock(UserRequestDto.class);
            UserResponseDto serviceResponse =
                    mock(UserResponseDto.class);

            when(userService.createUser(request))
                    .thenReturn(serviceResponse);

            // when
            ResponseEntity<ApiResponse<UserResponseDto>> response =
                    userController.createUser(request);

            // then
            assertAll(
                    () -> assertEquals(
                            HttpStatus.CREATED,
                            response.getStatusCode()
                    ),
                    () -> assertNotNull(response.getBody())
            );

            verify(userService).createUser(request);
        }
    }

    @Nested
    @DisplayName("회원 정보 수정")
    class UpdateUser {

        @Test
        @DisplayName("회원 정보를 수정하고 200 OK를 반환한다")
        void success() {
            try (MockedStatic<SecurityUtils> securityUtils =
                         mockStatic(SecurityUtils.class)) {

                securityUtils
                        .when(() ->
                                SecurityUtils.resolveAuthentication(authentication))
                        .thenReturn(USER_ID);

                // given
                UpdateUserRequestDto request =
                        mock(UpdateUserRequestDto.class);

                // when
                ResponseEntity<ApiResponse<Void>> response =
                        userController.updateUser(authentication, request);

                // then
                assertAll(
                        () -> assertEquals(
                                HttpStatus.OK,
                                response.getStatusCode()
                        ),
                        () -> assertNotNull(response.getBody())
                );

                verify(userService).updateUser(USER_ID, request);
            }
        }
    }

    @Nested
    @DisplayName("회원 비밀번호 수정")
    class UpdatePassword {

        @Test
        @DisplayName("비밀번호를 수정하고 200 OK를 반환한다")
        void success() {
            try (MockedStatic<SecurityUtils> securityUtils =
                         mockStatic(SecurityUtils.class)) {

                securityUtils
                        .when(() ->
                                SecurityUtils.resolveAuthentication(authentication))
                        .thenReturn(USER_ID);

                // given
                UpdatePasswordRequestDto request =
                        mock(UpdatePasswordRequestDto.class);

                // when
                ResponseEntity<ApiResponse<Void>> response =
                        userController.updatePassword(authentication, request);

                // then
                assertAll(
                        () -> assertEquals(
                                HttpStatus.OK,
                                response.getStatusCode()
                        ),
                        () -> assertNotNull(response.getBody())
                );

                verify(userService).updatePassword(USER_ID, request);
            }
        }
    }

    @Nested
    @DisplayName("회원 삭제")
    class DeleteUser {

        @Test
        @DisplayName("회원을 삭제하고 200 OK를 반환한다")
        void success() {
            try (MockedStatic<SecurityUtils> securityUtils =
                         mockStatic(SecurityUtils.class)) {

                securityUtils
                        .when(() ->
                                SecurityUtils.resolveAuthentication(authentication))
                        .thenReturn(USER_ID);

                // when
                ResponseEntity<ApiResponse<Void>> response =
                        userController.deleteUser(authentication);

                // then
                assertAll(
                        () -> assertEquals(
                                HttpStatus.OK,
                                response.getStatusCode()
                        ),
                        () -> assertNotNull(response.getBody())
                );

                verify(userService).deleteUser(USER_ID);
            }
        }
    }

    @Nested
    @DisplayName("이메일 중복 검사")
    class ValidateEmail {

        @Test
        @DisplayName("DTO에서 이메일을 추출하여 중복 검사를 수행한다")
        void success() {
            // given
            String email = "newuser@test.com";

            EmailValidationRequestDto request =
                    mock(EmailValidationRequestDto.class);

            when(request.getEmail()).thenReturn(email);

            // when
            ResponseEntity<ApiResponse<Void>> response =
                    userController.isValidEmail(request);

            // then
            assertAll(
                    () -> assertEquals(
                            HttpStatus.OK,
                            response.getStatusCode()
                    ),
                    () -> assertNotNull(response.getBody())
            );

            verify(request).getEmail();
            verify(userService).isValidEmail(email);
        }
    }

    @Nested
    @DisplayName("닉네임 중복 검사")
    class ValidateNickname {

        @Test
        @DisplayName("DTO에서 닉네임을 추출하여 중복 검사를 수행한다")
        void success() {
            // given
            String nickname = "newnickname";

            NicknameValidationRequestDto request =
                    mock(NicknameValidationRequestDto.class);

            when(request.getNickname()).thenReturn(nickname);

            // when
            ResponseEntity<ApiResponse<Void>> response =
                    userController.isValidNickname(request);

            // then
            assertAll(
                    () -> assertEquals(
                            HttpStatus.OK,
                            response.getStatusCode()
                    ),
                    () -> assertNotNull(response.getBody())
            );

            verify(request).getNickname();
            verify(userService).isValidNickname(nickname);
        }
    }
}