package com.java.banksystemproject.service.exception;

public final class ExceptionMessageCodes {
    public static final String BSS_NEGATIVE_AMOUNT = "BSS_NEGATIVE_AMOUNT_IS_NOT_VALID";
    public static final String BSS_NEGATIVE_INPUT_VALUE = "BSS_NEGATIVE_INPUT_VALUE_IS_NOT_VALID";
    public static final String BSS_EMPTY_STRING = "BSS_EMPTY_STRING_IS_NOT_VALID";

    public static final String BSS_INSUFFICIENT_BALANCE = "BSS_BALANCE_IS_INSUFFICIENT";
    public static final String BSS_INSUFFICIENT_BALANCE_AND_OVER_DRAFT_LIMIT = "BSS_SUM_OF_BALANCE_AND_OVER_DRAFT_LIMIT_IS_INSUFFICIENT";
    public static final String BSS_MINIMUM_BALANCE_LIMIT = "BSS_MINIMUM_BALANCE_LIMIT_VIOLATED";
    public static final String BSS_INSUFFICIENT_BALANCE_AND_AMOUNT_TO_PAY_FEE = "BSS_INSUFFICIENT_BALANCE_AND_AMOUNT_TO_PAY_FEE";
    public static final String BSS_INCOMPATIBLE_ACCOUNT_TYPE = "BSS_INCOMPATIBLE_ACCOUNT_TYPE";

    public static final String BSS_USER_NAME_ALREADY_EXIST = "BSS_USER_NAME_ALREADY_EXIST";
    public static final String BSS_USER_NOT_EXIST = "BSS_USER_NOT_EXIST";
}