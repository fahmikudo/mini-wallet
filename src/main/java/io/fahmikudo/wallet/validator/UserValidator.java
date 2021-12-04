package io.fahmikudo.wallet.validator;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.model.request.UserRegistrationRequest;
import io.fahmikudo.wallet.repository.UserRepository;
import io.fahmikudo.wallet.util.commonfunction.ValidatorUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String userRegistrationValidator(UserRegistrationRequest request){
        if (ValidatorUtil.isEmptyOrNull(request.getEmail())) {
            return "Email can not be null or empty";
        }
        if (!ValidatorUtil.regexValidate(request.getEmail(), ValidatorUtil.EMAIL_PATTERN)) {
            return "Invalid format email";
        }

        if (ValidatorUtil.isEmptyOrNull(request.getFirstName())) {
            return "First name can not be null or empty";
        }
        if (ValidatorUtil.isEmptyOrNull(request.getPhone())) {
            return "Phone can not be null or empty";
        }

        if (!ValidatorUtil.regexValidate(request.getPhone(), ValidatorUtil.PHONE_PATTERN)) {
            return "Invalid format phone number";
        }

        if (ValidatorUtil.isEmptyOrNull(request.getPassword())) {
            return "Password can not be null or empty";
        }

        if (!ValidatorUtil.regexValidate(request.getPassword(), ValidatorUtil.PASSWORD_PATTERN)) {
            return "Invalid format password";
        }

        Optional<User> user = userRepository.findByEmailAndIsDeleted(request.getEmail(), false);
        if (user.isPresent()) {
            return "Email has been registered";
        }

        Optional<User> phone = userRepository.findByPhoneAndIsDeleted(request.getPhone(), false);
        if (phone.isPresent()) {
            return "Phone has been registered";
        }
        return null;
    }

}
