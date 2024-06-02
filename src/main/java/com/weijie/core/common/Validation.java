package com.weijie.core.common;

import com.weijie.core.service.UserFilterService;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.enums.WJLevel;

public enum Validation {
    /**
     * The length must be between 3 and 20 characters.
     * You can not begin with a period, underscore, or hyphen.
     * Can not contain consecutive periods, underscores, or hyphens.
     * Only letters (case-sensitive) , numbers, underscores, and hyphens are allowed.
     */
    Username("^(?=.{3,20}$)(?![_.-])(?!.*[_.-]{2})[a-zA-Z0-9_-]+([^._-])$", 1),
    /**
     * User name section: may contain lowercase letters, numbers, underscores, periods, and hyphens, at least one character.
     *
     * @ symbol: used to separate the user name from the domain name section.
     * Domain name section: may contain lowercase letters, numbers, periods, and hyphens, at least one character.
     * Top-level domain section: may contain lowercase letters and periods, 2 to 6 characters in length.
     */
    Email("^([a-z0-9_\\.-]+\\@[\\da-z\\.-]+\\.[a-z\\.]{2,6})$", 2),
    /**
     * Checks that a password has a minimum of 6 characters,
     * at least 1 uppercase letter,
     * 1 lowercase letter, and 1 number with no spaces.
     */
    Password("^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,12})\\S$", 3);

    private final String regex;
    private final int position;

    Validation(String regex, int position) {
        this.regex = regex;
        this.position = position;
    }

    private boolean isMatch(String s) {
        return !s.matches(this.regex);
    }

    public boolean check(String s, boolean isSignUp) {
        if (s == null || s.equals("null") || s.equals("")) {
            WJMessage.show(this.name() + " can not be empty", WJLevel.DANGER);
            return true;
        }
        if (isMatch(s)) {
            WJMessage.show("Incorrect " + this.name() + " format", WJLevel.DANGER);
            return true;
        }
        switch (this) {
            case Username -> {
                if (UserFilterService.isNameExist(s)) {
                    WJMessage.show("This username is already taken", WJLevel.DANGER);
                    return true;
                }
            }
            case Email -> {
                boolean isEmailExist = UserFilterService.isEmailExist(s);
                if (!isSignUp) {
                    if (!isEmailExist) {
                        WJMessage.show("Can't find your email address, please register as a new user", WJLevel.DANGER);
                        return true;
                    }
                } else {
                    if (isEmailExist) {
                        WJMessage.show("This email address is already registered", WJLevel.DANGER);
                        return true;
                    }
                }
            }
            case Password -> {
                if (!isSignUp && !UserFilterService.isPasswordExist(PasswordHashing.get(s))) {
                    WJMessage.show("Wrong password", WJLevel.DANGER);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean check(String s) {
        return check(s, false);
    }
}
