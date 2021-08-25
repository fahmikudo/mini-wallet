package io.fahmikudo.wallet.util.commonfunction;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static final String PHONE_PATTERN = "^(([0-9]){6,15})$";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";

    public static boolean isNotEmptyOrNull(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String)
            return !((String) obj).isEmpty();
        else if (obj instanceof Collection)
            return ((Collection<?>) obj).size() != 0;
        return true;
    }

    public static boolean isEmptyOrNull(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String)
            return ((String) obj).isEmpty();
        else if (obj instanceof Collection)
            return ((Collection<?>) obj).size() == 0;
        return false;
    }

    public static boolean regexValidate(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }

}
