package com.community.demo.unit.user;

import com.community.demo.exception.BusinessException;
import com.community.demo.exception.NotFoundException;
import com.community.demo.files.File;
import com.community.demo.files.FileCategory;
import com.community.demo.files.FileRepository;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import com.community.demo.users.UserService;
import com.community.demo.users.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "testone@test.com";
    private static final String USER_PASSWORD = "Password123!";
    private static final String USER_NICKNAME = "test1";
    private static final String PROFILE_IMAGE_PATH = "/profile/default.png";

    private User user;
    private File userProfileImage;

    @BeforeEach
    void setUp() {
        userProfileImage = new File(
                PROFILE_IMAGE_PATH,
                FileCategory.PROFILE_IMAGE,
                0L
        );

        user = new User(
                USER_NICKNAME,
                userProfileImage,
                USER_EMAIL,
                USER_PASSWORD
        );

        ReflectionTestUtils.setField(user, "id", USER_ID);
    }

    @Nested
    @DisplayName("회원 생성")
    class CreateUser {

        @Test
        @DisplayName("유효한 요청이면 비밀번호를 암호화하고 회원을 저장한다")
        void success() {
            // given
            String email = "testtwo@naver.com";
            String rawPassword = "Password123!";
            String encodedPassword = "encoded-password";
            String nickname = "test2";

            UserRequestDto request = new UserRequestDto(
                    email,
                    rawPassword,
                    nickname,
                    PROFILE_IMAGE_PATH
            );

            when(fileRepository.findByFilePath(PROFILE_IMAGE_PATH))
                    .thenReturn(Optional.of(userProfileImage));

            when(passwordEncoder.encode(rawPassword))
                    .thenReturn(encodedPassword);

            when(userRepository.save(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // when
            UserResponseDto response = userService.createUser(request);

            // then
            assertNotNull(response);

            ArgumentCaptor<User> userCaptor =
                    ArgumentCaptor.forClass(User.class);

            verify(fileRepository)
                    .findByFilePath(PROFILE_IMAGE_PATH);
            verify(passwordEncoder)
                    .encode(rawPassword);
            verify(userRepository)
                    .save(userCaptor.capture());

            User savedUser = userCaptor.getValue();

            assertAll(
                    () -> assertEquals(email, savedUser.getEmail()),
                    () -> assertEquals(nickname, savedUser.getNickname()),
                    () -> assertEquals(
                            encodedPassword,
                            savedUser.getPassword()
                    ),
                    () -> assertSame(
                            userProfileImage,
                            savedUser.getProfileImage()
                    )
            );
        }

        @Test
        @DisplayName("이메일 형식이 올바르지 않으면 예외가 발생한다")
        void failWhenEmailFormatIsInvalid() {
            // given
            UserRequestDto request = new UserRequestDto(
                    "wrongemailaddress",
                    "Password123!",
                    "wrongemail",
                    PROFILE_IMAGE_PATH
            );

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.createUser(request)
            );

            verifyNoInteractions(
                    passwordEncoder,
                    fileRepository,
                    userRepository
            );
        }

        @Test
        @DisplayName("이메일이 비어 있으면 예외가 발생한다")
        void failWhenEmailIsEmpty() {
            // given
            UserRequestDto request = new UserRequestDto(
                    "",
                    "Password123!",
                    "wrongemail",
                    PROFILE_IMAGE_PATH
            );

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.createUser(request)
            );

            verifyNoInteractions(
                    passwordEncoder,
                    fileRepository,
                    userRepository
            );
        }

        @Test
        @DisplayName("비밀번호가 비어 있으면 예외가 발생한다")
        void failWhenPasswordIsEmpty() {
            // given
            UserRequestDto request = new UserRequestDto(
                    "testtwo@naver.com",
                    "",
                    "wrongpassword",
                    PROFILE_IMAGE_PATH
            );

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.createUser(request)
            );

            verifyNoInteractions(
                    passwordEncoder,
                    fileRepository,
                    userRepository
            );
        }

        @Test
        @DisplayName("비밀번호 형식이 올바르지 않으면 예외가 발생한다")
        void failWhenPasswordFormatIsInvalid() {
            // given
            UserRequestDto request = new UserRequestDto(
                    "testtwo@naver.com",
                    "password",
                    "wrongpassword",
                    PROFILE_IMAGE_PATH
            );

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.createUser(request)
            );

            verifyNoInteractions(
                    passwordEncoder,
                    fileRepository,
                    userRepository
            );
        }

        @Test
        @DisplayName("프로필 이미지가 존재하지 않으면 예외가 발생한다")
        void failWhenProfileImageDoesNotExist() {
            // given
            String missingImagePath = "/profile/missing.png";

            UserRequestDto request = new UserRequestDto(
                    "testtwo@naver.com",
                    "Password123!",
                    "test2",
                    missingImagePath
            );

            when(fileRepository.findByFilePath(missingImagePath))
                    .thenReturn(Optional.empty());

            // when & then
            assertThrows(
                    RuntimeException.class,
                    () -> userService.createUser(request)
            );

            verify(fileRepository)
                    .findByFilePath(missingImagePath);
            verify(userRepository, never())
                    .save(any(User.class));
        }
    }

    @Nested
    @DisplayName("회원 삭제")
    class DeleteUser {

        @Test
        @DisplayName("회원을 삭제 상태로 변경하고 저장한다")
        void success() {
            // given
            when(userRepository.findById(USER_ID))
                    .thenReturn(Optional.of(user));

            // when
            userService.deleteUser(USER_ID);

            // then
            assertTrue(user.isDeleted());

            verify(userRepository).findById(USER_ID);
            verify(userRepository).save(user);
        }

        @Test
        @DisplayName("회원이 존재하지 않으면 예외가 발생한다")
        void failWhenUserDoesNotExist() {
            // given
            when(userRepository.findById(USER_ID))
                    .thenReturn(Optional.empty());

            // when & then
            assertThrows(
                    RuntimeException.class,
                    () -> userService.deleteUser(USER_ID)
            );

            verify(userRepository).findById(USER_ID);
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("회원 정보 수정")
    class UpdateUser {

        @Test
        @DisplayName("닉네임과 프로필 이미지를 수정하고 저장한다")
        void success() {
            // given
            String newNickname = "newnickname";
            String newProfileImagePath = "/profile/new.png";

            File newProfileImage = new File(
                    newProfileImagePath,
                    FileCategory.PROFILE_IMAGE,
                    0L
            );

            UpdateUserRequestDto request =
                    new UpdateUserRequestDto(
                            newNickname,
                            newProfileImagePath
                    );

            when(userRepository.findById(USER_ID))
                    .thenReturn(Optional.of(user));

            when(fileRepository.findByFilePath(newProfileImagePath))
                    .thenReturn(Optional.of(newProfileImage));

            // when
            userService.updateUser(USER_ID, request);

            // then
            assertAll(
                    () -> assertEquals(
                            newNickname,
                            user.getNickname()
                    ),
                    () -> assertSame(
                            newProfileImage,
                            user.getProfileImage()
                    )
            );

            verify(userRepository).findById(USER_ID);
            verify(fileRepository)
                    .findByFilePath(newProfileImagePath);
            verify(userRepository).save(user);
        }

        @Test
        @DisplayName("회원이 존재하지 않으면 예외가 발생한다")
        void failWhenUserDoesNotExist() {
            // given
            UpdateUserRequestDto request =
                    new UpdateUserRequestDto(
                            "newnickname",
                            "/profile/new.png"
                    );

            when(userRepository.findById(USER_ID))
                    .thenReturn(Optional.empty());

            // when & then
            assertThrows(
                    RuntimeException.class,
                    () -> userService.updateUser(USER_ID, request)
            );

            verify(userRepository).findById(USER_ID);
            verify(userRepository, never()).save(any());
            verifyNoInteractions(fileRepository);
        }
    }

    @Nested
    @DisplayName("회원 비밀번호 수정")
    class UpdatePassword {

        @Test
        @DisplayName("새 비밀번호를 암호화하여 저장한다")
        void success() {
            // given
            String newPassword = "NewPassword123!";
            String encodedPassword = "encoded-new-password";

            UpdatePasswordRequestDto request =
                    new UpdatePasswordRequestDto(newPassword);

            when(userRepository.findById(USER_ID))
                    .thenReturn(Optional.of(user));

            when(passwordEncoder.encode(newPassword))
                    .thenReturn(encodedPassword);

            // when
            userService.updatePassword(USER_ID, request);

            // then
            assertEquals(encodedPassword, user.getPassword());

            verify(userRepository).findById(USER_ID);
            verify(passwordEncoder).encode(newPassword);
            verify(userRepository).save(user);
        }

        @Test
        @DisplayName("새 비밀번호 형식이 잘못되면 예외가 발생한다")
        void failWhenPasswordFormatIsInvalid() {
            // given
            UpdatePasswordRequestDto request =
                    new UpdatePasswordRequestDto("password");

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.updatePassword(USER_ID, request)
            );

            verify(passwordEncoder, never()).encode(anyString());
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("회원이 존재하지 않으면 예외가 발생한다")
        void failWhenUserDoesNotExist() {
            // given
            String newPassword = "NewPassword123!";

            UpdatePasswordRequestDto request =
                    new UpdatePasswordRequestDto(newPassword);

            when(userRepository.findById(USER_ID))
                    .thenReturn(Optional.empty());

            // when & then
            assertThrows(
                    RuntimeException.class,
                    () -> userService.updatePassword(USER_ID, request)
            );

            verify(userRepository).findById(USER_ID);
            verify(passwordEncoder, never()).encode(anyString());
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("회원 정보 조회")
    class GetUser {

        @Test
        @DisplayName("이메일로 삭제되지 않은 회원을 조회한다")
        void success() {
            // given
            when(userRepository.findByIsDeletedFalseAndEmail(USER_EMAIL))
                    .thenReturn(Optional.of(user));

            // when
            UserInfoDto response = userService.getUser(USER_EMAIL);

            // then
            assertNotNull(response);

            verify(userRepository)
                    .findByIsDeletedFalseAndEmail(USER_EMAIL);
        }

        @Test
        @DisplayName("회원이 존재하지 않으면 예외가 발생한다")
        void failWhenUserDoesNotExist() {
            // given
            when(userRepository.findByIsDeletedFalseAndEmail(USER_EMAIL))
                    .thenReturn(Optional.empty());

            // when & then
            assertThrows(
                    RuntimeException.class,
                    () -> userService.getUser(USER_EMAIL)
            );

            verify(userRepository)
                    .findByIsDeletedFalseAndEmail(USER_EMAIL);
        }
    }

    @Nested
    @DisplayName("이메일 중복 검사")
    class ValidateEmail {

        @Test
        @DisplayName("이메일이 중복되지 않으면 정상적으로 종료한다")
        void successWhenEmailIsNotDuplicated() {
            // given
            String email = "newemail@test.com";

            when(userRepository.existsByEmail(email))
                    .thenReturn(false);

            // when
            assertDoesNotThrow(
                    () -> userService.isValidEmail(email)
            );

            // then
            verify(userRepository).existsByEmail(email);
        }

        @Test
        @DisplayName("이메일이 중복되면 예외가 발생한다")
        void failWhenEmailIsDuplicated() {
            // given
            String email = "testone@test.com";

            when(userRepository.existsByEmail(email))
                    .thenReturn(true);

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.isValidEmail(email)
            );

            verify(userRepository).existsByEmail(email);
        }
    }

    @Nested
    @DisplayName("닉네임 중복 검사")
    class ValidateNickname {

        @Test
        @DisplayName("닉네임이 중복되지 않으면 정상적으로 종료한다")
        void successWhenNicknameIsNotDuplicated() {
            // given
            String nickname = "newnickname";

            when(userRepository.existsByNickname(nickname))
                    .thenReturn(false);

            // when
            assertDoesNotThrow(
                    () -> userService.isValidNickname(nickname)
            );

            // then
            verify(userRepository).existsByNickname(nickname);
        }

        @Test
        @DisplayName("닉네임이 중복되면 예외가 발생한다")
        void failWhenNicknameIsDuplicated() {
            // given
            String nickname = USER_NICKNAME;

            when(userRepository.existsByNickname(nickname))
                    .thenReturn(true);

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.isValidNickname(nickname)
            );

            verify(userRepository).existsByNickname(nickname);
        }
    }
}