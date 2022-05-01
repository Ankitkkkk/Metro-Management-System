package com.mms.mms

interface Constants {
    companion object {
        const val SUPER_ADMIN_KEY = "1e866dad-025e-46b7-9989-87cd962a6e4c"
        const val ADMIN_KEY = "89a7da13-e84f-48c4-99df-ca54303e9d89"
        const val USER_KEY = "2595b8df-7326-4792-bb2a-f4f64fd13b67"
        const val BAD_PARAM = "bad.param"
        const val MISSING_PARAM = "missing.param"
        const val EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    }
}