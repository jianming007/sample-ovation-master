package top.zhacker.ddd.identity.domain.user;
/** 加密服务-接口*/
public interface EncryptionService {
    /**
     * 加密值
     * @param aPlainTextValue 一个纯文本
     * @return
     */
    String encryptedValue(String aPlainTextValue);
}