package com.ohgiraffers.understand.exception;

/* insert 시 중복되는 이름있는지 확인하는 클래스 */
// 기존 메뉴에 중복된 이름이 있을 시 발생시킬 익셉션
public class NotInsertNameException extends Exception {

    public NotInsertNameException(String message) {
        super(message);
    }
}
